package com.test.wordcounter;

/**
 * Interface for WordCounter.
 */
public interface WordCounter {

    /**
     * Adds a word to the word counter.
     *
     * @param word to be added.
     *
     * @throws IllegalArgumentException if word input contains non-alphabet, null or empty string.
     */
    void addWord(String word) throws IllegalArgumentException;

    /**
     * Gets the count for a word.
     *
     * @param word to get the count for.
     *
     * @return count for the input word.
     */
    int getWordCount(String word);
}
