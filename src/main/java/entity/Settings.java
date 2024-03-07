package entity;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;

@Entity
@Data      // Generate automatically getter and setter
public class Settings implements Serializable {

    @Id
    @Column
    private String settingName;

    @Column
    private String settingValue;

    public Settings(String name, String value) {
        this.settingName = name;
        this.settingValue = value;
    }

    public Settings() {
    }
}

