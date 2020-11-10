package mx.unam.heuristicas

import mx.unam.ciencias.heuristicas.DAO
import mx.unam.ciencias.heuristicas.modelo.Ciudad
import org.junit.jupiter.api.Test;
import mx.unam.ciencias.heuristicas.tsp.Grafica
import mx.unam.ciencias.heuristicas.tsp.Solucion
import org.junit.jupiter.api.Assertions.assertTrue
import java.io.File
import kotlin.collections.ArrayList
import kotlin.random.Random

class TestAgenteViajero {
    @Test

    fun pruebaCosto() {
        val ciudades1 = ArrayList<Ciudad>()
        val citiesInput = File("input/instancia-40.txt").readLines()[0]
        val citiesIds = ArrayList(citiesInput.split(",").map { it.toInt() })
        for (i in citiesIds){
            ciudades1.add(DAO.getCiudad(i))
        }
        val graf1 = Grafica(ciudades1)
        val normalizador1 = 180836110.430000007
        val distanciaMax1 = 4947749.060000000
        val funcionCosto1 = 3305585.454990047

        val ciudades2 = ArrayList<Ciudad>()
        val citiesInput2 = File("input/instancia-150.txt").readLines()[0]
        val citiesIds2 = ArrayList(citiesInput2.split(",").map { it.toInt() })
        for (i in citiesIds2){
            ciudades2.add(DAO.getCiudad(i))
        }
        val graf2 = Grafica(ciudades2)
        val normalizador2 =  723059620.720000267
        val distanciaMax2 = 4978506.480000000
        val funcionCosto2 = 6152051.625245280

        assertTrue { graf1.normalizador == normalizador1 }
        assertTrue { graf1.distanciaMax == distanciaMax1 }
        val solucion1 = Solucion(citiesIds, Random(1))
        val number1 = graf1.f(solucion1)
        val funcionCostoGrafica1 = String.format("%.6f", number1).toDouble()
        val funcionCostoReducida1 = String.format("%.6f", funcionCosto1).toDouble()
        assertTrue { funcionCostoGrafica1 == funcionCostoReducida1 }

        assertTrue { graf2.normalizador == normalizador2 }
        assertTrue { graf2.distanciaMax == distanciaMax2 }
        val solucion2 = Solucion(citiesIds2, Random(1))
        val number2 = graf1.f(solucion2)
        val funcionCostoGrafica2 = String.format("%.6f", number2).toDouble()
        val funcionCostoReducida2 = String.format("%.6f", funcionCosto2).toDouble()
        assertTrue { funcionCostoGrafica2 == funcionCostoReducida2 }
    }






}