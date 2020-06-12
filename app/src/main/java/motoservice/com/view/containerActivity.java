package motoservice.com.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import android.os.Bundle;
import android.view.MenuItem;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import motoservice.com.R;
import motoservice.com.view.fragments.DriversFragment;
import motoservice.com.view.fragments.HomeFragment;
import motoservice.com.view.fragments.ServicesFragment;

public class containerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_container);
        BottomNavigationView bottomNav =findViewById(R.id.bottombar_nav);
        bottomNav.setOnNavigationItemSelectedListener(navListener);
    }
    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;
                    selectedFragment = new HomeFragment();
                    switch (item.getItemId()){
                        case R.id.tab_home:
                            selectedFragment = new HomeFragment();
                            break;
                        case R.id.tab_drivers:
                            selectedFragment = new DriversFragment();
                            break;
                        case R.id.tab_services:
                            selectedFragment = new ServicesFragment();
                            break;
                    }
                getSupportFragmentManager().beginTransaction().replace(R.id.container_fragments,
                        selectedFragment).commit();
                    return true;
                }
            };
}
