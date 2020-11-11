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
    val epsilonP = 0.0001
    /** Initial system temperature */
    var T = 8.0
    /** Upper bound for iterations when calculating a batch */
    val L = 2000
    /** Cooling factor that determines how slow or fast the temperature [T] it's going to decrease */
    val phi = 0.9
    /** Percentage */
    val P = 0.5
    /**  Value of accepted neighbors used when calculating the initial temperature. */
    val vecinosAceptados = 100
    /** Maximum number of attempts when calculating a batch. */
    val maxIteraciones = L * 42

    private var mejorSolucionActual: Pair<Solucion, Double> = Pair(solucionActual, g.f(solucionActual))

    fun calculaLote(T: Double, s: Solucion): Pair<Double, Solucion> {
        var c = 0
        var r = 0.0
        var i = 0
        var s = s
        while (c < L && i < maxIteraciones) {
            var sPrima = s.generaVecino()
            if (g.f(sPrima) <= g.f(s) + T) {
                s = sPrima
                c++
                r += g.f(s)
            } else {
                i++
            }
        }
        return Pair(r / L, s)
    }

    fun aceptacionPorUmbrales(){
        var min = Double.MAX_VALUE
        var p = 0.0
        while(T > epsilon){
            var q = Double.POSITIVE_INFINITY
            while(p <= q){
                q = p
                val ps = calculaLote(T, solucionActual)
                p = ps.first
                solucionActual = ps.second
                /** Hacemos que siempre imprima la mejor solución*/
                if (min > g.f(solucionActual)) {
                    mejorSolucionActual = Pair(solucionActual, g.f(solucionActual))
                    min = g.f(solucionActual)
                    println("E: " + g.f(solucionActual))
                }
            }
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
                T = 2 * T
                p = porcentajeAceptado(s, T)
            }
            T1 = T / 2
            T2 = T
        }
        else{
            while(p > P){
                T = T / 2
                p = porcentajeAceptado(s, T)
            }
            T1 = T
            T2 = 2 * T
        }
        T = busquedaBinaria(s, T1, T2, P)
    }

    fun porcentajeAceptado(s: Solucion, T: Double): Double{
        var c = 0.0
        var nuevaSolucion: Solucion
        var current: Solucion = s

        var currentCost: Double
        var newCost: Double

        for (i in 0 until vecinosAceptados) {
            nuevaSolucion = current.generaVecino()
            currentCost = g.f(current)
            newCost = g.f(nuevaSolucion)
            if (newCost <= currentCost + T) {
                c++
                current = nuevaSolucion
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
    fun ruta(): ArrayList<Int> = mejorSolucionActual.first.ruta

    /**
     *
     *
     * @return the evaluation of the path
     */
    fun evaluacion(): Double = mejorSolucionActual.second

    /**
     * TODO
     *
     * @return
     */
    fun esFactible(): Boolean = mejorSolucionActual.second <= 1
}