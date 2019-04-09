package com.example.paul.shoppinglist;

public class Logger {
    private static boolean loggedUsr;

    public Logger() {
        loggedUsr = true;
    }

    public static boolean isLogged() {
        return loggedUsr;
    }


}
