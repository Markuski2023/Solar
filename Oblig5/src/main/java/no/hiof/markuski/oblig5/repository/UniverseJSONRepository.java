package no.hiof.markuski.oblig5.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import no.hiof.markuski.oblig5.model.CelestialBody;
import no.hiof.markuski.oblig5.model.Planet;
import no.hiof.markuski.oblig5.model.PlanetSystem;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class UniverseJSONRepository implements UniverseRepository, Runnable {
    private HashMap<String, PlanetSystem> planetSystemHashMap = new HashMap<>();
    private String filename;

    public UniverseJSONRepository(String filename) throws IOException {
        this.filename = filename;
        readFromJSON(filename);
    }

    // returnerer alle planetsystemene i gitt JSON fil
    public ArrayList<PlanetSystem> readFromJSON(String filename) throws IOException {
        ObjectMapper mapper = new ObjectMapper();

        ArrayList<PlanetSystem> planetSystemsReturnList = new ArrayList<>();

        PlanetSystem[] planetSystems = mapper.readValue(new File(filename), PlanetSystem[].class);
        planetSystemsReturnList.addAll(Arrays.asList(planetSystems));

        for (PlanetSystem aSystem : planetSystems) {
            planetSystemHashMap.put(aSystem.getName(), aSystem);
        }
        return planetSystemsReturnList;
    }

    // skriver en HashMap til JSON filformat
    public void writeHashMapToJson(HashMap<String, PlanetSystem> planetSystems, String filename) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();

        objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File(filename), planetSystems.values());
    }

    @Override
    public ArrayList<PlanetSystem> getAllPlanetSystems() {
       return new ArrayList<>(planetSystemHashMap.values());
    }

    // henter et gitt planetsystem
    @Override
    public PlanetSystem getPlanetSystem(String planetSystemName) throws IOException {
        return planetSystemHashMap.get(planetSystemName);

        // hente fra hashmap direkte
    }

    // henter alle planeter fra et gitt Planetsystem
    @Override
    public ArrayList<Planet> getAllPlanets(String planetSystemName) throws IOException {
        PlanetSystem planetSystem = getPlanetSystem(planetSystemName);

        if (planetSystem != null) {
            return planetSystem.getPlanets();
        }
        return null;
    }

    // henter en spesifisert planet fra et spesifisert planetsystem
    @Override
    public Planet getPlanet(String planetSystemName, String planetName) throws IOException {
        PlanetSystem planetSystem = getPlanetSystem(planetSystemName);

        if (planetSystem != null)
            return planetSystem.getPlanet(planetName);

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
            writeHashMapToJson(aHashMap, "planets_100.json");
        }
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
            writeHashMapToJson(aHashMap, "planets_100.json");
        }
    }


    // oppdaterer informasjon om gitt planet
    @Override
    public void updatePlanet(String planetSystemName, String oldPlanetName, Planet newPlanet) throws IOException {
        ArrayList<Planet> planets = planetSystemHashMap.get(planetSystemName).getPlanets();
        for (int i = 0; i < planets.size(); i++) {
            if (planets.get(i).getName().equals(oldPlanetName)) {
                planets.set(i, newPlanet);
            }
        }
            writeHashMapToJson(planetSystemHashMap, "planets_100.json");
    }

    @Override
    public void run() {

    }
}
