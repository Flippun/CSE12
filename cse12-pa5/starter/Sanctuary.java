/*
 * Name Flip Frauenzimmer
 * Email: ffrauenzimmer@ucsd.edu
 * PID: A17685777
 * Sources Used: JDK 17 Documentation, PA5 Write-up
 * 
 * This file is for CSE 12 PA5 in Spring 2023,
 * and contains the implementation of the sanctuary class.
 */

import java.util.HashMap;

/**
 * Class that defines the Sanctuary object and several methods
 * regarding it's implementation.
 * 
 * Instance Variables: 
 * sanctaury - Container to store all the animal species in the sanctuary. 
 * maxAnimals - maximum number of animals that the sanctuary can support.
 * maxSpeecies - maximum number of species that the sanctuary can support.
 */
public class Sanctuary {
    HashMap<String, Integer> sanctuary;
    private final int maxAnimals;
    private final int maxSpecies;

    /**
     * Constructor that initializes the sanctuary's information.
     * 
     * @param maxAnimals maximum number of animals to be supported.
     * @param maxSpecies maximum number of species to be supported
     */
    public Sanctuary(int maxAnimals, int maxSpecies) {
        if (maxAnimals <= 0 || maxSpecies <= 0 || maxSpecies > maxAnimals) {
            throw new IllegalArgumentException();
        }
        this.maxAnimals = maxAnimals;
        this.maxSpecies = maxSpecies;
        this.sanctuary = new HashMap<>();
    }

    /**
     * Determines the number of animals in the sanctuary that are 
     * of the given species
     * 
     * @param species species in question
     * @return the number of animals in the sanctuary that are 
     *         of the given species
     */
    public int countForSpecies(String species) {
        if (species == null) {
            throw new IllegalArgumentException();
        }
        if (sanctuary.containsKey(species)) {
            return sanctuary.get(species);
        }
        return 0;
    }

    /**
     * Finds the total number of animals in the sanctuary.
     * 
     * @return the total number of animals in the sanctuary.
     */
    public int getTotalAnimals() {
        int TotalAnimals = 0;
        for (int Animals: sanctuary.values()) {
            TotalAnimals += Animals;
        }
        return TotalAnimals;
    }

    /**
     * Finds the total number of species in the sanctuary.
     * 
     * @return the total number of species in the sanctuary.
     */
    public int getTotalSpecies() {
        return sanctuary.size();
    }

    /**
     * Find the maximum allowed number of animals in the sanctuary.
     * 
     * @return the maximum allowed number of animals in the sanctuary.
     */
    public int getMaxAnimals() {
        return maxAnimals;
    }

    /**
     * Find the maximum allowed number of species in the sanctuary.
     * 
     * @return the maximum allowed number of species in the sanctuary.
     */
    public int getMaxSpecies() {
        return maxSpecies;
    }

    /**
     * Add num animals of species to the sanctuary
     * 
     * @param species species of animals to be added
     * @param num number of aniamls to be added
     * @return the number of animals that could not be rescued.
     */
    public int rescue(String species, int num) {
        if (num <= 0 || species == null) {
            throw new IllegalArgumentException();
        }
        if (sanctuary.containsKey(species) 
                || getTotalSpecies() + 1 <= getMaxSpecies()) {
            int UnAddedAnimals = 0;
            if (num + getTotalAnimals() <= getMaxAnimals()) {
                sanctuary.put(species, countForSpecies(species) + num);
            }
            else {
                UnAddedAnimals = num - (getMaxAnimals() - getTotalAnimals());
                int AddedAnimals = num - UnAddedAnimals;
                if (UnAddedAnimals != num) {
                    sanctuary.put(species, countForSpecies(species) + AddedAnimals);
                }
            }
            return UnAddedAnimals;
        }
        return num;
    }

    /**
     * Remove num animals of species from the sanctuary.
     * 
     * @param species species of animals to be removed
     * @param num number of animals to be removed
     */
    public void release(String species, int num) {
        if (num <= 0 || species == null || num > sanctuary.get(species)
                || !sanctuary.containsKey(species)) {
            throw new IllegalArgumentException();
        }
        if (sanctuary.get(species) - num == 0) {
            sanctuary.remove(species);
        }
        else {
            sanctuary.put(species, sanctuary.get(species) - num);
        }
    }
}




