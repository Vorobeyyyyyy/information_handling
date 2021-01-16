package com.vorobyev.text.parser.text;

import com.vorobyev.text.entity.Lexeme;
import com.vorobyev.text.entity.LexemeComponent;
import com.vorobyev.text.entity.LexemeType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TextParser extends AbstractTextParser {
    private final static String PARAGRAPH_DELIMITER = "(\\A|\\n)(\\s{4}|\\t)";

    public TextParser() {
        super();
        nextParser = new ParagraphParser();
    }

    @Override
    public List<Lexeme> parse(String text) {
        List<Lexeme> result = new ArrayList<>();
        String[] paragraphsArray = text.split(PARAGRAPH_DELIMITER);
        paragraphsArray = Arrays.copyOfRange(paragraphsArray, 1, paragraphsArray.length);
        for (String paragraphString : paragraphsArray) {
            List<Lexeme> lexemes = nextParser.parse(paragraphString);
            LexemeComponent paragraph = new LexemeComponent(lexemes, LexemeType.PARAGRAPH);
            result.add(paragraph);
        }
        return result;
    }
}
