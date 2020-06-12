package motoservice.com.Adapter;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import motoservice.com.Model.PictureCard;
import motoservice.com.R;

public class PictureAdapterRecyclerView extends RecyclerView.Adapter<PictureAdapterRecyclerView.PictureViewHolder>{
    private ArrayList<PictureCard> picturesCards; //array de datos con la clase que creamos
    private int resource;
    private Activity activity;

    public PictureAdapterRecyclerView(ArrayList<PictureCard> picturesCards, int resource, Activity activity) {
        this.picturesCards = picturesCards;
        this.resource = resource;
        this.activity = activity;
    }

    //Este método se encarga de inicializar la clase PictureViewHolder para que encuentre cada componente
    @Override
    public PictureViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //convertimos el código xml a un view  (código java)
        View view = LayoutInflater.from(parent.getContext()).inflate(resource,parent,false);
        //view inflado con su recurso catchado
        return new PictureViewHolder(view); //retorna el view
    }

    //Trabajaremos el paso de datos de cada objetos de la lista
    @Override
    public void onBindViewHolder(PictureViewHolder holder, int position) {
        //Recorrer la lista uno a uno y asignamos los objetos al cardview
        PictureCard pictureCard = picturesCards.get(position);
        holder.username.setText(pictureCard.getUsername());
        holder.like_driver.setText(pictureCard.getLike_driver());
        Picasso.with(activity).load(pictureCard.getPicture()).into(holder.picture);
    }

    @Override
    public int getItemCount() {
        return picturesCards.size();
    }

    public class PictureViewHolder extends RecyclerView.ViewHolder{
        private ImageView picture;
        private TextView username;
        private TextView like_driver;

        public PictureViewHolder(@NonNull View itemView) {
            super(itemView);
            picture = (ImageView)itemView.findViewById(R.id.photo_driver_card);
            username = (TextView)itemView.findViewById(R.id.username_driver_card);
            like_driver= (TextView)itemView.findViewById(R.id.valoration_driver_card);
        }


    }



}