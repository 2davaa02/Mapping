package com.example.a2davaa02.mapping;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.osmdroid.config.Configuration;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.views.overlay.ItemizedIconOverlay;
import org.osmdroid.views.overlay.ItemizedIconOverlay;
import org.osmdroid.views.overlay.OverlayItem;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class HelloMap extends Activity

{

    MapView mv;
    boolean returned = false;
    ItemizedIconOverlay<OverlayItem> items;
    ItemizedIconOverlay.OnItemGestureListener<OverlayItem> markerGestureListener;
    ItemizedIconOverlay<OverlayItem> error;

    class MyTask extends AsyncTask<Void,Void,ItemizedIconOverlay<OverlayItem>>
    {
        public ItemizedIconOverlay<OverlayItem> doInBackground(Void... unused)
        {
            HttpURLConnection conn = null;
            try
            {
                URL url = new URL("http://www.free-map.org.uk/course/mad/poi.txt");
                conn = (HttpURLConnection) url.openConnection();
                InputStream in = conn.getInputStream();

                if(conn.getResponseCode() == 200)
                {
                    markerGestureListener = new ItemizedIconOverlay.OnItemGestureListener<OverlayItem>()
                    {
                        public boolean onItemLongPress(int i, OverlayItem item)
                        {
                            Toast title=Toast.makeText(HelloMap.this,item.getTitle(),Toast.LENGTH_SHORT);
                            title.setGravity(Gravity.CENTER,0,0);
                            title.show();

                            Toast.makeText(HelloMap.this, item.getSnippet(), Toast.LENGTH_SHORT).show();
                            return true;

                        }

                        public boolean onItemSingleTapUp(int i, OverlayItem item)
                        {
                            Toast title=Toast.makeText(HelloMap.this,item.getTitle(),Toast.LENGTH_SHORT);
                            title.setGravity(Gravity.CENTER,0,0);
                            title.show();

                            Toast.makeText(HelloMap.this, item.getSnippet(), Toast.LENGTH_SHORT).show();
                            return true;
                        }
                    };

                    items=new ItemizedIconOverlay<OverlayItem>(HelloMap.this,new ArrayList<OverlayItem>(),markerGestureListener);
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in));

                    String line;
                    while((line = reader.readLine()) != null)
                    {
                        String[] components = line.split(",");
                        if(components.length==5)
                        {
                            OverlayItem item=new OverlayItem(components[0],components[2],new GeoPoint(Double.parseDouble(components[4]),Double.parseDouble(components[3])));
                            if(components[1].equals("pub"))
                            {
                                item.setMarker(getResources().getDrawable(R.drawable.pub));
                            }
                            else if(components[1].equals("restaurant"))
                            {
                                item.setMarker(getResources().getDrawable(R.drawable.restaurant));

                            }
                            items.addItem(item);
                        }
                    }

                    return items;
                }
                else
                    return error;


            }
            catch(IOException e)
            {
                return error;
            }
            finally
            {
                if(conn!=null)
                    conn.disconnect();
            }
        }

        public void onPostExecute(ItemizedIconOverlay<OverlayItem> items)
        {
            mv.getOverlays().add(items);
        }
    }


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

        MyTask t=new MyTask();
        t.execute();

    }

    public void onResume()
    {

        super.onResume();
        if(returned==false) {
            SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            double lat = Double.parseDouble(pref.getString("lat", "47.1666"));
            double lon = Double.parseDouble(pref.getString("lon", "27.5787"));
            int zoom = Integer.parseInt(pref.getString("zoom", "14"));

            mv.getController().setZoom(zoom);
            mv.getController().setCenter(new GeoPoint(lat, lon));

            if (pref.getString("style", "R").equals("R")) {
                mv.setTileSource(TileSourceFactory.MAPNIK);
            } else {
                mv.setTileSource(TileSourceFactory.CYCLEMAP);
            }
        }
    }

    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.menu_hello_map,menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item)
    {
        returned = false;
        if(item.getItemId()==R.id.choosemapstylelist)
        {
            Intent intent = new Intent(this,ExampleListActivity.class);
            startActivityForResult(intent,0);

            return true;
        }
        else if(item.getItemId()==R.id.setLocation)
        {
            Intent intent = new Intent(this,SetMapLocation.class);
            startActivityForResult(intent,1);

            return true;
        }
        else if(item.getItemId()==R.id.Preferences)
        {
            Intent intent=new Intent(this,MyPrefereceActivity.class);
            startActivityForResult(intent,2);
        }
        return false;
    }

    protected void onActivityResult(int requestCode,int resultCode,Intent intent)
    {

        if(requestCode==0)
        {

            if (resultCode==RESULT_OK)
            {
                Bundle extras=intent.getExtras();
                boolean cyclemap = extras.getBoolean("com.example.cyclemap");
                if(cyclemap)
                {
                    mv.setTileSource(TileSourceFactory.CYCLEMAP);
                }
                else
                {
                    mv.setTileSource(TileSourceFactory.MAPNIK);
                }

            }
            returned = true;
        }
        else if(requestCode==1)
        {
            if (resultCode==RESULT_OK)
            {
                Bundle extras=intent.getExtras();
                double lat= extras.getDouble("com.example.setlat");
                double lon= extras.getDouble("com.example.setlon");

                mv.getController().setZoom(10);
                mv.getController().setCenter(new GeoPoint(lat, lon));

            }
            returned = true;
        }

    }

}