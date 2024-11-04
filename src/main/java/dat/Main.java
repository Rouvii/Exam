package dat;

import dat.config.ApplicationConfig;
import dat.config.Popluate;

public class Main {

 private static  Popluate popluate = new Popluate();
    public static void main(String[] args) {
        popluate.populate();
        ApplicationConfig.startServer(7070);
    }
}