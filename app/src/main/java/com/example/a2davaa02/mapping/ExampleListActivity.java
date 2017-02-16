package com.example.a2davaa02.mapping;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

/**
 * Created by 2davaa02 on 16/02/2017.
 */
public class ExampleListActivity extends ListActivity {
    String[] names,details;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        names = new String[]{"Regular Map", "Cycle Map"};
        details = new String[]{"Driving paths","Cycling and walking paths"};
        MyAdapter adapter = new  MyAdapter();
        setListAdapter(adapter);
    }

    public void onListItemClick(ListView lv, View view, int index, long id) {
        boolean cyclemap = false;

        if (index == 1) {
            cyclemap = true;
        }

        Intent intent = new Intent();
        Bundle bundle = new Bundle();

        bundle.putBoolean("com.example.cyclemap", cyclemap);
        intent.putExtras(bundle);
        setResult(RESULT_OK, intent);
        finish();
    }

    public class MyAdapter extends ArrayAdapter<String>
    {
        public MyAdapter()
        {
            super(ExampleListActivity.this,android.R.layout.simple_list_item_1,names);
        }
        public View getView(int index, View convertView, ViewGroup parent)
        {
            View view=convertView;

            if(view==null)
            {
                LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = inflater.inflate(R.layout.poientry, parent, false);
            }

            TextView title=(TextView)view.findViewById(R.id.poi_name),detail=(TextView)view.findViewById(R.id.poi_descr);

            title.setText(names[index]);
            detail.setText(details[index]);

            return view;

        }
    }
}
