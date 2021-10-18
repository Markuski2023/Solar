package no.hiof.markuski.oblig5.model;

public class Car extends Item {
    private String brand, model;
    private Double year, km;

    public Car(String name, String description, Double price, String brand,
               String model, Double year, Double km, String pictureUrl) {
        super(name, description, price, pictureUrl);
        this.brand = brand;
        this.model = model;
        this.year = year;
        this.km = km;
    }

    public Car() {
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Double getYear() {
        return year;
    }

    public void setYear(Double year) {
        this.year = year;
    }

    public Double getKm() {
        return km;
    }

    public void setKm(Double km) {
        this.km = km;
    }

    public String toString() {
        return String.format("%s", getDescription());
    }
}