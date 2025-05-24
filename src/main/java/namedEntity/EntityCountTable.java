package namedEntity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jakewharton.fliptables.FlipTable;

import feed.Article;
import feed.Feed;
import feed.FeedList;
import httpRequest.HttpRequester;
import namedEntity.heuristic.Heuristic;
import namedEntity.heuristic.QuickHeuristic;
import parser.SubscriptionParser;
import utils.Tripla;
import utils.Tupla;

public class EntityCountTable {

  // Hago la tabla, aca estan todos mis contadores DE CATEGORIAS
  // Map<String, Integer> Tabla = new HashMap<>();
  private Map<String, Integer> Tabla;

  // Tabla de claves que tienen una entrada en el map, util para inicializar
  private static List<String> CATEGORY_KEYS = Arrays.asList(
      // <!--Categorias -->
      "Person",
      "Place",
      "Organization",
      "Product",
      "Event",
      "Date",
      // Otro
      "OtherCategories");

  private static String NAMED_ENTITY_KEY = "";

  private static List<String> TOPIC_KEYS = Arrays.asList(
      // <!-- Topicos -->
      // Deportes
      "Sports",
      "Football",
      "Basketball",
      "Tennis",
      "FormulaOne",
      "OtherSports",
      // Cultura
      "Culture",
      "Music",
      "Cinema",
      "OtherCultures",
      // Politics
      "Politics",
      "NationalPolitics",
      "InternationalPolitics",
      "OtherPolitics",
      // Otro
      "OtherTopics");

  private List<Tripla> entriesCat;
  private List<Tripla> entriesTopic;
  // <Nombre, [Categoria | Topico], frecuencia>
  // <Nombre, Categoria , frecuencia>
  // <Nombre, Topico, frecuencia>

  public EntityCountTable() {
    this.Tabla = new HashMap<>();
    this.entriesTopic = new ArrayList<>();
    this.entriesCat = new ArrayList<>();

  }

  // Actualiza el value v en Tabla[key] con v + frequency.
  // cuento las ocurrencias de esa key [categoria | topic]
  // Hago un record de esa info <name, category, frequency> para usos posteriores
  // Y las guardo en una lista
  private void updateKey(List<Tripla> entriesList, String name, String key, Integer frequency) {
    Tabla.put(key, Tabla.get(key) + frequency);
    Tripla tripla = new Tripla(name, key, frequency);
    entriesList.add(tripla);
  }

