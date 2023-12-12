package view;

import bridge.PublisherBridge;
import entity.Publisher;
import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import org.primefaces.PrimeFaces;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Named
@ViewScoped
public class PublisherListBean implements Serializable {

    private PublisherBridge publisherBridge;
    private List<Publisher> selectedPublishers = new ArrayList<>();
    private Publisher selectedPublisher;
    private List<Publisher> allPublishers;

    @PostConstruct
    public void init(){
        this.publisherBridge = new PublisherBridge();
        this.allPublishers = publisherBridge.getAllPublishers();
    }

    public void updateAllPublisher(){
        this.allPublishers = publisherBridge.getAllPublishers();
    }
    public List<Publisher> getAllPublishers(){
        return this.allPublishers;
    }
    public List<Publisher> getSelectedPublishers() {
        return selectedPublishers;
    }
    public void setSelectedPublishers(List<Publisher> selectedPublishers) {
        this.selectedPublishers = selectedPublishers;
    }
    public Publisher getSelectedPublisher() {
        return selectedPublisher;
    }

    public void setSelectedPublisher(Publisher selectedPublisher) {
        this.selectedPublisher = selectedPublisher;
    }

    public void addPublisher(){
        Publisher publisher = new Publisher();
        publisher.setName("Hallo");
        this.publisherBridge.createPublisher(publisher);
        updateAllPublisher();
    }

    public void deletePublishers(){
        this.publisherBridge.deleteMultiplePublishers(selectedPublishers);
        this.selectedPublishers.clear();
        this.selectedPublisher =null;
        updateAllPublisher();
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Verlag entfernt","Löschen war erfolgreich"));
        PrimeFaces.current().ajax().update("form:messages", "form:allPublisherList");
    }

    public void deletePublisher(Publisher publisher){
        this.publisherBridge.deletePublisher(publisher);
        this.selectedPublisher = null;
        updateAllPublisher();
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Verlag entfernt","Löschen war erfolgreich"));
        PrimeFaces.current().ajax().update("form:messages", "form:allPublisherList");
    }

    public boolean hasSelectedPublishers() {
        return !this.selectedPublishers.isEmpty();
    }

    public String getDeleteButtonMessage() {
        if (hasSelectedPublishers()) {
            int size = this.selectedPublishers.size();
            return size + " ausgewählt";
        }
        return "Löschen";
    }
}
