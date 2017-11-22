package com.example.android.androidtask.activities;

import android.graphics.Typeface;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.androidtask.R;
import com.example.android.androidtask.fragments.DetailsFragment;
import com.example.android.androidtask.fragments.EMagazineFragment;
import com.example.android.androidtask.fragments.ExploreFragment;
import com.example.android.androidtask.fragments.GalleryFragment;
import com.example.android.androidtask.fragments.HomeFragment;
import com.example.android.androidtask.fragments.LiveChatFragment;
import com.example.android.androidtask.fragments.WishListFragment;
import com.example.android.androidtask.model.Article;

public class MainActivity extends AppCompatActivity implements HomeFragment.Communicator {

    private NavigationView navigationView;
    private DrawerLayout drawer;
    private View navHeader;

    Typeface typeface1;
    private Toolbar toolbar;

    // index to identify current nav menu item
    public static int navItemIndex = 0;

    // tags used to attach the fragments
    private static final String TAG_HOME = "home";
    private static final String TAG_EXPLORE = "explore";
    private static final String TAG_LIVE_CHAT = "live_chat";
    private static final String TAG_GAllery = "gallery";
    private static final String TAG_WISH_LIST = "wish_list";
    private static final String TAG_E_MAGAZINE = "e_magazine";

    public static String CURRENT_TAG = TAG_HOME;

    // toolbar titles respected to selected nav menu item
    private String[] activityTitles;

    // flag to load home fragment when user presses back key
    private boolean shouldLoadHomeFragOnBackPress = true;
    private Handler mHandler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

       /* typeface1 = Typeface.createFromAsset(getAssets(), "fonts/Lato-Regular.ttf");
        TextView txt_view = (TextView)findViewById(R.id.txt_name);
        txt_view.setTypeface(typeface1);*/

        mHandler = new Handler();

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navHeader = navigationView.getHeaderView(0);
        activityTitles = getResources().getStringArray(R.array.nav_item_activity_titles);

        // initializing navigation menu
        setUpNavigationView();

        if (savedInstanceState == null) {
            navItemIndex = 0;
            CURRENT_TAG = TAG_HOME;
            loadHomeFragment();
        }

    }

    private void loadHomeFragment() {
        // selecting appropriate nav menu item
        selectNavMenu();

        // set toolbar title
        setToolbarTitle();

        // if user select the current navigation menu again, don't do anything
        // just close the navigation drawer
        if (getSupportFragmentManager().findFragmentByTag(CURRENT_TAG) != null) {
            drawer.closeDrawers();

            return;
        }

        // Sometimes, when fragment has huge data, screen seems hanging
        // when switching between navigation menus
        // So using runnable, the fragment is loaded with cross fade effect
        // This effect can be seen in GMail app
        Runnable mPendingRunnable = new Runnable() {
            @Override
            public void run() {
                // update the main content by replacing fragments
                Fragment fragment = getHomeFragment();
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
                        android.R.anim.fade_out);
                fragmentTransaction.replace(R.id.frame, fragment, CURRENT_TAG);
                fragmentTransaction.commitAllowingStateLoss();
            }
        };

        // If mPendingRunnable is not null, then add to the message queue
        if (mPendingRunnable != null) {
            mHandler.post(mPendingRunnable);
        }


        //Closing drawer on item click
        drawer.closeDrawers();

        // refresh toolbar menu
        invalidateOptionsMenu();
    }

    private Fragment getHomeFragment() {

        switch (navItemIndex) {
            case 0:
                HomeFragment homeFragment = new HomeFragment();
                return homeFragment;
            case 1:
                ExploreFragment exploreFragment = new ExploreFragment();
                return exploreFragment;
            case 2:
                LiveChatFragment liveChatFragment = new LiveChatFragment();
                return liveChatFragment;
            case 3:
                GalleryFragment galleryFragment = new GalleryFragment();
                return galleryFragment;
            case 4:
                WishListFragment wishListFragment = new WishListFragment();
                return wishListFragment;
            case 5:
                EMagazineFragment eMagazineFragment = new EMagazineFragment();
                return eMagazineFragment;
            default:
                return new HomeFragment();
        }
    }

    private void setUpNavigationView() {
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_home:
                        navItemIndex = 0;
                        CURRENT_TAG = TAG_HOME;
                        break;
                    case R.id.nav_explore:
                        navItemIndex = 1;
                        Toast.makeText(MainActivity.this, "Explore Fragment", Toast.LENGTH_LONG).show();
                        CURRENT_TAG = TAG_EXPLORE;
                        break;
                    case R.id.nav_live_chat:
                        navItemIndex = 2;
                        Toast.makeText(MainActivity.this, "Live Chat Fragment", Toast.LENGTH_LONG).show();
                        CURRENT_TAG = TAG_LIVE_CHAT;
                        break;
                    case R.id.nav_gallery:
                        navItemIndex = 3;
                        Toast.makeText(MainActivity.this, "Gallery Fragment", Toast.LENGTH_LONG).show();
                        CURRENT_TAG = TAG_GAllery;
                        break;
                    case R.id.nav_wish_list:
                        navItemIndex = 4;
                        Toast.makeText(MainActivity.this, "Wish List Fragment", Toast.LENGTH_LONG).show();
                        CURRENT_TAG = TAG_WISH_LIST;
                        break;
                    case R.id.nav_e_magazine:
                        navItemIndex = 5;
                        Toast.makeText(MainActivity.this, "E-Magazine Fragment", Toast.LENGTH_LONG).show();
                        CURRENT_TAG = TAG_E_MAGAZINE;
                        break;
                    default:
                        navItemIndex = 0;
                }

                if (item.isChecked()) {
                    item.setChecked(false);
                } else {
                    item.setChecked(true);
                }
                item.setChecked(true);

                loadHomeFragment();

                return true;
            }
        });

        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.openDrawer, R.string.closeDrawer) {

            @Override
            public void onDrawerClosed(View drawerView) {
                // Code here will be triggered once the drawer closes as we dont want anything to happen so we leave this blank
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                // Code here will be triggered once the drawer open as we dont want anything to happen so we leave this blank
                super.onDrawerOpened(drawerView);
            }
        };
        //Setting the actionbarToggle to drawer layout
        drawer.addDrawerListener(actionBarDrawerToggle);

        //calling sync state is necessary or else your hamburger icon wont show up
        actionBarDrawerToggle.syncState();
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawers();
            return;
        }

        // This code loads home fragment when back key is pressed
        // when user is in other fragment than home
        if (shouldLoadHomeFragOnBackPress) {
            // checking if user is on other navigation menu
            // rather than home
            if (navItemIndex != 0) {
                navItemIndex = 0;
                CURRENT_TAG = TAG_HOME;
                loadHomeFragment();
                return;
            }
        }

        super.onBackPressed();
    }

    private void setToolbarTitle() {
        getSupportActionBar().setTitle(activityTitles[navItemIndex]);
    }

    private void selectNavMenu() {
        navigationView.getMenu().getItem(navItemIndex).setChecked(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.search_item, menu);
        MenuItem search = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(search);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onResponse(Article article) {

        DetailsFragment detailViewFragment = new DetailsFragment();
        Bundle arguments = new Bundle();
        arguments.putParcelable("EXTRA_ARTICLE_ID", article);


        detailViewFragment.setArguments(arguments);

        getSupportFragmentManager().beginTransaction().replace(R.id.frame, detailViewFragment, "fragmentDetails").commit();
    }
}
