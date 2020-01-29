package hu.companycar.model.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class FileReader {

    public List<String> read(String input) {
        List<String> lines = new ArrayList<>();
        try {
            lines = Files.readAllLines(Paths.get(input));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lines;
    }
}
