package View;

import ViewModel.ViewModel;

public class AView implements IView {
    protected ViewModel viewModel;
    @Override
    public void setViewModel(ViewModel viewModel) {
        this.viewModel = viewModel;
    }
}
