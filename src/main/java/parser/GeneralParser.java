package parser;

/*
 * Esta clase modela los atributos y metodos comunes a todos los distintos
 * tipos de parser existentes en la aplicacion
 */
public abstract class GeneralParser {
  /**
   * Obliga a las clases hijas a parsear el archivo en la ruta `filePath`
   */
  protected abstract void parseFile(String filePath);
}