  /**
   * construye la tabla a partir de los feeds y la heurística
   *
   * @param feeds     lista de feeds
   * @param heuristic heuristica a utilizar
   */
  public void generateTable(List<Feed> feeds, Heuristic heuristic) {

    // Inicializo todos los contadores en 0 en el map;
    Tabla.put(NAMED_ENTITY_KEY, 0);
    for (String clave : CATEGORY_KEYS) {
      Tabla.put(clave, 0);
    }
    for (String clave : TOPIC_KEYS) {
      Tabla.put(clave, 0);
    }

    // por cada feed, tomo la lista de articulos
    for (Feed feed : feeds) {

      // por cada articulo, guarda las named entities
      for (Article article : feed.getArticleList()) {
        // Esto filtra el articulo y solo deja las namedEntities que estan
        // mapeadas en mi diccionario de heuristica
        article.computeNamedEntities(heuristic);

        // obtengo la lista computada
        // heuristic.isEntity(namedEntity) == True
        // Para todo ne en namedEntityList
        List<NamedEntity> namedEntityList = article.getNamedEntitiesList();

        // EntidadesNombradas += namedEntityList.size()
        // Tabla.put("NamedEntities", Tabla.get("NamedEntities") +
        // namedEntityList.size());
        // EntidadesNombradas = namedEntityList.size()
        Tabla.put("NamedEntities", namedEntityList.size());

        // por cada namedEntity computada, me fijo si es mapeable
        for (NamedEntity namedEntity : namedEntityList) {
          String name = namedEntity.getName();
          Integer frequency = namedEntity.getFrequency();

          String category = null;
          String topic = null;

          // Busco en la tabla de la heuristica el mapeo correspondiente
          // Da null si no encuentra uno
          NamedEntity ne = heuristic.getNamedEntity(name);

          if (ne == null) { // "Mesa12nasdn -> null en el diccionario"
            // Hago algo si quiero con la palabra no mapeada/reconocida
          }

          if (ne != null) { // Entoncesa existe un mapeo de String -> NamedEntity
            // Son las que voy a tener en cuenta para armar mis tablas en general
            category = ne.getCategory();
            topic = ne.getTopic();

            // Si la categoria dicha en el mapeo de heuristic matchea con las
            // que cuento aqui, entonces hago cosas
            if (Tabla.containsKey(category)) { // INFO: <-- Necesario???
              // DEBUG:
              // System.err.println("NOMBRE -> " + name);
              // System.err.println("FRECUENCIA -> " + frequency);
              // System.err.println(category);

              // Tabla[category].value() += frequency
              // Hago un record de la info <name, category, frequency> para usos posteriores
              // Y las guardo en una lista de triplas
              updateKey(entriesCat, name, category, frequency);

              // Para verlo mas lindo
              // System.err.println("Tabla[" + category + "] : " +
              // Tabla.get(category).toString());
            }

            // Si el topico dicho en el mapeo de heuristic matchea con los
            // que cuento aqui, entonces hago cosas
            if (Tabla.containsKey(topic)) {
              // Tabla[topic].value() += frequency
              // Hago un record de la info <name, category, frequency> para usos posteriores
              // Y las guardo en una lista de triplas
              updateKey(entriesTopic, name, topic, frequency);

              // DEBUG:
              // System.err.println(topic);
              // System.err.println("Tabla[" + topic + "] : " + Tabla.get(topic).toString());
              // System.err.println("----------------------------------------------------------------------");
            }
          }
        }
      }
    }
  }

  private enum Fields {
    CATEGORY("category"),
    TOPIC("topic");

    private final String valor;

    Fields(String str) {
      this.valor = str;
    }

    public String getValor() {
      return valor;
    }
  }

  // ESTO ES LA PAPA
  public static List<Tripla> generateEntries(Feed f, Heuristic h, Fields campo) {
    List<Tripla> triplista = new ArrayList<Tripla>();
    Tripla tripla = null;
    String name = null;
    String field = null;
    Integer frequency = null;

    for (Article art : f.getArticleList()) {
      art.computeNamedEntities(h);
      for (NamedEntity ne : art.getNamedEntitiesList()) {
        if (ne.getFrequency() > 0) {
          name = ne.getName();
          field = (campo.getValor().equals("category")) ? ne.getCategory() : ne.getTopic();
          frequency = ne.getFrequency();
          tripla = new Tripla(name, field, frequency);
          triplista.add(tripla);
        }
      }
    }
    return triplista;
  }

  // Dicconario papa
  // Persona 5
  // Football 4
  public static Map<String, Integer> countDict(List<Tripla> catList, List<Tripla> topList) {
    Map<String, Integer> d = new HashMap<>();

    for (String cat : CATEGORY_KEYS) {
      d.put(cat, 0);
    }
    for (String top : TOPIC_KEYS) {
      d.put(top, 0);
    }

    for (Tripla<String, String, Integer> t : catList) {
      d.put(t.second, d.get(t.second) + t.third);
    }
    for (Tripla<String, String, Integer> t : topList) {
      d.put(t.second, d.get(t.second) + t.third);
    }

    return d;
  }
  // Messi 5

  // Toma un feed y devuelve un diccionario con el conteo de todas las ne
  public static Map<String, Integer> buildCountDict(Feed feed, Heuristic h) {
    Map<String, Integer> d = new HashMap<String, Integer>();
    List<Tripla> entriesCat = generateEntries(feed, h, Fields.CATEGORY);
    List<Tripla> entriesTop = generateEntries(feed, h, Fields.TOPIC);

    d = countDict(entriesCat, entriesTop);

    // for (Article a : feed.getArticleList()) {
    // for (NamedEntity ne : a.buildListOfNamedEntities(a, h)) { // INFO: ES MUY
    // POLEMICO esto???

    // String category = ne.getCategory();
    // System.err.println("100200300aaa:" + category);
    // if (category != null) {
    // d.put(ne.getCategory(), d.get(category) + ne.getFrequency());
    // }
    // }
    // }

    return d;
  }

