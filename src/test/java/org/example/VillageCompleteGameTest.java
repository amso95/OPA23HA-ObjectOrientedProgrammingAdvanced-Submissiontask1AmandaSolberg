package org.example;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class VillageCompleteGameTest {
    /*
     * A test to test the game from day 0 until finishing a castle in the village.
     * With methods Day(), AddProject() and AddWorker().
     * Comments along the when-part of the test to keep track of the values changes.
     */
    @Test
    public void Village_FromDay0ToCastleComplete(){
        Village village = new Village();
        int expectedFood = 76;
        int expectedFoodPerDay = 5;
        int expectedWood = 63;
        int expectedWoodPerDay = 3;
        int expectedMetal = 60;
        int expectedMetalPerDay = 3;
        int expectedNrOfBuildings = 11;
        int expectedNrOfMaxWorkers = 12;
        int expectedNrOfWorkers = 12;
        int expectedDaysGone = 31;

        village.AddWorker("Monica", "builder");
        village.AddWorker("Rachel", "lumberjack");
        village.AddWorker("Phoebe", "lumberjack");
        village.AddWorker("Chandler", "farmer");
        village.AddWorker("Ross", "miner");
        village.AddWorker("Joey", "farmer");
        village.Day();  //food:10+10-6=14 wood:0+2=2 metal:0+1=1
        village.Day();  //food:14+10-6=18 wood:2+2=4 metal:1+1=2
        village.Day();  //food:18+10-6=22 wood:4+2=6 metal:2+1=3
        village.AddProject( "House");    //wood:6-5=1
        village.Day();  //food:22+10-6=26 wood:1+2=3 metal:3+1=4
        village.Day();  //food:26+10-6=30 wood:3+2=5 metal:4+1=5
        village.AddProject("Woodmill");  //wood:5-5=0 metal:5-1=4
        village.Day();  //food:30+10-6=34 wood:0+2=2 metal:4+1=5
        //House complete, maxWorkers = 8
        village.AddWorker("Ben","builder");
        village.AddWorker("Emma","builder");
        village.Day();  //food:34+10-8=36 wood:2+2=4 metal:5+1=6
        village.Day();  //food:36+10-8=38 wood:4+2=6 metal:6+1=7
        //Woodmill complete, woodPerDay = 2
        village.AddProject("Quarry");   //wood:6-3=3 metal:7-5=2
        village.Day();  //food:38+10-8=40 wood:3+4=7 metal:2+1=3
        village.Day();  //food:40+10-8=42 wood:7+4=11 metal:3+1=4
        village.Day();  //food:42+10-8=44 wood:11+4=15 metal:4+2=6 <- Quarry complete, metalPerDay=2
        village.AddProject("House");    //wood:15-5=10
        village.AddProject("Woodmill"); //wood:10-5=5 metal:6-1=5
        village.Day();  //food:44+10-8=46 wood:5+4=9 metal:5+2=7
        //House complete, maxWorkers = 10
        village.AddWorker("Gunther","miner");
        village.AddWorker("Carol","builder");
        village.AddProject("Quarry");   //wood:9-3=6 metal:7-5=2
        village.Day();  //food:46+10-10=46 wood:6+4=10 metal:2+4=6 -4days
        village.Day();  //food:46+10-10=46 wood:10+6=16 metal:6+4=10 <- Woodmill complete, woodPerDay=3 -2days
        village.Day();  //food:46+10-10=46 wood:16+6=22 metal:10+4=14     -6days
        village.Day();  //food:46+10-10=46 wood:22+6=28 metal:14+6=20 <- Quarry complete, metalPerDay=3
        village.Day();  //food:46+10-10=46 wood:28+6=34 metal:20+6=26
        village.Day();  //food:46+10-10=46 wood:34+6=40 metal:26+6=32
        village.Day();  //food:46+10-10=46 wood:40+6=46 metal:32+6=38
        village.Day();  //food:46+10-10=46 wood:46+6=52 metal:38+6=44
        village.AddProject("House"); //wood:52-5=47
        village.Day();  //food:46+10-10=46 wood:47+6=53 metal:44+6=50
        // House complete, maxWorkers = 12
        village.AddWorker("Mike", "builder");
        village.AddWorker("Denise", "farmer");
        village.AddProject("Castle");   //wood:53-50=3 metal:56-50=6
        village.Day();  //food:46+15-12=49 wood:3+6=9 metal:6+6=12      -5days
        village.Day();  //food:49+15-12=52 wood:9+6=15 metal:6+6=12     -10days
        village.Day();  //food:52+15-12=55 wood:15+6=21 metal:12+6=18   -15days
        village.Day();  //food:55+15-12=58 wood:21+6=27 metal:18+6=24   -20days
        village.Day();  //food:58+15-12=61 wood:27+6=33 metal:24+6=30   -25days
        village.Day();  //food:61+15-12=64 wood:33+6=39 metal:30+6=36   -30days
        village.Day();  //food:64+15-12=67 wood:39+6=45 metal:36+6=42   -35days
        village.Day();  //food:67+15-12=70 wood:45+6=51 metal:42+6=48   -40days
        village.Day();  //food:70+15-12=73 wood:51+6=57 metal:48+6=54   -45days
        village.Day();  //food:73+15-12=76 wood:57+6=63 metal:54+6=60   -50days

        assertAll(
                () -> assertEquals(expectedFood, village.getFood()),
                () -> assertEquals(expectedFoodPerDay, village.getFoodPerDay()),
                () -> assertEquals(expectedWood, village.getWood()),
                () -> assertEquals(expectedWoodPerDay, village.getWoodPerDay()),
                () -> assertEquals(expectedMetal, village.getMetal()),
                () -> assertEquals(expectedMetalPerDay, village.getMetalPerDay()),
                () -> assertEquals(expectedNrOfBuildings, village.getBuildings().size()),
                () -> assertEquals(expectedNrOfMaxWorkers, village.getMaxWorkers()),
                () -> assertEquals(expectedNrOfWorkers, village.getWorkers().size()),
                () -> assertEquals(expectedDaysGone, village.getDaysGone()),
                () -> assertTrue(village.isGameOver())
        );
    }

}