package de.zuse.hotel.core;

import java.util.Date;

public class Person
{
    private int id;
    private String firstname;
    private String lastname;
    private Date birthday;
    private String email;
    private long telefonnr;
    private Address address;

    public Person(String firstname, String lastname, Date birthday, String email, long telefonnr, Address address)
    {
        ZuseCore.checkFatal(firstname != null && !firstname.strip().isEmpty(), "The FirstName can not be null!");
        ZuseCore.checkFatal(lastname != null && !lastname.strip().isEmpty(), "The LastName can not be null!");
        ZuseCore.checkFatal(email != null && !email.strip().isEmpty(), "The Email can not be null");

        ZuseCore.check(String.valueOf(telefonnr).length() == 12, "The Telefonnr must contains 12 nummbers");

        this.firstname = firstname;
        this.lastname = lastname;
        this.birthday = birthday;
        this.email = email;
        this.telefonnr = telefonnr;
        this.address = address;
    }

    public String getFirstname()
    {
        return firstname;
    }

    public void setFirstname(String firstname)
    {
        ZuseCore.checkFatal(firstname != null && !firstname.strip().isEmpty(), "The FirstName can not be null!");
        this.firstname = firstname;
    }

    public String getLastname()
    {
        return lastname;
    }

    public void setLastname(String lastname)
    {
        ZuseCore.checkFatal(lastname != null && !lastname.strip().isEmpty(), "The LastName can not be null!");
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
        ZuseCore.checkFatal(email != null && !email.strip().isEmpty(), "The Email can not be null");
        this.email = email;
    }

    public long getTelefonnr()
    {
        return telefonnr;
    }

    public void setTelefonnr(long telefonnr)
    {
        ZuseCore.check(String.valueOf(telefonnr).length() == 12, "The Telefonnr must contains 12 nummbers");
        this.telefonnr = telefonnr;
    }

    public Address getAddress()
    {
        return address;
    }

    public void setAddress(Address address)
    {
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
                ", telefonnr=" + telefonnr + "," +
                ", address=" + address + "\n" +
                '}';
    }


}
