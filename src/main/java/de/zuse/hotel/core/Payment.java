package de.zuse.hotel.core;

import de.zuse.hotel.util.ZuseCore;

import javax.persistence.Column;
import javax.persistence.*;
import javax.persistence.Id;
import java.time.LocalDate;

@Entity
@Table(name = "Payments")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JoinColumn(name = "payment_id", referencedColumnName = "Booking_id")
    public int paymentID;

    public enum Status
    {
        PAID, NOT_PAID
    }

    public enum Type
    {
        CASH, CREDIT_CARD, DEBIT_CARD, MOBILE_PAYMENT
    }

    public LocalDate date;
    public Status status;
    public Type type;
    public float price;
    private static float TAX = 10.0f;//TODO

    public Payment(LocalDate date, Status status, Type type, float price)
    {
        ZuseCore.check(price >= 0.0f, "price must be >= 0");

        this.date = date;
        this.status = status;
        this.type = type;
        this.price = price;
    }

    public Payment()
    {
        this(null, Status.NOT_PAID, Type.CASH, 0.0f);
    }

    @Override
    public String toString()
    {
        return "Payment{" +
                "date=" + date +
                ", status=" + status +
                ", type=" + type +
                '}';
    }
}
