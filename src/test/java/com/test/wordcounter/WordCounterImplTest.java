package com.test.wordcounter;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class WordCounterImplTest {

    @ParameterizedTest
    @ValueSource(strings = {"flower", "ball", "window"})
    void shouldAddWord(final String word) {
        WordCounter wordCounter = new WordCounterImpl();
        wordCounter.addWord(word);
    }

    @ParameterizedTest
    @ValueSource(strings = {"flower1", "2ball", "w%indow", StringUtils.EMPTY, StringUtils.SPACE})
    void shouldThrowIllegalArgumentException(final String word) {
        WordCounter wordCounter = new WordCounterImpl();

        Exception exception = assertThrows(IllegalArgumentException.class, () -> wordCounter.addWord(word));

        String expectedMessage = String.format("Word cannot contain non-alphabets: %s", word);
        String actualMessage = exception.getMessage();

        assertEquals(expectedMessage, actualMessage, "Wrong exception message found");
    }


    @ParameterizedTest
    @CsvSource({"flower,1,flower,ball,window,glass", "flower,2,flower,ball,window,flower", "flower,4,flower,flower,flower,flower", "flower,0,flowerxx,ball,window,glass"})
    void shouldGetWordCount(final ArgumentsAccessor argumentsAccessor) {
        WordCounter wordCounter = new WordCounterImpl();

        String expectedWord = argumentsAccessor.getString(0);
        int expectedWordCount = argumentsAccessor.getInteger(1);

        wordCounter.addWord(argumentsAccessor.getString(2));
        wordCounter.addWord(argumentsAccessor.getString(3));
        wordCounter.addWord(argumentsAccessor.getString(4));
        wordCounter.addWord(argumentsAccessor.getString(5));


        int actualWordCount = wordCounter.getWordCount(expectedWord);

        assertEquals(expectedWordCount, actualWordCount, "Word count is incorrect.");
    }
}