package service;

import persistance.CarDao;
import persistance.model.Booking;
import persistance.model.Car;
import service.Exception.InvalidCarException;

import java.awt.print.Book;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

public class CarService {
    private CarDao carDao;

    public CarService(CarDao carDao) {
        this.carDao = carDao;
    }

    public void allCars() {
        for (Car car : carDao.showAllCars()) {
            System.out.println(car.getId() + "-Brand: " + car.getBrand() + " model: " + car.getModel() + " type: " +
                    car.getType() + " price: " + car.getPrice());
        }
    }

    public void filterByBrand(String brand) throws InvalidCarException {
        List<Car> fullList = carDao.showAllCars();
        List<Car> list = fullList.stream().filter(car -> car.getBrand().equals(brand)).collect(Collectors.toList());
        if (list == null) {
            throw new InvalidCarException();
        } else {
            for (Car car : list) {
                System.out.println(car.getId() + "-Brand: " + car.getBrand() + " model: " + car.getModel() + " type: " + car.getType() + " price: " + car.getPrice());
            }
        }
    }

    public void filterByModel(String model) throws InvalidCarException {
        List<Car> fullList = carDao.showAllCars();
        List<Car> list = fullList.stream().filter(car -> car.getModel().equals(model)).collect(Collectors.toList());
        if (list == null) {
            throw new InvalidCarException();
        } else {
            for (Car car : list) {
                System.out.println(car.getId() + "-Brand: " + car.getBrand() + " model: " + car.getModel() + " type: " + car.getType() + " price: " + car.getPrice());
            }
        }
    }


    public void typeOfCars() {
        List<Car> fullList = carDao.showAllCars();
        Set<String> typeList = new HashSet<String>();
        for (Car car : fullList) {
            typeList.add(car.getType());
        }
        int i = 1;
        for (String str : typeList) {
            System.out.println(i + ". " + str);
            i++;
        }
    }

    public void filterByPrice(Double price) throws InvalidCarException {
        List<Car> fullList = carDao.showAllCars();
        List<Car> list = fullList.stream().filter(car -> car.getPrice() <= price).collect(Collectors.toList());
        if (list == null) {
            throw new InvalidCarException();
        } else {
            for (Car car : list) {
                System.out.println(car.getId() + "-Brand: " + car.getBrand() + " model: " + car.getModel() + " type: " + car.getType() + " price: " + car.getPrice());
            }
        }
    }

    public void filterByType(String type) throws InvalidCarException {
        List<Car> fullList = carDao.showAllCars();
        List<Car> list = fullList.stream().filter(car -> car.getType().equals(type)).collect(Collectors.toList());
        if (list == null) {
            throw new InvalidCarException();
        } else {
            for (Car car : list) {
                System.out.println(car.getId() + "-Brand: " + car.getBrand() + " model: " + car.getModel() + " type: " + car.getType() + " price: " + car.getPrice());
            }
        }
    }

    public void addCar(Car car) {
        carDao.save(car);
    }

    public void sortByPrice() {
        List<Car> list = carDao.showAllCars().stream().sorted(Comparator.comparing(Car::getPrice)).collect(Collectors.toList());
        for (Car car : list) {
            System.out.println(car.getBrand() + " " + car.getPrice());
        }
    }

    public List<Car> getAllAvabileCars(String startDate, String endDate) throws ParseException {
        Date start = new SimpleDateFormat("dd/MM/yyyy").parse(startDate);
        Date end = new SimpleDateFormat("dd/MM/yyyy").parse(endDate);
        List<Car> listOfCars = carDao.showAllCars();
        List<Car> avabileCars = new ArrayList<>();
        for (Car car : listOfCars) {
            List<Booking> listOfBooking = car.getListOfBookings();
            boolean canBeBooked = true;                                              // S(02/01/2022 - 10/01/2022
            for (Booking booking : listOfBooking) {   //    BGS      <     S             BSG(04/01/2022 - 09/01/2022)
//                if ((booking.getStart().before(start) // 04/01/2022 - 02/01/2022
//                        &&                            //      S       >      BGS
//                        start.after(booking.getEnd())) // 09/01/2022 - 09/01/2022
//                        ||                             //    BSG       <    S
//                        (booking.getStart().before(end)// 04/01/2022 - 10/01/2022
//                                &&                             //     S       >     BSG
//                                end.after(booking.getEnd()))) {// 10/01/2022 - 09/01/2022
//                    canBeBooked = false;
//                }
                if(end.after(booking.getStart()) && start.before(booking.getEnd())){
                    canBeBooked = false;
                }
            }
            if (canBeBooked) {
                avabileCars.add(car);
            }
        }
        return avabileCars;
    }

    public Car findById(int carChoose) {
        return carDao.findById(carChoose);
    }

    public void updateCar(Car car){
        car.setAvabile(false);
        carDao.save(car);
    }
}


