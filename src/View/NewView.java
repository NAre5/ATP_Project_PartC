package View;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.Observable;
import java.util.ResourceBundle;


public class NewView extends AView {
    public Button next;
    public TextField text_rowNum;
    public TextField text_columnNum;
    public GridPane advanced_choose;
    public ToggleGroup level;
    public RadioButton easy;
    public RadioButton medium;
    public RadioButton hard;


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
//        maze_columns_size.set(column);
//        maze_rows_size.set(row);
//        viewModel.switchScene((Stage)next.getScene().getWindow(),"Game");
        viewModel.switchScene((Stage) next.getScene().getWindow(), "Game");
        viewModel.generateMaze(row, column);

    }


    public void ValidNumber(KeyEvent keyEvent) {
        try {
            String keyev = keyEvent.getCode().toString();
            String key = keyev.substring(5);
            int i = Integer.parseInt(key);
//        }catch (InvocationHandler e)
//        {
        } catch (Exception e) {
            if (keyEvent.getSource() == text_rowNum) {
                text_rowNum.cancelEdit();
                text_rowNum.cancelEdit();
            } else if (keyEvent.getSource() == text_columnNum)
                text_columnNum.cancelEdit();
        }
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
        text_rowNum.setDisable(true);
        text_columnNum.setDisable(true);
        level.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
                if (newValue == easy){
                    text_columnNum.setText("10");
                    text_rowNum.setText("10");
                }
                else if(newValue==medium) {
                    text_columnNum.setText("20");
                    text_rowNum.setText("20");
                }
                else {
                    text_columnNum.setText("50");
                    text_rowNum.setText("50");
                }
            }
        });
    }
//        medium.setSelected(true);


    public void change_difficulty(ActionEvent actionEvent) {
        if(!easy.isDisable()) {
            easy.setDisable(true);
            medium.setDisable(true);
            hard.setDisable(true);
            text_rowNum.setDisable(false);
            text_columnNum.setDisable(false);
        }
        else
        {
            easy.setDisable(false);
            medium.setDisable(false);
            hard.setDisable(false);
            text_rowNum.setDisable(true);
            text_columnNum.setDisable(true);

        }
    }
}
