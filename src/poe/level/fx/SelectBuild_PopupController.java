/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package poe.level.fx;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import poe.level.data.Build;
import poe.level.data.Util;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * FXML Controller class
 *
 * @author Christos
 */
public class SelectBuild_PopupController implements Initializable {

    @FXML
    private Label lblDialogTitle;
    @FXML
    private VBox buildsBox;

    private final Consumer<Build> closePopupFunction;

    public SelectBuild_PopupController(Consumer<Build> closePopupFunction) {
        this.closePopupFunction = closePopupFunction;
    }

    public void update(int id) {
        Build selected = POELevelFx.buildsLoaded.get(id);
        closePopupFunction.accept(selected);
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        for (int i = 0; i < POELevelFx.buildsLoaded.size(); i++) { //this might require to update the buildsLoaded on each new build added and removed
            Build b = POELevelFx.buildsLoaded.get(i);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("buildEntry.fxml"));
            try {
                //this will add the AnchorPane to the VBox
                buildsBox.getChildren().add(loader.load());
            } catch (IOException ex) {
                Logger.getLogger(SelectBuild_PopupController.class.getName()).log(Level.SEVERE, null, ex);
            }
            BuildEntry_Controller bec = loader.<BuildEntry_Controller>getController(); //add controller to the linker class
            bec.init_for_popup(Util.charToImage(b.getClassName(), b.getAsc()), b.getName(), b.getAsc(), i, this::update);
        }
    }


}
