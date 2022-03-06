import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;
import persistance.model.Booking;
import persistance.model.Car;
import persistance.model.User;

import java.util.Properties;

public class HibernateConfig {
    private SessionFactory sessionFactory;

    public HibernateConfig() {
        Configuration configuration = new Configuration();
        Properties properties = new Properties();
        properties.put(Environment.URL, "jdbc:mysql://localhost:3306/rental_car_bogdan");
        properties.put(Environment.USER, "test");
        properties.put(Environment.PASS, "test");
        properties.put(Environment.DRIVER, "com.mysql.jdbc.Driver");
        properties.put(Environment.HBM2DDL_AUTO, "update");

        configuration.addAnnotatedClass(User.class);
        configuration.addAnnotatedClass(Car.class);
        configuration.addAnnotatedClass(Booking.class);

        properties.put(Environment.SHOW_SQL, true);

        configuration.setProperties(properties);

        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(properties).build();
        sessionFactory = configuration.buildSessionFactory(serviceRegistry);
    }

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}
