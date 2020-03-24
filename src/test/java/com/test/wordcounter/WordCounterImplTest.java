package com.test.wordcounter;

import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class WordCounterImplTest {

    @Mock
    private Translator translator;

    @ParameterizedTest
    @ValueSource(strings = {"flower", "ball", "window"})
    void shouldAddWord(final String word) {
        when(translator.translate(word)).thenReturn(word);

        WordCounter wordCounter = new WordCounterImpl(translator);
        wordCounter.addWord(word);
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"flower1", "2ball", "w%indow"})
    void shouldThrowIllegalArgumentException(final String word) {
        WordCounter wordCounter = new WordCounterImpl(translator);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> wordCounter.addWord(word));

        String expectedMessage = String.format("Word cannot contain non-alphabets: %s", word);
        String actualMessage = exception.getMessage();

        assertEquals(expectedMessage, actualMessage, "Wrong exception message found");
    }


    @ParameterizedTest
    @CsvSource({"flower,1,flower,ball,window,glass", "flower,2,flower,ball,window,flower", "flower,4,flower,flower,flower,flower",
            "flower,0,flowerxx,ball,window,glass"})
    void shouldGetWordCount(final ArgumentsAccessor argumentsAccessor) {
        when(translator.translate(anyString())).thenAnswer(i -> i.getArguments()[0]);

        WordCounter wordCounter = new WordCounterImpl(translator);

        String word = argumentsAccessor.getString(0);
        int expectedWordCount = argumentsAccessor.getInteger(1);

        wordCounter.addWord(argumentsAccessor.getString(2));
        wordCounter.addWord(argumentsAccessor.getString(3));
        wordCounter.addWord(argumentsAccessor.getString(4));
        wordCounter.addWord(argumentsAccessor.getString(5));

        int actualWordCount = wordCounter.getWordCount(word);

        assertEquals(expectedWordCount, actualWordCount, "Word count is incorrect for " + word);
    }

    @ParameterizedTest
    @CsvSource({"flower,3,flower,flor,blume", "glass,2,vaso,glas"})
    void shouldGetWordCountNonEnglishWords(final ArgumentsAccessor argumentsAccessor) {
        WordCounter wordCounter = new WordCounterImpl(translator);

        String englishWord = argumentsAccessor.getString(0);
        int expectedWordCount = argumentsAccessor.getInteger(1);

        //add words
        for (int i = 2; i < argumentsAccessor.size(); i++) {
            String word = argumentsAccessor.getString(i);
            when(translator.translate(word)).thenReturn(englishWord);
            wordCounter.addWord(word);
        }

        //verify count is correct for each of translated words
        for (int i = 2; i < argumentsAccessor.size(); i++) {
            String word = argumentsAccessor.getString(i);
            int actualWordCount = wordCounter.getWordCount(word);
            assertEquals(expectedWordCount, actualWordCount, "Word count is incorrect for " + word);
        }
    }

}