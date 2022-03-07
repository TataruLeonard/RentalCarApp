import persistance.BookingDao;
import persistance.CarDao;
import persistance.UserDao;
import service.BookingService;
import service.CarService;
import service.UserService;
import ui.Ui;

public class Main {
    public static void main(String[] args) {
        HibernateConfig hibernateConfig = new HibernateConfig();

        UserDao userDao = new UserDao(hibernateConfig.getSessionFactory());
        CarDao carDao = new CarDao(hibernateConfig.getSessionFactory());
        BookingDao bookingDao = new BookingDao(hibernateConfig.getSessionFactory());

        UserService userService = new UserService(userDao);
        CarService carService = new CarService(carDao);
        BookingService bookingService = new BookingService(bookingDao,carDao);

        Ui ui = new Ui(userService,carService,bookingService);

        ui.RentalCarApp();
    }
}
