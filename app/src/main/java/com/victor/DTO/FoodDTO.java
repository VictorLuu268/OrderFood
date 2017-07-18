package com.victor.DTO;

import java.io.Serializable;

/**
 * Created by Victor on 09/07/2017.
 */

public class FoodDTO implements Serializable {
    int id, id_foodType;
    String name;
    String price;
    String image;

    public FoodDTO() {
    }

    public FoodDTO(int id, int id_foodType, String name, String price, String image) {
        this.id = id;
        this.id_foodType = id_foodType;
        this.name = name;
        this.price = price;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_foodType() {
        return id_foodType;
    }

    public void setId_foodType(int id_foodType) {
        this.id_foodType = id_foodType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
