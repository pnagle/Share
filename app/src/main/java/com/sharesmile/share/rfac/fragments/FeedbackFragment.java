package com.sharesmile.share.rfac.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.sharesmile.share.CustomJSONObject;
import com.sharesmile.share.MainApplication;
import com.sharesmile.share.R;
import com.sharesmile.share.core.BaseFragment;
import com.sharesmile.share.network.NetworkAsyncCallback;
import com.sharesmile.share.network.NetworkDataProvider;
import com.sharesmile.share.network.NetworkException;
import com.sharesmile.share.utils.Urls;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by apurvgandhwani on 3/26/2016.
 */
public class FeedbackFragment extends BaseFragment implements View.OnClickListener {

    @BindView(R.id.btn_feedback)
    Button mSubmitButton;

    @BindView(R.id.et_feedback_text)
    EditText mFeedbackText;
    private InputMethodManager inputMethodManager;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_drawer_feedback, null);
        ButterKnife.bind(this, view);
        mSubmitButton.setOnClickListener(this);
        getFragmentController().updateToolBar(getString(R.string.feedback), true);
        return view;
    }

    @Override
    public void onClick(View v) {
        //submit feedback to server
        if (TextUtils.isEmpty(mFeedbackText.getText().toString())) {
            return;
        }
        hideKeyboard(mFeedbackText);
        MainApplication.showToast(R.string.feedback_thanks);
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("user_id", MainApplication.getInstance().getUserID());
            jsonObject.put("feedback", mFeedbackText.getText().toString());
            NetworkDataProvider.doPostCallAsync(Urls.getFeedBackUrl(), jsonObject, new NetworkAsyncCallback<CustomJSONObject>() {
                @Override
                public void onNetworkFailure(NetworkException ne) {

                }

                @Override
                public void onNetworkSuccess(CustomJSONObject unObfuscable) {

                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
        getFragmentController().replaceFragment(new OnScreenFragment(), true);
    }

    protected void hideKeyboard(View view) {
        if (inputMethodManager == null) {
            inputMethodManager = (InputMethodManager) getActivity().getSystemService(
                    Context.INPUT_METHOD_SERVICE);
        }
        if (view != null) {
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}
