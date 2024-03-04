package view;

import bridge.SettingsBridge;
import entity.Settings;
import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;


import java.io.Serializable;

@Named
@ViewScoped
public class SettingsBean implements Serializable {

    private SettingsBridge settingsBridge;

    @PostConstruct
    public void init() {
        this.settingsBridge = new SettingsBridge();
    }

    public boolean getPartial() {
        Settings partial = settingsBridge.getSetting("partial");
        if (partial == null) {
            return false;
        }
        return Boolean.parseBoolean(partial.getSettingValue());
    }

    public void setPartial(boolean menu) {
        Settings settings = new Settings("partial", String.valueOf(menu));
        settingsBridge.updateSettings(settings);
    }

    public void save() {
    }

}
