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


    public void switchScene(Stage primaryStage, String sceneName) {
        try {
            if(viewModel.mediaPlayer!=null){viewModel.mediaPlayer.dispose();}
            FXMLLoader fxmlLoader = new FXMLLoader();
            Parent root = fxmlLoader.load(getClass().getResource(sceneName + "View.fxml").openStream());
            Scene scene = new Scene(root, 800, 700);
            scene.getStylesheets().add(getClass().getResource(sceneName + "Style.css").toExternalForm());
            primaryStage.setScene(scene);
            AView view = fxmlLoader.getController();
            view.setViewModel(viewModel);
            viewModel.addObserver(view);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void raiseStage(String sceneName) {
        Stage aboutStage = new Stage();
        aboutStage.setAlwaysOnTop(true);
        aboutStage.setResizable(true);
        aboutStage.setTitle(sceneName);

        Parent root = null;
        FXMLLoader fxmlLoader = null;
        try {
            //change MyView.fxml to help.fxml after designed
            fxmlLoader = new FXMLLoader();
            root = fxmlLoader.load(getClass().getResource(sceneName + "View.fxml").openStream());
        } catch (IOException e) {
            showAlert("Exception!");
        }
        Scene scene = new Scene(root, 600, 650);
        aboutStage.setScene(scene);
        AView view = fxmlLoader.getController();
        view.setViewModel(viewModel);
        viewModel.addObserver(view);
//        aboutStage.initModality(Modality.APPLICATION_MODAL);
        aboutStage.show();
    }
}
