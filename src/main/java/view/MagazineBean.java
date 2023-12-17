package view;

import bridge.MagazineBridge;
import bridge.PublisherBridge;
import entity.Magazine;
import entity.Publisher;
import jakarta.faces.annotation.ManagedProperty;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@Named
@ViewScoped
public class MagazineBean implements Serializable {

    private MagazineBridge magazineBridge;
    private PublisherBridge publisherBridge;
    private Magazine magazine;
    private List<Publisher> allPublisher;
    private Long id;


    public void init() {
        magazineBridge = new MagazineBridge();
        publisherBridge = new PublisherBridge();
        magazine = magazineBridge.getMagazine(id);
        allPublisher = publisherBridge.getAllPublishers();


    }

    public String saveAndNavigate() {
        System.out.println("Save method is being called");
        magazineBridge.updateMagazine(magazine);
        return "magazineList.xhtml?faces-redirect=true";
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
        if(magazine.getPublisher()==null){
            return null;
        }
        return magazine.getPublisher().getId();
    }
    public void setPublisher(Long id) {
        for(Publisher p : allPublisher){
            if(p.getId().longValue() == id.longValue()){
                magazine.setPublisher(p);
                break;
            }
        }
    }


    public List<Publisher> getAllPublisher() {
        return allPublisher;
    }

    public void setAllPublisher(List<Publisher> allPublisher) {
        this.allPublisher = allPublisher;
    }
}