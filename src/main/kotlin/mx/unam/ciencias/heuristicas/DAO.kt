package mx.unam.ciencias.heuristicas

import mx.unam.ciencias.heuristicas.grafica.Ciudad
import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException
import java.sql.Statement
import java.sql.ResultSet

/**
 * Clase que obtiene la información de la base de datos
 *
 */
class DAO(DB_URL: String){

    fun getCiudad(id: Int): Ciudad {
        val connection: Connection?
        val statement: Statement?
        val ciudad: Ciudad?
        try {
            connection = DriverManager.getConnection(DB_URL)
            connection.autoCommit = false
            statement = connection.createStatement()
            val resultSet: ResultSet?
            query = "SELECT * FROM cities WHERE ID = " + id
            resultSet = statement.executeQuery(query)
            while (resultSet.next()) {
                 val id = resultSet.getInt("id")
                 val name = resultSet.getString("name")
                 val country = resultSet.getString("country")
                 val population = resultSet.getInt("population")
                 val latitude = resultSet.getDouble("latitude")
                 val longitude = resultSet.getDouble("longitude")
                 ciudad = Ciudad(id, name, country, population, latitude, longitude)
            }
            resultSet.close()
            statement.close()
            connection.close()
        } catch (e: SQLException) {
          println(e.message)
        }
        return ciudad
    }
}
