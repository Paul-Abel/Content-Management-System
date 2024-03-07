package view;

import bridge.SettingsBridge;
import entity.Settings;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;
import lombok.Getter;
import lombok.Setter;
import org.primefaces.PrimeFaces;

import java.io.Serializable;

// Make the bean accessible from the view layer (e.g., XHTML files) using Expression Language (EL)
@Named
// Beans last for the entire user session, data stored in the bean is accessible across different views and even different browser tabs
// Used here, because after saving the website get reloaded to update the menu template, so the bean dont need to get created everytime
@SessionScoped
public class SettingsBean implements Serializable {

    // Get called creating bean and after dependencies are injected
    @PostConstruct
    public void init() {
        // Create new bridge to database;
        this.settingsBridge = new SettingsBridge();

        // Get value from database just once in init to reduce database calls
        Settings menu = settingsBridge.getSetting("menu");
        if (menu == null) {
            selectedMenu = "templateMenubar";
        } else {
            selectedMenu = menu.getSettingValue();
        }

        // Get value from database just once in init to reduce database calls
        Settings partial = settingsBridge.getSetting("partial");
        if (partial == null) {
            partialShow = false;
        } else {
            partialShow = Boolean.parseBoolean(partial.getSettingValue());
        }
    }

    // Bridge to the database which provide the defined database calls.
    private SettingsBridge settingsBridge;

    // Var, which saves if the partial showcase is shown or not
    @Getter
    private Boolean partialShow;

    // Var, which saves the selected menu template
    @Getter
    private String selectedMenu;

    // Just values for partial showcase
    @Setter
    @Getter
    private String input_1 = "Das ist Input 1.";

    @Setter
    @Getter
    private String input_2 = "Das ist Input 2.";


    //Saves var in database
    public void setSelectedMenu(String selectedMenu) {
        this.selectedMenu = selectedMenu;
        settingsBridge.updateSettings(new Settings("menu", String.valueOf(selectedMenu)));
    }

    //Saves var in database
    public void setPartialShow(Boolean partialShow) {
        this.partialShow = partialShow;
        settingsBridge.updateSettings(new Settings("partial", String.valueOf(partialShow)));
    }

    // Checks if the input get submitted and shows a message
    public void checkSubmitted() {
        //Containing all data related to the current request being processed
        FacesContext context = FacesContext.getCurrentInstance();
        String input1Submitted = context.getExternalContext().getRequestParameterMap().containsKey("form:input1") ? "Input 1 wurde submitted." : "Input 1 wurde nicht submitted.";
        String input2Submitted = context.getExternalContext().getRequestParameterMap().containsKey("form:input2") ? "Input 2 wurde submitted." : "Input 2 wurde nicht submitted.";

        //Adding message
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Input 1", input1Submitted));
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Input 2", input2Submitted));

        //Update the growl
        PrimeFaces.current().ajax().update("messages");
    }

    //returns a string which reloads the website, so the new selected template menu is shown
    public String save() {
        return "/homepage.xhtml?faces-redirect=true";
    }
}
