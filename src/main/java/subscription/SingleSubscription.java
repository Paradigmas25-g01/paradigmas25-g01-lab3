package subscription;

import java.util.ArrayList;
import java.util.List;

import utils.AnsiColors;

/*Esta clse abstrae el contenido de una sola suscripcion que ocurre en lista de suscripciones que figuran en el archivo de suscrpcion(json) */
public class SingleSubscription {

  private String url;
  private List<String> urlParams;
  private String urlType;

  public SingleSubscription(String url, List<String> urlParams, String urlType) { // POJO
    super();
    this.url = url;
    this.urlParams = urlParams;
    this.urlType = urlType;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public List<String> getUrlParams() {
    return urlParams;
  }

  public String getUrlParams(int i) {
    return this.urlParams.get(i);
  }

  public void setUrlParams(String urlParam) {
    this.urlParams.add(urlParam);
  }

  public int getUrlParamsSize() {
    return urlParams.size();
  }

  public String getUrlType() {
    return urlType;
  }

  public void setUrlType(String urlType) {
    this.urlType = urlType;
  }

  @Override
  public String toString() {
    return "{url=" + getUrl() + ", urlParams=" + getUrlParams().toString() + ", urlType=" + getUrlType() + "}";
  }

  public void prettyPrint() {
    StringBuilder paramsBuilder = new StringBuilder();
    int numberOfParams = this.getUrlParamsSize();
    for (int i = 0; i < numberOfParams; i++) {
      String paramAtIndex = this.getUrlParams(i);
      if (paramAtIndex != null) {
        paramsBuilder.append(AnsiColors.YELLOW_BOLD);
        paramsBuilder.append("\"").append(paramAtIndex).append("\"");
        paramsBuilder.append(AnsiColors.RESET);
        if (i < numberOfParams - 1) {
          paramsBuilder.append(", ");
        }
      }
    }
    System.out.println(
        AnsiColors.CYAN + "Subscription" + AnsiColors.RED + "[" + AnsiColors.RESET +
            "url=" + AnsiColors.GREEN + getUrl() + AnsiColors.RESET +
            ", urlType=" + AnsiColors.PURPLE + getUrlType() + AnsiColors.RESET +
            ", params = [" + paramsBuilder.toString() + "]" + AnsiColors.RED + "]" + AnsiColors.RESET);
  }

  public String getFeedToRequest(int i) {
    return this.getUrl().replace("%s", this.getUrlParams(i));
  }

  public static void main(String[] args) {
    System.out.println("SingleSubscriptionClass");
    SingleSubscription s = new SingleSubscription("https://rss.nytimes.com/services/xml/rss/nyt/%s.xml", null, "rss");
    s.setUrlParams("Business");
    s.setUrlParams("Technology");
    System.out.println(s.getFeedToRequest(0));
    s.prettyPrint();
  }

}
