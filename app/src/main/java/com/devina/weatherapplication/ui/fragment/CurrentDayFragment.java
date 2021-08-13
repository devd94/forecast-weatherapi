package com.devina.weatherapplication.ui.fragment;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.devina.weatherapplication.R;
import com.devina.weatherapplication.WeatherApplication;
import com.devina.weatherapplication.ui.contract.CurrDayFragContract;
import com.devina.weatherapplication.ui.presenter.CurrDayFragPresenter;
import com.devina.weatherapplication.utils.CommonMethods;

import java.io.IOException;
import java.io.InputStream;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CurrentDayFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CurrentDayFragment extends Fragment implements View.OnClickListener,
        CurrDayFragContract.CurrForecastBaseView, CurrDayFragContract.TempChangeView {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    TextView locationTv, dateTv, actualTempTv, feelsLikeTempTv, windSpeedTv, windDegTv, windDirTv,
            humidityTv, cloudCoverTv, precipitationTv;
    AppCompatButton tempUnitChangeBtn;
    ImageView weatherIconIv;

    CurrDayFragContract.Presenter mCurrDayFragPresenter;

    String feelsLikeText, degCelUnit,degFahrenUnit, windSpUnit,windDegUnit, humidUnit, cloudUnit, precipUnit;

    public CurrentDayFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CurrentDayFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CurrentDayFragment newInstance(String param1, String param2) {
        CurrentDayFragment fragment = new CurrentDayFragment();
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
        return inflater.inflate(R.layout.fragment_current_day, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initialise(view);
        listener();
    }

    private void initialise(View view)
    {
        locationTv=view.findViewById(R.id.currLocationTv);
        dateTv=view.findViewById(R.id.currDateTv);
        actualTempTv=view.findViewById(R.id.currtemp);
        feelsLikeTempTv=view.findViewById(R.id.currFeelsLikeTemp);
        windSpeedTv=view.findViewById(R.id.currWindSpeed);
        windDegTv=view.findViewById(R.id.currWindDegree);
        windDirTv=view.findViewById(R.id.currWindDir);
        humidityTv=view.findViewById(R.id.currHumidity);
        cloudCoverTv=view.findViewById(R.id.currCloud);
        precipitationTv=view.findViewById(R.id.currPrecipitation);
        tempUnitChangeBtn=view.findViewById(R.id.tempUnitChangeBtn);
        weatherIconIv =view.findViewById(R.id.currWeatherIcon);

        new CurrDayFragPresenter(this, this,
                WeatherApplication.get(getContext()).getDataManager());

        feelsLikeText=getContext().getResources().getString(R.string.feelsLikeText);
        degCelUnit=getContext().getResources().getString(R.string.degreeCelsius);
        degFahrenUnit=getContext().getResources().getString(R.string.degreeFahrenheit);
        windSpUnit=getContext().getResources().getString(R.string.windSpeedUnit);
        windDegUnit=getContext().getResources().getString(R.string.windDegreeUnit);
        humidUnit=getContext().getResources().getString(R.string.humidityUnit);
        cloudUnit=getContext().getResources().getString(R.string.cloudUnit);
        precipUnit=getContext().getResources().getString(R.string.precipitationUnit);
    }

    private void listener()
    {
        tempUnitChangeBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        if(v.getId() == R.id.tempUnitChangeBtn)
        {
            mCurrDayFragPresenter.onTempChangeButtonClick();
        }
    }

    @Override
    public void setPresenter(CurrDayFragContract.Presenter presenter) {

        mCurrDayFragPresenter=presenter;
    }

    @Override
    public void setForecastData(String location, String date, String actualTempCelsius, String feelsLikeCelsiusTemp,
                                String windSpeed, String windDeg, String windDir, String humidity, String cloud,
                                String precip, String icon) {

        locationTv.setText(location);
        dateTv.setText(date);
        actualTempTv.setText(actualTempCelsius + degCelUnit);
        feelsLikeTempTv.setText("("+ feelsLikeText+" "+feelsLikeCelsiusTemp+degCelUnit+")");
        windSpeedTv.setText(windSpeed+" "+windSpUnit);
        windDegTv.setText(windDeg+windDegUnit);
        windDirTv.setText(windDir);
        humidityTv.setText(humidity+" "+humidUnit);
        cloudCoverTv.setText(cloud+" "+cloudUnit);
        precipitationTv.setText(precip+" "+precipUnit);


        Bitmap iconBitmap=CommonMethods.getIconDrawable(CommonMethods.getIconFileName(icon), getContext());
        if(iconBitmap!=null)
            weatherIconIv.setImageBitmap(iconBitmap);
    }

    @Override
    public boolean isChangeToCelsius() {
        if(tempUnitChangeBtn.getText().toString().equalsIgnoreCase(
                getContext().getResources().getString(R.string.tempUnitBtn_toCelsius)))
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    @Override
    public void setActualTemperature(boolean isCelsius, String temperature) {

        if(isCelsius)
        {
            actualTempTv.setText(temperature+degCelUnit);

            tempUnitChangeBtn.setText(getContext().getResources().getString(R.string.tempUnitBtn_toFahrenheit));
        }
        else
        {
            actualTempTv.setText(temperature+degFahrenUnit);

            tempUnitChangeBtn.setText(getContext().getResources().getString(R.string.tempUnitBtn_toCelsius));
        }


    }

    @Override
    public void setFeelsLikeTemperature(boolean isCelsius, String temperature) {

        if(isCelsius)
        {
            feelsLikeTempTv.setText("("+ feelsLikeText+" "+temperature+degCelUnit+")");
        }
        else
        {
            feelsLikeTempTv.setText("("+ feelsLikeText+" "+temperature+degFahrenUnit+")");
        }
    }


}