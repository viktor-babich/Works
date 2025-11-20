package com.plagiarism;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

// test for CheckPlagiarism behaviours.
 
class CheckPlagiarismTest {

    private CheckPlagiarism checker;

    @BeforeEach
    void setUp() {
        checker = new CheckPlagiarism();
    }

    @Test
    void identicalFilesAreDetected(@TempDir Path tempDir) throws IOException {
        File a = tempDir.resolve("a.txt").toFile();
        File b = tempDir.resolve("b.txt").toFile();
        try (FileWriter wa = new FileWriter(a); FileWriter wb = new FileWriter(b)) {
            wa.write("one\ntwo\nthree\n");
            wb.write("one\ntwo\nthree\n");
        }
        CheckPlagiarism.ComparisonResult r = checker.CompareFiles(a.getAbsolutePath(), b.getAbsolutePath());
        assertTrue(r.isIdentical());
        assertEquals(0.0, r.getAverageDistance(), 0.01);
        assertEquals(3, r.getIdenticalLines());
    }

    @Test
    void similarFilesTriggerPlagiarism(@TempDir Path tempDir) throws IOException {
        File a = tempDir.resolve("a.txt").toFile();
        File b = tempDir.resolve("b.txt").toFile();
        try (FileWriter wa = new FileWriter(a); FileWriter wb = new FileWriter(b)) {
            wa.write("one\ntwo\nthree\n");
            wb.write("one\ntwox\nthree\n");
        }
        CheckPlagiarism.ComparisonResult r = checker.CompareFiles(a.getAbsolutePath(), b.getAbsolutePath());
        assertFalse(r.isIdentical());
        assertTrue(r.getAverageDistance() < CheckPlagiarism.getPlagiarismThreshold());
        assertTrue(r.isPlagiarismDetected());
    }

    @Test
    void totallyDifferentFilesAreNotPlagiarism(@TempDir Path tempDir) throws IOException {
        File a = tempDir.resolve("a.txt").toFile();
        File b = tempDir.resolve("b.txt").toFile();
        try (FileWriter wa = new FileWriter(a); FileWriter wb = new FileWriter(b)) {
            // write LONGER, CLEARLY different lines so average distance exceeds threshold
            wa.write("AAAAAAAAAABBBBBBBBBBCCCCCCCCCC\nDDDDDDDDDDEEEEEEEEEEFFFFffffff\n");
            wb.write("xxxxxxxxxxyyyyyyyyyyzzzzzzzzzz\nqqqqqqqqqqrrrrrrrrrrssssssssss\n");
        }
        CheckPlagiarism.ComparisonResult r = checker.CompareFiles(a.getAbsolutePath(), b.getAbsolutePath());
        assertFalse(r.isPlagiarismDetected());
    }

    @Test
    void emptyFilesHandled(@TempDir Path tempDir) throws IOException {
        File a = tempDir.resolve("a.txt").toFile();
        File b = tempDir.resolve("b.txt").toFile();
        // ensure the files exist (zero-length) before comparing
        try (FileWriter wa = new FileWriter(a); FileWriter wb = new FileWriter(b)) {
            // create empty files
        }
        CheckPlagiarism.ComparisonResult r = checker.CompareFiles(a.getAbsolutePath(), b.getAbsolutePath());
        assertEquals(0.0, r.getAverageDistance(), 0.01);
        assertEquals(0, r.getIdenticalLines());
    }

    @Test
    void missingFilesThrow() {
        assertThrows(IOException.class, () -> checker.CompareFiles("nope1", "nope2"));
    }
}

