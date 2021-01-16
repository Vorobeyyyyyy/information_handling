package com.vorobyev.text.parser.text;

import com.vorobyev.text.entity.Lexeme;
import com.vorobyev.text.entity.Symbol;

import java.util.ArrayList;
import java.util.List;

public class WordParser extends AbstractTextParser {
    public WordParser() {
        super();
    }

    @Override
    public List<Lexeme> parse(String text) {
        List<Lexeme> result = new ArrayList<>();
        for (char c : text.toCharArray()) {
            result.add(new Symbol(c));
        }
        return result;
    }
}
