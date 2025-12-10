document.getElementById('registerForm').addEventListener('submit', async function(e) {
    e.preventDefault();

    // Obtener valores del formulario
    const nombre = document.getElementById('nombre').value.trim();
    const apellidos = document.getElementById('apellidos').value.trim();
    const rfc = document.getElementById('rfc').value.trim().toUpperCase();
    const correo = document.getElementById('correo').value.trim();
    const esAdministrador = document.getElementById('esAdministrador').checked;
    const password = document.getElementById('password').value;

    const errorDiv = document.getElementById('errorMessage');
    const successDiv = document.getElementById('successMessage');

    // Limpiar mensajes previos
    if (errorDiv) errorDiv.textContent = '';
    if (successDiv) successDiv.textContent = '';

    // Validación básica en el cliente
    if (!nombre || !apellidos || !rfc || !correo) {
        showError('Por favor complete todos los campos obligatorios');
        return;
    }

    // Validar que el nombre solo contenga letras mayúsculas
    const nombrePattern = /^[A-ZÑÁÉÍÓÚ\s]+$/;
    if (!nombrePattern.test(nombre)) {
        showError('El nombre solo debe contener letras mayúsculas');
        return;
    }

    if (!nombrePattern.test(apellidos)) {
        showError('Los apellidos solo deben contener letras mayúsculas');
        return;
    }

    // Validar formato de RFC
    const rfcPattern = /^[A-ZÑ&]{4}\d{6}[A-Z0-9]{3}$/;
    if (!rfcPattern.test(rfc)) {
        showError('RFC inválido. Formato: 4 letras + 6 números + 3 alfanuméricos');
        return;
    }

    // Validar contraseña SOLO si es administrador
    if (esAdministrador) {
        if (!password || password.trim() === '') {
            showError('La contraseña es obligatoria para administradores');
            return;
        }

        const passwordPattern = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[!@#$%^&])[A-Za-z\d!@#$%^&]{8,}$/;
        if (!passwordPattern.test(password)) {
            showError('La contraseña debe tener mínimo 8 caracteres: 1 mayúscula, 1 minúscula, 1 número y 1 símbolo (!@#$%^&)');
            return;
        }
    }
    // Si NO es administrador, la contraseña se envía como null (ya está en el fetch)

    try {
        // Deshabilitar botón de submit para evitar doble envío
        const submitButton = document.querySelector('.btn-submit');
        submitButton.disabled = true;
        submitButton.textContent = 'Registrando...';

        // Enviar petición al backend API
        const response = await fetch('http://localhost:4001/api/v1/auth/register', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({
                nombre: nombre,
                apellidos: apellidos,
                rfc: rfc,
                correo: correo,
                esAdministrador: esAdministrador,
                password: esAdministrador ? password : null
            })
        });

        const data = await response.json();

        if (response.ok && data.success) {
            // Registro exitoso
            showSuccess(data.message || 'Usuario registrado correctamente');

            // Limpiar formulario
            document.getElementById('registerForm').reset();
            document.getElementById('esAdministrador').checked = false;
            togglePassword(); // Ocultar campo de contraseña

            // Opcional: Redirigir después de 2 segundos
            setTimeout(() => {
                window.location.href = '/user/home';
            }, 2000);

        } else {
            // Error en el registro
            showError(data.message || 'Error al registrar usuario');
        }

        // Rehabilitar botón
        submitButton.disabled = false;
        submitButton.innerHTML = `
            <svg width="18" height="18" viewBox="0 0 24 24" fill="none">
                <path d="M19 21H5a2 2 0 01-2-2V5a2 2 0 012-2h11l5 5v11a2 2 0 01-2 2z" stroke="currentColor" stroke-width="2"/>
                <path d="M17 21v-8H7v8M7 3v5h8" stroke="currentColor" stroke-width="2"/>
            </svg>
            Registrar
        `;

    } catch (error) {
        console.error('Error:', error);
        showError('Error de conexión con el servidor');

        // Rehabilitar botón
        const submitButton = document.querySelector('.btn-submit');
        submitButton.disabled = false;
        submitButton.innerHTML = `
            <svg width="18" height="18" viewBox="0 0 24 24" fill="none">
                <path d="M19 21H5a2 2 0 01-2-2V5a2 2 0 012-2h11l5 5v11a2 2 0 01-2 2z" stroke="currentColor" stroke-width="2"/>
                <path d="M17 21v-8H7v8M7 3v5h8" stroke="currentColor" stroke-width="2"/>
            </svg>
            Registrar
        `;
    }
});

/**
 * Función para mostrar mensajes de error
 */
function showError(message) {
    const errorDiv = document.getElementById('errorMessage');
    if (errorDiv) {
        errorDiv.textContent = message;
        errorDiv.style.display = 'block';
    }

    const successDiv = document.getElementById('successMessage');
    if (successDiv) {
        successDiv.style.display = 'none';
    }
}

/**
 * Función para mostrar mensajes de éxito
 */
function showSuccess(message) {
    const successDiv = document.getElementById('successMessage');
    if (successDiv) {
        successDiv.textContent = message;
        successDiv.style.display = 'block';
    }

    const errorDiv = document.getElementById('errorMessage');
    if (errorDiv) {
        errorDiv.style.display = 'none';
    }
}

/**
 * Función para mostrar/ocultar campo de contraseña
 */
function togglePassword() {
    const checkbox = document.getElementById('esAdministrador');
    const passwordField = document.getElementById('passwordField');
    const passwordInput = document.getElementById('password');

    if (checkbox.checked) {
        passwordField.classList.add('active');
        passwordInput.required = true;
    } else {
        passwordField.classList.remove('active');
        passwordInput.required = false;
        passwordInput.value = '';
    }
}

// Inicializar el estado al cargar la página
document.addEventListener('DOMContentLoaded', function() {
    togglePassword();
});