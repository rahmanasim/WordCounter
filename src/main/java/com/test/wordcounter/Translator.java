package com.test.wordcounter;

/**
 * Interface to represent Translator Service.
 */
public interface Translator {

    /**
     * Translates word into English.
     *
     * @param word to translate.
     * @return translated word in English.
     */
    String translate(String word);
}
