package id.ac.ui.cs.advprog.eshop.model;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter @Setter
public class Car {
    private String carId;
    private String carName;
    private String carColor;
    private int carQuantity;

    public Car update(Car newCar){
        this.setCarName(newCar.getCarName());
        this.setCarColor(newCar.getCarColor());
        this.setCarQuantity(newCar.getCarQuantity());

        return this;
    }
}