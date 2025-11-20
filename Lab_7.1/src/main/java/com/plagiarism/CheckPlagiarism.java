package com.plagiarism;

import java.io.*;
import java.util.ArrayList;
import java.util.List;


public class CheckPlagiarism {
    private static final double PLAGIARISM_THRESHOLD = 5.0;
    
    public ComparisonResult CompareFiles(String file1Path, String file2Path) throws IOException {
        List<String> lines1 = readFileLines(file1Path);
        List<String> lines2 = readFileLines(file2Path);
        
        // check which file has more lines
        List<String> longerFile = lines1.size() >= lines2.size() ? lines1 : lines2;
        List<String> shorterFile = lines1.size() >= lines2.size() ? lines2 : lines1;
        
        HammingCleared hamming = new HammingCleared();
        List<Integer> minimalDistances = new ArrayList<>();
        int identicalLines = 0;
        
        //compare each line from the longer file to all lines in the shorter file
        for (String line1 : longerFile) {
            if (line1.trim().isEmpty()) {
                continue;
            }
            
            int minDistance = Integer.MAX_VALUE;
            boolean foundIdentical = false;
            
            for (String line2 : shorterFile) {
                if (line2.trim().isEmpty()) {
                    continue;
                }
                
                int distance = hamming.compare(line1, line2);
                if (distance < minDistance) {
                    minDistance = distance;
                }
                if (distance == 0) {
                    foundIdentical = true;
                }
            }
            
            if (minDistance != Integer.MAX_VALUE) {
                minimalDistances.add(minDistance);
                if (foundIdentical) {
                    identicalLines++;
                }
            }
        }
        
        double averageDistance = 0.0;
        if (!minimalDistances.isEmpty()) {
            int sum = minimalDistances.stream().mapToInt(Integer::intValue).sum();
            averageDistance = (double) sum / minimalDistances.size();
        }
        
        boolean plagiarismDetected = averageDistance < PLAGIARISM_THRESHOLD;
        boolean identical = averageDistance == 0.0;
        
        return new ComparisonResult(
            file1Path,
            file2Path,
            averageDistance,
            identicalLines,
            plagiarismDetected,
            identical
        );
    }
    
    private List<String> readFileLines(String filePath) throws IOException {
        List<String> lines = new ArrayList<>();
        try (LineNumberReader reader = new LineNumberReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        }
        return lines;
    }
    
    public static double getPlagiarismThreshold() {
        return PLAGIARISM_THRESHOLD;
    }
    
    public static class ComparisonResult {
        private final String file1;
        private final String file2;
        private final double averageDistance;
        private final int identicalLines;
        private final boolean plagiarismDetected;
        private final boolean identical;
        
        public ComparisonResult(String file1, String file2, double averageDistance, 
                               int identicalLines, boolean plagiarismDetected, boolean identical) {
            this.file1 = file1;
            this.file2 = file2;
            this.averageDistance = averageDistance;
            this.identicalLines = identicalLines;
            this.plagiarismDetected = plagiarismDetected;
            this.identical = identical;
        }
        
        public String getFile1() { return file1; }
        public String getFile2() { return file2; }
        public double getAverageDistance() { return averageDistance; }
        public int getIdenticalLines() { return identicalLines; }
        public boolean isPlagiarismDetected() { return plagiarismDetected; }
        public boolean isIdentical() { return identical; }
    }
}

