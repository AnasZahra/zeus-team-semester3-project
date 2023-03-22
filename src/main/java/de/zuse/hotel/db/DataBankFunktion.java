package de.zuse.hotel.db;

import de.zuse.hotel.core.Address;

import java.util.List;

public interface DataBankFunktion {

    public void dbCreate (T extends <?>);
    public List<?> dbSerscheAll();
    public List<?> dbSerscheforOne ();
    public void dbRemoveAll();
    public void dbRemoveOne();





}
