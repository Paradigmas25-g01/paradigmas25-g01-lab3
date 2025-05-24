package namedEntity.category.date;

import namedEntity.category.*;
import namedEntity.topic.*;
import namedEntity.NamedEntity;

public class Date extends NamedEntity {
  protected String formaCanonica;
  protected String precisa;

  public Date(String name, Category category, Topic topic, int frequency, String formaCanonica, String precisa) {
    super(name, category, topic, frequency);
    this.formaCanonica = formaCanonica;
    this.precisa = precisa;
  }

  public String getFormaCanonica() {
    return formaCanonica;
  }

  public void setFormaCanonica(String formaCanonica) {
    this.formaCanonica = formaCanonica;
  }

  public String getPrecisa() {
    return precisa;
  }

  public void setPrecisa(String precisa) {
    this.precisa = precisa;
  }
}
