package com.cs442.bkyada.memorygame;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class Home extends AppCompatActivity
        implements DifficultyDialog.DifficultyDialogListener, LevelDialog.LevelDialogListener, SettingsDialog.SettingsDialogListener {

    private NavigationView navigationView;
    private DrawerLayout drawer;
    private View navHeader;
    private ImageView imgNavHeaderBg, imgProfile;
    private TextView txtName, txtWebsite;
    private Toolbar toolbar;
    private FloatingActionButton fab;
    private static final String urlNavHeaderBg = "http://www.clker.com/cliparts/f/e/3/9/1422439112226163877surf%20light%20teal%20green%20android%20wallpaper%20background-md.png";
    public static int navItemIndex = 0;
    private static final String TAG_HOME = "home";
    private static final String TAG_PHOTOS = "photos";
    private static final String TAG_MOVIES = "movies";
    private static final String TAG_NOTIFICATIONS = "notifications";
    private static final String TAG_SETTINGS = "settings";
    public static String CURRENT_TAG = TAG_HOME;
    Intent objIntent;
    private String[] activityTitles;
    private boolean shouldLoadHomeFragOnBackPress = true;
    private Handler mHandler;
    private Switch myswitch;
    private CommonUtils commonUtils ;
    private User user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        commonUtils = new CommonUtils(this);
        user = commonUtils.getUserSessionDetails();
//        Log.d("welcome", "welcome "+ user.getName() +" email "+ user.getEmail());
//        Toast.makeText(getApplicationContext(), "welcome "+ user.getName() +" email "+ user.getEmail(), Toast.LENGTH_LONG).show();

        setContentView(R.layout.activity_home);

//        toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//
//        mHandler = new Handler();
//
//        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        navigationView = (NavigationView) findViewById(R.id.nav_view);
//        fab = (FloatingActionButton) findViewById(R.id.fab);
//
//        // Navigation view header
//        navHeader = navigationView.getHeaderView(0);
//        txtName = (TextView) navHeader.findViewById(R.id.name);
//        txtWebsite = (TextView) navHeader.findViewById(R.id.website);
//        imgNavHeaderBg = (ImageView) navHeader.findViewById(R.id.img_header_bg);
//        imgProfile = (ImageView) navHeader.findViewById(R.id.img_profile);
//
//        // load toolbar titles from string resources
//        activityTitles = getResources().getStringArray(R.array.nav_item_activity_titles);
//
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
//
//        // load nav menu header data
//        loadNavHeader();
//
//        // initializing navigation menu
//        setUpNavigationView();
//
//        if (savedInstanceState == null) {
//            navItemIndex = 0;
//            CURRENT_TAG = TAG_HOME;
//            loadHomeFragment();
//        }
    }

    private void loadNavHeader() {
        // name, website
        txtName.setText("Welcome User");

//        Glide.with(this).load(urlNavHeaderBg)
//                .crossFade()
//                .diskCacheStrategy(DiskCacheStrategy.ALL)
//                .into(imgNavHeaderBg);

        navigationView.getMenu().getItem(3).setActionView(R.layout.menu_dot);
    }

    private void setUpNavigationView() {
        //Setting Navigation View Item Selected Listener to handle the item click of the navigation menu
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            // This method will trigger on item Click of navigation menu
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {

                //Check to see which item was being clicked and perform appropriate action
                Toast.makeText(Home.this, menuItem.getItemId(), Toast.LENGTH_SHORT).show();
                switch (menuItem.getItemId()) {
                    //Replacing the main content with ContentFragment Which is our Inbox View;
                    case R.id.nav_help:
                        navItemIndex = 1;
                        CURRENT_TAG = TAG_PHOTOS;
                        break;
                    case R.id.nav_music:
                        myswitch = (Switch) findViewById(R.id.switch1);
                        objIntent = new Intent(Home.this,BackgroundSoundService.class);
                        myswitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                if(isChecked){
                                    startService(objIntent);
                                }
                                else
                                {
                                    stopService(objIntent);
                                }
                            }
                        });
                    case R.id.nav_notification:


                    default:
                        navItemIndex = 0;
                }

                //Checking if the item is in checked state or not, if not make it in checked state
                if (menuItem.isChecked()) {
                    menuItem.setChecked(false);
                } else {
                    menuItem.setChecked(true);
                }
                menuItem.setChecked(true);

                loadHomeFragment();

                return true;
            }
        });

        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.openDrawer, R.string.closeDrawer) {

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
        drawer.setDrawerListener(actionBarDrawerToggle);

        //calling sync state is necessary or else your hamburger icon wont show up
        actionBarDrawerToggle.syncState();
    }

    private void selectNavMenu() {
        navigationView.getMenu().getItem(navItemIndex).setChecked(true);
    }

    private void setToolbarTitle() {
        getSupportActionBar().setTitle(activityTitles[navItemIndex]);
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

            // show or hide the fab button
            toggleFab();
            return;
        }

        // Sometimes, when fragment has huge data, screen seems hanging
        // when switching between navigation menus
        // So using runnable, the fragment is loaded with cross fade effect
        // This effect can be seen in GMail app

        // If mPendingRunnable is not null, then add to the message queue


        // show or hide the fab button
        toggleFab();

        //Closing drawer on item click
        drawer.closeDrawers();

        // refresh toolbar menu
        invalidateOptionsMenu();
    }



    private void toggleFab() {
        if (navItemIndex == 0)
            fab.show();
        else
            fab.hide();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            moveTaskToBack(true);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void selectDifficultyDialog(View view) {
        FragmentManager fm = getSupportFragmentManager();
        DifficultyDialog dialog = new DifficultyDialog();
        dialog.setCancelable(true);
        dialog.setDialogTitle("Select Difficulty");
        int id = view.getId();
        String category = "";
        if (id == R.id.ib_animals) {
            category = "animals";
        } else if (id == R.id.ib_birds) {
            category = "birds";
        } else if (id == R.id.ib_food) {
            category = "food";
        } else if (id == R.id.ib_cartoon) {
            category = "cartoon";
        } else if (id == R.id.ib_logos) {
            category = "logos";
        } else if (id == R.id.ib_christmas) {
            category = "christmas";
        }
        dialog.setCategory(category);
        dialog.show(fm, "Difficulty Dialog");
    }

    @Override
    public void onDifficultyDialogComplete(String category, String difficulty) {
        FragmentManager fm = getSupportFragmentManager();
        LevelDialog dialog = new LevelDialog();
        dialog.setCancelable(true);
        dialog.setDialogTitle("Select Level");

        dialog.setCategory(category);
        dialog.setDifficulty(difficulty);

        dialog.setUnLockedLevelCount(user.getUnlockLevelByDifficulty(difficulty));

        dialog.show(fm, "Difficulty Dialog");
    }

    @Override
    public void onLevelDialogComplete(String category, String difficulty, int level) {

        //Integer level = 1;
        Intent game = new Intent(this, Game.class);
        game.putExtra("category", category);
        game.putExtra("difficulty", difficulty);
        game.putExtra("startLevel", level);
        this.startActivity(game);
    }

    public void showMyScore(View view) {
        Intent myScore = new Intent(this, MyScore.class);
        startActivity(myScore);
    }

    public void showTopScores(View view) {
        Intent topScores = new Intent(this, TopScores.class);
        startActivity(topScores);
    }

    public void showHelp(View view) {
        Intent help = new Intent(this, Help.class);
        help.putExtra("parent", "home");
        startActivity(help);
    }

    public void showSettingsDialog(View view) {
        FragmentManager fm = getSupportFragmentManager();
        SettingsDialog dialog = new SettingsDialog();
        dialog.setCancelable(true);
        dialog.setDialogTitle("Settings");
        dialog.show(fm, "Settings Dialog");
    }

    @Override
    public void onSettingsDialogComplete(String action) {
        if ("logout".equals(action) || "cerrar sesión".equals(action) || "登出".equals(action)) {
            commonUtils.logout();
            startActivity(new Intent(this, Login.class));
        } else if ("edit profile".equals(action) || "editar perfil".equals(action) || "編輯個人資料".equals(action)) {
            startActivity(new Intent(this, UserProfile.class));
        }
    }
}
