package utility;

import lombok.Getter;
import lombok.Setter;

// A utility class just to model the menuItems

// Getters and setters
@Setter
@Getter
public class MenuItem {

    // Name of the menu item
    private String value;

    //Url of the menu item
    private String url;

    public MenuItem(String label, String value) {
        this.value = label;
        this.url = value;
    }
}

