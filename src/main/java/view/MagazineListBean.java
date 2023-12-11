package view;

import entity.Magazine;
import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import bridge.MagazineBridge;
import org.primefaces.PrimeFaces;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Named
@ViewScoped
public class MagazineListBean implements Serializable {

    private MagazineBridge magazineBridge;
    private List<Magazine> selectedMagazines = new ArrayList<>();
    private Magazine selectedProduct;
    private List<Magazine> allMagazines;

    @PostConstruct
    public void init(){
        this.magazineBridge = new MagazineBridge();
        this.allMagazines = magazineBridge.getAllMagazines();
    }

    public void updateAllMagazine(){
        this.allMagazines = magazineBridge.getAllMagazines();
    }
    public List<Magazine> getAllMagazines(){
        return this.allMagazines;
    }
    public List<Magazine> getSelectedMagazines() {
        return selectedMagazines;
    }
    public void setSelectedMagazines(List<Magazine> selectedMagazines) {
        this.selectedMagazines = selectedMagazines;
    }
    public Magazine getSelectedProduct() {
        return selectedProduct;
    }

    public void setSelectedProduct(Magazine selectedProduct) {
        this.selectedProduct = selectedProduct;
    }

    public void addMagazine(){
        Magazine magazine = new Magazine();
        magazine.setTitle("Hallo");
        magazine.setDescription("Welt");
        magazine.setCategory("Fußball");
        this.magazineBridge.createMagazine(magazine);
        updateAllMagazine();
    }

    public void deleteMagazines(){
        this.magazineBridge.deleteMultipleMagazines(selectedMagazines);
        this.selectedMagazines = null;
        this.selectedProduct=null;
        updateAllMagazine();
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Magazine entfernt","Löschen war erfolgreich"));
        PrimeFaces.current().ajax().update("form:messages", "form:allMagazineList");
    }

    public void deleteMagazine(Magazine magazine){
        this.magazineBridge.deleteMagazine(magazine);
        this.selectedProduct = null;
        updateAllMagazine();
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Magazin entfernt","Löschen war erfolgreich"));
        PrimeFaces.current().ajax().update("form:messages", "form:allMagazineList");
    }

    public boolean hasSelectedMagazines() {
        return !this.selectedMagazines.isEmpty();
    }

    public String getDeleteButtonMessage() {
        if (hasSelectedMagazines()) {
            int size = this.selectedMagazines.size();
            return size + " ausgewählt";
        }
        return "Löschen";
    }
}
