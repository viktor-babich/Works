package com.plagiarism;

public class Hamming {
    
    public int compare(String str1, String str2) {
        if (str1 == null && str2 == null) {
            return 0;
        }
        if (str1 == null) {
            return str2.length();
        }
        if (str2 == null) {
            return str1.length();
        }
        
        if (str1.isEmpty() && str2.isEmpty()) {
            return 0;
        }
        
        //evaluate Hamming distance
        int distance = 0;
        int minLength = Math.min(str1.length(), str2.length());
        
        //compare characters 
        for (int i = 0; i < minLength; i++) {
            if (str1.charAt(i) != str2.charAt(i)) {
                distance++;
            }
        }
        
        //adding the difference s
        distance += Math.abs(str1.length() - str2.length());
        
        return distance;
    }
}

