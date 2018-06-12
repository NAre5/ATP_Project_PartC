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
        Parent root = viewModel.setScene(sceneName);

    }

}
