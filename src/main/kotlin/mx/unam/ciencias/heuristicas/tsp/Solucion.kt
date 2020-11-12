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
    fun generaVecino(): Pair<Pair<Int, Int>,ArrayList<Int>> {
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
        val indices = Pair(uIndex, vIndex)
        return Pair(indices, nuevaRuta)
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