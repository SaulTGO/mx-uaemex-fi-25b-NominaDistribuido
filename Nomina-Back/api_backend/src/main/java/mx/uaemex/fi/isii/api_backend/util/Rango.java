package mx.uaemex.fi.isii.api_backend.util;

/**
 * For setting and describing useful range attributes, lower Boundary, upper boundary and it's isr
 * @see mx.uaemex.fi.isii.api_backend.util.TablaISR
 */
public class Rango {

 double limiteInferior;
    double limiteSuperior;
    double isr;

    public Rango(double limiteInferior, double limiteSuperior, double isr) {
        this.limiteInferior = limiteInferior;
        this.limiteSuperior = limiteSuperior;
        this.isr = isr;
    }

    /**
     *
     * @param nomina double nomina value
     * @return {@code true} if nomina is in range
     * @see TablaISR, buscarRango()
     */
    public boolean estaEnRango(double nomina) {
        return nomina >= limiteInferior && nomina <= limiteSuperior;
    }

    public double getIsr() {
        return isr;
    }
}
