package View;

import View.AView;
import View.MainView;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;

import java.util.Observable;


public class NewViewModel extends AView {
    public Button next;
    public TextField text_rowNum;
    public TextField text_columnNum;
    public GridPane gridPane;

    public void StartGame(ActionEvent actionEvent) {
        int row;
        int column;
        try {
            row = Integer.parseInt(text_rowNum.getText());
            column = Integer.parseInt(text_columnNum.getText());
        } catch (NumberFormatException e) {
            showAlert("Enter only number");
            return;
        } catch (NullPointerException e) {
            showAlert("Enter only number");
            return;
        }
        MainView m = new MainView();
        m.displayMaze(viewModel.generateMaze(row, column));
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

    }
}
