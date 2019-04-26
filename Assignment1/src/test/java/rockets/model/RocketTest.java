package rockets.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;
import rockets.dataaccess.neo4j.Neo4jDAO;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

class RocketTest {

    private Rocket target;
    private Rocket mockTarget;
    private LaunchServiceProvider lsp;

    @BeforeEach
    public void setUp() {

        lsp = mock(LaunchServiceProvider.class);
        LaunchServiceProvider l = new LaunchServiceProvider("hehe", 1949, "China");
        target = new Rocket("HeHe X", "China", l);
        mockTarget = new Rocket("HeHe x", "China", lsp);
    }


    //Constructor validation.

    @DisplayName("should throw exception when pass a null as name to the Rocket's constructor")
    @Test
    public void shouldThrowExceptionWhenSetNameToNull(){

        NullPointerException exception = assertThrows(NullPointerException.class, () -> new Rocket(null, "China", target.getManufacturer()));
        assertEquals("name cannot be null or empty", exception.getMessage());
    }

    @DisplayName("should throw exception when pass a null as country to the Rocket's constructor")
    @Test
    public void shouldThrowExceptionWhenSetCountryToNull(){

        NullPointerException exception = assertThrows(NullPointerException.class, () -> new Rocket("HeHe X", null, target.getManufacturer()));
        assertEquals("country cannot be null or empty", exception.getMessage());
    }

    @DisplayName("should throw exception when pass a null as manufacturer to the Rocket's constructor")
    @Test
    public void shouldThrowExceptionWhenSetManufacturerToNull(){

        NullPointerException exception = assertThrows(NullPointerException.class, () -> new Rocket("HeHe X", "China", null));
        assertEquals("manufacturer cannot be null", exception.getMessage());
    }

    @DisplayName("should throw exception when pass an empty string as name to the Rocket's constructor")
    @ParameterizedTest
    @ValueSource(strings = {"", " ", "  "})
    public void shouldThrowExceptionWhenSetNameToEmptyString(String name){

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> new Rocket(name, "China", target.getManufacturer()));
        assertEquals("name cannot be null or empty", exception.getMessage());
    }

    @DisplayName("should throw exception when pass an empty string as country to the Rocket's constructor")
    @ParameterizedTest
    @ValueSource(strings = {"", " ", "  "})
    public void shouldThrowExceptionWhenSetCountryToEmptyString(String country){

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> new Rocket("HeHe X", country, target.getManufacturer()));
        assertEquals("country cannot be null or empty", exception.getMessage());
    }

//    @DisplayName("should throw exception when pass an empty string as manufacturer to the Rocket's constructor")
//    @ParameterizedTest
//    @CsvSource({
//            "12, 12, ''",
//            "12, 12, ' '",
//            "12, 12, '  '"
//    })
//    public void shouldThrowExceptionWhenSetManufacturerToEmptyString(String name, String country, String manufacturer){
//
//        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> new Rocket(name, country, manufacturer));
//        assertEquals("manufacturer cannot be null or empty", exception.getMessage());
//    }

    @DisplayName("should throw exception when pass a name which has empty space at the beginning or the end to the Rocket's constructor")
    @ParameterizedTest
    @ValueSource(strings = {"aa ", " aa", " aa "})
    public void shouldThrowExceptionWhenTheNameContainsEmptySpaceAtTheBeginningOrEnd(String name) {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> new Rocket(name, "China", target.getManufacturer()));
        assertEquals("There should be no empty space at the beginning or the end of a name", exception.getMessage());
    }

    @DisplayName("should throw exception when pass a country which has empty space at the beginning or the end to the Rocket's constructor")
    @ParameterizedTest
    @ValueSource(strings = {"aa ", " aa", " aa "})
    public void shouldThrowExceptionWhenTheCountryContainsEmptySpaceAtTheBeginningOrEnd(String country) {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> new Rocket("hehe X", country, target.getManufacturer()));
        assertEquals("There should be no empty space at the beginning or the end of a country", exception.getMessage());
    }

