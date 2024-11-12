package com.farmacia_web.farmacia_web.controllers;

import com.farmacia_web.farmacia_web.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/dashboard")
public class DashboardController {

    @Autowired
    ClienteRepository clienteRepository;
    @Autowired
    private ProveedorRepository proveedorRepository;
    @Autowired
    private ProductoRepository productoRepository;
    @Autowired
    private VentaRepository ventaRepository;
    @Autowired
    private DetallesVentaRepository detallesVentaRepository;

    @Autowired
    private ComprasRepository comprasRepository;
    @Autowired
    private CategoriaRepository categoriaRepository;

    @GetMapping
    public String dashboard(Model model) {

        // Suponiendo que estás dentro de un método del controlador y ya tienes acceso al model
        model.addAttribute("totalClientes", clienteRepository.totalClientes());
        model.addAttribute("totalProveedores", proveedorRepository.totalProveedores());
        model.addAttribute("totalProductos", productoRepository.totalProductos());
        model.addAttribute("totalVentas", ventaRepository.totalVentas());
        model.addAttribute("existenciasVendidas", detallesVentaRepository.existenciasvendidas());
        model.addAttribute("existenciasActuales", productoRepository.existenciasActuales());
        model.addAttribute("existenciasTotales", productoRepository.existenciasActuales() + detallesVentaRepository.existenciasvendidas());
        model.addAttribute("importeVendido", ventaRepository.importeVendido());
        model.addAttribute("importePagado", comprasRepository.importePagado());
        model.addAttribute("beneficioBruto", ventaRepository.obtenerBeneficioBruto());
        model.addAttribute("impuestos", ventaRepository.obtenerTotalIva());
        model.addAttribute("beneficioNeto", ventaRepository.obtenerBeneficioNeto());

        List<Object[]> chart1 = categoriaRepository.cantidadVendidaPorCategoria();

        List<String> categorias = new ArrayList<>();
        List<Long> cantidades = new ArrayList<>();

        for (Object[] fila : chart1) {
            categorias.add((String) fila[0]);    // La categoría
            cantidades.add(((BigDecimal) fila[1]).longValue()); // Convierte BigDecimal a Long
        }

        List<Object[]> chart2 = ventaRepository.findEmpleadosConMasVentas();

        List<String> empleado = new ArrayList<>();
        List<Long> numeroVentas = new ArrayList<>();

        for (Object[] fila : chart2) {
            empleado.add((String) fila[0]);
            numeroVentas.add(((Long) fila[1]));
        }

        List<Object[]> chart3 = detallesVentaRepository.topProductos();

        List<String> productos = new ArrayList<>();
        List<Long> cantidadVentas = new ArrayList<>();

        for (Object[] fila : chart3) {
            productos.add((String) fila[0]);
            cantidadVentas.add(((BigDecimal) fila[1]).longValue()); // Convierte BigDecimal a Long
        }

        List<Object[]> chart4 = ventaRepository.contarMetodosPago();
        List<String> metodosPago = new ArrayList<>();
        List<Long> cantidadPagos = new ArrayList<>();

        for (Object[] fila : chart4) {
            metodosPago.add((String) fila[0]);
            cantidadPagos.add(((Long) fila[1])); // Convierte BigDecimal a Long
        }

        List<Object[]> chart5 = detallesVentaRepository.topProveedores();
        List<String> proveedores = new ArrayList<>();
        List<Long> numVentas = new ArrayList<>();

        for (Object[] fila : chart5) {
            proveedores.add((String) fila[0]);
            numVentas.add(((BigDecimal) fila[1]).longValue()); // Convierte BigDecimal a Long
        }


        model.addAttribute("categorias", categorias);
        model.addAttribute("cantidades", cantidades);

        model.addAttribute("empleados", empleado);
        model.addAttribute("numeroVentas", numeroVentas);

        model.addAttribute("productos", productos);
        model.addAttribute("cantidadVentas", cantidadVentas);

        model.addAttribute("metodosPago", metodosPago);
        model.addAttribute("cantidadPagos", cantidadPagos);

        model.addAttribute("proveedores", proveedores);
        model.addAttribute("numVentas", numVentas);


        return "entidades/dashboard";
    }
}


