package namedEntity.category.place;

class Country {

  protected Integer poblacion;
  protected String lenguaOficial;

  public Country(Integer poblacion, String lenguaOficial) {
    this.poblacion = poblacion;
    this.lenguaOficial = lenguaOficial;
  }

  public Integer getPoblacion() {
    return poblacion;
  }

  public void setPoblacion(Integer poblacion) {
    this.poblacion = poblacion;
  }

  public String getLenguaOficial() {
    return lenguaOficial;
  }

  public void setLenguaOficial(String lenguaOficial) {
    this.lenguaOficial = lenguaOficial;
  }

}
