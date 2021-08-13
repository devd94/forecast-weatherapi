package com.devina.weatherapplication.ui.fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.devina.weatherapplication.R;
import com.devina.weatherapplication.WeatherApplication;
import com.devina.weatherapplication.data.model.DayForecast;
import com.devina.weatherapplication.data.model.ForecastObj;
import com.devina.weatherapplication.ui.adapter.NextDayForecastAdapter;
import com.devina.weatherapplication.ui.contract.NextDayFragContract;
import com.devina.weatherapplication.ui.presenter.NextDayFragPresenter;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NextDaysFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NextDaysFragment extends Fragment implements NextDayFragContract.View{

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    TextView locationTv;
    RecyclerView nextForecastsRv;

    Context context;

    NextDayForecastAdapter nextDayForecastAdapter;

    NextDayFragContract.Presenter presenter;

    List<ForecastObj> forecastObjList;

    public NextDaysFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NextDaysFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NextDaysFragment newInstance(String param1, String param2) {
        NextDaysFragment fragment = new NextDaysFragment();
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
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_next_days, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initialise(view);
    }

    private void initialise(View view)
    {
        context=getContext();

        nextForecastsRv=view.findViewById(R.id.nextForecastsRv);
        locationTv=view.findViewById(R.id.nextDayLocationTv);

        LinearLayoutManager lm = new LinearLayoutManager(context);

        nextForecastsRv.setLayoutManager(lm);

        new NextDayFragPresenter(this, WeatherApplication.get(getContext()).getDataManager());

//        setUpForecastList();
    }

    private void setUpForecastList(List<ForecastObj> forecastList)
    {
        nextDayForecastAdapter=new NextDayForecastAdapter(context, forecastList);
        nextForecastsRv.setAdapter(nextDayForecastAdapter);
    }

    @Override
    public void setLocationText(String location) {

        locationTv.setText(location);
    }

    @Override
    public void setForecastData(DayForecast dayForecast) {

        if(dayForecast != null)
        {
            forecastObjList=dayForecast.getForecast().getForecastObjList();

            if(forecastObjList!=null && forecastObjList.size()>0)
            {
                List<ForecastObj> nextDayForecastList=new ArrayList<>();

                for(int i=1; i<forecastObjList.size();i++)
                {
                    nextDayForecastList.add(forecastObjList.get(i));
                }

                setUpForecastList(nextDayForecastList);
            }
        }
    }

    @Override
    public void setPresenter(NextDayFragContract.Presenter presenter) {

        this.presenter=presenter;
    }
}