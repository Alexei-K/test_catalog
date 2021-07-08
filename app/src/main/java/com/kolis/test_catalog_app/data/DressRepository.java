package com.kolis.test_catalog_app.data;

import androidx.lifecycle.LiveData;

import com.kolis.test_catalog_app.ui.login.OnPasswordCheckObserver;

import java.util.ArrayList;

interface DressRepository {

    void addDress(DressModel model);

    LiveData<ArrayList<DressModel>> getAllDressesLD();

    void addProfile(String login, String password);

    void isPasswordRight(String login, String password, OnPasswordCheckObserver observer);

}
