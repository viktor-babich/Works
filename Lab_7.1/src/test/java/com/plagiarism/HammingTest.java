package com.plagiarism;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

//test for the Hamming class.
class HammingTest {

    private Hamming hamming;

    @BeforeEach
    void setUp() {
        hamming = new Hamming();
    }

    @Test
    void identicalStringsReturnZero() {
        assertEquals(0, hamming.compare("medication", "medication"));
        assertEquals(0, hamming.compare("", ""));
    }

    @Test
    void countsCharacterDifferences() {
        assertEquals(1, hamming.compare("medication", "meditation"));
        assertEquals(1, hamming.compare("hello", "hallo"));
    }

    @Test
    void handlesDifferentLengths() {
        assertEquals(3, hamming.compare("speed", "speeding"));
        assertEquals(5, hamming.compare("cat", "category"));
    }

    @Test
    void handlesNullAndEmptyCorrectly() {
        assertEquals(0, hamming.compare(null, null));
        assertEquals(5, hamming.compare(null, "hello"));
        assertEquals(5, hamming.compare("hello", null));
        assertEquals(5, hamming.compare("", "hello"));
    }

    @Test
    void whitespaceIsCountedAsCharacterDifference() {
        assertEquals(1, hamming.compare("hello", "hello "));
        // Behavior: whitespace vs shifted characters produces larger distance
        assertEquals(6, hamming.compare("hello world", "helloworld"));
    }
}

