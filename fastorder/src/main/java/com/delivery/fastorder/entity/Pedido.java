package com.delivery.fastorder.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "pedidos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = false)
    private Usuario cliente;

    @ManyToOne
    @JoinColumn(name = "repartidor_id")
    private Usuario repartidor; // Puede ser nulo al inicio

    @Column(nullable = false)
    private LocalDateTime fechaPedido = LocalDateTime.now();

    @Column(nullable = false)
    private Double costoEnvio = 20.00; // Costo fijo según requerimiento

    @Column(nullable = false)
    private Double montoTotal;

    @Column(nullable = false, length = 50)
    private String estado = "PENDIENTE"; // PENDIENTE, EN_PREPARACION, EN_CAMINO, ENTREGADO, CANCELADO

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DetallePedido> detalles;
}