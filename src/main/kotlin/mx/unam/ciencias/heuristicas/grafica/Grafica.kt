package mx.unam.ciencias.heuristicas.grafica


import kotlin.math.*

/**
 * Implementación de la gráfica ponderada.
 *
 *
 */
class Grafica<T, E> {

    /** Distancia Máxima*/
    fun maxD(): Double = aristas.peek().weight

    /** Conversión a radianes */
    fun rad(g: Double): Double = (g * Math.PI) / 180

    /** Distancia Natural*/
    fun d(u: Ciudad, v: Ciudad): Double {
        val latU = rad(u!!.latitude)
        val lonU = rad(u!!.longitude)
        val latV = rad(v!!.latitude)
        val lonV = rad(v!!.longitude)
        val a = sin((latV - latU) / 2).pow(2) +
                cos(latU) * cos(latV) * sin((lonV - lonU) / 2).pow(2)
        val r = 6373000
        val c = 2 * atan2(sqrt(a), sqrt(1 - a))
        return r * c
    }

    /** Función de peso aumentada
    private fun ws(u: Int, v: Int): Double =
    if (grafica.tieneArista(u, v))
    grafica.pesoArista(u, v)!!
    else
    d(u, v) * maxD()
     */
}