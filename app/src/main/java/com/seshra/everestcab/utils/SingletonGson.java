package com.seshra.everestcab.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


public class SingletonGson {
    private static final Gson instance = new GsonBuilder().create();

    // Private constructor prevents instantiation from other classes
    private SingletonGson() {
    }

    public static Gson getInstance() {
        return instance;
    }

}
