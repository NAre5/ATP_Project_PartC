package View;

import Model.Model;
import ViewModel.ViewModel;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.util.Observer;
import java.util.Optional;

public class Main extends Application {
    public Scene scene;
    private Model model;
    @Override
    public void start(Stage primaryStage) throws Exception {
        model = new Model();
        model.startServers();
        ViewModel viewModel = new ViewModel(model);
        model.addObserver(viewModel);
        //--------------
        primaryStage.setTitle("My Application!");

        FXMLLoader fxmlLoader = new FXMLLoader();
        Parent root = fxmlLoader.load(getClass().getResource("StartView.fxml").openStream());
        Scene scene = new Scene(root,800,700);
        scene.getStylesheets().add(getClass().getResource("StartStyle.css").toExternalForm());
        primaryStage.setScene(scene);
        AView view = fxmlLoader.getController();
//        view.setResizeEvent(scene);
//        view.switchScene( primaryStage,"Start");
        view.setViewModel(viewModel);
        viewModel.addObserver(view);

        SetStageCloseEvent(primaryStage);
        primaryStage.show();
    }


    private void SetStageCloseEvent(Stage primaryStage) {
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent windowEvent) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK){
                    model.stopServers();
                    // ... user chose OK
                    // Close program
                } else {
                    // ... user chose CANCEL or closed the dialog
                    windowEvent.consume();
                }
            }
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}
