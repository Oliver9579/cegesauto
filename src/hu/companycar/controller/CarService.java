package hu.companycar.controller;

import hu.companycar.model.domain.Direction;
import hu.companycar.model.domain.LogEntry;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CarService {

    private final List<LogEntry> logEntries;

    public CarService(List<LogEntry> logEntries) {
        this.logEntries = logEntries;
    }

    public String getLastTakenCar() {
        List<LogEntry> takenCars = logEntries.stream()
                .filter(i -> i.getDirection() == Direction.Out)
                .collect(Collectors.toList());
        LogEntry log = takenCars.get(takenCars.size() - 1);
        return String.format("%d.nap rendszám: %s", log.getDay(), log.getCarId());
    }

    public String getCertainDayLogs(int day) {
        return logEntries.stream()
                .filter(i -> i.isDay(day))
                .map(LogEntry::toString)
                .collect(Collectors.joining("\r\n"));
    }

    public long countTakenCars() {
        return createCarIdCountMap().values().stream()
        .filter(i -> i % 2 > 0)
        .count();
    }

    public String getMonthlyDistances() {
        return createCarIdDistanceMap().entrySet().stream()
                .map(i -> i.getKey() + " " + i.getValue() + " km")
                .collect(Collectors.joining("\r\n"));
    }


    private Map<String, Integer> createCarIdDistanceMap() {
        return logEntries.stream()
                .map(i -> i.getCarId())
                .distinct()
                .collect(Collectors.toMap(i -> i, this::getMonthlyDistance));
    }

    private int getMonthlyDistance(String carId) {
        return getLastLog(carId) - getFirstLog(carId);
    }

    private Map<String, Long> createCarIdCountMap() {
        return logEntries.stream()
                .collect(Collectors.groupingBy(LogEntry::getCarId, Collectors.counting()));
    }

    private int getFirstLog(String carId) {
        return logEntries.stream()
                .filter(i -> i.getCarId().equals(carId))
                .findFirst()
                .map(LogEntry::getDistanceCounter)
                .get();
    }

    private int getLastLog(String carId) {
        List<Integer> carDistanceCounters = logEntries.stream()
                .filter(i -> i.getCarId().equals(carId))
                .map(LogEntry::getDistanceCounter)
                .collect(Collectors.toList());
        return carDistanceCounters.get(carDistanceCounters.size() - 1);
    }


}
