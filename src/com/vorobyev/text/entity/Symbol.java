package com.vorobyev.text.entity;

public class Symbol implements Lexeme {
    private final char symbol;

    public Symbol(char c) {
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

        Symbol symbol1 = (Symbol) o;

        return symbol == symbol1.symbol;
    }

    @Override
    public int hashCode() {
        return symbol;
    }

    @Override
    public String toString() {
        return String.valueOf(symbol);
    }
}
