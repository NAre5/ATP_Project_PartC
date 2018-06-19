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
        primaryStage.setTitle("Pokemon World!");
        FXMLLoader fxmlLoader = new FXMLLoader();
        Parent root = fxmlLoader.load(getClass().getResource("StartView.fxml").openStream());
//        Scene scene = new Scene(root,800,700);
//        scene.getStylesheets().add(getClass().getResource("StartStyle.css").toExternalForm());
//        viewModel.switchScene(primaryStage,"Start");
        AView view = fxmlLoader.getController();
        view.setViewModel(viewModel);
        view.switchScene(primaryStage,"Start");
//        viewModel.addObserver(view);
        viewModel.SetStageCloseEvent(primaryStage);
        primaryStage.show();
    }




    public static void main(String[] args) {
        launch(args);
    }
}
