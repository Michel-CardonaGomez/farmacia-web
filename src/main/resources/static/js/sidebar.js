document.getElementById('open_btn').addEventListener('click', function () {
    document.getElementById('sidebar').classList.toggle('open-sidebar');
});

// Función para filtrar la tabla
function filterTable() {
    // Obtener el valor del input de búsqueda
    let input = document.getElementById('search-input');
    let filter = input.value.toLowerCase().normalize("NFD").replace(/[\u0300-\u036f]/g, ""); // Normalizar el texto para eliminar tildes

    // Obtener la tabla y todas las filas (excepto el encabezado)
    let table = document.getElementById('data-table');
    let rows = table.getElementsByTagName('tr');

    // Recorrer todas las filas y ocultar las que no coincidan con el filtro
    for (let i = 1; i < rows.length; i++) { // Empezamos en 1 para evitar el encabezado
        let cells = rows[i].getElementsByTagName('td');

        // Verificar si las celdas 1 (Cédula) y 2 (Nombre) contienen el valor de búsqueda
        let cedulaCell = cells[1] ? cells[1].textContent.toLowerCase().normalize("NFD").replace(/[\u0300-\u036f]/g, "") : '';
        let nombreCell = cells[2] ? cells[2].textContent.toLowerCase().normalize("NFD").replace(/[\u0300-\u036f]/g, "") : '';

        // Mostrar u ocultar la fila según si coincide con el filtro
        // Para la cédula, usamos "startsWith" para verificar el inicio
        if (cedulaCell.startsWith(filter) || nombreCell.includes(filter)) {
            rows[i].style.display = ''; // Mostrar fila
        } else {
            rows[i].style.display = 'none'; // Ocultar fila
        }
    }
}


    const items = document.querySelectorAll("#sidebar .side-item");

    items.forEach(item => {
        item.addEventListener("click", function() {
            // Remover la clase 'item-activo' de todos los elementos
            items.forEach(i => i.classList.remove("active"));
            // Agregar la clase 'item-activo' al elemento seleccionado
            item.classList.add("active");
        });
    });


