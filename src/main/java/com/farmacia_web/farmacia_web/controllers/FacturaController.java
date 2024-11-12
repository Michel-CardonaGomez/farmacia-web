package com.farmacia_web.farmacia_web.controllers;

import com.farmacia_web.farmacia_web.models.*;
import com.farmacia_web.farmacia_web.repositories.*;
import com.farmacia_web.farmacia_web.services.ClienteService;
import com.farmacia_web.farmacia_web.services.ProductoService;
import com.farmacia_web.farmacia_web.services.VentaService;
import com.itextpdf.io.IOException;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.colors.Color;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.UnitValue;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.security.Principal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("/facturas")
public class FacturaController {

    @Autowired
    ProductoService productoService;

    @Autowired
    VentaService ventaService;

    @Autowired
    EmpleadoRepository empleadoRepository;

    Map<String, Integer> contadorPorFecha = new HashMap<>();

    @Autowired
    private ClienteRepository clienteRepository;
    @Autowired
    private ClienteService clienteService;
    @Autowired
    private ProveedorRepository proveedorRepository;
    @Autowired
    private ProductoRepository productoRepository;
    @Autowired
    private FacturaRepository facturaRepository;
    @Autowired
    private ComprasRepository comprasRepository;
    @Autowired
    private DetallesCompraRepository detallesCompraRepository;
    @Autowired
    private VentaRepository ventaRepository;


    @GetMapping("/ventas")
    public String mostrarFormVentas(Model model, Authentication authentication, @RequestParam(value = "identificacion", required = false) Long identificacion) {
        if (identificacion == null) {
            model.addAttribute("cliente", new Cliente());
        } else {
            Cliente cliente = clienteRepository.findByIdentificacion(identificacion).orElseThrow();
            model.addAttribute("cliente", cliente);
        }
        List<Producto> productos = productoService.obtenerProductos();
        EmpleadoDetails empleadoDetails = (EmpleadoDetails) authentication.getPrincipal();
        int totalProductos = productos.size();
        int filas = (totalProductos + 3) / 4; // Calcular filas redondeando hacia arriba
        model.addAttribute("empleado", empleadoDetails.getName());
        model.addAttribute("productos", productoService.obtenerProductos());
        model.addAttribute("filas", filas);
        model.addAttribute("venta", new Venta());
        return "entidades/facturasVentas";
    }

    @GetMapping("/compras")
    public String mostrarFormCompras(Model model, Authentication authentication, @RequestParam(value = "idProveedor", required = false) Long id) {
        List<Producto> productos = new ArrayList<>();

        if (id == null) {
            model.addAttribute("proveedor", new Proveedor());
            productos = productoService.obtenerProductos();
        } else {
            Proveedor proveedor = proveedorRepository.findById(id).orElseThrow();
            model.addAttribute("proveedor", proveedor);
            productos = productoRepository.findByProveedorId(id);
        }

        EmpleadoDetails empleadoDetails = (EmpleadoDetails) authentication.getPrincipal();
        int totalProductos = productos.size();
        int filas = (totalProductos + 3) / 4; // Calcular filas redondeando hacia arriba
        model.addAttribute("empleado", empleadoDetails.getName());
        model.addAttribute("productos", productos);
        model.addAttribute("filas", filas);
        model.addAttribute("venta", new Venta());
        return "entidades/facturasCompras";
    }


