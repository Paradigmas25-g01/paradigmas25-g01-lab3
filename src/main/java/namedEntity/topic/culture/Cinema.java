package namedEntity.topic.culture;

import namedEntity.category.*;
import namedEntity.topic.*;

class Cine extends Culture {

  protected String genero;

  public Cine(String name, Category category, Topic topic, int frequency, String genero) {
    super(name, category, topic, frequency);
    this.genero = genero;
  }


  public String getGenero() {
    return genero;
  }

  public void setGenero(String genero) {
    this.genero = genero;
  }

}
