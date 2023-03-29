package de.zuse.hotel.db;
import javax.persistence.*;

@Entity
@Table(name = "Address_trash_collection")
public class Address_trash_collection {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Address_id")
    private int id;
    @Column(name = "Country", nullable = false)
    private String Country;
    @Column(name = "City", nullable = false)
    private String City;
    @Column(name = "Street", nullable = false)
    private String Street;
    @Column(name = "PostCode")
    private int PostCode;
    @Column(name = "House_Number")
    private int House_Number;
}
