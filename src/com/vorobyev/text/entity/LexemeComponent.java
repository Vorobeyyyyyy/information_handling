package com.vorobyev.text.entity;

import java.util.ArrayList;
import java.util.List;

public class LexemeComponent implements Lexeme {
    private final static String NEW_LINE = "\n";

    private final static String INDENT = "    ";

    private List<Lexeme> lexemeList;

    private LexemeType type;

    public LexemeComponent(LexemeType type) {
        lexemeList = new ArrayList<>();
        this.type = type;
    }

    public LexemeComponent(List<Lexeme> lexemes, LexemeType type) {
        lexemeList = lexemes;
        this.type = type;
    }

    @Override
    public LexemeType getType() {
        return type;
    }

    public void setLexemes(List<Lexeme> lexemes) {
        lexemeList = lexemes;
    }

    public List<Lexeme> getLexemes() {
        return new ArrayList<>(lexemeList);
    }

    public void append(Lexeme lexeme) {
        lexemeList.add(lexeme);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LexemeComponent that = (LexemeComponent) o;

        if (lexemeList != null ? !lexemeList.equals(that.lexemeList) : that.lexemeList != null) return false;
        return type == that.type;
    }

    @Override
    public int hashCode() {
        int result = lexemeList != null ? lexemeList.hashCode() : 0;
        result = 31 * result + (type != null ? type.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        for (Lexeme lexeme : lexemeList) {
            if (type == LexemeType.TEXT) {
                sb.append(INDENT);
            }
            sb.append(lexeme.toString());
            if (type == LexemeType.TEXT && lexeme != lexemeList.get(lexemeList.size() - 1)) {
                sb.append(NEW_LINE);
            }
        }
        return sb.toString();
    }
}
