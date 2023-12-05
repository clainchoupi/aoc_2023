package com.kanoma;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Day02 {
    private static final Logger logger = LogManager.getLogger(Day02.class);
    
    public static void main(String[] args){
        //String day = "sample";
        String day = "02";
        File inputFile = new File("src/main/resources/DAY/"+day+".txt");
        partOne(inputFile);
        partTwo(inputFile);
    }
    
    private static void partOne(File file) {
        //Condition : 12 red cubes, 13 green cubes, and 14 blue cubes
        try {
            int sumIdPossible = 0;
            
            Scanner reader = new Scanner(file);
            while (reader.hasNext()){
                String line = reader.nextLine();

                String[] splitLine = line.split(":");
                String gameId = splitLine[0].split(" ")[1];
                String[] parts = splitLine[1].trim().split(";");

                boolean partPossible = true;
                //Iterate over parts
                for (String part : parts) {
                    if (!partPossible) break;

                    int blue = 0, red = 0, green = 0;
                    Matcher m = Pattern.compile("(\\d+) (blue|red|green)").matcher(part);
                    while (m.find()) {
                        int count = Integer.parseInt(m.group(1));
                        String color = m.group(2);
                        if (color.equals("blue")) blue = count;
                        else if (color.equals("red")) red = count;
                        else if (color.equals("green")) green = count;

                    }
                    if (red <= 12 && green <= 13 && blue <= 14) {
                        partPossible = true;
                    }else{
                        partPossible = false; 
                    }
                }

                if (partPossible) {
                    sumIdPossible += Integer.parseInt(gameId);
                }
            }
            
            logger.info("PartOne result --> sumIdPossible: " + sumIdPossible);
            reader.close();
        } catch (FileNotFoundException e) {
            logger.error("File not found", e);
        }
        
    }
    

    private static void partTwo(File file) {
        //Condition : 12 red cubes, 13 green cubes, and 14 blue cubes
        try {
            int sumPowers = 0;
            
            Scanner reader = new Scanner(file);
            while (reader.hasNext()){
                String line = reader.nextLine();

                String[] splitLine = line.split(":");
                String gameId = splitLine[0].split(" ")[1];
                String[] parts = splitLine[1].trim().split(";");

                int maxBlue = 0, maxRed = 0, maxGreen = 0;

                //Iterate over parts
                for (String part : parts) {

                    int blue = 0, red = 0, green = 0;
                    Matcher m = Pattern.compile("(\\d+) (blue|red|green)").matcher(part);
                    while (m.find()) {
                        int count = Integer.parseInt(m.group(1));
                        String color = m.group(2);
                        if (color.equals("blue")) blue = count;
                        else if (color.equals("red")) red = count;
                        else if (color.equals("green")) green = count;
                    }

                    if (blue > maxBlue) maxBlue = blue;
                    if (red > maxRed) maxRed = red;
                    if (green > maxGreen) maxGreen = green;
                }

                sumPowers += maxBlue * maxRed * maxGreen;
            }
            
            logger.info("PartTwo result --> sumPowers: " + sumPowers);
            reader.close();
        } catch (FileNotFoundException e) {
            logger.error("File not found", e);
        }
        
    }
}

