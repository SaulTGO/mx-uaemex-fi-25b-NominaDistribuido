let selectedEmployeeIndex = -1;
let empleados = [];

/**
 * Función para formatear números con separadores de miles y decimales
 */
function formatCurrency(value) {
    if (!value || isNaN(value)) return '$0.00';

    const number = parseFloat(value);
    return '$' + number.toLocaleString('es-MX', {
        minimumFractionDigits: 2,
        maximumFractionDigits: 2
    });
}

/**
 * Cargar empleados desde la API al cargar la página
 */
async function cargarEmpleados() {
    try {
        const response = await fetch('http://localhost:4001/api/v1/user/nomina/empleados', {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
            },
            credentials: 'include'
        });

        const data = await response.json();

        if (response.ok && data.success) {
            empleados = data.empleados;
            renderizarTablaEmpleados(empleados);
        } else {
            mostrarError('Error al cargar empleados: ' + data.message);
        }

    } catch (error) {
        console.error('Error:', error);
        mostrarError('Error de conexión al cargar empleados');
    }
}

/**
 * Renderizar la tabla de empleados
 */
function renderizarTablaEmpleados(empleados) {
    const tbody = document.querySelector('.employees-table tbody');
    const emptyState = document.querySelector('.empty-state');

    if (!empleados || empleados.length === 0) {
        tbody.innerHTML = '';
        if (emptyState) emptyState.style.display = 'block';
        return;
    }

    if (emptyState) emptyState.style.display = 'none';

    tbody.innerHTML = empleados.map((empleado, index) => `
        <tr class="employee-row">
            <td>${index + 1}</td>
            <td>${empleado.nombre}</td>
            <td>${empleado.apellidos}</td>
            <td>${empleado.rfc}</td>
            <td>
                <button class="btn-select"
                        data-index="${index}"
                        data-nombre="${empleado.nombre}"
                        data-apellidos="${empleado.apellidos}"
                        data-rfc="${empleado.rfc}"
                        data-correo="${empleado.correo}">
                    <svg width="16" height="16" viewBox="0 0 24 24" fill="none">
                        <path d="M9 11l3 3L22 4" stroke="currentColor" stroke-width="2" stroke-linecap="round"/>
                        <path d="M21 12v7a2 2 0 01-2 2H5a2 2 0 01-2-2V5a2 2 0 012-2h11" stroke="currentColor" stroke-width="2"/>
                    </svg>
                    Seleccionar
                </button>
            </td>
        </tr>
    `).join('');

    // Agregar event listeners a los botones de selección
    document.querySelectorAll('.btn-select').forEach(button => {
        button.addEventListener('click', function() {
            const index = parseInt(this.dataset.index);
            const nombre = this.dataset.nombre;
            const apellidos = this.dataset.apellidos;
            const rfc = this.dataset.rfc;
            const correo = this.dataset.correo;

            selectEmployee(index, nombre, apellidos, rfc, correo);
        });
    });
}

/**
 * Seleccionar un empleado
 */
function selectEmployee(index, nombre, apellidos, rfc, correo) {
    // Remover selección previa
    document.querySelectorAll('.employee-row').forEach(row => {
        row.classList.remove('selected');
    });

    // Marcar fila seleccionada
    document.querySelectorAll('.employee-row')[index].classList.add('selected');

    // Rellenar campos
    document.getElementById('empleadoNombre').value = nombre;
    document.getElementById('empleadoApellidos').value = apellidos;
    document.getElementById('empleadoRFC').value = rfc;
    document.getElementById('empleadoCorreo').value = correo;

    // Limpiar monto anterior
    document.getElementById('montoNomina').value = '';

    // Ocultar resultados previos
    document.getElementById('resultsSection').style.display = 'none';

    // Habilitar botón de calcular
    document.getElementById('btnCalculate').disabled = false;

    // Guardar índice seleccionado
    selectedEmployeeIndex = index;

    // Ocultar alerta
    document.querySelector('.alert-info').style.display = 'none';

    // Enfocar campo de monto
    document.getElementById('montoNomina').focus();
}

/**
 * Calcular nómina
 */
