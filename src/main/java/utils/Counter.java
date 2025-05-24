package utils;

import java.util.HashMap;
import java.util.Map;

public class Counter {
  private int total = 0;
  private Map<String, Integer> entityCounts = new HashMap<>();

  public void add(String entityName, int frequency) {
    total += frequency;
    entityCounts.put(entityName, entityCounts.getOrDefault(entityName, 0) + frequency);
  }

  public int getTotal() {
    return total;
  }

  public Map<String, Integer> getEntityCounts() {
    return entityCounts;
  }
}
