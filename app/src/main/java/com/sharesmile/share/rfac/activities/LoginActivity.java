package com.sharesmile.share.rfac.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sharesmile.share.R;
import com.sharesmile.share.TrackerActivity;
import com.sharesmile.share.core.Constants;
import com.sharesmile.share.core.LoginImpl;
import com.sharesmile.share.gps.RunTracker;
import com.sharesmile.share.utils.SharedPrefsManager;
import com.sharesmile.share.views.MRTextView;


import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by apurvgandhwani on 4/5/2016.
 */
public class LoginActivity extends AppCompatActivity implements View.OnClickListener, LoginImpl.LoginListener {

    private static final int REQUEST_GOOGLE_SIGN_IN = 1001;
    public static final String BUNDLE_FROM_MAINACTIVITY = "is_from_mainactivity";
    @BindView(R.id.btn_login_fb)
    LinearLayout mFbLoginButton;

    @BindView(R.id.btn_login_google)
    LinearLayout mGoogleLoginButton;

    @BindView(R.id.tv_welcome_skip)
    MRTextView tv_skip;

    @BindView(R.id.login_container)
    LinearLayout mLoginContainer;

    @BindView(R.id.progress_container)
    LinearLayout mProgressContainer;
    private boolean isFromMainActivity;
    private LoginImpl mLoginHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        isFromMainActivity = getIntent().getBooleanExtra(BUNDLE_FROM_MAINACTIVITY, false);

        if (!SharedPrefsManager.getInstance().getBoolean(Constants.PREF_IS_LOGIN)
                && (isFromMainActivity || !SharedPrefsManager.getInstance().getBoolean(Constants.PREF_LOGIN_SKIP, false))) {
            setContentView(R.layout.welcome_screen);
            mLoginHandler = new LoginImpl(this, this);
            ButterKnife.bind(this);
            initUi();
        } else if(RunTracker.isWorkoutActive()) {
            Intent intent = new Intent(this, TrackerActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        } else {
            startMainActivity();
        }
    }

    private void initUi() {
        tv_skip.setOnClickListener(this);
        mFbLoginButton.setOnClickListener(this);
        mGoogleLoginButton.setOnClickListener(this);

        //init fb login
        TextView mFbText = (TextView) mFbLoginButton.findViewById(R.id.title);
        mFbText.setText(getString(R.string.logn_with_fb));
        mFbText.setTextColor(getResources().getColor(R.color.denim_blue));

        ImageView mFbImage = (ImageView) mFbLoginButton.findViewById(R.id.login_image);
        mFbImage.setImageResource(R.drawable.logo_fb);


        //init Google login
        TextView mGText = (TextView) mGoogleLoginButton.findViewById(R.id.title);
        mGText.setText(getString(R.string.logn_with_google));
        mGText.setTextColor(getResources().getColor(R.color.pale_red));

        ImageView mGImage = (ImageView) mGoogleLoginButton.findViewById(R.id.login_image);
        mGImage.setImageResource(R.drawable.login_google);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login_fb:
                // performFbLogin();
                mLoginHandler.performFbLogin();
                break;
            case R.id.tv_welcome_skip:
                SharedPrefsManager prefsManager = SharedPrefsManager.getInstance();
                prefsManager.setBoolean(Constants.PREF_LOGIN_SKIP, true);
                startMainActivity();
                break;
            case R.id.btn_login_google:
                mLoginHandler.performGoogleLogin();
                break;

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mLoginHandler.onActivityResult(requestCode, resultCode, data);

    }

    private void startMainActivity() {
        if (!isFromMainActivity) {
            Intent intent = new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        } else {
            finish();
        }
    }


    @Override
    public void onLoginSuccess() {
        startMainActivity();
    }

    @Override
    public void showHideProgress(boolean show, String title) {

        if (show) {
            mLoginContainer.setVisibility(View.GONE);
            mProgressContainer.setVisibility(View.VISIBLE);
        } else {
            mLoginContainer.setVisibility(View.VISIBLE);
            mProgressContainer.setVisibility(View.GONE);
        }
    }
}
