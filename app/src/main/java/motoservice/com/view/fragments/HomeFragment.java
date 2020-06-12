package motoservice.com.view.fragments;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;
import motoservice.com.Adapter.PictureAdapterRecyclerView;
import motoservice.com.Model.PictureCard;
import motoservice.com.R;


public class HomeFragment extends Fragment {

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home,container,false);
        showToolbar("Principal",false,view);




        RecyclerView pictureRecycler = (RecyclerView) view.findViewById(R.id.picture_recycler);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        pictureRecycler.setLayoutManager(linearLayoutManager);
        PictureAdapterRecyclerView pictureAdapterRecyclerView =
                new PictureAdapterRecyclerView(cardDrivers(), R.layout.cardview_driver,getActivity());
        pictureRecycler.setAdapter(pictureAdapterRecyclerView);
        return view;
    }

    public ArrayList<PictureCard> cardDrivers(){



        ArrayList<PictureCard> pictureCards = new ArrayList<>();
        pictureCards.add(new PictureCard("https://st3.depositphotos.com/1000816/18136/i/450/depositphotos_181361292-stock-photo-attractive-elegant-serious-man-drives.jpg",
                "José Cáceres",getResources().getString(R.string.text_schedule_card)+" 9:00am a 7:00pm"));
        pictureCards.add(new PictureCard("https://www.interseguro.pe/wp-content/uploads/2019/12/chofer-de-reemplazo-como-cuando-pedir-blog.png",
                "Juan Pablo",getResources().getString(R.string.text_schedule_card)+" 7:00am a 4:00pm"));
        pictureCards.add(new PictureCard("https://i.eldiario.com.ec/fotos-manabi-ecuador/2018/06/20180624110020_choferes-de-fiesta.jpg",
                "Pablo Uriel",getResources().getString(R.string.text_schedule_card)+" 4:00pm a 8:00pm"));
        return pictureCards;
    }

    public void showToolbar(String title, boolean upButton, View view){
        Toolbar toolbar = (Toolbar)view.findViewById(R.id.toolbar);
        //Casstealo a un AppCompatActivity y trae un elemento igual al container
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(title);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(upButton);
    }

}
