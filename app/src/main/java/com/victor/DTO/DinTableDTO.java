package com.victor.DTO;

import java.io.Serializable;

/**
 * Created by Victor on 09/07/2017.
 */

public class DinTableDTO implements Serializable {

    int id;
    String name;
    boolean isChoose;

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

    public boolean isChoose() {
        return isChoose;
    }

    public void setChoose(boolean choose) {
        isChoose = choose;
    }

    public DinTableDTO() {
    }

    public DinTableDTO(int id, String name, boolean isChoose) {
        this.id = id;
        this.name = name;
        this.isChoose = isChoose;
    }
}
