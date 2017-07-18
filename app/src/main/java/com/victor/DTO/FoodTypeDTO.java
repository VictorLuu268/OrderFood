package com.victor.DTO;

import java.io.Serializable;

/**
 * Created by Victor on 09/07/2017.
 */

public class FoodTypeDTO implements Serializable {
    int id;
    String name;
    String image;

    public FoodTypeDTO() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
