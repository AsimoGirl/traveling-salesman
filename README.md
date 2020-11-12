# Proyecto 1 Heurísticas

Se necesita tener gradle en versión 6+

### Generar documentación
Para generar documentación usar ./gradlew dokkaHtml y dentro de build/dokka aparecerá la documentación

### Ejecución
Se le tendrá que pasar un archivo con la lista de ciudades, el inicio de las semillas y el final de ellas. Se ejecuta de la
sguiente manera:

```bash
$ ./gradlew run -Pcities=FILE -PseedS=Int -PseedF=Int
```

Ejemplo de ejecución:
```bash
$ ./gradlew run -Pcities=input/instancia-40.txt -PseedS=20 -PseedF=25
```

El formato de la entrada para las ciudades debe ser [este](samples/input/40-cities.txt) 
