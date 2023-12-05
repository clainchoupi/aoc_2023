package com.kanoma.optims.day05.threads;

import java.util.ArrayList;
import java.util.List;

import com.kanoma.optims.day05.threads.Day05.Sources;
import com.kanoma.optims.day05.threads.Day05.Transformateur;

public class TestThread implements Runnable {
    
    private ArrayList<Transformateur> transformateurs;
    private long seed;
    private long length;
    private List<Long> threadResult;
    
    public TestThread(long seed, long length, ArrayList<Transformateur> transformateurs, List<Long> threadResult) {
        this.seed = seed;
        this.length = length;
        this.transformateurs = transformateurs;
        this.threadResult = threadResult;
    }
    
    @Override
    public void run() {
        long lowestValuePartTwo = Long.MAX_VALUE;
        System.out.println("Thread started: " + Thread.currentThread().getName());
        
        //Boucler sur seed + length
        for (int j=0; j<length; j++){
            long seedToApply = seed + j;
            
            //Passer par chaque transformateur et appliquer les sources
            for (Transformateur transformateur : transformateurs){
                //Passer par chaque source et appliquer la transformation
                for (Sources source : transformateur.sources){
                    // si la graine est dans l'intervalle 'from - > from+length', on applique la transformation to+length
                    if (seedToApply >= source.from && seedToApply <= source.from + source.length-1){
                        seedToApply = seedToApply - source.from + source.to;
                        break;
                    }
                }
            }
            
            //keep the lowest seed value
            if (seedToApply < lowestValuePartTwo){
                lowestValuePartTwo = seedToApply;
            }
        }
        
        threadResult.add(lowestValuePartTwo);
        System.out.println("lowestValuePartTwo: " + lowestValuePartTwo);
    }
}