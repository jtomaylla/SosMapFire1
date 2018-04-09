package com.ecandle.firebase.sosmapfire1.models;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by juantomaylla on 30/01/17.
 */

public class DataContainer<T> implements Iterable<T> {

    private ArrayList<T> lista= new ArrayList<T>();
    public  DataContainer() {
        super();
    }
    public void add(T objeto ) {
        lista.add(objeto);
    }
    @Override
    public Iterator<T> iterator() {
        return lista.iterator();
    }
}
