package genetic;

import datatype.*;
import datatype.List;
import interpreter.InterpreterException;
import interpreter.ParseException;

import java.util.*;

/**
 * Main class, deals with logic of genetic algorithm
 */
public class GP {

    // GA VARIABLES
    private int population_size = 1000;
    private int generations = 1;
    private String problem_name = "successor";


    private ArrayList<Individual> individualList;

    private ArrayList<Long> selectionTime = new ArrayList<>();
    private ArrayList<Long> fitnessTime = new ArrayList<>();
    private ArrayList<Long> crossoverTime = new ArrayList<>();
    private ArrayList<Long> mutationTime = new ArrayList<>();

    private ArrayList<Long> time1 = new ArrayList<>();
    private ArrayList<Long> time2 = new ArrayList<>();
    private ArrayList<Long> time3 = new ArrayList<>();

    private boolean foundSolution;

    private static ArrayList<Integer> averageIter = new ArrayList<>();

    public static void main(String[] args) {
        int number_of_GAs = 1;
        for (int i = 0; i < number_of_GAs; i++) {
            new GP().run();
        }
        //System.out.println("Array:" + Arrays.toString(averageIter.toArray()));
        int sum = 0;
        for (Integer l : averageIter) sum += l;
        double avg = 1.0d * sum / averageIter.size();
        GP gp = new GP();
        //System.out.println("Avg: " + avg + " / " + (gp.generations * gp.population_size));
        avg = avg / (gp.generations * gp.population_size);
        avg = 1 / avg;
        //System.out.println("Value: " + avg);
    }

    private GP() {
    }

    private void run() {
        foundSolution = false;
        System.out.print("Creating population: ");
        initialiseRandomPopulation(population_size);

        if (false) {
            int iter = 0;
            for (Individual i : individualList) {
                i.num = iter;
                if (checkFitness(i) > -1) {
                    System.out.println(iter + ": " + checkFitness(i));
                    System.out.println(i.getProgramAsString());
                    //getProgramAsBitString(i);
                }
                iter++;
            }
        }

        if (true) {
            ArrayList<Integer> avgFit = new ArrayList<>();
            for (Individual i : individualList) {
                avgFit.add(checkFitness(i));
            }
            System.out.println("Average fitness: " + getAverageI(avgFit));
        }

        if (true) {
            System.out.print("\nExecuting GA: ");
            int i = -1;
            int progress;
            while (i < generations * population_size && !foundSolution) {
                i++;
                progress = (i * 100) / (generations * population_size);
                //double gen = i/population_size;
                if (progress % 2 == 0)
                    System.out.print("\rExecuting GA: " + progress + "% done  (" + i + "/" + generations * population_size + ")");

                tournamentSelection();
            }
            System.out.println("\rExecuting GA: 100% done. " + i + " iterations\n");


            printHighestFitness();
            System.out.println("Solution found on iteration: " + i + ", generation " + (i/population_size));
            averageIter.add(i);

            if (false) {
                System.out.println("Selection:\t" + getAverageL(selectionTime) + "\tms");
                System.out.println("Fitness:\t\t" + getAverageL(fitnessTime) + "\t\tms");
                System.out.println("Crossover:\t" + getAverageL(crossoverTime) + "\tms");
            }
        }
        //System.out.println(Arrays.toString(averageIter.toArray()));
    }

    private double getAverageL(ArrayList<Long> list) {
        int sum = 0;
        for (Long l : list) sum += l;
        return 1.0d * sum / list.size();
    }

    private double getAverageI(ArrayList<Integer> list) {
        int sum = 0;
        for (Integer l : list) sum += l;
        return 1.0d * sum / list.size();
    }

    public void initialiseRandomPopulation(int numberOfIndividuals) {
        individualList = new RandomIndividuals(numberOfIndividuals).getRandomIndividuals();
    }

    private int checkFitness(Individual individual) {
        switch (problem_name) {
            case "successor":
                return executeSuccessor(individual);
            case "square":
                return executeSquare(individual);
            default: return 0;
        }
    }

    private int getMaxFitnessForProblem(String problem) {
        if (problem.equals("successor")) return 5;
        if (problem.equals("square")) return 5;
        else return 4;
    }

