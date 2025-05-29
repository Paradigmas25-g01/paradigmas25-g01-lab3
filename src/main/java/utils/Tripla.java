package utils;

// Definida como tipo generico

import java.io.Serializable;

public class Tripla<A, B, C> implements Serializable{
  public final A first;
  public final B second;
  public final C third;

  public Tripla(A first, B second, C third) {
    this.first = first;
    this.second = second;
    this.third = third;
  }

}
