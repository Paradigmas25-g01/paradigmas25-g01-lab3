package namedEntity.category.place;

import namedEntity.category.Category;
import namedEntity.topic.Topic;
import namedEntity.NamedEntity;

class OtherPlaces extends NamedEntity {
  public OtherPlaces(String name, Category category, Topic topic, int frequency) {
    super(name, category, topic, frequency);
  }

  protected String comentarios;

  public String getComentarios() {
    return comentarios;
  }

  public void setComentarios(String comentarios) {
    this.comentarios = comentarios;
  }
}
