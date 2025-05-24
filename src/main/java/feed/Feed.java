package feed;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Clase que modela la lista de articulos de un determinado feed.
 */
public class Feed {

  /** Nombre del sitio o fuente del feed */
  String siteName;

  /** Lista de articulos del feed */
  List<Article> articleList;

  /**
   * Constructor que inicializa el feed con un nombre de sitio.
   *
   * @param siteName nombre del sitio o fuente del feed
   */
  public Feed(String siteName) {
    super();
    this.siteName = siteName;
    this.articleList = new ArrayList<Article>();
  }

  /**
   * Obtiene el nombre del sitio del feed.
   *
   * @return nombre del sitio
   */
  public String getSiteName() {
    return siteName;
  }

  /**
   * Establece el nombre del sitio del feed.
   *
   * @param siteName nombre del sitio a establecer
   */
  public void setSiteName(String siteName) {
    this.siteName = siteName;
  }

  /**
   * Obtiene la lista de articulos del feed.
   *
   * @return lista de articulos
   */
  public List<Article> getArticleList() {
    return articleList;
  }

  /**
   * Establece la lista de articulos del feed.
   *
   * @param articleList lista de articulos a establecer
   */
  public void setArticleList(List<Article> articleList) {
    this.articleList = articleList;
  }

  /**
   * Agrega un articulo a la lista de articulos del feed.
   *
   * @param a articulo a agregar
   */
  public void addArticle(Article a) {
    this.getArticleList().add(a);
  }

  /**
   * Obtiene un articulo en una posicion especifica de la lista.
   *
   * @param i indice del articulo
   * @return articulo en la posicion i
   */
  public Article getArticle(int i) {
    return this.getArticleList().get(i);
  }

  /**
   * Obtiene la cantidad de articulos que tiene el feed.
   *
   * @return numero de articulos
   */
  public int getNumberOfArticles() {
    return this.getArticleList().size();
  }

  /**
   * Representacion en forma de cadena del feed.
   *
   * @return cadena con nombre del sitio y lista de articulos
   */
  @Override
  public String toString() {
    return "Feed [siteName=" + getSiteName() + ", articleList=" + getArticleList() + "]";
  }

  /**
   * Imprime de forma legible los articulos del feed.
   */
  public void prettyPrint() {
    for (Article a : this.getArticleList()) {
      a.prettyPrint();
    }
  }

  /**
   * Metodo principal para probar la clase Feed.
   *
   * @param args argumentos de linea de comandos (no usados)
   */
  public static void main(String[] args) {
    Article a1 = new Article("This Historically Black University Created Its Own Tech Intern Pipeline",
        "A new program at Bowie State connects computing students directly with companies, bypassing an often harsh Silicon Valley vetting process",
        new Date(),
        "https://www.nytimes.com/2023/04/05/technology/bowie-hbcu-tech-intern-pipeline.html");

    Article a2 = new Article("This Historically Black University Created Its Own Tech Intern Pipeline",
        "A new program at Bowie State connects computing students directly with companies, bypassing an often harsh Silicon Valley vetting process",
        new Date(),
        "https://www.nytimes.com/2023/04/05/technology/bowie-hbcu-tech-intern-pipeline.html");

    Article a3 = new Article("This Historically Black University Created Its Own Tech Intern Pipeline",
        "A new program at Bowie State connects computing students directly with companies, bypassing an often harsh Silicon Valley vetting process",
        new Date(),
        "https://www.nytimes.com/2023/04/05/technology/bowie-hbcu-tech-intern-pipeline.html");

    Feed f = new Feed("nytimes");
    f.addArticle(a1);
    f.addArticle(a2);
    f.addArticle(a3);

    f.prettyPrint();

  }

}
