package epm.animeschedule.CustomClasses;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import epm.animeschedule.MainClasses.Genres;
import epm.animeschedule.R;

public class GridViewLoading {

    private List<Genres> genresList;
    private Context context;
    private ViewGroup viewGroup;
    private View view;
    private boolean notloading;

    public GridViewLoading(List<Genres> genresList, Context context, ViewGroup viewGroup) {
        this.genresList = genresList;
        this.context = context;
        this.viewGroup = viewGroup;

    }
    public View getView(){
        if (genresList.size() == 1) {
            if (genresList.get(0).getGenres().equals("Loading")) {
                view = new View(context);
                view = LayoutInflater.from(context).inflate(R.layout.custom_gridviewloadingitem, viewGroup, false);
                notloading = false;
                return view;
            }
            else{
                view = LayoutInflater.from(context).inflate(R.layout.custom_gridviewitem, viewGroup, false);
                notloading = true;
                return  view;
            }
        }
        else{
            view = LayoutInflater.from(context).inflate(R.layout.custom_gridviewitem, viewGroup, false);
            notloading = true;
            return  view;
        }
    }
    public boolean getLoading(){
        return notloading;
    }

}
