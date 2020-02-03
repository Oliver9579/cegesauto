package hu.companycar.model.domain;

public class LogEntry {

    private final LogDate date;
    private final String carId;
    private final int userId;
    private final int distanceCounter;
    private final Direction direction;

    public LogEntry(LogDate date, String carId, int userId, int distanceCounter, Direction direction) {
        this.date = date;
        this.carId = carId;
        this.userId = userId;
        this.distanceCounter = distanceCounter;
        this.direction = direction;
    }

    public String getDate() {
        return date.toString();
    }

    public String getTime() {
        return date.getTime();
    }

    public int getDay() {
        return date.getDay();
    }

    public boolean isDay(int day) {
        return date.isDay(day);
    }

    public String getCarId() {
        return carId;
    }

    public int getUserId() {
        return userId;
    }

    public int getDistanceCounter() {
        return distanceCounter;
    }

    public Direction getDirection() {
        return direction;
    }

    @Override
    public String toString() {
        return getTime() + " " + carId + " " + userId + " " + direction.getDescription();
    }
}
