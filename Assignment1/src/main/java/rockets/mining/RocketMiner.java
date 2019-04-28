package rockets.mining;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rockets.dataaccess.DAO;
import rockets.model.Launch;
import rockets.model.LaunchServiceProvider;
import rockets.model.Rocket;

import java.math.BigDecimal;
import java.security.Provider;
import java.util.*;
import java.util.stream.Collectors;

import static org.apache.commons.lang3.Validate.*;

public class RocketMiner {

    private static Logger logger = LoggerFactory.getLogger(RocketMiner.class);
    private DAO dao;

    public RocketMiner(DAO dao) {

        this.dao = dao;
    }

    /**
     * TODO: to be implemented & tested!
     * Returns the top-k most active rockets, as measured by number of completed launches.
     *
     * @param k the number of rockets to be returned.
     * @return the list of k most active rockets.
     */
    public List<Rocket> mostLaunchedRockets(int k) {

        isTrue(k > 0, "k must be greater than 0.");

        logger.info("find most active " + k + " rockets.");
        Collection<Launch> launches = dao.loadAll(Launch.class);
        List<Rocket> rockets = new ArrayList<Rocket>();

        if(launches.size() == 0){
            return null;
        }

        for(Launch launch : launches){

            if(launch.getLaunchOutcome() != null){

                rockets.add(launch.getLaunchVehicle());
            }
        }

        if(rockets.size() == 0){
            return null;
        }

        Comparator<Rocket> rocketCountComparator = (a, b) -> -Collections.frequency(rockets, a) + Collections.frequency(rockets, b);
        List<Rocket> rocketsSorted = rockets.stream().sorted(rocketCountComparator).collect(Collectors.toList());
        List<Rocket> rocketsResult = new ArrayList<>();

        for(Rocket rocket : rocketsSorted){

            if (Collections.frequency(rocketsResult, rocket) < 1){

                rocketsResult.add(rocket);
            }
        }

        if(rocketsResult.size() < k){
            k = rocketsResult.size();
        }

        return rocketsResult.subList(0,k);
    }


    /**
     * TODO: to be implemented & tested!
     * <p>
     * Returns the top-k most reliable launch service providers as measured
     * by percentage of successful launches.
     *
     * @param k the number of launch service providers to be returned.
     * @return the list of k most reliable ones.
     */
    public List<LaunchServiceProvider> mostReliableLaunchServiceProviders(int k) {

        isTrue(k > 0, "k must be greater than 0.");

        logger.info("find most reliable " + k + " launch service providers.");
        Collection<Launch> launches = dao.loadAll(Launch.class);
        Map<LaunchServiceProvider, List<Launch.LaunchOutcome>> lspAndLocs = new HashMap<>();

        if(launches.size() == 0){
            return null;
        }

        for(Launch launch : launches){

            if(launch.getLaunchOutcome() != null){

                if(lspAndLocs.containsKey(launch.getLaunchServiceProvider())){

                    lspAndLocs.get(launch.getLaunchServiceProvider()).add(launch.getLaunchOutcome());
                }
                else{

                    List<Launch.LaunchOutcome> locs = new ArrayList<>();
                    locs.add(launch.getLaunchOutcome());
                    lspAndLocs.put(launch.getLaunchServiceProvider(), locs);
                }
            }
        }

        if(lspAndLocs.size() == 0){
            return null;
        }

        Set<LaunchServiceProvider> lspList = lspAndLocs.keySet();
        List<LaunchServiceProvider> lspResult = new ArrayList<>(lspList);

        Comparator<LaunchServiceProvider> lspReliableComparator = (a, b) -> {
            int aSuccess = 0;
            int aFail = 0;
            int bSuccess = 0;
            int bFail = 0;
            List<Launch.LaunchOutcome> aList = lspAndLocs.get(a);
            List<Launch.LaunchOutcome> bList = lspAndLocs.get(b);

            for(Launch.LaunchOutcome loc : aList){

                if(loc == Launch.LaunchOutcome.FAILED){
                    aFail += 1;
                }
                if(loc == Launch.LaunchOutcome.SUCCESSFUL){
                    aSuccess += 1;
                }
            }

            for(Launch.LaunchOutcome loc : bList){
                if(loc == Launch.LaunchOutcome.FAILED){
                    bFail += 1;
                }
                if(loc == Launch.LaunchOutcome.SUCCESSFUL){
                    bSuccess += 1;
                }
            }

            if(aSuccess/(aSuccess + aFail) > bSuccess/(bSuccess + bFail)){
                return 1;
            }
            else if(aSuccess/(aSuccess + aFail) == bSuccess/(bSuccess + bFail)){
                return 0;
            }
            else{
                return -1;
            }
        };

        lspResult.sort(lspReliableComparator);

        if(k > lspResult.size()){
            k = lspResult.size();
        }

        return lspResult.subList(0,k);
    }

