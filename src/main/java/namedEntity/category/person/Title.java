package namedEntity.category.person;

class Title {
  protected String formaCanonica;
  protected Boolean profesional;

  public Title(String formaCanonica, Boolean profesional) {
    this.formaCanonica = formaCanonica;
    this.profesional = profesional;
  }

  public String getFormaCanonica() {
    return formaCanonica;
  }

  public void setFormaCanonica(String formaCanonica) {
    this.formaCanonica = formaCanonica;
  }

  public Boolean getProfesional() {
    return profesional;
  }

  public void setProfesional(Boolean profesional) {
    this.profesional = profesional;
  }
}