//    @DisplayName("should throw exception when pass a manufacturer which has empty space at the beginning or the end to the Rocket's constructor")
//    @ParameterizedTest
//    @ValueSource(strings = {"aa ", " aa", " aa "})
//    public void shouldThrowExceptionWhenTheManufacturerContainsEmptySpaceAtTheBeginningOrEnd(String manufacturer) {
//        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> new Rocket("hehe X", "China", manufacturer));
//        assertEquals("There should be no empty space at the beginning or the end of a manufacturer", exception.getMessage());
//    }

    @DisplayName("should throw exception when pass a name which length is not between 2 to 40 inclusively to the Rocket's constructor")
    @ParameterizedTest
    @ValueSource(strings = {"1", "12345678901234567890123456789012345678901"})
    public void shouldThrowExceptionWhenTheLengthOfTheNameIsInvalid(String name){

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> new Rocket(name, "China", target.getManufacturer()));
        assertEquals("The length of the name must be equal or greater than 2 and equal or smaller than 40", exception.getMessage());
    }

    @DisplayName("should Not throw exception when pass a name which length is between 2 to 40 inclusively to the Rocket's constructor")
    @ParameterizedTest
    @ValueSource(strings = {"12","213","12345678901234567890", "123456789012345678901234567890123456789", "1234567890123456789012345678901234567890"})
    public void shouldNotThrowExceptionWhenTheLengthOfTheNameIsValid(String name){

        assertDoesNotThrow(()-> {
            new Rocket(name, "China", target.getManufacturer());
        });

    }

    @DisplayName("should throw exception when pass a country which length is not between 2 to 40 inclusively to the Rocket's constructor")
    @ParameterizedTest
    @ValueSource(strings = {"1", "12345678901234567890123456789012345678901"})
    public void shouldThrowExceptionWhenTheLengthOfTheCountryIsInvalid(String country){

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> new Rocket("HeHe X", country, target.getManufacturer()));
        assertEquals("The length of the country must be equal or greater than 2 and equal or smaller than 40", exception.getMessage());
    }

    @DisplayName("should Not throw exception when pass a country which length is between 2 to 40 inclusively to the Rocket's constructor")
    @ParameterizedTest
    @MethodSource("LaunchServiceProviderObjectProvider")
    //@ValueSource(strings = {"aa", "aaa", "aaaaaaaaaaaaaaaaaaaa", "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa", "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"})
    public void shouldNotThrowExceptionWhenTheLengthOfTheCountryIsValid(String country, LaunchServiceProvider manufacture){

        assertDoesNotThrow(()-> {
            new Rocket("HeHe X", country, manufacture);
        });
    }

    private static Stream<Arguments> LaunchServiceProviderObjectProvider() {

        LaunchServiceProvider l1 = new LaunchServiceProvider("hehe", 1949, "aa");
        LaunchServiceProvider l2 = new LaunchServiceProvider("hehe", 1949, "aaa");
        LaunchServiceProvider l3 = new LaunchServiceProvider("hehe", 1949, "aaaaaaaaaaaaaaaaaaaa");
        LaunchServiceProvider l4 = new LaunchServiceProvider("hehe", 1949, "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
        LaunchServiceProvider l5 = new LaunchServiceProvider("hehe", 1949, "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");

        return Stream.of(Arguments.of("aa", l1), Arguments.of("aaa", l2), Arguments.of("aaaaaaaaaaaaaaaaaaaa", l3), Arguments.of("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa", l4), Arguments.of("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa", l5));
    }

//    @DisplayName("should throw exception when pass a manufacturer which length is not between 2 to 40 inclusively to the Rocket's constructor")
//    @ParameterizedTest
//    @ValueSource(strings = {"1", "12345678901234567890123456789012345678901"})
//    public void shouldThrowExceptionWhenTheLengthOfTheManufacturerIsInvalid(String name, String country, String manufacturer){
//
//        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> new Rocket(name, country, manufacturer));
//        assertEquals("The length of the manufacturer must be equal or greater than 2 and equal or smaller than 40", exception.getMessage());
//    }
//
//    @DisplayName("should Not throw exception when pass a manufacturer which length is between 2 to 40 inclusively to the Rocket's constructor")
//    @ParameterizedTest
//    @CsvSource({
//            "12, aa, 12",
//            "12, aa, 123",
//            "12, aa, 12345678901234567890",
//            "12, aa, 123456789012345678901234567890123456789",
//            "12, aa, 1234567890123456789012345678901234567890",
//    })
//    public void shouldNotThrowExceptionWhenTheLengthOfTheManufacturerIsValid(String name, String country, String manufacturer){
//
//        assertDoesNotThrow(()-> {
//            new Rocket(name, country, manufacturer);
//        });
//
//    }

    @DisplayName("should throw exception when pass a country which includes numbers or special characters to the Rocket's constructor")
    @ParameterizedTest
    @ValueSource(strings = {"abc@", "abc1", "abc@1"})
    public void shouldThrowExceptionWhenTheCountrytHasNumbersOrSpecialCharacters(String country){

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> new Rocket("HeHe X", country, target.getManufacturer()));
        assertEquals("country cannot have numbers or special characters", exception.getMessage());
    }

    @DisplayName("should Not throw exception when pass a country which does not includes numbers or special characters to the Rocket's constructor")
    @Test
    public void shouldNotThrowExceptionWhenTheCountryDoesNotHasNumbersOrSpecialCharacters(){

        assertDoesNotThrow(()-> {
            new Rocket("HeHe X", "China", target.getManufacturer());
        });
    }

