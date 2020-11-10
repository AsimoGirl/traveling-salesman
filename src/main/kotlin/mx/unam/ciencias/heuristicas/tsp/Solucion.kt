package mx.unam.ciencias.heuristicas.tsp

import kotlin.random.Random

class Solucion(ruta: ArrayList<Int>, random: Random) {
    /**
     *
     */
    val ruta = ruta
    val random = random
    val numCiudades = ruta.size

    /**
     * TODO
     *
     * @return
     */
    fun generaVecino(): Solucion {
        var uIndex = 0
        var vIndex = 0
        while (uIndex == vIndex) {
            uIndex = (0 until numCiudades).random(this.random)
            vIndex = (0 until numCiudades).random(this.random)
        }
        val nuevaRuta = ArrayList(ruta)
        val auxIndex = nuevaRuta[uIndex]
        nuevaRuta[uIndex] = nuevaRuta[vIndex]
        nuevaRuta[vIndex] = auxIndex
        return Solucion(nuevaRuta, this.random)
    }

    /**
     * TODO
     *
     */
    override fun toString(): String {
        var solucion = ""
        for(i in 0 until numCiudades){
            if (i == numCiudades -1){
                solucion += (ruta[i].toString())
            }
            else{
                solucion += (ruta[i].toString()) + ","
            }
        }
        return solucion
    }


}