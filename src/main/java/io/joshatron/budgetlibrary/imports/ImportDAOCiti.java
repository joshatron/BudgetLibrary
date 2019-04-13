package io.joshatron.budgetlibrary.imports;

public class ImportDAOCiti extends ImportDAO {

    @Override
    void createTransaction(String[] elements) {
    }

    @Override
    public int getInitialLinesSkipped() {
        return 1;
    }

    @Override
    public String getName() {
        return "Citi";
    }
}
