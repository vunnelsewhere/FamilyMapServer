package Service;

// From Java Serializer/Deserializer
import com.google.gson.Gson;

// From Java Reader (json files to objects)
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

// From java Random (generate random names and locationList)
import java.util.Random;

public class SerializeDataService {

    // Variable Declaration


    private static String[] femaleNameList;

    private static String[] maleNameList;

    private static Location[] locationList;
    private static String[] surnameList;


    public SerializeDataService() {
        Gson gson = new Gson();
        loadSurnameList(gson, "json/snames.json");
        loadFemaleNameList(gson, "json/fnames.json");
        loadMaleNameList(gson, "json/mnames.json");
        loadLocationList(gson, "json/locations.json");
    }

    private void loadSurnameList(Gson gson, String path) {
        try (Reader reader = new FileReader(path)) {
            SNameData data = gson.fromJson(reader, SNameData.class);
            this.surnameList = data.data;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadFemaleNameList(Gson gson, String path) {
        try (Reader reader = new FileReader(path)) {
            FNameData data = gson.fromJson(reader, FNameData.class);
            this.femaleNameList = data.data;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadMaleNameList(Gson gson, String path) {
        try (Reader reader = new FileReader(path)) {
            MNameData data = gson.fromJson(reader, MNameData.class);
            this.maleNameList = data.data;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadLocationList(Gson gson, String path) {
        try (Reader reader = new FileReader(path)) {
            LocationData data = gson.fromJson(reader, LocationData.class);
            this.locationList = data.data;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getRandomName(String gender) {
        String firstName;
        firstName = getFirstName(gender);
        String lastName;
        lastName = getLastName();
        return firstName + " " + lastName;
    }

    private String getFirstName(String gender) {
        if (gender.equals("m")) {
            return getRandomElement(this.maleNameList);
        } else {
            return getRandomElement(this.femaleNameList);
        }
    }

    private String getLastName() {
        return getRandomElement(this.surnameList);
    }

    private String getRandomElement(String[] array) {
        Random rand = new Random();
        return array[rand.nextInt(array.length)];
    }

    public Location getRandomLocation() {
        if (this.locationList.length == 0) {
            throw new IllegalArgumentException("Empty Location List");
        }

        Random rand = new Random();
        int index = rand.nextInt(this.locationList.length);
        Location location = this.locationList[index];

        System.out.println("Generated random location: " + location.getCountry() + ", " + location.getCity());

        return location;
    }

}
