package com.plagiarism;

public class HammingCleared extends Hamming {
    
    private String clear(String str) {
        if (str == null) {
            return "";
        }
        // delete whitespaces, tabulators, underlines
        return str.replaceAll("[\\s\t_]+", "");
    }
    
    @Override
    public int compare(String str1, String str2) {
        String cleared1 = clear(str1);
        String cleared2 = clear(str2);
        return super.compare(cleared1, cleared2);
    }
}