    @PostMapping("/nueva-venta")
    @Transactional
    public String nuevaVenta(@ModelAttribute DetallesVentasListWrapper detallesVentasListWrapper,
                             Model model,
                             Principal principal,
                             @RequestParam("total") BigDecimal total,
                             @RequestParam("metodoPago") String metodoPago,
                             @RequestParam(value = "cliente", required = false) Long idCliente, RedirectAttributes rm) {

        try {
            // Paso 1: Crear y guardar la factura
            Factura factura = new Factura();
            factura.setSerial(generarNumeroFactura("venta"));
            factura.setTipo("venta");
            ventaService.guardarFactura(factura);

            // Paso 2: Crear y guardar la venta
            Venta venta = new Venta();
            Empleado empleado = empleadoRepository.findByEmail(principal.getName()).orElseThrow();
            venta.setEmpleado(empleado);
            venta.setTotal(total);
            venta.setMetodoPago(metodoPago);
            venta.setFactura(factura);

            if (idCliente != null) {
                Cliente cliente = clienteService.obtenerPorId(idCliente);
                venta.setCliente(cliente);
            }

            Venta ventaGuardada = ventaService.guardarVenta(venta);

            // Paso 3: Asignar y guardar los detalles de la venta
            List<DetallesVenta> detallesVenta = detallesVentasListWrapper.getDetallesVenta();
            for (DetallesVenta detalle : detallesVenta) {
                detalle.setVenta(ventaGuardada);
                ventaGuardada.getDetallesVenta().add(detalle);
                ventaService.guardarDetalleVenta(detalle);
            }

            // Paso 4: Generar archivo de factura y actualizar la factura
            String rutaArchivo = generarFacturaVenta(factura);
            factura.setRutaArchivo(rutaArchivo);
            ventaService.actualizarFactura(factura);
            ventaGuardada.setFactura(factura);
            ventaService.actualizarVenta(ventaGuardada);

            // Paso 5: Redireccionar con mensaje de éxito
            rm.addFlashAttribute("message", "Factura creada exitosamente");
            return "redirect:/facturas/ventas";

        } catch (RuntimeException e) {
            model.addAttribute("message", "Error: " + e.getMessage());
            return "redirect:/facturas/ventas";
        }

    }

    @PostMapping("/nueva-compra")
    @Transactional
    public String nuevaCompra(@ModelAttribute DetallesComprasListWrapper detallesComprasListWrapper,
                              Model model, Principal principal,
                              @RequestParam("total") BigDecimal total,
                              @RequestParam("metodoPago") String metodoPago,
                              @RequestParam(value = "idProveedor", required = false) Long idProveedor) {

        // Paso 1: Obtener detalles de compra desde el formulario
        List<DetallesCompra> detallesCompra = detallesComprasListWrapper.getDetallesCompra();

        // Paso 2: Crear la compra
        Compra compra = new Compra();
        Empleado empleado = empleadoRepository.findByEmail(principal.getName()).orElseThrow();

        // Paso 3: Crear la factura asociada
        Factura factura = new Factura();
        factura.setSerial(generarNumeroFactura("compra")); // Método para generar el número de factura
        factura.setTipo("compra");

        // Paso 4: Asociar la factura a la compra
        compra.setFactura(factura);
        facturaRepository.save(factura); // Guardar la factura

        // Paso 5: Asignar los detalles de la compra y guardar
        compra.setProveedor(proveedorRepository.findById(idProveedor).orElseThrow());
        compra.setMetodoPago(metodoPago);
        compra.setEmpleado(empleado);
        compra.setTotal(total);

        // Guardar la compra para obtener su ID
        Compra compraGuardada = comprasRepository.save(compra);

        // Paso 6: Guardar cada detalle de la compra
        for (DetallesCompra detalle : detallesCompra) {
            detalle.setCompra(compraGuardada); // Asignar la compra a cada detalle
            compra.getDetallesCompra().add(detalle);
            detallesCompraRepository.save(detalle); // Guardar el detalle de la compra
        }

        // Paso 7: Generar y almacenar el archivo de la factura
        factura.setRutaArchivo(generarFacturaCompra(compraGuardada.getFactura()));
        facturaRepository.save(factura);

        // Paso 8: Redirigir a la vista de compras (o facturas)
        model.addAttribute("mensaje", "Compra y factura creadas exitosamente.");
        return "redirect:/facturas/compras"; // O "redirect:/facturas" si prefieres ir a la vista de facturas
    }

    public String generarNumeroFactura(String tipo) {
        // Obtener la fecha actual en formato yyyy-MM-dd para la búsqueda y ddMMyyyy para la generación del número de factura
        SimpleDateFormat formatoFechaBuscar = new SimpleDateFormat("yyyy-MM-dd");
        String fechaBuscar = formatoFechaBuscar.format(new Date());
        System.out.println(fechaBuscar);

        SimpleDateFormat formatoFecha = new SimpleDateFormat("ddMMyyyy");
        String fecha = formatoFecha.format(new Date());

        // Buscar el último número de factura del día en la base de datos
        String ultimoNumeroFactura = facturaRepository.findUltimoNumeroFacturaPorFecha(fechaBuscar, tipo);

        // Si hay facturas generadas hoy, extraer los últimos 4 dígitos del número; si no, comenzar desde 1
        int contador;
        if (ultimoNumeroFactura != null) {
            // Extraer los últimos 4 caracteres del número de factura
            String contadorStr = ultimoNumeroFactura.substring(ultimoNumeroFactura.length() - 3);
            contador = Integer.parseInt(contadorStr) + 1; // Incrementar el contador
        } else {
            contador = 1; // Iniciar desde 1 si no hay facturas hoy
        }

        // Definir el prefijo (C para compra, V para venta)
        String prefijo = tipo.equals("venta") ? "V" : "C";

        // Generar el número de factura con formato que incluya la fecha y un contador de 4 dígitos
        return prefijo + fecha + String.format("%03d", contador);
    }


