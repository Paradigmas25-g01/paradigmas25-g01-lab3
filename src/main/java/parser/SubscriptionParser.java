package parser;

import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import subscription.SingleSubscription;
import subscription.Subscription;

import java.util.List;

/**
 * Me encargo de parsear un archivo JSON con una lista de suscripciones
 * Convierto cada objeto JSON en una instancia de Subscription y las almaceno en
 * una lista
 */
public class SubscriptionParser extends JsonParser {

  /**
   * Lista de objetos Subscription que represento como un arreglo de cuatruplas
   */
  private Subscription subsList;

  // INFO: Esta bien usar JSONObjects aca? Es romper alguna abstraccion?
  /**
   * @param path ruta del archivo JSON a leer
   * 
   */
  @Override
  public void parseFile(String path) {
    super.parseFile(path); // genero el jsonArray

    // Obtengo la lista de suscripciones del parser
    this.subsList = new Subscription(path);

    // Para cada suscripcion, hago
    for (int i = 0; i < this.jsonArr.length(); i++) {
      // Agarro el jsonObject en la posicion i
      JSONObject json = this.jsonArr.getJSONObject(i);

      // Extraigo campos url y urlType de una
      String url = json.getString("url");
      String urlType = json.getString("urlType");

      // Para la urlParams es una lista de Strings (JsonArray), lo paso
      // a una lista de strings
      JSONArray paramsJsonArray = json.getJSONArray("urlParams");

      // Convierto el jsonArray de Strings a una lista de Strings
      List<String> urlParamsList = new ArrayList<>();
      for (int j = 0; j < paramsJsonArray.length(); j++) {
        urlParamsList.add(paramsJsonArray.getString(j));
      }

      // Para debugear
      // System.out.println("DEBUG\n");
      // System.out.println(urlParamsList.toString());

      // Creo la suscripcion y la agrego a la lista de suscripciones
      SingleSubscription singleSub = new SingleSubscription(url, urlParamsList, urlType);
      this.subsList.addSingleSubscription(singleSub);
    }
  }

  /**
   * Devuelvo la lista de subscriptions parseada
   */
  public List<SingleSubscription> getSubscriptions() {
    return this.subsList.getSubscriptionsList();
  }

  /**
   * Devuelvo la subscripcion i
   *
   * @param indice del single subscription a obtener de la lista de suscripciones
   */
  public SingleSubscription getSingleSubscription(int i) {
    List<SingleSubscription> subsList = this.subsList.getSubscriptionsList();
    return subsList.get(i);
  }

  /**
   * Devuelve la longitud de la lista de suscripciones
   */
  public int getLength() {
    List<SingleSubscription> subsList = this.subsList.getSubscriptionsList();
    return subsList.size();
  }

}