async function calcularNomina(event) {
    event.preventDefault();

    const nombre = document.getElementById('empleadoNombre').value;
    const apellidos = document.getElementById('empleadoApellidos').value;
    const rfc = document.getElementById('empleadoRFC').value;
    const correo = document.getElementById('empleadoCorreo').value;
    const montoNomina = parseFloat(document.getElementById('montoNomina').value);

    // Validación básica
    if (!rfc || !montoNomina || montoNomina <= 0) {
        mostrarError('Por favor seleccione un empleado e ingrese un monto válido');
        return;
    }

    try {
        // Deshabilitar botón mientras se calcula
        const btnCalculate = document.getElementById('btnCalculate');
        btnCalculate.disabled = true;
        btnCalculate.textContent = 'Calculando...';

        const response = await fetch('http://localhost:4001/api/v1/user/nomina/calcular', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            credentials: 'include',
            body: JSON.stringify({
                empleadoNombre: nombre,
                empleadoApellidos: apellidos,
                empleadoRFC: rfc,
                empleadoCorreo: correo,
                montoNomina: montoNomina
            })
        });

        const data = await response.json();

        if (response.ok && data.success) {
            // Mostrar resultados
            mostrarResultados(data);
        } else {
            mostrarError(data.message || 'Error al calcular nómina');
        }

        // Rehabilitar botón
        btnCalculate.disabled = false;
        btnCalculate.innerHTML = `
            <svg width="20" height="20" viewBox="0 0 24 24" fill="none">
                <rect x="4" y="2" width="16" height="20" rx="2" stroke="currentColor" stroke-width="2"/>
                <path d="M8 6h8M8 10h8M8 14h5" stroke="currentColor" stroke-width="2" stroke-linecap="round"/>
                <circle cx="16" cy="17" r="3" stroke="currentColor" stroke-width="2"/>
            </svg>
            Calcular Nómina
        `;

    } catch (error) {
        console.error('Error:', error);
        mostrarError('Error de conexión con el servidor');

        // Rehabilitar botón
        const btnCalculate = document.getElementById('btnCalculate');
        btnCalculate.disabled = false;
        btnCalculate.innerHTML = `
            <svg width="20" height="20" viewBox="0 0 24 24" fill="none">
                <rect x="4" y="2" width="16" height="20" rx="2" stroke="currentColor" stroke-width="2"/>
                <path d="M8 6h8M8 10h8M8 14h5" stroke="currentColor" stroke-width="2" stroke-linecap="round"/>
                <circle cx="16" cy="17" r="3" stroke="currentColor" stroke-width="2"/>
            </svg>
            Calcular Nómina
        `;
    }
}

/**
 * Mostrar resultados del cálculo
 */
function mostrarResultados(data) {
    // Actualizar valores en las tarjetas de resultados
    document.getElementById('resultSalarioBase').textContent = formatCurrency(data.montoNomina);
    document.getElementById('resultISR').textContent = data.isr.toFixed(2) + '%';
    document.getElementById('resultDeducciones').textContent = formatCurrency(data.deducciones);
    document.getElementById('resultNeto').textContent = formatCurrency(data.nominaNeto);

    // Mostrar sección de resultados
    document.getElementById('resultsSection').style.display = 'block';

    // Scroll suave hacia resultados
    document.getElementById('resultsSection').scrollIntoView({
        behavior: 'smooth',
        block: 'nearest'
    });
}

/**
 * Mostrar mensaje de error
 */
function mostrarError(mensaje) {
    alert(mensaje); // Puedes mejorar esto con un modal o toast notification
}

/**
 * Limpiar formulario
 */
function limpiarFormulario() {
    document.getElementById('empleadoNombre').value = '';
    document.getElementById('empleadoApellidos').value = '';
    document.getElementById('empleadoRFC').value = '';
    document.getElementById('empleadoCorreo').value = '';
    document.getElementById('montoNomina').value = '';
    document.getElementById('resultsSection').style.display = 'none';
    document.getElementById('btnCalculate').disabled = true;
    document.querySelector('.alert-info').style.display = 'block';

    // Remover selección de filas
    document.querySelectorAll('.employee-row').forEach(row => {
        row.classList.remove('selected');
    });

    selectedEmployeeIndex = -1;
}

/**
 * Validar monto en tiempo real
 */
function validarMonto() {
    const montoInput = document.getElementById('montoNomina');
    const empleadoNombre = document.getElementById('empleadoNombre').value;

    if (montoInput.value && parseFloat(montoInput.value) > 0 && empleadoNombre && empleadoNombre.trim() !== '') {
        document.getElementById('btnCalculate').disabled = false;
    } else {
        document.getElementById('btnCalculate').disabled = true;
    }
}

// Event Listeners
document.addEventListener('DOMContentLoaded', function() {
    // Cargar empleados al iniciar
    cargarEmpleados();

    // Event listener para el formulario
    const form = document.getElementById('nominaForm');
    if (form) {
        form.addEventListener('submit', calcularNomina);
    }

    // Validar monto en tiempo real
    const montoInput = document.getElementById('montoNomina');
    if (montoInput) {
        montoInput.addEventListener('input', validarMonto);
    }
});