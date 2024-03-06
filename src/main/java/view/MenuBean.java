package view;

import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import utility.MenuItem;

import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

//This bean just provides the menu items for the templates.


//Add to every variable in the class automatically a Getter and Setter.
@Setter
@Getter
@Named
@ViewScoped
public class MenuBean implements Serializable {

    //Contains every menu item
    private List<MenuItem> menuItems;

    //Menu items get initialized directly after class creation.
    @PostConstruct
    public void init() {
        menuItems = new ArrayList<>();
        menuItems.add(new MenuItem("Startseite", "homepage.xhtml"));
        menuItems.add(new MenuItem("Magazine", "magazineList.xhtml"));
        menuItems.add(new MenuItem("Verlag", "publisher.xhtml"));
        // Add more menu items as needed
    }
}
