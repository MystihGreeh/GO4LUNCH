package com.mystihgreeh.go4lunch.ui;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.opengl.Visibility;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.view.GravityCompat;
import androidx.core.view.MenuItemCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.model.RectangularBounds;
import com.google.android.libraries.places.api.model.TypeFilter;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.mystihgreeh.go4lunch.BuildConfig;
import com.mystihgreeh.go4lunch.R;
import com.mystihgreeh.go4lunch.api.Injection;
import com.mystihgreeh.go4lunch.model.Workmates.Workmate;
import com.mystihgreeh.go4lunch.repository.WorkmatesRepository;
import com.mystihgreeh.go4lunch.viewModel.SettingsViewModel;
import com.mystihgreeh.go4lunch.viewModel.SharedViewModel;
import com.mystihgreeh.go4lunch.viewModel.ViewModelFactory;

import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    //FOR FRAGMENTS
    // 1 - Declare fragment and activities handled by Navigation Drawer
    private Fragment mapViewActivity;
    private Fragment fragmentListView;
    private Fragment fragmentWorkmates;


    //FOR DATAS
    // 2 - Identify each fragment with a number
    private static final int FRAGMENT_MAP = 3;
    private static final int FRAGMENT_LISTVIEW = 4;
    private static final int FRAGMENT_WORKMATES = 5;
    private SharedViewModel sharedViewModel;



    private final String type = "restaurant";
    public double currentLatitude;
    public double currentLongitude;
    private final int AUTOCOMPLETE_REQUEST_CODE = 1;
    public String selectedRestaurantId;

    private FusedLocationProviderClient fusedLocationProviderClient;


    //FOR DESIGN
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    ArrayAdapter<String> adapter;
    BottomNavigationView bottomNavigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Initialize the SDK
        String key = BuildConfig.API_KEY;
        Places.initialize(getApplicationContext(), key);

        // Initializing currentLocation
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        // Check permission
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(MainActivity.this, ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                getCurrentLocation();
            }
        }


    }

    // get current location
    @SuppressLint("MissingPermission")
    private void getCurrentLocation() {
        fusedLocationProviderClient.getLastLocation().addOnSuccessListener(location -> {
            if (location != null) {
                currentLatitude = location.getLatitude();
                currentLongitude = location.getLongitude();
                initAll();
            } else {
                LocationRequest locationRequest = new LocationRequest().setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                        .setInterval(10000)
                        .setFastestInterval(1000)
                        .setNumUpdates(1);

                LocationCallback locationCallback = new LocationCallback() {
                    @Override
                    public void onLocationResult(@NonNull @NotNull LocationResult locationResult) {
                        Location location1 = locationResult.getLastLocation();
                        currentLatitude = location1.getLatitude();
                        currentLongitude = location1.getLongitude();
                    }
                };
                fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper());
            }
        });
    }

    public void initAll() {
        // Configure all views
        this.initViewModel();
        this.configureToolBar();
        this.configureDrawerLayout();
        this.configureNavigationView();
        this.configureBottomView();
        this.showFirstFragment();
        this.createUserInFirestore();

    }

    @Override
    public void onBackPressed() {
        // Handle back click to close menu
        if (this.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            this.drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    // --------------------------------------------------------------------------------------------
    //                                        CONFIGURATION
    // --------------------------------------------------------------------------------------------

    // 1 - Configure Toolbar
    @SuppressLint("NewApi")
    private void configureToolBar() {
        this.toolbar = findViewById(R.id.activity_main_toolbar);
        setSupportActionBar(toolbar);
    }

    // 2 - Configure Drawer Layout
    private void configureDrawerLayout() {
        this.drawerLayout = findViewById(R.id.activity_main_drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }

    // 3 - Configure NavigationView
    private void configureNavigationView() {
        NavigationView navigationView = findViewById(R.id.activity_main_nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        ImageView imageUser = navigationView.getHeaderView(0).findViewById(R.id.user_picture);
        TextView userNameTextView = navigationView.getHeaderView(0).findViewById(R.id.user_name);
        TextView emailTextView = navigationView.getHeaderView(0).findViewById(R.id.user_email);

        //Firebase user
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        System.out.println(user + "is connected");
        if (user != null) {
            String uid = Objects.requireNonNull(sharedViewModel.getCurrentUser()).getUid();
            userNameTextView.setText(user.getDisplayName());
            String useremail = Objects.requireNonNull(sharedViewModel.getCurrentUser()).getEmail();
            List<? extends UserInfo> provider = sharedViewModel.getCurrentUser().getProviderData();
            for (UserInfo p : provider) {
                if (p.getEmail() != null) useremail = p.getEmail();
            }
            emailTextView.setText(useremail);
            if (user.getPhotoUrl() != null) {
                Glide.with(this)
                        .load(user.getPhotoUrl())
                        .transform(new CircleCrop())
                        .into(imageUser);
            } else {
                Glide.with(this)
                        .load(R.drawable.avatar)
                        .apply(RequestOptions.circleCropTransform())
                        .into(imageUser);
            }
            sharedViewModel.getSelectedRestaurant();
        }
    }


    private void configureBottomView() {
        bottomNavigationView = findViewById(R.id.activity_main_bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> updateMainFragment(item.getItemId()));
    }

    public void initViewModel() {
        ViewModelFactory viewModelFactory = Injection.viewModelFactory();
        sharedViewModel = ViewModelProviders.of(this, viewModelFactory).get(SharedViewModel.class);
        sharedViewModel.fetchRestaurants(currentLatitude, currentLongitude);
        sharedViewModel.fetchWorkmateIsGoing();
        sharedViewModel.getWorkmates();

    }



    //----------------------------------------------------------------------------------------------
    //                                         SEARCH VIEW
    //----------------------------------------------------------------------------------------------

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_menu, menu);
        MenuItem searchViewItem = menu.findItem(R.id.search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchViewItem);
        searchView.setOnQueryTextFocusChangeListener((view, b) -> startAutocompleteActivity());
        return super.onCreateOptionsMenu(menu);
    }


    // ------------------------------------- NAVIGATION DRAWER --------------------------------- //


    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // 4 - Handle Navigation Item Click
        int id = item.getItemId();
        switch (id) {
            case R.id.activity_main_drawer_lunch:
                String restaurantId = sharedViewModel.getUserRestaurant();
                if (restaurantId != null) {
                    Intent intent = new Intent(this, RestaurantDetailActivity.class);
                    intent.putExtra("restaurantId", restaurantId);
                    startActivity(intent);
                }

                break;
            case R.id.activity_main_drawer_settings:
                startSettingsActivity();
                break;
            case R.id.activity_main_drawer_logout:
                signOutUserFromFirebase();
                finish();
                break;
            default:
                break;
        }

        this.drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }


    // ---- Logout button ----
    private void signOutUserFromFirebase() {
        AuthUI.getInstance()
                .signOut(this)
                .addOnSuccessListener(this, this.updateUIAfterRESTRequestsCompleted());
    }

    // Create OnCompleteListener called after tasks ended
    private OnSuccessListener<Void> updateUIAfterRESTRequestsCompleted() {
        return aVoid -> finish();
    }



    // ------------------------------------ BOTTOM NAVIGATION VIEW ------------------------------ //

    @SuppressLint("NonConstantResourceId")
    private Boolean updateMainFragment(Integer integer) {
        switch (integer) {
            case R.id.map:
                this.showFragment(FRAGMENT_MAP);
                break;
            case R.id.list_view:
                this.showFragment(FRAGMENT_LISTVIEW);
                break;
            case R.id.workmates:
                this.showFragment(FRAGMENT_WORKMATES);
                break;
        }
        return true;
    }


    // -------------------------------------------------------------------------------------------
    //                                          FRAGMENTS
    // -------------------------------------------------------------------------------------------

    private void showFragment(int fragmentIdentifier) {
        switch (fragmentIdentifier) {
            case FRAGMENT_MAP:
                this.showMapFragment();
                break;
            case FRAGMENT_LISTVIEW:
                this.showListViewFragment();
                break;
            case FRAGMENT_WORKMATES:
                this.showWorkmatesFragment();
                break;
            default:
                break;
        }
    }

    private void showMapFragment() {
        if (this.mapViewActivity == null)
            this.mapViewActivity = new MapViewFragment();
        this.startTransactionFragment(this.mapViewActivity);
    }

    private void showListViewFragment() {
        if (this.fragmentListView == null)
            this.fragmentListView = RestaurantsListFragment.newInstance();
        this.startTransactionFragment(this.fragmentListView);
    }

    private void showWorkmatesFragment() {
        if (this.fragmentWorkmates == null)
            this.fragmentWorkmates = WorkmatesListFragment.newInstance();
        this.startTransactionFragment(this.fragmentWorkmates);
    }

    private void startTransactionFragment(Fragment fragment) {
        if (!fragment.isVisible())
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.activity_main_frame_layout, fragment).commit();

    }

    // Show first fragment when activity is created
    private void showFirstFragment() {
        Fragment visibleFragment = getSupportFragmentManager().findFragmentById(R.id.activity_main_frame_layout);
        if (visibleFragment == null)
            // 1.1 - Show Map Fragment
            this.showFragment(FRAGMENT_MAP);
        // 1.2 - Mark as selected the menu item corresponding to NewsFragment
        this.bottomNavigationView.getMenu().getItem(0).setChecked(true);
    }


    //Settings
    private void startSettingsActivity() {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }


    // -------------------------------------------------------------------------------------------
    //                                      ERROR HANDLER
    // -------------------------------------------------------------------------------------------

    protected OnSuccessListener<? super Void> onFailureListener() {
        return e -> Toast.makeText(getApplicationContext(), getString(R.string.unknown_error), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void createUserInFirestore() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String userId = user.getUid();
        //String currentUserId = sharedViewModel.getCurrentUser().getUid();
        if (sharedViewModel.checkIfUserExist(user.getUid())) {

            if (sharedViewModel.getCurrentUser() == null) {
                FirebaseUser currentUser = sharedViewModel.getCurrentUser();
                String urlPicture = (currentUser.getPhotoUrl() != null) ? currentUser.getPhotoUrl().toString() : null;
                String username = currentUser.getDisplayName();
                String useremail = currentUser.getEmail();
                String uid = currentUser.getUid();
                //String restaurantName = currentUser.get();
                //String restaurantUid = this.getCurrentUser();
                //String restaurantAddress = this.getCurrentUser();
                List<? extends UserInfo> provider = currentUser.getProviderData();
                for (UserInfo p : provider) {
                    if (p.getEmail() != null) useremail = p.getEmail();
                }
                sharedViewModel.createWorkmate(uid, username, urlPicture, useremail, null, null, null);
            }
        } else{
            sharedViewModel.getCurrentUser();
        }
    }


    // ---------------------------------------------------------------------------------------------
    //                                      PLACE AUTOCOMPLETE
    // ---------------------------------------------------------------------------------------------

    public void startAutocompleteActivity() {
        List<Place.Field> fields = Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.TYPES, Place.Field.LAT_LNG);
        Intent intent = new Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY, fields)
                .setLocationBias(RectangularBounds.newInstance(new LatLng(currentLatitude, currentLongitude), new LatLng(currentLatitude, currentLongitude)))
                .setCountry("FR")
                .setTypeFilter(TypeFilter.ESTABLISHMENT)
                .build(this);
        startActivityForResult(intent, AUTOCOMPLETE_REQUEST_CODE);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.map);
        if (requestCode == AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Place requestPlace = Autocomplete.getPlaceFromIntent(data);
                sharedViewModel.autoCompleteResult.setValue(requestPlace);
                if (currentFragment == mapViewActivity) {
                    //moveToRestaurantLocation(requestPlace);
                } else {
                    //displayDetailRestaurant(requestPlace);
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }




    private void displayDetailRestaurant(@NotNull Place requestPlace) {
        if (requestPlace.getTypes() != null) {
            for (Place.Type type : requestPlace.getTypes()) {
                if (type == Place.Type.RESTAURANT) {
                    Intent intent = new Intent(this, RestaurantDetailActivity.class);
                    intent.putExtra("restaurantId", requestPlace.getId());
                    startActivity(intent);
                }
            }
        }
    }


}



