package no.hiof.markuski.oblig5.repository;

import no.hiof.markuski.oblig5.model.Category;
import no.hiof.markuski.oblig5.model.Item;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public interface UniverseRepository {
    ArrayList<Category> getAllCategories();
    Category getCategory(String categoryName) throws IOException;

    ArrayList<Item> getAllItems(String planetSystemName) throws IOException;
    Item getItem(String planetSystemName, String planetName) throws IOException;

    void deleteItem(String categoryName, String itemName) throws IOException;
    void updateItem(String categoryName, String oldItemName, Item newItem) throws IOException;
    void createItem(String categoryName, String name, String description, double price, String pictureUrl) throws IOException;
}