import grafica.Ciudad
import grafica.ConexionCiudad
import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException
import java.sql.Statement
import java.sql.ResultSet

/**
 * Clase que obtiene la información de la base de datos
 *
 */
class DAO {
    const val "jdbc:sqlite:src/resources/ciudades.db"

    fun getCiudades(): ArrayList<Ciudad> {
        val ciudades = arrayListOf<Ciudad>()
        val connection: Connection?
        val statement: Statement?
        try {
             connection = DriverManager.getConnection(DB_URL)
             connection.autoCommit = false
             statement = connection.createStatement()
             val resultSet: ResultSet?
             resultSet = statement.executeQuery("SELECT * FROM cities")
             while (resultSet.next()) {
                  val id = resultSet.getInt("id")
                  val name = resultSet.getString("name")
                  val country = resultSet.getString("country")
                  val population = resultSet.getInt("population")
                  val latitude = resultSet.getDouble("latitude")
                  val longitude = resultSet.getDouble("longitude")
                  ciudades.add(Ciudad(id, name, country, population, latitude, longitude))
             }
             resultSet.close()
             statement.close()
             connection.close()
        } catch (e: SQLException) {
          println(e.message)
        }
        return ciudades
        }

        fun getConexiones(): ArrayList<ConexionCiudad> {
            val conexiones = arrayListOf<ConexionCiudad>()
            val connection: Connection?
            val statement: Statement?

            try {
                connection = DriverManager.getConnection(DB_URL)
                connection.autoCommit = false
                statement = connection.createStatement()
                val resultSet: ResultSet?
                resultSet = statement.executeQuery("SELECT * FROM connections")
                while (resultSet.next()) {
                    val idCity1 = resultSet.getInt("id_city_1")
                    val idCity2 = resultSet.getInt("id_city_2")
                    val distance = resultSet.getDouble("distance")
                    conexiones.add(ConexionCiudad(idCity1, idCity2, distance))
                }
                resultSet.close()
                statement.close()
                connection.close()
            } catch (e: SQLException) {
                println(e.message)
            }
            return conexiones
        }
    }
