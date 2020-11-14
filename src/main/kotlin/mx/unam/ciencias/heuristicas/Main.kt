@file:JvmName("Main")
package mx.unam.ciencias.heuristicas

import mx.unam.ciencias.heuristicas.tsp.Grafica
import mx.unam.ciencias.heuristicas.tsp.Solucion
import mx.unam.ciencias.heuristicas.umbrales.Heuristica
import java.io.File
import kotlin.collections.ArrayList
import kotlin.random.Random


/**
 * Función main del proyecto
 * @param args Argumentos obtenidos por la terminal
 */
fun main(args: Array<String>) {
  val citiesInput = File(args[0]).readLines()[0]
  val seedS = (args[1]).toInt()
  val seedF = (args[2]).toInt()
  val citiesIds = ArrayList(citiesInput.split(",").map { it.toInt() })
  var citiesIdsString = ""
  for(i in 0 until citiesIds.size)
  {
    val a = citiesIds[i].toString()
    citiesIdsString += if (i < citiesIds.size - 1) {
      "\'$a\', "
    } else {
      "\'$a\'"
    }
  }
  println("Ciudades: $citiesIds")
  val ciudades1 = DAO(citiesIdsString).getCiudades()
  val graf1 = Grafica(ciudades1, citiesIdsString)
  val costoInicial = graf1.f()
  println("Costo Inicial: $costoInicial")
  var string = "Soluciones de las distintas semillas en el rango\n"
  var mejorCosto = Double.MAX_VALUE
  var mejorSemilla = 0
  var mejorRuta = citiesIds
  for (i in seedS until seedF + 1)  {
    println("Semilla: $i")
    val solucion1 = Solucion(citiesIds, Random(i), costoInicial)
    val tsp = Heuristica(graf1, solucion1)
    tsp.aceptacionPorUmbrales()
    if (tsp.evaluacion() <= mejorCosto){
      mejorCosto = tsp.evaluacion()
      mejorSemilla = i
      mejorRuta = tsp.ruta()
    }
    println("Ruta: ${tsp.ruta()}")
    println("Costo: ${tsp.evaluacion()}")
    println("¿Es Factible?: ${tsp.esFactible()}")
    println("---------------------------------------------\n")
    string += "Semilla: $i, Costo: ${tsp.evaluacion()}\n"
  }
  string += "Mejor Semilla: $mejorSemilla, Mejor Costo: $mejorCosto , Mejor Ruta: $mejorRuta"
  if(seedS != seedF) {
    File("resultado/resultado-actual.txt").writeText(string)
    println("Mejor Ruta: $mejorRuta")
    println("Mejor Costo: $mejorCosto")
  }
}