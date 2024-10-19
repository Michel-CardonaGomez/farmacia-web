// Si hay un mensaje de error, muestra el modal
document.addEventListener('DOMContentLoaded', function () {
    // Obtener el valor inyectado desde el backend
    const showErrorModal = /*[[${showErrorModal}]]*/ false;

    // Si showErrorModal es verdadero, mostrar el modal
    if (showErrorModal === true) {
        var errorModal = new bootstrap.Modal(document.getElementById('errorModal'));
        errorModal.show();
    }
});