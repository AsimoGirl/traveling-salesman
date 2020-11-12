package mx.unam.ciencias.heuristicas

import mx.unam.ciencias.heuristicas.DAO
import mx.unam.ciencias.heuristicas.modelo.Ciudad
import mx.unam.ciencias.heuristicas.tsp.Grafica
import mx.unam.ciencias.heuristicas.tsp.Solucion
import org.junit.jupiter.api.Test
import java.io.File
import kotlin.random.Random
import kotlin.test.assertEquals
import kotlin.test.assertTrue


class TestAgenteViajero {
    /**
    @Test
    fun pruebaCosto40() {
        val citiesInput = File("input/instancia-40.txt").readLines()[0]
        val citiesIds = ArrayList(citiesInput.split(",").map { it.toInt() })
        var citiesIds1S = ""
        for(i in 0 until citiesIds.size)
        {
            var a = citiesIds[i].toString()
            citiesIds1S += if (i < citiesIds.size - 1) {
                "\'$a\', "
            } else {
                "\'$a\'"
            }
        }
        val ciudades1 = DAO(citiesIds1S).getCiudades()
        val graf1 = Grafica(ciudades1, citiesIds1S)
        val normalizador1 = 180836110.430000007
        val distanciaMax1 = 4947749.060000000
        val funcionCosto1 = 3305585.454990047

        assertEquals(graf1.normalizador, normalizador1)
        assertEquals(graf1.distanciaMax, distanciaMax1)
        val solucion1 = Solucion(citiesIds, Random(1))
        val number1 = graf1.f(solucion1)
        val funcionCostoGrafica1 = String.format("%.6f", number1).toDouble()
        val funcionCostoReducida1 = String.format("%.6f", funcionCosto1).toDouble()
        assertEquals(funcionCostoGrafica1, funcionCostoReducida1)
    }

    @Test
    fun pruebaCosto150() {
        val citiesInput2 = File("input/instancia-150.txt").readLines()[0]
        val citiesIds2 = ArrayList(citiesInput2.split(",").map { it.toInt() })
        var citiesIds2S = ""
        for(i in 0 until citiesIds2.size)
        {
            var a = citiesIds2[i].toString()
            citiesIds2S += if (i < citiesIds2.size - 1) {
                "\'$a\', "
            } else {
                "\'$a\'"
            }
        }
        val ciudades2 = DAO(citiesIds2S).getCiudades()
        val graf2 = Grafica(ciudades2, citiesIds2S)
        val normalizador2 =  723059620.720000267
        val distanciaMax2 = 4978506.480000000
        val funcionCosto2 = 6152051.625245280

        assertEquals(graf2.normalizador, normalizador2)
        assertEquals(graf2.distanciaMax, distanciaMax2)
        val solucion2 = Solucion(citiesIds2, Random(1))
        val number2 = graf2.f(solucion2)
        val funcionCostoGrafica2 = String.format("%.6f", number2).toDouble()
        val funcionCostoReducida2 = String.format("%.6f", funcionCosto2).toDouble()
        assertEquals(funcionCostoGrafica2, funcionCostoReducida2)

    }
    **/





}