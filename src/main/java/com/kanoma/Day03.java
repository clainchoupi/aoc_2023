package com.kanoma;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Day03 {
    private static final Logger logger = LogManager.getLogger(Day01.class);
    
    public static void main(String[] args){
        String day = "sample";
        //String day = "03";
        File inputFile = new File("src/main/resources/DAY/"+day+".txt");
        partOneAndTwo(inputFile);
    }
    
    public static void partOneAndTwo(File file) {
        try {
            Day03 day03 = new Day03();
            ArrayList<char[]> lines = new ArrayList<>();
            ArrayList<NumberDay03> numbers = new ArrayList<>();
            ArrayList<GearDay03> gears = new ArrayList<>();
            int sumPartOne = 0;
            int sumPartTwo = 0;
            int gearId = 0;
            
            Scanner reader = new Scanner(file);
            //parcours du fichier
            while (reader.hasNext()){
                String line = reader.nextLine();
                char[] chars = line.toCharArray();
                lines.add(chars);
            }
            
            //Get ever number in the file
            for (int i = 0; i < lines.size(); i++) {
                char[] line = lines.get(i);
                //parcours des colonnes
                for (int j = 0; j < line.length; j++) {
                    char c = line[j];
                    //Check if char 'c' is a number
                    if (Character.isDigit(c)) {
                        ArrayList<Character> currentNumber = new ArrayList<>();
                        currentNumber.add(c);
                        int startPosition = j;
                        // Start position = j
                        // check if the next char is a number
                        while (j+1 < line.length && Character.isDigit(line[j+1])) {
                            currentNumber.add(line[j+1]);
                            j++;
                        }
                        
                        StringBuilder numberStr = new StringBuilder();
                        for (Character digit : currentNumber) {
                            numberStr.append(digit);
                        }
                        int value = Integer.parseInt(numberStr.toString());
                        
                        Day03.NumberDay03 currentNumberDay03 = day03.new NumberDay03(value, i, startPosition, j);
                        numbers.add(currentNumberDay03);
                    }
                }
            }
            
            // Get every GearDay03 in the file
            for (int i = 0; i < lines.size(); i++) {
                char[] line = lines.get(i);
                //parcours des colonnes
                for (int j = 0; j < line.length; j++) {
                    char c = line[j];
                    //Check if char 'c' is a number
                    if (c == '*') {
                        Day03.GearDay03 gear = day03.new GearDay03();
                        gear.row = i;
                        gear.column = j;
                        gear.id = gearId;
                        gearId++;
                        gears.add(gear);
                    }
                }
            }
            
            //Part ONE
            //parcours des nombres, et vérifier si ils sont valides
            for(NumberDay03 number : numbers) {
                boolean isValid = false;
                //Check if every box around the number is a dot
                isValid = checkAroundNumber(lines, number);
                
                if (isValid) {
                    //add the number to the sum
                    sumPartOne += number.value;
                }
            }
            logger.info("PartOne result --> sumPartOne: " + sumPartOne);
            
            //Part TWO
            //parcours des nombres, et noter si ils sont proches d'un gear
            for(NumberDay03 number : numbers) {
                //Check if the number is close to a gear
                for (GearDay03 gear : gears) {
                    if (number.row == gear.row && number.startPosition <= gear.column && number.endPosition >= gear.column) {
                        //add the number to the sum
                        sumPartTwo += number.value;
                    }
                }
            }
            
            String a = "";
            
            logger.info("PartTwo result --> sumPartTwo: " + sumPartTwo);
            
            reader.close();
        } catch (FileNotFoundException e) {
            logger.error("File not found", e);
        }
        
    }
    
    //Function to check if one box around the number is a dot
    public static boolean checkAroundNumber(ArrayList<char[]> lines, NumberDay03 number) {
        boolean isValid = false;
        int row = number.row;
        int startPosition = number.startPosition;
        int endPosition = number.endPosition;
        
        //Check if the box above is a dot
        if (row > 0) {
            char[] lineAbove = lines.get(row-1);
            for (int i = startPosition; i <= endPosition; i++) {
                if (lineAbove[i] != '.') {
                    return true;
                }
            }
        }
        
        //Check if the box below is a dot
        if (row < lines.size()-1) {
            char[] lineBelow = lines.get(row+1);
            for (int i = startPosition; i <= endPosition; i++) {
                if (lineBelow[i] != '.') {
                    return true;
                }
            }
        }
        
        //Check if the box on the left is a dot
        if (startPosition > 0) {
            char[] line = lines.get(row);
            if (line[startPosition-1] != '.') {
                return true;
            }
        }
        
        //Check if the box on the right is a dot
        if (endPosition < lines.get(row).length-1) {
            char[] line = lines.get(row);
            if (line[endPosition+1] != '.') {
                return true;
            }
        }
        
        //Check if the box on the top left is a dot
        if (row > 0 && startPosition > 0) {
            char[] lineAbove = lines.get(row-1);
            if (lineAbove[startPosition-1] != '.') {
                return true;
            }
        }
        
        //Check if the box on the top right is a dot
        if (row > 0 && endPosition < lines.get(row).length-1) {
            char[] lineAbove = lines.get(row-1);
            if (lineAbove[endPosition+1] != '.') {
                return true;
            }
        }
        
        //Check if the box on the bottom left is a dot
        if (row < lines.size()-1 && startPosition > 0) {
            char[] lineBelow = lines.get(row+1);
            if (lineBelow[startPosition-1] != '.') {
                return true;
            }
        }
        
        //Check if the box on the bottom right is a dot
        if (row < lines.size()-1 && endPosition < lines.get(row).length-1) {
            char[] lineBelow = lines.get(row+1);
            if (lineBelow[endPosition+1] != '.') {
                return true;
            }
        }
        
        return isValid;
    }
    
    
    public class NumberDay03 {
        int value;
        int row;
        int startPosition;
        int endPosition;
        int nextToGearId;
        
        public NumberDay03(int value, int row, int startPosition, int endPosition) {
            this.value = value;
            this.row = row;
            this.startPosition = startPosition;
            this.endPosition = endPosition;
        }
        
    }
    
    public class GearDay03 {
        int row; 
        int column;
        int id;
    }
    
}

