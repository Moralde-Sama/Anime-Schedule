package epm.animeschedule.CustomClasses;

import android.util.Log;

import java.util.List;

import epm.animeschedule.MainClasses.Anime;

public class CompareTwoList {
    private List<Anime> animeList;
    private String animeID;
    public CompareTwoList(List<Anime> animeList, String animeID) {
        this.animeList = animeList;
        this.animeID = animeID;
    }
    public boolean itemExist(){

        for (int i = 0; i < animeList.size(); i++) {
            if(animeList.get(i).getAnimeID().equals(animeID)){
                Log.d("Existya", "Oo");
                return true;
            }
        }
        return false;
    }
}
