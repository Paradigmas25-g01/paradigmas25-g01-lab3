# LAB 3 / Grupo 1

## Configuración del entorno y ejecución
Instrucciones para el usuario sobre cómo correr las dos partes del laboratorio con spark. Explicación del resultado que se espera luego de ejecutar cada parte.

1) Detallar en el Makefile (si no lo está) la variable JAVA_HOME_PATH con Java 11.
2) Para compilar:
    - 'make default_args' corre el programa por defecto, imprime los feeds RSS.
    - 'make named_entities' y 'make quick_heuristic' corren el programa y la función de procesamiento de Named Entities con la Heurística 'Quick'
    - 'make random_heuristic' corre el programa y la funcion de procesamiento de Named Entities con la Heurística 'Quick'
    - 'make clean limpia los archivos temporales restantes'

## Decisiones de diseño
Opcional. Cualquier cosa que quieran aclarar sobre la implementación del laboratorio

Decidimos configurar nuestro main para hacerlo concurrente. Creamos una funcion nueva dentro de FeedList llamada
buildTopicFeeds que a partir de una Single Suscription obtiene una lista de Feeds con los feeds de cada topico de esta suscripción, esto fue para no perder el orden de impresión del laboratorio anterior.

También usamos paralellize() para facilitarnos a resolver el proyecto orientados por la idea de que este trabajo está
para entender como se trabaja en un framework y aprovechar sus herramientas; evitando asi lidiar con condiciones de carrera, sección crítica y conceptos ya practicado en materias como Sistemas Operativos. 


## Conceptos importantes
1. **Describa el flujo de la aplicación** ¿Qué pasos sigue la aplicación desde la lectura del archivo feeds.json hasta la obtención de las entidades nombradas? ¿Cómo se reparten las tareas entre los distintos componentes del programa?

*Programa por defecto*
- lectura y parseo de suscriptions.json
- inicializacion de spark
- particion de trabajo x suscripción, en cada tópico se:
    - se hace el request html
    - se parsea el contenido
    - se construye el feed
- se añade a una lista de feeds por subscripcion
- se imprimen en ese orden

*Obtención de entidades nombradas*
- se reconoce los argumentos (se printea solo o tambien se recuentan las entidades) (heuristicas)
- particion de trabajo x feed
- se configura la heuristica
-      por cada feed se:
    - se hacen las cuentas de ocurrencias (diccionarios)
- se agrupan los diccionarios
- se construyen e imprimen las tablas

- se cierra sesión de spark


2. **¿Por qué se decide usar Apache Spark para este proyecto?** ¿Qué necesidad concreta del problema resuelve?

Pues tenemos 2 posibles cuellos de botellas al trabajar secuencialmente:
    - Descarga de Feeds
    - Procesamiento de NE
Y Spark es ideal para paralelizar o hacer concurrentes procesos que no lo son, brindando eficiencia.


3. **Liste las principales ventajas y desventajas que encontró al utilizar Spark.**

Ventajas:
- manejo de concurrencia y paralelizacion de alto nivel: con paralellize() nos podemos abstraer de sutilezas tales como particiones, asignaciones de trabajo, locks y región crítica.
- modelo funcional claro: operaciones como map, filter, reduce son fáciles de componer.
Desventajas:
- costo de aprendizaje, al principio fue un poco dificil entender la lógica del framework


4. **¿Cómo se aplica el concepto de inversión de control en este laboratorio?** Explique cómo y dónde se delega el control del flujo de ejecución. ¿Qué componentes deja de controlar el desarrollador directamente?

En este laboratorio, la inversión de control se manifiesta en cómo Spark toma el control del flujo de ejecución de los datos. En lugar de que el desarrollador dicte explícitamente cómo se deben ejecutar los pasos (como en un código secuencial tradicional), Spark se encarga de decidir cuándo y cómo ejecutar transformaciones sobre los RDD.
Por ejemplo, las operaciones sobre los RDD son lazy (evaluadas perezosamente) y solo se ejecutan cuando se aplica una acción como collect() o reduce().
Además, delegamos el control de concurrencia y paralelismo a Spark, que decide cómo distribuir el trabajo entre los núcleos o nodos, a través de métodos como parallelize().
El desarrollador deja de controlar directamente el orden exacto de ejecución, la gestión de hilos y sincronización, delegando esa responsabilidad al framework.


5. **¿Considera que Spark requiere que el codigo original tenga una integración tight vs loose coupling?**

Spark no requiere estrictamente que el código original esté diseñado con loose coupling, pero sí se ve altamente beneficiado por ello. Al trabajar con funciones que se pasan a operaciones como map, filter o reduce, es importante que estas funciones estén modularizadas y sean independientes del contexto externo, es decir, tengan bajo acoplamiento.
Esto facilita su reutilización y adaptación al modelo distribuido de Spark. Si el código estuviera altamente acoplado (tight coupling), sería difícil modificar solo una parte sin afectar otras. Loose coupling también favorece el testeo, la extensibilidad y la integración con nuevos frameworks como Spark.


6. **¿El uso de Spark afectó la estructura de su código original?** ¿Tuvieron que modificar significativamente clases, métodos o lógica de ejecución del laboratorio 2? 

No tuvimos que modificar significativamente la lógica de ejecución en general del LAB 2, lo más cambiado es Main pero solo se "tradujo" a concurrente usando Spark, la logica es mas o menos la misma. Tuvimos que crear algunas funciones auxiliares más atómicas para paralelizar algunos procesos y tambien hacer que varias clases hereden de serializable para hacerlas aptas para RDD.