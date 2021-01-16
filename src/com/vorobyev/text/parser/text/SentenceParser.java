package com.vorobyev.text.parser.text;

import com.vorobyev.text.entity.*;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SentenceParser extends AbstractTextParser {
    private final static String WORD_DELIMITER = "\\s|(,\\s)|\\.|\\Z";
    private final static String SPACE_STRING = " ";

    public SentenceParser() {
        super();
        nextParser = new WordParser();
    }

    @Override
    public List<Lexeme> parse(String text) {
        List<Lexeme> result = new ArrayList<>();
        Pattern pattern = Pattern.compile(WORD_DELIMITER);
        Matcher matcher = pattern.matcher(text);
        int wordStart = 0;
        while (matcher.find()) {
            String wordString = text.substring(wordStart, matcher.start());
            String punctuationString = matcher.group();
            wordStart = matcher.end();
            List<Lexeme> lexemes = nextParser.parse(wordString);
            LexemeComponent word = new LexemeComponent(lexemes, LexemeType.WORD);
            Lexeme betweenWord;
            if (punctuationString.equals(SPACE_STRING)) {
                betweenWord = new Symbol(punctuationString.charAt(0));
            } else {
                betweenWord = new Punctuation(punctuationString);
            }
            result.add(word);
            if (!punctuationString.isEmpty()) {
                result.add(betweenWord);
            }
        }
        return result;
    }
}

