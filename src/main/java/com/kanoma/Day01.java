package com.kanoma;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day01 {
    public static void main(String[] args){
        String day = "01";
        File inputFile = new File("src/main/resources/DAY/"+day+".txt");
        partTwo(inputFile);
    }
    
    private static void partTwo(File file) {
        try {
            int sum = 0;
            
            Scanner reader = new Scanner(file);
            while (reader.hasNext()){
                String line = reader.nextLine();
                sum += concatDigit(getFirstCalibration(line), getLastCalibration(line));
            }
            
            System.out.println("PartTwo result --> Sum: " + sum);
            reader.close();
        } catch (FileNotFoundException e) {
            System.err.println("File not found");
        }
        
    }
    
    private static int getFirstCalibration(String line) {
        String[] words = {"one", "two", "three", "four", "five", "six", "seven", "eight", "nine"};
        Pattern pattern = Pattern.compile("\\d|one|two|three|four|five|six|seven|eight|nine");
        Matcher matcher = pattern.matcher(line);
        int firstDigit = -1;
        boolean found = false;
        
        while (matcher.find() && !found) {
            String match = matcher.group();
            if (Character.isDigit(match.charAt(0))) {
                firstDigit = Integer.parseInt(match);
                found = true;
            } else {
                for (int i = 0; i < words.length; i++) {
                    if (match.equals(words[i])) {
                        firstDigit = i+1;
                        found = true;
                    }
                }
            }
        }
        
        return firstDigit;
    }
    
    private static int getLastCalibration(String line) {
        String[] words = {"one", "two", "three", "four", "five", "six", "seven", "eight", "nine"};
        Pattern pattern = Pattern.compile("(?=(\\d|one|two|three|four|five|six|seven|eight|nine))");
        Matcher matcher = pattern.matcher(line);
        int lastDigit = -1;
        
        while (matcher.find()) {
            String match = matcher.group(1);
            if (Character.isDigit(match.charAt(0))) {
                lastDigit = Integer.parseInt(match);
            } else {
                for (int i = 0; i < words.length; i++) {
                    if (match.equals(words[i])) {
                        lastDigit = i+1;
                    }
                }
            }
        }
        
        return lastDigit;
    }
    
    private static int concatDigit(int firstDigit, int lastDigit) {
        String value = "" + firstDigit + lastDigit;
        return Integer.parseInt(value);
    }
}

