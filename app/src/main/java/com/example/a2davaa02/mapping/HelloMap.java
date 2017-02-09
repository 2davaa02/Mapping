package com.example.a2davaa02.mapping;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import org.osmdroid.config.Configuration;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;

public class HelloMap extends Activity implements View.OnClickListener

{

    MapView mv;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // this line tells OpenStreetMap about our app.
        // If you miss this out, you might get banned from OSM servers
        Configuration.getInstance().load
                (this, PreferenceManager.getDefaultSharedPreferences(this));

        mv = (MapView)findViewById(R.id.map1);

        mv.setBuiltInZoomControls(true);
        mv.getController().setZoom(14);
        mv.getController().setCenter(new GeoPoint(40.1,22.5));

        Button btn   = (Button)findViewById(R.id.searchLocation);
        btn.setOnClickListener(this);
    }

    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.menu_hello_map,menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item)
    {
        if(item.getItemId()==R.id.choosemap)
        {

            Intent intent = new Intent(this,MapChooseActivity.class);
            startActivity(intent);
            //code
            return true;
        }
        return false;
    }


    @Override
    public void onClick(View view) {
        EditText lat = (EditText)findViewById(R.id.latitude);
        EditText lon = (EditText)findViewById(R.id.longitude);

        if(lat.getText().toString().equals("")||lon.getText().toString().equals(""))
        {
            mv.getController().setZoom(7);
            mv.getController().setCenter(new GeoPoint(0.0,0.0));
        }
        else
        {
            Float la = Float.parseFloat(lat.getText().toString());
            Float lo = Float.parseFloat(lon.getText().toString());

            mv.getController().setZoom(2);
            mv.getController().setCenter(new GeoPoint(la, lo));
        }
    }
}