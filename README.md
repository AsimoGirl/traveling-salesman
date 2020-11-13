# Proyecto 1 Heurísticas: Problema del agente viajero usando recocido simulado con aceptación por umbrales

Se necesita tener gradle en versión 6+

### Generar documentación
Para generar documentación usar ./gradlew dokkaHtml y dentro de build/dokka aparecerá la documentación

### Ejecución
Se le tendrá que pasar al programa un archivo con la lista de ciudades, el inicio de las semillas y el final de ellas. 
El programa también funciona con una sola semilla.
En caso que se quiera probar un rango de semillas, aparecerá un txt llamado resultados.txt dentro de una carpeta resultados, el cual tendrá la evaluacion de todas las semillas y dará la mejor solución entre ellas.


El programa se ejecuta de la siguiente manera dentro del directorio actual:

```bash
$ ./gradlew run -Pcities=FILE -PseedS=Int -PseedF=Int
```

Ejemplo de ejecución si se quieren probar un rango de semillas:
```bash
$ ./gradlew run -Pcities=input/instancia-40.txt -PseedS=20 -PseedF=25
```
Ejemplo de ejecución si solo se quiere una semilla:
```bash
$ ./gradlew run -Pcities=input/instancia-150.txt -PseedS=20 -PseedF=20
```

El formato de la entrada para las ciudades debe ser como [este](input/40-cities.txt) 
