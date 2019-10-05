package io.joshatron.budgetlibrary.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.text.similarity.LevenshteinDistance;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
public class Vendor {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(unique = true, nullable = false)
    private String name;
    @ManyToOne
    private Type type;
    @ElementCollection
    private List<String> rawMappings;

    public Vendor() {
        rawMappings = new ArrayList<>();
    }

    public boolean isValid() {
        return name != null && !name.isEmpty() && type != null && type.isValid();
    }

    public void addRawMapping(String mapping) {
        rawMappings.add(mapping);
    }

    public void removeRawMapping(String mapping) {
        rawMappings.remove(mapping);
    }

    public boolean hasRawMapping(String mapping) {
        for(String raw : rawMappings) {
            if(mapping.equals(raw)) {
                return true;
            }
        }

        return false;
    }

    public int getDistanceFromRaw(String raw) {
        int best = Integer.MAX_VALUE;
        for(String mapping : rawMappings) {
            best = Math.min(getDistance(mapping, raw), best);
        }

        return best;
    }

    /*
     * The distance is obtained using the levenshtein distance algorithm with one difference.
     * Most differences seem to be just numbers, but we mostly care about the text when looking at example comparisons,
     * so first all digits are turned to the number 0 so that ones with just number differences will seem more similar.
     * And the numbers are turned to 0s, not just deleted to check that the format still looks similar.
     */
    private int getDistance(String first, String second) {
        return new LevenshteinDistance().apply(first.replaceAll("[0-9]", "0"), second.replaceAll("[0-9]", "0"));
    }
}
