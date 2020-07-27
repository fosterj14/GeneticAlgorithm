package cs1181.project1.foster;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Scanner;

/**
 *
 * @author John Foster
 */
public class GeneticAlgorithm {

    final static int POP_SIZE = 10;

    /**
     * @param args the command line arguments
     * @throws java.io.FileNotFoundException
     */
    public static void main(String[] args) throws FileNotFoundException {
        ArrayList<Item> itemList = readData("Items.txt");
        // create set of 10 random individuals as initial population
        ArrayList<Chromosome> startPop = initializedPopulation(itemList, POP_SIZE); // gets initial 10
        ArrayList<Chromosome> currentPop = new ArrayList<>();
        for (int i = 0; i < startPop.size(); i++) {
            currentPop.add(startPop.get(i)); // places initial 10 into current population
        }
        ArrayList<Chromosome> children = new ArrayList<>();
        ArrayList<Chromosome> epoch = new ArrayList<>(); // this is the generation

        // TODO loop
        int count = 0;
        while (count <= 20) { // repeated 20 times
            // add individuals to next generation
            for (int i = 0; i < currentPop.size(); i++) { // goes through the current population
                epoch.add(currentPop.get(i)); // adds current population into the gerneration
            }
            // randomly pair individuals and perform crossover to create child and add child to next gen
            Collections.shuffle(epoch); // shuffles the generation
            for (int i = 0; i < epoch.size(); i += 2) { // loops through the generation
                Chromosome parent1 = epoch.get(i); // gets parent 1
                Chromosome parent2 = epoch.get(i + 1); // gets parent 2 as the Chromosome next to it
                Chromosome child = parent1.crossover(parent2); // runs crossover to create a child
                children.add(new Chromosome(child)); // add te child to the arraylist of children
            }
            // randomly choose 10% of next gen and expose to mutation
            Random rng = new Random();
            children.get(rng.nextInt(children.size())).mutate(); // picks a random child to run mutate on
            // sort next gen based off fitness
            for (int i = 0; i < children.size(); i++) {
                currentPop.add(children.get(i)); // puts children into current population
            }
            children.clear();
            Collections.sort(currentPop); // highest to lowest
            // clear current pop and add top 10 of next gen back into pop
            ArrayList<Chromosome> temp = new ArrayList<>();
            for (int i = 0; i < 10; i++) {
                temp.add(currentPop.get(i));
            }
            currentPop.clear();
            // get the top 10 fittest
            for (int i = 0; i < temp.size(); i++) {
                currentPop.add(temp.get(i));
            }
            temp.clear();
            // END loop
            count++;
            Collections.sort(currentPop);
        }
        // sort pop and display fittest individual
        Collections.sort(currentPop);
        for (int i = 0; i < 1; i++) {
            System.out.println(currentPop.get(i));
        }
    }

    public static ArrayList<Item> readData(String file) throws FileNotFoundException {
        // read in data file Items.txt
        File itemFile = new File(file);
        // make scanner to read file
        Scanner read = new Scanner(itemFile);
        read.useDelimiter(",");
        // make list
        ArrayList<Item> itemList = new ArrayList<>();
        // goes through file, creates 1 item out of each line, places them in list
        while (read.hasNext()) {
            String line = read.nextLine();
            Scanner lineRead = new Scanner(line);
            lineRead.useDelimiter(",");

            String itemName = lineRead.next().trim();
            double itemWeight = Double.parseDouble(lineRead.next().trim());
            int itemValue = Integer.parseInt(lineRead.next().trim());
            Item tempItem = new Item(itemName, itemWeight, itemValue);

            itemList.add(tempItem);
        }
        return itemList;
    }

    public static ArrayList<Chromosome> initializedPopulation(ArrayList<Item> items, int populationSize) {
        ArrayList<Chromosome> chromosomeList = new ArrayList<>();
        // creates ArrayList of with populationSize number of Chromosome objects (takes in the ArrayList of items)
        for (int i = 0; i < populationSize; i++) {
            Chromosome temp = new Chromosome(items);
            chromosomeList.add(temp);
        }
        // returns the ArrayList of Chromosomes
        return chromosomeList;
    }
}
