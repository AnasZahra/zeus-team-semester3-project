package de.zuse.hotel.db;

import java.util.List;

public interface DataBankOperation {

    public void dbCreate (Object object);
    public List<?> dbsearchAll();
    public <T> T dbsearchById  (int id);
    // this need to be changed latter pls by Basel
    public void dbRemoveAll();
    public void dbRemoveById(int id);


    //public void dbSearchByVariabel (String searchTerm);

}
