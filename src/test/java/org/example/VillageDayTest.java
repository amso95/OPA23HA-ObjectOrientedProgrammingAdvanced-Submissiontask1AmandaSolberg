package org.example;

import org.example.interfaces.IOccupationAction;
import org.example.objects.Building;
import org.example.objects.Project;
import org.example.objects.Worker;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class VillageDayTest {
    /*
     * A test to test if one worker is doing his/hers work when getting feed when calling the Day() method in village.
     * The first parameter is the name of the worker.
     * The second parameter is the occupation of the worker.
     * The third parameter is the amount of food in the village.
     * The fourth parameter is the amount of food per day in the village.
     * The fifth parameter is the expected food in the village after the Day() method is called.
     * The sixth parameter is the number of days gone in the village.
     * The seventh parameter is the expected number of days gone in the village after the Day() method is called.
     * The eight parameter is the amount of metal in the village.
     * The ninth parameter is the amount of metal per day in the village.
     * The tenth parameter is the expected metal in the village after the Day() method is called.
     * The eleventh parameter is the amount of wood in the village.
     * The twelfth parameter is the amount of wood per day in the village.
     * The thirteenth parameter is the expected wood in the village after the Day() method is called.
     * The fourteenth parameter is the expected number of days left of the project in the village after the Day() method is called.
     */
    @ParameterizedTest
    @CsvSource(value = {"Monica,lumberjack,10,5,9,0,1,2,1,2,5,1,6,5", "Rachel,lumberjack,10,5,9,0,1,2,1,2,2,1,3,5", "Phoebe,builder,5,5,4,0,1,2,1,2,2,1,2,4",
            "Chandler,farmer,2,5,6,0,1,2,1,2,5,1,5,5", "Ross,miner,8,5,7,0,1,3,1,4,5,1,5,5", "Joey,builder,7,5,6,0,1,4,1,4,5,1,5,4"})
    public void Village_Day_FeedWorkers_FromCsvValues_Success(String workerName, String occupation, int food, int foodPerDay, int expectedFood,
                                                              int daysGone, int expectedDaysGone, int metal, int metalPerDay, int expectedMetal,
                                                              int wood, int woodPerDay, int expectedWood, int expectedDaysLeft){
        Building house = new Building("House");
        ArrayList<Worker> workers = new ArrayList<>();
        ArrayList<Building> buildings = new ArrayList<>();
        ArrayList<Project> projects = new ArrayList<>();
        buildings.add(house);
        buildings.add(house);
        buildings.add(house);
        Village village = new Village(false, food, wood, metal, workers, buildings, projects, metalPerDay,
                                      woodPerDay, foodPerDay, 6, daysGone,5);
        Project project = new Project("Farm", 5, () -> village.getPossibleProjects().get("Farm").GetProject().Complete());
        projects.add(project);
        IOccupationAction workerInterface = village.getOccupationHashMap().get(occupation);
        Worker worker = new Worker(workerName, occupation, workerInterface);
        workers.add(worker);

        village.Day();

        assertAll(
                () -> assertEquals(expectedFood, village.getFood()),
                () -> assertEquals(expectedDaysGone, village.getDaysGone()),
                () -> assertEquals(expectedMetal, village.getMetal()),
                () -> assertEquals(expectedWood, village.getWood()),
                () -> assertEquals(expectedDaysLeft, village.getProjects().get(0).getDaysLeft()),
                () -> assertFalse(village.isGameOver())
        );
    }
    /*
     * A test to test if a worker is not feed if he/she is not alive and the game will end when method Day() is called.
     * The first parameter is the name of the worker.
     * The second parameter is the occupation of the worker.
     * The third parameter is number of days gone in village.
     * The fourth parameter is the expected number of days gone in the village after the method Day() is called.
     */
    @ParameterizedTest
    @CsvSource(value = {"Monica,lumberjack,0,1", "Rachel,lumberjack,5,6", "Phoebe,builder,10,11", "Chandler,farmer,7,8", "Ross,miner,2,3", "Joey,builder,50,51"})
    public void Village_Day_FeedWorkers_FromCsvValues_Fail(String workerName, String occupation, int daysGone, int expectedDaysGone){
        Building house = new Building("House");
        ArrayList<Worker> workers = new ArrayList<>();
        ArrayList<Building> buildings = new ArrayList<>();
        ArrayList<Project> projects = new ArrayList<>();
        buildings.add(house);
        buildings.add(house);
        buildings.add(house);
        Village village = new Village(false, 10, 0, 0, workers, buildings, projects, 1, 1, 5,
                            6, daysGone,5);
        Project project = new Project("Farm", 5, () -> village.getPossibleProjects().get("Farm").GetProject().Complete());
        projects.add(project);
        IOccupationAction workerInterface = village.getOccupationHashMap().get(occupation);
        Worker worker = new Worker(workerName, occupation, workerInterface, true, false, 5);
        workers.add(worker);

        village.Day();

        assertAll(
                () -> assertTrue(village.isGameOver()),
                () -> assertEquals(expectedDaysGone, village.getDaysGone()),
                () -> assertTrue(worker.isHungry()),
                () -> assertFalse(worker.isAlive())
        );
    }
    /*
     * A test to continue to the next day when there is no food in the village with max amount of worker.
     * The method Day() is called.
     */
    @Test
    public void Village_ContinueToNextDayWithoutFood_OneDay(){
        Village village = createFullVillage();
        village.setFoodPerDay(0);
        village.setWoodPerDay(0);
        village.setMetalPerDay(0);
        village.setFood(0);
        int expected = 1;

        village.Day();

        assertAll(
                () -> assertEquals(expected, village.getWorkers().get(0).getDaysHungry()),
                () -> assertEquals(expected, village.getWorkers().get(1).getDaysHungry()),
                () -> assertEquals(expected, village.getWorkers().get(2).getDaysHungry()),
                () -> assertEquals(expected, village.getWorkers().get(3).getDaysHungry()),
                () -> assertEquals(expected, village.getWorkers().get(4).getDaysHungry()),
                () -> assertEquals(expected, village.getWorkers().get(5).getDaysHungry()),
                () -> assertTrue(village.getWorkers().get(0).isHungry()),
                () -> assertTrue(village.getWorkers().get(1).isHungry()),
                () -> assertTrue(village.getWorkers().get(2).isHungry()),
                () -> assertTrue(village.getWorkers().get(3).isHungry()),
                () -> assertTrue(village.getWorkers().get(4).isHungry()),
                () -> assertTrue(village.getWorkers().get(5).isHungry()),
                () -> assertTrue(village.getWorkers().get(0).isAlive()),
                () -> assertTrue(village.getWorkers().get(1).isAlive()),
                () -> assertTrue(village.getWorkers().get(2).isAlive()),
                () -> assertTrue(village.getWorkers().get(3).isAlive()),
                () -> assertTrue(village.getWorkers().get(4).isAlive()),
                () -> assertTrue(village.getWorkers().get(5).isAlive())
        );
    }
    /*
     * A test to continue two days when there is no food in the village with max amount of worker.
     * The method Day() is called twice.
     */
    @Test
    public void Village_ContinueToNextDayWithoutFood_TwoDays(){
        Village village = createFullVillage();
        village.setFoodPerDay(0);
        village.setWoodPerDay(0);
        village.setMetalPerDay(0);
        village.setFood(0);
        int expected = 2;

        village.Day();
        village.Day();

        assertAll(
                () -> assertEquals(expected, village.getWorkers().get(0).getDaysHungry()),
                () -> assertEquals(expected, village.getWorkers().get(1).getDaysHungry()),
                () -> assertEquals(expected, village.getWorkers().get(2).getDaysHungry()),
                () -> assertEquals(expected, village.getWorkers().get(3).getDaysHungry()),
                () -> assertEquals(expected, village.getWorkers().get(4).getDaysHungry()),
                () -> assertEquals(expected, village.getWorkers().get(5).getDaysHungry()),
                () -> assertTrue(village.getWorkers().get(0).isHungry()),
                () -> assertTrue(village.getWorkers().get(1).isHungry()),
                () -> assertTrue(village.getWorkers().get(2).isHungry()),
                () -> assertTrue(village.getWorkers().get(3).isHungry()),
                () -> assertTrue(village.getWorkers().get(4).isHungry()),
                () -> assertTrue(village.getWorkers().get(5).isHungry()),
                () -> assertTrue(village.getWorkers().get(0).isAlive()),
                () -> assertTrue(village.getWorkers().get(1).isAlive()),
                () -> assertTrue(village.getWorkers().get(2).isAlive()),
                () -> assertTrue(village.getWorkers().get(3).isAlive()),
                () -> assertTrue(village.getWorkers().get(4).isAlive()),
                () -> assertTrue(village.getWorkers().get(5).isAlive())
        );
    }
    /*
     * A test to continue three days when there is no food in the village with max amount of worker.
     * The method Day() is called three times.
     */
    @Test
    public void Village_ContinueToNextDayWithoutFood_ThreeDays(){
        Village village = createFullVillage();
        village.setFoodPerDay(0);
        village.setWoodPerDay(0);
        village.setMetalPerDay(0);
        village.setFood(0);
        int expected = 3;

        village.Day();
        village.Day();
        village.Day();

        assertAll(
                () -> assertEquals(expected, village.getWorkers().get(0).getDaysHungry()),
                () -> assertEquals(expected, village.getWorkers().get(1).getDaysHungry()),
                () -> assertEquals(expected, village.getWorkers().get(2).getDaysHungry()),
                () -> assertEquals(expected, village.getWorkers().get(3).getDaysHungry()),
                () -> assertEquals(expected, village.getWorkers().get(4).getDaysHungry()),
                () -> assertEquals(expected, village.getWorkers().get(5).getDaysHungry()),
                () -> assertTrue(village.getWorkers().get(0).isHungry()),
                () -> assertTrue(village.getWorkers().get(1).isHungry()),
                () -> assertTrue(village.getWorkers().get(2).isHungry()),
                () -> assertTrue(village.getWorkers().get(3).isHungry()),
                () -> assertTrue(village.getWorkers().get(4).isHungry()),
                () -> assertTrue(village.getWorkers().get(5).isHungry()),
                () -> assertTrue(village.getWorkers().get(0).isAlive()),
                () -> assertTrue(village.getWorkers().get(1).isAlive()),
                () -> assertTrue(village.getWorkers().get(2).isAlive()),
                () -> assertTrue(village.getWorkers().get(3).isAlive()),
                () -> assertTrue(village.getWorkers().get(4).isAlive()),
                () -> assertTrue(village.getWorkers().get(5).isAlive())
        );
    }
    /*
     * A test to continue four days when there is no food in the village with max amount of worker.
     * The method Day() is called four times.
     */
    @Test
    public void Village_ContinueToNextDayWithoutFood_FourDays(){
        Village village = createFullVillage();
        village.setFoodPerDay(0);
        village.setWoodPerDay(0);
        village.setMetalPerDay(0);
        village.setFood(0);
        int expected = 4;

        village.Day();
        village.Day();
        village.Day();
        village.Day();

        assertAll(
                () -> assertEquals(expected, village.getWorkers().get(0).getDaysHungry()),
                () -> assertEquals(expected, village.getWorkers().get(1).getDaysHungry()),
                () -> assertEquals(expected, village.getWorkers().get(2).getDaysHungry()),
                () -> assertEquals(expected, village.getWorkers().get(3).getDaysHungry()),
                () -> assertEquals(expected, village.getWorkers().get(4).getDaysHungry()),
                () -> assertEquals(expected, village.getWorkers().get(5).getDaysHungry()),
                () -> assertTrue(village.getWorkers().get(0).isHungry()),
                () -> assertTrue(village.getWorkers().get(1).isHungry()),
                () -> assertTrue(village.getWorkers().get(2).isHungry()),
                () -> assertTrue(village.getWorkers().get(3).isHungry()),
                () -> assertTrue(village.getWorkers().get(4).isHungry()),
                () -> assertTrue(village.getWorkers().get(5).isHungry()),
                () -> assertTrue(village.getWorkers().get(0).isAlive()),
                () -> assertTrue(village.getWorkers().get(1).isAlive()),
                () -> assertTrue(village.getWorkers().get(2).isAlive()),
                () -> assertTrue(village.getWorkers().get(3).isAlive()),
                () -> assertTrue(village.getWorkers().get(4).isAlive()),
                () -> assertTrue(village.getWorkers().get(5).isAlive())
        );
    }
    /*
     * A test to continue five days when there is no food in the village with max amount of worker.
     * The method Day() is called five times.
     */
    @Test
    public void Village_ContinueToNextDayWithoutFood_FiveDays(){
        Village village = createFullVillage();
        village.setFoodPerDay(0);
        village.setWoodPerDay(0);
        village.setMetalPerDay(0);
        village.setFood(0);
        int expected = 5;

        village.Day();
        village.Day();
        village.Day();
        village.Day();
        village.Day();

        assertAll(
                () -> assertEquals(expected, village.getWorkers().get(0).getDaysHungry()),
                () -> assertEquals(expected, village.getWorkers().get(1).getDaysHungry()),
                () -> assertEquals(expected, village.getWorkers().get(2).getDaysHungry()),
                () -> assertEquals(expected, village.getWorkers().get(3).getDaysHungry()),
                () -> assertEquals(expected, village.getWorkers().get(4).getDaysHungry()),
                () -> assertEquals(expected, village.getWorkers().get(5).getDaysHungry()),
                () -> assertTrue(village.getWorkers().get(0).isHungry()),
                () -> assertTrue(village.getWorkers().get(1).isHungry()),
                () -> assertTrue(village.getWorkers().get(2).isHungry()),
                () -> assertTrue(village.getWorkers().get(3).isHungry()),
                () -> assertTrue(village.getWorkers().get(4).isHungry()),
                () -> assertTrue(village.getWorkers().get(5).isHungry()),
                () -> assertFalse(village.getWorkers().get(0).isAlive()),
                () -> assertFalse(village.getWorkers().get(1).isAlive()),
                () -> assertFalse(village.getWorkers().get(2).isAlive()),
                () -> assertFalse(village.getWorkers().get(3).isAlive()),
                () -> assertFalse(village.getWorkers().get(4).isAlive()),
                () -> assertFalse(village.getWorkers().get(5).isAlive())
        );
    }
    private Village createFullVillage(){
        Building house = new Building("House");
        ArrayList<Worker> workers = new ArrayList<>();
        ArrayList<Building> buildings = new ArrayList<>();
        ArrayList<Project> projects = new ArrayList<>();
        buildings.add(house);
        buildings.add(house);
        buildings.add(house);
        Village village = new Village(false, 10, 0, 0, workers, buildings, projects, 1, 1, 5, 6, 0,5);
        village.AddWorker("Monica", "builder");
        village.AddWorker("Rachel", "lumberjack");
        village.AddWorker("Phoebe", "lumberjack");
        village.AddWorker("Chandler", "farmer");
        village.AddWorker("Ross", "miner");
        village.AddWorker("Joey", "builder");
        return village;
    }
}