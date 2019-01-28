package epm.animeschedule;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import epm.animeschedule.Adapter.GridAdapter;
import epm.animeschedule.MainClasses.Genres;

public class AnimeInfo extends AppCompatActivity {

    private TextView Title;
    private TextView Desciption;
    private TextView Episode;
    private TextView Studio;
    private TextView Season;
    private TextView Duration;
    private TextView Source;
    private TextView Rating;
    private TextView StartDate;
    private TextView EndDate;
    private TextView Score;
    private ImageView Image;
    private ImageView ImgHeader;
    private GridView Genre;
    private TabLayout tabLayoutInfo;
    private RelativeLayout AnimeInfo;
    private ProgressBar progressBarHeader;
    private ProgressBar progressBarImg;
    private NestedScrollView scrollView;
    private CollapsingToolbarLayout toolbarLayout;
    private Bundle bundle;
    private ProgressBar progressBarDate;
    private ProgressBar GridLoading;
    private boolean InfoSelected = true;

    private String AnimeID;
    private String[] values = {"Romance", "Comedy", "Drama", "Supernatural", "Josei", "Harem", "Adventure", "Ecchi", "Action", "Shoujo"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity_anime_info);


        android.support.v7.widget.Toolbar toolbar = findViewById(R.id.animeInfoToolbar);
        setSupportActionBar(toolbar);

        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setSubtitleTextColor(Color.WHITE);
        //transition

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setAllowEnterTransitionOverlap(false);
            getWindow().setAllowReturnTransitionOverlap(false);
        }

        Image = findViewById(R.id.imgAnimeInfo);
        Desciption = findViewById(R.id.lblInfoDescription);
        Episode = findViewById(R.id.lblInfoEpisode);
        Studio = findViewById(R.id.lblInfoStudioDes);
        scrollView = findViewById(R.id.nestedScrollView);
        StartDate = findViewById(R.id.lblInfoStart);
        GridLoading = findViewById(R.id.progressBarInfoGrid);
        EndDate = findViewById(R.id.lblInfoEnd);
        Score = findViewById(R.id.lblInfoScoreDef);
        Duration = findViewById(R.id.lblInfoDurationDes);
        Source = findViewById(R.id.lblInfoSourceDes);
        progressBarDate = findViewById(R.id.horizontalprogress);
        Genre = findViewById(R.id.gridViewGenre);
        Rating = findViewById(R.id.lblInfoRatingDes);
        progressBarImg = findViewById(R.id.progressbarInfoImg);
        progressBarHeader = findViewById(R.id.progressbarInfoHeader);
        Title = findViewById(R.id.lblInfoTitle);
        Season = findViewById(R.id.lblInfoSeasonDes);
        ImgHeader = findViewById(R.id.imgAnimeInfoHeader);
        AnimeInfo = findViewById(R.id.RLInformation);
        tabLayoutInfo = findViewById(R.id.tabLayoutInfo);
        toolbarLayout = findViewById(R.id.collapsingtoolbar);
        bundle = getIntent().getExtras();

        //setGridView
        List<Genres> setLoadingGrid = new ArrayList<>();
        setLoadingGrid.add(new Genres("Loading"));
        GridAdapter adapter = new GridAdapter(getApplicationContext(), setLoadingGrid);
        Genre.setAdapter(adapter);

        //set Activity Title
        toolbarLayout.setTitle(bundle.getString("Title"));
        if(bundle.getString("Title").length() < 17){ Title.setTextSize(24); }
        AnimeID = bundle.getString("AnimeID");
        Title.setText(bundle.getString("Title"));
        Episode.setText(bundle.getString("Episode"));
        Season.setText(bundle.getString("Season")+" "+bundle.getString("Year"));
        Studio.setText(bundle.getString("Studio"));
        Duration.setText(bundle.getString("Duration"));
        Source.setText(bundle.getString("Source"));
        Rating.setText(bundle.getString("Rating"));
        StartDate.setText("Start: "+bundle.getString("StartDate"));
        EndDate.setText("End: "+bundle.getString("EndDate"));
        Score.setText(bundle.getString("Score"));

        //progressBarDate
        for (int i = 0; i < bundle.getString("Episode").length(); i++) {
            if (bundle.getString("Episode").substring(i, (++i)).equals(" ")) {
                try {
                    progressBarDate.setProgress((bundle.getInt("CurrentEpisode")-1));
                    progressBarDate.setMax(Integer.parseInt(bundle.getString("Episode").substring(0, (--i))));
                }catch (Exception r){
                    progressBarDate.setProgress(0);
                    progressBarDate.setMax(10);
                }
            }
        }


        tabLayoutInfo.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == 0) {
                    AnimeInfo.setVisibility(View.VISIBLE);
                    InfoSelected = true;
                } else {
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                if (tab.getPosition() == 0) {
                    AnimeInfo.setVisibility(View.GONE);
                    InfoSelected = false;
                } else {
                }
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        progressBarHeader.setVisibility(View.VISIBLE);
        Picasso.get()
                .load(bundle.getString("ImageLinkHeader"))
                .into(ImgHeader, new Callback() {
                    @Override
                    public void onSuccess() {
                        progressBarHeader.setVisibility(View.INVISIBLE);
                    }

                    @Override
                    public void onError(Exception e) {
                        progressBarHeader.setVisibility(View.INVISIBLE);
                    }
                });

        Picasso.get()
                .load(bundle.getString("ImageLink"))
                .error(R.drawable.unavailbleinfo)
                .into(Image, new Callback() {
                    @Override
                    public void onSuccess() {
                        progressBarImg.setVisibility(View.INVISIBLE);
                        Desciption.setText(bundle.getString("Description"));
                    }

                    @Override
                    public void onError(Exception e) {
                        progressBarImg.setVisibility(View.INVISIBLE);
                    }
                });


    }

    public int convertDpToPixels(float dp, Context context){
        Resources resources = context.getResources();
        return (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                dp,
                resources.getDisplayMetrics()
        );
    }

    private FirebaseFirestore db;
    private void getGenres(){

        final List<Genres> genres =  new ArrayList<>();

        db = FirebaseFirestore.getInstance();
        db.collection("Genres").whereEqualTo(AnimeID, AnimeID).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            int count = 0;
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                genres.add(new Genres(document.getId()));
                                Log.d("DocumentSnapshot", document.getId());
                                count++;
                            }
                            if(genres.size() <= 0) {
                                List<Genres> genres1 = new ArrayList<>();
                                genres1.add(new Genres("No"));
                                genres1.add(new Genres("Genre"));
                                genres1.add(new Genres("For The"));
                                genres1.add(new Genres("Moment"));
                                setGridItem(genres1);
                            }
                            else{
                                setGridItem(genres);
                            }

                        } else {
                            Log.d("DocumentSnapshot", "Error getting documents: ", task.getException());
                        }
                    }
                });
    }

    private void setGridItem(List<Genres> genres){
        Genre.setAdapter(null);
        Genre.setNumColumns(4);
        if(genres.size() > 4){
            Log.d("Result", "true");
            int gridsize = 0;
            double result = ((double) genres.size())/4;
            if((result- Math.floor(result)) == .25){
                int rowcount = (int) Math.round(result)+1;
                for (int i = 0; i < rowcount; i++) {
                    gridsize += 35;
                }
            }
            else{
                int rowcount = (int) Math.round(result);
                for (int i = 0; i < rowcount; i++) {
                    gridsize += 35;
                }
            }

            ViewGroup.LayoutParams layoutParams = Genre.getLayoutParams();
            layoutParams.height = convertDpToPixels(gridsize, getApplicationContext()); //this is in pixels
            Genre.setLayoutParams(layoutParams);

            GridAdapter adapter = new GridAdapter(getApplicationContext(), genres);
            Genre.setAdapter(adapter);
        }
        else{
            Log.d("Result", "False");

            GridAdapter adapter = new GridAdapter(getApplicationContext(), genres);
            Genre.setAdapter(adapter);
        }
    }


    @Override
    protected void onStart() {
        super.onStart();
        getGenres();
    }

    @Override
    protected void onResume() {
        super.onResume();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @Override
    public void onBackPressed() {
        if (!InfoSelected) {
            tabLayoutInfo.setScrollPosition(0, 0f, true);
            AnimeInfo.setVisibility(View.VISIBLE);
        }
        scrollView.scrollTo(0, 0);
        super.onBackPressed();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}
