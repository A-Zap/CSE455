package com.example.captain.schedit;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Captain on 12/6/2017.
 */

public class EventListAdapter extends ArrayAdapter<CalEvent> {

    public EventListAdapter(@NonNull final Context context, final List<CalEvent> elements) {
        super(context, 0, elements);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        CalEvent currentEvent = getItem(position);

        View view = convertView;

        if (view == null)
            view = LayoutInflater.from(getContext()).inflate(R.layout.row, parent , false);

        ((TextView)view.findViewById(R.id.task_title)).setText(currentEvent.getName());
        ((TextView)view.findViewById(R.id.warningBox)).setText(currentEvent.getFavorite());
        ((TextView)view.findViewById(R.id.dateBox)).setText(currentEvent.getDate());

        return view;
    }
}
