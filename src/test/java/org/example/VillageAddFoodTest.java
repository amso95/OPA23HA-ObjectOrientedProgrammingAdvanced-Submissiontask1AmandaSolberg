package org.example;

import org.example.objects.Building;
import org.example.objects.Project;
import org.example.objects.Worker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class VillageAddFoodTest {
    Village village;
    @BeforeEach
    public void setup(){
        Building house = new Building("House");
        ArrayList<Worker> workers = new ArrayList<>();
        ArrayList<Building> buildings = new ArrayList<>();
        ArrayList<Project> projects = new ArrayList<>();
        buildings.add(house);
        buildings.add(house);
        buildings.add(house);
        this.village = new Village(false, 10, 0, 0, workers, buildings, projects, 1, 1, 5, 6, 0,5);
    }
    /*
    * A test to test if a worker gathers food when the AddFood() method calls.
    * The first parameter is the name of the worker.
    * The second parameter is how much food the village start with.
    * The third parameter is how much food that's expected after the AddFood() method is called.
    */
    @ParameterizedTest
    @CsvSource(value = {"Monica,10,15", "Rachel,8,13", "Phoebe,5,10", "Chandler,14,19", "Ross,7,12", "Joey,2,7"})
    public void Village_AddFood_AddFromCsvValues_Success(String name, int startFood, int expected){
        village.setFood(startFood);

        village.AddFood(name);
        int actual = village.getFood();

        assertEquals(expected, actual);
    }
    /*
    * A test to test if a worker don't gather food when the AddFood() method is called.
    * The first parameter is the name of the worker
    * The second parameter is much food the village start with.
    * The third parameter is how much food that's expected after the AddFood() method is called.
    */
    @ParameterizedTest
    @CsvSource(value = {"Monica,10,15", "Rachel,8,13", "Phoebe,5,10", "Chandler,14,19", "Ross,7,12", "Joey,2,7"})
    public void Village_AddFood_AddFromCsvValues_Fail(String name, int startFood, int expected){
        village.setFood(startFood);
        village.setFoodPerDay(0);

        village.AddFood(name);
        int actual = village.getFood();

        assertNotEquals(expected, actual);
    }
}