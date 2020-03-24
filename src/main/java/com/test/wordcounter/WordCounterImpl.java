package com.test.wordcounter;

import org.apache.commons.collections4.MultiSet;
import org.apache.commons.collections4.multiset.HashMultiSet;
import org.apache.commons.lang3.StringUtils;

/**
 * Implementation of WordCounter.
 *
 * Word counts internally are stored in a {@link org.apache.commons.collections4.MultiSet}.
 *
 * Note: The class is not ThreadSafe.
 */
public class WordCounterImpl implements WordCounter {

    private Translator translator;

    private MultiSet<String> wordCounter;

    public WordCounterImpl(final Translator translator) {
        this.translator = translator;
        wordCounter = new HashMultiSet<>();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addWord(final String word) {
        if (StringUtils.isAlpha(word)) {
            wordCounter.add(translator.translate(word));
        } else {
            throw new IllegalArgumentException("Word cannot contain non-alphabets: " + word);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getWordCount(final String word) {
        return wordCounter.getCount(translator.translate(word));
    }
}
