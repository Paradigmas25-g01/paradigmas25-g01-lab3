package utils;

import java.util.Arrays;
import java.util.List;

/**
 * Clase que representa los argumentos del programa FeedReader.
 * Permite parsear los flags relacionados con la activacion de
 * la funcionalidad de entidades nombradas y la heuristica a usar. 
 * */
public class Arguments {

  /**
   * Tipo de heuristica a utilizar para deteccion de entidades nombradas.
   */
  public enum HeuristicType {
    /** Heuristica rapida por defecto */
    QUICK,
    /** Heuristica aleatoria */
    RANDOM
  }

  /** Indica si se debe usar la funcionalidad de entidades nombradas */
  public boolean useNamedEntities;

  /** La heuristica seleccionada para el analisis */
  public HeuristicType heuristic;

  /**
   * Parse los argumentos de linea de comandos para determinar las opciones
   * relacionadas con las entidades nombradas.
   *
   * @param args Array de argumentos de linea de comandos.
   * @return Un objeto {@code Arguments} con los flags configurados.
   * @throws IllegalArgumentException si la combinacion de argumentos es invalida.
   *
   * <p>Valid flags:
   * <ul>
   *   <li>No args: no se usa entidades nombradas.</li>
   *   <li>-ne: usa heuristica rapida (QuickHeuristic).</li>
   *   <li>-ne -rh: usa heuristica aleatoria (RandomHeuristic).</li>
   * </ul>
   * </p>
   */
  public static Arguments parse(String[] args) throws IllegalArgumentException {
    List<String> argumentos = Arrays.asList(args);
    Arguments parsedArgs = new Arguments();

    if (argumentos.isEmpty()) {
      parsedArgs.useNamedEntities = false;
      return parsedArgs;
    }

    if (!argumentos.contains("-ne")) {
      throw new IllegalArgumentException("Missing required flag: -ne");
    }

    parsedArgs.useNamedEntities = true;
    if (argumentos.contains("-rh") && argumentos.size() == 2) {
      parsedArgs.heuristic = HeuristicType.RANDOM;
    } else if (!argumentos.contains("-rh") && argumentos.size() == 1) {
      parsedArgs.heuristic = HeuristicType.QUICK;
    } else {
      throw new IllegalArgumentException("Invalid argument combination. Use: [-ne] or [-ne -rh]");
    }

    return parsedArgs;
  }
}
