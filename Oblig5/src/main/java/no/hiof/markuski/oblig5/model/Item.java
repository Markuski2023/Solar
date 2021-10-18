package no.hiof.markuski.oblig5.model;

public class Item implements Comparable<Item> {
    private String name, description, pictureUrl;
    private Double price;

    public Item(String name, String description, Double price, String pictureUrl) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.pictureUrl = pictureUrl;
    }

    public Item(String name, String description, Double price) {
        this.name = name;
        this.description = description;
        this.price = price;
    }

    public Item() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    @Override
    public int compareTo(Item otherItem) {
        return this.getName().compareTo(otherItem.getName());
    }
}
