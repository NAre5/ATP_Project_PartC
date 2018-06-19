package View;

import ViewModel.ViewModel;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.Observable;
import java.util.ResourceBundle;


public class NewView extends AView {
    public Button next;
    public TextField text_rowNum;
    public TextField text_columnNum;
    public ToggleGroup level;
    public RadioButton easy;
    public RadioButton medium;
    public RadioButton hard;
    public Pane pane;
    public ImageView background;
    public ComboBox<String> comboBox;
    public ImageView pokemon_view;

    public void StartGame(ActionEvent actionEvent) {
        int row;
        int column;
        try {
            row = Integer.parseInt(text_rowNum.getText());
            column = Integer.parseInt(text_columnNum.getText());
            if (row < 1 || column < 1)
                throw new IllegalArgumentException();
        } catch (NumberFormatException e) {
            showAlert("Enter only number");
            actionEvent.consume();
            return;
        } catch (NullPointerException e) {
            showAlert("Enter only number");
            actionEvent.consume();
            return;
        } catch (IllegalArgumentException e) {
            showAlert("Enter only positive number");
            actionEvent.consume();
            return;
        }
        switchScene((Stage) next.getScene().getWindow(), "Game");
        viewModel.generateMaze(row, column);

    }


    @Override
    public void update(Observable o, Object arg) {
    }

    @Override
    protected void init() {
        maze_columns_size.bind(viewModel.maze_columns_size);
        maze_rows_size.bind(viewModel.maze_rows_size);
    }

    public IntegerProperty maze_rows_size = new SimpleIntegerProperty();
    public IntegerProperty maze_columns_size = new SimpleIntegerProperty();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        String[] names = {"Articuno", "Dragonite", "Mew", "Mewtwo", "Missingno", "Moltres", "Zapdos"};

        comboBox.getItems().addAll(names);
        comboBox.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                pokemon_view.setImage(new Image(getClass().getResourceAsStream("/Images/pokemon/" + newValue + ".jpg")));
            }
        });
        comboBox.getSelectionModel().selectFirst();

        background.setImage(new Image(getClass().getResourceAsStream("/Images/Oak.jpg")));
        background.fitHeightProperty().bind(pane.heightProperty());
        background.fitWidthProperty().bind(pane.widthProperty());
        text_rowNum.setDisable(true);
        text_columnNum.setDisable(true);
        level.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
                if (newValue == easy) {
                    text_columnNum.setText("10");
                    text_rowNum.setText("10");
                } else if (newValue == medium) {
                    text_columnNum.setText("25");
                    text_rowNum.setText("25");
                } else {
                    text_columnNum.setText("50");
                    text_rowNum.setText("50");
                }
            }
        });
    }

    @Override
    public void setViewModel(ViewModel viewModel) {
        super.setViewModel(viewModel);
//        comboBox.valueProperty().bind(viewModel.pokemon_name);
        viewModel.pokemon_name.bind(comboBox.valueProperty());

    }

    public void change_difficulty(ActionEvent actionEvent) {
        if (!easy.isDisable()) {
            easy.setDisable(true);
            medium.setDisable(true);
            hard.setDisable(true);
            text_rowNum.setDisable(false);
            text_columnNum.setDisable(false);
        } else {
            easy.setDisable(false);
            medium.setDisable(false);
            hard.setDisable(false);
            text_rowNum.setDisable(true);
            text_columnNum.setDisable(true);
        }
    }
}
