package feed;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import namedEntity.NamedEntity;
import namedEntity.heuristic.Heuristic;
import utils.AnsiColors;

/**
 * Clase que modela el contenido de un articulo (por ejemplo, un item en un feed
 * RSS).
 */
public class Article implements Serializable {

  /** Titulo del articulo */
  private String title;

  /** Texto o contenido del articulo */
  private String text;

  /** Fecha de publicacion del articulo */
  private Date publicationDate;

  /** Enlace URL del articulo */
  private String link;

  /** Lista de entidades nombradas encontradas en el articulo */
  private List<NamedEntity> namedEntityList = new ArrayList<NamedEntity>();

  /**
   * Constructor que inicializa un articulo con titulo, texto, fecha y link.
   *
   * @param title           titulo del articulo
   * @param text            contenido o texto del articulo
   * @param publicationDate fecha de publicacion del articulo
   * @param link            enlace URL del articulo
   */
  public Article(String title, String text, Date publicationDate, String link) {
    super();
    this.title = title;
    this.text = text;
    this.publicationDate = publicationDate;
    this.link = link;
  }

  /**
   * Obtiene el titulo del articulo.
   *
   * @return titulo del articulo
   */
  public String getTitle() {
    return title;
  }

  /**
   * Establece el titulo del articulo.
   *
   * @param title titulo a establecer
   */
  public void setTitle(String title) {
    this.title = title;
  }

  /**
   * Obtiene el texto o contenido del articulo.
   *
   * @return texto del articulo
   */
  public String getText() {
    return text;
  }

  /**
   * Establece el texto o contenido del articulo.
   *
   * @param text texto a establecer
   */
  public void setText(String text) {
    this.text = text;
  }

  /**
   * Obtiene la fecha de publicacion del articulo.
   *
   * @return fecha de publicacion
   */
  public Date getPublicationDate() {
    return publicationDate;
  }

  /**
   * Establece la fecha de publicacion del articulo.
   *
   * @param publicationDate fecha a establecer
   */
  public void setPublicationDate(Date publicationDate) {
    this.publicationDate = publicationDate;
  }

  /**
   * Obtiene el enlace URL del articulo.
   *
   * @return enlace URL
   */
  public String getLink() {
    return link;
  }

  /**
   * Establece el enlace URL del articulo.
   *
   * @param link enlace a establecer
   */
  public void setLink(String link) {
    this.link = link;
  }

  /**
   * Representacion en forma de cadena del articulo.
   *
   * @return cadena con los atributos del articulo
   */
  @Override
  public String toString() {
    return "Article [title=" + title + ", text=" + text + ", publicationDate=" + publicationDate + ", link=" + link
        + "]";
  }

  /**
   * Busca una entidad nombrada por su nombre en la lista de entidades.
   *
   * @param namedEntity nombre de la entidad a buscar
   * @return la entidad nombrada si existe, null si no se encuentra
   */
  public NamedEntity getNamedEntity(String namedEntity) {
    for (NamedEntity n : namedEntityList) {
      if (n.getName().compareTo(namedEntity) == 0) {
        return n;
      }
    }
    return null;
  }

  /**
   * Calcula las entidades nombradas presentes en el titulo y texto del articulo
   * usando una heuristica dada.
   *
   * @param h heuristica para determinar si una palabra es una entidad nombrada
   */
  public void computeNamedEntities(Heuristic h) {
    String text = this.getTitle() + " " + this.getText();

    String charsToRemove = ".,;:()'!?\n";
    for (char c : charsToRemove.toCharArray()) {
      text = text.replace(String.valueOf(c), "");
    }

    for (String s : text.split(" ")) {
      if (h.isEntity(s)) {
        NamedEntity ne = this.getNamedEntity(s);
        if (ne == null) {
          this.namedEntityList.add(new NamedEntity(s, null, null, 1));
        } else {
          ne.incFrequency();
        }
      }
    }
  }

  public static List<NamedEntity> buildListOfNamedEntities(Article a, Heuristic h) {
    List<NamedEntity> lne = new ArrayList<NamedEntity>();
    String text = a.getTitle() + " " + a.getText();

    String charsToRemove = ".,;:()'!?\n";
    for (char c : charsToRemove.toCharArray()) {
      text = text.replace(String.valueOf(c), "");
    }

    for (String s : text.split(" ")) {
      if (h.isEntity(s)) {
        NamedEntity ne = a.getNamedEntity(s);
        if (ne == null) {
          lne.add(new NamedEntity(s, null, null, 1));
        } else {
          ne.incFrequency();
        }
      }
    }

    return lne;
  }

  /**
   * Imprime de forma legible el contenido del articulo.
   */
  public void prettyPrint() {
    String RESET = AnsiColors.RESET;
    String BLUE = AnsiColors.BLUE;
    String CYANB = AnsiColors.CYAN_BOLD;
    String GREEN = AnsiColors.GREEN;
    String CYAN = AnsiColors.CYAN;
    System.out.println(BLUE);
    System.out
        .println("*********************************************************************************");
    System.out.println(CYANB);
    System.out.println("Title: " + RESET + this.getTitle());
    System.out.println(CYANB);
    System.out.println("Publication Date: " + RESET + this.getPublicationDate());
    System.out.println(CYANB);
    System.out.println("Link: " + RESET + this.getLink());
    System.out.println(CYANB);
    System.out.println("Text: " + RESET + this.getText());
    System.out.println(BLUE);
    System.out
        .println("*******************************************************************************************");

    System.out.println(RESET);
  }

  /**
   * Metodo principal para probar la clase Article.
   *
   * @param args argumentos de linea de comandos (no usados)
   */
  public static void main(String[] args) {
    Article a = new Article("This Historically Black University Created Its Own Tech Intern Pipeline",
        "A new program at Bowie State connects computing students directly with companies, bypassing an often harsh Silicon Valley vetting process",
        new Date(),
        "https://www.nytimes.com/2023/04/05/technology/bowie-hbcu-tech-intern-pipeline.html");

    a.prettyPrint();
  }

  public List<NamedEntity> getNamedEntitiesList() {
    return namedEntityList;
  }

}
