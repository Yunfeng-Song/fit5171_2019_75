package rockets.model;

import com.google.common.collect.Sets;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Set;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class LaunchServiceProviderTest {

    private LaunchServiceProvider target;

    @BeforeEach
    public void setUp() {

        target = new LaunchServiceProvider("HeHe X", 1949, "China");
    }



    //Constructor validation.

    @DisplayName("should throw exception when pass a null as name to LaunchServiceProvider's constructor")
    @ParameterizedTest
    @CsvSource({
            ", 1949, hehe"
    })
    public void shouldThrowExceptionWhenSetNameToNull(String name, int yearFounded, String country){

        NullPointerException exception = assertThrows(NullPointerException.class, () -> new LaunchServiceProvider(name, yearFounded, country));
        assertEquals("name cannot be null or empty", exception.getMessage());
    }

    @DisplayName("should throw exception when pass an empty string as name to LaunchServiceProvider's constructor")
    @ParameterizedTest
    @CsvSource({
            "'', 1949, hehe",
            "' ', 1949, hehe",
            "'  ', 1949, hehe"
    })
    public void shouldThrowExceptionWhenSetNameToEmptyString(String name, int yearFounded, String country){

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> new LaunchServiceProvider(name, yearFounded, country));
        assertEquals("name cannot be null or empty", exception.getMessage());
    }

    @DisplayName("should throw exception when pass a null as country to LaunchServiceProvider's constructor")
    @ParameterizedTest
    @CsvSource({
            "hehe X, 1949, "
    })
    public void shouldThrowExceptionWhenSetCountryToNull(String name, int yearFounded, String country){

        NullPointerException exception = assertThrows(NullPointerException.class, () -> new LaunchServiceProvider(name, yearFounded, country));
        assertEquals("country cannot be null or empty", exception.getMessage());
    }

    @DisplayName("should throw exception when pass an empty string as country to LaunchServiceProvider's constructor")
    @ParameterizedTest
    @CsvSource({
            "hehe X, 1949, ''",
            "hehe X, 1949, ' '",
            "hehe X, 1949, '  '"
    })
    public void shouldThrowExceptionWhenSetCountryToEmptyString(String name, int yearFounded, String country){

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> new LaunchServiceProvider(name, yearFounded, country));
        assertEquals("country cannot be null or empty", exception.getMessage());
    }

    @DisplayName("should throw exception when pass a name which has empty space at the beginning or the end to LaunchServiceProvider's constructor")
    @ParameterizedTest
    @ValueSource(strings = {"aa ", " aa", " aa "})
    public void shouldThrowExceptionWhenTheNameContainsEmptySpaceAtTheBeginningOrEnd(String name) {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> new LaunchServiceProvider(name, 1949, "China"));
        assertEquals("There should be no empty space at the beginning or the end of a name", exception.getMessage());
    }

    @DisplayName("should throw exception when pass a country which has empty space at the beginning or the end to LaunchServiceProvider's constructor")
    @ParameterizedTest
    @ValueSource(strings = {"aa ", " aa", " aa "})
    public void shouldThrowExceptionWhenTheCountryContainsEmptySpaceAtTheBeginningOrEnd(String country) {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> new LaunchServiceProvider("hehe", 1949, country));
        assertEquals("There should be no empty space at the beginning or the end of a country", exception.getMessage());
    }



    @DisplayName("should throw exception when pass a name which length is not between 2 to 40 inclusively to the LaunchServiceProvider's constructor")
    @ParameterizedTest
    @CsvSource({
            "1, 1949, hehe",
            "12345678901234567890123456789012345678901, 1949, hehe"
    })
    public void shouldThrowExceptionWhenTheLengthOfTheNameIsInvalid(String name, int yearFounded, String country){

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> new LaunchServiceProvider(name, yearFounded, country));
        assertEquals("The length of the name must be equal or greater than 2 and equal or smaller than 40", exception.getMessage());
    }

    @DisplayName("should Not throw exception when pass a name which length is between 2 to 40 inclusively to the LaunchServiceProvider's constructor")
    @ParameterizedTest
    @CsvSource({
            "12, 1949, hehe",
            "123, 1949, hehe",
            "12345678901234567890, 1949, hehe",
            "123456789012345678901234567890123456789, 1949, hehe",
            "1234567890123456789012345678901234567890, 1949, hehe",
    })
    public void shouldNotThrowExceptionWhenTheLengthOfTheNameIsvalid(String name, int yearFounded, String country){

        assertDoesNotThrow(()-> {
            new LaunchServiceProvider(name, yearFounded, country);
        });

    }

    @DisplayName("should throw exception when pass a yearFounded which is not between 1500 to 2019 inclusively to the LaunchServiceProvider's constructor")
    @ParameterizedTest
    @CsvSource({
            "hehe X, 1499, hehe",
            "hehe X, 2020, hehe"
    })
    public void shouldThrowExceptionWhenTheYearFoundedIsInvalid(String name, int yearFounded, String country){

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> new LaunchServiceProvider(name, yearFounded, country));
        assertEquals("yearFounder must be equal or greater than 1500 and equal or smaller than current year", exception.getMessage());
    }

    @DisplayName("should Not throw exception when pass a yearFounded which is between 1500 to 2019 inclusively to the LaunchServiceProvider's constructor")
    @ParameterizedTest
    @CsvSource({
            "hehe X, 1500, hehe",
            "hehe X, 1501, hehe",
            "hehe X, 1750, hehe",
            "hehe X, 2018, hehe",
            "hehe X, 2019, hehe",

    })
    public void shouldNotThrowExceptionWhenTheYearFoundedIsvalid(String name, int yearFounded, String country){

        assertDoesNotThrow(()-> {
            new LaunchServiceProvider(name, yearFounded, country);
        });

    }

    @DisplayName("should throw exception when pass a country which length is not between 2 to 40 inclusively to the LaunchServiceProvider's constructor")
    @ParameterizedTest
    @CsvSource({
            "hehe X, 1949, a",
            "hehe X, 1949, aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
    })
    public void shouldThrowExceptionWhenTheLengthOfTheCountryIsInvalid(String name, int yearFounded, String country){

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> new LaunchServiceProvider(name, yearFounded, country));
        assertEquals("The length of the country must be equal or greater than 2 and equal or smaller than 40", exception.getMessage());
    }

    @DisplayName("should Not throw exception when pass a country which length is between 2 to 40 inclusively to the LaunchServiceProvider's constructor")
    @ParameterizedTest
    @CsvSource({
            "hehe X, 1949, aa",
            "hehe X, 1949, aaa",
            "hehe X, 1949, aaaaaaaaaaaaaaaaaaaa",
            "hehe X, 1949, aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa",
            "hehe X, 1949, aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa",

    })
    public void shouldNotThrowExceptionWhenTheLengthOfTheCountryIsvalid(String name, int yearFounded, String country){

        assertDoesNotThrow(()-> {
            new LaunchServiceProvider(name, yearFounded, country);
        });

    }

    @DisplayName("should throw exception when pass a country which includes numbers or special characters to the LaunchServiceProvider's constructor")
    @ParameterizedTest
    @CsvSource({
            "hehe X, 1949, abc@",
            "hehe X, 1949, abc1",
            "hehe X, 1949, abc@1"
    })
    public void shouldThrowExceptionWhenTheCountrytHasNumbersOrSpecialCharacters(String name, int yearFounded, String country){

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> new LaunchServiceProvider(name, yearFounded, country));
        assertEquals("country cannot have numbers or special characters", exception.getMessage());
    }

    @DisplayName("should Not throw exception when pass a country which does not includes numbers or special characters to the LaunchServiceProvider's constructor")
    @ParameterizedTest
    @CsvSource({
            "hehe X, 1949, China"
    })
    public void shouldNotThrowExceptionWhenTheCountryDoesNotHasNumbersOrSpecialCharacters(String name, int yearFounded, String country){

        assertDoesNotThrow(()-> {
            new LaunchServiceProvider(name, yearFounded, country);
        });
    }


    //Headquarters validation.

    @DisplayName("should throw exception when pass a null to setHeadquarters function")
    @Test
    public void shouldThrowExceptionWhenSetHeadquartersToNull(){

        NullPointerException exception = assertThrows(NullPointerException.class, () -> target.setHeadquarters(null));
        assertEquals("Headquarters cannot be null or empty", exception.getMessage());
    }

    @DisplayName("should throw exception when pass a empty string to setHeadquarters function")
    @ParameterizedTest
    @ValueSource(strings = {"", " ", "  "})
    public void shouldThrowExceptionWhenSetHeadquartersToEmpty(String headquarters) {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> target.setHeadquarters(headquarters));
        assertEquals("Headquarters cannot be null or empty", exception.getMessage());
    }

    @DisplayName("should throw exception when pass a headquarters which has empty space at the beginning or the end to setHeadquarters function")
    @ParameterizedTest
    @ValueSource(strings = {"aa ", " aa", " aa "})
    public void shouldThrowExceptionWhenTheHeadquartersContainsEmptySpaceAtTheBeginningOrEnd(String headquarters) {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> target.setHeadquarters(headquarters));
        assertEquals("There should be no empty space at the beginning or the end of a headquarters", exception.getMessage());
    }

    @DisplayName("should throw exception when pass a headquarters which length is not between 2 and 20 inclusively to setHeadquarters function")
    @ParameterizedTest
    @ValueSource(strings = {"a", "aaaaaaaaaaaaaaaaaaaaa" })
    public void shouldThrowExceptionWhenLengthOfTheHeadquartersIsNotVaild(String headquarters) {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> target.setHeadquarters(headquarters));
        assertEquals("The length of the headquarters must be equal or greater than 2 and equal or smaller than 20", exception.getMessage());
    }

    @DisplayName("should not throw exception when pass a headquarters which length is between 2 and 20 inclusively to setHeadquarters function")
    @ParameterizedTest
    @ValueSource(strings = {"aa", "aaa", "aaaaaaaaaa", "aaaaaaaaaaaaaaaaaaa", "aaaaaaaaaaaaaaaaaaaa"})
    public void shouldNotThrowExceptionWhenLengthOfTheHeadquartersIsVaild(String headquarters) {

        assertDoesNotThrow(()-> {
            target.setHeadquarters(headquarters);
        });
    }

    //We assume that first name can not contain special characters or numbers.
    @DisplayName("should throw exception when pass a headquarters which contains special characters or numbers to setHeadquarters function")
    @ParameterizedTest
    @ValueSource(strings = {"aaaa aa!", "aaaaaa1", "aaaaaa1!"})
    public void shouldThrowExceptionWhenHeadquartersContainsSpecialCharactersOrNumbers(String headquarters) {

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> target.setHeadquarters(headquarters));
        assertEquals("Headquarters must not contain special characters or numbers", exception.getMessage());

    }

    //Rockets validation.

    @DisplayName("should throw exception when pass a null to setRockets function")
    @Test
    public void shouldThrowExceptionWhenSetRocketsToNull(){

        NullPointerException exception = assertThrows(NullPointerException.class, () -> target.setRockets(null));
        assertEquals("Rockets cannot be null", exception.getMessage());
    }

    @DisplayName("should throw exception when pass rockets which have null in it to setRockets function")
    @ParameterizedTest
    @MethodSource("NullObjectSetProvider")
    public void shouldThrowExceptionWhenRocketsHaveNullInIt(Set<Rocket> rockets){

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> target.setRockets(rockets));
        assertEquals("Every rocket cannot be null", exception.getMessage());
    }

    private static Stream<Arguments> NullObjectSetProvider() {

        Rocket r1 = null;
        Set<Rocket> r = Sets.newLinkedHashSet();
        r.add(r1);

        return Stream.of(Arguments.of(r));
    }

    @DisplayName("should throw exception when pass rockets which have rockets which country is not the same as the provider in it to setRockets function")
    @ParameterizedTest
    @MethodSource("InvalidCountryRocketSetProvider")
    public void shouldThrowExceptionWhenRocketsHaveDiffferentCountryFromTheProvider(Set<Rocket> rockets){

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> target.setRockets(rockets));
        assertEquals("Every rocket must share the same country with the service provider", exception.getMessage());
    }

    private static Stream<Arguments> InvalidCountryRocketSetProvider() {

        LaunchServiceProvider l2 = new LaunchServiceProvider("hehe X", 1949, "China");
        LaunchServiceProvider l1 = new LaunchServiceProvider("hehe X", 1949, "Australia");
        Rocket r1 = new Rocket("Xcode", "Australia", l1);
        Rocket r2 = new Rocket("Xcode", "China", l2);
        Set<Rocket> r = Sets.newLinkedHashSet();
        r.add(r1);
        r.add(r2);

        return Stream.of(Arguments.of(r));
    }

    @DisplayName("should not throw exception when pass rockets which have rockets which country is the same as the provider in it to setRockets function")
    @ParameterizedTest
    @MethodSource("ValidCountryRocketSetProvider")
    public void shouldNotThrowExceptionWhenRocketsHaveTheSameCountryWithTheProvider(Set<Rocket> rockets){

        assertDoesNotThrow(()-> {
            target.setRockets(rockets);
        });

    }

    private static Stream<Arguments> ValidCountryRocketSetProvider() {

        LaunchServiceProvider l = new LaunchServiceProvider("hehe X", 1949, "China");
        Rocket r2 = new Rocket("Xcode", "China", l);
        Rocket r3 = new Rocket("Apple", "China", l);
        Set<Rocket> r = Sets.newLinkedHashSet();
        r.add(r2);
        Set<Rocket> rr = Sets.newLinkedHashSet();
        rr.add(r2);
        rr.add(r3);


        return Stream.of(Arguments.of(r), Arguments.of(rr));
    }


    //equals function validation.

    @DisplayName("should return true when two objects are the same")
    @Test
    public void shouldReturnTrueWhenTwoObjectsAreTheSame() {

        assertTrue(target.equals(target));
    }

    @DisplayName("should return false when pass a null to the equals function")
    @Test
    public void shouldReturnFalseWhenPassANull() {

        assertFalse(target.equals(null));
    }

    @DisplayName("should return false when pass a object which is not share the same class with the target to the equals function")
    @ParameterizedTest
    @MethodSource("InvalidObjectProvider")
    public void shouldReturnFalseWhenPassAObjectOfDifferentClass(Object o) {

        assertFalse(target.equals(o));
    }

    private static Stream<Arguments> InvalidObjectProvider() {

        User u = new User();

        return Stream.of(Arguments.of(u));
    }

    @DisplayName("should return false when pass a different launch service provider to the equals function")
    @ParameterizedTest
    @MethodSource("DifferentObjectProvider")
    public void shouldReturnFalseWhenPassADifferentRocket(Object o) {

        assertFalse(target.equals(o));
    }

    private static Stream<Arguments> DifferentObjectProvider() {

        LaunchServiceProvider l1 = new LaunchServiceProvider("HeHe XX", 1949, "China");
        LaunchServiceProvider l2 = new LaunchServiceProvider("HeHe X", 2019, "China");
        LaunchServiceProvider l3 = new LaunchServiceProvider("HeHe X", 1949, "Australia");
        LaunchServiceProvider l4 = new LaunchServiceProvider("HeHe X", 2019, "Australia");
        LaunchServiceProvider l5 = new LaunchServiceProvider("HeHe XX", 1949, "Australia");
        LaunchServiceProvider l6 = new LaunchServiceProvider("HeHe XX", 2019, "China");
        LaunchServiceProvider l7 = new LaunchServiceProvider("HeHe XX", 2019, "Australia");

        return Stream.of(Arguments.of(l1), Arguments.of(l2), Arguments.of(l3), Arguments.of(l4), Arguments.of(l5), Arguments.of(l6), Arguments.of(l7));
    }

    @DisplayName("should return true when pass a launch service provider with the same name, country and manufacturer to the equals function")
    @ParameterizedTest
    @MethodSource("SameObjectProvider")
    public void shouldReturnTrueWhenPassARocketWithTheSameNameCountryAndManufacturer(Object o) {

        assertTrue(target.equals(o));
    }

    private static Stream<Arguments> SameObjectProvider() {

        LaunchServiceProvider l1 = new LaunchServiceProvider("HeHe X", 1949, "China");

        return Stream.of(Arguments.of(l1));
    }

}