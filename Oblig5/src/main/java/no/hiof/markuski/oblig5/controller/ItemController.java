package no.hiof.markuski.oblig5.controller;

import no.hiof.markuski.oblig5.model.Category;
import no.hiof.markuski.oblig5.model.Item;
import no.hiof.markuski.oblig5.repository.UniverseJSONRepository;

import io.javalin.http.Context;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class ItemController {
    private UniverseJSONRepository universeJSONRepository;

    public ItemController(UniverseJSONRepository universeJSONRepository) {
        this.universeJSONRepository = universeJSONRepository;
    }

    public void getAllItems(Context context) throws IOException {
        String categoryName = context.pathParam("category-id");
        String sortBy = context.pathParam("sort-by");

        ArrayList<Item> items = universeJSONRepository.getAllItems(categoryName);

        switch (sortBy) {
            case "name" -> Collections.sort(items);
            case "price" -> Collections.sort(items, new Comparator<Item>() {
                @Override
                public int compare(Item item1, Item item2) {
                    if (item1.getPrice() > item2.getPrice()) {
                        return 1;
                    }
                    else if (item1.getPrice() < item2.getPrice()) {
                        return -1;
                    }
                    return 0;
                }
            });
        }
        context.json(items);
    }

    public void getItem(Context context) throws IOException {
        String categoryName = context.pathParam("category-id");
        String itemName = context.pathParam("item-id");

        context.json(universeJSONRepository.getItem(categoryName, itemName));
    }

    public void deleteItem(Context context) throws IOException {
        String categoryName = context.pathParam("category--id");
        String itemName = context.pathParam("item-id");

        universeJSONRepository.deleteItem(categoryName, itemName);
    }

    public Item setItem(Item item, Context context) throws IOException {
        String categoryName = context.pathParam("category-id");

        Category category = universeJSONRepository.getCategory(categoryName);

        String name = context.formParam("name");
        String description = context.formParam("description");
        String price1 = context.formParam("price");
        String pictureUrl = context.formParam("pictureUrl");

        double price = Double.parseDouble(price1);

        item.setName(name);
        item.setDescription(description);
        item.setPrice(price);
        item.setPictureUrl(pictureUrl);


        return item;
    }

    public void createItem(Context context) throws IOException {
        String categoryName = context.pathParam("category-id");

        Category category = universeJSONRepository.getCategory(categoryName);

        String name = context.formParam("name");
        String description = context.formParam("description");
        String price1 = context.formParam("price");
        String pictureUrl = context.formParam("pictureUrl");

        double price = Double.parseDouble(price1);


        universeJSONRepository.createItem(categoryName, name, description, price, pictureUrl);
        context.redirect(String.format("/category/%s/", categoryName));
    }

    public void updateItem(Context context) throws IOException {
        String categoryName = context.pathParam("category-id");
        String oldItemName = context.pathParam(":item-id");

        Item newItem = universeJSONRepository.getItem(categoryName, oldItemName);

        universeJSONRepository.updateItem(categoryName,oldItemName, setItem(newItem, context));
        context.redirect(String.format("/category/%s/", categoryName));
    }
}