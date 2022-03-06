package ui;

import persistance.model.User;
import service.BookingService;
import service.CarService;
import service.Exception.InvalidUserException;
import service.Exception.InvalidUsernameException;
import service.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Ui {
    private User user;
    private final UserService userService;
    private final CarService carService;
    private final BookingService bookingService;
    private final Scanner scannerLine = new Scanner(System.in);
    private final Scanner scannerInt = new Scanner(System.in);

    public Ui(UserService userService, CarService carService, BookingService bookingService) {
        this.userService = userService;
        this.carService = carService;
        this.bookingService = bookingService;
    }

    public void RentalCarApp() {


        System.out.println("-----Rental Car App------");
        int option;
        do {
            System.out.println("1. Log In");
            System.out.println("2. Register");
            System.out.println("3. Exit");
            option = scannerInt.nextInt();

            switch (option) {
                case 1:
                    if (login()) {
                        AppCustomer appCustomer = new AppCustomer(user,carService,userService,bookingService);
                        AppAdmin appAdmin = new AppAdmin(userService, carService);
                        if(user.getAdmin()){
                            appAdmin.start();
                        }else{
                            appCustomer.start();
                        }
                    }
                    break;
                case 2:
                    register();
                    break;
            }
        } while (option != 3);
    }

    private void register() {
        System.out.println("Enter name:");
        String name = scannerLine.nextLine();
        System.out.println("Enter surname:");
        String surName = scannerLine.nextLine();
        System.out.println("Enter phonenumber:");
        Double phoneNumber = scannerInt.nextDouble();
        System.out.println("Enter email:");
        String email = scannerLine.nextLine();
        System.out.println("Enter username:");
        String userName = scannerLine.nextLine();
        System.out.println("Enter password:");
        String password = scannerLine.nextLine();
        User user = new User();
        user.setName(name);
        user.setSurName(surName);
        user.setPhoneNumber(phoneNumber);
        user.setEmail(email);
        user.setUserName(userName);
        user.setPassword(password);
        user.setAdmin(false);
        user.setDiscount(false);
        try {
            userService.addNewUser(user);
        } catch (InvalidUsernameException e) {
            System.out.println(e.getMessage());
        }
    }

    private boolean login() {
        System.out.println("Enter username:");
        String username = scannerLine.nextLine();
        System.out.println("Enter password:");
        String password = scannerLine.nextLine();
        try {
            user = userService.getUser(username,password);
            return userService.checkUser(username, password);
        } catch (InvalidUserException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }
}
