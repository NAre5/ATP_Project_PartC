package View;

import Model.Model;
import ViewModel.ViewModel;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.util.Observer;
import java.util.Optional;

public class Main extends Application {
    public Scene scene;
    @Override
    public void start(Stage primaryStage) throws Exception {
        Model model = new Model();
        model.startServers();
        ViewModel viewModel = new ViewModel(model);
        model.addObserver(viewModel);
        //--------------
        primaryStage.setTitle("My Application!");
        FXMLLoader fxmlLoader = new FXMLLoader();
//        Parent root = fxmlLoader.load(getClass().getResource("View.fxml").openStream());
//        Parent root = fxmlLoader.load(getClass().getResource("Intro/Intro.fxml").openStream());
        Parent root = fxmlLoader.load(getClass().getResource("Intro/Intro.fxml").openStream());
        Scene scene = new Scene(root, 800, 700);
//        scene.getStylesheets().add(getClass().getResource("Intro/IntroStyle.css").toExternalForm());
        primaryStage.setScene(scene);
        AView view = fxmlLoader.getController();
//        view.setResizeEvent(scene);
        view.setViewModel(viewModel);
        viewModel.addObserver(view);
                //--------------
//        View view = fxmlLoader.getController();
//        view.setResizeEvent(scene);
//        view.setViewModel(viewModel);
//        viewModel.addObserver(view);
        //--------------
        SetStageCloseEvent(primaryStage);
        primaryStage.show();
//        Thread.sleep(3000);
//        scene.setRoot(FXMLLoader.load(getClass().getResource("NEW/NewView.fxml")));
        primaryStage.show();
    }

    private void SetStageCloseEvent(Stage primaryStage) {
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent windowEvent) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK){
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
