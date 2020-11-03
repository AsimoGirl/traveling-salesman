import org.junit.jupiter.api.Test;

class TestAgenteViajero {
    @Test
    fun obtieneInstancias() {
        val instanciasDePrueba = mapOf(
            40 to "input/instancia-40.txt",
            150 to "input/instancia-150.txt"
        )

        if (ciudades == 40) {
            normalizador = 180836110.430000007
            distanciaMax = 4947749.060000000
            funcionCosto = 3305585.454990047
        } else {
            normalizador =  723059620.720000267
            distanciaMax = 4978506.480000000
            funcionCosto = 6152051.625245280
        }

        assertTrue { grafica.normalizador == normalizador }
        assertTrue { grafica.distanciaMax == distanciaMax }
        val number = grafica.funcionCosto
        val funcionCostoGrafica = String.format("%.6f", number)
        assertTrue { equals(funcionCostoGrafica, funcionCosto) }

    }






}