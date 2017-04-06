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

    double defalutLongitude=27.578;
    double defalutLatitude=47.166;

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

        Intent intent = new Intent();
        Bundle bundle=new Bundle();

        bundle.putDouble("com.example.setlat",la);
        bundle.putDouble("com.example.setlon",lo);

        intent.putExtras(bundle);
        setResult(RESULT_OK,intent);
        finish();
    }
}
