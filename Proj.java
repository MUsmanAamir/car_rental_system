import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;

public class Proj {
    public static void main(String [] args) {
        Scanner scanner = new Scanner(System.in);
        RentalSystem rentalSystem = new RentalSystem();
        
        rentalSystem.registerUser(new Admin("A1", "Admin", "admin123"));
        
        while (true) {
            System.out.println("\n| Car Rental System |");
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Exit");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("Enter User ID: ");
                    String userId = scanner.nextLine();
                    System.out.print("Enter Name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter Password: ");
                    String password = scanner.nextLine();
                    System.out.print("Enter Role (Admin/Customer): ");
                    String role = scanner.nextLine();

                    if (role.equalsIgnoreCase("Admin")) {
                        rentalSystem.registerUser(new Admin(userId, name, password));
                    } else if (role.equalsIgnoreCase("Customer")) {
                        rentalSystem.registerUser(new Customer(userId, name, password));
                    } else {
                        System.out.println("Invalid role! Please enter 'Admin' or 'Customer'.");
                    }
                    break;

                case 2:
                    System.out.print("Enter User ID: ");
                    userId = scanner.nextLine();
                    System.out.print("Enter Password: ");
                    password = scanner.nextLine();

                    User user = rentalSystem.login(userId, password);
                    if (user != null) {
                        if (user.getRole().equals("Admin")) {
                            adminMenu(scanner, rentalSystem);
                        } else if (user.getRole().equals("Customer")) {
                            customerMenu(scanner, rentalSystem, (Customer) user);
                        }
                    }
                    break;

                case 3:
                    System.out.println("Exiting...");
                    scanner.close();
                    return;

                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    public static void adminMenu(Scanner scanner, RentalSystem rentalSystem) {
        while (true) {
            System.out.println("\n| Admin Menu |");
            System.out.println("1. View all rentals");
            System.out.println("2. Add new car");
            System.out.println("3. Logout");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.println("Viewing all rentals:");
                    for (Rental rental : rentalSystem.getRentals()) {
                        System.out.println("Car: " + rental.getCar().getBrand() + " " + rental.getCar().getModel() + 
                                           ", Rented by: " + rental.getCustomer().getName() + 
                                           ", Days: " + rental.getDays());
                    }
                    break;

                case 2:
                    System.out.print("Enter Car ID: ");
                    String carId = scanner.nextLine();
                    System.out.print("Enter Brand: ");
                    String brand = scanner.nextLine();
                    System.out.print("Enter Model: ");
                    String model = scanner.nextLine();
                    System.out.print("Enter Price per Day: ");
                    double price = scanner.nextDouble();

                    rentalSystem.addCar(new Car(carId, brand, model, price));
                    System.out.println("Car added successfully.");
                    break;

                case 3:
                    System.out.println("Logging out...");
                    return;

                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    public static void customerMenu(Scanner scanner, RentalSystem rentalSystem, Customer customer) {
        while (true) {
            System.out.println("\n| Customer Menu |");
            System.out.println("1. View available cars");
            System.out.println("2. Rent a car");
            System.out.println("3. Extend a rental");
            System.out.println("4. View rental history");
            System.out.println("5. Logout");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    rentalSystem.viewAvailableCars();
                    break;

                case 2:
                    System.out.println("Enter the Car Id to rent: ");
                    String carId = scanner.nextLine();
                    System.out.println("Enter the number of days to rent: ");
                    int days = scanner.nextInt();
                    rentalSystem.rentCar(carId, customer, days);
                    break;

                case 3:
                    System.out.print("Enter Car ID to extend rental: ");
                    String carIdd = scanner.nextLine();
                    System.out.print("Enter additional number of days: ");
                    int additionalDays = scanner.nextInt();
                    rentalSystem.extendRental(carIdd, additionalDays);
                    break;

                case 4:
                    rentalSystem.viewRentalHistory(customer);
                    break;

                case 5:
                    System.out.println("Logging out...");
                    return;

                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }
}

class User {
    protected String userId;
    private String name;
    private String role;
    private String password;

    public User(String userId, String name, String role, String password){
        this.userId = userId;
        this.name = name;
        this.role = role;
        this.password = password;
    }

    public String getUserId(){
        return userId;
    }

    public String getName(){
        return name;
    }

    public String getRole(){
        return role;
    }

    public String getPassword(){
        return password;
    }
}

class Customer extends User {
    public Customer(String userId, String name, String password){
        super(userId, name, "Customer", password);
    }

    public String getCustomerId(){
        return userId;
    }
}

class Admin extends User {
    public Admin(String userId, String name, String password){
        super(userId, name, "Admin", password);
    }
}

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

class Rental {
    private Car car;
    private Customer customer;
    private int days;

    public Rental(Car car, Customer customer, int days) {
        this.car = car;
        this.customer = customer;
        this.days = days;
    }

    public Car getCar() {
        return car;
    }

    public Customer getCustomer() {
        return customer;
    }

    public int getDays() {
        return days;
    }
}

class RentalSystem {
    private List<Car> cars = new ArrayList<>();
    private List<Rental> rentals = new ArrayList<>();
    private List<User> users = new ArrayList<>(); // List to store registered users

    public RentalSystem() {
        cars.add(new Car("C1", "Toyota", "Camry", 50.0));
        cars.add(new Car("C2", "Honda", "Civic", 45.0));
        cars.add(new Car("C3", "Ford", "Mustang", 70.0));
        cars.add(new Car("C4", "Audi", "E-tron", 65.0));
    }

    public void registerUser(User user) {
        users.add(user);
        System.out.println(user.getRole() + " " + user.getName() + " registered successfully.");
    }

    public User login(String userId, String password) {
        for (User user : users) {
            if (user.getUserId().equals(userId) && user.getPassword().equals(password)) {
                System.out.println(user.getRole() + " " + user.getName() + " logged in successfully.");
                return user;
            }
        }
        System.out.println("Invalid user ID or password.");
        return null;
    }

    public void viewAvailableCars() {
        System.out.println("Available Cars:");
        for (Car car : cars) {
            if (car.isAvailable()) {
                System.out.println("ID: " + car.getCarId() + ", Brand: " + car.getBrand() + ", Model: " + car.getModel()
                        + ", Price/Day: $" + car.calculatePrice(1));
            }
        }
    }

    public void rentCar(String carId, Customer customer, int days) {
        for (Car car : cars) {
            if (car.getCarId().equals(carId) && car.isAvailable()) {
                car.rentStart();
                Rental rental = new Rental(car, customer, days);
                rentals.add(rental);
                System.out.println(customer.getName() + " has rented the " + car.getBrand() + " " + car.getModel()
                        + " for " + days + " days.");
                return;
            }
        }
        System.out.println("Car with ID " + carId + " is not available.");
    }

    public void extendRental(String carId, int additionalDays) {
        for (Rental rental : rentals) {
            if (rental.getCar().getCarId().equals(carId)) {
                int newDays = rental.getDays() + additionalDays;
                System.out.println("Rental for car " + rental.getCar().getBrand() + " " + rental.getCar().getModel()
                        + " extended by " + additionalDays + " days. Total rental days: " + newDays);
                return;
            }
        }
        System.out.println("No rental found for car with ID " + carId);
    }

    public void viewRentalHistory(Customer customer) {
        System.out.println("Rental History for " + customer.getName() + ":");
        for (Rental rental : rentals) {
            if (rental.getCustomer().getCustomerId().equals(customer.getUserId())) {
                System.out.println("Car: " + rental.getCar().getBrand() + " " + rental.getCar().getModel()
                        + ", Days Rented: " + rental.getDays());
            }
        }
    }

    public void addCar(Car car) {
        cars.add(car);
    }

    public List<Rental> getRentals() {
        return rentals;
    }
}
