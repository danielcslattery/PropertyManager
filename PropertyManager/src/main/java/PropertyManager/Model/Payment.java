package PropertyManager.Model;

import javax.persistence.*;

@Entity
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private long apartmentId;
    private int amount;
    private int month;

    public Payment(){};

    public Payment(long apartmentId, int paymentAmount, int month){
        this.apartmentId = apartmentId;
        this.amount = paymentAmount;
        this.month = month;
    }

    @Override
    public String toString(){
        return String.format("Payment[paymentId = '%d', apartmentId = '%s', paymentAmount = '%d']",
                id, apartmentId, amount);
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

    public long getApartmentId() {
        return apartmentId;
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

    public void setApartmentId(long apartmentId) {
        this.apartmentId = apartmentId;
    }
}
