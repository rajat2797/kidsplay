package com.kidminks.xxx.image2to5;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import java.util.List;

/**
 * Created by rajat on 17/1/17.
 */
public class ObjectAdapter extends ArrayAdapter<collection> {
    public ObjectAdapter(Context context, int resource, List<collection> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = ((Activity) getContext()).getLayoutInflater().inflate(R.layout.objects, parent, false);
        }

        ImageView object = (ImageView) convertView.findViewById(R.id.object_item);

        collection item = getItem(position);


        return convertView;
    }
}
