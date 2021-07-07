package com.kolis.test_catalog_app.ui.login;

public interface OnPasswordCheckObserver {
    void onPasswordCorrect(String login, String password);

    void onPasswordWrong();
}
