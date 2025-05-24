package parser;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import feed.Article;

/**
 * Esta clase implementa un parser para feeds de tipo Reddit (formato JSON).
 *
 * Utiliza org.json para leer los elementos del feed y extraer
 * la información relevante de cada post (título, descripción, fecha y enlace).
 *
 * Hereda de {@link GeneralParser} y sobrescribe el método {@code parseFile}.
 */
public class RedditParser extends GeneralParser {
  /**
   * Lista que contiene los artículos extraídos del feed Reddit.
   */
  protected List<Article> articles = new ArrayList<>();

  /**
   * Obtiene la lista de artículos parseados.
   *
   * @return lista de objetos {@link Article}
   */
  public List<Article> getArticlesArray() {
    return this.articles;
  }

  /**
   * Parsea un archivo JSON de feed Reddit y extrae los artículos.
   *
   * Cada artículo se representa con un objeto {@link Article} que contiene
   * título, descripción, fecha de publicación y enlace.
   *
   * @param filePath ruta al archivo JSON del feed Reddit
   */
  @Override
  public void parseFile(String filePath) {
    try {
      // Leer el contenido del archivo JSON como texto
      String jsonText = Files.readString(Paths.get(filePath));

      // Convertir a objeto JSON
      JSONObject root = new JSONObject(new JSONTokener(jsonText));
      JSONArray posts = root.getJSONObject("data").getJSONArray("children");

      for (int i = 0; i < posts.length(); i++) {
        JSONObject postData = posts.getJSONObject(i).getJSONObject("data");

        String title = postData.optString("title", "(sin título)");
        String description = postData.optString("selftext", "").trim(); // obtenemos el texto
        
        // LIMITAMOS EL LARGO DEL TEXTO
        int maxLength = 300;
        int cutIndex = description.indexOf("\n"); // busco la posicion del primer salto de linea
        if (cutIndex == -1 || cutIndex > maxLength) { // si no hay salto de linea antes del maxlength cortamos en el maxlength
            cutIndex = Math.min(description.length(), maxLength);
        }
        if (description.length() > cutIndex) { // si hay un salto de linea antes del maxlength, lo cortamos luego del salto
            description = description.substring(0, cutIndex).trim() + "...";
        }

        String url = postData.optString("url", "");
        long createdUtc = postData.optLong("created_utc", 0);
        Date pubDate = new Date(createdUtc * 1000); // convertir a milisegundos

        if(!title.isEmpty() && !description.isEmpty() && pubDate != null && !url.isEmpty()){
        Article article = new Article(title, description, pubDate, url);
        articles.add(article);
        }
      }

    } catch (IOException e) {
      System.err.println("Error al leer el archivo JSON: " + e.getMessage());
    } catch (Exception e) {
      System.err.println("Error al parsear el feed Reddit: " + e.getMessage());
      e.printStackTrace();
    }
  }
}
