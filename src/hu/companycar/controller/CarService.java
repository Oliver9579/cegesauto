package hu.companycar.controller;

import hu.companycar.model.domain.Direction;
import hu.companycar.model.domain.LogEntry;
import hu.companycar.model.domain.Pair;

import java.util.*;
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

    public String getLongestDistancePerUser() {
        Pair pair = getMaxDistancePair();
        return String.format("Leghosszabb út: %d km, személy: %d", pair.getRight(), pair.getLeft());
    }

    private Pair getMaxDistancePair() {
        return createPairs().stream()
                .max(Comparator.comparing(Pair::getRight))
                .get();
    }

    private List<Pair> createPairs() {
        List<Pair> pairs = new ArrayList<>();
        getUsers().forEach(i -> pairs.addAll(createPairPerUser(i)));
        return pairs;

    }

    private Set<Integer> getUsers() {
        return logEntries.stream()
                .map(LogEntry::getUserId)
                .collect(Collectors.toSet());
    }
    private List<Pair> createPairPerUser(int userId) {
        List<LogEntry> userIdEntries = createUserIdEntries(userId);
        int size = userIdEntries.size();
        int lastIndex = size %2 == 0 ? size : size - 1;
        List<Pair> pairs = new ArrayList<>();
        for (int i = 0; i < lastIndex; i +=2 ) {
            int distance = userIdEntries.get(i + 1).getDistanceCounter() - userIdEntries.get(i).getDistanceCounter();
            pairs.add(new Pair(userId, distance));
        }
        return pairs;
    }

    private List<LogEntry> createUserIdEntries(int userId) {
        return logEntries.stream()
                .filter(i -> i.getUserId() == userId)
                .collect(Collectors.toList());
    }

    public List<String> getItinerary(String carId) {
        List<String> lines = new ArrayList<>();
        List<LogEntry> carIdLogs = getCarIdLogs(carId);
        int size = carIdLogs.size();
        int lastIndex = size %2 == 0 ? size : size - 1;
        for (int i = 0; i< lastIndex; i += 2) {
            lines.add(printLog(carIdLogs.get(i), carIdLogs.get(i + 1)));
        }
        if (size % 2 > 0) {
            lines.add(printLog(carIdLogs.get(size - 1)));
        }
        return lines;
    }

    private List<LogEntry> getCarIdLogs(String carId) {
        return logEntries.stream()
                .filter(i -> i.getCarId().equals(carId))
                .collect(Collectors.toList());
    }

    private String printLog(LogEntry out, LogEntry in) {
        return String.format("%d\t%s\t%d km\t%s\t%d km", out.getUserId(), out.getDate(), out.getDistanceCounter(), in.getDate(), in.getDistanceCounter());
    }

    private String printLog(LogEntry out) {
        return String.format("%d\t%s\t%d km", out.getUserId(), out.getDate(), out.getDistanceCounter());
    }
}
