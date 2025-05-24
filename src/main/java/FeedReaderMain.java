import parser.RssParser;
import parser.SubscriptionParser;
import subscription.SingleSubscription;
import utils.Arguments;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import namedEntity.EntityCountTable;
import feed.Article;
import feed.Feed;
import feed.FeedList;
import httpRequest.HttpRequester;
import namedEntity.heuristic.QuickHeuristic;
import namedEntity.heuristic.RandomHeuristic;

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

    // Cargo el subscription.json
    SubscriptionParser subparser = new SubscriptionParser();
    subparser.parseFile("config/subscriptions.json");

    // inicializo el requester y el feedlist
    HttpRequester requester = new HttpRequester();
    FeedList feeds = new FeedList(); // crea el objeto e inicializa la lista

    // obtengo los feeds
    feeds.BuildFeeds(subparser, requester);
    // printeo los feeds
    for (Feed feed : feeds.getFeedList()) {
      System.out.println("||||||| FEED: " + feed.getSiteName() + " |||||||");
      feed.prettyPrint();
    }

    // ----------------------------------------------------------
    // Printeamos Named Entities (si hay flags)
    // ----------------------------------------------------------

    // inicializo la EntityCountTable
    EntityCountTable ECT = new EntityCountTable();
    boolean printETC = false;

    // eleccion de euristica y creacion de la tabla
    if (argumentos.size() == 1 || (argumentos.size() == 2 && argumentos.contains("-qh"))) {
      QuickHeuristic QH = new QuickHeuristic();
      ECT.generateTable(feeds.getFeedList(), QH);
      printETC = true;
    } else if (argumentos.size() == 2 && argumentos.contains("-rh")) {
      RandomHeuristic RH = new RandomHeuristic();
      ECT.generateTable(feeds.getFeedList(), RH);
      printETC = true;
    }

    // printeo tabla
    if (printETC) {
      ECT.prettyPrint("Global");
      ECT.prettyPrint("Clase");
    }
  }
}
