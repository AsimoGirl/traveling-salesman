package mx.unam.ciencias.heuristicas.umbrales

import mx.unam.ciencias.heuristicas.tsp.Grafica
import mx.unam.ciencias.heuristicas.tsp.Solucion
import kotlin.math.abs

/**
 * Declaramos nuestra clase Heuristica que realizará el recocido simulado con aceptación por umbrales
 *
 * @property g La grafica en la que estaremos trabajando
 * @property solucionInicial La primera posible solucion
 */
class Heuristica(g: Grafica, solucionInicial: Solucion) {
    /** Gráfica que estaremos utilizando*/
    private val g = g
    /** Variable que representará la solución que se tenga en el momento*/
    private var solucionActual = solucionInicial
    /** Epsilon usada en la heurística de aceptación por umbrales */
    private val epsilon = 0.00001
    /** Epsilon usada en el algoritmo de obtención de la temperatura inicial */
    private val epsilonP = 0.00001
    /** Temperatura inicial del sistema */
    private var T = 8.0
    /** Límite superior para las iteraciones al calcula un lote */
    private val L = 2000
    /** Factor de enfriamiento que determinará que tan rápido descenderá la temperatura T */
    private val phi = 0.95
    /** Porcentaje de soluciones vecinas*/
    private val P = 0.9
    /**  Valor de vecinos aceptados usados para calcular la temperatura inicial. */
    private val vecinosAceptados = 2000
    /** Número máximo de iteraciones al calcular un lote */
    private val maxIteraciones = L * 21
    /** Variable que irá guardando la mejor solución del sistema */
    private var mejorSolucionActual: Solucion = solucionInicial

    /**
     * Función que sigue el algoritmo no determinista para calcular un lote
     * @param T La temperatura del sistema en esa iteración
     * @return El promedio de las soluciones aceptadas
     */
    fun calculaLote(T: Double): Double {
        var c = 0
        var r = 0.0
        var i = 0
        while (c < L && i < maxIteraciones) {
            //Se obtiene un vecino aleatorio de la solución
            val par = solucionActual.generaVecino()
            val indices = par.first
            val nuevaRuta = par.second
            val indiceI = indices.first
            val indiceJ = indices.second
            val rutaActual = solucionActual.ruta
            val costoAntiguo = solucionActual.costo
            //Para optimizar la forma de obtener el costo del vecino, llamamos a costoOptimizado
            val nuevoCosto = g.getCostoOptimizado(indiceI, indiceJ, costoAntiguo, rutaActual)
            //Actualizamos la mejor solución aceptada en caso que el costo del vecino sea mejor
            if (nuevoCosto <= costoAntiguo + T) {
                solucionActual.costo = nuevoCosto
                solucionActual.ruta = nuevaRuta
                val costoMinimo = mejorSolucionActual.costo
                if(nuevoCosto <= costoMinimo){
                    mejorSolucionActual.ruta = nuevaRuta
                    mejorSolucionActual.costo = nuevoCosto
                }
                c++
                r += nuevoCosto
            } else {
                i++
            }
        }
        return r / L
    }

    /**
     * Función que sigue el algoritmo de aceptacion por umbrales
     * No recibe ningún parametro ya que usará la temperatura y solucion
     * dentro del sistema, las cuales irá actualizando
     */
    fun aceptacionPorUmbrales(){
        var p = 0.0
        while(T > epsilon) {
            var q = Double.POSITIVE_INFINITY
            //Mientras no haya equilibrio térmico
            while (p <= q && q >= epsilon) {
                q = p
                p = calculaLote(T)
                println("E: ${mejorSolucionActual.costo}")
            }
            //Se disminuye la temperatura multiplicándola con el factor de enfriamiento
            T *= phi
        }
    }

    /**
     * Función que crea la temperatura inicial del sistema
     * @return T La temperatura del sistema que aumenta la probabilidad de que la heurística
     * pueda desplazarse rápidamente
     */
    fun temperaturaInicial() :Double{
        val T1: Double
        val T2: Double
        val s = solucionActual
        var p = porcentajeAceptado(s, T)
        if (abs(P - p) < epsilonP){
            return T
        }
        if(p < P){
            while(p < P){
                T *= 2
                p = porcentajeAceptado(s, T)
            }
            T1 = T / 2
            T2 = T
        }
        else{
            while(p > P){
                T /= 2
                p = porcentajeAceptado(s, T)
            }
            T1 = T
            T2 = 2 * T
        }
        T = busquedaBinaria(s, T1, T2, P)
        return T
    }

    /**
     * Función que obtiene el porcentaje de soluciones vecinas aceptadas dada una solución
     * @param T Temperatura del sistema
     * @param s La solución cuyos vecinos se evaluarán
     * @return El promedio de soluciones aceptadas
     */
    fun porcentajeAceptado(s: Solucion, T: Double): Double{
        var c = 0.0
        val actual = s
        var costoActual: Double
        var newCost: Double
        for (i in 0 until vecinosAceptados) {
            val parVecino = actual.generaVecino()
            val nuevaSolucion = parVecino.second
            val indicesVecino = parVecino.first
            val indiceI = indicesVecino.first
            val indiceJ = indicesVecino.second
            costoActual = solucionActual.costo
            val rutaActual = solucionActual.ruta
            newCost = g.getCostoOptimizado(indiceI, indiceJ,  costoActual, rutaActual)
            if (newCost <= costoActual + T) {
                actual.costo = newCost
                actual.ruta = nuevaSolucion
                c++
            }
        }
        return c / vecinosAceptados
    }

    /**
     * Función auxiliar que realiza búsqueda binaria para la obtención de la temperatura del sistema
     * @param solucion La solución cuyo porcentaje de soluciones aceptadas se usará
     * @param t1 Primera temperatura
     * @param t2 Segunda temperatura
     * @param probability La cota mayor de la probabilidad
     * @return La temperatura media entre esas dos temperaturas tomando en cuenta la probabilidad
     */
    fun busquedaBinaria(solucion: Solucion, t1: Double, t2: Double, probability: Double): Double {
        val tMid = (t1 + t2) / 2
        if (t2 - t1 < epsilonP)
            return tMid
        val p = porcentajeAceptado(solucion, tMid)
        if (abs(probability - p) < epsilonP)
            return tMid
        return if (p > probability)
            busquedaBinaria(solucion, t1, tMid, probability)
        else
            busquedaBinaria(solucion, tMid, t2, probability)
    }

    /**
     * Función que regresa la ruta de la mejor solución del sistema
     * @return La lista de ids de la mejor solución del sistema
     */
    fun ruta(): ArrayList<Int> = mejorSolucionActual.ruta

    /**
     * Función que regresa el costo de la mejor solución del sistema
     * @return El costo de la mejor solución del sistema
     */
    fun evaluacion(): Double = mejorSolucionActual.costo

    /**
     * Función que regresa si la mejor solución del sistema es factible o no
     * @return Un booleano que nos dice si es factible o no la solución
     */
    fun esFactible(): Boolean = mejorSolucionActual.costo <= 1
}