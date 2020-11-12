package mx.unam.ciencias.heuristicas.tsp

import kotlin.random.Random

class Solucion(
    var ruta: ArrayList<Int>, private val random: Random, var costo: Double) {
    private val numCiudades = ruta.size

    /**
     * TODO
     *
     * @return
     */
    fun generaVecino(g: Grafica): Solucion {
        var uIndex = 0
        var vIndex = 0
        while (uIndex == vIndex) {
            uIndex = (0 until numCiudades).random(random)
            vIndex = (0 until numCiudades).random(random)
        }
        val nuevaRuta = ArrayList(ruta)
        val auxIndex = nuevaRuta[uIndex]
        nuevaRuta[uIndex] = nuevaRuta[vIndex]
        nuevaRuta[vIndex] = auxIndex

        //Inicia la optimización
        // e0 = (i-1, i)
        // e1 = (i, i+1)
        // e2 = (j-1, j)
        // e3 = (j, j+1)
        // So the new cost consist of substracting the
        // weight of this deleted edges and adding the
        // new ones.
        var e0Antiguo = 0.0
        var e0Nuevo = 0.0
        var e1Antiguo = 0.0
        var e1Nuevo = 0.0
        var e2Antiguo = 0.0
        var e2Nuevo = 0.0
        var e3Antiguo = 0.0
        var e3Nuevo = 0.0
        val nuevoCosto: Double

        if (uIndex > 0 && vIndex != uIndex-1) {
            e0Antiguo = g.getPeso(ruta[uIndex-1], ruta[uIndex])
            e0Nuevo   = g.getPeso(ruta[uIndex-1], ruta[vIndex])
        }
        if (uIndex < numCiudades - 1 && vIndex != uIndex + 1) {
            e1Antiguo = g.getPeso(ruta[uIndex], ruta[uIndex+1])
            e1Nuevo   = g.getPeso(ruta[vIndex], ruta[uIndex+1])
        }
        if (vIndex > 0 && uIndex != vIndex-1) {
            e2Antiguo = g.getPeso(ruta[vIndex-1], ruta[vIndex])
            e2Nuevo   = g.getPeso(ruta[vIndex-1], ruta[uIndex])
        }
        if (vIndex < numCiudades - 1 && uIndex != vIndex + 1) {
            e3Antiguo = g.getPeso(ruta[vIndex], ruta[vIndex+1])
            e3Nuevo   = g.getPeso(ruta[uIndex], ruta[vIndex+1])
        }

        val sumaNuevos = e0Nuevo + e1Nuevo + e2Nuevo + e3Nuevo
        val sumaPrevios = e0Antiguo + e1Antiguo + e2Antiguo + e3Antiguo
        nuevoCosto = costo + (sumaNuevos - sumaPrevios)/g.normalizador

        return Solucion(nuevaRuta, this.random, nuevoCosto)
    }

    /**
     * TODO
     *
     */
    override fun toString(): String {
        var solucion = ""
        for(i in 0 until numCiudades){
            solucion += if (i == numCiudades -1){
                (ruta[i].toString())
            } else{
                (ruta[i].toString()) + ","
            }
        }
        return solucion
    }


}