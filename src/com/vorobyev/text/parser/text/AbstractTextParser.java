package com.vorobyev.text.parser.text;

import com.vorobyev.text.entity.Lexeme;

import java.util.List;

public abstract class AbstractTextParser {
    protected AbstractTextParser nextParser;

    AbstractTextParser() {
    }

    List<Lexeme> parse(String text) {
        return nextParser.parse(text);
    }
}
