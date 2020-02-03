package hu.companycar.model.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class FileWriter {

    private static final String POSTFIX = "_menetlevel.txt";

    public void write(String carId, List<String> content) {
        String fileName = carId + POSTFIX;
        try {
            Files.write(Paths.get(fileName), content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

