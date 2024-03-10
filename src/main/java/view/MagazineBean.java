package view;

import bridge.MagazineBridge;
import bridge.PublisherBridge;
import entity.Magazine;
import entity.Publisher;
import jakarta.faces.annotation.ManagedProperty;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@Named
@ViewScoped
public class MagazineBean implements Serializable {

    private MagazineBridge magazineBridge;
    private PublisherBridge publisherBridge;
    private Magazine magazine;
    @Setter
    @Getter
    private List<Publisher> allPublisher;
    @Setter
    @Getter
    private Long id;


    public void init() {
        // Add magazine and publisher
        magazineBridge = new MagazineBridge();
        publisherBridge = new PublisherBridge();

        if (id == null) {
            magazine = new Magazine();
        } else {
            magazine = magazineBridge.getMagazine(id);
        }
        allPublisher = publisherBridge.getAllPublishers();
    }

    // Navigate to Magazine
    public String saveAndNavigate() {
        if (id == null) {
            magazineBridge.createMagazine(magazine);
        } else {
            magazineBridge.updateMagazine(magazine);
        }
        return "magazineList.xhtml?faces-redirect=true";
    }

    // Add getter and setter but dont crate an extra variable
    public String getTitle() {
        return magazine.getTitle();
    }

    public void setTitle(String title) {
        magazine.setTitle(title);
    }

    public String getDescription() {
        return magazine.getDescription();
    }

    public void setDescription(String description) {
        magazine.setDescription(description);
    }

    public String getCategory() {
        return magazine.getCategory();
    }

    public void setCategory(String category) {
        magazine.setCategory(category);
    }

    // Workaround with Long instead of Publisher, for some reason the save method dont work with Publisher
    public Long getPublisher() {
        if (magazine.getPublisher() == null) {
            return null;
        }
        return magazine.getPublisher().getId();
    }

    public void setPublisher(Long id) {
        for (Publisher p : allPublisher) {
            if (p.getId().longValue() == id.longValue()) {
                magazine.setPublisher(p);
                break;
            }
        }
    }
}