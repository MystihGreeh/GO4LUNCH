package com.mystihgreeh.go4lunch.view;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
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
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.core.view.MenuItemCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.mystihgreeh.go4lunch.R;
import com.mystihgreeh.go4lunch.ViewModel.ViewModelWorkmates;
import com.mystihgreeh.go4lunch.model.Workmate;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    //FOR FRAGMENTS
    // 1 - Declare fragment and activities handled by Navigation Drawer
    private Fragment fragmentLunch;
    private Activity activitySettings;
    private Activity activityLogin;
    private Fragment mapViewActivity;
    private Fragment fragmentListView;
    private Fragment fragmentWorkmates;
    private Workmate workmate;

    List<Workmate> listOfMovies = new ArrayList<>();
    //WorkmateAdapter mAdapter;
    RecyclerView mPoster;
    ViewModelWorkmates viewModelWorkmates;

    //FOR DATAS
    // 2 - Identify each fragment with a number
    private static final int FRAGMENT_LUNCH = 0;
    private static final int ACTIVITY_SETTINGS = 1;
    private static final int ACTIVITY_LOGIN = 2;
    private static final int FRAGMENT_MAP = 3;
    private static final int FRAGMENT_LISTVIEW = 4;
    private static final int FRAGMENT_WORKMATES = 5;
    public final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 0;


    private static final int SIGN_OUT_TASK = 10;



    //FOR DESIGN
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    ArrayAdapter<String > adapter;
    BottomNavigationView bottomNavigationView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // Initialize the SDK
        //Places.initialize(getApplicationContext(), apiKey);
        // Create a new PlacesClient instance
        //PlacesClient placesClient = Places.createClient(this);

        // Configure all views
        this.configureToolBar();
        this.configureDrawerLayout();
        this.configureNavigationView();
        this.configureBottomView();
        this.showFirstFragment();

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




    // ----------------------------------------------------------------
    // CONFIGURATION
    // ----------------------------------------------------------------

    // 1 - Configure Toolbar
    @SuppressLint("NewApi")
    private void configureToolBar(){
        this.toolbar = findViewById(R.id.activity_main_toolbar);
        setSupportActionBar(toolbar);
    }

    // 2 - Configure Drawer Layout
    private void configureDrawerLayout(){
        this.drawerLayout = findViewById(R.id.activity_main_drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }

    // 3 - Configure NavigationView
    private void configureNavigationView(){
        NavigationView navigationView = findViewById(R.id.activity_main_nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        ImageView imageUser = navigationView.getHeaderView(0).findViewById(R.id.user_picture);
        TextView userNameTextView = navigationView.getHeaderView(0).findViewById(R.id.user_name);
        TextView emailTextView = navigationView.getHeaderView(0).findViewById(R.id.user_email);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            userNameTextView.setText(user.getDisplayName());
            emailTextView.setText(user.getEmail());
            Glide.with(this)
                    .load(user.getPhotoUrl())
                    .transform(new CircleCrop())
                    .into(imageUser);
        }

    }


    private void configureBottomView(){
        bottomNavigationView = findViewById(R.id.activity_main_bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> updateMainFragment(item.getItemId()));
    }



    // ----------------------- SEARCH VIEW ---------------------- //

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_menu, menu);
        MenuItem searchViewItem = menu.findItem(R.id.search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchViewItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchView.clearFocus();
             /*   if(list.contains(query)){
                    adapter.getFilter().filter(query);
                }else{
                    Toast.makeText(MainActivity.this, "No Match found",Toast.LENGTH_LONG).show();
                }*/
                return false;

            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }




    // ----------------------- NAVIGATION DRAWER ---------------------- //

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // 4 - Handle Navigation Item Click
        int id = item.getItemId();
        switch (id) {
            case R.id.activity_main_drawer_lunch:
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
    private void signOutUserFromFirebase(){
        AuthUI.getInstance()
                .signOut(this)
                .addOnSuccessListener(this, this.updateUIAfterRESTRequestsCompleted(SIGN_OUT_TASK));
    }

    // Create OnCompleteListener called after tasks ended
    private OnSuccessListener<Void> updateUIAfterRESTRequestsCompleted(int signOutTask){
        return aVoid -> finish();
    }

    // Get current user info
    @Nullable
    protected FirebaseUser getCurrentUser(){ return FirebaseAuth.getInstance().getCurrentUser(); }
    protected Boolean isCurrentUserLogged(){ return (this.getCurrentUser() != null); }



    // ----------------------- BOTTOM NAVIGATION VIEW ---------------------- //

    @SuppressLint("NonConstantResourceId")
    private Boolean updateMainFragment(Integer integer){
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

    // ---------------------
    // FRAGMENTS
    // ---------------------

    private void showFragment(int fragmentIdentifier){
        switch (fragmentIdentifier){
            case FRAGMENT_MAP :
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

    private void showMapFragment(){
        if (this.mapViewActivity == null) this.mapViewActivity = new MapViewFragment();
        this.startTransactionFragment(this.mapViewActivity);
    }

    private void showListViewFragment(){
        if (this.fragmentListView == null) this.fragmentListView = RestaurantsListFragment.newInstance(1);
        this.startTransactionFragment(this.fragmentListView);
    }

    private void showWorkmatesFragment(){
        if (this.fragmentWorkmates == null) this.fragmentWorkmates = WorkmatesListFragment.newInstance(2);
        this.startTransactionFragment(this.fragmentWorkmates);
    }

    private void startTransactionFragment(Fragment fragment){
        if (!fragment.isVisible())
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.activity_main_frame_layout, fragment).commit();

    }

    // Show first fragment when activity is created
    private void showFirstFragment(){
        Fragment visibleFragment = getSupportFragmentManager().findFragmentById(R.id.activity_main_frame_layout);
        if (visibleFragment == null)
            // 1.1 - Show Map Fragment
            this.showFragment(FRAGMENT_MAP);
            // 1.2 - Mark as selected the menu item corresponding to NewsFragment
            this.bottomNavigationView.getMenu().getItem(0).setChecked(true);
    }



    //Settings
    private void startSettingsActivity(){
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);}



    /*private void showSettingsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.settings));
        builder.setMessage(getString(R.string.setting_dialog_msg));
        builder.setPositiveButton(getString(R.string.yes),
                (dialogInterface, i) -> {
                    SharedPrefManager.getInstance(this).saveBoolean(Constants.NOTIFICATION_ENABLED, true);
                    WorkerNotificationController.startWorkRequest(getApplicationContext());
                });
        builder.setNegativeButton(getString(R.string.no),
                (dialog, which) -> {
                    SharedPrefManager.getInstance(this).saveBoolean(Constants.NOTIFICATION_ENABLED, false);
                    WorkerNotificationController.stopWorkRequest(getApplicationContext());
                });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }*/


    // --------------------
    // ERROR HANDLER
    // --------------------

    protected OnFailureListener onFailureListener(){
        return e -> Toast.makeText(getApplicationContext(), getString(R.string.unknown_error), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

}