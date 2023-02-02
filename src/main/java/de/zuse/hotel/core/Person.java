package de.zuse.hotel.core;

import java.util.Date;

public class Person
{
    private static final int TELEPHONE_NUMBER_COUNT = 12;

    private int id;
    private String firstname;
    private String lastname;
    private Date birthday;
    private String email;
    private long teleNumber;
    private Address address;

    public Person(String firstname, String lastname, Date birthday, String email, long teleNumber, Address address)
    {
        ZuseCore.check(firstname != null && !firstname.strip().isEmpty(), "The FirstName can not be null!");
        ZuseCore.check(lastname != null && !lastname.strip().isEmpty(), "The LastName can not be null!");
        ZuseCore.check(email != null && !email.strip().isEmpty(), "The Email can not be null");
        ZuseCore.check(String.valueOf(teleNumber).length() == TELEPHONE_NUMBER_COUNT, "The Telefonnr must contains"+ TELEPHONE_NUMBER_COUNT+" nummbers");
        ZuseCore.check(address != null, "address can not be null!!");

        this.firstname = firstname;
        this.lastname = lastname;
        this.birthday = birthday;
        this.email = email;
        this.teleNumber = teleNumber;
        this.address = address;
    }

    public String getFirstname()
    {
        return firstname;
    }

    public void setFirstname(String firstname)
    {
        ZuseCore.check(firstname != null && !firstname.strip().isEmpty(), "The FirstName can not be null!");
        this.firstname = firstname;
    }

    public String getLastname()
    {
        return lastname;
    }

    public void setLastname(String lastname)
    {
        ZuseCore.check(lastname != null && !lastname.strip().isEmpty(), "The LastName can not be null!");
        this.lastname = lastname;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        ZuseCore.check(id >= 0, "The Id must be >= 0!");
        this.id = id;
    }

    public Date getBirthday()
    {
        return birthday;
    }

    public void setBirthday(Date birthday)
    {
        this.birthday = birthday;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        ZuseCore.check(email != null && !email.strip().isEmpty(), "The Email can not be null");
        this.email = email;
    }

    public long getTelNumber()
    {
        return teleNumber;
    }

    public void setTelNumber(long telNumber)
    {
        ZuseCore.check(String.valueOf(telNumber).length() == 12, "The Telefonnr must contains 12 nummbers");
        this.teleNumber = telNumber;
    }

    public Address getAddress()
    {
        return address;
    }

    public void setAddress(Address address)
    {
        ZuseCore.check(address != null, "address can not be null!!");
        this.address = address;
    }

    @Override
    public String toString()
    {
        return "Person{" +
                "firstname='" + firstname + "," +
                ", lastname='" + lastname + "," +
                ", id=" + id +
                ", birthday=" + birthday +
                ", email='" + email + "," +
                ", telephone Number=" + teleNumber + "," +
                ", address=" + address + "\n" +
                '}';
    }


}
