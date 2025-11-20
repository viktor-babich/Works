package com.plagiarism;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    
    public static void main(String[] args) {
        String workingDir = System.getProperty("user.dir");
        File directory = new File(workingDir);
        
        if (args.length > 0) {
            directory = new File(args[0]);
        }
        //list all files in the directory
        File[] files = directory.listFiles();
        
        if (files == null || files.length == 0) {
            System.out.println("No files found in directory: " + directory.getAbsolutePath());
            return;
        }
        //filter for regular files 
        List<File> fileList = new ArrayList<>();
        for (File file : files) {
            if (file.isFile()) {
                fileList.add(file);
            }
        }
        
        if (fileList.size() < 2) {
            System.out.println("Need at least 2 files to compare. Found: " + fileList.size());
            return;
        }
        
        System.out.println("Checking for plagiarism in directory: " + directory.getAbsolutePath());
        System.out.println("Found " + fileList.size() + " files to compare.");
        System.out.println("Plagiarism threshold: " + CheckPlagiarism.getPlagiarismThreshold());
        System.out.println("=" .repeat(80));
        
        CheckPlagiarism checker = new CheckPlagiarism();
        int comparisons = 0;
        int plagiarismCount = 0;
        
        //compare all pairs 
        for (int i = 0; i < fileList.size(); i++) {
            for (int j = i + 1; j < fileList.size(); j++) {
                File file1 = fileList.get(i);
                File file2 = fileList.get(j);
                
                try {
                    CheckPlagiarism.ComparisonResult result = 
                        checker.CompareFiles(file1.getAbsolutePath(), file2.getAbsolutePath());
                    
                    comparisons++;
                    System.out.println("\nComparing:");
                    System.out.println("  File 1: " + file1.getName());
                    System.out.println("  File 2: " + file2.getName());
                    System.out.println("  Average minimal Hamming Distance: " + 
                                      String.format("%.2f", result.getAverageDistance()));
                    System.out.println("  Identical lines: " + result.getIdenticalLines());
                    
                    if (result.isIdentical()) {
                        System.out.println("  *** FILES ARE IDENTICAL ***");
                        plagiarismCount++;
                    } else if (result.isPlagiarismDetected()) {
                        System.out.println("  *** PLAGIARISM DETECTED ***");
                        plagiarismCount++;
                    } else {
                        System.out.println("  No plagiarism detected.");
                    }
                    
                } catch (IOException e) {
                    System.err.println("Error comparing files " + file1.getName() + 
                                     " and " + file2.getName() + ": " + e.getMessage());
                }
            }
        }
        
        System.out.println("\n" + "=".repeat(80));
        System.out.println("Summary:");
        System.out.println("  Total comparisons: " + comparisons);
        System.out.println("  Cases of plagiarism detected: " + plagiarismCount);
    }
}

