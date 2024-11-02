let totalFactura = 0;

function agregarProducto(button) {
    // Obtener la información del producto
    const productoDiv = button.closest('.producto-info');
    const nombre = productoDiv.querySelector('.producto-nombre').getAttribute('data-nombre');
    const codigo = productoDiv.querySelector('.producto-nombre').getAttribute('data-codigo');
    const iva = parseFloat(productoDiv.querySelector('.producto-nombre').getAttribute('data-iva'));
    const precio = parseFloat(productoDiv.querySelector('.producto-nombre').getAttribute('data-precio'));
    const cantidadInput = productoDiv.querySelector('.cantidad-input');
    const cantidad = parseInt(cantidadInput.value);

    if (isNaN(cantidad) || cantidad <= 0) {
        alert("Por favor, ingresa una cantidad válida.");
        return;
    }

    // Calcular subtotal
    const subtotal = Math.round(precio * cantidad);
    totalFactura += subtotal;


    // Crear una nueva fila en la tabla de factura
    const facturaBody = document.getElementById('facturaBody');
    const row = document.createElement('tr');

    const precioFormateado = precio.toLocaleString('es-CO');
    const subtotalFormateado = subtotal.toLocaleString('es-CO');
    const totalFacturaFormateado = totalFactura.toLocaleString('es-CO');

    row.innerHTML = `
        <td>${codigo}</td>
        <td>${nombre}</td>
        <td>${iva}%</td>
        <td>${precioFormateado}</td>
        <td>${cantidad}</td>
        <td>${subtotalFormateado}</td>
    `;

    // Añadir la fila a la tabla de la factura
    facturaBody.appendChild(row);

    // Actualizar el total en la factura
    document.getElementById('totalFactura').textContent = totalFacturaFormateado;

    // Reiniciar el campo de cantidad a 1
    cantidadInput.value = 1;
}
