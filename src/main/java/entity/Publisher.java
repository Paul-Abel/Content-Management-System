package entity;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;

@Entity
@Data      // Generate automatically getter and setter
public class Publisher implements Serializable {

    @Id
    @GeneratedValue
    @Column(updatable = false)
    private Long id;

    @Column(name = "name", unique = true)
    private String name;

    @Column(name = "street")
    private String street;

    @Column(name = "streetnumber")
    private Integer streetnumber;

    @Column(name = "zip")
    private Integer zip;

    @Column(name ="country")
    private String country;

    @Column(name = "deactivated",nullable = false)
    private boolean deactivated;

    public Publisher() {
    }


}