  // Dada una lista de entidades nombradas,

  public void prettyPrint(String parametro) {
    // Headers, filas y tabla de cada arbol en modo <parametro>
    String categoriasTabla = null;
    String topicosTabla = null;

    if (parametro.equals("Global")) {
      // Categorias
      String[] headerCategorias = { "Nombre", "Categoria", "Ocurrencias" };
      String[][] dataCategorias = new String[entriesCat.size()][3];

      // Popular entriesCat
      for (int fila = 0; fila < entriesCat.size(); fila++) {
        // Categorias
        Tripla<?, ?, ?> tripla = entriesCat.get(fila);
        dataCategorias[fila][0] = tripla.first.toString();
        dataCategorias[fila][1] = tripla.second.toString();
        dataCategorias[fila][2] = tripla.third.toString();
        // topicos
        tripla = entriesCat.get(fila);
      }
      categoriasTabla = FlipTable.of(headerCategorias, dataCategorias);
      // Topicos
      String[] headerTopicos = { "Nombre", "Topico", "Ocurrencias" };
      String[][] dataTopicos = new String[entriesCat.size()][3];
      for (int fila = 0; fila < entriesTopic.size(); fila++) {
        Tripla<?, ?, ?> tripla = entriesTopic.get(fila);
        dataTopicos[fila][0] = tripla.first.toString();
        dataTopicos[fila][1] = tripla.second.toString();
        dataTopicos[fila][2] = tripla.third.toString();
      }
      topicosTabla = FlipTable.of(headerTopicos, dataTopicos);
    }

    else if (parametro.equals("Clase")) {
      // proceso de listas para imprimir
      List<Tupla> categories = new ArrayList();
      List<Tupla> topics = new ArrayList();

      for (String category : CATEGORY_KEYS) {
        Integer frequency = Tabla.get(category);
        Tupla tuple = new Tupla(category, frequency);
        if (frequency != 0) {
          categories.add(tuple);
        }
      }

      for (String topic : TOPIC_KEYS) {
        Integer frequency = Tabla.get(topic);
        Tupla tuple = new Tupla(topic, frequency);
        if (frequency != 0) {
          topics.add(tuple);
        }
      }

      // Categorías
      String[] headerCategorias = { "Categoria", "Ocurrencias" };
      String[][] dataCategorias = new String[categories.size()][2];
      for (int fila = 0; fila < categories.size(); fila++) {
        dataCategorias[fila][0] = categories.get(fila).first.toString();
        dataCategorias[fila][1] = categories.get(fila).second.toString();
      }
      categoriasTabla = FlipTable.of(headerCategorias, dataCategorias);

      // Tópicos
      String[] headerTopicos = { "Topico", "Ocurrencias" };
      String[][] dataTopicos = new String[topics.size()][2];
      for (int fila = 0; fila < topics.size(); fila++) {
        dataTopicos[fila][0] = topics.get(fila).first.toString();
        dataTopicos[fila][1] = topics.get(fila).second.toString();
      }
      topicosTabla = FlipTable.of(headerTopicos, dataTopicos);
    }

    // Imprimir tabla final combinada
    String[] headers = { "Zona de categorias", "Zona de topicos" };
    String[][] data = { { categoriasTabla, topicosTabla } };
    System.out.println(FlipTable.of(headers, data));
  }

  // Funcion pura que mergea dos diccionarios
  public static Map<String, Integer> mergeCountDicts(Map<String, Integer> d1, Map<String, Integer> d2) {
    Map<String, Integer> d = new HashMap(d1);
    for (Map.Entry<String, Integer> entry : d2.entrySet()) {
      d.merge(entry.getKey(), entry.getValue(), Integer::sum);
    }
    return d;
  }

