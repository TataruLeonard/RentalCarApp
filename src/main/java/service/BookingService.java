package service;

import persistance.BookingDao;
import persistance.CarDao;
import persistance.model.Booking;
import persistance.model.Car;

public class BookingService {
    private BookingDao bookingDao;
    private CarDao carDao;

    public BookingService(BookingDao bookingDao, CarDao carDao) {
        this.bookingDao = bookingDao;
        this.carDao = carDao;
    }

    public void addBookingToCar(Car car, Booking booking) {
        Booking saveBooking = booking;

    booking.setCar(car);
    bookingDao.save(booking);
    }
}
