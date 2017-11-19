package com.pompom.pomji;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;


public class Intro extends AppCompatActivity {
    private Button setNameButton;
    private EditText name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        setNameButton = (Button)findViewById(R.id.setNameButton);
        name = (EditText)findViewById(R.id.name);
        SharedPreferences shared = getSharedPreferences("my_ref",MODE_PRIVATE);
        if(shared.getBoolean("first",true)) {
            setNameButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    SharedPreferences shared = getSharedPreferences("my_ref", MODE_PRIVATE);
                    SharedPreferences.Editor editor = shared.edit();
                    editor.putString("userName", name.getText().toString());
                    editor.putBoolean("first", true);
                    editor.commit();
                    Intent intent = new Intent(Intro.this, main.class);
                    startActivity(intent);
                }
            });
        }else{
            finish();
            Intent intent = new Intent(Intro.this, main.class);
            startActivity(intent);
        }
    }
}
