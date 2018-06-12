package View;

import ViewModel.ViewModel;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Observer;

public abstract class AView implements IView, Observer {

    protected ViewModel viewModel;
    protected Scene currentscene;

    @Override
    public void setViewModel(ViewModel viewModel) {
        this.viewModel = viewModel;
//        currentscene = new Scene();
        init();
    }

    protected abstract void init();

    protected void showAlert(String alertMessage) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(alertMessage);
        alert.show();
    }

    protected void switchScene(Stage primaryStage, String sceneName) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            Parent root = fxmlLoader.load(getClass().getResource( sceneName + "View.fxml").openStream());
            Scene scene = new Scene(root, 800, 700);
            scene.getStylesheets().add(getClass().getResource(sceneName + "Style.css").toExternalForm());
            primaryStage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
