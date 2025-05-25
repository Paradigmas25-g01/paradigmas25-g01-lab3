package utils;

// Definida como tipo generico

import java.io.Serializable;

public class Tupla<A, B> implements Serializable{
    public final A first;
    public final B second;

    public Tupla(A first, B second) {
        this.first = first;
        this.second = second;
    }
}
