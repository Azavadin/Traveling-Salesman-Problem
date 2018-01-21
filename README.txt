1. download Sakamuri_1530974_Project1.zip
2. unzip Sakamuri_1530974_Project1.zip
3. Click on Project1 and then open command prompt
4. change the control to project directory. Go to src folder.
5. compile the program using the command "javac tsp/*.java"
6. Run the program using the command "java tsp.TravellingSalesman Strategy NUmOfCities 	  CostFunction output.txt"
7. Strategy, NUmOfCities CostFunction output.txt are the arguments
8. Strategy = 1 for Nearest Neighbors(Simple strategy) and Strategy = 2 for Simulated Annealing(Sophisticated startegy)
8. Pass the arguments in the correct order.
9. You can find the result in console as well as output.txt

NOTE: I have also implemented A star and Genetic algorithms and placed them in the folder ASTARandGENETIC(other strategies). You need to pass the same arguments to it. Strategy = 1 for Genetic approach and 2 for Astar.
To execute it:
compile the program using the command "javac TSP/*.java"
Run the program using the command "java TSP.TravellingSalesman Strategy NUmOfCities 	  CostFunction output.txt"