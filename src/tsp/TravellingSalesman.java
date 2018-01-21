package tsp;


import java.io.IOException;

public class TravellingSalesman {
    public static void main(String args[]) throws IOException{
        int strategy = Integer.parseInt(args[0]);
        int numOfCities = Integer.parseInt(args[1]);
        int costFunction = Integer.parseInt(args[2]);
        String fileName = args[3];

        NearestNeighbours nntsp = new NearestNeighbours();
        SimulatedAnnealingTSP satsp = new SimulatedAnnealingTSP();
        switch (strategy){
            case 1 :
                nntsp.NearestNeighboursTSP(numOfCities, costFunction, fileName);
                break;
            case 2:
                satsp.simulatedTSP(numOfCities, costFunction, fileName);
                break;
        }
    }
}
