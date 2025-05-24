package namedEntity.category.organization;

import namedEntity.category.*;
import namedEntity.topic.*;
import namedEntity.NamedEntity;

public class Organization extends NamedEntity {

  public Organization(String name, Category category, Topic topic, int frequency, String formaCanonica,
      Integer numeroMiembros,
      String tipoOrganizacion) {
    super(name, category, topic, frequency);
    this.formaCanonica = formaCanonica;
    this.numeroMiembros = numeroMiembros;
    this.tipoOrganizacion = tipoOrganizacion;
  }

  protected String formaCanonica;
  protected Integer numeroMiembros;
  protected String tipoOrganizacion;

  public String getformaCanonica() {
    return formaCanonica;
  }

  public void setformaCanonica(String formaCanonica) {
    this.formaCanonica = formaCanonica;
  }

  public Integer getnumeroMiembros() {
    return numeroMiembros;
  }

  public void setnumeroMiembros(Integer numeroMiembros) {
    this.numeroMiembros = numeroMiembros;
  }

  public String gettipoOrganización() {
    return tipoOrganizacion;
  }

  public void settipoOrganización(String tipoOrganización) {
    this.tipoOrganizacion = tipoOrganización;
  }

}