    private int executeSuccessor(Individual individual) {
        int fitness = 0;
        Data output = getOutputFromInput(individual, new Num("0").dataToString());
        // test for 1
        if (output != null && output.equality(new Num("1"))) {
            fitness++;
            output = getOutputFromInput(individual, new Num("1").dataToString());
            // test for 2
            if (output != null && output.equality(new Num("2"))) {
                fitness++;
                // random testing
                for (int i = 0; i < 3; i++) {
                    int randomNum = new Random().nextInt(1000);
                    output = getOutputFromInput(individual, new Num(Integer.toString(randomNum)).dataToString());
                    if (output != null && output.equality(new Num(Integer.toString(randomNum+1)))) {
                        fitness++;
                    }
                }
            }
        }
        return fitness;
    }

    private int executeSquare(Individual individual) {
        int fitness = 0;

        // test for 0
        Data output = getOutputFromInput(individual, new Num("0").dataToString());
        if (output != null && output.equality(new Num("0")))
            fitness++;
        else return fitness;

        // test for 1
        output = getOutputFromInput(individual, new Num("1").dataToString());
        if (output != null && output.equality(new Num("2")))
            fitness++;
        else return fitness;

        // test for 2
        output = getOutputFromInput(individual, new Num("2").dataToString());
        if (output != null && output.equality(new Num("4")))
            fitness++;
        else return fitness;

        // test for 3
        output = getOutputFromInput(individual, new Num("3").dataToString());
        if (output != null && output.equality(new Num("6")))
            fitness++;
        else return fitness;

        // test for random number
        int randomNumber = new Random().nextInt(100);
        output = getOutputFromInput(individual, new Num(Integer.toString(randomNumber)).dataToString());
        if (output != null && output.equality(new Num(Integer.toString(randomNumber*2))))
            fitness++;

        return fitness;
    }

    private Data getOutputFromInput(Individual individual, String input) {
        Data output = null;
        interpreter.ProgramParser interpreter = new interpreter.ProgramParser(individual.getProgramAsString(), input);
        try {
            output = interpreter.runProgram();
            //System.out.print("Output: " + output.dataToString());
            return output;
        } catch (ParseException | inputparser.ParseException | InterpreterException | UnsupportedOperationException e) {
            //System.out.print("error");
            return null;
        }
    }

    private void tournamentSelection() {
        long startTime = System.currentTimeMillis();

        /*
        // Individual 1 - select through best fit
        ArrayList<Individual> listOfFitInd = new ArrayList<>();
        Individual ind1;
        for (Individual i : individualList) {
            if (checkFitness(i, problem_name) > 0) listOfFitInd.add(i);
        }
        if (listOfFitInd.isEmpty()) {
            ind1 = individualList.get(new Random().nextInt(individualList.size()));
        } else {
            ind1 = listOfFitInd.get(new Random().nextInt(listOfFitInd.size()));
        }

        Individual ind2 = individualList.get(new Random().nextInt(individualList.size()));
        while (ind1 != ind2) ind2 = individualList.get(new Random().nextInt(individualList.size()));
        */

        int tries = 0;
        Individual ind1 = individualList.get(new Random().nextInt(population_size));
        while(checkFitness(ind1) < 1) {
            ind1 = individualList.get(new Random().nextInt(population_size));
            tries++;
            if (tries > population_size) break;
        }

        if (checkFitness(ind1) < 2) {
            tries = 0;
            ind1 = individualList.get(new Random().nextInt(population_size));
            while (checkFitness(ind1) < 0) {
                ind1 = individualList.get(new Random().nextInt(population_size));
                tries++;
                if (tries > population_size) break;
            }
        }

        Individual ind2 = individualList.get(new Random().nextInt(population_size));
        while (ind1 == ind2) {
            ind2 = individualList.get(new Random().nextInt(population_size));
        }

        selectionTime.add(System.currentTimeMillis()-startTime);
        //System.out.print("\r.");


        startTime = System.currentTimeMillis();

        int ind1Fit = checkFitness(ind1);
        int ind2Fit = checkFitness(ind2);

        fitnessTime.add(System.currentTimeMillis()-startTime);
        //System.out.print("\r..");


        startTime = System.currentTimeMillis();
        // ind1 fitter
        if (ind1Fit > ind2Fit) {
            performCrossover(ind1, ind2);
        }
        // ind2 fitter
        else if (ind1Fit < ind2Fit) {
            performCrossover(ind2, ind1);
        }
        // else get random
        else {
            if (new Random().nextBoolean()) performCrossover(ind1, ind2);
            else performCrossover(ind2, ind1);
        }

        crossoverTime.add(System.currentTimeMillis()-startTime);
        //System.out.print("\r...");
    }

