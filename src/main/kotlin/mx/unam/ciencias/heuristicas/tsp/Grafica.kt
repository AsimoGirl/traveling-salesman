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
class Grafica(val ciudades: ArrayList<Ciudad>, citiesIdsString: String){
    private val numVertices = ciudades.size
    private val todasDistancias = DAO(citiesIdsString).getDistancia()
    private val distancias = getDistancias()
    val distanciaMax = maxD()
    val normalizador = n()
    val matrizAdj = ws()

    fun getValorLista(idU: Int, idV:Int): Double{
        for (i in todasDistancias){
            if ((i.idCity1 == idU && i.idCity2 == idV) || (i.idCity2 == idU && i.idCity1 == idV)){
                return i.distance
            }
        }
        return -1.0
    }

    fun getPeso(idI: Int, idU: Int): Double{
        val i = ciudades.indexOfFirst{ it.id == idI}
        val j = ciudades.indexOfFirst{ it.id == idU}
        return matrizAdj[i][j]
    }

    /** Devuelve todas las posibles distancias */
    fun getDistancias(): ArrayList<Double>{
        val todasDistancias = arrayListOf<Double>()
        var u :Int
        var v: Int
        var i = 0
        var j = 1
        var distActual: Double
        while (i < numVertices - 1) {
            u = ciudades[i].id
            j = i + 1
            while(j < numVertices){
                v = ciudades[j].id
                distActual = getValorLista(u, v)
                if (distActual > -1){
                    todasDistancias.add(distActual)
                }
                j++
            }
            i++
        }
        return todasDistancias
    }

    /** Obtiene la distancia Máxima*/
    fun maxD(): Double{
        return Collections.max(distancias)
    }

    /** Obtiene la normalización*/
    fun n(): Double{
        var normalizador = 0.0
        var a = 0
        Collections.sort(distancias, Collections.reverseOrder())
        for (i in distancias) {
            if (a >= numVertices-1)
                break;
            normalizador += i
            a++
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
                distancia = getValorLista(u.id, v.id)
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
    fun f(v: ArrayList<Int>) :Double{
        val ruta = v
        var suma = 0.0
        var indexU: Int
        var indexV: Int
        for (i in 0 until ruta.size - 1) {
            indexU = ciudades.indexOf(ciudades[i])
            indexV = ciudades.indexOf(ciudades[i+1])
            suma += matrizAdj[indexU][indexV]
        }
        return suma / normalizador
    }
}