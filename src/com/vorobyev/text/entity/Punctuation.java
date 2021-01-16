package com.vorobyev.text.entity;

import java.util.List;

public class Punctuation implements Lexeme {
    private final String symbol;

    public Punctuation(String c) {
        symbol = c;
    }

    @Override
    public LexemeType getType() {
        return LexemeType.PUNCTUATION;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Punctuation that = (Punctuation) o;

        return symbol == that.symbol;
    }

    @Override
    public int hashCode() {
        return symbol.hashCode();
    }

    @Override
    public String toString() {
        return String.valueOf(symbol);
    }
}
