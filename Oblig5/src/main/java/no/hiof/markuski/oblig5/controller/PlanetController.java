package no.hiof.markuski.oblig5.controller;

import io.javalin.http.Context;
import no.hiof.markuski.oblig5.model.*;
import no.hiof.markuski.oblig5.repository.UniverseJSONRepository;
import no.hiof.markuski.oblig5.repository.UniverseCSVRepository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class PlanetController {
    private UniverseJSONRepository universeJSONRepository;

    public PlanetController(UniverseJSONRepository universeJSONRepository) {
        this.universeJSONRepository = universeJSONRepository;
    }

    public void getAllPlanets(Context context) throws IOException {
        String planetSystemName = context.pathParam("planet-system-id");
        String sortBy = context.queryParam("sort_by");

        ArrayList<Planet> planets = universeJSONRepository.getAllPlanets(planetSystemName);

        switch (sortBy) {
            case "name" -> Collections.sort(planets);
            case "mass" -> Collections.sort(planets, new Comparator<Planet>() {
                @Override
                public int compare(Planet planet1, Planet planet2) {
                    if (planet1.getMass() > planet2.getMass())
                        return 1;
                    else if(planet1.getMass() < planet2.getMass())
                        return -1;

                    return 0;
                }
            });
            case "radius" -> Collections.sort(planets, Comparator.comparing(Planet::getRadius));
        }

        context.json(planets);
    }

    public void getPlanet(Context context) throws IOException {
        String planetSystemName = context.pathParam("planet-system-id");
        String planetName = context.pathParam("planet-id");

        context.json(universeJSONRepository.getPlanet(planetSystemName, planetName));
    }

    public void deletePlanet(Context context) throws IOException {
        String planetSystemName = context.pathParam("planet-system-id");
        String planetName = context.pathParam("planet-id");

        universeJSONRepository.deletePlanet(planetSystemName, planetName);
    }

    // Rakk ikke å implementere denne metoden inn i createPlanet
    public Planet setPlanet(Planet planet, Context context) throws IOException {
        String planetSystemName = context.pathParam("planet-system-id");

        PlanetSystem system = universeJSONRepository.getPlanetSystem(planetSystemName);

        Star celestialbody = system.getCenterStar();
        String name = context.formParam("name");
        String radius = context.formParam("radius");
        String mass = context.formParam("mass");
        String semiMajorAxis = context.formParam("semiMajorAxis");
        String eccentricity = context.formParam("eccentricity");
        String orbitalPeriod = context.formParam("orbitalPeriod");
        String pictureUrl = context.formParam("pictureUrl");

        planet.setName(name);
        planet.setRadius(Double.parseDouble(radius));
        planet.setMass(Double.parseDouble(mass));
        planet.setSemiMajorAxis(Double.parseDouble(semiMajorAxis));
        planet.setEccentricity(Double.parseDouble(eccentricity));
        planet.setOrbitalPeriod(Double.parseDouble(orbitalPeriod));
        planet.setPictureUrl(pictureUrl);
        planet.setCentralCelestialBody(celestialbody);


        return planet;
    }

    public void createPlanet(Context context) throws IOException {
        String planetSystemName = context.pathParam("planet-system-id");

        PlanetSystem system = universeJSONRepository.getPlanetSystem(planetSystemName);

        Star celestialbody = system.getCenterStar();

        String name = context.formParam("name");
        String radius1 = context.formParam("radius");
        String mass1 = context.formParam("mass");
        String semiMajorAxis1 = context.formParam("semiMajorAxis");
        String eccentricity1 = context.formParam("eccentricity");
        String orbitalPeriod1 = context.formParam("orbitalPeriod");
        String pictureUrl = context.formParam("pictureUrl");

        double radius = Double.parseDouble(radius1);
        double mass = Double.parseDouble(mass1);
        double semiMajorAxis = Double.parseDouble(semiMajorAxis1);
        double eccentricity = Double.parseDouble(eccentricity1);
        double orbitalPeriod = Double.parseDouble(orbitalPeriod1);


        universeJSONRepository.createPlanet(planetSystemName, name, radius, mass, semiMajorAxis, eccentricity, orbitalPeriod, celestialbody, pictureUrl);
        context.redirect(String.format("/planet-system/%s/", planetSystemName));
    }

    public void updatePlanet(Context context) throws IOException {
        String planetSystemName = context.pathParam("planet-system-id");
        String oldPlanetName = context.pathParam(":planet-id");

        Planet newPlanet = universeJSONRepository.getPlanet(planetSystemName, oldPlanetName);

        universeJSONRepository.updatePlanet(planetSystemName,oldPlanetName, setPlanet(newPlanet, context));
        context.redirect(String.format("/planet-system/%s/", planetSystemName));
    }
}

