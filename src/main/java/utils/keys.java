package utils;

import java.util.Arrays;
import java.util.List;

public class keys {

  // Tabla de claves que tienen una entrada en el map, util para inicializar
  public static List<String> CATEGORY_KEYS = Arrays.asList(
      // <!--Categorias -->
      "Person",
      "Place",
      "Organization",
      "Product",
      "Event",
      "Date",
      // Otro
      "OtherCategories");

  public static String NAMED_ENTITY_KEY = "";

  public static List<String> TOPIC_KEYS = Arrays.asList(
      // <!-- Topicos -->
      // Deportes
      "Sports",
      "Football",
      "Basketball",
      "Tennis",
      "FormulaOne",
      "OtherSports",
      // Cultura
      "Culture",
      "Music",
      "Cinema",
      "OtherCultures",
      // Politics
      "Politics",
      "NationalPolitics",
      "InternationalPolitics",
      "OtherPolitics",
      // Otro
      "OtherTopics");
}
