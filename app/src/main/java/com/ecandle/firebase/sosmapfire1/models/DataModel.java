package com.ecandle.firebase.sosmapfire1.models;

import java.io.Serializable;

/**
 * Created by jtomaylla on 08/01/16.
 */
public class DataModel implements Serializable {

    private static final long serialVersionUID = 1L;

    String fStr, fType, fHeader;

    public DataModel(String fStr, String fType, String fHeader) {
        this.fStr = fStr;
        this.fType = fType;
        this.fHeader = fHeader;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getfStr() {
        return fStr;
    }

    public String getfType() {
        return fType;
    }

    public String getfHeader() {
        return fHeader;
    }

    @Override
    public String toString() {
        return "DataModel{" +
                "fStr='" + fStr + '\'' +
                ", fType='" + fType + '\'' +
                ", fHeader='" + fHeader + '\'' +
                '}';
    }

}