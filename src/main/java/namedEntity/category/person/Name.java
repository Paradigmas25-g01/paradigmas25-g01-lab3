package namedEntity.category.person;

class Name {

  protected String formaCanonica;
  protected String origen;
  protected String formasAlternativas;

  public Name(String formaCanonica, String origen, String formasAlternativas) {
    this.formaCanonica = formaCanonica;
    this.origen = origen;
    this.formasAlternativas = formasAlternativas;
  }

  public String getformaCanonica() {
    return formaCanonica;
  }

  public void setformaCanonica(String forma_canonica) {
    this.formaCanonica = forma_canonica;
  }

  public String getOrigen() {
    return origen;
  }

  public void setOrigen(String origen) {
    this.origen = origen;
  }

  public String getFormasAlternativas() {
    return formasAlternativas;
  }

  public void setFormasAlternativas(String formasAlternativas) {
    this.formasAlternativas = formasAlternativas;
  }
}
