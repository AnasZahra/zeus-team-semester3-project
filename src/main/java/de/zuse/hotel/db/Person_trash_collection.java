package de.zuse.hotel.db;
import de.zuse.hotel.core.Address;

import java.util.Date;
import javax.persistence.*;

@Entity
@Table(name = "Person_trash_collection")
public class Person_trash_collection {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Person_id")
    private int id;
    @Column(name = "Firstname", nullable = false)
    private String Firstname;
    @Column(name = "Lastname", nullable = false)
    private String Lastname;
    @Column(name = "Birthday", nullable = false)
    private Date Birthday;
    @Column(name = "Email")
    private String Email;
    @Column(name = "Phone_Number", length = 12)
    private int Phone_Number;
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "Address_id", nullable = false)
    private Address address_id;
}