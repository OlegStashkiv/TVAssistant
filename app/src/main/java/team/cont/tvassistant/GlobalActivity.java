package team.cont.tvassistant;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;

import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import team.cont.tvassistant.fragments.CatalogFragment;
import team.cont.tvassistant.fragments.FAQFragment;
import team.cont.tvassistant.fragments.GlobalFragment;
import team.cont.tvassistant.fragments.LookedFragment;
import team.cont.tvassistant.fragments.SavedFragment;
import team.cont.tvassistant.fragments.StatisticFragment;

public class GlobalActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        GlobalFragment.OnFragmentInteractionListener,
        SavedFragment.OnFragmentInteractionListener,
        CatalogFragment.OnFragmentInteractionListener,
        LookedFragment.OnFragmentInteractionListener,
        StatisticFragment.OnFragmentInteractionListener,
        FAQFragment.OnFragmentInteractionListener{


    //Оголошення Фрагментів
    GlobalFragment GFrag;
    SavedFragment SFrag;
    CatalogFragment CFrag;
    LookedFragment LFrag;
    StatisticFragment StFrag;
    FAQFragment FaqFrag;


    FragmentTransaction fTrans;
    FragmentTransaction fTrans2;

    private TextView Loginq;
    private TextView Emailq;
    //Постійні дані
    private SharedPreferences  sPref;
    public static final String LOGIN_SETTING = "loginfile"; // Ім'я файла
    public static final String SAVED_EMAIL = "saved_email";   //Ключі
    public static final String SAVED_LOGIN = "saved_login";  //До даних
    public static final String SAVED_ID = "saved_id";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_global);
        sPref = getSharedPreferences(LOGIN_SETTING, Context.MODE_PRIVATE);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        View headerView = navigationView.getHeaderView(0);
        Loginq = (TextView) headerView.findViewById(R.id.LoginT);
        Emailq = (TextView) headerView.findViewById(R.id.EmailT);



        SharedPreferences myPrefs = getSharedPreferences(LOGIN_SETTING, MODE_PRIVATE);
        String userEmail = myPrefs.getString(SAVED_EMAIL,null);
        String userLogin = myPrefs.getString(SAVED_LOGIN,null);
        int userID=myPrefs.getInt(SAVED_ID,1);
        if (userEmail != null && userLogin != null )
        {
            Loginq.setText(userLogin);
            Emailq.setText(userEmail);
       }else{
           Intent activity1 = new Intent(GlobalActivity.this, LoginActivity.class);
            startActivity(activity1);
       }
//
        GFrag = new GlobalFragment();
        SFrag = new SavedFragment();
        CFrag = new CatalogFragment();
        LFrag = new LookedFragment();
        StFrag = new StatisticFragment();
        FaqFrag = new FAQFragment();

        fTrans = getSupportFragmentManager().beginTransaction();
        fTrans.add(R.id.globalFrame,GFrag);
        fTrans.commit();

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
        finish();
        Toast toast = Toast.makeText(getApplicationContext(),
                "GOOOODBYYYYEEEE", Toast.LENGTH_SHORT);
        toast.show();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.global, menu);
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        fTrans2 = getSupportFragmentManager().beginTransaction();
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_search) {
            fTrans2.replace(R.id.globalFrame,GFrag);
        } else if (id == R.id.nav_catalog) {
            Intent activity1 = new Intent(GlobalActivity.this, SearchActivity.class);
            startActivity(activity1);
        } else if (id == R.id.nav_saved) {
            Intent activity1 = new Intent(GlobalActivity.this, SavedActivity.class);
            startActivity(activity1);
        }else if (id == R.id.nav_looked) {
            Intent activity1 = new Intent(GlobalActivity.this, LookedActivity.class);
            startActivity(activity1);
        }else if (id == R.id.nav_lists) {
            Intent activity1 = new Intent(GlobalActivity.this, ListActivity.class);
            startActivity(activity1);
        }else if (id == R.id.nav_statistic) {
            Intent activity1 = new Intent(GlobalActivity.this, StatisticActivity.class);
            startActivity(activity1);
        }else if (id == R.id.nav_settings) {
            Intent activity1 = new Intent(GlobalActivity.this, SettingsActivity.class);
            startActivity(activity1);
        }else if (id == R.id.nav_faq) {
            fTrans2.replace(R.id.globalFrame,FaqFrag);
        }

        fTrans2.addToBackStack(null);
        fTrans2.commit();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();

        SharedPreferences myPrefs = getSharedPreferences(LOGIN_SETTING, MODE_PRIVATE);
        String userEmail = myPrefs.getString(SAVED_EMAIL,null);
        String userLogin = myPrefs.getString(SAVED_LOGIN,null);

        if (userEmail != null && userLogin != null )
        {Toast toast = Toast.makeText(getApplicationContext(),
                "+++++++++", Toast.LENGTH_SHORT);
            toast.show();
            Loginq.setText(userLogin);
            Emailq.setText(userEmail);
        }else{
            Toast toast = Toast.makeText(getApplicationContext(),
                    "-------", Toast.LENGTH_SHORT);
            toast.show();
            Intent activity1 = new Intent(GlobalActivity.this, LoginActivity.class);
            startActivity(activity1);
        }
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
