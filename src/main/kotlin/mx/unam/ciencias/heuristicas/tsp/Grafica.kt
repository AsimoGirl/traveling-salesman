package mx.unam.ciencias.heuristicas.tsp

import mx.unam.ciencias.heuristicas.DAO
import mx.unam.ciencias.heuristicas.modelo.Ciudad
import mx.unam.ciencias.heuristicas.tsp.Solucion
import java.util.*
import kotlin.math.*

/**
 * Implementación de los métodos usados de la gráfica.
 *
 *
 */
class Grafica(ciudades: ArrayList<Ciudad>){
    private val numVertices = ciudades.size
    private val ciudades = ciudades
    val distanciaMax = maxD()
    private val distancias = getDistancias()
    val normalizador = n()
    private val matrizAdj = ws()

    /** Devuelve todas las posibles distancias */
    fun getDistancias(): ArrayList<Double>{
        val distancias = arrayListOf<Double>()
        var u :Int
        var v: Int
        var distActual: Double
        for (i in 0 until numVertices-1){
            u = ciudades[i].id
            for(j in 1 until numVertices){
                v = ciudades[j].id
                distActual = DAO.getDistancia(u, v)
                if (distActual > -1){
                    distancias.add(distActual)
                }
            }
        }
        /**
        while (i < numVertices - 1) {
            u = ciudades[i].id
            j = i + 1
            while(j < numVertices){
                v = ciudades[j].id
                distActual = DAO.getDistancia(u, v)
                if (distActual > -1){
                    distancias.add(distActual)
                }
                j++
            }
            i++
        }*/
        return distancias
    }

    /** Obtiene la distancia Máxima*/
    fun maxD(): Double{
        return Collections.max(distancias)
    }

    /** Obtiene la normalización*/
    fun n(): Double{
        var normalizador = 0.0
        Collections.sort(distancias, Collections.reverseOrder())
        for (i in 0 until distancias.size - 2) {
            normalizador += distancias[i];
        }
        return normalizador
    }

    /** Conversión a radianes */
    private fun rad(g: Double): Double = (g * Math.PI) / 180

    /** Distancia Natural*/
    fun d(u: Ciudad, v: Ciudad): Double {
        val latU = rad(u.latitude)
        val lonU = rad(u.longitude)
        val latV = rad(v.latitude)
        val lonV = rad(v.longitude)
        val a = sin((latV - latU) / 2).pow(2) +
                cos(latU) * cos(latV) * sin((lonV - lonU) / 2).pow(2)
        val r = 6373000
        val c = 2 * atan2(sqrt(a), sqrt(1 - a))
        return r * c
    }

    /** Se llena la matriz de adyacencia y el diccionario de indices */
    /** Usamos la función de peso aumentada para definir la matriz de adjacencia*/
    fun ws():Array<DoubleArray>{
        val matrizAdj = Array(numVertices) { DoubleArray(numVertices) }
        var natural: Double
        var distancia: Double
        for (i in 0 until numVertices) {
            val u: Ciudad = ciudades[i]
            for (j in 1 until numVertices) {
                val v: Ciudad = ciudades[j]
                distancia = DAO.getDistancia(u.id, v.id)
                if (distancia > -1) {
                    matrizAdj[i][j] = distancia
                    matrizAdj[j][i] = distancia
                } else {
                    natural = d(u, v)
                    matrizAdj[i][j] = distanciaMax * natural
                    matrizAdj[j][i] = distanciaMax * natural
                }
            }
        }
        return matrizAdj
    }

    /** Función de costo*/
    fun f(v: Solucion) :Double{
        val ruta = v.ruta
        var suma = 0.0
        var indexU: Int
        var indexV: Int
        for (i in 0 until ruta.size - 1) {
            indexU = ciudades.indexOf(i)
            indexV = ciudades.indexOf(i+1)
            suma += matrizAdj[indexU][indexV]
        }
        return suma / normalizador
    }
}