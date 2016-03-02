package org.darkerthanblack.videodownloader;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    EditText videoUrlET,typeET;
    //Button searchBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
                LayoutInflater inflater = getLayoutInflater();
                final View layout = inflater.inflate(R.layout.new_video_url,null);
                new AlertDialog.Builder(MainActivity.this).setTitle(getString(R.string.url)).setView(layout)
                .setPositiveButton(getString(R.string.search), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        videoUrlET = (EditText) layout.findViewById(R.id.video_url);
                        typeET = (EditText) layout.findViewById(R.id.type);
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                Downloader.download(videoUrlET.getText().toString(),typeET.getText().toString());
                            }
                        }).start();
                    }
                })
                .setNegativeButton(getString(R.string.cancel), null).show();
            }
        });
        /*videoUrlET = (EditText) findViewById(R.id.video_url);
        typeET = (EditText) findViewById(R.id.type);
        searchBtn = (Button) findViewById(R.id.search);

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Downloader.download(videoUrlET.getText().toString(),typeET.getText().toString());
                    }
                }).start();

            }
        });*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
