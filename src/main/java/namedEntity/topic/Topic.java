package namedEntity.topic;

public class Topic { // POJO, plain old java object, clase <=> struct
  public String topic;

  public Topic(String topic) {
    this.topic = topic;
  }

  public String getType() {
    return topic;
  }

  public void setType(String type) {
    this.topic = topic;
  }

}
