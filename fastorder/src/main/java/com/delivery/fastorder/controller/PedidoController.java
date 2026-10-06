package com.delivery.fastorder.controller;

import com.delivery.fastorder.dto.PedidoRequestDto;
import com.delivery.fastorder.entity.Pedido;
import com.delivery.fastorder.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/pedidos")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @PostMapping
    @PreAuthorize("hasRole('CLIENTE')")
    public ResponseEntity<Pedido> crearPedido(@RequestBody PedidoRequestDto requestDto, Authentication authentication) {
        String emailCliente = authentication.getName();
        Pedido nuevoPedido = pedidoService.crearPedido(emailCliente, requestDto);
        return ResponseEntity.ok(nuevoPedido);
    }

    @GetMapping("/mis-pedidos")
    @PreAuthorize("hasRole('CLIENTE')")
    public ResponseEntity<List<Pedido>> obtenerMisPedidos(Authentication authentication) {
        String emailCliente = authentication.getName();
        List<Pedido> pedidos = pedidoService.obtenerPedidosPorCliente(emailCliente);
        return ResponseEntity.ok(pedidos);
    }

    @GetMapping("/disponibles")
    @PreAuthorize("hasAnyRole('REPARTIDOR', 'ADMIN')")
    public ResponseEntity<List<Pedido>> obtenerPedidosDisponibles() {
        List<Pedido> pedidos = pedidoService.obtenerPedidosDisponibles();
        return ResponseEntity.ok(pedidos);
    }

    @PatchMapping("/{id}/estado")
    @PreAuthorize("hasAnyRole('REPARTIDOR', 'ADMIN')")
    public ResponseEntity<Pedido> actualizarEstado(@PathVariable Long id, @RequestBody Map<String, String> body) {
        String nuevoEstado = body.get("estado");
        Pedido pedidoActualizado = pedidoService.actualizarEstado(id, nuevoEstado);
        return ResponseEntity.ok(pedidoActualizado);
    }

    @PatchMapping("/{id}/cancelar")
    @PreAuthorize("hasAnyRole('CLIENTE', 'ADMIN')")
    public ResponseEntity<Pedido> cancelarPedido(@PathVariable Long id, Authentication authentication) {
        String emailUsuario = authentication.getName();
        Pedido pedidoCancelado = pedidoService.cancelarPedido(id, emailUsuario);
        return ResponseEntity.ok(pedidoCancelado);
    }
}