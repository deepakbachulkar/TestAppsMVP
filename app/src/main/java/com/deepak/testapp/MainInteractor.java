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

import com.deepak.testapp.model.User;

import java.util.List;

public interface MainInteractor {
    interface OnLoginFinishedListener {
        void error(String reason);
        void onSuccess(List<User> data);
        void onRequest(boolean isRequest);
        void onVersionCheck(boolean isVersionChange);
    }
    void serverCall(Context context, String url,  OnLoginFinishedListener listener);
    void requestPermission(Context context,  OnLoginFinishedListener listener);
    void appVersion(Context context,  OnLoginFinishedListener listener);
}