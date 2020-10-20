/***
fn getCities(): ArrayList<City> {
    val cities = arrayListOf<City>()

    val connection: Connection?
    val statement: Statement?

    try {
        connection = DriverManager.getConnection(DB_URL)
        connection.autoCommit = false

        statement = connection.createStatement()
        val resultSet = statement.executeQuery("SELECT * FROM cities")

        while (resultSet.next()) {
            val id = resultSet.getInt("id")
            val name = resultSet.getString("name")
            val country = resultSet.getString("country")
            val population = resultSet.getInt("population")
            val latitude = resultSet.getDouble("latitude")
            val longitude = resultSet.getDouble("longitude")

            cities.add(City(id, name, country, population, latitude, longitude))
        }

        resultSet.close()
        statement.close()
        connection.close()
    } catch (e: SQLException) {
        println(e.message)
    }

    return cities
}

fun getConnectionsBetweenTwoCities(): ArrayList<CityConnection> {
    val cityConnections = arrayListOf<CityConnection>()

    val connection: Connection?
    val statement: Statement?

    try {
        connection = DriverManager.getConnection(DB_URL)
        connection.autoCommit = false

        statement = connection.createStatement()
        val resultSet =
            statement.executeQuery("SELECT * FROM connections")

        while (resultSet.next()) {
            val idCity1 = resultSet.getInt("id_city_1")
            val idCity2 = resultSet.getInt("id_city_2")
            val distance = resultSet.getDouble("distance")

            cityConnections.add(CityConnection(idCity1, idCity2, distance))
        }

        resultSet.close()
        statement.close()
        connection.close()
    } catch (e: SQLException) {
        println(e.message)
    }

    return cityConnections
}
****/