   public String generarFacturaVenta (Factura factura) throws IOException {
            Venta venta = ventaRepository.findByFactura(factura).orElseThrow();

           try{
               String filePath = "src/main/resources/static/archivos/facturasVentas/";
               // Verificar si la carpeta existe, si no, crearla
               File directorio = new File(filePath);
               if (!directorio.exists()) {
                   directorio.mkdirs(); // Crear las carpetas necesarias
               }

               String rutaArchivo = filePath + factura.getSerial() + ".pdf";
               PdfWriter writer = new PdfWriter(new FileOutputStream(rutaArchivo));
               PdfDocument pdf = new PdfDocument(writer);
               pdf.addNewPage();

               PdfFont poppinsFont = PdfFontFactory.createFont("src/main/resources/static/fonts/Poppins-Regular.ttf", true);

               // Crear el documento
               Document document = new Document(pdf);

               Color verdeOscuro = new DeviceRgb(34, 139, 34);
               Color verdeClaro = new DeviceRgb(144, 238, 144);

               // Encabezado con logo
               String logoPath = "src/main/resources/static/imagenes/logo_farmacia.png"; // Ruta del logo de la farmacia
               Image logo = new Image(ImageDataFactory.create(logoPath));
               logo.setWidth(60);

               // Formateo de la fecha
               SimpleDateFormat dateFormat = new SimpleDateFormat("yy-MM-dd HH:mm:ss");
               String fechaFormateada = dateFormat.format(venta.getCreacion());

               // Título de la factura
               Paragraph header = new Paragraph("Factura de Venta")
                       .setFont(poppinsFont)
                       .setFontSize(16)
                       .setBold()
                       .setTextAlignment(TextAlignment.CENTER)
                       .setFontColor(verdeOscuro);

               // Información de la farmacia y cliente
               Table infoTable = new Table(UnitValue.createPercentArray(new float[]{1, 2})).useAllAvailableWidth();
               infoTable.addCell(new Cell().add(logo).setBorder(Border.NO_BORDER));
               infoTable.addCell(new Cell().add(new Paragraph("Farmacia Vida Natural").setFont(poppinsFont))
                       .setTextAlignment(TextAlignment.LEFT)
                       .setBorder(Border.NO_BORDER));

               infoTable.addCell(new Cell().add(new Paragraph(fechaFormateada))
                       .setBold().setFontColor(verdeOscuro)
                       .setTextAlignment(TextAlignment.LEFT)
                       .setBorder(Border.NO_BORDER)
                       .setFont(poppinsFont)
                       .setMarginBottom(20));

               infoTable.addCell(new Cell().add(new Paragraph("Factura N°: " + factura.getSerial()))
                       .setBold().setFontColor(verdeOscuro)
                       .setTextAlignment(TextAlignment.RIGHT)
                       .setBorder(Border.NO_BORDER)
                       .setFont(poppinsFont)
                       .setMarginBottom(20));

               document.add(header);

               if (venta.getCliente() != null) {
                   Cell clienteCell = new Cell(1,2)
                           .add(new Paragraph("Cliente: " + venta.getCliente().getNombre() + "     identificacion: " + venta.getCliente().getIdentificacion())
                                   .setFontColor(new DeviceRgb(128, 128, 128))
                                   .setFontSize(10)
                                   .setFont(poppinsFont))
                           .setTextAlignment(TextAlignment.LEFT)
                           .setMarginBottom(20)
                           .setBorder(Border.NO_BORDER); // Sin bordes

                   infoTable.addCell(clienteCell);
               }

               document.add(infoTable);



               DecimalFormatSymbols symbols = new DecimalFormatSymbols();
               symbols.setGroupingSeparator('.');
               symbols.setDecimalSeparator(',');
               DecimalFormat df = new DecimalFormat("#,##0", symbols);

               // Tabla de productos
               Table table = new Table(UnitValue.createPercentArray(new float[]{1, 3, 2, 2, 2,2})).useAllAvailableWidth().setFont(poppinsFont).setFontSize(10).setTextAlignment(TextAlignment.CENTER);
               table.addHeaderCell(new Cell().add(new Paragraph("Código")).setBackgroundColor(verdeClaro).setBold().setBorder(Border.NO_BORDER));
               table.addHeaderCell(new Cell().add(new Paragraph("Producto")).setBackgroundColor(verdeClaro).setBold().setBorder(Border.NO_BORDER));
               table.addHeaderCell(new Cell().add(new Paragraph("Iva")).setBackgroundColor(verdeClaro).setBold().setBorder(Border.NO_BORDER));
               table.addHeaderCell(new Cell().add(new Paragraph("Precio unitario")).setBackgroundColor(verdeClaro).setBold().setBorder(Border.NO_BORDER));
               table.addHeaderCell(new Cell().add(new Paragraph("Cantidad")).setBackgroundColor(verdeClaro).setBold().setBorder(Border.NO_BORDER));
               table.addHeaderCell(new Cell().add(new Paragraph("Subtotal")).setBackgroundColor(verdeClaro).setBold().setBorder(Border.NO_BORDER));

               // Agregar celdas de detalles sin bordes
               for (DetallesVenta detalle : venta.getDetallesVenta()) {
                   table.addCell(new Cell().add(new Paragraph(String.valueOf(detalle.getProducto().getCodigo())).setFontSize(10)).setBorder(Border.NO_BORDER));
                   table.addCell(new Cell().add(new Paragraph(detalle.getProducto().getNombre() + " " + detalle.getProducto().getMarca().getNombre() + " " + detalle.getProducto().getPresentacion()).setFontSize(10)).setBorder(Border.NO_BORDER).setTextAlignment(TextAlignment.LEFT));
                   table.addCell(new Cell().add(new Paragraph(String.valueOf(detalle.getProducto().getIva() + "%")).setFontSize(10)).setBorder(Border.NO_BORDER));
                   table.addCell(new Cell().add(new Paragraph("$ " + df.format(detalle.getPrecioVenta())).setFontSize(10)).setFontColor(verdeOscuro).setBorder(Border.NO_BORDER));
                   table.addCell(new Cell().add(new Paragraph(detalle.getCantidad().toString()).setFontSize(10)).setFontColor(verdeOscuro).setBorder(Border.NO_BORDER));
                   table.addCell(new Cell().add(new Paragraph("$ " + df.format(detalle.getSubtotal())).setFontSize(10)).setFontColor(verdeOscuro).setBorder(Border.NO_BORDER));
               }

               // Agregar fila de total sin bordes
               table.addCell(new Cell(1, 5).add(new Paragraph("Total:").setFontSize(10)).setFontColor(verdeOscuro).setBold().setTextAlignment(TextAlignment.RIGHT).setBorder(Border.NO_BORDER));
               table.addCell(new Cell().add(new Paragraph(df.format(venta.getTotal())).setFontSize(10)).setFontColor(verdeOscuro).setBold().setTextAlignment(TextAlignment.CENTER).setBorder(Border.NO_BORDER));

               // Agregar fila de método de pago sin bordes
               table.addCell(new Cell(1, 5).add(new Paragraph("Método de Pago").setFontSize(10)).setFontColor(verdeOscuro).setBold().setTextAlignment(TextAlignment.RIGHT).setBorder(Border.NO_BORDER));
               table.addCell(new Cell().add(new Paragraph(venta.getMetodoPago()).setFontSize(10)).setFontColor(verdeOscuro).setTextAlignment(TextAlignment.CENTER).setBorder(Border.NO_BORDER));

               // Añadir la tabla al documento
               document.add(table);

               // Pie de página
               Paragraph footer = new Paragraph("Gracias por su compra en Farmacia Vida Natural")
                       .setTextAlignment(TextAlignment.CENTER)
                       .setFontColor(new DeviceRgb(128, 128, 128))
                       .setFontSize(10)
                       .setFont(poppinsFont);

               Paragraph empleado = new Paragraph("Usted fue atendido por: " + venta.getEmpleado().getNombre())
                        .setTextAlignment(TextAlignment.CENTER)
                       .setFontColor(new DeviceRgb(128, 128, 128))
                       .setFontSize(10)
                       .setFont(poppinsFont);

               document.add(empleado);
               document.add(footer);
               document.close();


               return "/archivos/facturasVentas/" + factura.getSerial() + ".pdf";
           } catch (Exception e) {
               throw new RuntimeException(e);
           }

        }

