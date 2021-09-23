package no.hiof.markuski.oblig5.controller;

import io.javalin.http.Context;
import no.hiof.markuski.oblig5.model.PlanetSystem;
import no.hiof.markuski.oblig5.repository.UniverseJSONRepository;
import no.hiof.markuski.oblig5.repository.UniverseCSVRepository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class PlanetSystemController {
    private UniverseJSONRepository universeJSONRepository;

    public PlanetSystemController(UniverseJSONRepository universeJSONRepository) {
        this.universeJSONRepository = universeJSONRepository;
    }

    public void getAllPlanetSystems(Context context) {
        ArrayList<PlanetSystem> planetSystems = universeJSONRepository.getAllPlanetSystems();

        context.json(planetSystems);
    }

    public void getPlanetSystem(Context context) throws IOException {
        String planetSystemName = context.pathParam("planet-system-id");

        context.json(universeJSONRepository.getPlanetSystem(planetSystemName));
    }
}