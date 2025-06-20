import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;

import utils.AnsiColors;
import utils.Tupla;

import javax.ws.rs.client.Entity;

import org.apache.spark.api.java.function.Function;

import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.SparkSession;

import feed.Feed;
import feed.FeedList;
import httpRequest.HttpRequester;
import namedEntity.EntityCountTable;
import namedEntity.heuristic.Heuristic;
import namedEntity.heuristic.QuickHeuristic;
import namedEntity.heuristic.RandomHeuristic;
import parser.SubscriptionParser;
import scala.io.AnsiColor;
import subscription.SingleSubscription;

public class FeedReaderMain {

  // Parseo de Reddit -> 0% implementado
  // Funcion Named Entity -> 15% implementado

  public static void main(String[] args) {
    // Parseo argumentos como una lista para usar metodos ya heredados
    List<String> argumentos = Arrays.asList(args);

    // Imprimo saludo
    System.out.println("************* FeedReader version 1.0 *************");

    // ----------------------------------------------------------
    // Chequeamos errores
    // ----------------------------------------------------------

    // Si los argumentos no incluyen "-ne" -> error
    if (argumentos.size() >= 1 && !argumentos.contains("-ne")) {
      System.err.println("Please, run the program in a proper way: FeedReader [-ne]");
      return;
    }

    // Si hay 2 argumentos que no incluyen "-rh" o "-qh" -> error
    if (argumentos.size() == 2 && !argumentos.contains("-rh") && !argumentos.contains("-qh")) {
      System.err.println("Please, run the program in a proper way: FeedReader [-ne] ~ [-ne -qh] ~ [-ne -rh]");
      return;
    }

    // Si hay mas de 2 argumentos -> error
    if (argumentos.size() > 2) {
      System.err.println("ERROR: Too many arguments. This program accepts at most two arguments.");
      return;
    }

    // ----------------------------------------------------------
    // Printeamos FEEDs
    // ----------------------------------------------------------
    //
    SubscriptionParser subparser = new SubscriptionParser();
    subparser.parseFile("config/subscriptions.json");
    // subparser.parseFile("config/subscriptionsLarge.json");

    // INFO: 0.0
    // Crea una sesión de Spark en modo local (usa todos los núcleos de la máquina)
    SparkSession spark = SparkSession
        .builder()
        .appName("FeedReader")
        .master("local[*]") // Ejecuta en modo local usando todos los núcleos
        .getOrCreate();
    JavaSparkContext sc = new JavaSparkContext(spark.sparkContext());

    // INFO: 1.1
    // Creo el RDD con mis SingleSubscriptions
    // Ver javadoc de `RDD` (shift+k en RDD)
    JavaRDD<SingleSubscription> subs = sc.parallelize(subparser.getSubscriptions());

    // INFO: 1.2
    // Configuro el map del RDD, voy a mapear (\singSub -> List<Feed>)
    // Es decir, a partir de cada sub consigo una lista con los feeds por topico de esa sub.
    JavaRDD<List<Feed>> feedsGroupedByTopic = subs.map(singSub -> FeedList.buildTopicFeeds(singSub));

    List<Feed> feedList = new ArrayList<Feed>(); // solo para Named Entities

    // INFO: 1.3
    // Voy a agrupar todos los feeds a una lista de feeds
    // Notar que no uso reduce porque estaria perdiendo la nocion de que
    // Cada singlesub tiene varios topicos... Collect los agrupa en una lista de listas
    // Y luego se imprimen, tambien se agregan a una lista de feeds (necesaria para la funcion extra)

    List<List<Feed>> feedListList = feedsGroupedByTopic.collect();
    for (List <Feed> list : feedListList) {
      for (Feed feed : list) {
        feed.prettyPrint();

        feedList.add(feed);
      }
    }

    // System.out.println(AnsiColors.CYAN + feedList.toString() + AnsiColors.RESET);

    // ----------------------------------------------------------
    // Printeamos Named Entities (si hay flags)
    // ----------------------------------------------------------

    if (argumentos.contains("-ne")) {

      // INFO: 2.1
      // RDD con todos mis feeds obtenidos tras ejecutar las funciones map
      // FeedList.buildFeed(ssi) esta en feeds para todo ss en subs
      // "ssi = SingleSubscription_i"
      
      
      JavaRDD<Feed> feeds = sc.parallelize(feedList);

      // // INFO: 2.2
      // // Configuro el map del RDD, voy a mapear (\feed -> diccionario)
      // // donde en ese diccionario estaran contadas las ocurrencias de las cosas del
      // // feed.
      // // Aca dicts es el output del mapeo

      // TODO: Chequear
      // Configuro Euristica segun el argumento
      Heuristic h = argumentos.contains("-rh") ? new RandomHeuristic() : new QuickHeuristic(); // hardcodeo por ahora
      
      JavaRDD<Map<String, Integer>> dicts = feeds.map(feed2 -> EntityCountTable.buildCountDict(feed2, h));

      // INFO: 2.3
      // Voy a agrupar todos los diccionarios en un gran diccionario con todo sumado
      // Notar que hago una copia en la lambda, Map es una interfaz HashMap si es
      // instanciable
      // INFO: ACA FALLA, getCategory da NULL o ALGO ASI
      Map<String, Integer> finalDict = dicts.reduce((d1, d2) -> EntityCountTable.mergeCountDicts(d1, d2));

      // EntityCountTable ECT = new EntityCountTable()
      EntityCountTable.prettyPrintCountDict(finalDict);
    }

    // INFO: IMPORTANTISIMO
    sc.close();
    spark.stop();
  }
}
