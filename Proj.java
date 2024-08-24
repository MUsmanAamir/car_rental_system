import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;

class Car {
    private String carId;
    private String brand;
    private String model;
    private double basePricePerDay;
    private boolean isAvailable;

    public Car(String carId, String brand, String model, double basePricePerDay) {
        this.carId = carId;
        this.brand = brand;
        this.model = model;
        this.basePricePerDay = basePricePerDay;
        this.isAvailable = true;
    }

    public String getCarId() {
        return carId;
    }

    public String getModel() {
        return model;
    }

    public String getBrand() {
        return brand;
    }

    public double calculatePrice(double rentalDays) {
        return rentalDays * basePricePerDay;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void rentStart() {
        isAvailable = false;
    }

    public void returnCar() {
        isAvailable = true;
    }
}

class Proj {
    public static void main(String[] args) {
        System.out.println("Usman");
    }
}