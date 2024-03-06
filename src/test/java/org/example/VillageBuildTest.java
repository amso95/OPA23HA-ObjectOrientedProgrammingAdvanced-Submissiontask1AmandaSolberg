package org.example;

import org.example.objects.Building;
import org.example.objects.Project;
import org.example.objects.Worker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class VillageBuildTest {
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
     * A test to test if one worker is building on a project that is in the village.
     * The first parameter is the name of the worker.
     * The second parameter is the name of the project.
     * The third parameter is the amount of wood in the village.
     * The fourth parameter is the amount of metal in the village.
     * The fifth parameter is number of days left of the project.
     * The sixth parameter is the expected number of days left after the method Build() har been called.
     */
    @ParameterizedTest
    @CsvSource(value = {"Monica,House,5,0,3,2", "Rachel,Woodmill,5,1,5,4", "Phoebe,Quarry,3,5,7,6", "Chandler,Farm,5,2,5,4", "Ross,Castle,50,50,50,49"})
    public void Village_Build_BuildFromCsvValues_Success(String builderName, String name,int wood, int metal, int daysLeft, int expected){
        ArrayList<Project> projects = new ArrayList<>();
        Project project = new Project(name, daysLeft, null);
        projects.add(project);
        village.setWood(wood);
        village.setMetal(metal);
        village.setProjects(projects);

        village.Build(builderName);

        assertEquals(expected, project.getDaysLeft());
    }
    /*
     * A test to test if one worker is building on a project in the village that get finished.
     * The first parameter is the name of the worker.
     * The second parameter is the name of the project.
     * The third parameter is the amount of wood in the village.
     * The fourth parameter is the amount of metal in the village.
     * The fifth parameter is number of days left of the project.
     * The sixth parameter is the expected number of days left after the method Build() har been called.
     * The seventh parameter is the expected size of the ArrayList of buildings in the village.
     * The eight parameter is the expected amount of food per day in the village.
     * The ninth parameter is the expected amount of metal per day in the village.
     * The tenth parameter is the expected amount of wood per day in the village.
     * The eleventh parameter is the expected game over boolean value in the village.
     */
    @ParameterizedTest
    @CsvSource(value = {"Monica,House,5,0,1,0,4,5,1,1,false", "Rachel,Woodmill,5,1,1,0,4,5,1,2,false", "Phoebe,Quarry,3,5,1,0,4,5,2,1,false", "Chandler,Farm,5,2,1,0,4,10,1,1,false", "Ross,Castle,50,50,1,0,4,5,1,1,true"})
    public void Village_Build_BuildFromCsvValues_Finish(String builderName, String name,int wood, int metal, int daysLeft, int expectedDaysLeft, int expectedBuildingsSize,
                                                        int expectedFoodPerDay, int expectedMetalPerDay, int expectedWoodPerDay, boolean expectedGameOver){
        ArrayList<Project> projects = new ArrayList<>();
        Project project = new Project(name, daysLeft, () -> village.getPossibleProjects().get(name).GetProject().Complete());
        projects.add(project);
        village.setWood(wood);
        village.setMetal(metal);
        village.setProjects(projects);

        village.Build(builderName);

        assertAll(
                () -> assertEquals(expectedDaysLeft, project.getDaysLeft()),
                () -> assertEquals(expectedBuildingsSize, village.getBuildings().size()),
                () -> assertEquals(expectedFoodPerDay, village.getFoodPerDay()),
                () -> assertEquals(expectedMetalPerDay, village.getMetalPerDay()),
                () -> assertEquals(expectedWoodPerDay, village.getWoodPerDay()),
                () -> assertEquals(expectedGameOver, village.isGameOver())
        );
    }
    /*
     * A test to test if workers is building on a project in the village that get finished.
     * Continuing to the next day to see that the new buildings effect is implemented in the village.
     * The first parameter is the name of the project.
     * The second parameter is the number of days left of the project.
     * The third parameter is the expected number of days left after the method Build() har been called.
     * The fourth parameter is the expected size of the ArraysList of buildings in the village.
     * The fifth parameter is the expected number of max workers in the village.
     * The sixth parameter is the expected number of wood in the village
     * The seventh parameter is the expected number of metal in the village.
     * The eight parameter is the expected number of food in the village.
     * The ninth parameter is the expected value of the game over boolean in the village.
     * The tenth parameter is the expected number of wood per day in the village.
     * The eleventh parameter is the expected number of metal per day in the village.
     * The twelfth parameter is the expected number of food per day in the village.
     */
    @ParameterizedTest
    @CsvSource(value = {"House,1,0,4,8,2,1,9,false,1,1,5", "Woodmill,1,0,4,6,4,1,9,false,2,1,5", "Quarry,1,0,4,6,2,2,9,false,1,2,5",
            "Farm,1,0,4,6,2,1,14,false,1,1,10", "Castle,1,0,4,6,2,1,9,true,1,1,5"})
    public void Village_Build_BuildFromCsvValues_FinishHouseNextDay(String name, int daysLeft, int expectedDaysLeft, int expectedBuildingsSize,
                                                                    int expectedMaxWorkers, int expectedWood, int expectedMetal, int expectedFood,
                                                                    boolean expectedGameOver, int expectedWoodPerDay, int expectedMetalPerDay,
                                                                    int expectedFoodPerDay){
        Building house = new Building("House");
        ArrayList<Worker> workers = new ArrayList<>();
        ArrayList<Building> buildings = new ArrayList<>();
        ArrayList<Project> projects = new ArrayList<>();
        buildings.add(house);
        buildings.add(house);
        buildings.add(house);
        Village village = new Village(false, 10, 0, 0, workers, buildings, projects, 1, 1, 5, 6, 0,5);
        workers.add(new Worker("Monica", "lumberjack", village.getOccupationHashMap().get("builder")));
        workers.add(new Worker("Rachel", "lumberjack", village.getOccupationHashMap().get("lumberjack")));
        workers.add(new Worker("Phoebe", "builder", village.getOccupationHashMap().get("lumberjack")));
        workers.add(new Worker("Chandler", "farmer", village.getOccupationHashMap().get("farmer")));
        workers.add(new Worker("Ross", "miner", village.getOccupationHashMap().get("miner")));
        workers.add(new Worker("Joey", "builder", village.getOccupationHashMap().get("builder")));
        Project project = new Project(name, daysLeft, () -> village.getPossibleProjects().get(name).GetProject().Complete());
        village.getProjects().add(project);

        village.Day();

        assertAll(
                () -> assertEquals(expectedDaysLeft, project.getDaysLeft()),
                () -> assertEquals(expectedBuildingsSize, village.getBuildings().size()),
                () -> assertEquals(expectedMaxWorkers,village.getMaxWorkers()),
                () -> assertEquals(expectedWood,village.getWood()),
                () -> assertEquals(expectedWoodPerDay,village.getWoodPerDay()),
                () -> assertEquals(expectedMetal,village.getMetal()),
                () -> assertEquals(expectedMetalPerDay,village.getMetalPerDay()),
                () -> assertEquals(expectedFood,village.getFood()),
                () -> assertEquals(expectedFoodPerDay,village.getFoodPerDay()),
                () -> assertEquals(expectedGameOver,village.isGameOver())
        );
    }
    /*
     * A test to test if one worker have no projects to building on in the village.
     * The first parameter is the name of the worker.
     * The second parameter is the name of the project.
     * The third parameter is the amount of wood in the village.
     * The fourth parameter is the amount of metal in the village.
     * The fifth parameter is number of days left of the project.
     * The sixth parameter is the expected number of days left after the method Build() har been called.
     */
    @ParameterizedTest
    @CsvSource(value = {"Monica,House,5,0,3,3", "Rachel,Woodmill,5,1,5,5", "Phoebe,Quarry,3,5,7,7", "Chandler,Farm,5,2,5,5", "Ross,Castle,50,50,50,50"})
    public void Village_Build_BuildFromCsvValues_Fail(String builderName, String name,int wood, int metal, int daysLeft, int expected){
        ArrayList<Project> projects = new ArrayList<>();
        Project project = new Project(name, daysLeft, () -> village.getPossibleProjects().get(name).GetProject().Complete());
        village.setWood(wood);
        village.setMetal(metal);
        village.setProjects(projects);

        village.Build(builderName);

        assertEquals(expected, project.getDaysLeft());
    }
}