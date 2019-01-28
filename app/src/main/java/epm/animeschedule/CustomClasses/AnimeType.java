package epm.animeschedule.CustomClasses;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import epm.animeschedule.Adapter.AnimeAdapter;
import epm.animeschedule.MainClasses.Anime;
import epm.animeschedule.R;

public class AnimeType {
    private ViewGroup viewGroup;
    private List<Anime> animeList;
    private int currentPosition;
    private boolean[] animeType;
    private int getDataPosition;
    private View view;
    private boolean Status;

    public AnimeType(ViewGroup viewGroup, List<Anime> animeList, int CurrentPosition, boolean[] AnimeType) {
        this.viewGroup = viewGroup;
        this.animeList = animeList;
        currentPosition = CurrentPosition;
        animeType = AnimeType;
    }
    public AnimeAdapter.ViewHolder getViewHolder(){
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());

        Log.d("AnimeTYpe", getCurrentType() + " = " + animeList.get(currentPosition).getType() +" = "+animeList.size());
        if (animeList.get(currentPosition).getType().equals("TV") && getCurrentType().equals(animeList.get(currentPosition).getType())) {
            view = inflater.from(viewGroup.getContext()).inflate(R.layout.custom_recyclertype, viewGroup, false);
            setTypeBoolean(animeList.get(currentPosition).getType());
            Status = true;
        }
        else{
            view = inflater.from(viewGroup.getContext()).inflate(R.layout.custom_cardlayout, viewGroup, false);
            Status = false;
            getDataPosition = currentPosition-1;
        }
        AnimeAdapter.ViewHolder viewHolder = new AnimeAdapter.ViewHolder(view);

        return viewHolder;
    }
    public int getDataInfoPosition(){
        return getDataPosition;
    }
    public String getType(){
        if(animeList.get(currentPosition).getType().equals("TV")){
            return "TV Series";
        }
        return animeList.get(currentPosition).getType();
    }
    public boolean getViewHolderStatus(){
        return Status;
    }
    private String getCurrentType(){
        if(animeType[0] == true){
            return "TV";
        }
        else if(animeType[1] == true){
            return "OVA";
        }
        else if(animeType[2] == true){
            return "Movie";
        }
        return "";
    }
    private void setTypeBoolean(String type){
        if (type.equals("TV")) {
            animeType[0] = false;
        } else if (type.equals("OVA")) {
            animeType[1] = false;
        } else {
            animeType[2] = false;
        }
    }
    public boolean[] getBoolean(){
        return animeType;
    }
}
