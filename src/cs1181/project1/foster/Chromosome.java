/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs1181.project1.foster;

import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author John Foster
 */
public class Chromosome extends ArrayList<Item> implements Comparable<Chromosome> {

    private static Random rng = new Random();

    public Chromosome() {

    }

    public Chromosome(ArrayList<Item> items) {
        // copy the items in the arraylist into the chromosome (which is a list)
        for (int i = 0; i < items.size(); i++) {
            this.add(new Item(items.get(i)));
        }
        // use rng to decide if included is T or F (the included value from Item.java)
        for (int i = 0; i < items.size(); i++) { // loops through item
            int determination = rng.nextInt(10) + 1; // this number will be used to decide if the value is T or F
            if (determination >= 1 && determination <= 5) { // if 1-5
                this.get(i).setIncluded(true); // it is included
            } else if (determination > 5 && determination <= 10) { // if 6-10
                this.get(i).setIncluded(false); // it is not included
            }
        }
    }

    public Chromosome crossover(Chromosome other) {
        // create child Chromosome using crossover (based on this.Chromosome and other.Chromosome)
        // needs to be NEW COPY of chromosome to prevent changing original copies
        Chromosome child = new Chromosome(); // create new Chromosome
        for (int i = 0; i < other.size(); i++) { // loops through chromosome
            int determination = rng.nextInt(10) + 1; // rng
            if (determination >= 1 && determination <= 5) { // if 1-5
                child.add(this.get(i)); // parent 1
            } else if (determination > 5 && determination <= 10) { // if 6-10
                child.add(other.get(i)); // parent 2
            }
        }
        // return the child Chromosome
        return child;
    }

    public void mutate() {
        // perform mutation on chromosome
        for (int i = 0; i < this.size(); i++) { // loops through all items
            int determination = rng.nextInt(10) + 1;
            if (determination == 1) { // if rng is 1
                if (this.get(i).isIncluded() == true) { // if the included is true
                    this.get(i).setIncluded(false); // set false
                } else this.get(i).setIncluded(true); // else set true
            }
        }
    }

    public int getFitness() {
        // returns the fitness of the Chromosome
        int fitness = 0;
        double totalWeight = 0;
        for (int i = 0; i < this.size(); i++) {
            if (this.get(i).isIncluded() == true) {
                totalWeight += this.get(i).getWeight();
                fitness += this.get(i).getValue();
            }
        }
        // if weight > 10; return 0
        if (totalWeight > 10.0) {
            fitness = 0;
            return fitness;
        }
        // else; fitness = sum of values
        else {
            return fitness;
        }
    }

    @Override
    public int compareTo(Chromosome other) {
        // return -1 if this.Chromosome fitness is greater than other.Chromosome fitness
        if (this.getFitness() > other.getFitness()) {
            return -1;
        }
        // return +1 if this.Chromosome fitness is less than other.Chromosome fitness
        if ( this.getFitness() < other.getFitness()) {
            return 1;
        }            
        // return 0 of fitness is the same
        else {
            return 0;
        }
    }

    @Override
    public String toString() {
        // display the name, weight, and value of all items in this.Chromosome with an included value of T, followed by Chromosome fitness (total value)
        String temp = "";
        for (int i = 0; i < this.size(); i++) {
            if (this.get(i).isIncluded() == true) {
                temp += this.get(i)  + " ";
            }
        }
        temp = temp + " => " + getFitness();
        return temp;
    }
}
