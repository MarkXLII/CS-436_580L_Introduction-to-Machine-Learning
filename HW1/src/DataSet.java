import com.opencsv.CSVReader;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * hw1
 * Author:      Swapnil Bhoite
 * B-Number:    XXX
 */
class DataSet {

    private int attributesCount;
    private String[] attributes;
    private List<Set<String>> attributeDistinctValues;
    private int targetAttribute;
    private List<String[]> data;

    private DataSet() {
        this.attributesCount = 0;
        this.attributes = new String[0];
        this.targetAttribute = -1;
        this.data = new ArrayList<>();
        this.attributeDistinctValues = new ArrayList<>();
    }

    DataSet(String filePath) {
        this.attributes = new String[0];
        this.data = new ArrayList<>();
        try {
            CSVReader csvReader = new CSVReader(new BufferedReader(new FileReader(filePath)));
            List<String[]> records = csvReader.readAll();
            if (records == null || records.isEmpty()) {
                return;
            }
            this.attributesCount = records.get(0).length;
            attributeDistinctValues = new ArrayList<>();
            for (int i = 0; i < attributesCount; i++) {
                attributeDistinctValues.add(new HashSet<>());
            }
            this.attributes = Arrays.copyOf(records.get(0), attributesCount);
            this.targetAttribute = this.attributesCount - 1;
            records.remove(0);
            this.data = new ArrayList<>(records);
            for (String[] row : data) {
                for (int j = 0; j < row.length; j++) {
                    attributeDistinctValues.get(j).add(row[j]);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Unable to open file: " + filePath + ", exiting!");
            System.exit(1);
        }
    }

    int getAttributesCount() {
        return attributesCount;
    }

    int getTargetAttribute() {
        return targetAttribute;
    }

    List<String[]> getData() {
        return data;
    }

    String[] getAttributes() {
        return attributes;
    }

    List<Set<String>> getAttributeDistinctValues() {
        return attributeDistinctValues;
    }

    DataSet filter(int attribute, String value) {
        DataSet filteredDataSet = new DataSet();
        filteredDataSet.attributesCount = this.attributesCount;
        filteredDataSet.attributes = Arrays.copyOf(this.attributes, attributesCount);
        filteredDataSet.attributeDistinctValues = new ArrayList<>(attributeDistinctValues);
        filteredDataSet.targetAttribute = this.targetAttribute;
        filteredDataSet.data.clear();
        for (String[] row : this.getData()) {
            if (row[attribute].equals(value)) {
                filteredDataSet.data.add(row);
            }
        }
        return filteredDataSet;
    }

    void filter(int attribute) {
        this.attributesCount--;

        String[] newAttributes = new String[attributesCount];
        for (int i = 0, j = 0; i <= attributesCount; i++) {
            if (i != attribute) {
                newAttributes[j++] = attributes[i];
            }
        }
        this.attributes = newAttributes;

        ArrayList<Set<String>> attributeDistinctValuesNew = new ArrayList<>(attributeDistinctValues);
        attributeDistinctValuesNew.remove(attribute);
        this.attributeDistinctValues = attributeDistinctValuesNew;

        this.targetAttribute--;

        List<String[]> newData = new ArrayList<>();
        for (String[] row : this.getData()) {
            String[] newRow = new String[row.length - 1];
            for (int i = 0, j = 0; i < row.length; i++) {
                if (i != attribute) {
                    newRow[j++] = row[i];
                }
            }
            newData.add(newRow);
        }
        this.data = newData;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (String[] aData : data) {
            stringBuilder.append(Arrays.toString(aData));
            stringBuilder.append('\n');
        }
        return "DataSet{" +
                "attributesCount=" + attributesCount +
                ", targetAttribute=" + targetAttribute +
                ", \nattributes=\n" + Arrays.toString(attributes) +
                ", \nattributeDistinctValues=\n" + attributeDistinctValues +
                ", \ndata=\n" + stringBuilder.toString() +
                '}';
    }
}
