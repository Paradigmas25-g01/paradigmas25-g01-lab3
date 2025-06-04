package feed;

import parser.GeneralParser;
import parser.RedditParser;
import parser.RssParser;
import parser.SubscriptionParser;
import scala.collection.View.Single;
import subscription.SingleSubscription;
import java.util.ArrayList;
import java.util.List;
import httpRequest.HttpRequester;

/**
 * Clase que representa una lista de feeds.
 * Proporciona funcionalidad para construir la lista de feeds
 * a partir de un parser de suscripciones y un requester HTTP.
 */
public class FeedList {

  /** Lista que contiene los feeds construidos */
  private List<Feed> feedList;

  /**
   * Constructor que inicializa la lista de feeds vacia.
   */
  public FeedList() {
    this.feedList = new ArrayList<Feed>();
  }

  // Toma una SingleSubscription y devuelve un feed
  // static porque es una funcion pura
  static public List<Feed> buildTopicFeeds(SingleSubscription singSub) {
    List<Feed> topicFeeds = new ArrayList<Feed>();

    String type = singSub.getUrlType().toLowerCase();
    if (type.equals("rss")) {
      String urlFormat = singSub.getUrl();
      for (String topic : singSub.getUrlParams()) {
        try {
          // Formo la URL con el topic
          String finalUrl = String.format(urlFormat, topic);

          // Obtengo el contenido del feed (articulos) como XML o JSON
          HttpRequester requester = new HttpRequester();
          String rawFeed = requester.getFeed(finalUrl);
          requester.shutdown();

          // Declaro un archivo temporal
          java.nio.file.Path tempFile;
          
          tempFile = java.nio.file.Files.createTempFile("rssFeed", ".xml"); // inicializo el archivo temp
          RssParser parser = new RssParser(); // inicializo el parser
          java.nio.file.Files.writeString(tempFile, rawFeed); // pongo el contenido en el archivo
          parser.parseFile(tempFile.toString()); // parseo el archivo

          String siteName = singSub.getUrlType().toUpperCase() + " - " + topic;
          Feed feed = new Feed(siteName);

          for (Article article : parser.getArticlesArray()) { // agrego los articulos al feed
            feed.addArticle(article);
          }
          
          topicFeeds.add(feed);

        } catch (Exception e) {
          System.err.println("Error al procesar el feed RSS para el topic: " + topic);
          e.printStackTrace();
        }
      }
    }

    return topicFeeds;
  }

  /**
   * Construye y guarda la lista de feeds a partir de las suscripciones
   * proporcionadas por el parser y utilizando el requester para obtener
   * el contenido de cada feed.
   * <p>
   * Solo procesa suscripciones con tipo "rss". Para cada topico dentro
   * de una suscripcion, descarga y parsea el feed RSS correspondiente,
   * creando un objeto Feed con sus articulos y agregandolo a la lista.
   * </p>
   *
   * @param parser    objeto SubscriptionParser que contiene las suscripciones a
   *                  procesar
   * @param requester objeto HttpRequester para obtener el contenido de los feeds
   *                  RSS
   */
  public void BuildFeeds(List<SingleSubscription> subsList) {

    feedList.clear();

    // para cada suscripcion individual
    for (SingleSubscription subscription : subsList) {

      // Si es RSS o REDDIT entonces:
      String type = subscription.getUrlType().toLowerCase();
      if (type.equals("rss") /*|| type.equals("reddit")*/) { // lab 3 solo usa rss

        // Tomo la URL sin topicos
        String urlFormat = subscription.getUrl();

        // para cada topico
        for (String topic : subscription.getUrlParams()) {
          try {
            // Formo la URL con el topic
            String finalUrl = String.format(urlFormat, topic);

            // Obtengo el contenido del feed (articulos) como XML o JSON
            HttpRequester requester = new HttpRequester();
            String rawFeed = requester.getFeed(finalUrl);

            // Declaro un archivo temporal
            java.nio.file.Path tempFile;

            /* CASO FEED RSS */
            if (type.equals("rss")) {
              tempFile = java.nio.file.Files.createTempFile("rssFeed", ".xml"); // inicializo el archivo temp
              RssParser parser = new RssParser(); // inicializo el parser
              java.nio.file.Files.writeString(tempFile, rawFeed); // pongo el contenido en el archivo
              parser.parseFile(tempFile.toString()); // parseo el archivo

              String siteName = subscription.getUrlType().toUpperCase() + " - " + topic;
              Feed feed = new Feed(siteName);

              for (Article article : parser.getArticlesArray()) { // agrego los articulos al feed
                feed.addArticle(article);
              }

              this.feedList.add(feed); // agrego el feed a la feedlist
            }
            /*CASO FEED REDDIT:  Para el Lab 3 se simplifica el problema usando solo FeedRS
            else {
              tempFile = java.nio.file.Files.createTempFile("redditFeed", ".json");
              RedditParser parser = new RedditParser(); // A implementar
              java.nio.file.Files.writeString(tempFile, rawFeed);
              parser.parseFile(tempFile.toString());

              String siteName = subscription.getUrlType().toUpperCase() + " - " + topic;
              Feed feed = new Feed(siteName);

              for (Article article : parser.getArticlesArray()) { // agrego los articulos al feed
                feed.addArticle(article);
              }

              this.feedList.add(feed); // agrego el feed a la feedlist
            }
            */


          } catch (Exception e) {
            System.err.println("Error al procesar el feed RSS para el topic: " + topic);
            e.printStackTrace();
          }
        }
      }
    }
  }

  /**
   * Obtiene la lista de feeds construida.
   *
   * @return lista de objetos Feed
   */
  public List<Feed> getFeedList() {
    return feedList;
  }
}
