package namedEntity.category;

import java.io.Serializable;

import namedEntity.NamedEntity;

public class Category implements Serializable { // POJO, plain old java object, clase <=> struct

  public String categoria;

  public Category(String categoria) {
    this.categoria = categoria;
  }

  public String getCategoria() {
    System.err.println("100200300aaa: " + categoria);
    return categoria;
  }

  public void setCategoria(String type) {
    this.categoria = type;
  }

}
