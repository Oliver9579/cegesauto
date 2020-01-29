package hu.companycar.model.domain;

public class LogDate {

    private final int day;
    private final String time;

    public LogDate(int day, String time) {
        this.day = day;
        this.time = time;
    }

    public int getDay() {
        return day;
    }

    public String getTime() {
        return time;
    }

    public boolean isDay(int day) {
        return this.day == day;
    }

    @Override
    public String toString() {
        return day + ". " + time;
    }
}
