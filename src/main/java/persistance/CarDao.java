package persistance;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import persistance.model.Booking;
import persistance.model.Car;

import java.awt.print.Book;
import java.util.List;

public class CarDao {
    private SessionFactory sessionFactory;

    public CarDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public Car findById(int id) {
        Session session = sessionFactory.openSession();
        Car car = session.find(Car.class, id);
        session.close();
        return car;
    }

    public List<Car> showAllCars() {
        Session session = sessionFactory.openSession();
        List<Car> list = session.createQuery("select c from Car c", Car.class).list();
        session.close();
        return list;
    }

    public void save(Car car){
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.saveOrUpdate(car);
        transaction.commit();
        session.close();
    }
}
