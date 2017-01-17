/**
 * Copyright 2016 Google Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.korchid.msg.firebase.fcm;

import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.korchid.msg.storage.server.retrofit.RestfulAdapter;
import com.korchid.msg.storage.server.retrofit.response.DeviceToken;
import com.korchid.msg.storage.server.retrofit.response.Res;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.korchid.msg.global.QuickstartPreferences.SHARED_PREF_USER_LOGIN;
import static com.korchid.msg.global.QuickstartPreferences.USER_DEVICE_TOKEN;
import static com.korchid.msg.global.QuickstartPreferences.USER_ID_NUMBER;


public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {

    private static final String TAG = "MyFirebaseIIDService";

    /**
     * Called if InstanceID token is updated. This may occur if the security of
     * the previous token had been compromised. Note that this is called when the InstanceID token
     * is initially generated so this is where you would retrieve the token.
     */
    // [START refresh_token]
    @Override
    public void onTokenRefresh() {
        // Get updated InstanceID token.
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "Refreshed token: " + refreshedToken);

        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREF_USER_LOGIN, 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(USER_DEVICE_TOKEN, refreshedToken);

        editor.apply();

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.
        sendRegistrationToServer(refreshedToken);
    }
    // [END refresh_token]

    /**
     * Persist token to third-party servers.
     *
     * Modify this method to associate the user's FCM InstanceID token with any server-side account
     * maintained by your application.
     *
     * @param token The new token.
     */
    private void sendRegistrationToServer(String token) {
        Log.d(TAG, "sendRegistrationToServer");

        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREF_USER_LOGIN, 0);

        int userId = sharedPreferences.getInt(USER_ID_NUMBER, 0);

        Log.d(TAG, "userId : " + userId);

        Call<DeviceToken> userCall = RestfulAdapter.getInstance().firebaseRegister(userId, token);

        userCall.enqueue(new Callback<DeviceToken>() {
            @Override
            public void onResponse(Call<DeviceToken> call, Response<DeviceToken> response) {
                DeviceToken deviceToken = response.body();

                if(deviceToken.getRegister()){
                    Log.d(TAG, "Register device token successfully");
                }else{
                    Log.d(TAG, "Registering device token fail");
                }

            }
            @Override
            public void onFailure(Call<DeviceToken> call, Throwable t) {
                Log.d(TAG, "onFailure");
                //Toast.makeText(getApplicationContext(), "잠시 후 다시 시도하세요.", Toast.LENGTH_LONG).show();
                Log.d(TAG, "Fail to receive response data");
            }
        });
    }
}
