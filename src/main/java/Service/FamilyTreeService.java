package Service;

// generate fake family trees
// shared by RegisterService and FillService
import Model.*;
import DataAccess.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

public class FamilyTreeService {
    private User user;
    private final PersonDao pDao;
    private final EventDao eDao;

    private int personCount = 0;
    private int eventCount = 0;
    private SerializeDataService sd = new SerializeDataService();

    public FamilyTreeService(User user, PersonDao pDao, EventDao eDao) {
        this.user = user;
        this.pDao = pDao;
        this.eDao = eDao;
    }

    public void generate(int generation) {

        // Extract Data
        String username = this.user.getUsername();
        String firstName = this.user.getFirstName();
        String lastName = this.user.getLastName();
        String gender = this.user.getGender();
        String personID = this.user.getPersonID();

        // Corresponding person object (the root user)
        Person person = new Person(personID, username, firstName, lastName, gender, null, null, null);

        // Generate list of events for the user
        List<Event> events = genEvents(person);
        int birthYear = 2000;

        // set year for the root person (year field not null)
        events.get(0).setYear(2000);
        events.get(1).setYear(2025);
        events.get(2).setYear(2100); // nobody must die at an age older than 120 years old
        genFamily(person, generation, events);

        // Each Person object may also have Events associated with them.
        // These Event objects share an identical associatedUsername and will each have a unique eventID.
        // They also have a personID that matches the person they are associated with.
    }



   /*
    * This method is used to generate events for the particular person
    */
    public List<Event> genEvents(Person person) {

        List<Event> events = new ArrayList<Event>();
        Event birth = genBirth(person);
        Event marriage = genMarriage(person);
        Event death = genDeath(person);
        events.add(birth);
        events.add(marriage);
        events.add(death);

        return events;
    }

    public Event genBirth(Person person) {
        String eventType = "birth";
        String personID = person.getPersonID();
        SerializeDataService.Location location = sd.getRandomLocation();
        Event birth = new Event(UUID.randomUUID().toString(), this.user.getUsername(), personID,
                location.getLatitude(), location.getLongitude(), location.getCountry(), location.getCity(),
                eventType, null);
        return birth;
    }

    public Event genMarriage(Person person) {
        String eventType = "marriage";
        String personID = person.getPersonID();
        SerializeDataService.Location location = sd.getRandomLocation();
        Event marriage = new Event(UUID.randomUUID().toString(), this.user.getUsername(), personID,
                location.getLatitude(), location.getLongitude(), location.getCountry(), location.getCity(),
                eventType, null);
        return marriage;
    }

    public Event genDeath(Person person) {
        String eventType = "death";
        String personID = person.getPersonID();
        SerializeDataService.Location location = sd.getRandomLocation();
        Event death = new Event(UUID.randomUUID().toString(), this.user.getUsername(), personID,
                location.getLatitude(), location.getLongitude(), location.getCountry(), location.getCity(),
                eventType, null);
        return death;
    }

    /**
     * Generate given number of generations of family for the root person
     * @param root: A Person object, the root of the family tree
     * @param generation: the number of generations to be generated
     */
    public void genFamily(Person root, int generation, List<Event> events) {

        Random rand = new Random();

        // case 1: generation 0 - only generate person and event data for the user
        if (generation == 0) {
            try {
                pDao.insert(root);
                eDao.insertEvents(events);
                personCount += 1;
                eventCount += events.size();
            } catch (DataAccessException e) {
                e.printStackTrace();
            }
            return;
        }

        // case 2: any generation > 0

        // Generate name for parents
        String[] motherName = sd.getRandomName("f").split(" ");
        String[] fatherName = sd.getRandomName("m").split(" ");

        Person mother = new Person(UUID.randomUUID().toString(), this.user.getUsername(),
                motherName[0], motherName[1], "f", null, null, null);
        Person father = new Person(UUID.randomUUID().toString(), this.user.getUsername(),
                fatherName[0], fatherName[1], "m", null, null, null);
        father.setSpouseID(mother.getPersonID());
        mother.setSpouseID(father.getPersonID());

        // generate the mother and father default Event[] objects (without years)
        List<Event> motherEvents = genEvents(mother);
        Event motherMarriage = motherEvents.get(1);
        List<Event> fatherEvents = genEvents(father);
        Event fatherMarriage = fatherEvents.get(1);
        fatherMarriage.setCity(motherMarriage.getCity());
        fatherMarriage.setCountry(motherMarriage.getCountry());
        fatherMarriage.setLatitude(motherMarriage.getLatitude());
        fatherMarriage.setLongitude(motherMarriage.getLongitude());

        // matching the mother and father events with the root person's events
        Integer birthYear = events.get(0).getYear();
        Integer[] parentYears = genParentYears(birthYear);
        motherEvents.get(0).setYear(parentYears[0]);
        fatherEvents.get(0).setYear(parentYears[0]);
        motherMarriage.setYear(parentYears[1]);
        fatherMarriage.setYear(parentYears[1]);
        motherEvents.get(2).setYear(parentYears[2]);
        fatherEvents.get(2).setYear(parentYears[2]);

        // update information for the root person
        root.setMotherID(mother.getPersonID());
        root.setFatherID(father.getPersonID());

        // insert the root person into the database
        try {
            pDao.insert(root);
            eDao.insertEvents(events);
            personCount += 1;
            eventCount += events.size();
        } catch (DataAccessException e) {
            e.printStackTrace();
        }

        // recurse on the mother and father of the current person
        genFamily(mother, generation - 1, motherEvents);
        genFamily(father, generation - 1, fatherEvents);
    }


    private Integer[] genParentYears(Integer childBirthYear) {

        Integer[] parentYears = new Integer[3];
        // parent birth year (born at least 13 years before their children, not give birth when older than 50)
        parentYears[0] = ThreadLocalRandom.current().nextInt(childBirthYear - 50, childBirthYear - 14);
        // parent marriage year (at least 13 years old when they are married)
        parentYears[1] = ThreadLocalRandom.current().nextInt(parentYears[0] + 13, childBirthYear);
        // parent death year (live no longer than 120, not die before their child is born)
        parentYears[2] = ThreadLocalRandom.current().nextInt(childBirthYear, parentYears[0] + 120);

        return parentYears;
    }

    public int getPersonCount() {
        return personCount;
    }

    public int getEventCount() {
        return eventCount;
    }
}