    /**
     * <p>
     * Returns the top-k most recent launches.
     *
     * @param k the number of launches to be returned.
     * @return the list of k most recent launches.
     */
    public List<Launch> mostRecentLaunches(int k) {

        isTrue(k > 0, "k must be greater than 0.");

        logger.info("find most recent " + k + " launches.");
        Collection<Launch> launches = dao.loadAll(Launch.class);
        Comparator<Launch> launchDateComparator = (a, b) -> -a.getLaunchDate().compareTo(b.getLaunchDate());

        if(launches == null){
            return null;
        }

        return launches.stream().sorted(launchDateComparator).limit(k).collect(Collectors.toList());
    }

    /**
     * TODO: to be implemented & tested!
     * <p>
     * Returns the dominant country who has the most launched rockets in an orbit.
     *
     * @param orbit the orbit
     * @return the country who sends the most payload to the orbit 
     */
    public String dominantCountry(String orbit) {

        notBlank(orbit, "orbit cannot be null or empty.");
        isTrue(orbit.equals(orbit.trim()), "There should be no empty space at the beginning or the end of a orbit.");
        inclusiveBetween(2,10, orbit.length(), "The length of the orbit must be equal or greater than 2 and equal or smaller than 10.");
        isTrue("LEO".equals(orbit) || "GTO".equals(orbit) || "Other".equals(orbit), "The orbit must be 'GTO', 'LEO' or 'Other'.");

        logger.info("find the dominant country who has the most launched rockets in " + orbit +".");
        Collection<Launch> launches = dao.loadAll(Launch.class);
        List<Launch> launchList = new ArrayList<>();
        List<String> countryList = new ArrayList<>();

        if(launches.size() == 0){
            return null;
        }

        for(Launch launch : launches){

            if(launch.getLaunchOutcome() == Launch.LaunchOutcome.SUCCESSFUL && launch.getOrbit().equals(orbit)){
                launchList.add(launch);
                countryList.add(launch.getLaunchVehicle().getCountry());
            }
        }

        Comparator<String> dominantCountryComparator = (a, b) -> -Collections.frequency(countryList, a) + Collections.frequency(countryList, b);
        countryList.sort(dominantCountryComparator);

        if(countryList.size() == 0){
            return "Cannot find any country.";
        }

        return countryList.get(0);
    }

    /**
     * TODO: to be implemented & tested!
     * <p>
     * Returns the top-k most expensive launches.
     *
     * @param k the number of launches to be returned.
     * @return the list of k most expensive launches.
     */
    public List<Launch> mostExpensiveLaunches(int k) {

        isTrue(k > 0, "k must be greater than 0.");

        logger.info("find the top " + k + " most expensive launches.");
        Collection<Launch> launches = dao.loadAll(Launch.class);
        List<Launch> launchList = new ArrayList<>();

        if(launches.size() == 0){
            return null;
        }

        for(Launch launch : launches){

            if(launch.getPrice() != null){
                launchList.add(launch);
            }
        }

        if(launchList.size() == 0){
            return null;
        }

        Comparator<Launch> mostExpensiveLaunchesComparator = (a, b) -> -a.getPrice().compareTo(b.getPrice());
        launchList.sort(mostExpensiveLaunchesComparator);

        if(k > launchList.size()){
            k = launchList.size();
        }

        return launchList.subList(0,k);
    }

