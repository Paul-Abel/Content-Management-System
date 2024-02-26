package entity;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;


@Entity
@Data
public class Magazine implements Serializable {
    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "title")
    private String title;
    @Column(name = "description")
    private String description;
    @Column(name = "category")
    private String category;

    @ManyToOne
    @JoinColumn(name = "publisher_id")
    private Publisher publisher;

    public Magazine(){
    }


}
