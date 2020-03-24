package com.test.wordcounter;

import org.apache.commons.collections4.MultiSet;
import org.apache.commons.collections4.multiset.HashMultiSet;
import org.apache.commons.lang3.StringUtils;

/**
 * Implementation of WordCounter.
 *
 * Word counts internally are stored in a {@link org.apache.commons.collections4.MultiSet}.
 */
public class WordCounterImpl implements WordCounter {

    private MultiSet<String> wordCounter;

    public WordCounterImpl() {
        wordCounter = new HashMultiSet<>();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addWord(String word) {

        if (StringUtils.isAlpha(word)) {
            wordCounter.add(word);
        } else {
            throw new IllegalArgumentException("Word cannot contain non-alphabets: " + word);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getWordCount(String word) {
        return wordCounter.getCount(word);
    }
}