    /**
     * TODO: to be implemented & tested!
     * <p>
     * Returns a list of launch service provider that has the top-k highest
     * sales revenue in a year.
     *
     * @param k the number of launch service provider.
     * @param year the year in request
     * @return the list of k launch service providers who has the highest sales revenue.
     */
    public List<LaunchServiceProvider> highestRevenueLaunchServiceProviders(int k, int year) {

        isTrue(k > 0, "k must be greater than 0.");
        isTrue(Calendar.getInstance().get(Calendar.YEAR) >= year && year >= 1500, "year must be greater than 1500.");


        logger.info("find the top " + k + " highest sales revenue in a year.");
        Collection<Launch> launches = dao.loadAll(Launch.class);
        List<Launch> launchList = new ArrayList<>();

        if(launches.size() == 0){
            return null;
        }

        for(Launch launch : launches){

            if(launch.getLaunchDate().getYear() == year && launch.getPrice() != null){
                launchList.add(launch);
            }
        }

        if(launchList.size() == 0){
            return null;
        }

        Map<LaunchServiceProvider, List<BigDecimal>> lspRevenue = new HashMap<>();

        for(Launch launch : launchList){

            if(lspRevenue.containsKey(launch.getLaunchServiceProvider())){

                lspRevenue.get(launch.getLaunchServiceProvider()).add(launch.getPrice());
            }
            else{

                List<BigDecimal> revenue = new ArrayList<>();
                revenue.add(launch.getPrice());
                lspRevenue.put(launch.getLaunchServiceProvider(), revenue);
            }
        }

        if(lspRevenue.size() == 0){
            return null;
        }

        Set<LaunchServiceProvider> lspList = lspRevenue.keySet();

        Comparator<LaunchServiceProvider> lspRevenueComparator = (a,b) -> {

            BigDecimal aRevenue = new BigDecimal(0);
            BigDecimal bRevenue = new BigDecimal(0);

            List<BigDecimal> aPrice = lspRevenue.get(a);
            List<BigDecimal> bPrice = lspRevenue.get(b);

            for(BigDecimal price : aPrice){
                aRevenue = aRevenue.add(price);
            }

            for(BigDecimal price : bPrice){
                bRevenue = bRevenue.add(price);
            }

            return aRevenue.compareTo(bRevenue);
        };

        if(k > lspList.size()){
            k = lspList.size();
        }

        return lspList.stream().sorted(lspRevenueComparator).limit(k).collect(Collectors.toList());
    }

    public List<LaunchServiceProvider> providersWithLongestHistory(int k) {
        isTrue(k > 0, "k must be greater than 0");
        Collection<LaunchServiceProvider> providers = dao.loadAll(LaunchServiceProvider.class);
        List<LaunchServiceProvider> providerList = new ArrayList<>();

        if (providers.size() == 0) {
            return null;
        }

        for (LaunchServiceProvider provider : providers) {

            if (provider.getYearFounded() != 0) {
                providerList.add(provider);
            }
        }

        if (providerList.size() == 0) {
            return null;
        }

        Comparator<LaunchServiceProvider> longestHistoryComparator = (b, a) -> -BigDecimal.valueOf(a.getYearFounded()).compareTo(BigDecimal.valueOf(b.getYearFounded()));
        providerList.sort(longestHistoryComparator);

        if (k > providerList.size()) {
            k = providerList.size();
        }

        return providerList.subList(0, k);
    }

    public List<String> countriesWithTheMostProviders(int k){
        isTrue(k > 0, "k must be greater than 0.");
        Collection<LaunchServiceProvider> providers = dao.loadAll(LaunchServiceProvider.class);
        ArrayList<String> countries= new ArrayList<String>();
        for(LaunchServiceProvider provider: providers){
            countries.add(provider.getCountry());
        }


        if(countries.size() == 0){
            return null;
        }

        Comparator<String> countriesComparator = (a, b) -> -Collections.frequency(countries, a) + Collections.frequency(countries, b);
        List<String> countriesSorted = countries.stream().sorted(countriesComparator).collect(Collectors.toList());
        List<String> countryResult = new ArrayList<>();

        for(String country : countriesSorted){

            if (Collections.frequency(countryResult, country) < 1){

                countryResult.add(country);
            }
        }

        if(countryResult.size() < k){
            k = countryResult.size();
        }

        return countryResult.subList(0,k);
    }
}
