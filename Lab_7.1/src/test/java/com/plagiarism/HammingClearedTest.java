package com.plagiarism;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

//test for the HammingCleared class.
 
class HammingClearedTest {

    @Test
    void whitespaceAndUnderscoreAreIgnored() {
        HammingCleared h = new HammingCleared();
        assertEquals(0, h.compare("x y_z", "xyz"));
    }

    @Test
    void tabsAndNewlinesAreIgnoredToo() {
        HammingCleared h = new HammingCleared();
        String s1 = "xy\tz\n_w";
        String s2 = "xyzw";
        assertEquals(0, h.compare(s1, s2));
    }

    @Test
    void realDifferenceAfterCleaningIsCounted() {
        HammingCleared h = new HammingCleared();
        assertEquals(1, h.compare("x u z", "x v z"));
    }

    @Test
    void nullBecomesEmptyStringAndIsHandled() {
        HammingCleared h = new HammingCleared();
        assertEquals(3, h.compare(null, "xyz"));
    }

    @Test
    void nullVsNullBecomesZeroDistance() {
        HammingCleared h = new HammingCleared();
        assertEquals(0, h.compare(null, null));
    }
}

