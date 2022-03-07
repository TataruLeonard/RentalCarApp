package ui;

import persistance.model.Car;
import persistance.model.User;
import service.CarService;
import service.Exception.InvalidCarException;
import service.UserService;

import java.util.Scanner;

public class AppAdmin {
    private UserService userService;
    private CarService carService;
    private final Scanner scannerLine = new Scanner(System.in);
    private final Scanner scannerInt = new Scanner(System.in);

    public AppAdmin(UserService userService, CarService carService) {
        this.userService = userService;
        this.carService = carService;
    }

    public void start() {
        int option;
        do {
            System.out.println("1. Show all cars");
            System.out.println("2. Filtru dupa:");
            System.out.println("3. Add cars:");
            System.out.println("4. Make users admin.");

            option = scannerInt.nextInt();

            switch (option) {
                case 1:
                    showAllCars();
                    break;
                case 2:filtrare();
                    break;
                case 3:addCars();
                    break;
                case 4:makeUserAdmin();
                    break;
            }
        } while (option != 0);
    }

    private void makeUserAdmin() {
        userService.getAllUser();
        System.out.println("Choose user:");
        String chooseUser = scannerLine.nextLine();
       userService.makeUserAdmin(chooseUser);
    }

    private void addCars() {
        System.out.println("Add car.");
        System.out.println("Enter the brand:");
        String carBrand = scannerLine.nextLine();
        System.out.println("Enter the model:");
        String carModel = scannerLine.nextLine();
        System.out.println("Enter car type:");
        String carType = scannerLine.nextLine();
        System.out.println("Enter price:");
        Double carPrice = scannerInt.nextDouble();
        Car car = new Car();
        car.setBrand(carBrand);
        car.setModel(carModel);
        car.setType(carType);
        car.setPrice(carPrice);
        car.setAvabile(true);
        carService.addCar(car);
    }

    private void filtrare() {
        int option;
        do{
            System.out.println("1. Brand");
            System.out.println("2. Model");
            System.out.println("3. Type");
            System.out.println("4. Price");
            System.out.println("5. Exit");

            option = scannerInt.nextInt();
            switch (option){
                case 1:filtruBrand();
                    break;
                case 2:filtruModel();
                    break;
                case 3:filtruType();
                    break;
                case 4:filtruPrice();
                    break;
            }
        }while (option != 5);
    }

    private void filtruPrice() {
        System.out.println("Enter the max price:");
        Double price = scannerInt.nextDouble();
        try {
            carService.filterByPrice(price);
        } catch (InvalidCarException e) {
            System.out.println(e.getMessage());
        }
    }

    private void filtruType() {
        System.out.println("This are all types of cars we have:");
        carService.typeOfCars();
        System.out.println("Choose a type (write the type u have choosed)");
        String type = scannerLine.nextLine();
        try {
            carService.filterByType(type);
        } catch (InvalidCarException e) {
            System.out.println(e.getMessage());
        }
    }

    private void filtruModel() {
        System.out.println("Enter model:");
        String model = scannerLine.nextLine();
        try {
            carService.filterByModel(model);
        } catch (InvalidCarException e) {
            System.out.println(e.getMessage());
        }
    }

    private void filtruBrand() {
        System.out.println("Enter brand:");
        String brand = scannerLine.nextLine();
        try {
            carService.filterByBrand(brand);
        } catch (InvalidCarException e) {
            System.out.println(e.getMessage());
        }
    }

    private void showAllCars() {
        carService.allCars();
    }
}
