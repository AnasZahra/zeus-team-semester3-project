package de.zuse.hotel.core;

import de.zuse.hotel.util.ZuseCore;

import javax.persistence.*;

@Entity
@Table(name = "address")
public class Address
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String country;
    private String city;
    private String street;
    private int plz;
    private int houseNr;

    public Address(String country, String city, String street, int plz, int houseNr)
    {
        ZuseCore.check(country != null && !country.strip().isEmpty(), "country can not be null");
        ZuseCore.check(city != null && !city.strip().isEmpty(), "city can not be null");
        ZuseCore.check(street != null && !street.strip().isEmpty(), "street can not be null");

        ZuseCore.check(String.valueOf(plz).length() == 5, "The plz must contains 5 Nummbers");
        ZuseCore.check(houseNr >= 0, "The houseNr must be >= 0");

        this.country = country;
        this.city = city;
        this.street = street;
        this.plz = plz;
        this.houseNr = houseNr;
    }

    public Address(){}

    public String getCountry()
    {
        return country;
    }

    public String getStreet()
    {
        return street;
    }

    public String getCity()
    {
        return city;
    }

    public int getPlz()
    {
        return plz;
    }

    public int getHouseNr()
    {
        return houseNr;
    }

    public void setCountry(String country)
    {
        ZuseCore.check(country != null && !country.strip().isEmpty(), "country can not be null");
        this.country = country;
    }

    public void setCity(String city)
    {
        ZuseCore.check(city != null && !city.strip().isEmpty(), "city can not be null");
        this.city = city;
    }

    public void setStreet(String street)
    {
        ZuseCore.check(street != null && !street.strip().isEmpty(), "street can not be null");
        this.street = street;
    }

    public void setPlz(int plz)
    {
        ZuseCore.check(String.valueOf(plz).length() == 5, "The plz must contains 5 Nummbers");
        this.plz = plz;
    }

    public void setHausnur(int houseNr)
    {
        ZuseCore.check(houseNr >= 0, "The hausnr must be >= 0");
        this.houseNr = houseNr;
    }

    @Override
    public String toString()
    {
        return "Address{" +
                "country='" + country + '\'' +
                ", city='" + city + '\'' +
                ", street='" + street + '\'' +
                ", plz=" + plz +
                ", hausnur=" + houseNr +
                '}';
    }
}
