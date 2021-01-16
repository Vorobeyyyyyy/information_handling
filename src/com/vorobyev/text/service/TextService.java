package com.vorobyev.text.service;

import com.vorobyev.text.comparator.ParagraphComparator;
import com.vorobyev.text.entity.*;
import com.vorobyev.text.exception.ServiceException;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class TextService {
    public LexemeComponent sortParagraphsInTextBy(LexemeComponent text, ParagraphComparator comparator) throws ServiceException {
        if (!text.getType().equals(LexemeType.TEXT)) {
            throw new ServiceException("Not a text");
        }
        List<Lexeme> lexemes = text.getLexemes();
        List<LexemeComponent> paragraphs = new ArrayList<>();
        lexemes.forEach(o -> paragraphs.add((LexemeComponent) o));
        paragraphs.sort(comparator);
        lexemes.clear();
        lexemes.addAll(paragraphs);
        return new LexemeComponent(lexemes, LexemeType.TEXT);
    }


    public List<LexemeComponent> findLongestWordSentences(LexemeComponent text) throws ServiceException {
        if (!text.getType().equals(LexemeType.TEXT)) {
            throw new ServiceException("Not a text");
        }
        int globalMaxWordLength = 0;
        Map<LexemeComponent, Integer> sentenceMap = new HashMap<>();
        for (Lexeme paragraph : text.getLexemes()) {
            for (Lexeme lexemeSentence : ((LexemeComponent) paragraph).getLexemes()) {
                if (lexemeSentence.getType().equals(LexemeType.SENTENCE)) {
                    int maxWordLength = 0;
                    for (Lexeme lexemeWord : ((LexemeComponent) lexemeSentence).getLexemes()) {
                        if (lexemeWord.getType().equals(LexemeType.WORD)) {
                            int wordLength = ((LexemeComponent) lexemeWord).getLexemes().size();
                            if (wordLength > maxWordLength) {
                                maxWordLength = wordLength;
                            }
                        }
                    }
                    if (maxWordLength > globalMaxWordLength) {
                        globalMaxWordLength = maxWordLength;
                    }
                    sentenceMap.put((LexemeComponent) lexemeSentence, maxWordLength);
                }
            }
        }
        final int finalGlobalMaxWordLength = globalMaxWordLength;
        return sentenceMap.keySet().stream().filter(o -> sentenceMap.get(o) == finalGlobalMaxWordLength).collect(Collectors.toList());
    }

    public LexemeComponent deleteSentencesWithWordCountLess(LexemeComponent text, int wordCount) throws ServiceException {
        if (!text.getType().equals(LexemeType.TEXT)) {
            throw new ServiceException("Not a text");
        }
        List<Lexeme> newParagraphs = new ArrayList<>();
        for (Lexeme paragraph : text.getLexemes()) {
            List<Lexeme> newLexemeList = new ArrayList<>();
            for (Lexeme lexemeSentence : ((LexemeComponent) paragraph).getLexemes()) {
                if (lexemeSentence.getType().equals(LexemeType.SENTENCE)) {
                    int actualWordCount = (int) ((LexemeComponent) lexemeSentence).getLexemes().stream().filter(o -> o.getType().equals(LexemeType.WORD)).count();
                    if (actualWordCount >= wordCount) {
                        newLexemeList.add(lexemeSentence);
                    }
                }
            }
            newParagraphs.add(new LexemeComponent(newLexemeList, LexemeType.PARAGRAPH));
        }
        return new LexemeComponent(newParagraphs, LexemeType.TEXT);
    }

    public List<LexemeComponent> findDuplicatesWords(LexemeComponent text) throws ServiceException {
        if (!text.getType().equals(LexemeType.TEXT)) {
            throw new ServiceException("Not a text");
        }
        Map<LexemeComponent, Integer> result = new HashMap<>();
        List<LexemeComponent> words = new ArrayList<>();
        findAll(text, o -> o.getType().equals(LexemeType.WORD)).forEach(o -> words.add((LexemeComponent) o));
        for (LexemeComponent word : words) {
            if (result.containsKey(word)) {
                Integer count = result.get(word);
                result.remove(word);
                result.put(word, count + 1);
            } else {
                result.put(word, 1);
            }
        }
        return result.keySet().stream().filter(o -> result.get(o) >= 2).collect(Collectors.toList());
    }

    public List<Lexeme> findAll(LexemeComponent base, Predicate<Lexeme> predicate) {
        List<Lexeme> result = base.getLexemes().stream().filter(predicate).collect(Collectors.toList());
        base.getLexemes().stream().filter(o -> o instanceof LexemeComponent).forEach(o -> result.addAll(findAll(((LexemeComponent) o), predicate)));
        return result;
    }
}
