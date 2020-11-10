package mx.unam.ciencias.heuristicas

import mx.unam.ciencias.heuristicas.DAO
import mx.unam.ciencias.heuristicas.modelo.Ciudad
import mx.unam.ciencias.heuristicas.tsp.Grafica
import mx.unam.ciencias.heuristicas.tsp.Solucion
import java.io.File
import kotlin.collections.ArrayList
import kotlin.random.Random

fun main(args: Array<String>) {
  val citiesInput = File(args[0]).readLines()[0]
  val seedInput = (args[1]).toInt()
  val ciudades1 = ArrayList<Ciudad>()
  val citiesIds = ArrayList(citiesInput.split(",").map { it.toInt() })
  for (i in citiesIds){
    ciudades1.add(DAO.getCiudad(i))
  }
  val graf1 = Grafica(ciudades1)
  val solucion1 = Solucion(citiesIds, Random(seedInput))
  val number1 = graf1.f(solucion1)
  println("Probamos ahora")
  println(number1)
}
