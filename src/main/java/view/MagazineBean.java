package view;

import bridge.MagazineBridge;
import entity.Magazine;
import jakarta.faces.annotation.ManagedProperty;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;

import java.io.Serializable;

@Named
@ViewScoped
public class MagazineBean implements Serializable {

    private MagazineBridge magazineBridge;
    private Magazine magazine;
    private Long id;



    public void init(){
        this.magazineBridge = new MagazineBridge();
        magazine = magazineBridge.getMagazine(id);
    }




    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getTitle() {
        return magazine.getTitle();
    }
    public String getDescribtion() {
        return magazine.getDescription();
    }

}
