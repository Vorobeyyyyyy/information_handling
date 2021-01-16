package com.vorobyev.text.parser.text;

import com.vorobyev.text.entity.Lexeme;
import com.vorobyev.text.entity.LexemeComponent;
import com.vorobyev.text.entity.LexemeType;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class TextParserTest {
    TextParser textParser = new TextParser();

    String rawText = "    It has survived - not only (five) centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the “Динамо” (Рига) with the release of Letraset sheets.toString() containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker Faclon9 including versions of Lorem Ipsum!\n" +
            "    It is a long a!=b established fact that a reader will be distracted by the readable content of a page when looking at its layout. The point of using Ipsum is that it has a more-or-less normal distribution ob.toString(a?b:c), as opposed to using (Content here), content here's, making it look like readable English?\n" +
            "    It is a established fact that a reader will be of a page when looking at its layout...\n" +
            "    Bye бандерлоги.";

    @Test
    public void testStart() {
        Lexeme text = new LexemeComponent(textParser.parse(rawText), LexemeType.TEXT);
        String actual = text.toString();
        String expected = rawText;
        assertEquals(actual, expected);
    }
}