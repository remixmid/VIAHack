package com.VIAHack.FridgeCheff.Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Ingredient {
    public int id;
    public double amount;
    public String unit;
    public String unitLong;
    public String unitShort;
    public String aisle;
    public String name;
    public String original;
    public String originalName;
    public String[] meta;
    public String extendedName;
    public String image;
}