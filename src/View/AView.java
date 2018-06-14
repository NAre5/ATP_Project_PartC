package View;

import ViewModel.ViewModel;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Observer;

public abstract class AView implements IView, Observer,Initializable {

    protected ViewModel viewModel;

    @Override
    public void setViewModel(ViewModel viewModel) {
        this.viewModel = viewModel;
        init();
    }

    protected abstract void init();

    protected void showAlert(String alertMessage) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(alertMessage);
        alert.show();
    }



}
