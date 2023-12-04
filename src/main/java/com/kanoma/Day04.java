package com.kanoma;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Day04 {
    private static final Logger logger = LogManager.getLogger(Day01.class);
    
    public static void main(String[] args){
        //String day = "sample";
        String day = "04";
        File inputFile = new File("src/main/resources/DAY/"+day+".txt");
        //partOne(inputFile);
        partTwo(inputFile);
    }
    
    public static void partOne(File file) {
        try {
            int sumPartOne = 0;
            
            Scanner reader = new Scanner(file);
            //parcours du fichier
            while (reader.hasNext()){
                String line = reader.nextLine();
                String[] splitLine = line.split(":");
                String gameId = splitLine[0].split(" ")[1];
                
                String[] parts = splitLine[1].trim().split("\\|");
                
                // split one or more spaces
                String[] goodCards = parts[0].trim().split(" +");
                
                String[] player = parts[1].trim().split(" +");
                
                //check how many cards are good in player's hand
                int goodCardsInHand = 0;
                for (int i = 0; i < player.length; i++) {
                    for (int j = 0; j < goodCards.length; j++) {
                        if (player[i].equals(goodCards[j])) {
                            goodCardsInHand++;
                        }
                    }
                }
                
                if (goodCardsInHand == 1) {
                    sumPartOne++;
                } else {
                    //sumPartOne = 2 power (goodCardsInHand - 1)
                    sumPartOne += (int) Math.pow(2, goodCardsInHand - 1);
                }
                logger.info("Game " + gameId + " --> goodCardsInHand: " + goodCardsInHand + " --> sumPartOne: " + sumPartOne);
            }
            
            logger.info("PartOne result --> sumPartOne: " + sumPartOne);
            
            
            reader.close();
        } catch (FileNotFoundException e) {
            logger.error("File not found", e);
        }
        
    }
    
    
    public static void partTwo(File file) {
        try {
            HashMap<Integer, Integer> cards = new HashMap<Integer, Integer>();
            int sumPartTwo = 0;
            Integer nbLines = 0;
            
            Scanner reader = new Scanner(file);
            //parcours du fichier
            while (reader.hasNext()){
                if (cards.get(nbLines) == null) {
                    cards.put(nbLines, 1);
                }else {
                    cards.put(nbLines, cards.get(nbLines) + 1);
                }
                
                String line = reader.nextLine();
                String[] splitLine = line.split(":");
                String gameId = splitLine[0].split(" ")[1];
                
                String[] parts = splitLine[1].trim().split("\\|");
                
                // split one or more spaces
                String[] goodCards = parts[0].trim().split(" +");
                
                String[] player = parts[1].trim().split(" +");
                
                //check how many cards are good in player's hand
                int goodCardsInHand = 0;
                for (int i = 0; i < player.length; i++) {
                    for (int j = 0; j < goodCards.length; j++) {
                        if (player[i].equals(goodCards[j])) {
                            goodCardsInHand++;
                        }
                    }
                }
                
                if (goodCardsInHand > 0) {
                    // Add 1 to following cards
                    for (int i = 0; i < goodCardsInHand; i++) {
                        if (cards.get(nbLines + i + 1) == null) {
                            cards.put(nbLines + i + 1, cards.get(nbLines));
                        }else {
                            cards.put(nbLines + i + 1, cards.get(nbLines + i + 1) + cards.get(nbLines));
                        }
                    }
                }
                
                nbLines ++;
                
            }
            
            //Sum of cards
            for (Integer value : cards.values()) {
                sumPartTwo += value;
            }
            logger.info("PartOne result --> sumPartTwo: " + sumPartTwo);
            
            
            reader.close();
        } catch (FileNotFoundException e) {
            logger.error("File not found", e);
        }
        
    }
    
}

