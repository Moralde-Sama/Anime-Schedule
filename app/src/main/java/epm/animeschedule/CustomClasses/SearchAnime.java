package epm.animeschedule.CustomClasses;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.ArrayList;

import epm.animeschedule.MainClasses.Anime;

public class SearchAnime {
    private Context context;
    private RecyclerView recyclerView;
    private ArrayList<Anime> animeArrayList;
    private ArrayList<Anime> animeResult;
    private String Title;

    public SearchAnime(Context context, RecyclerView recyclerView) {
        this.context = context;
        this.recyclerView = recyclerView;
        animeResult = new ArrayList<>();
    }

    public void setArrayList(ArrayList<Anime> animeArrayList) {
        this.animeArrayList = animeArrayList;
    }

    public void searchTitle(String title) {
        Title = title;
        animeResult.clear();
    }

    public ArrayList<Anime> getAnimeResult(){
        try {
            int checksize = 0;
            if (!Title.isEmpty() && Title != null) {
                for (Anime anime : animeArrayList) {
                    if (anime.getTitle().toLowerCase().contains(Title)) {
                        Log.d("Compare", "Title: " + anime.getTitle());
                        animeResult.add(anime);
                        animeResult.get(0).getTitle();
                    }
                }
                if (animeResult.size() != 0 && animeResult.size() == 1) {
                    animeResult.add(new Anime("dsf", "dsf", "dsf", "dsf", "dsf"
                            , "dsf", "dsf", "dsf", "dsf", "dsf", "dsf", "dsf"
                            , "dsf", "dsf", "dsf", "dsf", "dsf", "dsf", "dsf", "dsf"));
                    return animeResult;
                }
                else{
                    return animeResult;
                }
            }
        } catch (Exception r) {
            Log.d("Error", "" + r);
        }
        return animeArrayList;
    }
}
