package rockets.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

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

}