package com.victor.DTO;

import java.io.Serializable;

/**
 * Created by Victor on 13/07/2017.
 */

public class PaymentDTO implements Serializable {
    String nameFood;
    int quantity, price;

    public PaymentDTO() {
    }

    public String getNameFood() {
        return nameFood;
    }

    public void setNameFood(String nameFood) {
        this.nameFood = nameFood;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
