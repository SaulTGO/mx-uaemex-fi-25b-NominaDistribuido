/**
 * Función para manejar el cierre de sesión
 */
async function logout() {
    try {
        const response = await fetch('/logout', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            credentials: 'include' // Importante para enviar las cookies
        });

        const data = await response.json();

        if (response.ok && data.success) {
            console.log('Sesión cerrada exitosamente');
            // Redirigir al login
            window.location.href = '/login';
        } else {
            console.error('Error al cerrar sesión:', data.message);
            alert('Error al cerrar sesión: ' + data.message);
        }

    } catch (error) {
        console.error('Error de conexión:', error);
        alert('Error de conexión con el servidor');
    }
}

// Si tienes un botón de logout en tu HTML, agrega el event listener
document.addEventListener('DOMContentLoaded', function() {
    const logoutButton = document.getElementById('logoutButton');

    if (logoutButton) {
        logoutButton.addEventListener('click', function(e) {
            e.preventDefault();
            logout();
        });
    }
});