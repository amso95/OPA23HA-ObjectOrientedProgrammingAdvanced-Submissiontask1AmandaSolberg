package org.example;

import org.example.objects.Building;
import org.example.objects.Project;
import org.example.objects.Worker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DatabaseConnectionTest {

    DatabaseConnection databaseConnection;
    @BeforeEach
    public void setup(){
        this.databaseConnection = mock(DatabaseConnection.class);
    }
    /*
     * A test to test if the expected town names are returned when GetTownName() method is called.
     */
    @Test
    public void DatabaseConnection_GetTownNames_Success(){
        ArrayList<String> list = new ArrayList<>();
        list.add("These");
        list.add("are");
        list.add("placeholders");
        list.add("to");
        list.add("make");
        list.add("sure");
        list.add("it");
        list.add("works");

        when(databaseConnection.GetTownNames()).thenReturn(list);

        assertAll(
                () -> assertEquals(list.get(0), databaseConnection.GetTownNames().get(0)),
                () -> assertEquals(list.get(1), databaseConnection.GetTownNames().get(1)),
                () -> assertEquals(list.get(2), databaseConnection.GetTownNames().get(2)),
                () -> assertEquals(list.get(3), databaseConnection.GetTownNames().get(3)),
                () -> assertEquals(list.get(4), databaseConnection.GetTownNames().get(4)),
                () -> assertEquals(list.get(5), databaseConnection.GetTownNames().get(5)),
                () -> assertEquals(list.get(6), databaseConnection.GetTownNames().get(6)),
                () -> assertEquals(list.get(7), databaseConnection.GetTownNames().get(7))
        );
        verify(databaseConnection, times(8)).GetTownNames();
    }
    /*
     * A test to test if an internal server error occur during when GetTownName() method is called.
     */
    @Test
    public void DatabaseConnection_GetTownNames_Fail(){
        when(databaseConnection.GetTownNames()).thenReturn(null);

        assertNull(databaseConnection.GetTownNames());
        verify(databaseConnection).GetTownNames();
    }
    /*
     * A test to test if a village is loaded when the method LoadVillage() is called.
     * The first parameter is the name of the save, could be a town name.
     */
    @ParameterizedTest
    @CsvSource(value = {"These", "are", "placeholders", "make", "to", "sure", "it", "works"})
    public void DatabaseConnection_LoadVillage_Success(String loadName){
        Village village = createEmptyVillage();

        when(databaseConnection.LoadVillage(loadName)).thenReturn(village);

        assertEquals(village, databaseConnection.LoadVillage(loadName));
        verify(databaseConnection).LoadVillage(loadName);
    }
    /*
     * A test to test if an internal server error occur during LoadVillage().
     * The first parameter is the name of the load, could be a town name.
     */
    @ParameterizedTest
    @CsvSource(value = {"These", "are", "placeholders", "to", "sure", "it", "works", "1235"})
    public void DatabaseConnection_LoadVillage_Fail(String loadName){
        Village village = createEmptyVillage();

        when(databaseConnection.LoadVillage("make")).thenReturn(village);

        assertAll(
                () -> assertNotEquals(village, databaseConnection.LoadVillage(loadName)),
                () -> assertNull(databaseConnection.LoadVillage(loadName))
        );
        verify(databaseConnection, times(2)).LoadVillage(loadName);
    }
    /*
     * A test to test if a village is saved when method SaveVillage() is called.
     * The first parameter is the name of the save, could be a town name.
     */
    @ParameterizedTest
    @CsvSource(value = {"These", "are", "placeholders", "make", "to", "sure", "it", "works"})
    public void DatabaseConnection_SaveVillage_Success(String saveName){
        Village village = createEmptyVillage();

        when(databaseConnection.SaveVillage(village, saveName)).thenReturn(true);

        assertTrue(databaseConnection.SaveVillage(village, saveName));
        verify(databaseConnection).SaveVillage(village, saveName);
    }
    /*
     * A test to test if an internal server error occur during SaveVillage().
     * The first parameter is the name of the save, could be a town name.
     */
    @ParameterizedTest
    @CsvSource(value = {"These", "are", "placeholders", "make", "to", "sure", "it", "works"})
    public void DatabaseConnection_SaveVillage_Fail(String saveName){
        Village village = createEmptyVillage();

        when(databaseConnection.SaveVillage(village, saveName)).thenReturn(false);

        assertFalse(databaseConnection.SaveVillage(village, saveName));
        verify(databaseConnection).SaveVillage(village, saveName);;
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
}