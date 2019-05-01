package ml.lylin.utils.fueldata;

import android.app.Fragment;
import android.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.NonNull;
import com.google.android.material.navigation.NavigationView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import ml.lylin.utils.fueldata.db.FuelData;
import ml.lylin.utils.fueldata.fragments.AddFragment;
import ml.lylin.utils.fueldata.fragments.ListFragment;
import ml.lylin.utils.fueldata.fragments.SettingsFragment;
import ml.lylin.utils.fueldata.fragments.StatFragment;
import ml.lylin.utils.fueldata.viewmodel.FuelDataViewModel;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, AddFragment.OnFragmentInteractionListener,
        SettingsFragment.OnFragmentInteractionListener{

    private FuelDataViewModel mViewModel;
    //private LiveData<List<FillingRecord>> fillingRecordList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mViewModel = ViewModelProviders.of(this).get(FuelDataViewModel.class);
        mViewModel.getFuelDataList().observe(this, fuelData -> Toast.makeText(getApplicationContext(), "LiveData changed", Toast.LENGTH_SHORT).show());

        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        insertFragment(AddFragment.newInstance("", ""));

    }

    private void insertFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_add) {
            insertFragment(AddFragment.newInstance("", ""));
        } else if (id == R.id.nav_list) {
            insertFragment(ListFragment.newInstance());
        } else if (id == R.id.nav_statistics) {
            insertFragment(StatFragment.newInstance());
        } else if (id == R.id.nav_settings) {
            insertFragment(SettingsFragment.newInstance("", ""));
        } else if (id == R.id.nav_advertising) {

        } else if (id == R.id.nav_info) {

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void onFragmentInteraction(Uri uri) {
    }

    @Override
    public void onBtnWritePressed(FuelData fuelData) {
        mViewModel.insertFuelData(fuelData);
        Toast.makeText(this, "Data inserted", Toast.LENGTH_SHORT).show();
    }

}
