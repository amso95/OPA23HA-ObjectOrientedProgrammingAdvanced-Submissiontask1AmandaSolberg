package org.example;

import org.example.objects.Building;
import org.example.objects.Project;
import org.example.objects.Worker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class VillageAddProjectTest {
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
    * A test to test if a project is added when the method AddProject() method is called.
    * The first parameter is the name of the project.
    * The second parameter is the amount of wood that is set in the village.
    * The third parameter is the amount of metal that is set in the village.
    * The fourth parameter is the expected size of the project ArrayList.
    */
    @ParameterizedTest
    @CsvSource(value = {"House,5,0,1", "House,10,5,1", "Woodmill,5,1,1", "Woodmill,7,5,1", "Quarry,3,5,1", "Quarry,5,8,1", "Farm,5,2,1", "Farm,9,7,1", "Castle,50,50,1", "Castle,65,59,1"})
    public void Village_AddProject_AddFromCsvValues_Success(String projectName, int woodAmount, int metalAmount, int size){
        village.setWood(woodAmount);
        village.setMetal(metalAmount);

        village.AddProject(projectName);

        assertAll(
                () -> assertEquals(projectName, village.getProjects().get(0).getName()),
                () -> assertEquals(size, village.getProjects().size())
        );
    }
    /*
     * A test to add a project without enough resources when the method AddProject() method is called.
     * The first parameter is the name of the project.
     * The second parameter is the amount of wood that is set in the village.
     * The third parameter is the amount of metal that is set in the village.
     * The fourth parameter is the expected size of the project ArrayList.
     */
    @ParameterizedTest
    @CsvSource(value = {"House,4,0,0", "House,2,0,0", "Woodmill,4,1,0", "Woodmill,2,0,0", "Quarry,2,5,0", "Quarry,1,2,0", "Farm,4,2,0", "Farm,2,1,0", "Castle,5,50,0", "Castle,5,20,0"})
    public void Village_AddProject_AddFromCsvValues_Fail(String projectName, int woodAmount, int metalAmount, int size){
        village.setWood(woodAmount);
        village.setMetal(metalAmount);

        village.AddProject(projectName);

        assertEquals(size, village.getProjects().size());
    }
}