package namedEntity.category;

import namedEntity.NamedEntity;

public class Category { // POJO, plain old java object, clase <=> struct

  public String categoria;

  public Category(String categoria) {
    this.categoria = categoria;
  }

  public String getCategoria() {
    return categoria;
  }

  public void setCategoria(String type) {
    this.categoria = type;
  }

}
