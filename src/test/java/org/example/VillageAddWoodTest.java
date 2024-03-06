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

class VillageAddWoodTest {
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
     * A test to test if a worker gathers food when the AddWood() method calls.
     * The first parameter is the name of the worker.
     * The second parameter is how much wood the village start with.
     * The third parameter is how much wood that's expected after the AddWood() method is called.
     */
    @ParameterizedTest
    @CsvSource(value = {"Monica,0,1", "Rachel,8,9", "Phoebe,5,6", "Chandler,14,15", "Ross,7,8", "Joey,2,3"})
    public void Village_AddWood_AddFromCsvValues_Success(String name, int startWood, int expected){
        village.setWood(startWood);

        village.AddWood(name);
        int actual = village.getWood();

        assertEquals(expected, actual);
    }
    /*
     * A test to test if a worker don't gather food when the AddWood() method is called.
     * The first parameter is the name of the worker
     * The second parameter is much wood the village start with.
     * The third parameter is how much wood that's expected after the AddWood() method is called.
     */
    @ParameterizedTest
    @CsvSource(value = {"Monica,9,15", "Rachel,8,15", "Phoebe,5,15", "Chandler,14,16", "Ross,7,15", "Joey,2,15"})
    public void Village_AddWood_AddFromCsvValues_Fail(String name, int startWood, int expected){
        village.setWood(startWood);

        village.AddWood(name);
        int actual = village.getWood();

        assertNotEquals(expected, actual);
    }
}