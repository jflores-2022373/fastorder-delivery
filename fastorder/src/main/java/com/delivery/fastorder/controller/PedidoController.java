package com.delivery.fastorder.controller;

import com.delivery.fastorder.dto.PedidoRequestDto;
import com.delivery.fastorder.entity.Pedido;
import com.delivery.fastorder.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<Pedido> crearPedido(@RequestBody PedidoRequestDto requestDto, Authentication authentication) {
        String email = authentication.getName();
        Pedido nuevoPedido = pedidoService.crearPedido(email, requestDto);
        return ResponseEntity.ok(nuevoPedido);
    }

    @GetMapping("/mis-pedidos")
    public ResponseEntity<List<Pedido>> obtenerMisPedidos(Authentication authentication) {
        String email = authentication.getName();
        List<Pedido> pedidos = pedidoService.obtenerPedidosPorCliente(email);
        return ResponseEntity.ok(pedidos);
    }

    @GetMapping("/disponibles")
    public ResponseEntity<List<Pedido>> obtenerPedidosDisponibles() {
        List<Pedido> pedidos = pedidoService.obtenerPedidosDisponibles();
        return ResponseEntity.ok(pedidos);
    }

    @PatchMapping("/{id}/estado")
    public ResponseEntity<Pedido> actualizarEstado(@PathVariable Long id, @RequestBody Map<String, String> body) {
        String nuevoEstado = body.get("estado");
        Pedido pedidoActualizado = pedidoService.actualizarEstado(id, nuevoEstado);
        return ResponseEntity.ok(pedidoActualizado);
    }

    @PatchMapping("/{id}/cancelar")
    public ResponseEntity<Pedido> cancelarPedido(@PathVariable Long id, Authentication authentication) {
        // Obtenemos el email si se requiere validar rol, o llamamos directo al servicio
        String email = authentication.getName();
        Pedido pedidoCancelado = pedidoService.cancelarPedido(id, email);
        return ResponseEntity.ok(pedidoCancelado);
    }
}