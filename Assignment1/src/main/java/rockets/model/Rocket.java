package rockets.model;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.apache.commons.lang3.Validate.*;

public class Rocket extends Entity {

    private String name;
    private String country;
    private LaunchServiceProvider manufacturer;
    private String massToLEO;
    private String massToGTO;
    private String massToOther;

    /**
     * All parameters shouldn't be null.
     *
     * @param name
     * @param country
     * @param manufacturer
     */
    public Rocket(String name, String country, LaunchServiceProvider manufacturer) {

        notBlank(name, "name cannot be null or empty");
        notBlank(country, "country cannot be null or empty");
        notNull(manufacturer, "manufacturer cannot be null");
        //notBlank(manufacturer, "manufacturer cannot be null or empty");

        isTrue(name.equals(name.trim()), "There should be no empty space at the beginning or the end of a name");
        isTrue(country.equals(country.trim()), "There should be no empty space at the beginning or the end of a country");
        //isTrue(manufacturer.trim() == manufacturer, "There should be no empty space at the beginning or the end of a manufacturer");

        inclusiveBetween(2,40, name.length(), "The length of the name must be equal or greater than 2 and equal or smaller than 40");
        inclusiveBetween(2,40, country.length(), "The length of the country must be equal or greater than 2 and equal or smaller than 40");
        //inclusiveBetween(2,40, manufacturer.length(), "The length of the manufacturer must be equal or greater than 2 and equal or smaller than 40");

        isTrue(!checkNumber(country) && !checkSpecialCharacter(country), "country cannot have numbers or special characters");


        this.name = name;
        this.country = country;
        this.manufacturer = manufacturer;
    }


    public String getName() {
        return name;
    }

    public String getCountry() {
        return country;
    }

    public LaunchServiceProvider getManufacturer() {
        return manufacturer;
    }

    public String getMassToLEO() {
        return massToLEO;
    }

    public String getMassToGTO() {
        return massToGTO;
    }

    public String getMassToOther() {
        return massToOther;
    }


    public void setMassToLEO(String massToLEO) {

        notNull(massToLEO, "massToLEO cannot be null");
        isTrue(massToLEO.equals(massToLEO.trim()), "There should be no empty space at the beginning or the end of massToLEO");
        inclusiveBetween(0,30, massToLEO.length(), "The length of the massToLEO must be equal or greater than 0 and equal or smaller than 30");

        this.massToLEO = massToLEO;
    }

    public void setMassToGTO(String massToGTO) {

        notNull(massToGTO, "massToGTO cannot be null");
        isTrue(massToGTO.equals(massToGTO.trim()), "There should be no empty space at the beginning or the end of massToGTO");
        inclusiveBetween(0,30, massToGTO.length(), "The length of the massToGTO must be equal or greater than 0 and equal or smaller than 30");

        this.massToGTO = massToGTO;
    }

    public void setMassToOther(String massToOther) {

        notNull(massToOther, "massToOther cannot be null");
        isTrue(massToOther.equals(massToOther.trim()), "There should be no empty space at the beginning or the end of massToOther");
        inclusiveBetween(0,30, massToOther.length(), "The length of the massToOther must be equal or greater than 0 and equal or smaller than 30");

        this.massToOther = massToOther;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Rocket rocket = (Rocket) o;

        return Objects.equals(name, rocket.name) &&
                Objects.equals(country, rocket.country) &&
                Objects.equals(manufacturer, rocket.manufacturer);
    }

    @Override
    public int hashCode() {

        return Objects.hash(name, country, manufacturer);
    }

    @Override
    public String toString() {

        return "Rocket{" +
                "name='" + name + '\'' +
                ", country='" + country + '\'' +
                ", manufacturer='" + manufacturer + '\'' +
                ", massToLEO='" + massToLEO + '\'' +
                ", massToGTO='" + massToGTO + '\'' +
                ", massToOther='" + massToOther + '\'' +
                '}';
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
}
