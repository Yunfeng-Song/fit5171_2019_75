package rockets.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.LocalDate;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class LaunchTest {

    private Rocket mockRocket;
    private LaunchServiceProvider mockLSP;
    private  Launch launch;

    @BeforeEach
    public void setUp() {

        mockLSP = mock(LaunchServiceProvider.class);
        mockRocket = mock(Rocket.class);
        launch = new Launch();
        launch.setLaunchServiceProvider(mockLSP);
        launch.setLaunchVehicle(mockRocket);
    }

    //getLaunchVehicle validaton
    @DisplayName("should be the same when test the getLaunchVehicle function")
    @Test
    public void shouldBeTheSameWhenTestThegetLaunchVehicleFunction(){

        assertEquals(mockRocket, launch.getLaunchVehicle());

    }

    //getLaunchServiceProvider validaton
    @DisplayName("should be the same when test the getLaunchServiceProvider function")
    @Test
    public void shouldBeTheSameWhenTestThegetLaunchServiceProviderFunction(){

        assertEquals(mockLSP, launch.getLaunchServiceProvider());

    }

    //hashcode validation
    @DisplayName("should not throw exception when test the hashcode function")
    @Test
    public void shouldNotThrowExceptionWhenTestThehashcodeFunction(){

        assertDoesNotThrow(()-> {
            launch.hashCode();
        });

    }

    //equals validation
    @DisplayName("should not throw exception when test the equals function")
    @Test
    public void shouldNotThrowExceptionWhenTestTheequalsFunction(){

        assertTrue(launch.equals(launch));

    }

    @DisplayName("should return false when launch object is different")
    @ParameterizedTest
    @MethodSource("LaunchObjectProvider")
    public void shouldReturnFalseWhenLaunchObjectIsDifferent(Launch testLaunch){
        LaunchServiceProvider provider = new LaunchServiceProvider("provider", 2000, "North Korea");
        Rocket rocket = new Rocket("Haha", "North Korea", provider);

        launch.setLaunchDate(LocalDate.of(2010,10,25));
        launch.setLaunchVehicle(rocket);
        launch.setLaunchServiceProvider(provider);
        launch.setOrbit("Other");
        assertFalse(launch.equals(testLaunch));
    }

    @DisplayName("should return true when launch object is equal")
    @Test
    public void shouldReturnTrueWhenLaunchObjectIsEqual(){
        LaunchServiceProvider provider = new LaunchServiceProvider("provider", 2000, "North Korea");
        Rocket rocket = new Rocket("Haha", "North Korea", provider);

        launch.setLaunchDate(LocalDate.of(2010,10,25));
        launch.setLaunchVehicle(rocket);
        launch.setLaunchServiceProvider(provider);
        launch.setOrbit("Other");

        LaunchServiceProvider provider1 = new LaunchServiceProvider("provider", 2000, "North Korea");
        Rocket rocket1 = new Rocket("Haha", "North Korea", provider1);

        Launch testLaunch = new Launch();
        testLaunch.setLaunchDate(LocalDate.of(2010,10,25));
        testLaunch.setLaunchVehicle(rocket1);
        testLaunch.setLaunchServiceProvider(provider1);
        testLaunch.setOrbit("Other");
        assertTrue(launch.equals(testLaunch));
    }

    private static Stream<Arguments> LaunchObjectProvider() {
        LaunchServiceProvider provider1 = new LaunchServiceProvider("provider2", 2000, "Sorth Korea");
        Rocket rocket1  = new Rocket("Haha2","Sorth Korea", provider1);

        Launch launch1 = new Launch();
        launch1.setLaunchDate(LocalDate.of(2010,10,25));
        launch1.setLaunchServiceProvider(new LaunchServiceProvider("provider2",2000,"South Korea"));
        launch1.setLaunchVehicle(rocket1);
        launch1.setOrbit("Other");

        LaunchServiceProvider provider2 = new LaunchServiceProvider("provider", 2000, "North Korea");
        Rocket rocket2  = new Rocket("Haha","North Korea", provider2);

        Launch launch2 = new Launch();
        launch1.setLaunchDate(LocalDate.of(2011,10,25));
        launch1.setLaunchServiceProvider(new LaunchServiceProvider("provider2",2000,"South Korea"));
        launch1.setLaunchVehicle(rocket2);
        launch1.setOrbit("Other");

        return Stream.of(Arguments.of(launch1), Arguments.of(launch2));
    }
}