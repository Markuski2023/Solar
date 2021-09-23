package no.hiof.markuski.oblig5;

import com.fasterxml.jackson.databind.ObjectMapper;
import no.hiof.markuski.oblig5.model.Planet;
import no.hiof.markuski.oblig5.model.PlanetSystem;
import no.hiof.markuski.oblig5.model.Star;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Main {
    public static void main(String[] args) throws IOException {

        ArrayList<PlanetSystem> planetSystems = new ArrayList<>();
        HashMap<String, PlanetSystem> hash = new HashMap<>();
        // Suns
        Star sun = new Star("Sun", 1, 1, 5780, "http://bit.ly/3cVhuZc");
        Star kepler11 = new Star("Kepler-11", 1.021, 1.042, 5836, "http://bit.ly/336nzNZ");


        // PlanetSystems
        PlanetSystem solar = new PlanetSystem("Solar", sun, "http://bit.ly/333CTus");
        PlanetSystem kepler = new PlanetSystem("Kepler", kepler11, "http://bit.ly/2Iz4jPB");


        // Planets for MilkyWay
        solar.addPlanet(new Planet("Saturn", 0.8145247020645666, 0.2994204425711275, 9.5826, 0.057, 10585, sun, "http://bit.ly/2W0sqic"));
        solar.addPlanet(new Planet("Venus", 0.08465003077267387, 0.002564278187565859, 0.723, 0.007, 225, sun, "http://bit.ly/2W3p4L9"));
        solar.addPlanet(new Planet("Jupiter", 1.0, 1.0, 5.20440, 0.049, 4380, sun, "http://bit.ly/2Q0fjK3"));
        solar.addPlanet(new Planet("Earth", 0.08911486599899289, 0.003146469968387777, 1, 0.017, 365, sun, "http://bit.ly/33bvXLZ"));
        solar.addPlanet(new Planet("Uranus", 0.35475297935433336, 1, 19.2184, 0.046, 30660, sun, "http://bit.ly/335pbHy"));
        solar.addPlanet(new Planet("Mercury", 0.03412549655905556, 1.7297154899894627E-4, 0.387, 0.206, 88, sun, "http://bit.ly/2TB2Heo"));
        solar.addPlanet(new Planet("Mars", 0.04741089912158004, 3.3667017913593256E-4, 1.524, 0.093, 687, sun, "http://bit.ly/3aGyFvr"));
        solar.addPlanet(new Planet("Neptune", 0.34440217087226543, 0.05395152792413066, 30.11, 0.010, 60225, sun, "http://bit.ly/38AyEba\n"));
        ArrayList<Planet> planetListForMilkyWay = solar.getPlanets();
        planetSystems.add(solar);


        //Planets for Kepler
        kepler.addPlanet(new Planet("Kepler-11b", 0.1610, 0.0060, 1.36134E7, 0.045, 10.0, kepler11, "x"));
        kepler.addPlanet(new Planet("Kepler-11c", 3.15, 13.5, 1.5857E7, 0.026, 13.0, kepler11, "x"));
        kepler.addPlanet(new Planet("Kepler-11d", 3.43, 6.1, 2.3786E7, 0.004, 22.0, kepler11, "x"));
        kepler.addPlanet(new Planet("Kepler-11e", 4.52, 8.4, 2.9021E7, 0.012, 31.0, kepler11, "x"));
        kepler.addPlanet(new Planet("Kepler-11f", 2.61, 2.3, 3.7399E7, 0.013, 36.0, kepler11, "x"));
        kepler.addPlanet(new Planet("Kepler-11g", 3.66, 25, 6.9114E7, 0.015, 118.0, kepler11, "x"));
        ArrayList<Planet> planetListForKepler = kepler.getPlanets();
        planetSystems.add(kepler);

        for (PlanetSystem system : planetSystems) {
            hash.put(system.getName(), system);
        }

        writeHashMapToCSV(hash, "planetos.csv");

        ArrayList<PlanetSystem> superhelterLestFraFil = lesFraJSonFil("planets_4000.json");

        System.out.println("\n\n*****************Konvertert fra JSON***********************");
        System.out.println(superhelterLestFraFil);

        planetSystems.remove(solar);

        skrivTilJSONFil(planetSystems, "planets");

    }

    private static ArrayList<PlanetSystem> lesFraCSVFil(File kilde) {
        ArrayList<PlanetSystem> returListe = new ArrayList<>();

        try (BufferedReader bufretLeser = new BufferedReader(new FileReader(kilde))) {
            String linje;

            while ((linje = bufretLeser.readLine()) != null) {
                String[] verdier = linje.split(";");

                PlanetSystem superhelt = new PlanetSystem(verdier[0], verdier[1]);

                returListe.add(superhelt);
            }
        }
        catch (FileNotFoundException fileNotFoundException) {
            fileNotFoundException.printStackTrace();
        }
        catch (IOException ioException) {
            ioException.printStackTrace();
        }

        return returListe;
    }


    private static void writeHashMapToCSV(HashMap<String, PlanetSystem> planetSystems, String filename) throws IOException {

        BufferedWriter writer = new BufferedWriter(new FileWriter(filename));

        for (PlanetSystem aSystem : planetSystems.values()) {
            for (Planet aPlanet : aSystem.getPlanets()) {
                writer.write(aSystem.getName() + ";" + aSystem.getPictureUrl() + ";" + aSystem.getCenterStar().getName() +
                        ";" + aSystem.getCenterStar().getRadius() + ";" + aSystem.getCenterStar().getMass() +
                        ";" + aSystem.getCenterStar().getEffectiveTemp() + ";" + aPlanet.getName() + ";" + aPlanet.getRadius() +
                        ";" + aPlanet.getMass() + ";" + aPlanet.getSemiMajorAxis() + ";" + aPlanet.getEccentricity() +
                        ";" + aPlanet.getOrbitalPeriod() + ";" + aPlanet.getCentralCelestialBody() + ";" + aPlanet.getPictureUrl());
            }
            writer.newLine();
        }
    }

    private static ArrayList<PlanetSystem> lesFraJSonFil(String filnavn) {
        ArrayList<PlanetSystem> superheltReturListe = new ArrayList<>();

        ObjectMapper objectMapper = new ObjectMapper();

        try {
            PlanetSystem[] superheltArray = objectMapper.readValue(new File(filnavn), PlanetSystem[].class);

            superheltReturListe.addAll(Arrays.asList(superheltArray));
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }


        return superheltReturListe;
    }

    private static void skrivTilJSONFil(ArrayList<PlanetSystem> planetSystems, String filename) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();

        objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File(filename), planetSystems);
    }
}
