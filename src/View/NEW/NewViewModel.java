package View.NEW;

import View.AView;
import View.MainPage.MainView;
import algorithms.mazeGenerators.Maze;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;


public class NewViewModel extends AView {
    public Button next;
    public TextField text_rowNum;
    public TextField text_columnNum;

    public void StartGame(ActionEvent actionEvent)
    {
        int row;
        int column;
        try
        {
            row = Integer.parseInt(text_rowNum.getText());
            column = Integer.parseInt(text_columnNum.getText());
        }catch (NumberFormatException e)
        {
            showAlert("Enter only number");
            return;
        }
        catch (NullPointerException e)
        {
            showAlert("Enter only number");
            return;
        }
        MainView m = new MainView();
        m.displayMaze(viewModel.generateMaze(row,column));
    }
    public onKey
}
