package rockets.model;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import static org.apache.commons.lang3.Validate.*;

public class User extends Entity {

    private String firstName;
    private String lastName;
    private String email;
    private String password;


    public String getFirstName() {

        return firstName;
    }

    public void setFirstName(String firstName) {

        notBlank(firstName, "First name cannot be null or empty");
        isTrue(firstName.trim() == firstName, "There should be no empty space at the beginning or the end of a first name");
        inclusiveBetween(2,20, firstName.length(), "The length of the first name must be equal or greater than 2 and equal or smaller than 20");
        isTrue(!checkSpecialCharacter(firstName) && !checkNumber(firstName), "First name must not contain special characters or numbers");

        this.firstName = firstName;
    }

    public String getLastName() {

        return lastName;
    }

    public void setLastName(String lastName) {

        notBlank(lastName, "Last name cannot be null or empty");
        isTrue(lastName.trim() == lastName, "There should be no empty space at the beginning or the end of a last name");
        inclusiveBetween(2,10, lastName.length(), "The length of the last name must be equal or greater than 2 and equal or smaller than 10");
        isTrue(!checkSpecialCharacter(lastName) && !checkNumber(lastName), "Last name must not contain special characters or numbers");

        this.lastName = lastName;
    }

    public String getEmail() {

        return email;
    }

    public void setEmail(String email) {

        notBlank(email, "email cannot be null or empty");
        isTrue(email.trim() == email, "There should be no empty space at the beginning or the end of a email");
        inclusiveBetween(7,40, email.length(), "The length of email must be equal or greater than 7 and equal or smaller than 40");
        //其中\w代表可用作标识符的字符,不包括$. \w+表示多个
        //  \\.\\w表示点.后面有\w 括号{2,3}代表这个\w有2至3个
        //牵扯到有些邮箱类似com.cn结尾 所以(\\.\\w{2,3})*后面表示可能有另一个2至3位的域名结尾
        //*表示重复0次或更多次
        matchesPattern(email,"\\w+@\\w+(\\.\\w{2,10})*\\.\\w{2,3}","email should follow a specific format");

        this.email = email;
    }

    public String getPassword() {

        return password;
    }

    public void setPassword(String password)
    {
        notBlank(password, "password cannot be null or empty");
        isTrue(password.trim() == password, "There should be no empty space at the beginning or the end of a password");
        inclusiveBetween(6,15, password.length(), "The length of the password must be equal or greater than 6 and equal or smaller than 15");
        isTrue(checkPassword(password), "The character used in the password must include 3 or more different type of character and the character can be special character, uppercase character, lowercase character and number");

        this.password = password;
    }

    // match the given password against user's password and return the result
    public boolean isPasswordMatch(String password) {

        return this.password.equals(password.trim());
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;

        return Objects.equals(email, user.email);
    }

    @Override
    public int hashCode() {

        return Objects.hash(email);
    }

    @Override
    public String toString() {

        return "User{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

    public boolean checkSpecialCharacter(String input){

        Pattern p = Pattern.compile("[^a-z0-9 ]", Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(input);
        boolean b = m.find();

        if (b){

            //System.out.println("There is a special character in my string");
            return true;
        }

        else{
            return false;
        }

    }

    public boolean checkUppercaseCharacter(String input)
    {
        for(int i = 0; i < input.length(); i++)
        {
            char c = input.charAt(i);
            if (Character.isUpperCase(c))
            {
                return true;
            }
        }
        return false;
    }

    public boolean checkLowercaseCharacter(String input)
    {
        for(int i = 0; i < input.length(); i++)
        {
            char c = input.charAt(i);
            if (Character.isLowerCase(c))
            {
                return true;
            }
        }
        return false;
    }

    public boolean checkNumber(String input)
    {
        for(int i = 0; i < input.length(); i++)
        {
            char c = input.charAt(i);
            if (Character.isDigit(c))
            {
                return true;
            }
        }
        return false;
    }

    public boolean checkPassword(String input){

        int result = 0;

        if (checkSpecialCharacter(input)){
            result += 1;
        }
        if (checkUppercaseCharacter(input)){
            result += 1;
        }
        if (checkLowercaseCharacter(input)){
            result += 1;
        }
        if (checkNumber(input)){
            result += 1;
        }

        if (result >= 3){
            return true;
        }
        else{
            return false;
        }

    }
}