  // TODO: Terminar...
  public void prettyPrintCountDict(Map<String, Integer> d1, String parametro) {
    // Headers, filas y tabla de cada arbol en modo <parametro>

    // INFO: Tengo que generar estas pero para d1
    // List<Tripla> entriesCat = ???;
    // List<Tripla> entriesTopic = ???;

    String categoriasTabla = null;
    String topicosTabla = null;

    if (parametro.equals("Global")) {
      // Categorias
      String[] headerCategorias = { "Nombre", "Categoria", "Ocurrencias" };
      String[][] dataCategorias = new String[entriesCat.size()][3];

      // Popular entriesCat
      for (int fila = 0; fila < entriesCat.size(); fila++) {
        // Categorias
        Tripla<?, ?, ?> tripla = entriesCat.get(fila);
        dataCategorias[fila][0] = tripla.first.toString();
        dataCategorias[fila][1] = tripla.second.toString();
        dataCategorias[fila][2] = tripla.third.toString();
        // topicos
        tripla = entriesCat.get(fila);
      }
      categoriasTabla = FlipTable.of(headerCategorias, dataCategorias);
      // Topicos
      String[] headerTopicos = { "Nombre", "Topico", "Ocurrencias" };
      String[][] dataTopicos = new String[entriesCat.size()][3];
      for (int fila = 0; fila < entriesTopic.size(); fila++) {
        Tripla<?, ?, ?> tripla = entriesTopic.get(fila);
        dataTopicos[fila][0] = tripla.first.toString();
        dataTopicos[fila][1] = tripla.second.toString();
        dataTopicos[fila][2] = tripla.third.toString();
      }
      topicosTabla = FlipTable.of(headerTopicos, dataTopicos);
    }

    else if (parametro.equals("Clase")) {
      // proceso de listas para imprimir
      List<Tupla> categories = new ArrayList();
      List<Tupla> topics = new ArrayList();

      for (String category : CATEGORY_KEYS) {
        Integer frequency = Tabla.get(category);
        Tupla tuple = new Tupla(category, frequency);
        if (frequency != 0) {
          categories.add(tuple);
        }
      }

      for (String topic : TOPIC_KEYS) {
        Integer frequency = Tabla.get(topic);
        Tupla tuple = new Tupla(topic, frequency);
        if (frequency != 0) {
          topics.add(tuple);
        }
      }

      // Categorías
      String[] headerCategorias = { "Categoria", "Ocurrencias" };
      String[][] dataCategorias = new String[categories.size()][2];
      for (int fila = 0; fila < categories.size(); fila++) {
        dataCategorias[fila][0] = categories.get(fila).first.toString();
        dataCategorias[fila][1] = categories.get(fila).second.toString();
      }
      categoriasTabla = FlipTable.of(headerCategorias, dataCategorias);

      // Tópicos
      String[] headerTopicos = { "Topico", "Ocurrencias" };
      String[][] dataTopicos = new String[topics.size()][2];
      for (int fila = 0; fila < topics.size(); fila++) {
        dataTopicos[fila][0] = topics.get(fila).first.toString();
        dataTopicos[fila][1] = topics.get(fila).second.toString();
      }
      topicosTabla = FlipTable.of(headerTopicos, dataTopicos);
    }

    // Imprimir tabla final combinada
    String[] headers = { "Zona de categorias", "Zona de topicos" };
    String[][] data = { { categoriasTabla, topicosTabla } };
    System.out.println(FlipTable.of(headers, data));
  }

  public static void main(String[] args) {
    System.out.println("Compilo");

    QuickHeuristic qh = new QuickHeuristic();
    FeedList feedl = new FeedList();
    SubscriptionParser subParser = new SubscriptionParser();
    subParser.parseFile("config/subscriptions.json");
    HttpRequester requester = new HttpRequester();

    feedl.BuildFeeds(subParser.getSubscriptions());
    List<Feed> feedlpapa = feedl.getFeedList();
    EntityCountTable ect = new EntityCountTable();
    ect.generateTable(feedlpapa, qh);

    System.out.println("Tabla global de ocurrencias de entidades nombradas");
    ect.prettyPrint("Global");
    System.out.println("Tabla de ocurrencias de categorias y topicos");
    ect.prettyPrint("Clase");
  }
}
