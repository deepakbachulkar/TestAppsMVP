/*
 *
 *  * Copyright (C) 2014 Antonio Leiva Gordillo.
 *  *
 *  * Licensed under the Apache License, Version 2.0 (the "License");
 *  * you may not use this file except in compliance with the License.
 *  * You may obtain a copy of the License at
 *  *
 *  *      http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  * Unless required by applicable law or agreed to in writing, software
 *  * distributed under the License is distributed on an "AS IS" BASIS,
 *  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  * See the License for the specific language governing permissions and
 *  * limitations under the License.
 *
 */
package com.deepak.testapp;

import android.content.Context;
import android.widget.Toast;

import com.deepak.testapp.model.User;

import java.util.List;

public class MainPresenterImpl implements MainPresenter, MainInteractor.OnLoginFinishedListener
{
    private MainView mainView;
    private MainInteractor mainInteractor;

    public MainPresenterImpl(MainView loginView)
    {
        this.mainView = loginView;
        this.mainInteractor = new MainIndicatorImpl();
    }

    @Override
    public void onDestroy() {
        mainView = null;
    }

    @Override
    public void server(Context context, String url)
    {
        if (mainView != null)
        {
            mainView.showProgress();
        }
        mainInteractor.serverCall(context,url, this);
    }

    @Override
    public void requestCall(Context context) {
        mainInteractor.requestPermission(context, this);
    }

    @Override
    public void versionCheck(Context context) {
        mainInteractor.appVersion(context, this);
    }

    @Override
    public void onSuccess(List<User> data)
    {
        if (mainView != null)
        {
            mainView.hideProgress();
            mainView.updatedToUI(data);
        }
    }

    @Override
    public void error(String reason) {
        if (mainView != null) {
            mainView.hideProgress();
            mainView.error(reason);
        }
    }

    @Override
    public void onRequest(boolean isRequest) {
        if(mainView!=null)
            mainView.requestPermission(isRequest);
    }

    @Override
    public void onVersionCheck(boolean isVersionChange) {
        if(mainView!=null)
            mainView.versionCheck(isVersionChange);
 }
}