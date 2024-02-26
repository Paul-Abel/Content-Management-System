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

    public void openNew() {
        this.currentPublisher = new Publisher();
        PrimeFaces.current().ajax().update("dialogs");
    }

    public void deletionFailed(@NotNull List<Object[]> MagazinesWithPublisherNames){
        for (Object[] result : MagazinesWithPublisherNames) {
            String magazineTitle = (String) result[0];
            String publisherName = (String) result[1];
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Löschen fehlgeschlagen",
                    "Magazine " + magazineTitle + " hat Verlag " + publisherName + " noch als Link!"));
        }
    }


    public void deletePublishers() {
        List<Publisher> toDeletePublisher = new ArrayList<>();

        for (Publisher publisher : selectedPublishers) {
            List<Object[]> MagazinesWithPublisherNames = publisherBridge.getMagazinesWithPublisherNames(publisher);
            if (MagazinesWithPublisherNames.isEmpty()) {
                toDeletePublisher.add(publisher);
            } else {
                deletionFailed(MagazinesWithPublisherNames);
            }
        }

        if(!toDeletePublisher.isEmpty()){
            publisherBridge.deleteMultiplePublishers(toDeletePublisher);
            updateAllPublisher();
            FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_INFO,
                    "Löschen erfolgreich",
                    "Es wurden " + toDeletePublisher.size() + " von " + selectedPublishers.size() + " Verlagen gelöscht!"));
        }
        PrimeFaces.current().ajax().update("form:messages", "form:allPublisherList");
    }


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

    public void nameValidation (FacesContext context, UIComponent component, Object value) throws ValidatorException {
        if(publisherBridge.isPublisherNameTaken((String) value) && currentPublisher.getName()!= value){
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Name vergeben!",
                    "Der Name" + value + " is bereits vergeben."));
        }
    }
}
