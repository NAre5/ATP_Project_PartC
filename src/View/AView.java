package View;

import ViewModel.ViewModel;
import javafx.scene.control.Alert;

import java.util.Observer;

public abstract class AView implements IView,Observer {

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
