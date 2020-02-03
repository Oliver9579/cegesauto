package hu.companycar;

import hu.companycar.controller.CarService;
import hu.companycar.model.service.*;

import java.util.Scanner;

public class App {

    private final CarService carService;
    private final Console console;
    private final FileWriter fileWriter;

    private App() {
        DataApi dataApi = new DataApi(new FileReader(), new DataParser());
        carService = new CarService(dataApi.getData("autok.txt"));
        console = new Console(new Scanner(System.in));
        fileWriter = new FileWriter();
    }

    public static void main(String[] args) {
        new App().run();
    }

    private void run() {
        System.out.println("2.feladat");
        System.out.println(carService.getLastTakenCar());
        System.out.println("3.feladat");
        System.out.print("Nap: ");
        int day = console.readInt();
        System.out.println("forgalom a(z) " + day + ". napon");
        System.out.println(carService.getCertainDayLogs(day));
        System.out.println("4.feladat");
        System.out.println("A hónap végén " + carService.countTakenCars() + " autót nem hoztak vissza");
        System.out.println("5.feladat");
        System.out.println(carService.getMonthlyDistances());
        System.out.println("6.feladat");
        System.out.println(carService.getLongestDistancePerUser());
        System.out.println("7.feladat");
        System.out.print("Rendszám: ");
        String carId = console.read();
        fileWriter.write(carId, carService.getItinerary(carId));
    }
}
