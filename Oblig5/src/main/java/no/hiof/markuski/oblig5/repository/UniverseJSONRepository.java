package no.hiof.markuski.oblig5.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import no.hiof.markuski.oblig5.model.Car;
import no.hiof.markuski.oblig5.model.Category;
import no.hiof.markuski.oblig5.model.Item;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class UniverseJSONRepository implements UniverseRepository, Runnable {
    private HashMap<String, Category> categoryHashMap = new HashMap<>();
    private String filename;

    public UniverseJSONRepository(String filename) throws IOException {
        this.filename = filename;
        readFromJSON(filename);
    }

    public ArrayList<Category> readFromJSON(String filename) throws IOException {
        ObjectMapper mapper = new ObjectMapper();

        ArrayList<Category> categoryReturnList = new ArrayList<>();

        Category[] categories = mapper.readValue(new File(filename), Category[].class);
        categoryReturnList.addAll(Arrays.asList(categories));

        for (Category aCategory : categories) {
            categoryHashMap.put(aCategory.getName(), aCategory);
        }
        return categoryReturnList;
    }

    public void writeHashMapToJSON(HashMap<String, Category> categories, String filename) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File(filename), categories.values());
    }

    @Override
    public ArrayList<Category> getAllCategories() {
        return new ArrayList<>(categoryHashMap.values());
    }

    @Override
    public Category getCategory(String categoryName) throws IOException {
        return categoryHashMap.get(categoryName);
    }

    @Override
    public ArrayList<Item> getAllItems(String categoryName) throws IOException {
        Category category = getCategory(categoryName);

        if (category != null) {
            return category.getItems();
        }
        return null;
    }

    @Override
    public Item getItem(String categoryName, String itemName) throws IOException {
        Category category = getCategory(categoryName);

        if (category != null)
            return category.getItem(itemName);
        return null;
    }

    // sletter en gitt planet ut ifra planetsystem navn og planet navn
    @Override
    public void deleteItem(String categoryName, String itemName) throws IOException {
        HashMap<String, Category> aHashMap = categoryHashMap;
        for (Category aCategory : aHashMap.values()) {
            if (aCategory.getName().equalsIgnoreCase(categoryName)) {
                aCategory.deleteItem(itemName);
            }
            writeHashMapToJSON(aHashMap, "planets_100.json");
        }
    }

    // legger til en ny planet i et gitt planetsystem
    @Override
    public void createItem(String categoryName, String name, String description, double price, String pictureUrl) throws IOException {

        HashMap<String, Category> aHashMap = categoryHashMap;
        for (Category aCategory : aHashMap.values()) {
            if (aCategory.getName().equalsIgnoreCase(categoryName)) {
                aCategory.addItem(new Item(name, description, price, pictureUrl));
                aHashMap.put(aCategory.getName(), aCategory);
            }
            writeHashMapToJSON(aHashMap, "planets_100.json");
        }
    }


    // oppdaterer informasjon om gitt planet
    @Override
    public void updateItem(String categoryName, String oldItemName, Item newItem) throws IOException {
        ArrayList<Item> items = categoryHashMap.get(categoryName).getItems();
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).getName().equals(oldItemName)) {
                items.set(i, newItem);
            }
        }
        writeHashMapToJSON(categoryHashMap, "planets_100.json");
    }

    @Override
    public void run() {

    }
}