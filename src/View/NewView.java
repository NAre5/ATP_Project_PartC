package View;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.net.URL;
import java.util.Observable;
import java.util.ResourceBundle;


public class NewView extends AView {
    public Button next;
    public TextField text_rowNum;
    public TextField text_columnNum;

    public void StartGame(ActionEvent actionEvent) {
        int row;
        int column;
        try {
            row = Integer.parseInt(text_rowNum.getText());
            column = Integer.parseInt(text_columnNum.getText());
        } catch (NumberFormatException e) {
            showAlert("Enter only number");
            actionEvent.consume();
            return;
        } catch (NullPointerException e) {
            showAlert("Enter only number");
            actionEvent.consume();
            return;
        }
//        maze_columns_size.set(column);
//        maze_rows_size.set(row);
        viewModel.switchScene((Stage)next.getScene().getWindow(),"Game");
        viewModel.generateMaze(row,column);
    }


    public void ValidNumber(KeyEvent keyEvent) {
        try {
            String keyev = keyEvent.getCode().toString();
            String key = keyev.substring(5);
            int i = Integer.parseInt(key);
//        }catch (InvocationHandler e)
//        {
        }
        catch (Exception e) {
            if (keyEvent.getSource() == text_rowNum) {
                text_rowNum.cancelEdit();
                text_rowNum.cancelEdit();
            }
            else if (keyEvent.getSource() == text_columnNum)
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

    }
}
