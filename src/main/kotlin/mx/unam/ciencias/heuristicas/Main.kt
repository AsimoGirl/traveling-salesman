@file:JvmName("Main")
package mx.unam.ciencias.heuristicas

import mx.unam.ciencias.heuristicas.DAO
import mx.unam.ciencias.heuristicas.modelo.Ciudad
import mx.unam.ciencias.heuristicas.tsp.Grafica
import mx.unam.ciencias.heuristicas.tsp.Solucion
import java.io.File
import kotlin.collections.ArrayList
import kotlin.random.Random

fun main(args: Array<String>) {
  //val citiesInput = File(args[0]).readLines()[0]
  //val seedInput = (args[1]).toInt()
  val citiesInput = File("input/instancia-150.txt").readLines()[0]
  val citiesIds = ArrayList(citiesInput.split(",").map { it.toInt() })
  var citiesIdsString = ""
  for(i in 0 until citiesIds.size)
  {
    var a = citiesIds[i].toString()
    citiesIdsString += if (i < citiesIds.size - 1) {
      "\'$a\', "
    } else {
      "\'$a\'"
    }
  }
  val ciudades1 = DAO(citiesIdsString).getCiudades()
  val graf1 = Grafica(ciudades1, citiesIdsString)
  val solucion1 = Solucion(citiesIds, Random(44001))
  val number1 = graf1.f(solucion1)
  println("Función de costo : $number1")
}