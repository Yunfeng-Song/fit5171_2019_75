package rockets.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class RocketTest {

    private Rocket target;

    @BeforeEach
    public void setUp() {

        target = new Rocket("HeHe X", "China", "HeHe");
    }


    //Constructor validation.

    @DisplayName("should throw exception when pass a null as name to the Rocket's constructor")
    @ParameterizedTest
    @CsvSource({
            ", 12, 12"
    })
    public void shouldThrowExceptionWhenSetNameToNull(String name, String country, String manufacturer){

        NullPointerException exception = assertThrows(NullPointerException.class, () -> new Rocket(name, country, manufacturer));
        assertEquals("name cannot be null or empty", exception.getMessage());
    }

    @DisplayName("should throw exception when pass a null as country to the Rocket's constructor")
    @ParameterizedTest
    @CsvSource({
            "12, , 12"
    })
    public void shouldThrowExceptionWhenSetCountryToNull(String name, String country, String manufacturer){

        NullPointerException exception = assertThrows(NullPointerException.class, () -> new Rocket(name, country, manufacturer));
        assertEquals("country cannot be null or empty", exception.getMessage());
    }

    @DisplayName("should throw exception when pass a null as manufacturer to the Rocket's constructor")
    @ParameterizedTest
    @CsvSource({
            "12, 12, "
    })
    public void shouldThrowExceptionWhenSetManufacturerToNull(String name, String country, String manufacturer){

        NullPointerException exception = assertThrows(NullPointerException.class, () -> new Rocket(name, country, manufacturer));
        assertEquals("manufacturer cannot be null or empty", exception.getMessage());
    }

    @DisplayName("should throw exception when pass an empty string as name to the Rocket's constructor")
    @ParameterizedTest
    @CsvSource({
            "'', 12, 12",
            "' ', 12, 12",
            "'  ',12, 12"
    })
    public void shouldThrowExceptionWhenSetNameToEmptyString(String name, String country, String manufacturer){

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> new Rocket(name, country, manufacturer));
        assertEquals("name cannot be null or empty", exception.getMessage());
    }

    @DisplayName("should throw exception when pass an empty string as country to the Rocket's constructor")
    @ParameterizedTest
    @CsvSource({
            "12, '', 12",
            "12, ' ', 12",
            "12, '  ', 12"
    })
    public void shouldThrowExceptionWhenSetCountryToEmptyString(String name, String country, String manufacturer){

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> new Rocket(name, country, manufacturer));
        assertEquals("country cannot be null or empty", exception.getMessage());
    }

    @DisplayName("should throw exception when pass an empty string as manufacturer to the Rocket's constructor")
    @ParameterizedTest
    @CsvSource({
            "12, 12, ''",
            "12, 12, ' '",
            "12, 12, '  '"
    })
    public void shouldThrowExceptionWhenSetManufacturerToEmptyString(String name, String country, String manufacturer){

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> new Rocket(name, country, manufacturer));
        assertEquals("manufacturer cannot be null or empty", exception.getMessage());
    }

    @DisplayName("should throw exception when pass a name which has empty space at the beginning or the end to the Rocket's constructor")
    @ParameterizedTest
    @ValueSource(strings = {"aa ", " aa", " aa "})
    public void shouldThrowExceptionWhenTheNameContainsEmptySpaceAtTheBeginningOrEnd(String name) {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> new Rocket(name, "China", "hehe"));
        assertEquals("There should be no empty space at the beginning or the end of a name", exception.getMessage());
    }

    @DisplayName("should throw exception when pass a country which has empty space at the beginning or the end to the Rocket's constructor")
    @ParameterizedTest
    @ValueSource(strings = {"aa ", " aa", " aa "})
    public void shouldThrowExceptionWhenTheCountryContainsEmptySpaceAtTheBeginningOrEnd(String country) {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> new Rocket("hehe X", country, "hehe"));
        assertEquals("There should be no empty space at the beginning or the end of a country", exception.getMessage());
    }

    @DisplayName("should throw exception when pass a manufacturer which has empty space at the beginning or the end to the Rocket's constructor")
    @ParameterizedTest
    @ValueSource(strings = {"aa ", " aa", " aa "})
    public void shouldThrowExceptionWhenTheManufacturerContainsEmptySpaceAtTheBeginningOrEnd(String manufacturer) {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> new Rocket("hehe X", "China", manufacturer));
        assertEquals("There should be no empty space at the beginning or the end of a manufacturer", exception.getMessage());
    }

    @DisplayName("should throw exception when pass a name which length is not between 2 to 40 inclusively to the Rocket's constructor")
    @ParameterizedTest
    @CsvSource({
            "1, 12, 12",
            "12345678901234567890123456789012345678901, 12, 12"
    })
    public void shouldThrowExceptionWhenTheLengthOfTheNameIsInvalid(String name, String country, String manufacturer){

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> new Rocket(name, country, manufacturer));
        assertEquals("The length of the name must be equal or greater than 2 and equal or smaller than 40", exception.getMessage());
    }

    @DisplayName("should Not throw exception when pass a name which length is between 2 to 40 inclusively to the Rocket's constructor")
    @ParameterizedTest
    @CsvSource({
            "12, aa, 12",
            "123, aa, 12",
            "12345678901234567890, aa, 12",
            "123456789012345678901234567890123456789, aa, 12",
            "1234567890123456789012345678901234567890, aa, 12",
    })
    public void shouldNotThrowExceptionWhenTheLengthOfTheNameIsValid(String name, String country, String manufacturer){

        assertDoesNotThrow(()-> {
            new Rocket(name, country, manufacturer);
        });

    }

    @DisplayName("should throw exception when pass a country which length is not between 2 to 40 inclusively to the Rocket's constructor")
    @ParameterizedTest
    @CsvSource({
            "12, 1, 12",
            "12, 12345678901234567890123456789012345678901, 12"
    })
    public void shouldThrowExceptionWhenTheLengthOfTheCountryIsInvalid(String name, String country, String manufacturer){

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> new Rocket(name, country, manufacturer));
        assertEquals("The length of the country must be equal or greater than 2 and equal or smaller than 40", exception.getMessage());
    }

    @DisplayName("should Not throw exception when pass a country which length is between 2 to 40 inclusively to the Rocket's constructor")
    @ParameterizedTest
    @CsvSource({
            "12, aa, 12",
            "12, aaa, 12",
            "12, aaaaaaaaaaaaaaaaaaaa, 12",
            "12, aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa, 12",
            "12, aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa, 12",
    })
    public void shouldNotThrowExceptionWhenTheLengthOfTheCountryIsValid(String name, String country, String manufacturer){

        assertDoesNotThrow(()-> {
            new Rocket(name, country, manufacturer);
        });

    }

    @DisplayName("should throw exception when pass a manufacturer which length is not between 2 to 40 inclusively to the Rocket's constructor")
    @ParameterizedTest
    @CsvSource({
            "12, 12, 1",
            "12, 12, 12345678901234567890123456789012345678901"
    })
    public void shouldThrowExceptionWhenTheLengthOfTheManufacturerIsInvalid(String name, String country, String manufacturer){

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> new Rocket(name, country, manufacturer));
        assertEquals("The length of the manufacturer must be equal or greater than 2 and equal or smaller than 40", exception.getMessage());
    }

    @DisplayName("should Not throw exception when pass a manufacturer which length is between 2 to 40 inclusively to the Rocket's constructor")
    @ParameterizedTest
    @CsvSource({
            "12, aa, 12",
            "12, aa, 123",
            "12, aa, 12345678901234567890",
            "12, aa, 123456789012345678901234567890123456789",
            "12, aa, 1234567890123456789012345678901234567890",
    })
    public void shouldNotThrowExceptionWhenTheLengthOfTheManufacturerIsValid(String name, String country, String manufacturer){

        assertDoesNotThrow(()-> {
            new Rocket(name, country, manufacturer);
        });

    }

    @DisplayName("should throw exception when pass a country which includes numbers or special characters to the Rocket's constructor")
    @ParameterizedTest
    @CsvSource({
            "12, abc@, 12",
            "12, abc1, 12",
            "12, abc@1, 12"
    })
    public void shouldThrowExceptionWhenTheCountrytHasNumbersOrSpecialCharacters(String name, String country, String manufacturer){

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> new Rocket(name, country, manufacturer));
        assertEquals("country cannot have numbers or special characters", exception.getMessage());
    }

    @DisplayName("should Not throw exception when pass a country which does not includes numbers or special characters to the Rocket's constructor")
    @ParameterizedTest
    @CsvSource({
            "12, Australia, 12"
    })
    public void shouldNotThrowExceptionWhenTheCountryDoesNotHasNumbersOrSpecialCharacters(String name, String country, String manufacturer){

        assertDoesNotThrow(()-> {
            new Rocket(name, country, manufacturer);
        });
    }


    //massToLEO validation.

    @DisplayName("should throw exception when pass a null to setMassToLEO function")
    @Test
    public void shouldThrowExceptionWhenSetMassToLEOToNull(){

        NullPointerException exception = assertThrows(NullPointerException.class, () -> target.setMassToLEO(null));
        assertEquals("massToLEO cannot be null", exception.getMessage());
    }

    @DisplayName("should throw exception when pass a massToLEO which has empty space at the beginning or the end to setMassToLEO function")
    @ParameterizedTest
    @ValueSource(strings = {" ", "  ", " aa", "aa ", " aa "})
    public void shouldThrowExceptionWhenMassToLEOContainsEmptySpaceAtTheBeginningOrEnd(String massToLEO) {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> target.setMassToLEO(massToLEO));
        assertEquals("There should be no empty space at the beginning or the end of massToLEO", exception.getMessage());
    }

    @DisplayName("should throw exception when pass a massToLEO which length is not between 0 and 30 inclusively to setMassToLEO function")
    @ParameterizedTest
    @ValueSource(strings = {"aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" })
    public void shouldThrowExceptionWhenLengthOfTheMassToLEOIsInvaild(String massToLEO) {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> target.setMassToLEO(massToLEO));
        assertEquals("The length of the massToLEO must be equal or greater than 0 and equal or smaller than 30", exception.getMessage());
    }

    @DisplayName("should not throw exception when pass a massToLEO which length is between 0 and 30 inclusively to setMassToLEO function")
    @ParameterizedTest
    @ValueSource(strings = {"", "a", "aaaaaaaaaaaaaaa", "aaaaaaaaaaaaaaaaaaaaaaaaaaaaa", "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" })
    public void shouldNotThrowExceptionWhenLengthOfTheMassToLEOIsVaild(String massToLEO) {

        assertDoesNotThrow(()-> {
            target.setMassToLEO(massToLEO);
        });
    }


    //massToGTO validation.

    @DisplayName("should throw exception when pass a null to setMassToGTO function")
    @Test
    public void shouldThrowExceptionWhenSetMassToGTOToNull(){

        NullPointerException exception = assertThrows(NullPointerException.class, () -> target.setMassToGTO(null));
        assertEquals("massToGTO cannot be null", exception.getMessage());
    }

    @DisplayName("should throw exception when pass a massToGTO which has empty space at the beginning or the end to setMassToGTO function")
    @ParameterizedTest
    @ValueSource(strings = {" ", "  ", " aa", "aa ", " aa "})
    public void shouldThrowExceptionWhenMassToGTOContainsEmptySpaceAtTheBeginningOrEnd(String massToGTO) {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> target.setMassToGTO(massToGTO));
        assertEquals("There should be no empty space at the beginning or the end of massToGTO", exception.getMessage());
    }

    @DisplayName("should throw exception when pass a massToGTO which length is not between 0 and 30 inclusively to setMassToGTO function")
    @ParameterizedTest
    @ValueSource(strings = {"aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" })
    public void shouldThrowExceptionWhenLengthOfTheMassToGTOIsInvaild(String massToGTO) {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> target.setMassToGTO(massToGTO));
        assertEquals("The length of the massToGTO must be equal or greater than 0 and equal or smaller than 30", exception.getMessage());
    }

    @DisplayName("should not throw exception when pass a massToGTO which length is between 0 and 30 inclusively to setMassToGTO function")
    @ParameterizedTest
    @ValueSource(strings = {"", "a", "aaaaaaaaaaaaaaa", "aaaaaaaaaaaaaaaaaaaaaaaaaaaaa", "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" })
    public void shouldNotThrowExceptionWhenLengthOfTheMassToGTOIsVaild(String massToGTO) {

        assertDoesNotThrow(()-> {
            target.setMassToGTO(massToGTO);
        });

    }


    //massToOther validation.

    @DisplayName("should throw exception when pass a null to setMassToOther function")
    @Test
    public void shouldThrowExceptionWhenSetMassToOtherToNull(){

        NullPointerException exception = assertThrows(NullPointerException.class, () -> target.setMassToOther(null));
        assertEquals("massToOther cannot be null", exception.getMessage());
    }

    @DisplayName("should throw exception when pass a massToOther which has empty space at the beginning or the end to setMassToOther function")
    @ParameterizedTest
    @ValueSource(strings = {" ", "  ", " aa", "aa ", " aa "})
    public void shouldThrowExceptionWhenMassToOtherContainsEmptySpaceAtTheBeginningOrEnd(String massToOther) {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> target.setMassToOther(massToOther));
        assertEquals("There should be no empty space at the beginning or the end of massToOther", exception.getMessage());
    }

    @DisplayName("should throw exception when pass a massToOther which length is not between 0 and 30 inclusively to setMassToOther function")
    @ParameterizedTest
    @ValueSource(strings = {"aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" })
    public void shouldThrowExceptionWhenLengthOfTheMassToOtherIsInvaild(String massToOther) {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> target.setMassToOther(massToOther));
        assertEquals("The length of the massToOther must be equal or greater than 0 and equal or smaller than 30", exception.getMessage());
    }

    @DisplayName("should not throw exception when pass a massToOther which length is between 0 and 30 inclusively to setMassToOther function")
    @ParameterizedTest
    @ValueSource(strings = {"", "a", "aaaaaaaaaaaaaaa", "aaaaaaaaaaaaaaaaaaaaaaaaaaaaa", "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" })
    public void shouldNotThrowExceptionWhenLengthOfTheMassToOtherIsVaild(String massToOther) {

        assertDoesNotThrow(()-> {
            target.setMassToOther(massToOther);
        });

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

    @DisplayName("should return false when pass a different rocket to the equals function")
    @ParameterizedTest
    @MethodSource("DifferentObjectProvider")
    public void shouldReturnFalseWhenPassADifferentRocket(Object o) {

        assertFalse(target.equals(o));
    }

    private static Stream<Arguments> DifferentObjectProvider() {

        Rocket r1 = new Rocket("HeHe XX", "China", "HeHe");
        Rocket r2 = new Rocket("HeHe X", "Australia", "HeHe");
        Rocket r3 = new Rocket("HeHe X", "China", "HeHeHe");
        Rocket r4 = new Rocket("HeHe X", "Australia", "HeHeHe");
        Rocket r5 = new Rocket("HeHe XX", "China", "HeHeHe");
        Rocket r6 = new Rocket("HeHe XX", "Australia", "HeHe");
        Rocket r7 = new Rocket("HeHe XX", "Australia", "HeHeHe");

        return Stream.of(Arguments.of(r1), Arguments.of(r2), Arguments.of(r3), Arguments.of(r4), Arguments.of(r5), Arguments.of(r6), Arguments.of(r7));
    }

    @DisplayName("should return true when pass a rocket with the same name, country and manufacturer to the equals function")
    @ParameterizedTest
    @MethodSource("SameObjectProvider")
    public void shouldReturnTrueWhenPassARocketWithTheSameNameCountryAndManufacturer(Object o) {

        assertTrue(target.equals(o));
    }

    private static Stream<Arguments> SameObjectProvider() {

        Rocket r1 = new Rocket("HeHe X", "China", "HeHe");


        return Stream.of(Arguments.of(r1));
    }

}