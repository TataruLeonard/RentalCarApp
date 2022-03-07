package ui;

import persistance.model.Booking;
import persistance.model.Car;
import persistance.model.User;
import service.BookingService;
import service.CarService;
import service.Exception.InvalidCarException;
import service.UserService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class AppCustomer {
    private User user;
    private CarService carService;
    private UserService userService;
    private BookingService bookingService;
    private final Scanner scannerLine = new Scanner(System.in);
    private final Scanner scannerInt = new Scanner(System.in);

    public AppCustomer(User user, CarService carService, UserService userService, BookingService bookingService) {
        this.user = user;
        this.carService = carService;
        this.userService = userService;
        this.bookingService = bookingService;
    }

    public void start() {
        int option;
        do {
            System.out.println("---Meniu---");
            System.out.println("1. Show all cars");
            System.out.println("2. Filter by:");
            System.out.println("3. Sort by price:");
            System.out.println("4. Rent car:");

            option = scannerInt.nextInt();

            switch (option) {
                case 1:
                    showAllCars();
                    break;
                case 2:
                    filtrare();
                    break;
                case 3:
                    filtrarePret();
                    break;
                case 4:
                    hireCar();
                    break;
            }
        } while (option != 0);
    }

    private void hireCar() {
        System.out.println("Enter start date:");
        String startDate = scannerLine.nextLine();
        System.out.println("Enter end date:");
        String endDate = scannerLine.nextLine();
        System.out.println("You want to see out promotiona pachet:(y/n)");
        String option = scannerLine.nextLine();
        double totalPay = 0;
        if (option.equalsIgnoreCase("y")) {
            System.out.println("You choosed a promotiona pachet so for next rent u will get a disconout of 10% if the " +
                    "rent time its more than 3 days.");
            userService.setDiscount(user);
            try {
                for (Car car : carService.getAllAvabileCars(startDate, endDate)) {
                    System.out.println(car.getId() + "." + car.getBrand() + " " + car.getModel() + " " + car.getPrice());
                }
                System.out.println("Choose a car:");
                int carChoose = scannerInt.nextInt();
                Car car = carService.findById(carChoose);
                Booking booking = new Booking();
                Date start = new SimpleDateFormat("dd/MM/yyyy").parse(startDate);
                Date end = new SimpleDateFormat("dd/MM/yyyy").parse(endDate);
                booking.setStart(start);
                booking.setEnd(end);
                bookingService.addBookingToCar(car, booking);
                int calculateEnd = Integer.parseInt(endDate.substring(0, 2));
                int calculateStart = Integer.parseInt(startDate.substring(0, 2));
                int daysRent = calculateEnd - calculateStart;
                //todo nu imi face scaderea de 10% pentru clientii care au discount = true
                totalPay = car.getPrice() * daysRent;
                if (user.getDiscount()) {
                    double discount = 10 / 100 * totalPay;
                    totalPay = totalPay - discount;
                    userService.deleteDiscount(user);
                }
                System.out.println("You have to pay: " + totalPay + " for " + daysRent + " days.");
            } catch (ParseException e) {
                System.out.println("No car avabile.");
            }
        } else if (option.equalsIgnoreCase("n")) {
            try {
                for (Car car : carService.getAllAvabileCars(startDate, endDate)) {
                    System.out.println(car.getId() + "." + car.getBrand() + " " + car.getModel() + " " + car.getPrice());
                }
                System.out.println("Choose a car:");
                int carChoose = scannerInt.nextInt();
                Car car = carService.findById(carChoose);
                carService.updateCar(car);
                Booking booking = new Booking();
                Date start = new SimpleDateFormat("dd/MM/yyyy").parse(startDate);
                Date end = new SimpleDateFormat("dd/MM/yyyy").parse(endDate);
                booking.setStart(start);
                booking.setEnd(end);
                bookingService.addBookingToCar(car, booking);
                int calculateEnd = Integer.parseInt(endDate.substring(0, 2));
                int calculateStart = Integer.parseInt(startDate.substring(0, 2));
                int daysRent = calculateEnd - calculateStart;
                totalPay = car.getPrice() * daysRent;
                System.out.println("You have to pay: " + totalPay + " for " + daysRent + " days.");
            } catch (ParseException e) {
                System.out.println("No car avabile.");
            }
        }
        System.out.println("You want to rent another car: (y/n)");
        String rentAnotherCar = scannerLine.nextLine();
        Double pay = 0.0;
        if (rentAnotherCar.equalsIgnoreCase("n")) {
//          Double pay = 0.0;
//          while(pay == totalPay){
//              System.out.println("You have to pay: " + totalPay);
//              System.out.println("Enter the payment:");
//              pay = scannerInt.nextDouble();
//              if(pay < totalPay){
//                  System.out.println("You have to enter the rest of the payment:");
//                  pay += scannerInt.nextDouble();
//              }
//          }
//            System.out.println("Thx for rent cars from us.");

            do {
                System.out.println("You have to pay: " + totalPay + " u payed: " + pay);
                System.out.println("Enter the payment:");
                pay = scannerInt.nextDouble();
                if (pay < totalPay) {
                    System.out.println("You have to enter the rest of the payment:" + (totalPay - pay));
                    pay += scannerInt.nextDouble();
                }
            } while (pay <= totalPay);
            System.out.println("Thx for rent cars from us.");
        }


    }

    private void filtrarePret() {
        System.out.println("Sort by price:");
        carService.sortByPrice();
    }

    private void filtrare() {
        int option;
        do {
            System.out.println("1. Brand");
            System.out.println("2. Model");
            System.out.println("3. Type");
            System.out.println("4. Price");
            System.out.println("5. Exit");

            option = scannerInt.nextInt();
            switch (option) {
                case 1:
                    filtruBrand();
                    break;
                case 2:
                    filtruModel();
                    break;
                case 3:
                    filtruType();
                    break;
                case 4:
                    filtruPrice();
                    break;
            }
        } while (option != 5);
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
        System.out.println("Choose a type (wrote the type of car u want)");
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
