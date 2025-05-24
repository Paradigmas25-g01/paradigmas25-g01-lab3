package namedEntity.category.product;

import namedEntity.category.*;
import namedEntity.topic.*;
import namedEntity.NamedEntity;

class Product extends NamedEntity {

  protected Boolean comercial;
  protected String productor;

  public Product(String name, Category category, Topic topic, int frequency, Boolean comercial, String productor) {
    super(name, category, topic, frequency);
    this.comercial = comercial;
    this.productor = productor;
  }

  public Boolean getComercial() {
    return comercial;
  }

  public void setComercial(Boolean comercial) {
    this.comercial = comercial;
  }

  public String getProductor() {
    return productor;
  }

  public void setProductor(String productor) {
    this.productor = productor;
  }

}
