package com.mystihgreeh.go4lunch.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.libraries.places.api.model.Place;
import com.mystihgreeh.go4lunch.R;
import com.mystihgreeh.go4lunch.viewModel.SharedViewModel;
import com.mystihgreeh.go4lunch.viewModel.ViewModelFactory;
import com.mystihgreeh.go4lunch.api.Injection;
import com.mystihgreeh.go4lunch.model.Restaurants.Result;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Observer;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;

public class MapViewFragment extends Fragment implements OnMapReadyCallback, LocationListener, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener{

    private GoogleMap mMap;
    Location mLastLocation;
    Marker mCurrLocationMarker;
    GoogleApiClient mGoogleApiClient;
    LocationRequest mLocationRequest;
    private MapView mapView;
    private static final String MAP_VIEW_BUNDLE_KEY = "MapViewBundleKey";
    private SharedViewModel sharedViewModel;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_maps, container, false);

        mapView = view.findViewById(R.id.map_view);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(googleMap -> {
            mMap = googleMap;
            mLocationRequest = new LocationRequest();
            mLocationRequest.setInterval(1000);
            mLocationRequest.setFastestInterval(1000);
            mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (ContextCompat.checkSelfPermission(requireActivity(),
                        ACCESS_FINE_LOCATION)
                        == PackageManager.PERMISSION_GRANTED) {
                    buildGoogleApiClient();
                    mMap.setMyLocationEnabled(true);
                }
            } else {
                buildGoogleApiClient();
                mMap.setMyLocationEnabled(true);
            }
            uiSettings();
            initViewModel();
        });
        return view;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(requireActivity(),
                    ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                buildGoogleApiClient();
                mMap.setMyLocationEnabled(true);
            }
        }
        else {
            buildGoogleApiClient();
            mMap.setMyLocationEnabled(true);
        }

    }
    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(requireActivity())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API).build();
        mGoogleApiClient.connect();

        //moveToRestaurantLocation(sharedViewModel.autoCompleteResult);
    }

    @Override
    public void onConnected(Bundle bundle) {

        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(1000);
        mLocationRequest.setFastestInterval(1000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        if (ContextCompat.checkSelfPermission(requireActivity(),
                ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        }

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onLocationChanged(@NotNull Location location) {

        mLastLocation = location;
        if (mCurrLocationMarker != null) {
            mCurrLocationMarker.remove();
        }
        //Place current location marker
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title("Current Position");
        markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.placeholder));
        mCurrLocationMarker = mMap.addMarker(markerOptions);

        //move map camera
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(11));

        //stop location updates
        if (mGoogleApiClient != null) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
        }

    }

    public void uiSettings() {
        mMap.setMinZoomPreference(12);
        mMap.setIndoorEnabled(true);
        UiSettings uiSettings = mMap.getUiSettings();
        uiSettings.setMyLocationButtonEnabled(true);
        uiSettings.setCompassEnabled(true);
        uiSettings.setZoomControlsEnabled(true);
        uiSettings.isMyLocationButtonEnabled();

    }


    @Override
    public void onConnectionFailed(@NotNull ConnectionResult connectionResult) {

    }

    @Override
    public void onSaveInstanceState(@NotNull Bundle outState) {
        super.onSaveInstanceState(outState);

        Bundle mapViewBundle = outState.getBundle(MAP_VIEW_BUNDLE_KEY);
        if (mapViewBundle == null) {
            mapViewBundle = new Bundle();
            outState.putBundle(MAP_VIEW_BUNDLE_KEY, mapViewBundle);
        }
        mapView.onSaveInstanceState(mapViewBundle);
    }


    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();

    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }


    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onResume();
    }

    private void initViewModel() {
        ViewModelFactory viewModelFactory = Injection.viewModelFactory();
        sharedViewModel = ViewModelProviders.of(requireActivity(), viewModelFactory).get(SharedViewModel.class);
        sharedViewModel.fetchPickedRestaurants();
        sharedViewModel.fetchWorkmateIsGoing();
        sharedViewModel.workmateId.observe(getViewLifecycleOwner(), workmateId ->
        sharedViewModel.getRestaurantMutableLiveData().observe(getViewLifecycleOwner(), results -> setMapMarkers(results, workmateId)));
    }


    private void setMapMarkers(List<Result> restaurants, List<String> workmateId) {
        if (mMap != null) {
            mMap.clear();

            for (Result restaurant : restaurants) {
                int restaurantPin = (workmateId.contains(restaurant.getPlaceId())) ? R.drawable.pinrestaurantpicked : R.drawable.pinrestaurant;
                LatLng positionRestaurant = new LatLng(restaurant.getGeometry().getLocation().getLat(),
                        restaurant.getGeometry().getLocation().getLng());
                mMap.addMarker(new MarkerOptions()
                        .position(positionRestaurant)
                        .icon(BitmapDescriptorFactory.fromResource(restaurantPin))
                        .title(restaurant.getName()))
                        .setTag(restaurant.getPlaceId());

                onMarkerClick();
            }
        }
    }

    private void onMarkerClick(){
        mMap.setOnMarkerClickListener(marker -> {
            marker.getTag();
            return false;
        });
    }

    private void onTagClick(){
        mMap.setOnMarkerClickListener(marker -> {
            String placeId = (String) marker.getTag();
            Intent intent = new Intent(getActivity(), RestaurantDetailActivity.class);
            intent.putExtra("restaurantId", placeId);
            startActivity(intent);
            return false;
        });
    }


    public void displayRestaurant(LatLng latLng, String name, String id) {
        if (latLng != null) {
            if (mMap != null) {
                Bundle bundle = new Bundle();
                bundle.getSerializable("RestaurantId");
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 16));
                int iconResource = R.drawable.restaurantavailable;
                LatLng positionRestaurant = new LatLng(latLng.latitude, latLng.longitude);
                mMap.addMarker(new MarkerOptions()
                        .position(positionRestaurant)
                        .icon(BitmapDescriptorFactory.fromResource(iconResource))
                        .title(name))
                        .setTag(id);

                onMarkerClick();
            }
            else{
                Intent intent = new Intent(requireActivity(), RestaurantDetailActivity.class);
                intent.putExtra("restaurantId", id);
                startActivity(intent);

            }
        }
    }

    /*private void moveToRestaurantLocation(@NotNull MutableLiveData<Place> requestPlace) {
        if (requestPlace.observe(Observer); != null) {
            for (Place.Type type : requestPlace.getTypes()) {
                if (type == Place.Type.RESTAURANT) {
                    displayRestaurant(requestPlace.getLatLng(), requestPlace.getName(), requestPlace.getId());
                }
            }
        }
    }*/


}