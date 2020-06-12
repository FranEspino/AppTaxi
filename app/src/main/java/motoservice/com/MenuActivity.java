package motoservice.com;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.ui.AppBarConfiguration;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import com.facebook.login.LoginManager;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import motoservice.com.view.fragments.DriversFragment;
import motoservice.com.view.fragments.HomeFragment;
import motoservice.com.view.fragments.ServicesFragment;

public class MenuActivity extends AppCompatActivity {
    private AppBarConfiguration mAppBarConfiguration;
    private TextView txt_name_user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        txt_name_user = findViewById(R.id.nombre_usuario);
        BottomNavigationView bottomBar =findViewById(R.id.bottombar_nav);
        bottomBar.setOnNavigationItemSelectedListener(navListener);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if(user!= null){

            if(user.getDisplayName()==null){
                String name_fr = user.getEmail();
                txt_name_user.setText(name_fr);
            }else{
                String name_fb = user.getDisplayName();
                txt_name_user.setText(name_fb);
            }

        }else{
            goLoginScreen();
        }


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow,
                R.id.nav_tools, R.id.nav_share, R.id.nav_send)
                .setDrawerLayout(drawer)
                .build();

    }
    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;

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
                        default: selectedFragment = new HomeFragment();
                            break;
                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.container_fragments,
                            selectedFragment).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                            .addToBackStack(null).commit();
                    return true;
                }
            };
    public void showToolbar(String title, boolean upButton){
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(upButton);
    }

    //method for init session
    public void logout(View view) {
        FirebaseAuth.getInstance().signOut(); //for firebase
        LoginManager.getInstance().logOut(); //for facebook
        goLoginScreen();
    }

    private void goLoginScreen(){
        Intent intent = new Intent(this , LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    public void goRequest(View view){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        Intent intent = new Intent(this, request_DriverActivity.class);
        intent.putExtra("correo_user",user.getEmail());
        intent.putExtra("chofer_user","Mario Alvares");
        startActivity(intent);
    }
}



