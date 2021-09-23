package no.hiof.markuski.oblig5;

import io.javalin.Javalin;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import io.javalin.plugin.rendering.vue.VueComponent;
import no.hiof.markuski.oblig5.controller.PlanetController;
import no.hiof.markuski.oblig5.controller.PlanetSystemController;
import no.hiof.markuski.oblig5.repository.UniverseJSONRepository;
import no.hiof.markuski.oblig5.repository.UniverseCSVRepository;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class Application {
    public static void main(String[] args) throws IOException {
        Javalin app = Javalin.create().start();

        app.config.enableWebjars();

        app.get("/hello-world", new Handler() {
            @Override
            public void handle(@NotNull Context ctx) throws Exception {
                ctx.result("Hello, World!");
            }
        });

        app.before("/", ctx -> ctx.redirect("/planet-system"));
        app.before("/api/planet-system/:planet-system-id/planets/:planet-id/delete", ctx -> ctx.redirect("/planet-system"));

        app.get("/planet-system", new VueComponent("planet-system-overview"));
        app.get("/planet-system/:planet-system-id", new VueComponent("planet-system-detail"));
        app.get("/planet-system/:planet-system-id/planets/create", new VueComponent("planet-create"));
        app.get("/planet-system/:planet-system-id/planets/:planet-id", new VueComponent("planet-detail"));
        app.get("/planet-system/:planet-system-id/planets/:planet-id/update", new VueComponent("planet-update"));

        UniverseJSONRepository universeJSONRepository = new UniverseJSONRepository("planets_4000.json");
        PlanetSystemController planetSystemController = new PlanetSystemController(universeJSONRepository);
        PlanetController planetController = new PlanetController(universeJSONRepository);


        app.get("api/planet-system", new Handler() {
            @Override
            public void handle(@NotNull Context ctx) throws Exception {
                planetSystemController.getAllPlanetSystems(ctx);
            }

        });
        app.get("/api/planet-system", planetSystemController::getAllPlanetSystems);
        app.get("/api/planet-system/:planet-system-id", planetSystemController::getPlanetSystem);
        app.get("/api/planet-system/:planet-system-id/planets", planetController::getAllPlanets);
        app.post("/api/planet-system/:planet-system-id/planets/create", planetController::createPlanet);
        app.get("/api/planet-system/:planet-system-id/planets/:planet-id", planetController::getPlanet);
        app.get("/api/planet-system/:planet-system-id/planets/:planet-id/delete", planetController::deletePlanet);
        app.post("/api/planet-system/:planet-system-id/planets/:planet-id/update", planetController::updatePlanet);
    }
}

