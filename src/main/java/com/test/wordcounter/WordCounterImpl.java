package com.test.wordcounter;

import org.apache.commons.collections4.MultiSet;
import org.apache.commons.collections4.multiset.HashMultiSet;
import org.apache.commons.lang3.StringUtils;

public class WordCounterImpl implements WordCounter {

    private MultiSet<String> wordCounter;

    public WordCounterImpl() {
        wordCounter = new HashMultiSet<>();
    }

    @Override
    public void addWord(String word) {

        if (StringUtils.isAlpha(word)) {
            wordCounter.add(word);
        } else {
            throw new IllegalArgumentException("Word cannot contain non-alphabets: " + word);
        }
    }

    @Override
    public int getWordCount(String word) {
        return wordCounter.getCount(word);
    }
}
