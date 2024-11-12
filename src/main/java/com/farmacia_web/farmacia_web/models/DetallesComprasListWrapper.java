package com.farmacia_web.farmacia_web.models;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Data
public class DetallesComprasListWrapper {
    private List<DetallesCompra> detallesCompra  = new ArrayList<>();
}
