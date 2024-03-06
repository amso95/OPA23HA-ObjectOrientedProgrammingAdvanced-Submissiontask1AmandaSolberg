package org.example;

import org.example.objects.Building;
import org.example.objects.Project;
import org.example.objects.Worker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class VillageAddWorkerTest {
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
     * A test to test if one worker is added to the village when AddWorker() method is called.
     * The first parameter is the name of the worker.
     * The second parameter is the occupation of the worker.
     * The third parameter is the expected size of the ArrayList of workers after the AddWood() method is called.
     */
    @ParameterizedTest
    @CsvSource(value = {"Monica,lumberjack,1", "Rachel,lumberjack,1", "Phoebe,builder,1", "Chandler,farmer,1", "Ross,miner,1", "Joey,builder,1"})
    public void Village_AddWorker_AddFromCsvValues_Success(String name, String occupation, int size){

        village.AddWorker(name, occupation);

        assertAll(
                () -> assertEquals(name, village.getWorkers().get(0).getName()),
                () -> assertEquals(occupation, village.getWorkers().get(0).getOccupation()),
                () -> assertEquals(size, village.getWorkers().size())
        );

    }
    /*
     * A test to test if one worker is not added to the village when AddWorker() method is called because of incorrect occupation name.
     * The first parameter is the name of the worker.
     * The second parameter is the occupation of the worker.
     * The third parameter is the expected size of the ArrayList of workers after the AddWood() method is called.
     */
    @ParameterizedTest
    @CsvSource(value = {"Monica,Lumberjack,0", "Rachel,lmberjack,0", "Phoebe,buider,0", "Chandler,farer,0", "Ross,mier,0", "Joey,bulder,0"})
    public void Village_AddWorker_AddFromCsvValues_Fail(String name, String occupation, int size){

        village.AddWorker(name, occupation);

        assertEquals(size, village.getWorkers().size());
    }
    /*
     * A test to test if one worker is not added to the village when AddWorker() method is called because the village is full.
     * The first parameter is the name of the worker.
     * The second parameter is the occupation of the worker.
     * The third parameter is the expected size of the ArrayList of workers after the AddWorker() method is called.
     */
    @ParameterizedTest
    @CsvSource(value = {"Monica,lumberjack,6", "Rachel,lumberjack,6", "Phoebe,builder,6", "Chandler,farmer,6", "Ross,miner,6", "Joey,builder,6"})
    public void Village_AddWorker_AddFromCsvValues_VillageFull_Fail(String name, String occupation, int size){
        Building house = new Building("House");
        ArrayList<Worker> workers = new ArrayList<>();
        ArrayList<Building> buildings = new ArrayList<>();
        ArrayList<Project> projects = new ArrayList<>();
        buildings.add(house);
        buildings.add(house);
        buildings.add(house);
        Village village = new Village(false, 10, 0, 0, workers, buildings, projects, 1, 1, 5, 6, 0,5);
        workers.add(new Worker("Monica", "lumberjack", village.getOccupationHashMap().get("lumberjack")));
        workers.add(new Worker("Rachel", "lumberjack", village.getOccupationHashMap().get("lumberjack")));
        workers.add(new Worker("Phoebe", "builder", village.getOccupationHashMap().get("builder")));
        workers.add(new Worker("Chandler", "farmer", village.getOccupationHashMap().get("farmer")));
        workers.add(new Worker("Ross", "miner", village.getOccupationHashMap().get("miner")));
        workers.add(new Worker("Joey", "builder", village.getOccupationHashMap().get("builder")));

        village.AddWorker(name, occupation);

        assertEquals(size, village.getWorkers().size());
    }
    /*
     * A test to test if two workers is added to the village when AddWorker() method is called.
     * The first parameter is the name of the first worker.
     * The second parameter is the occupation of the first worker.
     * The third parameter is the name of the second worker.
     * The fourth parameter is the occupation of the second worker.
     * The fifth parameter is the expected size of the ArrayList of workers after the AddWood() method is called.
     */
    @ParameterizedTest
    @CsvSource(value = {"Monica,lumberjack, Rachel,lumberjack,2", "Phoebe,builder,Chandler,farmer,2", "Ross,miner,Joey,builder,2"})
    public void Village_AddWorker_Add2FromCsvValues_Success(String name1, String occupation1, String name2, String occupation2, int size){
        village.AddWorker(name1, occupation1);
        village.AddWorker(name2, occupation2);

        assertAll(
                () -> assertEquals(name1, village.getWorkers().get(0).getName()),
                () -> assertEquals(occupation1, village.getWorkers().get(0).getOccupation()),
                () -> assertEquals(name2, village.getWorkers().get(1).getName()),
                () -> assertEquals(occupation2, village.getWorkers().get(1).getOccupation()),
                () -> assertEquals(size, village.getWorkers().size())
        );

    }
    /*
     * A test to test if two workers is not added to the village when AddWorker() method is called because of incorrect occupation.
     * The first parameter is the name of the first worker.
     * The second parameter is the occupation of the first worker.
     * The third parameter is the name of the second worker.
     * The fourth parameter is the occupation of the second worker.
     * The fifth parameter is the expected size of the ArrayList of workers after the AddWood() method is called.
     */
    @ParameterizedTest
    @CsvSource(value = {"Monica,lumbrjack, Rachel,luberjack,0", "Phoebe,bulder,Chandler,famer,0", "Ross,mine,Joey,builer,0"})
    public void Village_AddWorker_Add2FromCsvValues_Fail(String name1, String occupation1, String name2, String occupation2, int size){

        village.AddWorker(name1, occupation1);
        village.AddWorker(name2, occupation2);

        assertEquals(size, village.getWorkers().size());
    }
    /*
     * A test to test if three workers is added to the village when AddWorker() method is called.
     * The first parameter is the name of the first worker.
     * The second parameter is the occupation of the first worker.
     * The third parameter is the name of the second worker.
     * The fourth parameter is the occupation of the second worker.
     * The fifth parameter is the name of the third worker.
     * The sixth parameter is the occupation of the third worker.
     * The seventh parameter is the expected size of the ArrayList of workers after the AddWood() method is called.
     */
    @ParameterizedTest
    @CsvSource(value = {"Monica,lumberjack, Rachel,lumberjack,Phoebe,builder,3", "Chandler,farmer,Ross,miner,Joey,builder,3"})
    public void Village_AddWorker_Add3FromCsvValues_Success(String name1, String occupation1, String name2, String occupation2,
                                                            String name3, String occupation3, int size){

        village.AddWorker(name1, occupation1);
        village.AddWorker(name2, occupation2);
        village.AddWorker(name3, occupation3);

        assertAll(
                () -> assertEquals(name1, village.getWorkers().get(0).getName()),
                () -> assertEquals(occupation1, village.getWorkers().get(0).getOccupation()),
                () -> assertEquals(name2, village.getWorkers().get(1).getName()),
                () -> assertEquals(occupation2, village.getWorkers().get(1).getOccupation()),
                () -> assertEquals(name3, village.getWorkers().get(2).getName()),
                () -> assertEquals(occupation3, village.getWorkers().get(2).getOccupation()),
                () -> assertEquals(size, village.getWorkers().size())
        );

    }
    /*
     * A test to test if three workers is not added to the village when AddWorker() method is called because of incorrect occupation.
     * The first parameter is the name of the first worker.
     * The second parameter is the occupation of the first worker.
     * The third parameter is the name of the second worker.
     * The fourth parameter is the occupation of the second worker.
     * The fifth parameter is the name of the third worker.
     * The sixth parameter is the occupation of the third worker.
     * The seventh parameter is the expected size of the ArrayList of workers after the AddWood() method is called.
     */
    @ParameterizedTest
    @CsvSource(value = {"Monica,umberjack, Rachel,lumberjak,Phoebe,buildr,0", "Chandler,farer,Ross,mier,Joey,buider,0"})
    public void Village_AddWorker_Add3FromCsvValues_Fail(String name1, String occupation1, String name2, String occupation2,
                                                         String name3, String occupation3, int size){

        village.AddWorker(name1, occupation1);
        village.AddWorker(name2, occupation2);
        village.AddWorker(name3, occupation3);

        assertEquals(size, village.getWorkers().size());
    }
}