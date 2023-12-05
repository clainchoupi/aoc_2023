package com.kanoma.optims.day05.threads;

import java.io.File;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Day05 {
    private static final Logger logger = LogManager.getLogger(Day05.class);
    
    public static void main(String[] args){
        var startTimer = Instant.now();
        //String day = "sample";
        String day = "05";
        File inputFile = new File("src/main/resources/DAY/"+day+".txt");
        partOne(inputFile);
        
        var endTimer = Instant.now();
        logger.info("Execution time: " + Duration.between(startTimer, endTimer).toMillis() + " ms");
    }
    
    public static void partOne(File file) {
        try {
            Day05 day05 = new Day05();
            ArrayList<Transformateur> transformateurs = new ArrayList<>();
            Day05.Transformateur currentTransformateur = day05.new Transformateur();
            Day05.Sources currentSource = day05.new Sources();
            
            Scanner reader = new Scanner(file);
            //parcours du fichier
            //lire la premiere ligne
            String line = reader.nextLine();
            String[] splitLine = line.split(":");
            String[] seedStrings = splitLine[1].trim().split(" ");
            long[] initSeeds = new long[seedStrings.length];
            for (int i = 0; i < seedStrings.length; i++) {
                initSeeds[i] = Long.parseLong(seedStrings[i]);
            }
            
            long[] seeds = initSeeds.clone();
            
            //lire la ligne vide
            line = reader.nextLine();
            
            //lire les lignes suivantes, et créer les transformateurs
            while (reader.hasNext()){
                line = reader.nextLine();
                // soit ligne vide, soit nom du transformateur, soit valeurs du transformateur
                if (! line.isBlank()){
                    // si ligne contient un '-' alors c'est le nom du transformateur
                    if (line.contains("-")){
                        // on crée un nouveau transformateur
                        currentTransformateur = day05.new Transformateur();
                        transformateurs.add(currentTransformateur);
                    } else {
                        currentSource = day05.new Sources();
                        
                        // sinon c'est une valeur du transformateur
                        String[] values = line.split(" ");
                        // on ajoute les valeurs au transformateur courant
                        currentSource.to = Long.parseLong(values[0]); 
                        currentSource.from = Long.parseLong(values[1]); 
                        currentSource.length =  Long.parseLong (values[2]);
                        
                        currentTransformateur.sources.add(currentSource);
                    }
                }else{
                    // ligne vide, on passe au transformateur suivant
                }
            }
            
            
            // PART ONE
            // pour chaque graine, appliquer tous les transformateurs
            for (int i=0; i<seeds.length; i++){
                long seed = seeds[i];
                //Passer par chaque transformateur et appliquer les sources
                for (Transformateur transformateur : transformateurs){
                    //Passer par chaque source et appliquer la transformation
                    for (Sources source : transformateur.sources){
                        // si la graine est dans l'intervalle 'from - > from+length', on applique la transformation to+length
                        if (seed >= source.from && seed <= source.from + source.length-1){
                            seed = seed - source.from + source.to;
                            
                            seeds[i] = seed;
                            break;
                        }
                    }
                }
            }
            
            //Result = lowest value in seeds
            long lowestValuePartOne = Arrays.stream(seeds).min().getAsLong();
            logger.info("PartOne result --> lowestValuePartOne: " + lowestValuePartOne);
            
            
            // PART TWO
            // les graines sont en fait des ranges de valeurs
            
            // pour chaque graine, appliquer tous les transformateurs
            //il y a 10 groupes de graines
            // on démarre 10 threads
            ExecutorService fixedPool = Executors.newFixedThreadPool(20);
            List<Long> threadResult = Collections.synchronizedList(new ArrayList<>());
            
            for (int i=0; i<initSeeds.length; i++){
                long seed = initSeeds[i];
                
                i++;
                long length = initSeeds[i];
                
                fixedPool.submit(new TestThread(seed, length/2, transformateurs, threadResult));
                fixedPool.submit(new TestThread(seed + length/2, length/2, transformateurs, threadResult));
            }
            
            fixedPool.shutdown();
            fixedPool.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
            
            logger.info("All threads finished");
            long lowestValuePartTwo = Collections.min(threadResult);
            logger.info("PartTwo result --> lowestValuePartTwo: " + lowestValuePartTwo);
            
            reader.close();
        } catch (Exception e) {
            logger.error("Exception !! ", e);
        }
        
    }
    
    public class Transformateur {
        ArrayList<Sources> sources = new ArrayList<>();
    }
    
    public class Sources {
        long length;
        long from;
        long to;
        
        @Override
        public String toString() {
            return "Sources [length=" + length + ", from=" + from + ", to=" + to + "]";
        }
    }
    
    
    
}

