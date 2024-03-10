package view;

import bridge.PublisherBridge;
import entity.Publisher;
import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.validator.ValidatorException;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;
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

    // A new publisher gets created
    public void openNew() {
        this.currentPublisher = new Publisher();
        PrimeFaces.current().ajax().update("dialogs");
    }

    //Return Message for every Publisher that has a Magazine linked to
    public void deletionFailed(@NotNull List<Object[]> MagazinesWithPublisherNames) {
        for (Object[] result : MagazinesWithPublisherNames) {
            String magazineTitle = (String) result[0];
            String publisherName = (String) result[1];
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Löschen fehlgeschlagen",
                    "Magazine " + magazineTitle + " hat Verlag " + publisherName + " noch als Link!"));
        }

        // Option for Future: If a Publisher has more than n Magazines, just show a number.
    }

    // Delete selcted Publisher
    public void deletePublishers() {
        List<Publisher> toDeletePublisher = new ArrayList<>();

        //Check if Publisher is allowed to delete, else give back error message.
        for (Publisher publisher : selectedPublishers) {
            List<Object[]> MagazinesWithPublisherNames = publisherBridge.getMagazinesWithPublisherNames(publisher);
            if (MagazinesWithPublisherNames.isEmpty()) {
                toDeletePublisher.add(publisher);
            } else {
                deletionFailed(MagazinesWithPublisherNames);
            }
        }

        // Delete every deletable Publisher
        if (!toDeletePublisher.isEmpty()) {
            publisherBridge.deleteMultiplePublishers(toDeletePublisher);
            updateAllPublisher();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                    "Löschen erfolgreich",
                    "Es wurden " + toDeletePublisher.size() + " von " + selectedPublishers.size() + " Verlagen gelöscht!"));
        }

        // Update list and growl
        PrimeFaces.current().ajax().update("form:messages", "form:allPublisherList");
    }


    // Delete a Publisher
    public void deletePublisher(Publisher publisher) {
        List<Object[]> MagazinesWithPublisherNames = publisherBridge.getMagazinesWithPublisherNames(publisher);
        FacesContext context = FacesContext.getCurrentInstance();
        if (MagazinesWithPublisherNames.isEmpty()) {
            this.publisherBridge.deletePublisher(publisher);
            updateAllPublisher();
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,
                    "Löschen erfolgreich",
                    "Der Verlag " + publisher.getName() + " wurde erfolgreich gelöscht.");
            context.addMessage(null, message);
        } else {
            deletionFailed(MagazinesWithPublisherNames);
        }

        // Update list and growl
        PrimeFaces.current().ajax().update("form:messages", "form:allPublisherList");
    }

    // Check if an object is selected
    public boolean hasSelectedPublishers() {
        return !this.selectedPublishers.isEmpty();
    }

    // Show a message, how many objects are selected
    public String getDeleteButtonMessage() {
        if (hasSelectedPublishers()) {
            int size = this.selectedPublishers.size();
            return size + " ausgewählt";
        }
        return "Löschen";
    }

    // Save methode, create or update publisher.
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


    // Check if publisher name is allowed.
    public void nameValidation(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        if (publisherBridge.isPublisherNameTaken((String) value) && !Objects.equals(currentPublisher.getName(), value.toString())) {
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Name vergeben!",
                    "Der Name" + value + " is bereits vergeben."));
        }
    }
}
