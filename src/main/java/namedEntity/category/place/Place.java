package namedEntity.category.place;

import namedEntity.category.*;
import namedEntity.topic.*;
import namedEntity.NamedEntity;

class Place extends NamedEntity {

  protected Country pais;
  protected City ciudad;
  protected Address direccion;
  protected OtherPlaces otro;

  public Place(String name, Category category, Topic topico, int frequency, Country pais, City ciudad,
      Address direccion,
      OtherPlaces otro) {
    super(name, category, topico, frequency);
    this.pais = pais;
    this.ciudad = ciudad;
    this.direccion = direccion;
    this.otro = otro;
  }

  public Country getPais() {
    return pais;
  }

  public void setPais(Country pais) {
    this.pais = pais;
  }

  public City getCiudad() {
    return ciudad;
  }

  public void setCiudad(City ciudad) {
    this.ciudad = ciudad;
  }

  public Address getDireccion() {
    return direccion;
  }

  public void setDireccion(Address direccion) {
    this.direccion = direccion;
  }

  public OtherPlaces getOtro() {
    return otro;
  }

  public void setOtro(OtherPlaces otro) {
    this.otro = otro;
  }

}
