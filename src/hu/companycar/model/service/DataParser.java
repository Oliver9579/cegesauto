package hu.companycar.model.service;

import hu.companycar.model.domain.Direction;
import hu.companycar.model.domain.LogDate;
import hu.companycar.model.domain.LogEntry;

import java.util.List;
import java.util.stream.Collectors;

public class DataParser {

    public List<LogEntry> parse(List<String> lines) {
        return lines.stream()
                .map(this::createLogEntry)
                .collect(Collectors.toList());
    }

    private LogEntry createLogEntry(String line) {
        String[] items = line.split(" ");
        LogDate date = new LogDate(intValue(items[0]), items[1]);
        String carId = items[2];
        int userId = intValue(items[3]);
        int distanceCounter = intValue(items[4]);
        Direction direction = Direction.create(intValue(items[5]));
        return new LogEntry(date, carId, userId, distanceCounter, direction);
    }

    private int intValue(String item) {
        return Integer.parseInt(item);
    }
}
