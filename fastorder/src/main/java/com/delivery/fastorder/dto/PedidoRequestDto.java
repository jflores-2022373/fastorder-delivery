package com.delivery.fastorder.dto;

import lombok.Data;
import java.util.List;

@Data
public class PedidoRequestDto {
    private List<ItemPedidoDto> items;
}