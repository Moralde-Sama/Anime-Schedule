package epm.animeschedule.MainClasses;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import epm.animeschedule.R;

public class AnimeList extends ArrayAdapter<Anime> {
    private Activity context;
    private List<Anime> animelist;

    public AnimeList(Activity context, List<Anime> animelist){
        super(context, R.layout.custom_list_layout, animelist);
        this.context = context;
        this.animelist = animelist;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();

        View listviewitem = inflater.inflate(R.layout.custom_list_layout, null, true);

        TextView Title = listviewitem.findViewById(R.id.lblTitle);
        TextView Status = listviewitem.findViewById(R.id.lblStatus);

        Anime anime = animelist.get(position);

        Title.setText(anime.getTitle());
        Status.setText(anime.getStatus());

        return listviewitem;
    }
}
