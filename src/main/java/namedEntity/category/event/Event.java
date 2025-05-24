package namedEntity.category.event;

import namedEntity.category.date.Date;
import namedEntity.category.Category;
import namedEntity.topic.*;
import namedEntity.NamedEntity;

class Event extends NamedEntity {

  protected Boolean recurrente;
  protected String formaCanonica;
  protected Date fecha;

  public Event(String name, Category category, Topic topic, int frequency, Boolean recurrente, String formaCanonica,
      Date fecha) {
    super(name, category, topic, frequency);
    this.recurrente = recurrente;
    this.formaCanonica = formaCanonica;
    this.fecha = fecha;
  }

  public Boolean getRecurrente() {
    return recurrente;
  }

  public void setRecurrente(Boolean recurrente) {
    this.recurrente = recurrente;
  }

  public String getFormaCanonica() {
    return formaCanonica;
  }

  public void setFormaCanonica(String formaCanonica) {
    this.formaCanonica = formaCanonica;
  }

  public Date getFecha() {
    return fecha;
  }

  public void setFecha(Date fecha) {
    this.fecha = fecha;
  }

}