    private Boolean performCrossover(Individual superior, Individual inferior) {
        // should be a list as it's a program
        if (!(superior.getProgramAsData() instanceof List) || !(inferior.getProgramAsData() instanceof List)) {
            return false;
        }

        // 90% of crossover is on a function (cmd), rest on expressions
        int cmdOrExpr = new Random().nextInt(100);

        // INFERIOR LIST
        List inferiorList = (List) ((List) inferior.getProgramAsData()).getList().get(1);
        ArrayList<ArrayList<Object>> infTreeList = analyseTreeForCrossover(inferiorList, new ArrayList<>());


        // SUPERIOR LIST
        List superiorList = (List) ((List) superior.getProgramAsData()).getList().get(1);
        ArrayList<ArrayList<Object>> supTreeList = analyseTreeForCrossover(superiorList, new ArrayList<>());


        // CHECK PROBABILITY
        ArrayList<Data> availableInfSubtrees = new ArrayList<>();
        ArrayList<Data> availableSupSubtrees = new ArrayList<>();
        if (cmdOrExpr < 90) {
            for (ArrayList<Object> tagAndData : infTreeList) {
                if (tagAndData.get(0).equals("cmd")) availableInfSubtrees.add((Data) tagAndData.get(2));
            }
            for (ArrayList<Object> tagAndData : supTreeList) {
                if (tagAndData.get(0).equals("cmd")) availableSupSubtrees.add((Data) tagAndData.get(2));
            }
        } else {
            for (ArrayList<Object> tagAndData : infTreeList) {
                if (tagAndData.get(0).equals("expr")) availableInfSubtrees.add((Data) tagAndData.get(2));
            }
            for (ArrayList<Object> tagAndData : supTreeList) {
                if (tagAndData.get(0).equals("expr")) availableSupSubtrees.add((Data) tagAndData.get(2));
            }
        }

        // get two chosen subtrees
        try {
            String chosenInfData = availableInfSubtrees.get(new Random().nextInt(availableInfSubtrees.size())).dataToString();
            String chosenSupData = availableSupSubtrees.get(new Random().nextInt(availableSupSubtrees.size())).dataToString();
            // replace sup string with inf string and set new program
            String superiorString = superior.getProgramAsData().dataToString();
            superiorString = superiorString.replace(chosenSupData, chosenInfData);
            inferior.setProgramWithData(superiorString);
            if (checkFitness(inferior) == getMaxFitnessForProblem(problem_name)) {
                foundSolution = true;
            }

            //performMutation(inferior);

            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private ArrayList<ArrayList<Object>> analyseTreeForCrossover(Data tree, ArrayList<ArrayList<Object>> list) {
        if (tree instanceof List) {
            List newTree = (List) tree;
            if (!newTree.getList().isEmpty()) {
                Data firstChild = newTree.getList().get(0);
                if (firstChild instanceof Atom) {
                    Atom first = (Atom) firstChild;
                    switch (first.getValue()) {
                        case ":=":
                            list.add(new ArrayList<>(Arrays.asList("cmd", "assign", newTree)));
                            break;
                        case "while":
                            list.add(new ArrayList<>(Arrays.asList("cmd", "while", newTree)));
                            break;
                        case "if":
                            list.add(new ArrayList<>(Arrays.asList("cmd", "if", newTree)));
                            break;
                        case "hd":
                            list.add(new ArrayList<>(Arrays.asList("expr", "hd", newTree)));
                            break;
                        case "cons":
                            list.add(new ArrayList<>(Arrays.asList("expr", "cons", newTree)));
                            break;
                        case "tl":
                            list.add(new ArrayList<>(Arrays.asList("expr", "tl", newTree)));
                            break;
                    }
                }
            }
            for (Data child : newTree.getList()) {
                if (child instanceof List) analyseTreeForCrossover(child, list);
            }
        }
        return list;
    }

    private void printHighestFitness() {
        int highestFitness = 0;
        int fitness;
        Individual bestInd = null;
        for (Individual i : individualList) {
            fitness = checkFitness(i);
            if (fitness > highestFitness) {
                highestFitness = fitness;
                bestInd = i;
            }
        }
        System.out.println("Highest fitness: " + highestFitness);
        if (bestInd != null) System.out.println(bestInd.getProgramAsString());
    }
}
