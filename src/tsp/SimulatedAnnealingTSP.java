package tsp;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class SimulatedAnnealingTSP {
    double temp = 10000;
    double coolingRate = 0.0003;

    int bestCost = Integer.MAX_VALUE;
    List<Integer> bestTour = new ArrayList<Integer>();

    public List<Integer> getCities(int numOfCities){
        List<Integer> citieslist = new ArrayList<Integer>();
        for(int i = 0; i < numOfCities; i++)
            citieslist.add(i, null);
        for(int i = 0; i < numOfCities; i++){
            citieslist.set((i+3)%numOfCities, i);
        }
        //Collections.shuffle(citieslist);

        return citieslist;
    }


    public int evaluateCost(int city1, int city2, int functionNumber){
        int cost = 0;
        switch (functionNumber){
            case 1:
                cost = evaluateCost1(city1, city2);
                break;
            case 2:
                cost = evaluateCost2(city1, city2);
                break;
            case 3:
                cost = evaluateCost3(city1, city2);
                break;
        }
        return cost;
    }

    public int evaluateCost1(int city1, int city2){
        if (city1 == city2)
            return 0;
        else if (city1 < 3 && city2 < 3)
            return  1;
        else if (city1 < 3)
            return  200;
        else if (city2 < 3)
            return  200;
        else if (city1 % 7 == city2 % 7)
            return  2;
        else
            return Math.abs(city1 - city2)+3;
    }

    public int evaluateCost2(int city1, int city2){
        int dist=0;
        if(city1==city2) {
            dist=0;
        }
        else if (city1+city2<10) {
            dist=Math.abs(city1-city2)+4;
        }
        else if ((city1+city2)%11==0) {
            dist=3;
        }
        else {
            dist=(int) (Math.pow((double)Math.abs(city1-city2),2)+10);
        }
        return dist;
    }

    public int evaluateCost3(int city1, int city2){
        int dist=0;
        if(city1==city2) {
            dist=0;
        }else {
            dist=(int) Math.pow(city1+city2,2);
        }
        return dist;
    }


    public  int calculateTourCost(List<Integer> cities, int costFunction){
        int numOfCities = cities.size();
        int totalCost = evaluateCost(cities.get(0), cities.get(numOfCities-1), costFunction);
        for (int i = 0; i < numOfCities-1; i++){
            int city1 = cities.get(i);
            int city2 = cities.get(i+1);
            totalCost += evaluateCost(city1, city2,costFunction);
        }
        return totalCost;
    }

    public void simulatedTSP(int numOfCities, int costFunction, String fileName) throws IOException{
        List<Integer> initialTour;
        SimulatedAnnealingTSP stsp = new SimulatedAnnealingTSP();
        initialTour = stsp.getCities(numOfCities);
        stsp.bestTour = initialTour;
        stsp.bestCost = stsp.calculateTourCost(initialTour, costFunction);
        double temperature = stsp.temp;
        double coolRate = stsp.coolingRate;

        int MEB = 1;

        Random rn = new Random();
        rn.setSeed(0);


        while (temperature > 0.1){
            List<Integer> newTour = new ArrayList<Integer>();
            newTour.addAll(initialTour);
            int index1 = rn.nextInt(numOfCities);
            int index2 = rn.nextInt(numOfCities);


            stsp.swapCities(index1, index2, newTour);
            MEB++;
            int currentEnergy = stsp.calculateTourCost(initialTour, costFunction);
            int neighbourEnergy = stsp.calculateTourCost(newTour, costFunction);
            double acceptanceProb = stsp.acceptanceProbability(currentEnergy, neighbourEnergy, temperature);
            if (acceptanceProb > Math.random()) {
                initialTour = newTour;
            }
            else{
                //stsp.swapCities(index1, index2, newTour);
            }

            int newBestCost = stsp.calculateTourCost(initialTour, costFunction);
            if (newBestCost < stsp.bestCost) {
                stsp.bestCost = newBestCost;
                stsp.bestTour = initialTour;
            }

            else{
                stsp.swapCities(index1, index2, newTour);   
            }

            temperature *= 1-coolRate;
        }
        stsp.printBestTour(stsp.bestTour, costFunction, fileName);
        System.out.println("Total MEB = "+MEB);

    }


    private void printBestTour(List<Integer> bestTour, int costFunction, String fileName) throws IOException{
        SimulatedAnnealingTSP stsp = new SimulatedAnnealingTSP();
        int cost = stsp.calculateTourCost(bestTour, costFunction);
        int lastIndex = bestTour.size();
        int startCity = bestTour.get(0);
        bestTour.add(startCity);
        cost += evaluateCost(bestTour.get(0), bestTour.get(lastIndex-1), costFunction);

        System.out.println("Best cost = "+cost);
        System.out.println("Tour = ");
        System.out.println(bestTour);
        FileWriter fileWriter = new FileWriter(new File(fileName));
        fileWriter.write(Integer.toString(cost));
        fileWriter.write("\n");
        for (Integer city : bestTour){
            fileWriter.write(Integer.toString(city)+" ");
        }
        fileWriter.flush();

    }

    public double acceptanceProbability(int cost, int newCost, double temperature) {
        if (newCost < cost) {
            return 1.0;
        }
        return Math.exp((cost - newCost) / temperature);
    }

    public void swapCities(int index1, int index2, List<Integer> tour) {

        //Collections.swap(tour, index1, index2);
        int cost1 = tour.get(index1);
        int cost2 = tour.get(index2);

        tour.set(index1, cost2);
        tour.set(index2, cost1);
    }
}
