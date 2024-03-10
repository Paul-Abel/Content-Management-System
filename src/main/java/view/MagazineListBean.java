package view;

import entity.Magazine;
import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import bridge.MagazineBridge;
import lombok.Getter;
import lombok.Setter;
import org.primefaces.PrimeFaces;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Named
@ViewScoped
public class MagazineListBean implements Serializable {

    private MagazineBridge magazineBridge;
    @Setter
    @Getter
    private List<Magazine> selectedMagazines = new ArrayList<>();
    @Setter
    @Getter
    private Magazine selectedProduct;
    @Getter
    private List<Magazine> allMagazines;

    @PostConstruct
    public void init(){
        this.magazineBridge = new MagazineBridge();
        this.allMagazines = magazineBridge.getAllMagazines();
    }

    // Update Magazines
    public void updateAllMagazine(){
        this.allMagazines = magazineBridge.getAllMagazines();
    }

    // Delete multiple magazines
    public void deleteMagazines(){
        this.magazineBridge.deleteMultipleMagazines(selectedMagazines);
        this.selectedMagazines.clear();
        this.selectedProduct=null;
        updateAllMagazine();
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Magazine entfernt","Löschen war erfolgreich"));
        PrimeFaces.current().ajax().update("form:messages", "form:allMagazineList");
    }

    // Delete single magazine
    public void deleteMagazine(Magazine magazine){
        this.magazineBridge.deleteMagazine(magazine);
        this.selectedProduct = null;
        updateAllMagazine();
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Magazin entfernt","Löschen war erfolgreich"));
        PrimeFaces.current().ajax().update("form:messages", "form:allMagazineList");
    }

    // Check if a magazine is selected
    public boolean hasSelectedMagazines() {
        return !this.selectedMagazines.isEmpty();
    }


    // Update the message from deletion button.
    public String getDeleteButtonMessage() {
        if (hasSelectedMagazines()) {
            int size = this.selectedMagazines.size();
            return size + " ausgewählt";
        }
        return "Löschen";
    }
}
