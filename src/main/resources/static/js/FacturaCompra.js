let totalFactura = 0;
let productoCount = 0;

function agregarProducto(button) {
    // Obtener la información del producto
    const productoDiv = button.closest('.producto-info');
    const id = productoDiv.querySelector('.producto-nombre').getAttribute('data-id');
    const nombre = productoDiv.querySelector('.producto-nombre').getAttribute('data-nombre');
    const codigo = productoDiv.querySelector('.producto-nombre').getAttribute('data-codigo');
    const iva = parseFloat(productoDiv.querySelector('.producto-nombre').getAttribute('data-iva'));
    const precio = parseFloat(productoDiv.querySelector('.producto-nombre').getAttribute('data-precio'));
    const cantidadInput = productoDiv.querySelector('.cantidad-input');
    const cantidad = parseInt(cantidadInput.value);
    const inputTotal = document.getElementById('inputTotal')

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
        <input type="hidden" name="detallesCompra[${productoCount}].producto" value="${id}">
        <input type="hidden" name="detallesCompra[${productoCount}].precioCompra" value="${precio}">
        <input type="hidden" name="detallesCompra[${productoCount}].cantidad" value="${cantidad}">
        <input type="hidden" name="detallesCompra[${productoCount}].subtotal" value="${subtotal}">
    `;

    // Añadir la fila a la tabla de la factura
    facturaBody.appendChild(row);
    productoCount++;

    // Actualizar el total en la factura
    document.getElementById('totalFactura').textContent = totalFacturaFormateado;
    inputTotal.value = totalFactura;

    // Reiniciar el campo de cantidad a 1
    cantidadInput.value = 1;
}

// Función para realizar la búsqueda
function buscarProductos() {
    const query = removerTildes(document.getElementById('search-input').value.toLowerCase());  // Convertir la búsqueda a minúsculas y remover tildes
    const productos = document.querySelectorAll('.container-producto');
    let encontrados = 0;

    productos.forEach(producto => {
        const nombreProducto = removerTildes(producto.querySelector('.producto-nombre').textContent.toLowerCase());

        if (nombreProducto.startsWith(query)) {
            producto.style.display = 'block';  // Mostrar producto
            encontrados++;
        } else {
            producto.style.display = 'none';  // Ocultar producto que no coincida
        }
    });

    // Si no hay productos encontrados, mostrar un mensaje
    if (encontrados === 0) {
        const noResultados = document.getElementById('no-resultados');
        if (!noResultados) {
            const mensaje = document.createElement('p');
            mensaje.id = 'no-resultados';
            mensaje.textContent = 'No se encontraron productos que coincidan.';
            document.querySelector('.productos-container').appendChild(mensaje);
        }
    } else {
        const noResultados = document.getElementById('no-resultados');
        if (noResultados) {
            noResultados.remove();
        }
    }
}

// Evento para realizar la búsqueda mientras escribimos
document.getElementById('search-input').addEventListener('input', buscarProductos);