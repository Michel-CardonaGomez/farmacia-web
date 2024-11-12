package com.farmacia_web.farmacia_web.services;

import com.farmacia_web.farmacia_web.models.DetallesVenta;
import com.farmacia_web.farmacia_web.models.Factura;
import com.farmacia_web.farmacia_web.models.Venta;
import com.farmacia_web.farmacia_web.repositories.DetallesVentaRepository;
import com.farmacia_web.farmacia_web.repositories.FacturaRepository;
import com.farmacia_web.farmacia_web.repositories.VentaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VentaService {

    @Autowired
    private VentaRepository ventaRepository;

    @Autowired
    private DetallesVentaRepository detallesVentaRepository;

    @Autowired
    private FacturaRepository facturaRepository;

    public Venta guardarVenta(Venta venta) {
        return ventaRepository.save(venta);
    }

    public void guardarDetalleVenta(DetallesVenta detalle) {
        detallesVentaRepository.save(detalle);
    }

    public void guardarFactura(Factura factura) {
        facturaRepository.save(factura);
    }

    public void actualizarVenta(Venta venta) {
        ventaRepository.save(venta);
    }

    public void actualizarFactura(Factura factura) {
        facturaRepository.save(factura);
    }
}
