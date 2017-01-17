package com.kidminks.xxx.image2to5;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.TextViewCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link colorexamples.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link colorexamples#newInstance} factory method to
 * create an instance of this fragment.
 */
public class colorexamples extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public colorexamples() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment colorexamples.
     */
    // TODO: Rename and change types and number of parameters
    public static colorexamples newInstance(String param1, String param2) {
        colorexamples fragment = new colorexamples();
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

    ImageView color_button, down_arrow;
    TextView color_name;
    ListView objectslv;
    ObjectAdapter objectAdapter;

    String cname;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if( getArguments()!= null)
            cname = getArguments().getString("color_name");
        else
            cname="black";
        return inflater.inflate(R.layout.fragment_colorexamples, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        down_arrow = (ImageView) getView().findViewById(R.id.down_arrow);
        color_button = (ImageView) getView().findViewById(R.id.color_button);
        color_name = (TextView) getView().findViewById(R.id.color_name);

        color_name.setText(cname);
        int drawable_color_id = getResources().getIdentifier(cname.toLowerCase()+"_circle", "drawable", getActivity().getPackageName());
        color_button.setImageResource(drawable_color_id);

        objectslv = (ListView) getView().findViewById(R.id.objectslv);
        List<collection> mcollection = new ArrayList<>();
        objectAdapter = new ObjectAdapter(getContext(), R.layout.objects, mcollection);
        objectslv.setAdapter(objectAdapter);

        down_arrow.setOnClickListener(this);

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
    public void onClick(View v) {
        if(v == down_arrow) {
            getActivity().getSupportFragmentManager().beginTransaction().remove(this).commit();
        }
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
}
