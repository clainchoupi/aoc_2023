package com.kanoma;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Scanner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Day07 {
    private static final Logger logger = LogManager.getLogger(Day07.class);
    private static HashMap<Integer, Integer> valuesToRank = new HashMap<Integer,Integer>() {{
        // Valeur 1 = nbDistinctChar * 10
        // Valeur 2 = nbMaxSameChar 
        // AAAAA = 15
        // AAAAB = 24
        // AAABB = 23
        // ....
        put(15, 7);
        put(24, 6);
        put(23, 5);
        put(33, 4);
        put(32, 3);
        put(42, 2);
        put(51, 1);
    }};

    private static HashMap<Integer, Integer> valuesToRank3J = new HashMap<Integer,Integer>() {{
        put(12, 7);
        put(21, 6);

    }};

    private static HashMap<Integer, Integer> valuesToRank2J = new HashMap<Integer,Integer>() {{
        put(13, 7);
        put(22, 6);
        put(31, 4);
    }};

    private static HashMap<Integer, Integer> valuesToRank1J = new HashMap<Integer,Integer>() {{
        //Possible : paire brelan full carré ou quinte
        put(14, 7);
        put(23, 6);
        put(22 , 5);
        put(32, 4);
        put(41, 2);
    }};
    
    private static HashMap<String, Integer> cardToValuesP1 = new HashMap<String,Integer>() {{
        put("2", 2);
        put("3", 3);
        put("4", 4);
        put("5", 5);
        put("6", 6);
        put("7", 7);
        put("8", 8);
        put("9", 9);
        put("T", 10);
        put("J", 11);
        put("Q", 12);
        put("K", 13);
        put("A", 14);
    }};
    
    private static HashMap<String, Integer> cardToValuesP2 = new HashMap<String,Integer>() {{
        put("J", 1);
        put("2", 2);
        put("3", 3);
        put("4", 4);
        put("5", 5);
        put("6", 6);
        put("7", 7);
        put("8", 8);
        put("9", 9);
        put("T", 10);
        
        put("Q", 12);
        put("K", 13);
        put("A", 14);
    }};
    
    
    public static void main(String[] args){
        var startTimer = Instant.now();
        //String day = "sample";
        String day = "07";
        File inputFile = new File("src/main/resources/DAY/"+day+".txt");
        readInputP1(inputFile);
        readInputP2(inputFile);
        var endTimer = Instant.now();
        logger.info("Execution time: " + Duration.between(startTimer, endTimer).toMillis() + " ms");
    }
    
    public static void readInputP1(File file) {
        try {
            Day07 day07 = new Day07();
            ArrayList<Hand> handsP1 = new ArrayList<>();
            
            
            Scanner reader = new Scanner(file);
            while (reader.hasNext()){
                String line = reader.nextLine();
                String[] splitLine = line.split(" ");
                
                Day07.Hand currentHand = day07.new Hand();
                currentHand.cards = splitLine[0];
                
                // compte le nombre de caractères distincts dans currentHand.cards
                currentHand.nbDistinctChar = (int) currentHand.cards.chars().distinct().count();
                
                // compte le nombre de fois qu'un caractère est répété le plus de fois
                for(int i = 0; i < currentHand.cards.length(); i++){
                    int nbSameChar = 0;
                    for(int j = 0; j < currentHand.cards.length(); j++){
                        if(currentHand.cards.charAt(i) == currentHand.cards.charAt(j)){
                            nbSameChar++;
                        }
                    }
                    if(nbSameChar > currentHand.nbMaxSameChar){
                        currentHand.nbMaxSameChar = nbSameChar;
                    }
                }
                
                // calcul du rank
                int valueHand = currentHand.nbDistinctChar * 10 + currentHand.nbMaxSameChar;
                currentHand.rank = valuesToRank.get(valueHand);
                
                // Ajout des values des cartes dans un tableau
                currentHand.cardValues = new int[currentHand.cards.length()];
                for(int i = 0; i < currentHand.cards.length(); i++){
                    currentHand.cardValues[i] = cardToValuesP1.get(String.valueOf(currentHand.cards.charAt(i)));
                }
                
                
                currentHand.bid = Integer.parseInt(splitLine[1]);
                handsP1.add(currentHand);
                
            }
            
            // PART ONE
            getScore(handsP1, "one");
            
            reader.close();
        } catch (FileNotFoundException e) {
            logger.error("File not found", e);
        }
    }
    
    public static void readInputP2(File file) {
        try {
            Day07 day07 = new Day07();
            ArrayList<Hand> handsP2 = new ArrayList<>();
            
            
            Scanner reader = new Scanner(file);
            while (reader.hasNext()){
                String line = reader.nextLine();
                String[] splitLine = line.split(" ");
                
                Day07.Hand currentHand = day07.new Hand();
                currentHand.cards = splitLine[0];

                String withoutJoker = currentHand.cards.replaceAll("J", "");
                currentHand.nbMaxJoker = currentHand.cards.length() - withoutJoker.length();
                
                // compte le nombre de caractères distincts dans currentHand.cards
                currentHand.nbDistinctChar = (int) withoutJoker.chars().distinct().count();
                
                // compte le nombre de fois qu'un caractère est répété le plus de fois sans Joker
                for(int i = 0; i < withoutJoker.length(); i++){
                    int nbSameChar = 0;
                    for(int j = 0; j < withoutJoker.length(); j++){
                        if(withoutJoker.charAt(i) == withoutJoker.charAt(j)){
                            nbSameChar++;
                        }
                    }
                    if(nbSameChar > currentHand.nbMaxSameChar){
                        currentHand.nbMaxSameChar = nbSameChar;
                    }
                }

                // calcul du rank
                if (currentHand.nbMaxJoker ==0){
                    // Pas de joker, on garde comme avant
                    int valueHand = currentHand.nbDistinctChar * 10 + currentHand.nbMaxSameChar;
                    currentHand.rank = valuesToRank.get(valueHand);
                } else if(currentHand.nbMaxJoker >=4){
                    // 4 jokers ou plus, on a forcément une quinte 
                    currentHand.rank = 7;
                } else if(currentHand.nbMaxJoker == 3){
                    // 3 jokers
                    //Use valuesToRank3J
                   int valueHand = currentHand.nbDistinctChar * 10 + currentHand.nbMaxSameChar;
                    currentHand.rank = valuesToRank3J.get(valueHand);
                } else if(currentHand.nbMaxJoker == 2){
                    // 2 jokers
                    //Use valuesToRank2J
                    int valueHand = currentHand.nbDistinctChar * 10 + currentHand.nbMaxSameChar;
                    currentHand.rank = valuesToRank2J.get(valueHand);
                } else if(currentHand.nbMaxJoker == 1){
                    // 1 joker : paire, brelan, full ou carré
                    //Use valuesToRank1J
                    int valueHand = currentHand.nbDistinctChar * 10 + currentHand.nbMaxSameChar;
                    currentHand.rank = valuesToRank1J.get(valueHand);
                }
                
                // Ajout des values des cartes dans un tableau
                currentHand.cardValues = new int[currentHand.cards.length()];
                for(int i = 0; i < currentHand.cards.length(); i++){
                    currentHand.cardValues[i] = cardToValuesP2.get(String.valueOf(currentHand.cards.charAt(i)));
                }
                
                
                currentHand.bid = Integer.parseInt(splitLine[1]);
                handsP2.add(currentHand);
                
            }
            
            // PART TWO
            getScore(handsP2, "two");
            
            reader.close();
        } catch (FileNotFoundException e) {
            logger.error("File not found", e);
        }
    }
    
    public static void getScore(ArrayList<Hand> hands, String part){
        //Tri par rank puis par values
        //hands.sort(Hand::customCompare);
        Collections.sort(hands, (a, b) -> customCompare(a, b));
        
        //calcul du score : 1*bid pour la première main, 2*bid pour la deuxième, etc.
        int score = 0;
        for(int i = 0; i < hands.size(); i++){
            score += (i+1) * hands.get(i).bid;
        }
        logger.info("Part "+ part +" answer: " + score);
        
    }

    public static int customCompare(Hand a, Hand b) {
        if (a.getRank() != b.getRank()) {
            return Integer.compare(a.getRank(), b.getRank());
        } else {
            for (int i = 0; i < a.cardValues.length; i++) {
                if (a.getCardValueAt(i) != b.getCardValueAt(i)) {
                    return Integer.compare(a.getCardValueAt(i), b.getCardValueAt(i));
                }
            }
            return 0;
        }
        
    }
    
    
    public class Hand {
        String cards;
        int bid;
        int nbDistinctChar;
        int nbMaxSameChar;
        int rank;
        int[] cardValues;

        int nbMaxJoker;
        int nbMaxNotJoker;
        
        public int getRank() {
            return rank;
        }
        
        public int getCardValueAt(int i) {
            return cardValues[i];
        }
    }
    
}

