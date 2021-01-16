package com.vorobyev.text.comparator;

import com.vorobyev.text.entity.LexemeComponent;
import com.vorobyev.text.entity.LexemeType;

import java.util.Comparator;

public enum ParagraphComparator implements Comparator<LexemeComponent> {
    SENTENCE_COUNT((o1, o2) -> {
        int count1 = (int) o1.getLexemes().stream().filter(o -> o.getType().equals(LexemeType.SENTENCE)).count();
        int count2 = (int) o2.getLexemes().stream().filter(o -> o.getType().equals(LexemeType.SENTENCE)).count();
        return count1 - count2;
    });

    private final Comparator<LexemeComponent> comparator;

    private ParagraphComparator(Comparator<LexemeComponent> comparator) {
        this.comparator = comparator;
    }

    @Override
    public int compare(LexemeComponent o1, LexemeComponent o2) {
        return comparator.compare(o1, o2);
    }
}
