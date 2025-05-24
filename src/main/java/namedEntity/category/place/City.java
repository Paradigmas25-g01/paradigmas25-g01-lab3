package namedEntity.category.place;

class City {

  protected Country pais;
  protected City capital;
  protected Integer poblacion;

  public City(Country pais, City capital, Integer poblacion) {
    this.pais = pais;
    this.capital = capital;
    this.poblacion = poblacion;
  }

  public Country getPais() {
    return pais;
  }

  public void setPais(Country pais) {
    this.pais = pais;
  }

  public City getCapital() {
    return capital;
  }

  public void setCapital(City capital) {
    this.capital = capital;
  }

  public Integer getPoblacion() {
    return poblacion;
  }

  public void setPoblacion(Integer poblacion) {
    this.poblacion = poblacion;
  }

}
