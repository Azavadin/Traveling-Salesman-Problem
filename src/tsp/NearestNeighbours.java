package tsp;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class NearestNeighbours {

    public List<Integer> getCities(int numOfCities){
        List<Integer> citieslist = new ArrayList<Integer>();
        for(int i = 0; i < numOfCities; i++)
            citieslist.add(0);
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

    private void printBestTour(List<Integer> bestTour, int costFunction, String filename) throws IOException{
        int tourCost = calculateTourCost(bestTour, costFunction);
        System.out.println("Best cost = "+tourCost);
        System.out.println("Tour = ");
        System.out.println(bestTour);
        FileWriter fileWriter = new FileWriter(new File(filename));
        fileWriter.write(Integer.toString(tourCost));
        fileWriter.write("\n");
        for (int city = 0; city < bestTour.size(); city++){
        fileWriter.write(Integer.toString(city)+" ");
        }
        fileWriter.flush();
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



    public void NearestNeighboursTSP(int numOfCities, int costFunction, String fileName) throws IOException{
        List<Integer> theBestOrderOfCitiesVisited = getCities(numOfCities);
        int theBestTourCost = Integer.MAX_VALUE;
        int MEB = 0;
        for (int seed = 0; seed < numOfCities; seed++) {
            List<Integer> visitedStatusOfCities = getCities(numOfCities);
            List<Integer> orderOfCitiesVisited = new ArrayList<Integer>();
            int startCity = seed;
            visitedStatusOfCities.set(startCity, 1);
            orderOfCitiesVisited.add(startCity);
            int numOfCitiesVisited = 1;
            int TourCost = 0;
            int mostRecentlyVisitedCity = startCity;

            while (numOfCitiesVisited <= numOfCities) {
                int minDistanceCityCost = Integer.MAX_VALUE;
                int cityNumber = startCity;
                for (int i = 0; i < numOfCities; i++) {
                    if (visitedStatusOfCities.get(i) == 0) {
                        int cost = evaluateCost(mostRecentlyVisitedCity, i, costFunction);
                        if (cost < minDistanceCityCost) {
                            minDistanceCityCost = cost;
                            cityNumber = i;
                        }
                    }
                }
                visitedStatusOfCities.set(cityNumber, 1);
                orderOfCitiesVisited.add(cityNumber);
                mostRecentlyVisitedCity = cityNumber;
                TourCost = TourCost + minDistanceCityCost;
                numOfCitiesVisited++;
            }
            if(calculateTourCost(orderOfCitiesVisited, costFunction) < theBestTourCost){
                theBestTourCost = calculateTourCost(orderOfCitiesVisited, costFunction);
                theBestOrderOfCitiesVisited = orderOfCitiesVisited;
            }
        }
        printBestTour(theBestOrderOfCitiesVisited, costFunction, fileName);
    }
}
