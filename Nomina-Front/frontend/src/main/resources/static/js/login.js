document.getElementById('loginForm').addEventListener('submit', async function(e) {
    e.preventDefault();

    const username = document.getElementById('username').value;
    const password = document.getElementById('password').value;
    const errorDiv = document.getElementById('errorMessage');

    // Validación básica en el cliente
    if (!username || !password) {
        errorDiv.textContent = 'Por favor complete todos los campos';
        return;
    }

    try {
        // Paso 1: Autenticar con backend
        const loginResponse = await fetch('http://localhost:4001/api/v1/auth/login', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            credentials: 'include',
            body: JSON.stringify({
                username: username,
                password: password
            })
        });

        const loginData = await loginResponse.json();

        if (loginResponse.ok && loginData.success) {
            const sessionResponse = await fetch('/create-cookie', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                credentials: 'include',
                body: JSON.stringify({
                    username: username,
                    token: loginData.token || null
                })
            });

            const sessionData = await sessionResponse.json();

            if (sessionResponse.ok && sessionData.success) {
                // Cookie creada exitosamente por el servidor
                console.log('Sesión creada exitosamente');

                if (document.cookie.includes('sessionToken') || document.cookie.includes('JSESSIONID')) {
                    console.log('Cookie de sesión detectada');
                }

                window.location.href = '/user/home';
            } else {
                errorDiv.textContent = sessionData.message || 'Error al crear la sesión';
            }
        } else {
            errorDiv.textContent = loginData.message || 'Error en el inicio de sesión';
        }

    } catch (error) {
        console.error('Error:', error);
        errorDiv.textContent = 'Error de conexión con el servidor';
    }
});