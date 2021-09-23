package no.hiof.markuski.oblig5.model;


import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;

public class PlanetSystem implements Comparable<PlanetSystem> {
    private String name, pictureUrl;
    private Star centerStar;
    private ArrayList<Planet> planets = new ArrayList<>();

    public PlanetSystem(String name, Star centerStar, String pictureUrl) {
        this.name = name;
        this.centerStar = centerStar;
        this.pictureUrl = pictureUrl;
    }

    public PlanetSystem(String name, String pictureUrl) {
        this.name = name;
        this.pictureUrl = pictureUrl;
    }

    public PlanetSystem() {
    }

    public Planet getPlanet(String planetName) {
        for (Planet aPlanet : planets ) {
            if (aPlanet.getName().equalsIgnoreCase(planetName)) {
                return aPlanet;
            }
        }
        return null;
    }

    public void deletePlanet(String planetName) {
        for (Planet aPlanet : planets) {
            if (aPlanet.getName().equalsIgnoreCase(planetName)) {
                planets.remove(aPlanet);
                    }
                }
            }

    @JsonIgnore
    public Planet getSmallestPlanet() {
        if (planets.size() == 0)
            return null;

        Planet smallestPlanet = planets.get(0);

        for (Planet currentPlanet : planets) {
            if (currentPlanet.getRadius() < smallestPlanet.getRadius()) {
                smallestPlanet = currentPlanet;
            }
            else if (currentPlanet.getRadius() == smallestPlanet.getRadius() &&
                    currentPlanet.getMass() < smallestPlanet.getMass()) {
                smallestPlanet = currentPlanet;
            }
        }

        return smallestPlanet;
    }

    @JsonIgnore
    public Planet getLargestPlanet() {
        if (planets.size() == 0)
            return null;

        Planet largestPlanet = planets.get(0);

        for (Planet currentPlanet : planets) {
            if (currentPlanet.getRadius() > largestPlanet.getRadius()) {
                largestPlanet = currentPlanet;
            }
            else if (currentPlanet.getRadius() == largestPlanet.getRadius() &&
                    currentPlanet.getMass() > largestPlanet.getMass()) {
                largestPlanet = currentPlanet;
            }
        }

        return largestPlanet;
    }

    public void addPlanet(Planet aPlanet) {
        planets.add(aPlanet);
    }

    public void addPlanets() {
        ArrayList<Planet> planet = new ArrayList<>();

    }

    public ArrayList<Planet> getPlanets() {
        return planets;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Star getCenterStar() {
        return centerStar;
    }

    public void setCenterStar(Star centerStar) {
        this.centerStar = centerStar;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    // Object.java
    @Override
    public String toString() {
        return name;
    }

    @Override
    public int compareTo(PlanetSystem otherPlanetSystem) {
        return this.getName().compareTo(otherPlanetSystem.getName());
    }
}
