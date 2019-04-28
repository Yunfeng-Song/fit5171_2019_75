package rockets.model;

import com.google.common.collect.Sets;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Set;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class UserUnitTest {
    private User target;

    @BeforeEach
    public void setUp() {

        target = new User();
    }

    //First name validation.

    @DisplayName("should throw exception when pass a null to setFirstName function")
    @Test
    public void shouldThrowExceptionWhenSetFirstNameToNull(){

        NullPointerException exception = assertThrows(NullPointerException.class, () -> target.setFirstName(null));
        assertEquals("First name cannot be null or empty", exception.getMessage());
    }

    @DisplayName("should throw exception when pass a empty first name to setFirstName function")
    @ParameterizedTest
    @ValueSource(strings = {"", " ", "  "})
    public void shouldThrowExceptionWhenSetFirstNameToEmpty(String firstName) {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> target.setFirstName(firstName));
        assertEquals("First name cannot be null or empty", exception.getMessage());
    }

    @DisplayName("should throw exception when pass a first name which has empty space at the beginning or the end to setFirstName function")
    @ParameterizedTest
    @ValueSource(strings = {"a ", " a", " a "})
    public void shouldThrowExceptionWhenTheFirstNameContainsEmptySpaceAtTheBeginningOrEnd(String firstName) {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> target.setFirstName(firstName));
        assertEquals("There should be no empty space at the beginning or the end of a first name", exception.getMessage());
    }

    @DisplayName("should throw exception when pass a first name which length is not between 2 and 20 inclusively to setFirstName function")
    @ParameterizedTest
    @ValueSource(strings = {"a", "aaaaaaaaaaaaaaaaaaaaa" })
    public void shouldThrowExceptionWhenLengthOfTheFirstNameIsInvaild(String firstName) {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> target.setFirstName(firstName));
        assertEquals("The length of the first name must be equal or greater than 2 and equal or smaller than 20", exception.getMessage());
    }

    @DisplayName("should not throw exception when pass a first name which length is between 2 and 20 inclusively to setFirstName function")
    @ParameterizedTest
    @ValueSource(strings = {"aa", "aaa", "aaaaaaaaaa", "aaaaaaaaaaaaaaaaaaa", "aaaaaaaaaaaaaaaaaaaa"})
    public void shouldNotThrowExceptionWhenLengthOfTheFirstNameIsVaild(String firstName) {

        assertDoesNotThrow(()-> {
            target.setFirstName(firstName);
        });
    }

    //We assume that first name can not contain special characters or numbers.
    @DisplayName("should throw exception when pass a first name which contains special characters or numbers to setFirstName function")
    @ParameterizedTest
    @ValueSource(strings = {"aaaa aa!", "aaaaaa1", "aaaaaa1!"})
    public void shouldThrowExceptionWhenFirstNameContainsSpecialCharactersOrNumbers(String firstName) {

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> target.setFirstName(firstName));
        assertEquals("First name must not contain special characters or numbers", exception.getMessage());

    }


    //Last name validation.

    @DisplayName("should throw exception when pass a null to setLastName function")
    @Test
    public void shouldThrowExceptionWhenSetLastNameToNull(){

        NullPointerException exception = assertThrows(NullPointerException.class, () -> target.setLastName(null));
        assertEquals("Last name cannot be null or empty", exception.getMessage());
    }

    @DisplayName("should throw exception when pass a empty last name to setLastName function")
    @ParameterizedTest
    @ValueSource(strings = {"", " ", "  "})
    public void shouldThrowExceptionWhenSetLastNameToEmpty(String lastName) {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> target.setLastName(lastName));
        assertEquals("Last name cannot be null or empty", exception.getMessage());
    }

    @DisplayName("should throw exception when pass a last name which has empty space at the beginning or the end to setLastName function")
    @ParameterizedTest
    @ValueSource(strings = {"a ", " a", " a "})
    public void shouldThrowExceptionWhenTheLastNameContainsEmptySpaceAtTheBeginningOrEnd(String lastName) {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> target.setLastName(lastName));
        assertEquals("There should be no empty space at the beginning or the end of a last name", exception.getMessage());
    }

    @DisplayName("should throw exception when pass a last name which length is not between 2 and 10 inclusively to setLastName function")
    @ParameterizedTest
    @ValueSource(strings = {"a", "aaaaaaaaaaa" })
    public void shouldThrowExceptionWhenLengthOfTheLastNameIsInvaild(String lastName) {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> target.setLastName(lastName));
        assertEquals("The length of the last name must be equal or greater than 2 and equal or smaller than 10", exception.getMessage());
    }

    @DisplayName("should not throw exception when pass a last name which length is between 2 and 10 inclusively to setLastName function")
    @ParameterizedTest
    @ValueSource(strings = {"aa", "aaa", "aaaaa", "aaaaaaaaa", "aaaaaaaaaa"})
    public void shouldNotThrowExceptionWhenLengthOfTheLastNameIsVaild(String lastName) {

        assertDoesNotThrow(()-> {
            target.setLastName(lastName);
        });
    }

    //We assume that last name can not contain special characters or numbers.
    @DisplayName("should throw exception when pass a last name which contains special characters to setLastName function")
    @ParameterizedTest
    @ValueSource(strings = {"aaaaa!", "aaaaa1", "aaaaa1!"})
    public void shouldThrowExceptionWhenLastNameContainsSpecialCharacters(String lastName) {

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> target.setLastName(lastName));
        assertEquals("Last name must not contain special characters or numbers", exception.getMessage());

    }




    //email validation.

    @DisplayName("should throw exception when pass null to setEmail function")
    @Test
    public void shouldThrowExceptionWhenSetEmailToNull() {
        NullPointerException exception = assertThrows(NullPointerException.class, () -> target.setEmail(null));
        assertEquals("email cannot be null or empty", exception.getMessage());
    }

    @DisplayName("should throw exception when pass a empty email address to setEmail function")
    @ParameterizedTest
    @ValueSource(strings = {"", " ", "  "})
    public void shouldThrowExceptionWhenSetEmailToEmpty(String email) {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> target.setEmail(email));
        assertEquals("email cannot be null or empty", exception.getMessage());
    }

    @DisplayName("should throw exception when pass a email which has empty space at the beginning or the end to setEmail function")
    @ParameterizedTest
    @ValueSource(strings = {"a@qq.com ", " a@qq.com", " a@qq.com "})
    public void shouldThrowExceptionWhenTheEmailContainsEmptySpaceAtTheBeginningOrEnd(String email) {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> target.setEmail(email));
        assertEquals("There should be no empty space at the beginning or the end of a email", exception.getMessage());
    }

    @DisplayName("should throw exception when pass an email which length is not between 7 and 40 inclusively to setEmail function")
    @ParameterizedTest
    @ValueSource(strings = {"123456", "12345678901234567890123456789012345678901" })
    public void shouldThrowExceptionWhenTheLengthOfTheEmialIsInvaild(String email) {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> target.setEmail(email));
        assertEquals("The length of email must be equal or greater than 7 and equal or smaller than 40", exception.getMessage());
    }

    @DisplayName("should not throw exception when pass an email which length is between 7 and 40 inclusively to setEmail function")
    @ParameterizedTest
    @ValueSource(strings = {"1@qq.com", "12@qq.com", "1234567890123@qq.com", "12345678901234567890123456789012@qq.com", "123456789012345678901234567890123@qq.com"})
    public void shouldNotThrowExceptionWhenLengthOfTheEmailIsVaild(String email) {

        assertDoesNotThrow(()-> {
            target.setEmail(email);
        });
    }

    @DisplayName("should throw exception when pass an email which does not follow a universal format")
    @ParameterizedTest
    @ValueSource(strings = {"@qq.com", "123@.com", "123@qq.1.com", "123@qq.12345678901.com", "123@qq.1", "123@qq.1234"})
    public void shouldThrowExceptionWhenPassAnEmailWhichDoesNotFollowAUnversalFormat(String email){

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> target.setEmail(email));
        assertEquals("email should follow a specific format", exception.getMessage());
    }

    @DisplayName("should not throw exception when pass an email which follows a universal format")
    @ParameterizedTest
    @ValueSource(strings = {"123@qq.com", "123@qq.com", "123@qq.monashasas.com", "123@qq.onashasas.com", "123@qq.masas.com", "123@qq.mon.com", "123@qq.mo.com", "123@qq.com.cn", "123@qq.cnn"})
    public void shouldNotThrowExceptionWhenPassAnEmailWhichFollowsAUnversalFormat(String email){

        assertDoesNotThrow(()-> {
            target.setEmail(email);
        });

    }


    //password validation.

    @DisplayName("should throw exceptions when pass a null password to setPassword function")
    @Test
    public void shouldThrowExceptionWhenSetPasswordToNull() {
        NullPointerException exception = assertThrows(NullPointerException.class,
                () -> target.setPassword(null));
        assertEquals("password cannot be null or empty", exception.getMessage());
    }

    @DisplayName("should throw exception when pass a empty password to setPassword function")
    @ParameterizedTest
    @ValueSource(strings = {"", " ", "  "})
    public void shouldThrowExceptionWhenSetPasswordToEmpty(String password) {

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> target.setPassword(password));
        assertEquals("password cannot be null or empty", exception.getMessage());
    }

    @DisplayName("should throw exception when pass a password which has empty space at the beginning or the end to setPassword function")
    @ParameterizedTest
    @ValueSource(strings = {"@q1235 ", " @q1234", " @q1234 "})
    public void shouldThrowExceptionWhenThePasswordContainsEmptySpaceAtTheBeginningOrEnd(String password) {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> target.setPassword(password));
        assertEquals("There should be no empty space at the beginning or the end of a password", exception.getMessage());
    }

    @DisplayName("should throw exception when pass an password which length is not between 6 and 15 inclusively to setPassword function")
    @ParameterizedTest
    @ValueSource(strings = {"12345", "1234567890123456" })
    public void shouldThrowExceptionWhenTheLengthOfThePasswordIsNotVaild(String password) {

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> target.setPassword(password));
        assertEquals("The length of the password must be equal or greater than 6 and equal or smaller than 15", exception.getMessage());
    }

    @DisplayName("should not throw exception when pass an password which length is not between 6 and 15 inclusively to setPassword function")
    @ParameterizedTest
    @ValueSource(strings = {"@q3456", "@q34567", "@q34567890", "@q345678901234", "@q3456789012345" })
    public void shouldNotThrowExceptionWhenTheLengthOfThePasswordIsVaild(String password) {

        assertDoesNotThrow(()-> {
            target.setPassword(password);
        });

    }

    @DisplayName("should throw exception when pass an password which does not include 3 or more type of character. Type of character includes special character, uppercase character, lowercase character and number")
    @ParameterizedTest
    @ValueSource(strings = {"@@@@@@", "111111", "qqqqqq", "QQQQQQ", "@@@111", "@@@qqq", "@@@QQQ", "111qqq", "111QQQ", "qqqQQQ" })
    public void shouldThrowExceptionWhenTheDifferentTypeOfCharacterOfThePasswordIsNotEnough(String password) {

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> target.setPassword(password));
        assertEquals("The character used in the password must include 3 or more different type of character and the character can be special character, uppercase character, lowercase character and number", exception.getMessage());
    }

    @DisplayName("should not throw exception when pass an password which includes 3 or more type of character. Type of character includes special character, uppercase character, lowercase character and number")
    @ParameterizedTest
    @ValueSource(strings = {"@@11qq", "@@11QQ", "@@qqQQ", "11qqQQ", "@@11qqQQ"})
    public void shouldNotThrowExceptionWhenTheDifferentTypeOfCharacterOfThePasswordIsEnough(String password) {

        assertDoesNotThrow(()-> {
            target.setPassword(password);
        });

    }


    //equals method validation.

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

    @DisplayName("should return false when pass a object which is not share the same class with the traget to the equals function")
    @ParameterizedTest
    @MethodSource("InvalidObjectProvider")
    public void shouldReturnFalseWhenPassAObjectOfDifferentClass(Object o) {

        assertFalse(target.equals(o));
    }

    private static Stream<Arguments> InvalidObjectProvider() {

        LaunchServiceProvider l = new LaunchServiceProvider("hehe X", 1949, "China");
        Rocket r = new Rocket("hehe X", "China", l);

        return Stream.of(Arguments.of(r));
    }

    @DisplayName("should return true when two users have the same email")
    @Test
    public void shouldReturnTrueWhenUsersHaveSameEmail() {
        String email = "abc@example.com";
        target.setEmail(email);
        User anotherUser = new User();
        anotherUser.setEmail(email);
        assertTrue(target.equals(anotherUser));
    }


    @DisplayName("should return false when two users have different emails")
    @Test
    public void shouldReturnFalseWhenUsersHaveDifferentEmails() {
        target.setEmail("abc@example.com");
        User anotherUser = new User();
        anotherUser.setEmail("def@example.com");
        assertFalse(target.equals(anotherUser));
    }

    @DisplayName("should return false when user enters old password that doesn't match")
    @Test
    public void shouldThrowExceptionWhenPasswordDoesntMatch(){
        target.setPassword("123qweQWE");
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> target.changePassword("321ewqEWQ","333eeeEEE"));
        assertEquals("old password doesn't match", exception.getMessage());
    }

    @DisplayName("should throw exception when user enters old password that doesn't match")
    @Test
    public void shouldThrowExceptionWhenNewPasswordEqualsCurrentPassword(){
        target.setPassword("123qweQWE");
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> target.changePassword("123qweQWE","123qweQWE"));
        assertEquals("new password shouldn't be the same as old password", exception.getMessage());
    }
}
