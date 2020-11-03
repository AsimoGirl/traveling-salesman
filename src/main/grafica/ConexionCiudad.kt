package grafica

/**
 * Declaramos nuestra clase ConexionCiudad que almacenará las distancias entre las ciudades del problema.
 *
 * @property idCity1 Identificador de la primera ciudad.
 * @property idCity2 Identificador de la segunda ciudad.
 * @property distance Distancia entre las ciudades.
 */
data class ConexionCiudad(
    val idCity1: Int,
    val idCity2: Int,
    val distance: Double
)