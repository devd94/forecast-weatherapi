package com.devina.weatherapplication.ui.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.app.ActivityCompat;
import androidx.viewpager.widget.ViewPager;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.devina.weatherapplication.BuildConfig;
import com.devina.weatherapplication.R;
import com.devina.weatherapplication.WeatherApplication;
import com.devina.weatherapplication.ui.contract.HomeMainContract;
import com.devina.weatherapplication.ui.presenter.HomePresenter;
import com.devina.weatherapplication.ui.adapter.HomeTabsAdapter;
import com.devina.weatherapplication.utils.CommonMethods;
import com.devina.weatherapplication.utils.WeatherUtils;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabLayout;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener,
        HomeMainContract.MainView, HomeMainContract.SearchView{

    AppCompatButton searchBtn, currLocForecastBtn;
    EditText searchEt;
    TabLayout tabLayout;
    ViewPager viewPager;
    ProgressBar homeProgressBar;

    Context context;

    HomeTabsAdapter homeTabsAdapter;

    HomeMainContract.Presenter mHomePresenter;

    private LocationManager locationManager;
    private Location mLocation;
    private LocationRequest locationRequest;
    private LocationSettingsRequest.Builder locReqBuilder;
    private LocationCallback locationCallback;

    List<String> permsToReq;


    private FusedLocationProviderClient fusedLocationProviderClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        initialise();
        listener();
    }

    private void initialise() {
        context = this;

        searchEt = findViewById(R.id.searchbar_et);
        searchBtn = findViewById(R.id.searchBtn);
        currLocForecastBtn = findViewById(R.id.currLocationForecastBtn);
        homeProgressBar = findViewById(R.id.home_progress);
        tabLayout = findViewById(R.id.home_tablayout);
        viewPager = findViewById(R.id.home_viewpager);

        homeTabsAdapter = new HomeTabsAdapter(context, getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(homeTabsAdapter);

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context);

        new HomePresenter(this, this, WeatherApplication.get(this).getDataManager());

        setUpLocationRequest();

        registerLocationCallBack();

        checkLocPermission();

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

    }

    private void listener() {
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        searchBtn.setOnClickListener(this);
        currLocForecastBtn.setOnClickListener(this);

        searchEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                mHomePresenter.onSearchTextChange();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.searchBtn) {
            mHomePresenter.onSearchButtonClick();
        } else if (v.getId() == R.id.currLocationForecastBtn) {
            onLoadCurrLocForecastClick();
        }
    }

    private void setUpLocationRequest() {
        locationRequest = LocationRequest.create();
        locationRequest.setInterval(WeatherUtils.UPDATE_INTERVAL);
        locationRequest.setFastestInterval(WeatherUtils.FAST_INTERVAL);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        locReqBuilder = new LocationSettingsRequest.Builder().
                addLocationRequest(locationRequest);
    }

    private void registerLocationCallBack()
    {
        locationCallback=new LocationCallback() {
            @Override
            public void onLocationResult(@NonNull @NotNull LocationResult locationResult) {

                if(locationResult == null)
                {
                    showMessage("Current location forecast is unavailable.");
                    showMessage("Search city to load forecast");
                }
                else
                {
                    Location location=locationResult.getLastLocation();
                    getCurrentLocation(location);
                }
            }
        };
    }

    private void checkLocPermission()
    {
        Log.e("LocationErr:", "Perm Step 1");
        //check permission
        permsToReq = CommonMethods.checkPermissions(context);
        if (permsToReq != null && permsToReq.size() > 0) {
            Log.e("LocationErr:", "Perm Step 2");
            requestPermissions(permsToReq.toArray(new String[permsToReq.size()]), WeatherUtils.PERMISSIONS_REQUEST);
        } else {
            Log.e("LocationErr:", "Perm Step 3");
            checkLocationSettings();
        }
    }

    private void checkLocationSettings() {
        Log.e("LocationErr:", "Settings Step 1");
        SettingsClient settingsClient = LocationServices.getSettingsClient(context);
        Task<LocationSettingsResponse> locSettingsTask = settingsClient.checkLocationSettings(locReqBuilder.build())
        .addOnSuccessListener(this, new OnSuccessListener<LocationSettingsResponse>() {
            @Override
            public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
                Log.e("LocationErr:", "Settings Step 2");
                //check for loc update
                startLocationUpdate();

            }
        })
        .addOnFailureListener(this, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull @NotNull Exception e) {

                if (e instanceof ResolvableApiException) {
                    Log.e("LocationErr:", "Settings Step 3");
                    try {
                        ResolvableApiException resolvableApiEx = (ResolvableApiException) e;

                        resolvableApiEx.startResolutionForResult(HomeActivity.this, WeatherUtils.RESOLVE_LOC_SETTINGS_REQUEST);
                        Log.e("LocationErr:", "Settings Step 4");
                    } catch (IntentSender.SendIntentException ise) {
                        ise.printStackTrace();
                    }
                }
            }
        });
    }

    private void onLoadCurrLocForecastClick() {

        Log.e("LocationErr:", "BtnClick Step 1");
        checkLocPermission();
    }

    private void startLocationUpdate() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Log.e("LocationErr:", "LocUpdate Step 1");
            return;
        }
        Log.e("LocationErr:", "LocUpdate Step 2");
        fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper());
    }

    private void stopLocationUpdate()
    {
        Log.e("LocationErr:", "StopUpdate Step 1");
        fusedLocationProviderClient.removeLocationUpdates(locationCallback);
    }

    private void getCurrentLocation(Location location) {
        Log.e("LocationErr:", "CurrLoc Step 1");
        if (location != null) {
//            Toast.makeText(context, "Get loc " + location.getLatitude() + ", " + location.getLongitude(), Toast.LENGTH_SHORT).show();
            mHomePresenter.setCurrentLatLon(location.getLatitude(), location.getLongitude());

            Log.e("LocationErr:", "CurrLoc Step 2");
            stopLocationUpdate();
        }
    }

    private void getLastKnownLoc() {
        Log.e("LocationErr:", "LastLoc Step 1");
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        Log.e("LocationErr:", "LastLoc Step 2");
        fusedLocationProviderClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        Log.e("LocationErr:", "LastLoc Step 3");
                        if (location != null) {
                            Log.e("LocationErr:", "LastLoc Step 4");

                            getCurrentLocation(location);
                        }
                    }
                });
    }

    private boolean hasPermission(String permission) {
        Log.e("LocationErr:", "HasPerm Step 1");
        return (checkSelfPermission(permission) != PackageManager.PERMISSION_DENIED);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == WeatherUtils.PERMISSIONS_REQUEST) {
            Log.e("LocationErr:", "ReqPerm Step 1");
            List<String> rejectedPermissions = new ArrayList<>();

            for (String permission : permsToReq) {
                if (!hasPermission(permission)) {
                    rejectedPermissions.add(permission);
                }
            }

            if (rejectedPermissions.size() > 0) {
                permsToReq = rejectedPermissions;
                Log.e("LocationErr:", "ReqPerm Step 2");
                showPermissionRejectedDialog();
            } else {
                Log.e("LocationErr:", "ReqPerm Step 3");
                checkLocationSettings();
            }

        }
    }


    private void showPermissionRejectedDialog() {

        Log.e("LocationErr:", "PermRej Step 1");
        AlertDialog.Builder dialog = new AlertDialog.Builder(context);
        dialog.setMessage("Permissions are required to show forecast of current location.\nPlease update permissions in device Settings to continue.")
                .setPositiveButton("Settings", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Intent settingsIntent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        Uri uri = Uri.fromParts("package", getPackageName(), null);

                        Log.e("LocationErr:", "PermRej Step 2");
                        settingsIntent.setData(uri);
                        dialog.dismiss();

                        startActivity(settingsIntent);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Log.e("LocationErr:", "PermRej Step 3");
                        dialog.dismiss();

//                        showMessage("Current location forecast is unavailable.");
                        showMessage("Search city to load forecast");
                    }
                })
                .show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @androidx.annotation.Nullable @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == WeatherUtils.RESOLVE_LOC_SETTINGS_REQUEST) {
            if (resultCode == RESULT_OK) {
                Log.e("LocationErr:", "SettingReq Step 1");
                startLocationUpdate();

            }
            else {
                Log.e("LocationErr:", "SettingReq Step 2");
                getLastKnownLoc();
            }
        }
    }

    @Override
    public void setPresenter(HomeMainContract.Presenter presenter) {

        mHomePresenter = presenter;
    }

    @Override
    public String getSearchText() {
        return searchEt.getText().toString();
    }

    @Override
    public void showProgress() {
        if (homeProgressBar.getVisibility() == View.GONE) {
            homeProgressBar.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void hideProgress() {

        if (homeProgressBar.getVisibility() == View.VISIBLE) {
            homeProgressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public void showMessage(String msg) {

        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        mHomePresenter.destroy();
    }

//
//    @Override
//    protected void onPause() {
//        super.onPause();
//
//        stopLocationUpdate();
//    }

    private void enableGPSDialog() {
        AlertDialog.Builder gpsDialog = new AlertDialog.Builder(context);
        gpsDialog.setMessage("Enable GPS")
                .setPositiveButton("GPS Settings", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Intent gpsIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);

                        dialog.dismiss();

                        startActivity(gpsIntent);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();
                    }
                })
                .show();
    }
}