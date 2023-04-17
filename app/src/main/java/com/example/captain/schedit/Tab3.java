package com.example.captain.schedit;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Tab3.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Tab3#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Tab3 extends Fragment implements OnDateSelectedListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private MaterialCalendarView mcv = null;
    private Boolean mLoaded = false;

    private OnFragmentInteractionListener mListener;
    public static ArrayList<CalendarDay> mDayList = new ArrayList<>();

    public Tab3() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Tab3.
     */
    // TODO: Rename and change types and number of parameters
    public static Tab3 newInstance(String param1, String param2) {
        Tab3 fragment = new Tab3();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View frag3 = inflater.inflate(R.layout.fragment_tab3, container, false);;

        //set a refresh listener
        ((MainActivity)getActivity()).setFragmentRefreshListener(new MainActivity.FragmentRefreshListener() {
            @Override
            public void onRefresh() {
                refreshTab();
                // Refresh Your Fragment
            }
        });

        // Inflate the layout for this fragment
        return frag3;


    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //use the view to mark some events on the calendar view
        MaterialCalendarView mcv = (MaterialCalendarView)view.findViewById(R.id.calendarView);

        mcv.setOnDateChangedListener(this);
        //mcv.setOnMonthChangedListener(this);

        //mark today on the calendar
        mcv.addDecorator(new TodayDecorator());

        //mark some other days
        List<CalendarDay> days = new ArrayList<>();
        if (!mLoaded) {//when screen is first viewed, dates are read from the database
            DbHelper dbHelper = new DbHelper(getActivity());
            days.addAll(dbHelper.getCalendarDayList());
            mLoaded = true;
        }
        //days.add(CalendarDay.today());
        days.add(CalendarDay.from(new Date(117, 11, 12)));//first day of hanukkah
        days.add(CalendarDay.from(new Date(117,11,25)));//xmas
        days.add(CalendarDay.from(new Date(117,11,26)));//first day of kwanzaa
        int colorRed = 0xffff0000;
        mcv.addDecorator( new EventDecorator(colorRed, days));
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }


    @Override
    public void onResume() {
        super.onResume();
        refreshTab();
    }

    private void refreshTab()
    {
        mcv = (MaterialCalendarView)getView().findViewById(R.id.calendarView);
        if (mcv != null)
        {
            int colorBlue = 0xff0000ff;
            mcv.addDecorator( new EventDecorator(colorBlue, mDayList));
            //mcv.invalidateDecorators();
        }
        else
        {
            CharSequence failure = "NULL ptr error on refresh view";
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(getContext(), failure, duration);
            toast.show();
            mDayList.clear();
        }
    }

    @Override
    public void onDateSelected(@NonNull MaterialCalendarView widget, @Nullable CalendarDay date, boolean selected) {
        //CharSequence text = "Permission Denied";
        DbHelper dbHelper = new DbHelper(getActivity());
        mLoaded = true;
        String sDate = ("" + date.getYear() + "/" + (date.getMonth() + 1)+ "/" + date.getDay());
        ArrayList<String> a = dbHelper.getEventFromDay(sDate);
        String taskText = new String();
        taskText += "Events for " + sDate + ":";
        for (int i = 0; i < a.size(); ++i)
        {
            taskText += "\n";
            taskText += a.get(i);
        }
        if (a.isEmpty())
        {
            String text = "No Events on " + sDate;
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(getContext(), text, duration);
            toast.show();
        }
        else
        {
            //String text = "No Events planned for that day";
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(getContext(), taskText, duration);
            toast.show();
        }
        //textView.setText(getSelectedDatesString());
    }
}
