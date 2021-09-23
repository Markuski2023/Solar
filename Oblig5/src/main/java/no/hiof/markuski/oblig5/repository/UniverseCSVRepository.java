package no.hiof.markuski.oblig5.repository;



import no.hiof.markuski.oblig5.model.CelestialBody;
import no.hiof.markuski.oblig5.model.Planet;
import no.hiof.markuski.oblig5.model.PlanetSystem;
import no.hiof.markuski.oblig5.model.Star;


import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

public class UniverseCSVRepository implements UniverseRepository{
    private HashMap<String, PlanetSystem> planetSystemHashMap = new HashMap<>();
    private String filename;

    public UniverseCSVRepository(String filename) throws IOException {
        this.filename = filename;
        readFromCSV(filename);
    }

    public HashMap<String, PlanetSystem> readFromCSV(String filename) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;

            while ((line = reader.readLine()) != null) {
                String[] values = line.split(";");

                Star aStar = new Star(values[2], Double.parseDouble(values[3]), Double.parseDouble(values[4]), Double.parseDouble(values[5]), values[6]);
                Planet aPlanet = new Planet(values[7], Double.parseDouble(values[8]), Double.parseDouble(values[9]), Double.parseDouble(values[10]),
                        Double.parseDouble(values[11]), Double.parseDouble(values[12]), aStar, values[13]);
                PlanetSystem aSystem = new PlanetSystem(values[0], aStar, values[1]);

                if (!planetSystemHashMap.containsKey(aSystem.getName())) {
                    planetSystemHashMap.put(aSystem.getName(), aSystem);
                } else if (planetSystemHashMap.containsKey(aSystem.getName())) {
                    aSystem.addPlanet(aPlanet);
                    planetSystemHashMap.put(aSystem.getName(), aSystem);
                }
            }
        }
        return planetSystemHashMap;
    }

        private void writeHashMapToCSV(HashMap<String, PlanetSystem> planetSystems, String filename) throws IOException {

            BufferedWriter writer = new BufferedWriter(new FileWriter(filename));

            for (PlanetSystem aSystem : planetSystemHashMap.values()) {
                for (Planet aPlanet : aSystem.getPlanets()) {
                    writer.write(aSystem.getName() + ";" + aSystem.getPictureUrl() + ";" + aSystem.getCenterStar().getName() +
                            ";" + aSystem.getCenterStar().getRadius() + ";" + aSystem.getCenterStar().getMass() +
                            ";" + aSystem.getCenterStar().getEffectiveTemp() + ";" + aPlanet.getName() + ";" + aPlanet.getRadius() +
                            ";" + aPlanet.getMass() + ";" + aPlanet.getSemiMajorAxis() + ";" + aPlanet.getEccentricity() +
                            ";" + aPlanet.getOrbitalPeriod() + ";" + aPlanet.getCentralCelestialBody() + ";" + aPlanet.getPictureUrl());
                    writer.newLine();
                }
            }
    }

    @Override
    public ArrayList<Planet> getAllPlanets(String planetSystemName) throws IOException {
        PlanetSystem planetSystem = getPlanetSystem(planetSystemName);

        if (planetSystem != null) {
            return planetSystem.getPlanets();
        }
        return null;
    }

    @Override
    public Planet getPlanet(String planetSystemName, String planetName) throws IOException {
        PlanetSystem planetSystem = getPlanetSystem(planetSystemName);

        if (planetSystem != null)
            return planetSystem.getPlanet(planetName);

        return null;
    }

    @Override
    public ArrayList<PlanetSystem> getAllPlanetSystems() {
        return new ArrayList<>(planetSystemHashMap.values());
    }

    @Override
    public PlanetSystem getPlanetSystem(String planetSystemName) throws IOException {
        HashMap<String, PlanetSystem> listFromJSON = readFromCSV(filename);

        for (PlanetSystem aPlanetSystem : listFromJSON.values()) {
            if (aPlanetSystem.getName().equalsIgnoreCase(planetSystemName))
                return aPlanetSystem;
        }
        return null;
    }

    // sletter en gitt planet ut ifra planetsystem navn og planet navn
    @Override
    public void deletePlanet(String planetSystemName, String planetName) throws IOException {
        HashMap<String, PlanetSystem> aHashMap = planetSystemHashMap;
        for (PlanetSystem aSystem : aHashMap.values()) {
            if (aSystem.getName().equalsIgnoreCase(planetSystemName)) {
                aSystem.deletePlanet(planetName);
            }
            writeHashMapToCSV(aHashMap, "planets_100.json");
        }
    }

    @Override
    public void updatePlanet(String planetSystemName, String oldPlanetName, Planet newPlanet) throws IOException {
        ArrayList<Planet> planets = planetSystemHashMap.get(planetSystemName).getPlanets();
        for (int i = 0; i < planets.size(); i++) {
            if (planets.get(i).getName().equals(oldPlanetName)) {
                planets.set(i, newPlanet);
            }
        }
        writeHashMapToCSV(planetSystemHashMap, "planets_100.json");
    }

    // legger til en ny planet i et gitt planetsystem
    @Override
    public void createPlanet(String planetSystemName, String name, double radius, double mass, double semiMajorAxis, double eccentricity, double orbitalPeriod, CelestialBody celestialBody, String pictureUrl) throws IOException {
        HashMap<String, PlanetSystem> aHashMap = planetSystemHashMap;
        for (PlanetSystem aSystem : aHashMap.values()) {
            if (aSystem.getName().equalsIgnoreCase(planetSystemName)) {
                aSystem.addPlanet(new Planet(name, radius, mass, semiMajorAxis, eccentricity, orbitalPeriod, aSystem.getCenterStar(), pictureUrl));
                aHashMap.put(aSystem.getName(), aSystem);
            }
            writeHashMapToCSV(aHashMap, "planets_100.json");
        }
    }
}
