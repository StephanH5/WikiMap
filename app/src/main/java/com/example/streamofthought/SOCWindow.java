package com.example.streamofthought;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.text.HtmlCompat;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class SOCWindow extends AppCompatActivity {


    private TextView textView;
    private ThoughtPage page;
    private RequestQueue queue;
    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_socwindow);

        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();
        String url = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
        page = new ThoughtPage(url);
        editText = findViewById(R.id.editText2);
        textView = findViewById(R.id.textView);
        textView.setMovementMethod(new ScrollingMovementMethod());
        queue = Volley.newRequestQueue(this);

        addQueue();

        View.OnClickListener clickListener = new View.OnClickListener(){
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.menu_settings:
                        //startActivity(new Intent(SOCWindow.this, SOCMenu.class));
                        break;
                    case R.id.left_button:
                        goLeft();
                        break;
                    case R.id.down_button:
                        goDown();
                        break;
                    case R.id.up_button:
                        goUp();
                        break;
                    case R.id.right_button:
                        goRight();
                        break;
                }
            }
        };

        Button menuButton = findViewById(R.id.menu_settings);
        menuButton.setOnClickListener(clickListener);

        Button leftButton = findViewById(R.id.left_button);
        leftButton.setOnClickListener(clickListener);

        Button downButton = findViewById(R.id.down_button);
        downButton.setOnClickListener(clickListener);

        Button upButton = findViewById(R.id.up_button);
        upButton.setOnClickListener(clickListener);

        Button rightButton = findViewById(R.id.right_button);
        rightButton.setOnClickListener(clickListener);


    }

    /** Called when the user taps the right button */
    public void goLeft() {
        if(page.left == null)
        {
            if(editText.getText().toString().equals("")) {
                return;
            }
            page.addRelation("left",editText.getText().toString());
            editText.setText("");
        }

        page = page.left;
        addQueue();
    }

    /** Called when the user taps the right button */
    public void goDown() {
        if(page.down == null)
        {
            if(editText.getText().toString().equals("")) {
                return;
            }
            page.addRelation("down",editText.getText().toString());
            editText.setText("");
        }

        page = page.down;
        addQueue();
    }

    /** Called when the user taps the right button */
    public void goUp() {
        if(page.up == null)
        {
            if(editText.getText().toString().equals("")) {
                return;
            }
            page.addRelation("up",editText.getText().toString());
            editText.setText("");
        }

        page = page.up;
        addQueue();
    }

    /** Called when the user taps the right button */
    public void goRight() {
        if(page.right == null)
        {
            if(editText.getText().toString().equals("")) {
                return;
            }
            page.addRelation("right",editText.getText().toString());
            editText.setText("");
        }

        page = page.right;
        addQueue();
    }

    public void addQueue(){
        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, page.getUrl(),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //textView.setText(getBody(response));
                        textView.setText(HtmlCompat.fromHtml(getBody(response),0));
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                textView.setText("Invalid URL");
            }
        });

        queue.add(stringRequest);
    }

    public String getBody(String s)
    {
        String[] arrOfStr = s.split("<div id=\"WikiaArticle\" class=\"WikiaArticle\">",2);
        return arrOfStr[1];
    }
}
