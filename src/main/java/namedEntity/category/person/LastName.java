package namedEntity.category.person;

class LastName {
  protected String formaCanonica;
  protected String origen;

  public LastName(String formaCanonica, String origen) {
    this.formaCanonica = formaCanonica;
    this.origen = origen;
  }

  public String getFormaCanonica() {
    return formaCanonica;
  }

  public void setFormaCanonica(String formaCanonica) {
    this.formaCanonica = formaCanonica;
  }

  public String getOrigen() {
    return origen;
  }

  public void setOrigen(String origen) {
    this.origen = origen;
  }
}
