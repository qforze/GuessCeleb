package com.loloc.guessceleb;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.util.ArrayList;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    ImageView imageView;
    Button buttons[];
    ArrayList<String> names;
    ArrayList<String> pictures;
    int namern;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = findViewById(R.id.imageView);
        websiteInfo();

        buttons = new Button[4];
        buttons[0] = findViewById(R.id.button1);
        buttons[1] = findViewById(R.id.button2);
        buttons[2] = findViewById(R.id.button3);
        buttons[3] = findViewById(R.id.button4);

    }


    void websiteInfo() {

        Ion.with(this).load("https://www.imdb.com/list/ls052283250/").asString().setCallback(new FutureCallback<String>() {
            @Override
            public void onCompleted(Exception e, String result) {

                Pattern p = Pattern.compile("pst\"\n" +
                        "> <img alt=\"(.*?)\"");
                Matcher m = p.matcher(result);
                names = new ArrayList<>();

                while (m.find()) {

                    names.add(m.group(1));

                }

                Log.i("info123", String.valueOf(names));

                p = Pattern.compile("src=\"(.*?)\"\n" +
                        "width=\"");
                m = p.matcher(result);
                pictures = new ArrayList<>();

                while (m.find()) {

                    pictures.add(m.group(1));

                }

                Random c = new Random();
                int n = c.nextInt(pictures.size());

                String urlImage = pictures.get(n);

                loadPicture(urlImage);

                String urlName = names.get(n);

                names(urlName);
            }


        });
    }

    void loadPicture(String urlImage) {

        Ion.with(this).load(urlImage).intoImageView(imageView);

    }

    void names(String urlName) {

        Random name = new Random();
        namern = name.nextInt(4);
        for (int i = 0; i < 4; i++) {

            if (i == namern) {

                buttons[i].setText(urlName);

            } else {
                Random c = new Random();
                int n = c.nextInt(names.size());
                String randomName = names.get(n);

                buttons[i].setText(randomName);
            }
        }
    }
    void nextTry() {

        Random c = new Random();
        int n = c.nextInt(pictures.size());

        String urlImage = pictures.get(n);

        loadPicture(urlImage);

        String urlName = names.get(n);

        names(urlName);

    }
    public void onClick(View view) {

        String tag = view.getTag().toString();
        int tagint = Integer.parseInt(tag);
        Log.i("tagint", String.valueOf(tagint));

        if (tagint == namern) {

            Toast.makeText(this, "Correct!", Toast.LENGTH_SHORT).show();

        } else {

            Toast.makeText(this, "Wrong!", Toast.LENGTH_SHORT).show();

        }
        nextTry();
    }
}