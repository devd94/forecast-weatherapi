package com.devina.weatherapplication.data.api.request;

import android.util.Log;

import com.devina.weatherapplication.data.api.ApiHelper;
import com.devina.weatherapplication.data.api.ApiService;
import com.devina.weatherapplication.data.api.response.SearchAutocompleteResponse;
import com.devina.weatherapplication.data.model.AutocompleteOption;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchAutocompleteRequest {

    private ApiHelper mApiHelper;

    public SearchAutocompleteRequest(ApiHelper apiHelper)
    {
        mApiHelper=apiHelper;
    }

    public void requestSearchAutocompleteInput()
    {
        ApiService service= mApiHelper.getApiDataGenerator().createService(ApiService.class);

        Call<List<AutocompleteOption>> asyncCall=service.getAutocompleteOptions(mApiHelper.getApiParams().getApiKey(),
                mApiHelper.getApiParams().getParamQ());

        Log.e("weatherApiErr:", asyncCall.request().url().toString());

        asyncCall.enqueue(new Callback<List<AutocompleteOption>>() {
            @Override
            public void onResponse(Call<List<AutocompleteOption>> call, Response<List<AutocompleteOption>> response) {

                if(response.isSuccessful())
                {
                    List<AutocompleteOption> autocompleteOptionList=response.body();

                    SearchAutocompleteResponse autocompleteResponse=new SearchAutocompleteResponse();
                    autocompleteResponse.setSuccess(true);
                    autocompleteResponse.setAutocompleteOptionList(autocompleteOptionList);
                    autocompleteResponse.setFailureMsg("null");

                    mApiHelper.getApiResponse().setSearchAutocompleteResponse(autocompleteResponse);
                }
                else
                {
                    SearchAutocompleteResponse autocompleteResponse=new SearchAutocompleteResponse();
                    autocompleteResponse.setSuccess(false);
                    autocompleteResponse.setAutocompleteOptionList(null);
                    autocompleteResponse.setFailureMsg(response.message());

                    mApiHelper.getApiResponse().setSearchAutocompleteResponse(autocompleteResponse);
                }
            }

            @Override
            public void onFailure(Call<List<AutocompleteOption>> call, Throwable t) {

                SearchAutocompleteResponse autocompleteResponse=new SearchAutocompleteResponse();
                autocompleteResponse.setSuccess(false);
                autocompleteResponse.setAutocompleteOptionList(null);
                autocompleteResponse.setFailureMsg(t.getMessage());

                mApiHelper.getApiResponse().setSearchAutocompleteResponse(autocompleteResponse);

            }
        });
    }
}
