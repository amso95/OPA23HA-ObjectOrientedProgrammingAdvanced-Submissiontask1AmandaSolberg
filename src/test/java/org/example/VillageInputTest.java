package org.example;

import org.example.objects.Building;
import org.example.objects.Project;
import org.example.objects.Worker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class VillageInputTest {

    DatabaseConnection databaseConnection;
    @BeforeEach
    public void setup(){
        this.databaseConnection = mock(DatabaseConnection.class);
    }
    /*
     * A test to test if one worker is added to the village when AddWorker() method is called from VillageInput.
     * The first parameter is the name of the worker.
     * The second parameter is the occupation of the worker.
     * The third parameter is the expected size of the ArrayList of workers after the AddWorker() method is called.
     */
    @ParameterizedTest
    @CsvSource(value = {"Monica,lumberjack,1", "Rachel,lumberjack,1", "Phoebe,builder,1", "Chandler,farmer,1", "Ross,miner,1", "Joey,builder,1"})
    public void VillageInput_AddWorker_AddFromCsvValues_Success(String name, String occupation, int size){
        Village village = createEmptyVillage();
        VillageInput villageInput = new VillageInput(village, databaseConnection);
        InputStream backup = System.in;
        String myInput;
        myInput = "1\n" + name + "\n" + occupation + "\n6";
        ByteArrayInputStream in = new ByteArrayInputStream(myInput.getBytes());
        System.setIn(in);
        villageInput.scanner = new Scanner(myInput);

        villageInput.Run();

        System.setIn(backup);
        assertAll(
                () -> assertEquals(name, villageInput.village.getWorkers().get(0).getName()),
                () -> assertEquals(occupation, villageInput.village.getWorkers().get(0).getOccupation()),
                () -> assertEquals(size, villageInput.village.getWorkers().size())
        );
    }
    /*
     * A test to add one worker when village is full, AddWorker() method is called from VillageInput.
     */
    @Test
    public void VillageInput_AddWorker_VillageIsFull_Fail(){
        Village village = createFullVillage();
        String expectedWorkerName = "Joey";
        String expectedOccupation = "builder";
        int expectedSize = 6;
        VillageInput villageInput = new VillageInput(village, databaseConnection);
        InputStream backup = System.in;
        String myInput = "1\n6";
        ByteArrayInputStream in = new ByteArrayInputStream(myInput.getBytes());
        System.setIn(in);
        villageInput.scanner = new Scanner(myInput);

        villageInput.Run();

        System.setIn(backup);
        assertAll(
                () -> assertEquals(expectedWorkerName, villageInput.village.getWorkers().get(villageInput.village.getWorkers().size() - 1).getName()),
                () -> assertEquals(expectedOccupation, villageInput.village.getWorkers().get(villageInput.village.getWorkers().size() - 1).getOccupation()),
                () -> assertEquals(expectedSize, villageInput.village.getWorkers().size()),
                () -> assertTrue(villageInput.village.isFull())
        );
    }
    /*
     * A test to add seven worker when village only can have mac six workers, AddWorker() method is called from VillageInput.
     */
    @Test
    public void VillageInput_Add7Workers_OnlySpaceFor6_Fail(){
        Village village = createEmptyVillage();
        String expectedWorkerName = "Joey";
        String expectedOccupation = "builder";
        int expectedSize = 6;
        VillageInput villageInput = new VillageInput(village, databaseConnection);
        InputStream backup = System.in;
        String myInput = "1\nMonica\nlumberjack\n1\nRachel\nlumberjack\n1\nPhoebe\nbuilder\n" +
                "1\nChandler\nfarmer\n1\nRoss\nminer\n1\nJoey\nbuilder\n1\n6";
        ByteArrayInputStream in = new ByteArrayInputStream(myInput.getBytes());
        System.setIn(in);
        villageInput.scanner = new Scanner(myInput);

        villageInput.Run();

        System.setIn(backup);
        assertAll(
                () -> assertEquals(expectedWorkerName, villageInput.village.getWorkers().get(villageInput.village.getWorkers().size() - 1).getName()),
                () -> assertEquals(expectedOccupation, villageInput.village.getWorkers().get(villageInput.village.getWorkers().size() - 1).getOccupation()),
                () -> assertEquals(expectedSize, villageInput.village.getWorkers().size()),
                () -> assertTrue(villageInput.village.isFull())
        );
    }
    /*
     * A test to add a project when enough material is in the village, AddProject() method is called from VillageInput.
     * The first parameter is the name of the project.
     * The second parameter is the amount of wood in the village.
     * The third parameter is the amount of metal in the village.
     * The fourth parameter is number of days left of the project when the project is added.
     * The fifth parameter is the expected size of the ArrayList of the projects in village.
     */
    @ParameterizedTest
    @CsvSource(value = {"House,5,0,3,1", "House,8,2,3,1", "Woodmill,5,1,5,1", "Woodmill,9,6,5,1", "Quarry,3,5,7,1", "Quarry,5,7,7,1",
                        "Farm,5,2,5,1", "Farm,8,4,5,1", "Castle,50,50,50,1", "Castle,60,55,50,1"})
    public void VillageInput_AddProject_AddFromCsvValues_Success(String projectName, int amountOfWood, int amountOfMetal, int daysLeft, int nrOfProjects){
        Village village = createEmptyVillage();
        village.setWood(amountOfWood);
        village.setMetal(amountOfMetal);
        VillageInput villageInput = new VillageInput(village, databaseConnection);
        InputStream backup = System.in;
        String myInput = "2\n" + projectName + "\n6";
        ByteArrayInputStream in = new ByteArrayInputStream(myInput.getBytes());
        System.setIn(in);
        villageInput.scanner = new Scanner(myInput);

        villageInput.Run();

        System.setIn(backup);
        assertAll(
                () -> assertEquals(projectName, villageInput.village.getProjects().get(villageInput.village.getProjects().size() - 1).getName()),
                () -> assertEquals(daysLeft, villageInput.village.getProjects().get(villageInput.village.getProjects().size() - 1).getDaysLeft()),
                () -> assertEquals(nrOfProjects, villageInput.village.getProjects().size())
        );

    }
    /*
     * A test to add a project when there is not enough material is in the village, AddProject() method is called from VillageInput.
     * The first parameter is the name of the project.
     * The second parameter is the expected size of the ArrayList of the projects in village.
     */
    @ParameterizedTest
    @CsvSource(value = {"House,0", "Woodmill,0", "Quarry,0", "Farm,0", "Castle,0"})
    public void VillageInput_AddProject_AddFromCsvValues_Fail(String projectName, int nrOfProjects){
        Village village = createEmptyVillage();
        VillageInput villageInput = new VillageInput(village, databaseConnection);
        InputStream backup = System.in;
        String myInput = "2\n" + projectName + "\n6";
        ByteArrayInputStream in = new ByteArrayInputStream(myInput.getBytes());
        System.setIn(in);
        villageInput.scanner = new Scanner(myInput);

        villageInput.Run();

        System.setIn(backup);
        assertEquals(nrOfProjects, villageInput.village.getProjects().size());

    }
    /*
     * A test to save the game when SaveGame() method is called from VillageInput.
     * The first parameter is the name the save, could be a town name.
     * The second parameter is the expected answer to make sure the game saves.
     */
    @ParameterizedTest
    @CsvSource(value = {"These,y", "are,y", "placeholders,y", "make,y", "to,y", "sure,y", "it,y", "works,y"})
    public void VillageInput_SaveGame_FromCsvValues_Success(String saveName, String saveChoice){
        ArrayList<String> list = new ArrayList<>();
        list.add("These");
        list.add("are");
        list.add("placeholders");
        list.add("to");
        list.add("make");
        list.add("sure");
        list.add("it");
        list.add("works");
        Village village = createEmptyVillage();
        VillageInput villageInput = new VillageInput(village, databaseConnection);
        when(villageInput.databaseConnection.GetTownNames()).thenReturn(list);
        when(villageInput.databaseConnection.SaveVillage(village, saveName)).thenReturn(true);
        when(villageInput.databaseConnection.LoadVillage(saveName)).thenReturn(village);
        InputStream backup = System.in;
        String myInput = "5\n" + saveName + "\n" + saveChoice + "\n6";
        ByteArrayInputStream in = new ByteArrayInputStream(myInput.getBytes());
        System.setIn(in);
        villageInput.scanner = new Scanner(myInput);

        villageInput.Run();

        System.setIn(backup);
        assertEquals(village, villageInput.databaseConnection.LoadVillage(saveName));
        verify(villageInput.databaseConnection).SaveVillage(village, saveName);
        verify(villageInput.databaseConnection).GetTownNames();
    }
    /*
     * A test to save the game but an internal server error occur during when SaveGame() method is called from VillageInput.
     * The first parameter is the name the save, could be a town name.
     * The second parameter is the expected answer to make sure the game saves.
     */
    @ParameterizedTest
    @CsvSource(value = {"These,y", "are,y", "placeholders,y", "make,y", "to,y", "sure,y", "it,y", "works,y"})
    public void VillageInput_SaveGame_FromCsvValues_Fail(String saveName, String saveChoice){
        ArrayList<String> list = new ArrayList<>();
        list.add("These");
        list.add("are");
        list.add("placeholders");
        list.add("to");
        list.add("make");
        list.add("sure");
        list.add("it");
        list.add("works");
        Village village = createEmptyVillage();
        VillageInput villageInput = new VillageInput(village, databaseConnection);
        when(villageInput.databaseConnection.GetTownNames()).thenReturn(list);
        when(villageInput.databaseConnection.SaveVillage(village, saveName)).thenReturn(false);
        when(villageInput.databaseConnection.LoadVillage(saveName)).thenReturn(null);
        InputStream backup = System.in;
        String myInput = "5\n" + saveName + "\n" + saveChoice + "\n6";
        ByteArrayInputStream in = new ByteArrayInputStream(myInput.getBytes());
        System.setIn(in);
        villageInput.scanner = new Scanner(myInput);

        villageInput.Run();

        System.setIn(backup);
        assertNull(villageInput.databaseConnection.LoadVillage(saveName));
        verify(villageInput.databaseConnection).SaveVillage(village, saveName);
        verify(villageInput.databaseConnection).GetTownNames();
    }
    /*
     * A test to load the game but an internal server error occur during when LoadGame() method is called from VillageInput.
     * The first parameter is the name the load, could be a town name.
     */
    @ParameterizedTest
    @CsvSource(value = {"These", "are", "placeholders", "make", "to", "sure", "it", "works"})
    public void VillageInput_LoadGame_FromCsvValues_Success(String saveName){
        ArrayList<String> list = new ArrayList<>();
        list.add("These");
        list.add("are");
        list.add("placeholders");
        list.add("to");
        list.add("make");
        list.add("sure");
        list.add("it");
        list.add("works");
        Village village = createEmptyVillage();
        VillageInput villageInput = new VillageInput(village, databaseConnection);
        when(villageInput.databaseConnection.GetTownNames()).thenReturn(list);
        when(villageInput.databaseConnection.LoadVillage(saveName)).thenReturn(village);
        InputStream backup = System.in;
        String myInput = "4\n" + saveName + "\n6";
        ByteArrayInputStream in = new ByteArrayInputStream(myInput.getBytes());
        System.setIn(in);
        villageInput.scanner = new Scanner(myInput);

        villageInput.Run();

        System.setIn(backup);
        assertEquals(village, villageInput.databaseConnection.LoadVillage(saveName));
        verify(villageInput.databaseConnection, times(2)).LoadVillage(saveName);
        verify(villageInput.databaseConnection).GetTownNames();
    }
    /*
     * A test to load the game when LoadGame() method is called from VillageInput.
     * The first parameter is the name the load, could be a town name.
     */
    @ParameterizedTest
    @CsvSource(value = {"These", "are", "placeholders", "make", "to", "sure", "it", "works"})
    public void VillageInput_LoadGame_FromCsvValues_Fail(String saveName){
        ArrayList<String> list = new ArrayList<>();
        list.add("These");
        list.add("are");
        list.add("placeholders");
        list.add("to");
        list.add("make");
        list.add("sure");
        list.add("it");
        list.add("works");
        Village village = createEmptyVillage();
        VillageInput villageInput = new VillageInput(village, databaseConnection);

        when(villageInput.databaseConnection.GetTownNames()).thenReturn(list);
        when(villageInput.databaseConnection.LoadVillage(saveName)).thenReturn(null);
        InputStream backup = System.in;
        String myInput = "4\n" + saveName + "\n6";
        ByteArrayInputStream in = new ByteArrayInputStream(myInput.getBytes());
        System.setIn(in);
        villageInput.scanner = new Scanner(myInput);
        villageInput.Run();

        System.setIn(backup);
        assertNull(villageInput.databaseConnection.LoadVillage(saveName));
        verify(villageInput.databaseConnection, times(2)).LoadVillage(saveName);
        verify(villageInput.databaseConnection).GetTownNames();
    }
    private Village createEmptyVillage(){
        Building house = new Building("House");
        ArrayList<Worker> workers = new ArrayList<>();
        ArrayList<Building> buildings = new ArrayList<>();
        ArrayList<Project> projects = new ArrayList<>();
        buildings.add(house);
        buildings.add(house);
        buildings.add(house);
        Village village = new Village(false, 10, 0, 0, workers, buildings, projects, 1, 1, 5, 6, 0,5);
        return village;
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
        village.AddWorker("Monica", "lumberjack");
        village.AddWorker("Rachel", "lumberjack");
        village.AddWorker("Phoebe", "builder");
        village.AddWorker("Chandler", "farmer");
        village.AddWorker("Ross", "miner");
        village.AddWorker("Joey", "builder");
        return village;
    }

}