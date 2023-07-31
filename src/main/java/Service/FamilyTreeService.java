package Service;



import DataAccess.*;
import Model.User;
import Model.*;

import java.util.ArrayList;
import java.util.List;

import java.util.*;
import java.util.concurrent.*;

public class FamilyTreeService {


    private int mybirthyear = 2003;
    private int yeartoBYU = 2021;
    private int yeartoretire = 2100;

    private List<Event> events;
    private int numPeople = 0;
    private int numEvent = 0;
    private User user;
    private PersonDao pDao;
    private EventDao eDao;

    private SerializeDataService sd = new SerializeDataService();



    public FamilyTreeService(User newUser, PersonDao newpDao, EventDao neweDao) {
        user = newUser;
        pDao = newpDao;
        eDao = neweDao;
    }

    // Getters and Setters
    public int getNumPeople() {
        return numPeople;
    }

    public int getNumEvent() {
        return numEvent;
    }

    public void generate(int generation) {

        String personID = user.getPersonID();
        String username = user.getUsername();
        String firstName = user.getFirstName();
        String lastName = user.getLastName();
        String gender = user.getGender();

        Person person = new Person(personID, username, firstName, lastName, gender, null, null, null);

        this.events = generateEvents(person);

        events.get(0).setYear(mybirthyear);
        events.get(1).setYear(yeartoBYU);
        events.get(2).setYear(yeartoretire); // nobody must die at an age older than 120 years old
        familyGenerate(person, generation, events);

    }


    /*
     * This method is used to generate events for the particular person
     */
    public List<Event> generateEvents(Person person) {

        List<Event> events = new ArrayList<Event>();

        Event birth = generateBirth(person);
        Event marriage = generateMarriage(person);
        Event death = generateDeath(person);
        events.add(birth);
        events.add(marriage);
        events.add(death);

        return events;
    }

    public Event generateBirth(Person person) {
        String eventType = "birth";
        String personID = person.getPersonID();
        Location location = sd.getRandomLocation();
        Event birth = new Event(UUID.randomUUID().toString(), this.user.getUsername(), personID,
                location.getLatitude(), location.getLongitude(), location.getCountry(), location.getCity(),
                eventType, null);
        return birth;
    }

    public Event generateMarriage(Person person) {
        String eventType = "marriage";
        String personID = person.getPersonID();
        Location location = sd.getRandomLocation();
        Event marriage = new Event(UUID.randomUUID().toString(), this.user.getUsername(), personID,
                location.getLatitude(), location.getLongitude(), location.getCountry(), location.getCity(),
                eventType, null);
        return marriage;
    }

    public Event generateDeath(Person person) {
        String eventType = "death";
        String personID = person.getPersonID();
        Location location = sd.getRandomLocation();
        Event death = new Event(UUID.randomUUID().toString(), this.user.getUsername(), personID,
                location.getLatitude(), location.getLongitude(), location.getCountry(), location.getCity(),
                eventType, null);
        return death;
    }


    public void familyGenerate(Person root, int generation, List<Event> events) {
        // Case 1: generation 0
        if (generation == 0) {
            persistData(root, events);
            return;
        }

        // Case 2: any generation > 0
        // Generate name for parents
        Person mother = generateParent("f");
        Person father = generateParent("m");
        linkSpouses(mother, father);

        // Generate events for mother and father
        List<Event> motherEvents = generateEvents(mother);
        List<Event> fatherEvents = generateEvents(father);
        syncMarriageEvents(motherEvents.get(1), fatherEvents.get(1));

        // Match the mother and father events with the root person's events
        setParentYears(events.get(0).getYear(), motherEvents, fatherEvents);

        // Update information for the root person
        root.setMotherID(mother.getPersonID());
        root.setFatherID(father.getPersonID());

        // Insert the root person into the database
        persistData(root, events);

        // Decrement generations from every loop
        familyGenerate(mother, generation - 1, motherEvents);
        familyGenerate(father, generation - 1, fatherEvents);
    }

    private Person generateParent(String gender) {
        String[] parentName = sd.getRandomName(gender).split(" ");
        return new Person(UUID.randomUUID().toString(), this.user.getUsername(),
                parentName[0], parentName[1], gender, null, null, null);
    }

    private void linkSpouses(Person person1, Person person2) {
        person1.setSpouseID(person2.getPersonID());
        person2.setSpouseID(person1.getPersonID());
    }

    private void syncMarriageEvents(Event motherMarriage, Event fatherMarriage) {
        fatherMarriage.setCity(motherMarriage.getCity());
        fatherMarriage.setCountry(motherMarriage.getCountry());
        fatherMarriage.setLatitude(motherMarriage.getLatitude());
        fatherMarriage.setLongitude(motherMarriage.getLongitude());
    }

    private void setParentYears(Integer birth, List<Event> mEvents, List<Event> fEvents) {
        Integer[] parentAge = genParentYears(birth);

        setEventYears(mEvents, parentAge);
        setEventYears(fEvents, parentAge);
    }




    private void persistData(Person person, List<Event> events) {
        try {
            pDao.insert(person);
            numPeople++;

            eDao.insertEvents(events);

            numEvent = numEvent + events.size();
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
    }

    private void setEventYears(List<Event> events, Integer[] years) {
        for(int i=0; i < 3; i++) {
            events.get(i).setYear(years[i]);
        }
    }


    private Integer[] genParentYears(Integer childBirth) {
        if (childBirth <= 0) {
            throw new IllegalArgumentException("Child's birth year must be a positive integer.");
        }

        int maxParentAge = 120;
        // int minParentAgeAtMarriage = 20;
        int maxParentAgeAtMarriage = 50;
        int minParentAgeDifference = 13;
        // int maxParentAgeDifference = 45;

        Integer[] parentYears = new Integer[3];

        parentYears[0] = ThreadLocalRandom.current().nextInt(childBirth - maxParentAgeAtMarriage, childBirth - minParentAgeDifference);

        parentYears[1] = ThreadLocalRandom.current().nextInt(parentYears[0] + minParentAgeDifference, childBirth);

        parentYears[2] = ThreadLocalRandom.current().nextInt(childBirth, parentYears[0] + maxParentAge);

        // Validate parent death year to ensure it doesn't exceed the maximum age constraint
        if (parentYears[2] - parentYears[0] > maxParentAge) {
            parentYears[2] = parentYears[0] + maxParentAge;
        }

        // Validate parent marriage year to ensure it's before the child's birth
        if (parentYears[1] >= childBirth) {
            parentYears[1] = childBirth - 1;
        }

        return parentYears;
    }

}
