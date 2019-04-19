package io.joshatron.budgetlibrary.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Vendor {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(unique = true, nullable = false)
    private String name;
    @ManyToOne
    private Type type;
    @ElementCollection
    private ArrayList<String> rawMappings;

    public boolean isValid() {
        return name != null && !name.isEmpty() && type != null && type.isValid();
    }

    public void addRawMapping(String mapping) {
        rawMappings.add(mapping);
    }

    public void removeRawMapping(String mapping) {
        rawMappings.remove(mapping);
    }
}
