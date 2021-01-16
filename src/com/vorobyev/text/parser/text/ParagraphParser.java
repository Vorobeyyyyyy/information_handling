package com.vorobyev.text.parser.text;

import com.vorobyev.text.entity.Lexeme;
import com.vorobyev.text.entity.LexemeComponent;
import com.vorobyev.text.entity.LexemeType;
import com.vorobyev.text.entity.Punctuation;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ParagraphParser extends AbstractTextParser {
    private final static String SENTENCE_DELIMITER = "(\\.{1,3}|\\!|\\?)(\\s|\\Z)";

    public ParagraphParser() {
        super();
        nextParser = new SentenceParser();
    }

    @Override
    public List<Lexeme> parse(String text) {
        List<Lexeme> result = new ArrayList<>();
        Pattern pattern = Pattern.compile(SENTENCE_DELIMITER);
        Matcher matcher = pattern.matcher(text);
        int sentenceStart = 0;
        while (matcher.find()) {
            String sentenceString = text.substring(sentenceStart, matcher.start());
            String punctuationString = matcher.group();
            sentenceStart = matcher.end();
            List<Lexeme> lexemes = nextParser.parse(sentenceString);
            LexemeComponent sentence = new LexemeComponent(lexemes, LexemeType.SENTENCE);
            Punctuation punctuation = new Punctuation(punctuationString);
            result.add(sentence);
            result.add(punctuation);
        }
        return result;
    }
}
