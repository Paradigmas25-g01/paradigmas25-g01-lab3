package parser;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.jsoup.Jsoup;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import feed.Article;
import httpRequest.HttpRequester;
import subscription.SingleSubscription;
import org.xml.sax.InputSource;
import java.io.StringReader;

/**
 * Esta clase implementa un parser para feeds de tipo RSS (formato XML).
 *
 * Utiliza DOM parser para leer los elementos "item" del XML RSS y extraer
 * la información relevante de cada artículo (título, descripción, fecha de
 * publicación, enlace).
 *
 * Hereda de {@link GeneralParser} y sobrescribe el método {@code parseFile}.
 *
 * @see <a href=
 *      "https://www.tutorialspoint.com/java_xml/java_dom_parse_document.htm">Tutorial
 *      DOM Parsing</a>
 */

// mire esto para hacerlo, por si lo quieren ver
// https://stackoverflow.com/questions/2705548/parse-rss-pubdate-to-date-object-in-java
// https://www.youtube.com/watch?v=HfGWVy-eMRc
// https://www.youtube.com/watch?v=2JH5YeQ68H8

public class RssParser extends GeneralParser {
  /**
   * Lista que contiene los artículos extraídos del feed RSS.
   */
  protected List<Article> articles = new ArrayList<>();

  /**
   * Obtiene la lista de artículos parseados.
   *
   * @return lista de objetos {@link Article}
   */
  public List<Article> getArticlesArray() {
    return this.articles;
  };

  /**
   * Parsea un archivo XML de feed RSS y extrae los artículos.
   *
   * Cada artículo se representa con un objeto {@link Article} que contiene
   * título, descripción, fecha de publicación y enlace.
   *
   * El formato esperado para la fecha es: "EEE, dd MMM yyyy HH:mm:ss Z" en
   * inglés.
   *
   * @param filePath ruta al archivo XML del feed RSS
   */
  @Override
  public void parseFile(String filePath) {
    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
    try {
      DocumentBuilder docBuilder = factory.newDocumentBuilder();
      Document doc = docBuilder.parse(filePath);
      NodeList items = doc.getElementsByTagName("item");

      for (int i = 0; i < items.getLength(); i++) {
        Node itemNode = items.item(i);

        if (itemNode.getNodeType() == Node.ELEMENT_NODE) {
          Element item = (Element) itemNode;

          String title = item.getElementsByTagName("title").item(0).getTextContent();
          String description = item.getElementsByTagName("description").item(0).getTextContent().trim();
          String pubDate = item.getElementsByTagName("pubDate").item(0).getTextContent();
          String link = item.getElementsByTagName("link").item(0).getTextContent();

          DateFormat formatter = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss Z", Locale.ENGLISH);
          Date date = formatter.parse(pubDate);

          // uso jsoup para parsea el posible html (solo en algunos casos)
          org.jsoup.nodes.Document d = Jsoup.parse(description);

          // me fijo si hay un parrafo con alguna descripcion
          org.jsoup.nodes.Element snippet = d.selectFirst("p.medium-feed-snippet");
          description = (snippet != null) ? snippet.text() : d.text();

          Article article = new Article(title, description, date, link);
          articles.add(article);
        }
      }

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  // Sacada de stack overflow
  public static Document loadXMLFromString(String xml) throws Exception {
    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
    DocumentBuilder builder = factory.newDocumentBuilder();
    InputSource is = new InputSource(new StringReader(xml));
    return builder.parse(is);
  }

  // Hago una funcion pura que lea el XML como string y devuelva una lista de
  // articulos
  public List<Article> parseRssFromString(String xmlString) {
    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
    // La lista que devuelvo
    List<Article> listArt = new ArrayList<Article>();

    try {
      DocumentBuilder docBuilder = factory.newDocumentBuilder();
      Document doc = loadXMLFromString(xmlString);
      NodeList items = doc.getElementsByTagName("item");

      for (int i = 0; i < items.getLength(); i++) {
        Node itemNode = items.item(i);

        if (itemNode.getNodeType() == Node.ELEMENT_NODE) {
          Element item = (Element) itemNode;

          String title = item.getElementsByTagName("title").item(0).getTextContent();
          String description = item.getElementsByTagName("description").item(0).getTextContent().trim();
          String pubDate = item.getElementsByTagName("pubDate").item(0).getTextContent();
          String link = item.getElementsByTagName("link").item(0).getTextContent();

          DateFormat formatter = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss Z", Locale.ENGLISH);
          Date date = formatter.parse(pubDate);

          // uso jsoup para parsea el posible html (solo en algunos casos)
          org.jsoup.nodes.Document d = Jsoup.parse(description);

          // me fijo si hay un parrafo con alguna descripcion
          org.jsoup.nodes.Element snippet = d.selectFirst("p.medium-feed-snippet");
          description = (snippet != null) ? snippet.text() : d.text();

          Article article = new Article(title, description, date, link);
          listArt.add(article); // Lo agrego a la lista
        }
      }

    } catch (Exception e) {
      e.printStackTrace();
    }
    return listArt;
  }

  /**
   * Método principal para pruebas.
   *
   * Lee un archivo de configuración de suscripciones y luego realiza
   * una petición HTTP para obtener un feed Reddit con parámetros formateados.
   *
   * @param args argumentos de línea de comandos (no utilizados)
   */
  public static void main(String[] args) {
    SubscriptionParser subParser = new SubscriptionParser();
    subParser.parseFile("config/subscriptions.json");

    // Imprimo cada suscripcion de la lista de suscripciones
    for (SingleSubscription singSub : subParser.getSubscriptions()) {
      singSub.prettyPrint();
    }

    // Harcodeo 1 rss
    int idx_sub = 0; // indice para la subscription
    int idx_topic = 0; // indice para el topic (urlParam)

    // Armo el url
    String urlFormat, urlFinal, topic;
    SingleSubscription singSub = subParser.getSingleSubscription(idx_sub);
    urlFormat = singSub.getUrl();
    topic = singSub.getUrlParams(idx_topic);
    urlFinal = String.format(urlFormat, topic);

    // Lo mando a un archivo
    HttpRequester requester = new HttpRequester();
    File xmlResponse = requester.getFeedRssToFile(urlFinal);

    // Lo parseo desde el archivo
    String xmlPath = xmlResponse.getPath();
    RssParser rssParser = new RssParser();
    rssParser.parseFile(xmlPath);

    // Imprimo lo parseado
    for (Article xmlArticle : rssParser.getArticlesArray())
      xmlArticle.prettyPrint();
  }
}
