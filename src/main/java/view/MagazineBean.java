package view;

import entity.Magazine;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import bridge.MagazineBridge;
import org.primefaces.PrimeFaces;

import java.io.Serializable;
import java.util.List;

@Named
@ViewScoped
public class MagazineBean implements Serializable {
    private Magazine magazine = null;
    private MagazineBridge magazineBridge;
    private Magazine selectedMagazine;

    private List<Magazine> selectedMagazines;


    //Constructor
    public MagazineBean(){
        this.magazineBridge = new MagazineBridge();
    }
    public List<Magazine> getAllMagazines(){
        return magazineBridge.getAllMagazines();
    }

    public Magazine getSelectedMagazine() {
        return selectedMagazine;
    }
    public void setSelectedMagazine(Magazine selectedMagazine) {
        this.selectedMagazine = selectedMagazine;
    }

    public List<Magazine> getSelectedMagazines() {
        return selectedMagazines;
    }
    public void setSelectedMagazines(List<Magazine> selectedMagazines) {
        this.selectedMagazines = selectedMagazines;
    }

    public Long getId(){
        if(magazine!=null){
            return magazine.getId();
        }
        return 1L;
    }

    public void addMagazine(){
        Magazine magazine = new Magazine();
        magazine.setTitle("Hallo");
        magazine.setDescription("Welt");
        this.magazineBridge.createMagazine(magazine);
    }

    public void deleteMagazines(){
        this.magazineBridge.deleteMultipleMagazines(selectedMagazines);
        this.selectedMagazines = null;
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Products Removed"));
        PrimeFaces.current().ajax().update("form:messages", "form:allMagazineList");
    }

    public boolean hasSelectedMagazines() {
        boolean a = this.selectedMagazine != null && !this.selectedMagazines.isEmpty();
        return a;
    }
}
