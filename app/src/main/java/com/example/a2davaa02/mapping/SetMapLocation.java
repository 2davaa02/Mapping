package com.example.a2davaa02.mapping;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by 2davaa02 on 09/02/2017.
 */
public class SetMapLocation extends Activity implements View.OnClickListener{

    double defalutLongitude=40.1;
    double defalutLatitude=22.5;

    public void onCreate(Bundle savedInstancesState)
    {
        super.onCreate(savedInstancesState);
        setContentView(R.layout.setlocation);

        Button btn  = (Button)findViewById(R.id.searchLocation);
        btn.setOnClickListener(this);
    }
    @Override
    public void onClick(View view) {
        EditText lat = (EditText) findViewById(R.id.latitude);
        EditText lon = (EditText) findViewById(R.id.longitude);

        double la = defalutLatitude;
        double lo = defalutLongitude;

        if (!lat.getText().toString().equals("") && !lon.getText().toString().equals("")) {

            la = Double.parseDouble(lat.getText().toString());
            lo = Double.parseDouble(lon.getText().toString());
        }

        double[] coordinates= {la,lo};
        Intent intent = new Intent();
        Bundle bundle=new Bundle();

        bundle.putDoubleArray("com.example.setlocation",coordinates);
        intent.putExtras(bundle);
        setResult(RESULT_OK,intent);
        finish();
    }
}