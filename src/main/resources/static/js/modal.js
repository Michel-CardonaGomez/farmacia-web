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
