package com.moddakir.call.view;


public interface MainView {
    void showToastMessage(String message);

    void showAlertDialog();

    void hideAlertDialog();

    void showMessageDialog(String title, String message, boolean isCancelOutside, Runnable func);


}
