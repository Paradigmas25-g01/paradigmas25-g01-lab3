package parser;

import java.io.FileNotFoundException;
import java.io.FileReader;

import org.json.JSONArray;
import org.json.JSONTokener;

/**
 * Parser especializado en archivos de tipo Listas de JSON, solo parsea eso
 */
public class JsonParser extends GeneralParser {

  /**
   * Arreglo JSON que almaceno desde el archivo, se pasa a clases hijas
   */
  protected JSONArray jsonArr;

  /**
   * Parseo el archivo JSON desde la ruta indicada
   *
   * @param jsonPath Ruta al archivo JSON
   * @throws FileNotFoundException Si no encontre el archivo en la ruta indicada
   */
  @Override
  protected void parseFile(String jsonPath) {
    try {
      FileReader reader = new FileReader(jsonPath);
      this.jsonArr = new JSONArray(new JSONTokener(reader));
    } catch (FileNotFoundException e) {
      System.err.println("Archivo no encontrado: " + e.getMessage());
    }
  }

  /**
   * Devuelvo el array almacenado internamente
   */
  protected JSONArray getJsonArray() {
    return this.jsonArr;
  }

}
