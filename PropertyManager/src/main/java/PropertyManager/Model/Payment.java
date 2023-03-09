package PropertyManager.model;

import javax.persistence.*;

@Entity
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne
    private Apartment apartment;

    private int amount;
    private int month;

    public Payment(){};

    public Payment(Apartment apartment, int paymentAmount, int month){
        this.apartment = apartment;
        this.amount = paymentAmount;
        this.month = month;
    }

    @Override
    public String toString(){
        return String.format("Payment[paymentId = '%d', apartmentId = '%s', paymentAmount = '%d']",
                id, apartment, amount);
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public long getId() {
        return id;
    }

    public Apartment getApartment() {
        return apartment;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setApartment(Apartment apartment) {
        this.apartment = apartment;
    }
}
