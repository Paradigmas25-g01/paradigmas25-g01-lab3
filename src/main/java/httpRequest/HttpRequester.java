package httpRequest;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Clase encargada de realizar peticiones HTTP para obtener feeds de noticias.
 * 
 * Permite obtener contenido desde URLs que entregan datos en formato RSS o JSON
 * (como Reddit). También puede guardar las respuestas en archivos temporales.
 * 
 * Referencias para HTTP requests en Java:
 * https://www.baeldung.com/java-http-request
 * https://www.youtube.com/watch?v=tSKsDwsGVto
 */

public class HttpRequester {
  private final ExecutorService executor;
  private final HttpClient client;

  public HttpRequester() {

    int cores = Runtime.getRuntime().availableProcessors();
    int workers = cores;
    // System.out.println(workers);

    this.executor = Executors.newFixedThreadPool(workers);
    this.client = HttpClient.newBuilder()
        .executor(executor)
        .build();
  }

  public void shutdown() {
    executor.shutdown(); // libera los hilos
  }

  /**
   * Realiza una petición HTTP GET a la URL especificada.
   *
   * @param urlFeed URL desde la cual obtener el contenido
   * @return contenido recibido como string
   * @throws URISyntaxException   si la URL es inválida
   * @throws IOException          si ocurre un error de I/O
   * @throws InterruptedException si la operación es interrumpida
   */
  public String getFeed(String urlFeed) {
    HttpRequest request = null;
    HttpResponse<String> response = null;

    try {
      request = HttpRequest.newBuilder(new URI(urlFeed))
          .GET()
          .build();

      response = client.send(request, HttpResponse.BodyHandlers.ofString());
      return response.body(); // devolver solo si no hubo excepción
    } catch (URISyntaxException | IOException | InterruptedException e) {
      e.printStackTrace();
    }
    return ""; // en caso de error
  }

  /**
   * Realiza una petición HTTP GET a una URL de feed y guarda el contenido en un
   * archivo temporal.
   *
   * @param urlFeed  URL del feed (RSS o JSON)
   * @param feedType Tipo de feed: "RSS" o "JSON"
   * @return archivo temporal que contiene el contenido del feed
   * @throws IOException          si ocurre un error al escribir el archivo
   * @throws URISyntaxException   si la URL es inválida
   * @throws InterruptedException si la operación es interrumpida
   */
  public File getFeedToFile(String urlFeed, String feedType) {
    File tempfile = null;
    try {
      if (feedType.equals("RSS")) {
        tempfile = File.createTempFile("feedrss_", ".xml");
        try (FileWriter writer = new FileWriter(tempfile)) {
          writer.write(getFeedRss(urlFeed));
        }
      }
      if (feedType.equals("JSON")) {
        tempfile = File.createTempFile("feedreddit_", ".json");
        try (FileWriter writer = new FileWriter(tempfile)) {
          writer.write(getFeedReddit(urlFeed));
        }
      }
    } catch (Exception e) {
      // INFO: Mejorar handeleo
      e.printStackTrace();
    }
    // Lo hago temporal
    if (tempfile != null) {
      tempfile.deleteOnExit();
    }
    return tempfile;
  }

  /**
   * Obtiene el contenido de un feed RSS como texto XML.
   *
   * @param urlFeed URL del feed RSS
   * @return contenido XML del feed como String
   * @throws URISyntaxException   si la URL es inválida
   * @throws IOException          si ocurre un error de I/O
   * @throws InterruptedException si la operación es interrumpida
   */
  public String getFeedRss(String urlFeed) {
    return getFeed(urlFeed);
  }

  /**
   * Obtiene el contenido de un feed de Reddit (JSON) como String.
   *
   * @param urlFeed URL del feed de Reddit
   * @return contenido JSON del feed como String
   * @throws URISyntaxException   si la URL es inválida
   * @throws IOException          si ocurre un error de I/O
   * @throws InterruptedException si la operación es interrumpida
   */
  public String getFeedReddit(String urlFeed) {
    return getFeed(urlFeed);
  }

  /**
   * Descarga un feed RSS y lo guarda en un archivo temporal XML.
   *
   * @param urlFeed URL del feed RSS
   * @return archivo temporal con el contenido del feed RSS
   * @throws URISyntaxException   si la URL es inválida
   * @throws IOException          si ocurre un error al escribir el archivo
   * @throws InterruptedException si la operación es interrumpida
   */
  public File getFeedRssToFile(String urlFeed) {
    return getFeedToFile(urlFeed, "RSS");
  }

  /**
   * Descarga un feed de Reddit y lo guarda en un archivo temporal JSON.
   *
   * @param urlFeed URL del feed de Reddit
   * @return archivo temporal con el contenido del feed de Reddit
   * @throws URISyntaxException   si la URL es inválida
   * @throws IOException          si ocurre un error al escribir el archivo
   * @throws InterruptedException si la operación es interrumpida
   */
  public File getFeedReddiToFile(String urlFeed) {
    return getFeedToFile(urlFeed, "JSON");
  }

}
