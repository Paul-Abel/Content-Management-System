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


@Named
@SessionScoped
public class SettingsBean implements Serializable {

    @Getter
    private Boolean rendered = false;

    private String selectedMenu;

    public String getSelectedMenu() {
        if (selectedMenu == null) {
            Settings menu = settingsBridge.getSetting("menu");
            if (menu == null) {
                return "templateMenubar";
            }
            return menu.getSettingValue();
        }
        return selectedMenu;
    }

    public void setSelectedMenu(String selectedMenu) {
        Settings settings = new Settings("menu", String.valueOf(selectedMenu));
        settingsBridge.updateSettings(settings);
    }

    @Getter
    @Setter
    private Boolean partialActive = false;

    @Setter
    @Getter
    private String input_1 = "Das ist Input 1.";

    @Setter
    @Getter
    private String input_2 = "Das ist Input 2.";

    private SettingsBridge settingsBridge;

    @PostConstruct
    public void init() {
        this.settingsBridge = new SettingsBridge();
    }

    public boolean getPartialShow() {
        Settings partial = settingsBridge.getSetting("partial");
        if (partial == null) {
            rendered = false;
            return false;
        }
        rendered = Boolean.parseBoolean(partial.getSettingValue());
        return Boolean.parseBoolean(partial.getSettingValue());
    }

    public void setPartialShow(boolean menu) {
        Settings settings = new Settings("partial", String.valueOf(menu));
        settingsBridge.updateSettings(settings);
    }

    public String save() {
        return "/homepage.xhtml?faces-redirect=true";
    }

    public void checkSubmitted() {
        FacesContext context = FacesContext.getCurrentInstance();
        String input1Submitted = context.getExternalContext().getRequestParameterMap().containsKey("form:input1") ? "Input 1 wurde submitted." : "Input 1 wurde nicht submitted.";
        String input2Submitted = context.getExternalContext().getRequestParameterMap().containsKey("form:input2") ? "Input 2 wurde submitted." : "Input 2 wurde nicht submitted.";

        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Input 1", input1Submitted));
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Input 2", input2Submitted));

        PrimeFaces.current().ajax().update("messages");
    }


}
