package namedEntity.category.other;

import namedEntity.category.Category;
import namedEntity.topic.Topic;
import namedEntity.NamedEntity;

class OtherCategories extends NamedEntity {
  public OtherCategories(String name, Category category, Topic topic, int frequency) {
    super(name, category, topic, frequency);
  }

  protected String comentarios;
}
