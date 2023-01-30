package de.zuse.hotel.core;

public class Address {

      private String country;
      private String city;
      private String street;
      private int plz;
      private int hausnur;


      public Address(String country, String city, String street, int plz , int hausnur ) {
          ZuseCore.checkFatal( country != null && !country.strip().isEmpty() , "country can not be null" );
          ZuseCore.checkFatal( city != null && !city.strip().isEmpty() , "city can not be null" );
          ZuseCore.checkFatal( street != null && !street.strip().isEmpty() , "street can not be null" );

          ZuseCore.check(String.valueOf(plz).length() == 5 , "The plz must contains 5 Nummbers");
          ZuseCore.check(hausnur >= 0 , "The hausnr must be >= 0");

          this.country = country;
          this.city = city;
          this.street = street;
          this.plz = plz;
          this.hausnur = hausnur;

      }

    public String getCountry() {
        return country;
    }

    public String getStreet() {
        return street;
    }

    public String getCity() {
        return city;
    }

    public int getPlz() {
        return plz;
    }

    public int getHausnur() {
        return hausnur;
    }

    public void setCountry(String country) {
        ZuseCore.checkFatal( country != null && !country.strip().isEmpty() , "country can not be null" );
        this.country = country;
    }

    public void setCity(String city) {
        ZuseCore.checkFatal( city != null && !city.strip().isEmpty() , "city can not be null" );
        this.city = city;
    }

    public void setStreet(String street) {
        ZuseCore.checkFatal( street != null && !street.strip().isEmpty() , "street can not be null" );
        this.street = street;
    }

    public void setPlz(int plz) {
        ZuseCore.check(String.valueOf(plz).length() == 5 , "The plz must contains 5 Nummbers");
        this.plz = plz;
    }

    public void setHausnur(int hausnur) {
        ZuseCore.check(hausnur >= 0 , "The hausnr must be >= 0");
        this.hausnur = hausnur;
    }

    @Override
    public String toString() {
        return "Address{" +
                "country='" + country + '\'' +
                ", city='" + city + '\'' +
                ", street='" + street + '\'' +
                ", plz=" + plz +
                ", hausnur=" + hausnur +
                '}';
    }
}
