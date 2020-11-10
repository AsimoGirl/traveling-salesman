package mx.unam.ciencias.heuristicas

import mx.unam.ciencias.heuristicas.modelo.Ciudad
import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException
import java.sql.Statement

/**
 * Clase que obtiene la información de la base de datos
 *
 */
class DAO(){
    companion object {
        /** Se usa companion object para poder usar la clase Ciudad sin problema*/
        fun getCiudad(idIn: Int): Ciudad {
            val connection: Connection?
            val statement: Statement?
            val query: String
            var id = 0
            var name = "Null"
            var country = "Null"
            var population = 0
            var latitude = 0.0
            var longitude = 0.0
            try {
                connection = DriverManager.getConnection("jdbc:sqlite:resources/ciudades.db")
                connection.autoCommit = false
                statement = connection.createStatement()
                query = "SELECT * FROM cities WHERE ID = $idIn"
                val resultSet =
                    statement.executeQuery(query)
                while (resultSet.next()) {
                    id = resultSet.getInt("id")
                    name = resultSet.getString("name")
                    country = resultSet.getString("country")
                    population = resultSet.getInt("population")
                    latitude = resultSet.getDouble("latitude")
                    longitude = resultSet.getDouble("longitude")
                }
                resultSet.close()
                statement.close()
                connection.close()
            } catch (e: SQLException) {
                println(e.message)
            }
            return Ciudad(id, name, country, population, latitude, longitude)
        }

        fun getDistancia(id1: Int, id2: Int) : Double{
            val connection: Connection?
            val statement: Statement?
            val query: String
            var distance = -1.0
            try {
                connection = DriverManager.getConnection("jdbc:sqlite:resources/ciudades.db")
                connection.autoCommit = false
                statement = connection.createStatement()
                query = "SELECT * FROM connections WHERE (id_city_1 = $id1 AND id_city_2 = $id2) OR (id_city_1 = $id2 AND id_city_2 = $id1 ) "
                val resultSet = statement.executeQuery(query)
                distance = resultSet.getDouble("distance")
                resultSet.close()
                statement.close()
                connection.close()
            } catch (e: SQLException) {
                println(e.message)
            }
            return distance
        }
    }
}
