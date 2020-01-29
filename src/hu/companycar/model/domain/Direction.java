package hu.companycar.model.domain;

import java.util.Arrays;

public enum Direction {

    In(1, "be"),
    Out(0, "ki");

    private final int id;
    private final String description;

    Direction(int id, String description) {
        this.id = id;
        this.description = description;
    }

    public static Direction create(int id) {
        return Arrays.stream(Direction.values())
                .filter(i -> i.id == id)
                .findFirst()
                .get();
    }

    public String getDescription() {
        return description;
    }
}
