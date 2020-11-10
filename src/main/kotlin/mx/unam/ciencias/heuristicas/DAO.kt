package mx.unam.ciencias.heuristicas

import mx.unam.ciencias.heuristicas.modelo.Ciudad
import mx.unam.ciencias.heuristicas.modelo.Conexion
import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException
import java.sql.Statement

/**
 * Clase que obtiene la información de la base de datos
 *
 */
class DAO(ids: String) {
    val ids = ids
    val DB_URL = "jdbc:sqlite:resources/ciudades.db"

    fun getCiudades(): ArrayList<Ciudad> {
        val connection: Connection?
        val statement: Statement?
        val ciudades = ArrayList<Ciudad>()
        var query: String
        var id = 0
        var name = "Null"
        var country = "Null"
        var population = 0
        var latitude = 0.0
        var longitude = 0.0
        try {
            connection = DriverManager.getConnection(DB_URL)
            connection.autoCommit = false
            statement = connection.createStatement()
            query = "SELECT * FROM cities WHERE ID IN ($ids)"
            val resultSet = statement.executeQuery(query)
            while (resultSet.next()) {
                id = resultSet.getInt("id")
                name = resultSet.getString("name")
                country = resultSet.getString("country")
                population = resultSet.getInt("population")
                latitude = resultSet.getDouble("latitude")
                longitude = resultSet.getDouble("longitude")
                ciudades.add(Ciudad(id, name, country, population, latitude, longitude))
            }
            connection.close()
        } catch (e: SQLException) {
            println(e.message)
        }
        return ciudades
    }

    fun getDistancia(): ArrayList<Conexion> {
        val conexionesCiudad = arrayListOf<Conexion>()
        val connection: Connection?
        val statement: Statement?
        val query: String
        try {
            connection = DriverManager.getConnection(DB_URL)
            connection.autoCommit = false
            statement = connection.createStatement()
            query =
                "SELECT * FROM connections WHERE id_city_1 OR id_city_2  IN ($ids)"
            val resultSet = statement.executeQuery(query)
            while (resultSet.next()) {
                val idCity1 = resultSet.getInt("id_city_1")
                val idCity2 = resultSet.getInt("id_city_2")
                val distance = resultSet.getDouble("distance")
                conexionesCiudad.add(Conexion(idCity1, idCity2, distance))
            }
            connection.close()
        } catch (e: SQLException) {
            println(e.message)
        }
        return conexionesCiudad
    }
}

