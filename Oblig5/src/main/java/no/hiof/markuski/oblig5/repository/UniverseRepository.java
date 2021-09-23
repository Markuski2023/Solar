package no.hiof.markuski.oblig5.repository;

import no.hiof.markuski.oblig5.model.CelestialBody;
import no.hiof.markuski.oblig5.model.Planet;
import no.hiof.markuski.oblig5.model.PlanetSystem;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public interface UniverseRepository {
    ArrayList<PlanetSystem> getAllPlanetSystems();
    PlanetSystem getPlanetSystem(String planetSystemName) throws IOException;

    ArrayList<Planet> getAllPlanets(String planetSystemName) throws IOException;
    Planet getPlanet(String planetSystemName, String planetName) throws IOException;

    void deletePlanet(String planetSystemName, String planetName) throws IOException;
    void updatePlanet(String planetSystemName, String oldPlanetName, Planet newPlanet) throws IOException;
    void createPlanet(String planetSystemName, String name, double radius, double mass, double semiMajorAxis, double eccentricity, double orbitalPeriod, CelestialBody celestialBody, String pictureUrl) throws IOException;
}