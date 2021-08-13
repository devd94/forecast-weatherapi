package com.devina.weatherapplication.ui.contract;

import com.devina.weatherapplication.base.MvpPresenter;
import com.devina.weatherapplication.base.MvpView;
import com.devina.weatherapplication.data.model.AutocompleteOption;
import com.google.android.gms.location.LocationSettingsRequest;

import java.util.List;

public interface HomeMainContract {

    interface MainView extends MvpView<Presenter>
    {
        void showProgress();
        void hideProgress();

        void showMessage(String msg);
    }

    interface SearchView extends MvpView<Presenter>
    {
        String getSearchText();
    }

    interface Presenter extends MvpPresenter
    {
        void setCurrentLatLon(double  latitude, double longitude);

        void  initForecastRequest(String paramQ);

        void onSearchTextChange();

        void getSearchAutocompleteOptions(List<AutocompleteOption> autocompleteOptionList);

        void onSearchButtonClick();

        void destroy();
    }
}
