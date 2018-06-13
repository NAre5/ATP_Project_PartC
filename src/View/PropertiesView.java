package View;

import Server.Server;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.Observable;
import java.util.ResourceBundle;

/**
 * Created by Michael Michaelshvili on 13/06/2018.
 */
public class PropertiesView extends AView{
    public Label num_thread;
    public Label generation_algorithm;
    public Label solve;
    public VBox vb;
    @Override
    protected void init() {

    }

    @Override
    public void update(Observable o, Object arg) {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        int i= Server.Configurations.PROPERTIES.values().length;
        for(int j = 0;j<i;j++){
            Server.Configurations.PROPERTIES name = Server.Configurations.PROPERTIES.values()[j];
            vb.getChildren().add(new Label(name+" - "+Server.Configurations.getProperty(name).toString()));
        }



    }
}
