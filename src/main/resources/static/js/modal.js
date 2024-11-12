// Función para alternar la visibilidad del formulario de creación
function toggleCreateForm() {
    const createFormContainer = document.getElementById("create-form-container");

    // Ocultar el formulario de edición si está visible
    if (createFormContainer.style.display === "flex") {
        createFormContainer.style.display = "none";
    }

    // Alternar el formulario de creación
    createFormContainer.style.display = createFormContainer.style.display === "flex" ? "none" : "flex";
}

// Función para cerrar ambos formularios
function toggleForm() {
    const createFormContainer = document.getElementById("create-form-container");

    // Cerrar ambos formularios
    if (createFormContainer.style.display === "flex") {
        createFormContainer.style.display = "none";
    }
}

// Asegúrate de que los contenedores de los formularios estén ocultos al cargar la página
document.addEventListener("DOMContentLoaded", function() {
    const createFormContainer = document.getElementById("create-form-container");
    const editFormContainer = document.getElementById("edit-form-container");

    createFormContainer.style.display = "none";
    editFormContainer.style.display = "none";
});

const containerCompras = document.getElementById("container-compras");
const btnMostrarVentas = document.getElementById("mostar-btn-ventas");
const btnMostrarCompras = document.getElementById("mostar-btn-compras");
const containerVentas = document.getElementById("container-ventas");

function toggleDisplay(containerToShow, btnToUpdate, containerToHide, btnToReset) {
    // Oculta el contenedor opuesto y actualiza su botón si está visible
    if (containerToHide.style.display === "grid") {
        containerToHide.style.display = "none";
        btnToReset.innerHTML = "<i class='fas fa-plus'></i>"; // Resetea el ícono del botón del contenedor oculto
    }
    // Alterna el contenedor actual
    containerToShow.style.display = containerToShow.style.display === "grid" ? "none" : "grid";
    // Cambia el icono del botón basado en la visibilidad del contenedor
    btnToUpdate.innerHTML = containerToShow.style.display === "grid" ? "<i class='fas fa-times'></i>" : "<i class='fas fa-plus'></i>";
}

function mostrarVentas() {
    toggleDisplay(containerVentas, btnMostrarVentas, containerCompras, btnMostrarCompras);
}

function mostrarCompras() {
    toggleDisplay(containerCompras, btnMostrarCompras, containerVentas, btnMostrarVentas);
}
