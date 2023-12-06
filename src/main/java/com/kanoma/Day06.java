package com.kanoma;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Scanner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Day06 {
    private static final Logger logger = LogManager.getLogger(Day06.class);
    
    public static void main(String[] args){
        var startTimer = Instant.now();
        //String day = "sample";
        String day = "06";
        File inputFile = new File("src/main/resources/DAY/"+day+".txt");
        readInput(inputFile);
        
        var endTimer = Instant.now();
        logger.info("Execution time: " + Duration.between(startTimer, endTimer).toMillis() + " ms");
    }
    
    public static void readInput(File file) {
        try {
            Day06 day06 = new Day06();
            ArrayList<Race> races = new ArrayList<>();
            Day06.Race currentRace = day06.new Race();
            
            Scanner reader = new Scanner(file);
            //parcours du fichier
            //lire la premiere ligne
            String line = reader.nextLine();
            String line2 = reader.nextLine();
            String totalTime="";
            String totalDistance="";

            String[] splitLine = line.replace("Time:", "").trim().split("\\D+");
            String[] splitLine2 = line2.replace("Distance:", "").trim().split("\\D+");
            
            for (int i = 0; i < splitLine.length; i++) {
                currentRace = day06.new Race();
                currentRace.time = Integer.parseInt(splitLine[i]);
                currentRace.distance = Integer.parseInt(splitLine2[i]);
                races.add(currentRace);

                totalTime += splitLine[i];
                totalDistance += splitLine2[i];
            }
            
            // PART ONE
            // Calcul pour chaque race
            processPartOne(races);
            
            // PART TWO
            // Il n'y a qu'une race
            processPartTwo(Long.parseLong(totalTime), Long.parseLong(totalDistance));
            
            reader.close();
        } catch (FileNotFoundException e) {
            logger.error("File not found", e);
        }
    }
    
    public static void processPartOne(ArrayList<Race> races){
        ArrayList<Integer> nbValides = new ArrayList<>();
        for(Race currentRace : races){
            int nbValidInCurrent = 0;
            for(int i = 0; i < currentRace.time; i++){
                int currentDistance = 0;
                
                //Calcul de la distance parcourue si on tient i millisecondes
                // distance = temps attendu * temps restant
                currentDistance =  i * (currentRace.time - i);
                
                if(currentDistance > currentRace.distance){
                    nbValidInCurrent++;
                }
            }
            nbValides.add(nbValidInCurrent);
        }
        
        //Result is the multiplication of values in the list 'distances'
        int result = nbValides.stream().reduce(1, (a, b) -> a * b);
        
        logger.info("Part one answer: " + result);
    }
    
    
    public static void processPartTwo(Long time, Long distance){
        int nbValidInCurrent = 0;
        for(long i = 0; i < time; i++){
            long currentDistance = 0;
            
            //Calcul de la distance parcourue si on tient i millisecondes
            // distance = temps attendu * temps restant
            currentDistance =  i * (time - i);
            
            if(currentDistance > distance){
                nbValidInCurrent++;
            }
        }
        
        logger.info("Part two answer: " + nbValidInCurrent);
    }
    
    
    public class Race {
        int time;
        int distance;
    }
    
}

