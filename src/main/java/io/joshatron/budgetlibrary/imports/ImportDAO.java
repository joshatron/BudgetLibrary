package io.joshatron.budgetlibrary.imports;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public abstract class ImportDAO {

    public void addTransactions(String file) {
        try {
            BufferedReader br = Files.newBufferedReader(Paths.get(file));
            List<String> lines = br.lines().collect(Collectors.toList());

            int skip = getInitialLinesSkipped();
            for(String line : lines) {
                if(skip > 0) {
                    skip--;
                    continue;
                }

                createTransaction(getElements(line));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String[] getElements(String line) {
        ArrayList<String> elements = new ArrayList<>();

        while(!line.isEmpty()) {
            if(line.charAt(0) == '"') {
                int index = line.indexOf("\"", 1);
                elements.add(line.substring(1, index));
                line = line.substring(index + 1);
                if(!line.isEmpty() && line.charAt(0) == ',') {
                    line = line.substring(1);
                }
            }
            else if(line.contains(",")){
                int index = line.indexOf(",", 0);
                elements.add(line.substring(0, index));
                line = line.substring(index + 1);
            }
            else {
                elements.add(line);
                line = "";
            }
        }

        return elements.toArray(new String[0]);
    }

    public int getInitialLinesSkipped() {
        return 0;
    }

    abstract void createTransaction(String[] elements);

    abstract String getName();
}
