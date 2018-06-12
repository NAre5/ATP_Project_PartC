package View;

import ViewModel.ViewModel;
import javafx.scene.control.Alert;

public class AView implements IView {
    protected ViewModel viewModel;
    @Override
    public void setViewModel(ViewModel viewModel) {
        this.viewModel = viewModel;
    }
    public void showAlert(String alertMessage) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(alertMessage);
        alert.show();
    }
}
