package flickrdemo.yomna.flickrdemo;

import android.app.Activity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;


public class MainActivity extends Activity implements Response.Listener,
        Response.ErrorListener {
    public static final String REQUEST_TAG = "MainVolleyActivity";
    private TextView responseTextView;
    private Button sendRequestButton;
    private RequestQueue mQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        responseTextView = (TextView) findViewById(R.id.responseTextView);
        sendRequestButton = (Button) findViewById(R.id.senReqestButton);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mQueue = CustomVolleyRequestQueue.getInstance(this.getApplicationContext())
                .getRequestQueue();
        String url = "http://api.androidhive.info/volley/person_array.json";
        final CustomJSONObjectRequest jsonRequest = new CustomJSONObjectRequest(Request.Method
                .GET, url,
                new JSONObject(), this, this);
        jsonRequest.setTag(REQUEST_TAG);

        sendRequestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mQueue.add(jsonRequest);
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mQueue != null) {
            mQueue.cancelAll(REQUEST_TAG);
        }
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        responseTextView.setText(error.getMessage());
    }

    @Override
    public void onResponse(Object response) {
        responseTextView.setText("Response is: " + response);
        try {
            responseTextView.setText(responseTextView.getText() + "\n\n" + ((JSONObject) response).getString
                    ("name"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}