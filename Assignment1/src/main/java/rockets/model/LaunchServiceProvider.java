package rockets.model;

import com.google.common.collect.Sets;

import java.util.Objects;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.apache.commons.lang3.Validate.*;

public class LaunchServiceProvider extends Entity {

    private String name;
    private int yearFounded;
    private String country;
    private String headquarters;
    private Set<Rocket> rockets;


    public LaunchServiceProvider(String name, int yearFounded, String country) {

        notBlank(name, "name cannot be null or empty");
        notBlank(country, "country cannot be null or empty");

        isTrue(name.equals(name.trim()), "There should be no empty space at the beginning or the end of a name");
        isTrue(country.equals(country.trim()), "There should be no empty space at the beginning or the end of a country");

        inclusiveBetween(2,40, name.length(), "The length of the name must be equal or greater than 2 and equal or smaller than 40");
        inclusiveBetween(1500, 2019, yearFounded, "yearFounder must be equal or greater than 1500 and equal or smaller than current year");
        inclusiveBetween(2,40, country.length(), "The length of the country must be equal or greater than 2 and equal or smaller than 40");

        isTrue(!checkNumber(country) && !checkSpecialCharacter(country), "country cannot have numbers or special characters");


        this.name = name;
        this.yearFounded = yearFounded;
        this.country = country;

        rockets = Sets.newLinkedHashSet();
    }

    public String getName() {
        return name;
    }

    public int getYearFounded() {
        return yearFounded;
    }

    public String getCountry() {
        return country;
    }

    public String getHeadquarters() {
        return headquarters;
    }

    public Set<Rocket> getRockets() {
        return rockets;
    }

    public void setHeadquarters(String headquarters) {

        notBlank(headquarters, "Headquarters cannot be null or empty");
        isTrue(headquarters.trim() == headquarters, "There should be no empty space at the beginning or the end of a headquarters");
        inclusiveBetween(2,20, headquarters.length(), "The length of the headquarters must be equal or greater than 2 and equal or smaller than 20");
        isTrue(!checkSpecialCharacter(headquarters) && !checkNumber(headquarters), "Headquarters must not contain special characters or numbers");


        this.headquarters = headquarters;
    }

    public void setRockets(Set<Rocket> rockets) {

        notNull(rockets, "Rockets cannot be null");
        noNullElements(rockets, "Every rocket cannot be null");

        for(Rocket rocket :rockets) {
            isTrue(rocket.getCountry().equals(country), "Every rocket must share the same country with the service provider");
        }


        this.rockets = rockets;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LaunchServiceProvider that = (LaunchServiceProvider) o;

        return yearFounded == that.yearFounded &&
                Objects.equals(name, that.name) &&
                Objects.equals(country, that.country);
    }

    @Override
    public int hashCode() {

        return Objects.hash(name, yearFounded, country);
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
