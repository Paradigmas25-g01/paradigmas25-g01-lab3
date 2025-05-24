package namedEntity.heuristic;

import java.util.HashMap;

import namedEntity.NamedEntity;
import namedEntity.category.*;
import namedEntity.topic.*;

public abstract class Heuristic {

  private static HashMap<String, NamedEntity> mapa = new HashMap<>();

  static {
    // Entradas Originales
    mapa.put("Microsft", new NamedEntity("Microsoft", new Category("Organization"), new Topic("OtherTopics"), 0));
    mapa.put("Apple", new NamedEntity("Apple", new Category("Organization"), new Topic("OtherTopics"), 0));
    mapa.put("Google", new NamedEntity("Google", new Category("Organization"), new Topic("Politics"), 0));
    mapa.put("Biden", new NamedEntity("Biden", new Category("Person"), new Topic("Politics"), 0));
    mapa.put("Trump", new NamedEntity("Trump", new Category("Person"), new Topic("Politics"), 0));
    mapa.put("Messi", new NamedEntity("Messi", new Category("Person"), new Topic("Football"), 0));
    mapa.put("Federer", new NamedEntity("Federer", new Category("Person"), new Topic("Tennis"), 0));
    mapa.put("United States", new NamedEntity("United States", new Category("Place"), new Topic("Politics"), 0));
    mapa.put("Russia", new NamedEntity("Russia", new Category("Place"), new Topic("Politics"), 0));

    // --- Nuevas Entidades ---

    // Organizations
    mapa.put("G7", new NamedEntity("G7", new Category("Organization"), new Topic("Politics"), 0));
    mapa.put("Art Basel", new NamedEntity("Art Basel", new Category("Organization"), new Topic("Culture"), 0));
    mapa.put("GOP", new NamedEntity("GOP", new Category("Organization"), new Topic("Politics"), 0));
    mapa.put("IRS",
        new NamedEntity("Internal Revenue Service", new Category("Organization"), new Topic("Politics"), 0));
    mapa.put("CATL", new NamedEntity("CATL", new Category("Organization"), new Topic("OtherTopics"), 0));
    mapa.put("Senate", new NamedEntity("Senate", new Category("Organization"), new Topic("Politics"), 0));
    mapa.put("G.M.", new NamedEntity("General Motors", new Category("Organization"), new Topic("OtherTopics"), 0));
    mapa.put("General Motors",
        new NamedEntity("General Motors", new Category("Organization"), new Topic("OtherTopics"), 0));
    mapa.put("23andMe", new NamedEntity("23andMe", new Category("Organization"), new Topic("OtherTopics"), 0));
    mapa.put("Regeneron Pharmaceuticals",
        new NamedEntity("Regeneron Pharmaceuticals", new Category("Organization"), new Topic("OtherTopics"), 0));
    mapa.put("Walmart", new NamedEntity("Walmart", new Category("Organization"), new Topic("OtherTopics"), 0));
    mapa.put("Mattel", new NamedEntity("Mattel", new Category("Organization"), new Topic("OtherTopics"), 0));
    mapa.put("Airbnb", new NamedEntity("Airbnb", new Category("Organization"), new Topic("OtherTopics"), 0));
    mapa.put("Netflix", new NamedEntity("Netflix", new Category("Organization"), new Topic("Culture"), 0));
    mapa.put("PBS", new NamedEntity("PBS", new Category("Organization"), new Topic("Culture"), 0));
    mapa.put("Sesame Workshop",
        new NamedEntity("Sesame Workshop", new Category("Organization"), new Topic("Culture"), 0));
    mapa.put("CBS News", new NamedEntity("CBS News", new Category("Organization"), new Topic("OtherTopics"), 0));
    mapa.put("European Union",
        new NamedEntity("European Union", new Category("Organization"), new Topic("Politics"), 0));
    mapa.put("EU", new NamedEntity("European Union", new Category("Organization"), new Topic("Politics"), 0));
    mapa.put("Moody's", new NamedEntity("Moody's", new Category("Organization"), new Topic("OtherTopics"), 0));
    mapa.put("Vox Media", new NamedEntity("Vox Media", new Category("Organization"), new Topic("OtherTopics"), 0));
    mapa.put("CNN", new NamedEntity("CNN", new Category("Organization"), new Topic("OtherTopics"), 0));
    mapa.put("Publix", new NamedEntity("Publix", new Category("Organization"), new Topic("OtherTopics"), 0));
    mapa.put("Capital One", new NamedEntity("Capital One", new Category("Organization"), new Topic("OtherTopics"), 0));
    mapa.put("Chase Bank", new NamedEntity("Chase Bank", new Category("Organization"), new Topic("OtherTopics"), 0));
    mapa.put("Paychex", new NamedEntity("Paychex", new Category("Organization"), new Topic("OtherTopics"), 0));
    mapa.put("The New York Times",
        new NamedEntity("The New York Times", new Category("Organization"), new Topic("OtherTopics"), 0));
    mapa.put("Federal Reserve",
        new NamedEntity("Federal Reserve", new Category("Organization"), new Topic("Politics"), 0));
    mapa.put("Starbucks", new NamedEntity("Starbucks", new Category("Organization"), new Topic("OtherTopics"), 0));
    mapa.put("Avelo Airlines",
        new NamedEntity("Avelo Airlines", new Category("Organization"), new Topic("OtherTopics"), 0));
    mapa.put("ICE", new NamedEntity("U.S. Immigration and Customs Enforcement", new Category("Organization"),
        new Topic("Politics"), 0));

    // Persons
    mapa.put("Scott Bessent", new NamedEntity("Scott Bessent", new Category("Person"), new Topic("Politics"), 0));
    mapa.put("Billy Long", new NamedEntity("Billy Long", new Category("Person"), new Topic("Politics"), 0));
    mapa.put("Elon Musk", new NamedEntity("Elon Musk", new Category("Person"), new Topic("OtherTopics"), 0));
    mapa.put("Wendy McMahon", new NamedEntity("Wendy McMahon", new Category("Person"), new Topic("OtherTopics"), 0));
    mapa.put("Bill Owens", new NamedEntity("Bill Owens", new Category("Person"), new Topic("OtherTopics"), 0));
    mapa.put("Kara Swisher", new NamedEntity("Kara Swisher", new Category("Person"), new Topic("OtherTopics"), 0));
    mapa.put("Scott Galloway", new NamedEntity("Scott Galloway", new Category("Person"), new Topic("OtherTopics"), 0));
    mapa.put("Niraj Chokshi", new NamedEntity("Niraj Chokshi", new Category("Person"), new Topic("OtherTopics"), 0));
    mapa.put("Jerome H. Powell", new NamedEntity("Jerome H. Powell", new Category("Person"), new Topic("Politics"), 0));
    mapa.put("Seth Miller", new NamedEntity("Seth Miller", new Category("Person"), new Topic("Politics"), 0));
    mapa.put("Duffy", new NamedEntity("Duffy", new Category("Person"), new Topic("Politics"), 0));

    // Places
    mapa.put("Europe", new NamedEntity("Europe", new Category("Place"), new Topic("Politics"), 0));
    mapa.put("Canada", new NamedEntity("Canada", new Category("Place"), new Topic("Politics"), 0));
    mapa.put("Qatar", new NamedEntity("Qatar", new Category("Place"), new Topic("Politics"), 0));
    mapa.put("Mideast", new NamedEntity("Mideast", new Category("Place"), new Topic("Politics"), 0));
    mapa.put("Persian Gulf", new NamedEntity("Persian Gulf", new Category("Place"), new Topic("Politics"), 0));
    mapa.put("Gulf", new NamedEntity("Gulf", new Category("Place"), new Topic("Politics"), 0));
    mapa.put("Hong Kong", new NamedEntity("Hong Kong", new Category("Place"), new Topic("Politics"), 0));
    mapa.put("China", new NamedEntity("China", new Category("Place"), new Topic("Politics"), 0));
    mapa.put("India", new NamedEntity("India", new Category("Place"), new Topic("Politics"), 0));
    mapa.put("Spain", new NamedEntity("Spain", new Category("Place"), new Topic("Politics"), 0));
    mapa.put("Windsor", new NamedEntity("Windsor", new Category("Place"), new Topic("OtherTopics"), 0));
    mapa.put("Ontario", new NamedEntity("Ontario", new Category("Place"), new Topic("OtherTopics"), 0));
    mapa.put("Miami", new NamedEntity("Miami", new Category("Place"), new Topic("OtherTopics"), 0));
    mapa.put("Minnesota", new NamedEntity("Minnesota", new Category("Place"), new Topic("Politics"), 0));
    mapa.put("Iron Range", new NamedEntity("Iron Range", new Category("Place"), new Topic("OtherTopics"), 0));
    mapa.put("Newark Airport",
        new NamedEntity("Newark Liberty International Airport", new Category("Place"), new Topic("OtherTopics"), 0));
    mapa.put("Newark Liberty International Airport",
        new NamedEntity("Newark Liberty International Airport", new Category("Place"), new Topic("OtherTopics"), 0));
    mapa.put("Ann Arbor", new NamedEntity("Ann Arbor", new Category("Place"), new Topic("OtherTopics"), 0));
    mapa.put("Mich.", new NamedEntity("Michigan", new Category("Place"), new Topic("OtherTopics"), 0));
    mapa.put("Michigan", new NamedEntity("Michigan", new Category("Place"), new Topic("OtherTopics"), 0));
    mapa.put("U.A.E.", new NamedEntity("United Arab Emirates", new Category("Place"), new Topic("Politics"), 0));
    mapa.put("United Arab Emirates",
        new NamedEntity("United Arab Emirates", new Category("Place"), new Topic("Politics"), 0));
    mapa.put("Africa", new NamedEntity("Africa", new Category("Place"), new Topic("Politics"), 0));
    mapa.put("New Hampshire", new NamedEntity("New Hampshire", new Category("Place"), new Topic("Politics"), 0));

    // Products
    mapa.put("Instagram", new NamedEntity("Instagram", new Category("Product"), new Topic("OtherTopics"), 0));
    mapa.put("SPAC",
        new NamedEntity("Special Purpose Acquisition Company", new Category("Product"), new Topic("OtherTopics"), 0));
    mapa.put("Sesame Street", new NamedEntity("Sesame Street", new Category("Product"), new Topic("Culture"), 0));
    mapa.put("60 Minutes", new NamedEntity("60 Minutes", new Category("Product"), new Topic("OtherTopics"), 0));
    mapa.put("Pivot", new NamedEntity("Pivot", new Category("Product"), new Topic("OtherTopics"), 0));
    mapa.put("A.I.", new NamedEntity("Artificial Intelligence", new Category("Product"), new Topic("OtherTopics"), 0));
    mapa.put("Artificial Intelligence",
        new NamedEntity("Artificial Intelligence", new Category("Product"), new Topic("OtherTopics"), 0));
    mapa.put("Gemini", new NamedEntity("Gemini", new Category("Product"), new Topic("OtherTopics"), 0));
    mapa.put("401(k)", new NamedEntity("401(k)", new Category("Product"), new Topic("OtherTopics"), 0));
    mapa.put("crypto", new NamedEntity("Cryptocurrency", new Category("Product"), new Topic("OtherTopics"), 0));
    mapa.put("Cryptocurrency", new NamedEntity("Cryptocurrency", new Category("Product"), new Topic("OtherTopics"), 0));

    // Events
    mapa.put("Memorial Day Weekend",
        new NamedEntity("Memorial Day Weekend", new Category("Event"), new Topic("OtherTopics"), 0));
    mapa.put("Trade War", new NamedEntity("Trade War", new Category("Event"), new Topic("Politics"), 0));
    mapa.put("Housing Crunch", new NamedEntity("Housing Crunch", new Category("Event"), new Topic("OtherTopics"), 0));
    mapa.put("Confirmation Hearing",
        new NamedEntity("Confirmation Hearing", new Category("Event"), new Topic("Politics"), 0));

    // OtherCategories / OtherTopics
    mapa.put("tariffs", new NamedEntity("Tariffs", new Category("OtherCategories"), new Topic("Politics"), 0));
  }

  public abstract boolean isEntity(String word);

  public NamedEntity getNamedEntity(String entity) {
    return mapa.get(entity);
  }

}
