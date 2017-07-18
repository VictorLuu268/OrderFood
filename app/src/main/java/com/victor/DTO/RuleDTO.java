package com.victor.DTO;

import java.io.Serializable;

/**
 * Created by Victor on 15/07/2017.
 */

public class RuleDTO implements Serializable {
    int id;
    String name;

    public RuleDTO() {
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
}
