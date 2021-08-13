package com.devina.weatherapplication.ui.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.devina.weatherapplication.R;
import com.devina.weatherapplication.data.model.ForecastObj;
import com.devina.weatherapplication.utils.CommonMethods;

import java.util.List;

public class NextDayForecastAdapter extends RecyclerView.Adapter<NextDayForecastAdapter.ViewHolder> {

    Context context;
    List<ForecastObj> forecastObjList;

    public NextDayForecastAdapter(Context context, List<ForecastObj> forecastObjList)
    {
        this.context=context;
        this.forecastObjList=forecastObjList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater=LayoutInflater.from(context);

        View view=inflater.inflate(R.layout.item_next_day_forecast, parent, false);

        ViewHolder holder = new ViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull NextDayForecastAdapter.ViewHolder holder, int position) {

        holder.dateTv.setText(CommonMethods.formatDateString(forecastObjList.get(position).getDate()));

        holder.maxTempTv.setText(forecastObjList.get(position).getDay().getMaxTempC()
                +context.getResources().getString(R.string.degreeCelsius));

        holder.minTempTv.setText(forecastObjList.get(position).getDay().getMinTempC()
                +context.getResources().getString(R.string.degreeCelsius));

        holder.conditionTv.setText(forecastObjList.get(position).getDay().getCondition().getText());

        String icon=forecastObjList.get(position).getDay().getCondition().getIcon();
        Bitmap iconBitmap=CommonMethods.getIconDrawable(CommonMethods.getIconFileName(icon), context);
        if(iconBitmap!=null)
            holder.forecastIconIv.setImageBitmap(iconBitmap);
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return forecastObjList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder
    {
        ImageView forecastIconIv;
        TextView dateTv, conditionTv, maxTempTv, minTempTv;

        public ViewHolder(View view)
        {
            super(view);

            forecastIconIv=view.findViewById(R.id.forecastItemIconIv);
            dateTv=view.findViewById(R.id.nextForecastDateTv);
            conditionTv=view.findViewById(R.id.nextForecastConditionTv);
            maxTempTv=view.findViewById(R.id.nextForecastMaxTempTv);
            minTempTv=view.findViewById(R.id.nextForecastMinTempTv);
        }
    }
}
