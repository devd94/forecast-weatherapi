package com.devina.weatherapplication.data.api.response;

import com.devina.weatherapplication.data.model.AutocompleteOption;

import java.util.List;

public class SearchAutocompleteResponse {

    private boolean isSuccess;
    private List<AutocompleteOption> autocompleteOptionList;
    private String failureMsg;

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    public List<AutocompleteOption> getAutocompleteOptionList() {
        return autocompleteOptionList;
    }

    public void setAutocompleteOptionList(List<AutocompleteOption> autocompleteOptionList) {
        this.autocompleteOptionList = autocompleteOptionList;
    }

    public String getFailureMsg() {
        return failureMsg;
    }

    public void setFailureMsg(String failureMsg) {
        this.failureMsg = failureMsg;
    }
}
