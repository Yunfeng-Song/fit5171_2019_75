package rockets.mining;

import com.google.common.collect.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rockets.dataaccess.DAO;
import rockets.dataaccess.neo4j.Neo4jDAO;
import rockets.model.Launch;
import rockets.model.LaunchServiceProvider;
import rockets.model.Rocket;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class RocketMinerUnitTest {
    Logger logger = LoggerFactory.getLogger(RocketMinerUnitTest.class);

    private DAO dao;
    private RocketMiner miner;
    private List<Rocket> rockets;
    private List<LaunchServiceProvider> lsps;
    private List<Launch> launches;

    @BeforeEach
    public void setUp() {
        dao = mock(Neo4jDAO.class);
        miner = new RocketMiner(dao);
        rockets = Lists.newArrayList();

        lsps = Arrays.asList(
                new LaunchServiceProvider("ULA", 1990, "USA"),
                new LaunchServiceProvider("SpaceX", 2002, "USA"),
                new LaunchServiceProvider("ESA", 1975, "Europe")
        );

        Launch.LaunchOutcome[] launchOutcomes = new Launch.LaunchOutcome[]{Launch.LaunchOutcome.SUCCESSFUL, Launch.LaunchOutcome.FAILED, null};



        // index of lsp of each rocket
        int[] lspIndex = new int[]{0, 0, 0, 1, 1};

        // index of lsp of each launch
        int[] lspLaunchIndex = new int[]{0, 0, 0, 1, 2, 1, 0, 0, 0, 0};

        // 5 rockets
        for (int i = 0; i < 5; i++) {
            rockets.add(new Rocket("rocket_" + i, "USA", lsps.get(lspIndex[i])));
        }

        // month of each launch
        int[] months = new int[]{1, 6, 4, 3, 4, 11, 6, 5, 12, 5};

        // index of rocket of each launch
        int[] rocketIndex = new int[]{0, 0, 0, 0, 1, 1, 1, 2, 2, 3};

        // index of launch outcome of each launch.
        int[] launchOutcomeIndex = new int[]{0, 1, 0, 1, 0, 1, 0, 1, 2, 1};

        // 10 prices
        BigDecimal[] prices = new BigDecimal[]{null, null, new BigDecimal(300), new BigDecimal(4000), new BigDecimal(500), null, null, null, null, new BigDecimal(1000)};

        // 10 launches
        launches = IntStream.range(0, 10).mapToObj(i -> {
            logger.info("create " + i + " launch in month: " + months[i]);
            Launch l = new Launch();
            l.setLaunchDate(LocalDate.of(2017, months[i], 1));
            l.setLaunchVehicle(rockets.get(rocketIndex[i]));
            l.setLaunchSite("VAFB");
            l.setOrbit("LEO");
            l.setLaunchOutcome(launchOutcomes[launchOutcomeIndex[i]]);
            l.setPrice(prices[i]);
            l.setLaunchServiceProvider(lsps.get(lspLaunchIndex[i]));
            spy(l);
            return l;
        }).collect(Collectors.toList());
    }


    //mostLaunchedRockets validation.
    @DisplayName("should throw exception when k is smaller or equal to 0")
    @ParameterizedTest
    @ValueSource(ints = {-2, -1, 0})
    public void shouldThrowExceptionWhenKisSmallerOrEqualTo0ForMostLaunchedR(int k){

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> miner.mostLaunchedRockets(k));
        assertEquals("k must be greater than 0.", exception.getMessage());

    }

    @DisplayName("should not throw exception when k is greater than 0")
    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3})
    public void shouldNotThrowExceptionWhenKisGreaterThan0ForMostLaunchedR(int k){

        when(dao.loadAll(Launch.class)).thenReturn(launches);

        assertDoesNotThrow(()-> {
            miner.mostLaunchedRockets(k);
        });
        verify(dao,times(1)).loadAll(Launch.class);

    }

    @DisplayName("should not throw exception when using most launched rockets function(C1 Metric)")
    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3, 10})
    public void shouldNotThrowExceptionWhenUsingTopMostLaunchedRockets(int k){

        when(dao.loadAll(Launch.class)).thenReturn(launches);

        assertDoesNotThrow(()-> {
            miner.mostLaunchedRockets(k);
        });
        verify(dao,times(1)).loadAll(Launch.class);
    }

    @DisplayName("should return true when using most launched rockets function")
    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3, 10})
    public void shouldReturnTopMostLaunchedRockets(int k){

        when(dao.loadAll(Launch.class)).thenReturn(launches);
        List<Rocket> rocketsResult = miner.mostLaunchedRockets(k);

        verify(dao,times(1)).loadAll(Launch.class);
        //assertNotNull(rocketsResult);

        switch(k){
            case 1: assertEquals(k, rocketsResult.size()); break;
            case 2: assertEquals(k, rocketsResult.size()); break;
            case 3: assertEquals(k, rocketsResult.size()); break;
            default: assertEquals(4, rocketsResult.size()); break;
        }

        switch (k){
            case 1: assertEquals(rocketsResult, rockets.subList(0,k)); break;
            case 2: assertEquals(rocketsResult, rockets.subList(0,k)); break;
            case 3: assertEquals(rocketsResult, rockets.subList(0,k)); break;
            default: assertEquals(rocketsResult, rockets.subList(0, 4)); break;
        }
    }


    //mostReliableLaunchServiceProviders validation.
    @DisplayName("should throw exception when k is smaller or equal to 0")
    @ParameterizedTest
    @ValueSource(ints = {-2, -1, 0})
    public void shouldThrowExceptionWhenKisSmallerOrEqualTo0ForMostReliableLSP(int k){

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> miner.mostReliableLaunchServiceProviders(k));
        assertEquals("k must be greater than 0.", exception.getMessage());

    }

    @DisplayName("should not throw exception when k is greater than 0")
    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3})
    public void shouldNotThrowExceptionWhenKisGreaterThan0ForMostReliableLSP(int k){

        when(dao.loadAll(Launch.class)).thenReturn(launches);

        assertDoesNotThrow(()-> {
            miner.mostReliableLaunchServiceProviders(k);
        });
        verify(dao,times(1)).loadAll(Launch.class);

    }

    @DisplayName("should not throw exception when using most reliable launch service provider function(C1 Metric)")
    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3, 20})
    public void shouldNotThrowExceptionWhenUsingMostReliableLaunchServiceProviders(int k){

        when(dao.loadAll(Launch.class)).thenReturn(launches);

        assertDoesNotThrow(()-> {
            miner.mostReliableLaunchServiceProviders(k);
        });
        verify(dao,times(1)).loadAll(Launch.class);


    }

    @DisplayName("should return true when using most reliable launch service provider function")
    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3, 10})
    public void shouldReturnTop(int k){

        when(dao.loadAll(Launch.class)).thenReturn(launches);
        List<LaunchServiceProvider> lsp = miner.mostReliableLaunchServiceProviders(k);

        verify(dao,times(1)).loadAll(Launch.class);
        //assertNotNull(lsp);

        if(k > lsp.size()){

            k = lsp.size();
        }

        assertEquals(k, lsp.size());

        List<LaunchServiceProvider> lspActual = new ArrayList<>();

        switch(k){
            case 1: assertEquals(k, lsp.size()); break;
            case 2: assertEquals(k, lsp.size()); break;
            default: assertEquals(3, lsp.size()); break;
        }

        switch (k){
            case 1: {
                lspActual.add(lsps.get(1));
                assertEquals(lsp, lspActual);
                break;
            }
            case 2: {

                lspActual.add(lsps.get(1));
                lspActual.add(lsps.get(0));
                assertEquals(lsp, lspActual);
                break;

            }
            default:{
                lspActual.add(lsps.get(1));
                lspActual.add(lsps.get(0));
                lspActual.add(lsps.get(2));
                assertEquals(lsp, lspActual);
                break;
            }
        }
    }

    //mostRecentLaunches validation.
    @DisplayName("should throw exception when k is smaller or equal to 0")
    @ParameterizedTest
    @ValueSource(ints = {-2, -1, 0})
    public void shouldThrowExceptionWhenKisSmallerOrEqualTo0ForMostRecentLaunches(int k){

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> miner.mostRecentLaunches(k));
        assertEquals("k must be greater than 0.", exception.getMessage());

    }

    @DisplayName("should not throw exception when k is greater than 0")
    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3})
    public void shouldNotThrowExceptionWhenKisGreaterThan0ForMostRecentLaunches(int k){

        when(dao.loadAll(Launch.class)).thenReturn(launches);

        assertDoesNotThrow(()-> {
            miner.mostRecentLaunches(k);
        });
        verify(dao,times(1)).loadAll(Launch.class);
    }

    @DisplayName("should not throw exception when using most recent launches function(C1 Metric)")
    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3, 20})
    public void shouldNotThrowExceptionWhenUsingMostRecentLaunches(int k){

        when(dao.loadAll(Launch.class)).thenReturn(launches);

        assertDoesNotThrow(()-> {
            miner.mostRecentLaunches(k);
        });
        verify(dao,times(1)).loadAll(Launch.class);
    }

    @DisplayName("should return true when using most recent launches function")
    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3, 20})
    public void shouldReturnTopMostRecentLaunches(int k) {
        when(dao.loadAll(Launch.class)).thenReturn(launches);

        List<Launch> sortedLaunches = new ArrayList<>(launches);
        sortedLaunches.sort((a, b) -> -a.getLaunchDate().compareTo(b.getLaunchDate()));
        List<Launch> loadedLaunches = miner.mostRecentLaunches(k);

        verify(dao,times(1)).loadAll(Launch.class);
        //assertNotNull(loadedLaunches);

        if(k > 10){

            assertEquals(10, loadedLaunches.size());
            assertEquals(sortedLaunches, loadedLaunches);
        }
        else{

            assertEquals(k, loadedLaunches.size());
            assertEquals(sortedLaunches.subList(0, k), loadedLaunches);
        }

    }

    //dominantCountry validation.
    @DisplayName("should throw exception when pass a null to dominantCountry function")
    @Test
    public void shouldThrowExceptionWhenSetOrbitToNull(){

        NullPointerException exception = assertThrows(NullPointerException.class, () -> miner.dominantCountry(null));
        assertEquals("orbit cannot be null or empty.", exception.getMessage());
    }

    @DisplayName("should throw exception when pass a empty orbit to dominantCountry function")
    @ParameterizedTest
    @ValueSource(strings = {"", " ", "  "})
    public void shouldThrowExceptionWhenSetOrbitToEmpty(String orbit) {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> miner.dominantCountry(orbit));
        assertEquals("orbit cannot be null or empty.", exception.getMessage());
    }

    @DisplayName("should throw exception when pass a orbit which has empty space at the beginning or the end to dominantCountry function")
    @ParameterizedTest
    @ValueSource(strings = {"a ", " a", " a "})
    public void shouldThrowExceptionWhenTheOrbitContainsEmptySpaceAtTheBeginningOrEnd(String orbit) {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> miner.dominantCountry(orbit));
        assertEquals("There should be no empty space at the beginning or the end of a orbit.", exception.getMessage());
    }

    @DisplayName("should throw exception when pass a orbit which length is not between 2 and 10 inclusively to dominantCountry function")
    @ParameterizedTest
    @ValueSource(strings = {"a", "aaaaaaaaaaaaaaaaaaaaa" })
    public void shouldThrowExceptionWhenLengthOfTheOrbitIsInvaild(String orbit) {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> miner.dominantCountry(orbit));
        assertEquals("The length of the orbit must be equal or greater than 2 and equal or smaller than 10.", exception.getMessage());
    }

    @DisplayName("should not throw exception when pass a orbit which length is between 2 and 10 inclusively to dominantCountry function")
    @ParameterizedTest
    @ValueSource(strings = {"LEO", "GTO", "Other"})
    public void shouldNotThrowExceptionWhenLengthOfTheOrbitIsVaild(String orbit) {

        when(dao.loadAll(Launch.class)).thenReturn(launches);

        assertDoesNotThrow(()-> {
            miner.dominantCountry(orbit);
        });
        verify(dao,times(1)).loadAll(Launch.class);
    }

    @DisplayName("should throw exception when pass a orbit which is not 'GTO', 'LEO' or 'Other' to dominantCountry function")
    @ParameterizedTest
    @ValueSource(strings = {"abcd", "xixi", "asasasas"})
    public void shouldThrowExceptionWhenOrbitIsInvalid(String orbit) {

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> miner.dominantCountry(orbit));
        assertEquals("The orbit must be 'GTO', 'LEO' or 'Other'.", exception.getMessage());

    }

    @DisplayName("should not throw exception when pass a orbit which is 'GTO', 'LEO' or 'Other' to dominantCountry function")
    @ParameterizedTest
    @ValueSource(strings = {"LEO", "GTO", "Other"})
    public void shouldNotThrowExceptionWhenOrbitIsVaild(String orbit) {

        when(dao.loadAll(Launch.class)).thenReturn(launches);

        assertDoesNotThrow(()-> {
            miner.dominantCountry(orbit);
        });
        verify(dao,times(1)).loadAll(Launch.class);
    }

    @DisplayName("should not throw exception when using dominantCountry function(C1 Metric)")
    @ParameterizedTest
    @ValueSource(strings = {"LEO", "GTO", "Other"})
    public void shouldNotThrowExceptionWhenUsingdominantCountryFunction(String orbit) {

        when(dao.loadAll(Launch.class)).thenReturn(launches);

        assertDoesNotThrow(()-> {
            miner.dominantCountry(orbit);
        });
        verify(dao,times(1)).loadAll(Launch.class);
    }

    @DisplayName("should return true when using dominantCountry function")
    @ParameterizedTest
    @ValueSource(strings = {"LEO", "GTO", "Other"})
    public void shouldReturnTheDominantCountry(String orbit){

        when(dao.loadAll(Launch.class)).thenReturn(launches);
        String country = miner.dominantCountry(orbit);

        verify(dao,times(1)).loadAll(Launch.class);

        switch (orbit){
            case "LEO": assertEquals(country, "USA"); break;
            case "GTO": assertEquals(country, "Cannot find any country."); break;
            case "Other": assertEquals(country, "Cannot find any country."); break;
        }
    }

    //mostExpensiveLaunches validation.
    @DisplayName("should throw exception when k is smaller or equal to 0")
    @ParameterizedTest
    @ValueSource(ints = {-2, -1, 0})
    public void shouldThrowExceptionWhenKisSmallerOrEqualTo0ForMostExpensiveL(int k){

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> miner.mostExpensiveLaunches(k));
        assertEquals("k must be greater than 0.", exception.getMessage());

    }

    @DisplayName("should not throw exception when k is greater than 0")
    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3})
    public void shouldNotThrowExceptionWhenKisGreaterThan0ForMostMostExpensiveL(int k){

        when(dao.loadAll(Launch.class)).thenReturn(launches);

        assertDoesNotThrow(()-> {
            miner.mostExpensiveLaunches(k);
        });
        verify(dao,times(1)).loadAll(Launch.class);

    }

    @DisplayName("should not throw exception when using most expensive launches function(C1 Metric)")
    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3, 10})
    public void shouldNotThrowExceptionWhenUsingMostExpensiveL(int k){

        when(dao.loadAll(Launch.class)).thenReturn(launches);

        assertDoesNotThrow(()-> {
            miner.mostLaunchedRockets(k);
        });
        verify(dao,times(1)).loadAll(Launch.class);
    }

    @DisplayName("should return true when using most expensive launches function")
    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3, 20})
    public void shouldReturnTrueWhenUsingMostExpensiveLaunches(int k){

        when(dao.loadAll(Launch.class)).thenReturn(launches);
        List<Launch> launchList = miner.mostExpensiveLaunches(k);

        verify(dao,times(1)).loadAll(Launch.class);

        if(launchList != null){

            if(k > launchList.size()){

                k = launchList.size();
            }
            assertEquals(launchList.size(), k);
        }

        List<Launch> launch = new ArrayList<>();
        switch (k){
            case 1: {
                launch.add(launches.get(3));
                assertEquals(launchList, launch);
                break;
            }
            case 2:{
                launch.add(launches.get(3));
                launch.add(launches.get(9));
                assertEquals(launchList, launch);
                break;
            }
            case 3:{
                launch.add(launches.get(3));
                launch.add(launches.get(9));
                launch.add(launches.get(4));
                assertEquals(launchList, launch);
                break;
            }
            default:{
                launch.add(launches.get(3));
                launch.add(launches.get(9));
                launch.add(launches.get(4));
                launch.add(launches.get(2));
                assertEquals(launchList, launch);
                break;
            }
        }
    }

    //highestRevenueLaunchServiceProviders validation.
    @DisplayName("should throw exception when k is smaller or equal to 0")
    @ParameterizedTest
    @ValueSource(ints = {-2, -1, 0})
    public void shouldThrowExceptionWhenKisSmallerOrEqualTo0ForHighestRevenueLSP(int k){

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> miner.highestRevenueLaunchServiceProviders(k, 2019));
        assertEquals("k must be greater than 0.", exception.getMessage());

    }

    @DisplayName("should not throw exception when k is greater than 0 for highest revenue launch service provider function")
    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3})
    public void shouldNotThrowExceptionWhenKisGreaterThan0FoHighestRevenueLSP(int k){

        when(dao.loadAll(Launch.class)).thenReturn(launches);

        assertDoesNotThrow(()-> {
            miner.highestRevenueLaunchServiceProviders(k,2019);
        });
        verify(dao,times(1)).loadAll(Launch.class);

    }

    @DisplayName("should throw exception when year is not smaller or equal to current year and bigger or equal to 1500")
    @ParameterizedTest
    @ValueSource(ints = {1498, 1499, 2020, 2021})
    public void shouldThrowExceptionWhenYearisInvalidForHighestRevenueLSP(int year){

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> miner.highestRevenueLaunchServiceProviders(1, year));
        assertEquals("year must be greater than 1500.", exception.getMessage());

    }

    @DisplayName("should not throw exception when year is between 1500 and current year inclusively.")
    @ParameterizedTest
    @ValueSource(ints = {1500, 1501, 2000, 2018, 2019})
    public void shouldNotThrowExceptionWhenYearIsVaildForHighestRevenueLSP(int year){

        when(dao.loadAll(Launch.class)).thenReturn(launches);

        assertDoesNotThrow(()-> {
            miner.highestRevenueLaunchServiceProviders(1, year);
        });
        verify(dao,times(1)).loadAll(Launch.class);

    }

    @DisplayName("should not throw exception when using highest revenue launch service provider function(C1 Metric)")
    @ParameterizedTest
    @CsvSource({
            "1, 2017",
            "2, 2017",
            "10, 2017",
            "1, 2019"
    })
    public void shouldNotThrowExceptionWhenUsingHighestRevenueLSP(int k, int year){

        when(dao.loadAll(Launch.class)).thenReturn(launches);

        assertDoesNotThrow(()-> {
            miner.highestRevenueLaunchServiceProviders(k, year);
        });
        verify(dao,times(1)).loadAll(Launch.class);
    }

    @DisplayName("should return true when using highest revenue launch service provider function")
    @ParameterizedTest
    @CsvSource({
            "1, 2017",
            "2, 2017",
            "10, 2017",
            "1, 2019"
    })
    public void shouldReturnHighestRevenueLSP(int k, int year){

        when(dao.loadAll(Launch.class)).thenReturn(launches);
        List<LaunchServiceProvider> lspResult = miner.highestRevenueLaunchServiceProviders(k, year);

        verify(dao,times(1)).loadAll(Launch.class);
        //assertNotNull(lspResult);

        if(lspResult != null){

            if(lspResult.size() < k){
                k = lspResult.size();
            }
            assertEquals(k, lspResult.size());
        }

        List<LaunchServiceProvider> lspActual = new ArrayList<>();

        switch (year){
            case 2017: {

                switch (k){
                    case 1:{
                        lspActual.add(lsps.get(2));
                        assertEquals(lspResult, lspActual);
                        break;
                    }
                    case 2:{
                        lspActual.add(lsps.get(2));
                        lspActual.add(lsps.get(0));
                        assertEquals(lspResult, lspActual);
                        break;
                    }
                    default:{
                        lspActual.add(lsps.get(2));
                        lspActual.add(lsps.get(0));
                        lspActual.add(lsps.get(1));
                        assertEquals(lspResult, lspActual);
                        break;
                    }

                }
                break;

            }
            default: assertEquals(lspResult, null); break;

        }
    }
}