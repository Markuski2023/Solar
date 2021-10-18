package no.hiof.markuski.oblig5.controller;

import no.hiof.markuski.oblig5.model.Category;

import io.javalin.http.Context;
import no.hiof.markuski.oblig5.repository.UniverseJSONRepository;

import java.io.IOException;
import java.util.ArrayList;

public class CategoryController {
    private UniverseJSONRepository universeJSONRepository;

    public CategoryController(UniverseJSONRepository universeJSONRepository) {
        this.universeJSONRepository = universeJSONRepository;
    }

    public void getAllCategories(Context context) {
        ArrayList<Category> categories = universeJSONRepository.getAllCategories();

        context.json(categories);
    }

    public void getCategory(Context context) throws IOException {
        String categoryName = context.pathParam("category-id");

        context.json(universeJSONRepository.getCategory(categoryName));
    }
}