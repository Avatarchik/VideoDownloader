package org.darkerthanblack.videodownloader;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import org.xutils.x;
/**
 * Created by Jay on 16/3/3.
 */
public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
    }
}
