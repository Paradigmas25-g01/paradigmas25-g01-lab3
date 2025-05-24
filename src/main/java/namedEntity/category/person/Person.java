package namedEntity.category.person;

import namedEntity.category.*;
import namedEntity.NamedEntity;
import namedEntity.topic.*;

class Person extends NamedEntity {

  protected Name nombre;
  protected LastName apellido;
  protected String title;

  protected String id;

  public Person(String name, Category category, Topic topico, int frequency, String id, Name nombre,
      LastName apellido) {
    super(name, category, topico, frequency);
    this.id = id;
    this.nombre = nombre;
    this.apellido = apellido;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public Name getNombre() {
    return nombre;
  }

  public void setNombre(Name nombre) {
    this.nombre = nombre;
  }

  public LastName getApellido() {
    return apellido;
  }

  public void setApellido(LastName apellido) {
    this.apellido = apellido;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

}
