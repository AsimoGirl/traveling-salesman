package mx.unam.ciencias.heuristicas.umbrales

import mx.unam.ciencias.heuristicas.tsp.Grafica
import mx.unam.ciencias.heuristicas.tsp.Solucion
import kotlin.math.abs

class Heuristica(g: Grafica, solucionInicial: Solucion) {

    val g = g
    var solucionActual = solucionInicial
    /** Epsilon used in Threshold Accepting heuristic */
    val epsilon = 0.001
    /** Epsilon used in the initial temperature algorithm */
    val epsilonP = 0.001
    /** Initial system temperature */
    var T = 8.0
    /** Upper bound for iterations when calculating a batch */
    val L = 2000
    /** Cooling factor that determines how slow or fast the temperature [T] it's going to decrease */
    val phi = 0.9
    /** Percentage */
    val P = 0.5
    /**  Value of accepted neighbors used when calculating the initial temperature. */
    val vecinosAceptados = 1000
    /** Maximum number of attempts when calculating a batch. */
    val maxIteraciones = L * 4


    var mejorSolucionActual: Solucion = solucionActual
    fun calculaLote(T: Double): Double {
        var c = 0
        var r = 0.0
        var i = 0
        while (c < L && i < maxIteraciones) {
            val par = solucionActual.generaVecino()
            val indices = par.first
            val nuevaRuta = par.second
            val indiceI = indices.first
            val indiceJ = indices.second
            val rutaActual = solucionActual.ruta
            val costoAntiguo = solucionActual.costo
            val nuevoCosto = g.getCostoOptimizado(indiceI, indiceJ, costoAntiguo, rutaActual)

            if (nuevoCosto <= costoAntiguo + T) {
                solucionActual.costo = nuevoCosto
                solucionActual.ruta = rutaActual
                val costoMinimo = mejorSolucionActual.costo
                if(nuevoCosto <= costoMinimo){
                    mejorSolucionActual.ruta = solucionActual.ruta
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

    fun aceptacionPorUmbrales(){
        var min = Double.MAX_VALUE
        var p = 0.0
        while(T > epsilon){
            var q = Double.POSITIVE_INFINITY
            while(p <= q){
                q = p
                p = calculaLote(T)
            }
            println("E: ${mejorSolucionActual.costo}")
            T *= phi
        }
    }

    fun temperaturaInicial(){
        var T1: Double
        var T2: Double
        var s = solucionActual
        var p = porcentajeAceptado(s, T)
        if (abs(P - p) < epsilonP){
            return;
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
    }

    fun porcentajeAceptado(s: Solucion, T: Double): Double{
        var c = 0.0
        var current = s

        var currentCost: Double
        var newCost: Double

        for (i in 0 until vecinosAceptados) {
            val parVecino = current.generaVecino()
            var nuevaSolucion = parVecino.second
            val indicesVecino = parVecino.first
            val indiceI = indicesVecino.first
            val indiceJ = indicesVecino.second
            currentCost = solucionActual.costo
            val currentRute = solucionActual.ruta
            newCost = g.getCostoOptimizado(indiceI, indiceJ,  currentCost, currentRute)
            if (newCost <= currentCost + T) {
                current.costo = newCost
                current.ruta = nuevaSolucion
                c++
            }
        }

        return c / vecinosAceptados
    }

    private tailrec fun busquedaBinaria(solution: Solucion, t1: Double, t2: Double, probability: Double): Double {
        val tMid = (t1 + t2) / 2
        if (t2 - t1 < epsilonP)
            return tMid

        val p = porcentajeAceptado(solution, tMid)
        if (abs(probability - p) < epsilonP)
            return tMid

        return if (p > probability)
            busquedaBinaria(solution, t1, tMid, probability)
        else
            busquedaBinaria(solution, tMid, t2, probability)
    }

    /**
     * Returns the path ob the best solution found so far.
     *
     * @return a list of the solution.
     */
    fun ruta(): ArrayList<Int> = mejorSolucionActual.ruta

    /**
     *
     *
     * @return the evaluation of the path
     */
    fun evaluacion(): Double = mejorSolucionActual.costo

    /**
     * TODO
     *
     * @return
     */
    fun esFactible(): Boolean = mejorSolucionActual.costo <= 1
}