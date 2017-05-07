package com.sharonov.nikiz.simpletranslater.ui.activities;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.sharonov.nikiz.simpletranslater.R;
import com.sharonov.nikiz.simpletranslater.ui.fragments.FavoritesFragment;
import com.sharonov.nikiz.simpletranslater.ui.fragments.HistoryFragment;
import com.sharonov.nikiz.simpletranslater.ui.fragments.TranslateFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import io.realm.RealmConfiguration;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.navigation_view)
    NavigationView navView;

    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private ImageView imgHeaderBackground;

    private static final String HOME = "home";
    private static final String HISTORY = "history";
    private static final String STARRED = "starred";
    private static String CURRENT = HOME;

    // current nav menu item
    private static int NAV_ITEM_INDEX = 0;

    // array of toolbar titles
    private String[] toolbarTitles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // initialize Realm
        Realm.init(getApplicationContext());

        RealmConfiguration config = new RealmConfiguration.
                Builder().
                deleteRealmIfMigrationNeeded().
                build();
        Realm.setDefaultConfiguration(config);

        ButterKnife.bind(this);

        View navHeader = navView.getHeaderView(0);
        imgHeaderBackground = (ImageView) navHeader.findViewById(R.id.header_background);


        toolbarTitles = getResources().getStringArray(R.array.toolbar_titles);

        loadHeaderImage();

        // initializing nav menu
        setUpNavigationView();

        if (savedInstanceState == null) {
            NAV_ITEM_INDEX = 0;
            CURRENT = HOME;
            loadHomeFragment();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkInternetConnection();
        toolbar.setTitle(toolbarTitles[NAV_ITEM_INDEX]);
    }

    /**
     * This method loads image in header using Glide library
     */
    private void loadHeaderImage() {
        Glide.with(this)
                .load(R.drawable.header_bg)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imgHeaderBackground);
    }

    /**
     * This method sets up navigation view
     */
    private void setUpNavigationView() {
        navView.setNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.nav_main:
                    NAV_ITEM_INDEX = 0;
                    CURRENT = HOME;
                    break;
                case R.id.nav_history:
                    NAV_ITEM_INDEX = 1;
                    CURRENT = HISTORY;
                    break;
                case R.id.nav_starred:
                    NAV_ITEM_INDEX = 2;
                    CURRENT = STARRED;
                    break;
                default:
                    NAV_ITEM_INDEX = 0;
            }

            if (item.isChecked())
                item.setChecked(false);
            else
                item.setChecked(true);
            item.setChecked(true);

            loadHomeFragment();

            return true;
        });

        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this, drawerLayout,
                toolbar, R.string.open_drawer, R.string.close_drawer) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };

        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();
    }

    private void loadHomeFragment() {
        selectNavMenu();
        setToolbarTitle();

        // if user clicks the same item of nav menu again, just close nav drawer
        if (getSupportFragmentManager().findFragmentByTag(CURRENT) != null) {
            drawerLayout.closeDrawers();
            return;
        }

        setFragment();

        drawerLayout.closeDrawers();

        invalidateOptionsMenu();
    }

    private void selectNavMenu() {
        navView.getMenu().getItem(NAV_ITEM_INDEX).setChecked(true);
    }

    private void setToolbarTitle() {
        toolbar.setTitle(toolbarTitles[NAV_ITEM_INDEX]);
    }

    /**
     * This method replaces fragments
     */
    private void setFragment() {
        Fragment fragment = getHomeFragment();
        FragmentTransaction transaction = getSupportFragmentManager()
                .beginTransaction()
                .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                .replace(R.id.frame_container, fragment, CURRENT);
        transaction.commitAllowingStateLoss();
    }

    /**
     * This method returns the desired fragment
     * @return desired fragment
     */
    private Fragment getHomeFragment() {
        switch(NAV_ITEM_INDEX) {
            case 0:
                return new TranslateFragment();
            case 1:
                return new HistoryFragment();
            case 2:
                return new FavoritesFragment();
            default: return new TranslateFragment();
        }
    }

    @Override
    public void onBackPressed() {

        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawers();
            return;
        }

        // loads home fragment when user isn't in home fragment
        if (NAV_ITEM_INDEX != 0) {
            NAV_ITEM_INDEX = 0;
            CURRENT = HOME;
            loadHomeFragment();
            return;
        }
        super.onBackPressed();
    }

    /**
     * This method notifies the absence of an Internet connection
     */
    private void checkInternetConnection() {
        if (!isNetworkStatusAvailable(this)) {
            Toast.makeText(this, "Check your Internet connection", Toast.LENGTH_LONG).show();
        }
    }

    /**
     * This method check if internet connection available
     * @param context - context
     * @return false if there is not internet connection, else - true
     */
    public static boolean isNetworkStatusAvailable(Context context) {
        ConnectivityManager con = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (con != null) {
            NetworkInfo info = con.getActiveNetworkInfo();
            if (info != null) {
                if (info.isConnected())
                    return true;
            }
        }
        return false;
    }
}
