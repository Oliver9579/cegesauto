package hu.companycar.model.service;

import hu.companycar.model.domain.LogEntry;

import java.util.List;

public class DataApi {

    private final FileReader fileReader;
    private final DataParser dataParser;

    public DataApi(FileReader fileReader, DataParser dataParser) {
        this.fileReader = fileReader;
        this.dataParser = dataParser;
    }

    public List<LogEntry> getData(String input) {
        return dataParser.parse(fileReader.read(input));
    }
}