    public String generarFacturaCompra (Factura factura) throws IOException {
        Compra compra = comprasRepository.findByFactura(factura).orElseThrow();

        try{
            String filePath = "src/main/resources/static/archivos/facturasCompras/";
            // Verificar si la carpeta existe, si no, crearla
            File directorio = new File(filePath);
            if (!directorio.exists()) {
                directorio.mkdirs(); // Crear las carpetas necesarias
            }

            String rutaArchivo = filePath + factura.getSerial() + ".pdf";
            PdfWriter writer = new PdfWriter(new FileOutputStream(rutaArchivo));
            PdfDocument pdf = new PdfDocument(writer);
            pdf.addNewPage();

            PdfFont poppinsFont = PdfFontFactory.createFont("src/main/resources/static/fonts/Poppins-Regular.ttf", true);

            // Crear el documento
            Document document = new Document(pdf);

            Color verdeOscuro = new DeviceRgb(34, 139, 34);
            Color verdeClaro = new DeviceRgb(144, 238, 144);

            // Encabezado con logo
            String logoPath = "src/main/resources/static/imagenes/logo_farmacia.png"; // Ruta del logo de la farmacia
            Image logo = new Image(ImageDataFactory.create(logoPath));
            logo.setWidth(60);

            // Formateo de la fecha
            SimpleDateFormat dateFormat = new SimpleDateFormat("yy-MM-dd HH:mm:ss");
            String fechaFormateada = dateFormat.format(compra.getCreacion());

            // Título de la factura
            Paragraph header = new Paragraph("Factura de Compra")
                    .setFont(poppinsFont)
                    .setFontSize(16)
                    .setBold()
                    .setTextAlignment(TextAlignment.CENTER)
                    .setFontColor(verdeOscuro);

            // Información de la farmacia y cliente
            Table infoTable = new Table(UnitValue.createPercentArray(new float[]{1, 2})).useAllAvailableWidth();
            infoTable.addCell(new Cell().add(logo).setBorder(Border.NO_BORDER));
            infoTable.addCell(new Cell().add(new Paragraph("Farmacia Vida Natural").setFont(poppinsFont))
                    .setTextAlignment(TextAlignment.LEFT)
                    .setBorder(Border.NO_BORDER));

            infoTable.addCell(new Cell().add(new Paragraph(fechaFormateada))
                    .setBold().setFontColor(verdeOscuro)
                    .setTextAlignment(TextAlignment.LEFT)
                    .setBorder(Border.NO_BORDER)
                    .setFont(poppinsFont)
                    .setMarginBottom(20));

            infoTable.addCell(new Cell().add(new Paragraph("Factura N°: " + factura.getSerial()))
                    .setBold().setFontColor(verdeOscuro)
                    .setTextAlignment(TextAlignment.RIGHT)
                    .setBorder(Border.NO_BORDER)
                    .setFont(poppinsFont)
                    .setMarginBottom(20));

            document.add(header);


                Cell proveedorCell = new Cell(1,2)
                        .add(new Paragraph("Proveedor: " + compra.getProveedor().getNombre() +"     Contacto: " + compra.getProveedor().getEmail())
                                .setFontColor(new DeviceRgb(128, 128, 128))
                                .setFontSize(10)
                                .setFont(poppinsFont))
                        .setTextAlignment(TextAlignment.LEFT)
                        .setMarginBottom(20)
                        .setBorder(Border.NO_BORDER); // Sin bordes

                infoTable.addCell(proveedorCell);


            document.add(infoTable);



            DecimalFormatSymbols symbols = new DecimalFormatSymbols();
            symbols.setGroupingSeparator('.');
            symbols.setDecimalSeparator(',');
            DecimalFormat df = new DecimalFormat("#,##0", symbols);

            // Tabla de productos
            Table table = new Table(UnitValue.createPercentArray(new float[]{1, 3, 2, 2, 2,2})).useAllAvailableWidth().setFont(poppinsFont).setFontSize(10).setTextAlignment(TextAlignment.CENTER);
            table.addHeaderCell(new Cell().add(new Paragraph("Código")).setBackgroundColor(verdeClaro).setBold().setBorder(Border.NO_BORDER));
            table.addHeaderCell(new Cell().add(new Paragraph("Producto")).setBackgroundColor(verdeClaro).setBold().setBorder(Border.NO_BORDER));
            table.addHeaderCell(new Cell().add(new Paragraph("Iva")).setBackgroundColor(verdeClaro).setBold().setBorder(Border.NO_BORDER));
            table.addHeaderCell(new Cell().add(new Paragraph("Precio unitario")).setBackgroundColor(verdeClaro).setBold().setBorder(Border.NO_BORDER));
            table.addHeaderCell(new Cell().add(new Paragraph("Cantidad")).setBackgroundColor(verdeClaro).setBold().setBorder(Border.NO_BORDER));
            table.addHeaderCell(new Cell().add(new Paragraph("Subtotal")).setBackgroundColor(verdeClaro).setBold().setBorder(Border.NO_BORDER));

// Agregar celdas de detalles sin bordes
            for (DetallesCompra detalle : compra.getDetallesCompra()) {
                table.addCell(new Cell().add(new Paragraph(detalle.getProducto().getCodigo()))
                        .setFontSize(10)
                        .setBorder(Border.NO_BORDER));

                table.addCell(new Cell().add(new Paragraph(detalle.getProducto().getNombre() + " "
                                + detalle.getProducto().getMarca().getNombre() + " "
                                + detalle.getProducto().getPresentacion()))
                        .setFontSize(10)
                        .setBorder(Border.NO_BORDER)
                        .setTextAlignment(TextAlignment.LEFT));

                table.addCell(new Cell().add(new Paragraph(String.valueOf(detalle.getProducto().getIva() + "%")))
                        .setFontSize(10)
                        .setBorder(Border.NO_BORDER));

                table.addCell(new Cell().add(new Paragraph("$ " + df.format(detalle.getPrecioCompra())))
                        .setFontSize(10)
                        .setFontColor(verdeOscuro)
                        .setBorder(Border.NO_BORDER));

                table.addCell(new Cell().add(new Paragraph(detalle.getCantidad().toString()))
                        .setFontSize(10)
                        .setFontColor(verdeOscuro)
                        .setBorder(Border.NO_BORDER));

                table.addCell(new Cell().add(new Paragraph("$ " + df.format(detalle.getSubtotal())))
                        .setFontSize(10)
                        .setFontColor(verdeOscuro)
                        .setBorder(Border.NO_BORDER));
            }

// Agregar fila de total sin bordes
            table.addCell(new Cell(1, 5).add(new Paragraph("Total:"))
                    .setFontSize(10)
                    .setFontColor(verdeOscuro)
                    .setBold()
                    .setTextAlignment(TextAlignment.RIGHT)
                    .setBorder(Border.NO_BORDER));

            table.addCell(new Cell().add(new Paragraph(df.format(compra.getTotal())))
                    .setFontSize(10)
                    .setFontColor(verdeOscuro)
                    .setBold()
                    .setTextAlignment(TextAlignment.CENTER)
                    .setBorder(Border.NO_BORDER));

// Agregar fila de método de pago sin bordes
            table.addCell(new Cell(1, 5).add(new Paragraph("Método de Pago"))
                    .setFontSize(10)
                    .setFontColor(verdeOscuro)
                    .setBold()
                    .setTextAlignment(TextAlignment.RIGHT)
                    .setBorder(Border.NO_BORDER));

            table.addCell(new Cell().add(new Paragraph(compra.getMetodoPago()))
                    .setFontSize(10)
                    .setFontColor(verdeOscuro)
                    .setTextAlignment(TextAlignment.CENTER)
                    .setBorder(Border.NO_BORDER));

            // Añadir la tabla al documento
            document.add(table);




            // Pie de página
            Paragraph footer = new Paragraph("registro de compra realizada por Farmacia Vida Natural")
                    .setTextAlignment(TextAlignment.CENTER)
                    .setFontColor(new DeviceRgb(128, 128, 128))
                    .setFontSize(10)
                    .setFont(poppinsFont);

            Paragraph empleado = new Paragraph("Bajo la responsabilidad de: " + compra.getEmpleado().getNombre())
                    .setTextAlignment(TextAlignment.CENTER)
                    .setFontColor(new DeviceRgb(128, 128, 128))
                    .setFontSize(10)
                    .setFont(poppinsFont);

            document.add(footer);
            document.add(empleado);
            document.close();

            return "/archivos/facturasCompras/" + factura.getSerial() + ".pdf";
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

}


