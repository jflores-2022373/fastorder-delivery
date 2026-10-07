package com.delivery.fastorder.service;

import com.delivery.fastorder.dto.ItemPedidoDto;
import com.delivery.fastorder.dto.PedidoRequestDto;
import com.delivery.fastorder.entity.DetallePedido;
import com.delivery.fastorder.entity.Pedido;
import com.delivery.fastorder.entity.Producto;
import com.delivery.fastorder.entity.Usuario;
import com.delivery.fastorder.repository.PedidoRepository;
import com.delivery.fastorder.repository.ProductoRepository;
import com.delivery.fastorder.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Transactional
    public Pedido crearPedido(String emailCliente, PedidoRequestDto requestDto) {
        Usuario cliente = usuarioRepository.findByCorreo(emailCliente)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));

        Pedido pedido = new Pedido();
        pedido.setCliente(cliente);
        pedido.setEstado("PENDIENTE");
        pedido.setCostoEnvio(20.00);

        double subtotalGlobal = 0.0;
        List<DetallePedido> detalles = new ArrayList<>();

        for (ItemPedidoDto itemDto : requestDto.getItems()) {
            Producto producto = productoRepository.findById(itemDto.getProductId())
                    .orElseThrow(() -> new RuntimeException("Producto no encontrado con ID: " + itemDto.getProductId()));

            // Validación de stock dinámico
            if (producto.getStock() < itemDto.getCantidad()) {
                throw new RuntimeException("Stock insuficiente para el producto: " + producto.getNombre());
            }

            // Descontar stock (Corregido)
            producto.setStock(producto.getStock() - itemDto.getCantidad());
            productoRepository.save(producto);

            // Crear detalle
            DetallePedido detalle = new DetallePedido();
            detalle.setPedido(pedido);
            detalle.setProducto(producto);
            detalle.setCantidad(itemDto.getCantidad());
            detalle.setPrecioUnitario(producto.getPrecio());

            // Subtotal del item (Corregido)
            double subtotalItem = producto.getPrecio() * itemDto.getCantidad();
            detalle.setSubtotal(subtotalItem);

            subtotalGlobal += subtotalItem;
            detalles.add(detalle);
        }

        // Monto total = Subtotal de productos + Costo de envío (Q20.00)
        pedido.setMontoTotal(subtotalGlobal + pedido.getCostoEnvio());
        pedido.setDetalles(detalles);

        return pedidoRepository.save(pedido);
    }

    public List<Pedido> obtenerPedidosPorCliente(String emailCliente) {
        Usuario cliente = usuarioRepository.findByCorreo(emailCliente)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));
        return pedidoRepository.findByCliente(cliente);
    }

    public List<Pedido> obtenerPedidosDisponibles() {
        return pedidoRepository.findByEstadoIn(List.of("PENDIENTE", "EN_PREPARACION", "EN_CAMINO"));
    }

    @Transactional
    public Pedido actualizarEstado(Long pedidoId, String nuevoEstado) {
        Pedido pedido = pedidoRepository.findById(pedidoId)
                .orElseThrow(() -> new RuntimeException("Pedido no encontrado"));
        pedido.setEstado(nuevoEstado);
        return pedidoRepository.save(pedido);
    }

    @Transactional
    public Pedido cancelarPedido(Long pedidoId, String emailUsuario) {
        Pedido pedido = pedidoRepository.findById(pedidoId)
                .orElseThrow(() -> new RuntimeException("Pedido no encontrado"));

        if (!pedido.getEstado().equals("PENDIENTE")) {
            throw new RuntimeException("El pedido solo se puede cancelar si está en estado PENDIENTE");
        }

        // Restaurar stock de los productos
        for (DetallePedido detalle : pedido.getDetalles()) {
            Producto producto = detalle.getProducto();
            producto.setStock(producto.getStock() + detalle.getCantidad());
            productoRepository.save(producto);
        }

        pedido.setEstado("CANCELADO");
        return pedidoRepository.save(pedido);
    }
}