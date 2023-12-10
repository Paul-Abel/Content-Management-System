package view;

import entity.Magazine;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;
import bridge.MagazineBridge;

import java.io.Serializable;

@Named
@SessionScoped
public class MagazineBean implements Serializable {
    private  Magazine magazine;
    public MagazineBean(){
        MagazineBridge magazineBridge = new MagazineBridge();
        magazine = magazineBridge.getMagazine(51L);
    }

    public String getTitle() {
        return magazine.getTitle();
    }

    public String getDescription() {
        return magazine.getDescription();
    }
}
