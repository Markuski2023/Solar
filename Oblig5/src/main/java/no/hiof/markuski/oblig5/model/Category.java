package no.hiof.markuski.oblig5.model;

import java.util.ArrayList;

public class Category implements Comparable<Category> {
    private String name, pictureUrl, description;
    private ArrayList<Item> items = new ArrayList<>();

    public Category(String name, String description, String pictureUrl) {
        this.name = name;
        this.description = description;
        this.pictureUrl = pictureUrl;
    }

    public Category(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public Category() {
    }

    public Item getItem(String itemName) {
        for (Item aItem : items) {
            if (aItem.getName().equalsIgnoreCase(itemName)) {
                return aItem;
            }
        }
        return null;
    }

    public void deleteItem(String itemName) {
        for (Item anItem : items) {
            if (anItem.getName().equalsIgnoreCase(itemName)) {
                items.remove(anItem);
            }
        }
    }

    public void addItem(Item anItem) {
        items.add(anItem);
    }

    public void addPlanets() {
        ArrayList<Item> item = new ArrayList<>();
    }

    public ArrayList<Item> getItems() {
        return items;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String description() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public int compareTo(Category otherCategory) {
        return this.getName().compareTo(otherCategory.getName());
    }

    // FUNCTIONS TO SORT LIST
}