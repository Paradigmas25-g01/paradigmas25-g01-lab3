package namedEntity;

import java.io.Serializable;

import namedEntity.category.Category;
import namedEntity.topic.Topic;

/**
 * Esta clase modela la nocion de entidad nombrada.
 * Una entidad nombrada tiene un nombre, una categoria y una frecuencia de
 * aparicion.
 */
public class NamedEntity implements Serializable{
  public NamedEntity(String name, Category category, Topic topic, int frequency) {
    this.name = name;
    this.category = category;
    this.topic = topic;
    this.frequency = frequency;
  }

  private String name;
  private Category category;
  private Topic topic;

  private int frequency;

  /**
   * Constructor de NamedEntity.
   *
   * @param name      nombre de la entidad
   * @param category  categoria o tipo de la entidad
   * @param frequency cantidad de apariciones de la entidad
   */

  /**
   * Obtiene el nombre de la entidad.
   *
   * @return el nombre
   */
  public String getName() {
    return name;
  }

  /**
   * Establece el nombre de la entidad.
   *
   * @param name nuevo nombre
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * Obtiene la categoria de la entidad.
   *
   * @return la categoria
   */
  public String getCategory() {
    String cat =  category.getCategoria();
    return (cat != null ? cat : "null");
  }

  public Category getCategoryObject() { // Getter para el objeto Category
        return this.category;
    }

  /**
   * Establece la categoria de la entidad.
   *
   * @param category nueva categoria
   */
  public void setCategory(Category category) {
    this.category = category;
  }



  /**
   * Obtiene la frecuencia de aparicion de la entidad.
   *
   * @return la frecuencia
   */
  public int getFrequency() {
    return frequency;
  }

  /**
   * Establece la frecuencia de aparicion de la entidad.
   *
   * @param frequency nueva frecuencia
   */
  public void setFrequency(int frequency) {
    this.frequency = frequency;
  }

  /**
   * Incrementa en 1 la frecuencia de aparicion de la entidad.
   */
  public void incFrequency() {
    this.frequency++;
  }

  public String getTopic() { // Este es el que te da la representación String
        if (this.topic != null) { // ¡Importante verificar por null aquí!
            return this.topic.getType();
        }
        return "UnknownTopic"; // O null, o lanzar excepción
    }

    public Topic getTopicObject() { // Getter para el objeto Topic
        return this.topic;
    }

  @Override
  public String toString() {
    return "NamedEntity [name=" + name + ", category=" + category + "frequency=" + frequency + "]";
  }

  /**
   * Imprime en consola el nombre y frecuencia de la entidad.
   */
  public void prettyPrint() {
    System.out.println(name + " [" + category + "] → " + frequency);
  }

  public static boolean equals(NamedEntity a, NamedEntity b) {
    if (a == b)
      return true;
    if (a == null || b == null)
      return false;

    return a.getName().equals(b.getName()) &&
        a.getCategory().equals(b.getCategory());
  }

}
