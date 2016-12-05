package com.korchid.msg;

import android.content.Intent;
import android.content.IntentSender;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.model.people.Person;
import com.google.firebase.analytics.FirebaseAnalytics;

/**
 * Created by mac0314 on 2016-11-28.
 */

public class MainActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    private static final String TAG = "MainActivity";
    private GoogleApiClient mGoogleApiClient;

    private FirebaseAnalytics mFirebaseAnalytics;

    TextView userName;
    Button button;
    Button button2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Obtain the FirebaseAnalytics instance.
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        userName = (TextView) findViewById(R.id.userName);

        // Loading screen
        startActivity(new Intent(this,SplashActivity.class));

        //Next button
        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener (){
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), SelectParentActivity.class));
            }
        });

        // Invite Button
        button2 = (Button) findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener (){
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), InviteActivity.class));
            }
        });

    }

    public void mOnClick(View view){
            switch (view.getId()){
                case R.id.btn_con:
                Toast.makeText(this, "접속합니다", Toast.LENGTH_SHORT).show();

                mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(Plus.API)
                .addScope(Plus.SCOPE_PLUS_PROFILE)
                .build();

                mGoogleApiClient.connect();

                break;
            }
        }

    @Override
    public void onConnected(Bundle bundle) {
                Log.d(TAG, "구글 플레이 연결이 되었습니다.");

                if (!mGoogleApiClient.isConnected() || Plus.PeopleApi.getCurrentPerson(mGoogleApiClient) == null) {

                    Log.d(TAG, "onConnected 연결 실패");

                } else {
                    Log.d(TAG, "onConnected 연결 성공");

                    Person currentPerson = Plus.PeopleApi.getCurrentPerson(mGoogleApiClient);

                    if (currentPerson.hasImage()) {

                        Log.d(TAG, "이미지 경로는 : " + currentPerson.getImage().getUrl());

                           /* Glide.with(MainActivity.this)
                                    .load(currentPerson.getImage().getUrl())
                                    .into(userphoto);*/

                    }
                    if (currentPerson.hasDisplayName()) {
                        Log.d(TAG,"디스플레이 이름 : "+ currentPerson.getDisplayName());
                        Log.d(TAG, "디스플레이 아이디는 : " + currentPerson.getId());
                        userName.setText(currentPerson.getDisplayName());
                    }

                }
            }

    @Override
    public void onConnectionSuspended(int i) {

            }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
                Log.d(TAG, "연결 에러 " + connectionResult);

                if (connectionResult.hasResolution()) {
                    Log.e(TAG,
                    String.format(
                    "Connection to Play Services Failed, error: %d, reason: %s",
                    connectionResult.getErrorCode(),
                    connectionResult.toString()));
                    try {
                        connectionResult.startResolutionForResult(this, 0);
                    } catch (IntentSender.SendIntentException e) {
                        Log.e(TAG, e.toString(), e);
                    }
                }else{
                    Toast.makeText(getApplicationContext(), "이미 로그인 중", Toast.LENGTH_SHORT).show();
                }
            }
}
