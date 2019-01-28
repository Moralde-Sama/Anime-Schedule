package epm.animeschedule.PopUp;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SwitchCompat;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.Button;
import android.widget.CompoundButton;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import epm.animeschedule.Adapter.AnimeAdapter;
import epm.animeschedule.Main;
import epm.animeschedule.MainClasses.Anime;
import epm.animeschedule.R;

import static epm.animeschedule.Main.getMainContext;

public class Popup extends AppCompatActivity {

    private Button Cancel;
    private Button Confirm;

    private SwitchCompat NoFilter;

    private ArrayList<Anime> animeListReceive;

    private FirebaseFirestore db;

    private Main main;

    private boolean switchischecked;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_popup);

        Cancel = findViewById(R.id.filterCancel);
        Confirm = findViewById(R.id.filterConfirm);
        NoFilter = findViewById(R.id.switchNoFilter);

        main = getMainContext;

        db = FirebaseFirestore.getInstance();
        animeListReceive = new ArrayList<>();

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int) (width*.8), (int) (height*.8));

        NoFilter.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    switchischecked = true;
                    main.FilterAnime[0] = true;
                }
                else{
                    switchischecked = false;
                    main.FilterAnime[0] = false;
                }
            }
        });

        //setSwitchFilter
        if (main.FilterAnime[0]) {
            NoFilter.setChecked(true);
        } else {
            NoFilter.setChecked(false);
        }

        Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        Confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(switchischecked) { ReceiveData(); }
                main.noFilter(switchischecked);
                finish();
            }
        });
    }

    private void checkSearch(){
        if(main.searchView.isSearchOpen()){
            main.searchView.closeSearch();
        }
    }

    private Thread threadLatest;
    private void ReceiveData() {

        checkSearch();

        if(main.listenerAiring != null){ main.listenerAiring.remove(); }
        animeListReceive.clear();

        main.swipeRefreshAiring.setRefreshing(true);
        threadLatest = new Thread(new Runnable() {
            @Override
            public void run() {

//                final SpinnerSelectedMonth selectedMonth = new SpinnerSelectedMonth(main.spinner);
                main.listenerAiring = db.collection("AnimeInfo").whereEqualTo("Status", "Ongoing")
                        .orderBy("Title").addSnapshotListener(new EventListener<QuerySnapshot>() {
                            @Override
                            public void onEvent(QuerySnapshot queryDocumentSnapshots, FirebaseFirestoreException e) {
                                try {
                                    for (DocumentSnapshot ds : queryDocumentSnapshots.getDocuments()) {
                                        if (ds != null) {
                                            Anime anime = new Anime(ds.getString("AnimeID"), ds.getString("Day"), ds.getString("Description"), ds.getString("Duration"),
                                                    ds.getString("Episode"), ds.getString("ImageLink"), ds.getString("ImageLinkHeader"), ds.getString("Month"),
                                                    ds.getString("Rating"), ds.getString("Score"), ds.getString("Season"), ds.getString("Source"), ds.getString("Start"), ds.getString("End"),
                                                    ds.getString("Status"), ds.getString("Studio"), ds.getString("Title"), ds.getString("Type"), ds.getString("Year"),
                                                    ds.getString("Time"));
                                            Log.d("animelistleft", ds.getString("Title"));
                                                animeListReceive.add(anime);
                                        }
                                    }
                                        if(animeListReceive.size() != 0) {
                                            animeListReceive.add(new Anime("dsf", "dsf", "dsf", "dsf", "dsf"
                                                    , "dsf", "dsf", "dsf", "dsf", "dsf", "dsf", "dsf"
                                                    , "dsf", "dsf", "dsf", "dsf", "dsf", "dsf", "dsf", "dsf"));
                                        }
                                        runAnimation(main.recyclerViewLatest, 0, animeListReceive);
                                        main.Latest.setArrayList(animeListReceive);

                                }catch(Exception w){

                                }
                                main.swipeRefreshAiring.setRefreshing(false);

                                threadLatest.interrupt();
                            }
                        });
            }
        });
        threadLatest.start();
    }

    private void runAnimation(RecyclerView recyclerView, int type, ArrayList<Anime> animeList){
        Context context = recyclerView.getContext();
        LayoutAnimationController controller = null;


        if(type == 0){
            controller = AnimationUtils.loadLayoutAnimation(context, R.anim.layout_falldown);

            AnimeAdapter adapter = new AnimeAdapter(main, animeList, "");
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutAnimation(controller);
            recyclerView.getAdapter().notifyDataSetChanged();
            recyclerView.scheduleLayoutAnimation();
        }
    }
}
