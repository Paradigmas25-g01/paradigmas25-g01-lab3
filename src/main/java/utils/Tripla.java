package utils;

// Definida como tipo generico
public class Tripla<A, B, C> {
  public final A first;
  public final B second;
  public final C third;

  public Tripla(A first, B second, C third) {
    this.first = first;
    this.second = second;
    this.third = third;
  }
}
