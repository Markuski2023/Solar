package no.hiof.markuski.oblig5.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Star extends CelestialBody{
    private double effectiveTemp;

    public static final double SOLAR_RADIUS_IN_KM = 695700;
    public static final double SOLAR_MASS_IN_KG = 1.98892E30;

    public Star(String name, double radius, double mass, double effectiveTemp) {
        super(name, radius, mass);
        this.effectiveTemp = effectiveTemp;
    }

    public Star(String name, double radius, double mass, double effectiveTemp, String pictureUrl) {
        super(name, radius, mass, pictureUrl);
        this.effectiveTemp = effectiveTemp;
    }

    public Star() {
    }

    @JsonIgnore
    public double getRadiusInKm() {
        return getRadius() * SOLAR_RADIUS_IN_KM;
    }

    @JsonIgnore
    public double getMassInKg() {
        return getMass() * SOLAR_MASS_IN_KG;
    }

    public double getEffectiveTemp() {
        return effectiveTemp;
    }

    public void setEffectiveTemp(double effectiveTemp) {
        this.effectiveTemp = effectiveTemp;
    }

    @JsonIgnore
    public String toString() {
        return String.format("%s has a radius of %s Rsun, a mass of %s Msun and a effective temperature of %.0f K", getName(), getRadius(), getMass(), effectiveTemp);
    }
}

