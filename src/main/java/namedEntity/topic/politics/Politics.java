package namedEntity.topic.politics;
import namedEntity.NamedEntity;
import namedEntity.topic.Topic;
import namedEntity.category.Category;

public class Politics extends NamedEntity {

  public Politics(String name, Category category, Topic topic, int frequency) {
    super(name, category, topic, frequency);
  }

}
