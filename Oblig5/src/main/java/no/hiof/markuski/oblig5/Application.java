package no.hiof.markuski.oblig5;

import io.javalin.Javalin;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import io.javalin.plugin.rendering.vue.VueComponent;

import no.hiof.markuski.oblig5.controller.CategoryController;
import no.hiof.markuski.oblig5.controller.ItemController;
import no.hiof.markuski.oblig5.repository.UniverseJSONRepository;
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

        app.before("/", ctx -> ctx.redirect("/category"));
        app.before("/api/planet-system/:planet-system-id/planets/:planet-id/delete", ctx -> ctx.redirect("/category"));

        app.get("/category", new VueComponent("planet-system-overview"));
        app.get("/category/:category-id", new VueComponent("planet-system-detail"));
        app.get("/category/:category-id/items/create", new VueComponent("planet-create"));
        app.get("/category/:category-id/items/:item-id", new VueComponent("planet-detail"));
        app.get("/category/:category-id/items/:item-id/update", new VueComponent("planet-update"));

        UniverseJSONRepository universeJSONRepository = new UniverseJSONRepository("items_4000.json");
        CategoryController categoryController = new CategoryController(universeJSONRepository);
        ItemController itemController = new ItemController(universeJSONRepository);


        app.get("api/category", new Handler() {
            @Override
            public void handle(@NotNull Context ctx) throws Exception {
                categoryController.getAllCategories(ctx);
            }

        });
        app.get("/api/categories", categoryController::getAllCategories);
        app.get("/api/planet-system/:planet-system-id", categoryController::getCategory);
        app.get("/api/planet-system/:planet-system-id/planets", itemController::getAllItems);
        app.post("/api/planet-system/:planet-system-id/planets/create", itemController::createItem);
        app.get("/api/planet-system/:planet-system-id/planets/:planet-id", itemController::getItem);
        app.get("/api/planet-system/:planet-system-id/planets/:planet-id/delete", itemController::deleteItem);
        app.post("/api/planet-system/:planet-system-id/planets/:planet-id/update", itemController::updateItem);
    }
}

