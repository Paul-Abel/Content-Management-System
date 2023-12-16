package view;

import bridge.PublisherBridge;
import entity.Publisher;
import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import lombok.Getter;
import lombok.Setter;
import org.primefaces.PrimeFaces;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Named
@ViewScoped
public class PublisherListBean implements Serializable {

    private PublisherBridge publisherBridge;
    @Setter
    @Getter
    private List<Publisher> selectedPublishers = new ArrayList<>();
    @Getter
    private List<Publisher> allPublishers;
    @Getter
    @Setter
    private Publisher currentPublisher;

    @PostConstruct
    public void init() {
        this.publisherBridge = new PublisherBridge();
        this.allPublishers = publisherBridge.getAllPublishers();
        this.currentPublisher = new Publisher();
    }

    public void updateAllPublisher() {
        this.allPublishers = publisherBridge.getAllPublishers();
    }

    public void openNew() {
        this.currentPublisher = new Publisher();
        PrimeFaces.current().ajax().update("dialogs");
    }


    public void deletePublishers() {
        this.publisherBridge.deleteMultiplePublishers(selectedPublishers);
        this.selectedPublishers.clear();
        updateAllPublisher();
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Verlag entfernt", "Löschen war erfolgreich"));
        PrimeFaces.current().ajax().update("form:messages", "form:allPublisherList");
    }

    public void deletePublisher(Publisher publisher) {
        this.publisherBridge.deletePublisher(publisher);
        updateAllPublisher();
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Verlag entfernt", "Löschen war erfolgreich"));
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

    public void save() {
        if (!Objects.equals(currentPublisher.getCountry(), "Deutschland")) {
            currentPublisher.setZip(null);
        }
        if (currentPublisher.getId() == null) {
            publisherBridge.createPublisher(this.currentPublisher);
        } else {
            publisherBridge.updatePublisher(currentPublisher);
        }
        updateAllPublisher();
        PrimeFaces.current().ajax().update("form:allPublisherList");
    }
}
