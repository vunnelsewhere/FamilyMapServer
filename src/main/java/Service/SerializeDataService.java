package Service;

// From Java Serializer/Deserializer
import com.google.gson.Gson;

// From Java Reader (json files to objects)
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

// From java Random (generate random names and locations)
import java.util.Random;

public class SerializeDataService {

    // Variable Declaration
    private String[] surnames;
    private String[] fnames; // female first names
    private String[] mnames; // male last names
    private Location[] locations;

    // Location.java
    public class Location {
        private String country;
        private String city;
        private float latitude;
        private float longitude;

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public float getLatitude() {
            return latitude;
        }

        public void setLatitude(float latitude) {
            this.latitude = latitude;
        }

        public float getLongitude() {
            return longitude;
        }

        public void setLongitude(float longitude) {
            this.longitude = longitude;
        }
    }


    // LocationData.java
    private class LocationData {
        private Location[] data;
    }

    // SNameData.java
    private class SNameData {
        private String[] data;
    }

    // FNameData.java
    private class FNameData {
        private String[] data;
    }

    // MNameData.java
    private class MNameData {
        private String[] data;
    }

    public SerializeDataService() {

        Gson gson = new Gson();

        String lastNamePath = "json/snames.json";
        String femaleNamePath = "json/fnames.json";
        String maleNamePath = "json/mnames.json";
        String locationPath = "json/locations.json";


        try (Reader lastNameReader = new FileReader(lastNamePath);
             Reader femaleNameReader = new FileReader(femaleNamePath);
             Reader maleNameReader = new FileReader(maleNamePath);
             Reader locationReader = new FileReader(locationPath)) {

            SNameData surData = gson.fromJson(lastNameReader, SNameData.class);
            FNameData fData = gson.fromJson(femaleNameReader, FNameData.class);
            MNameData mData = gson.fromJson(maleNameReader, MNameData.class);
            LocationData locData = gson.fromJson(locationReader, LocationData.class);

            this.surnames = surData.data;
            this.fnames = fData.data;
            this.mnames = mData.data;
            this.locations = locData.data;

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Get a random name for the person of the given gender.
     * @param gender: the gender of the person
     * @return: a random name generated from the JSON data.
     */
    public String getRandomName(String gender) {

        Random rand = new Random();
        String lastName = this.surnames[rand.nextInt(this.surnames.length)];

        String firstName;
        if (gender.equals("f")) {
            firstName = this.fnames[rand.nextInt(this.fnames.length)];
        }
        else {
            firstName = this.mnames[rand.nextInt(this.mnames.length)];
        }

        return firstName + " " + lastName;
    }

    public Location getRandomLocation() {
        Random rand = new Random();
        return this.locations[rand.nextInt(this.locations.length)];
    }
}