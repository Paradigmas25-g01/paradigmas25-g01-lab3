package namedEntity.topic.sports;

import namedEntity.NamedEntity;
import namedEntity.topic.Topic;
import namedEntity.category.Category;

class Sports extends NamedEntity {

  public Sports(String name, Category category, Topic topic, int frequency) {
    super(name, category, topic, frequency);
  }

}
