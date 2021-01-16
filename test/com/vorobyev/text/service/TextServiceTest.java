package com.vorobyev.text.service;

import com.vorobyev.text.comparator.ParagraphComparator;
import com.vorobyev.text.entity.Lexeme;
import com.vorobyev.text.entity.LexemeComponent;
import com.vorobyev.text.entity.LexemeType;
import com.vorobyev.text.exception.ServiceException;
import com.vorobyev.text.parser.text.SentenceParser;
import com.vorobyev.text.parser.text.TextParser;
import com.vorobyev.text.parser.text.TextParserTest;
import com.vorobyev.text.parser.text.WordParser;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.testng.Assert.*;

public class TextServiceTest {
    TextService textService = new TextService();

    String rawText = "    Is this. Paragraph. Three sentences.\n" +
            "    There only. Two.\n" +
            "    And. There. Four Four. Haha.\n" +
            "    One sentence, VAV sentence.";

    String expectedSortBySentenceCount = "    One sentence, VAV sentence.\n" +
            "    There only. Two.\n" +
            "    Is this. Paragraph. Three sentences.\n" +
            "    And. There. Four Four. Haha.";

    String deleteSentenceWithWordCountLessThanTwo = "    Is this. Three sentences.\n" +
            "    There only.\n" +
            "    Four Four.\n" +
            "    One sentence, VAV sentence.";

    LexemeComponent text = new LexemeComponent(new TextParser().parse(rawText), LexemeType.TEXT);

    @Test
    public void testSortParagraphsInTextBy() {
        LexemeComponent actualText;
        try {
            actualText = textService.sortParagraphsInTextBy(text, ParagraphComparator.SENTENCE_COUNT);
        } catch (ServiceException exception) {
            actualText = new LexemeComponent(LexemeType.TEXT);
        }
        String actual = actualText.toString();
        String expected = expectedSortBySentenceCount;
        assertEquals(actual, expected);
    }

    @Test
    public void testFindLongestWordSentences() {
        List<LexemeComponent> actual;
        List<LexemeComponent> expected = new ArrayList<>();
        SentenceParser sentenceParser = new SentenceParser();
        expected.add(new LexemeComponent(sentenceParser.parse("Paragraph"), LexemeType.SENTENCE));
        expected.add(new LexemeComponent(sentenceParser.parse("Three sentences"), LexemeType.SENTENCE));
        try {
            actual = textService.findLongestWordSentences(text);
        } catch (ServiceException exception) {
            actual = new ArrayList<>();
        }
        assertEquals(actual, expected);
    }

    @Test
    public void testDeleteSentencesWithWordCountLess() {
        LexemeComponent actual;
        LexemeComponent expected = new LexemeComponent(new TextParser().parse(deleteSentenceWithWordCountLessThanTwo), LexemeType.TEXT);
        try {
            actual = textService.deleteSentencesWithWordCountLess(text, 2);
        } catch (ServiceException exception) {
            actual = new LexemeComponent(LexemeType.TEXT);
        }
        assertEquals(actual, expected);
    }

    @Test
    public void testFindDuplicatesWords() {
        List<LexemeComponent> actual;
        List<LexemeComponent> expected = new ArrayList<>();
        WordParser wordParser = new WordParser();
        expected.add(new LexemeComponent(wordParser.parse("There"), LexemeType.WORD));
        expected.add(new LexemeComponent(wordParser.parse("sentence"), LexemeType.WORD));
        expected.add(new LexemeComponent(wordParser.parse("Four"), LexemeType.WORD));

        try {
            actual = textService.findDuplicatesWords(text);
        } catch (ServiceException exception) {
            actual = new ArrayList<>();
        }
        assertEquals(actual, expected);
    }
}