//    @DisplayName("should throw exception when pass a country which is not the same as the country of the manufacture")
//    @Test
//    public void shouldThrowExceptionWhenTwoCountriesAreNotTheSame(){
//
//        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> new Rocket("HeHe X", "Australia", target.getManufacturer()));
//        assertEquals("The country of this rocket and the country of the manufacturer must be the same", exception.getMessage());
//    }
//
//    @DisplayName("should not throw exception when pass a country which is not the same as the country of the manufacture")
//    @Test
//    public void shouldNotThrowExceptionWhenTwoCountriesAreNotTheSame(){
//
//        assertDoesNotThrow(() -> {
//            new Rocket("HeHe X", "China", target.getManufacturer());
//    });
//
//    }


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

        LaunchServiceProvider l = new LaunchServiceProvider("hehehe", 1949, "China");
        Rocket r1 = new Rocket("HeHe XX", "China", l);
        //Rocket r2 = new Rocket("HeHe X", "Australia", l);
        Rocket r3 = new Rocket("HeHe X", "China", l);
        //Rocket r4 = new Rocket("HeHe X", "Australia", l);
        Rocket r5 = new Rocket("HeHe XX", "China", l);
        //Rocket r6 = new Rocket("HeHe XX", "Australia", l);
        //Rocket r7 = new Rocket("HeHe XX", "Australia", l);

        return Stream.of(Arguments.of(r1), Arguments.of(r3), Arguments.of(r5));
    }

    @DisplayName("should return true when pass a rocket with the same name, country and manufacturer to the equals function")
    @ParameterizedTest
    @MethodSource("SameObjectProvider")
    public void shouldReturnTrueWhenPassARocketWithTheSameNameCountryAndManufacturer(Object o) {

        assertTrue(target.equals(o));
    }

    private static Stream<Arguments> SameObjectProvider() {

        LaunchServiceProvider l = new LaunchServiceProvider("hehe", 1949, "China");
        Rocket r1 = new Rocket("HeHe X", "China", l);


        return Stream.of(Arguments.of(r1));
    }


    //getManufacturer validation
    @DisplayName("should be the same when test the getManufacturer function")
    @Test
    public void shouldBeTheSameWhenTestThegetManufacturerFunction(){

        assertEquals(lsp, mockTarget.getManufacturer());

    }

    //hashcode validation
    @DisplayName("should not throw exception when test the hashcode function")
    @Test
    public void shouldNotThrowExceptionWhenTestThehashcodeFunction(){

        assertDoesNotThrow(()-> {
            mockTarget.hashCode();
        });

    }

    //toString validation
    @DisplayName("should not throw exception when test the toString function")
    @Test
    public void shouldNotThrowExceptionWhenTestThetoStringFunction(){

        when(lsp.getName()).thenReturn("HeHe");

        assertDoesNotThrow(()-> {
            String a = mockTarget.toString();
        });
        verify(lsp,times(1)).getName();
        assertTrue(mockTarget.toString().contains("HeHe"));

    }

    //equals validation
    @DisplayName("should not throw exception when test the equals function")
    @Test
    public void shouldNotThrowExceptionWhenTestTheequalsFunction(){

        assertTrue(mockTarget.equals(mockTarget));

    }







}