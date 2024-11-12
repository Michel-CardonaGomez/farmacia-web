document.addEventListener("DOMContentLoaded", function() {
    const toggleButtons = document.querySelectorAll('.toggle-btn'); // Todos los botones de "menos" y "más"
    const chartContainers = document.querySelectorAll('.chart-container'); // Todas las gráficas

    toggleButtons.forEach((button, index) => {
        button.addEventListener('click', function() {
            // Si la gráfica está abierta, la cerramos y cambiamos el texto del botón a "+"
            if (chartContainers[index].classList.contains('show')) {
                chartContainers[index].classList.remove('show');
                button.textContent = "+"; // Cambia el botón a "más"
            } else {
                // Cerramos todas las gráficas abiertas
                chartContainers.forEach(container => {
                    container.classList.remove('show');
                });

                // Abrimos la gráfica correspondiente y cambiamos el texto del botón a "-"
                chartContainers[index].classList.add('show');
                button.textContent = "-"; // Cambia el botón a "menos"
            }

            // Restablece los otros botones a "más"
            toggleButtons.forEach((btn, i) => {
                if (i !== index) {
                    btn.textContent = "+"; // Restablece los otros botones a "+"
                }
            });
        });
    });
});
