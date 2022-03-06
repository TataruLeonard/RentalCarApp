package persistance.model;

//firma masinii, modelul masinii, tipul masinii sau costul inchirierii.

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String brand;
    private String model;
    private String type;
    private Double price;
    private Boolean isAvabile;
    @OneToMany(mappedBy = "car",fetch = FetchType.EAGER)
    private List<Booking> listOfBookings;

    public List<Booking> getListOfBookings() {
        return listOfBookings;
    }

    public void setListOfBookings(List<Booking> listOfBookings) {
        this.listOfBookings = listOfBookings;
    }

    public Boolean getAvabile() {
        return isAvabile;
    }

    public void setAvabile(Boolean avabile) {
        isAvabile = avabile;
    }

    public int getId() {
        return id;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
