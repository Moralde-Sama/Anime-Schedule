package epm.animeschedule;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabItem;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.QuerySnapshot;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.util.ArrayList;
import java.util.Calendar;

import epm.animeschedule.Adapter.AnimeAdapter;
import epm.animeschedule.CustomClasses.CompareTwoList;
import epm.animeschedule.CustomClasses.SearchAnime;
import epm.animeschedule.CustomClasses.SpinnerSelectedMonth;
import epm.animeschedule.MainClasses.Anime;
import epm.animeschedule.PopUp.Popup;

public class Main extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private Button Logout;

    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore db;
    public ListenerRegistration listenerAiring;
    public ListenerRegistration listenerUpcoming;
    public ListenerRegistration listenerLeftOver;

    public RecyclerView recyclerViewLatest;
    private RecyclerView RecyclerviewLeftover;
    private RecyclerView recyclerViewOngoing;

    public MaterialSearchView searchView;

    private TabLayout tab;
    private TabItem tabAiring;
    private TabItem tabUpcoming;
    private TabItem tabLeftOver;

    private Thread threadLatest;
    private Thread threadUpcoming;
    private Thread threadLeftOver;

    private android.support.v7.widget.Toolbar toolbar;

    public Spinner spinner;
    public SwipeRefreshLayout swipeRefreshAiring;
    protected SwipeRefreshLayout swipeRefreshUpcoming;
    protected SwipeRefreshLayout swipeRefreshLeftover;

    private BottomNavigationView bottomNavigationView;

    public SearchAnime Latest;

    private boolean[] tabselected = {true, false, false};
    public boolean[] FilterAnime = {false};
    public int selectedMonthNum;
    private boolean spinnerInitialize = false;

    public static Main getMainContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawer_activity_navigation_drawer);

        getMainContext = Main.this;

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowTitleEnabled(false);

        Logout = findViewById(R.id.btnLogout);
        tab = findViewById(R.id.tabsMain);
        tabAiring = findViewById(R.id.tabOngoing);
        tabUpcoming = findViewById(R.id.tabUpComing);
        tabLeftOver = findViewById(R.id.tabLeft);

        searchView = findViewById(R.id.searchView);

        swipeRefreshAiring = findViewById(R.id.swipelayouAiring);
        swipeRefreshLeftover = findViewById(R.id.swipelayouLeftover);
        swipeRefreshUpcoming = findViewById(R.id.swipelayouUpcoming);

        bottomNavigationView = findViewById(R.id.bottomNav);

        recyclerViewLatest = findViewById(R.id.Recyclerviewtoday);
        RecyclerviewLeftover = findViewById(R.id.RecyclerviewLeftover);
        recyclerViewOngoing = findViewById(R.id.RecyclerviewUpcoming);

        Latest = new SearchAnime(Main.this, recyclerViewLatest);

        //swipelayoutrefreshcolor
        swipeRefreshAiring.setColorSchemeColors(Color.rgb(36, 184, 239));
        swipeRefreshLeftover.setColorSchemeColors(Color.rgb(36, 184, 239));
        swipeRefreshUpcoming.setColorSchemeColors(Color.rgb(36, 184, 239));
        swipeRefreshAiring.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                    if(listenerAiring != null){ listenerAiring.remove(); }
                    ReceiveData();
                    tabselected[0] = false;
            }
        });
        swipeRefreshUpcoming.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if(listenerUpcoming != null){ listenerUpcoming.remove(); }
                ReceiveDataUpcoming();
                tabselected[1] = false;
            }
        });
        swipeRefreshLeftover.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if(listenerLeftOver != null){ listenerLeftOver.remove(); }
                ReceiveDataLeftover();
                tabselected[2] = false;
            }
        });


        //transition
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setAllowEnterTransitionOverlap(false);
            getWindow().setAllowReturnTransitionOverlap(false);
        }


        //navigationbar
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);




        //spinner
        Calendar calendar = Calendar.getInstance();

        ArrayList<String> SpinnerItem = new ArrayList<String>();
        SpinnerItem.add("Winter "+calendar.get(Calendar.YEAR)); SpinnerItem.add("Spring "+calendar.get(Calendar.YEAR));
        SpinnerItem.add("Summer "+calendar.get(Calendar.YEAR)); SpinnerItem.add("Fall "+calendar.get(Calendar.YEAR));
        spinner = findViewById(R.id.travelType_spinner);
        final ArrayAdapter<String> adapterspinner = new ArrayAdapter<String>(this,R.layout.custom_spinneritem, SpinnerItem);
        spinner.setAdapter(adapterspinner);

        //firebase
        firebaseAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
//        InitializeFirst();

        //SpinnerListener
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(tab.getSelectedTabPosition() == 0){
                    ReceiveData();
                    tabselected[0] = true;
                    tabselected[1] = false;
                    tabselected[2] = false;
                }
                else if(tab.getSelectedTabPosition() == 1){
                    ReceiveDataUpcoming();
                    tabselected[0] = false;
                    tabselected[1] = true;
                    tabselected[2] = false;
                }
                else{
                    ReceiveDataLeftover();
                    tabselected[0] = false;
                    tabselected[1] = false;
                    tabselected[2] = true;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        // Apply the adapter to the spinner
        if (spinner != null) {
            spinner.setAdapter(adapterspinner);
        }
        //set spinner season
        int Month = calendar.get(Calendar.MONTH);
        Month++;
        if(Month < 4){
            spinner.setSelection(0);
            selectedMonthNum = 0;
        }
        else if(Month < 7){
            spinner.setSelection(1);
            selectedMonthNum = 1;
        }
        else if(Month < 10){
            spinner.setSelection(2);
            selectedMonthNum = 2;
        }
        else{
            spinner.setSelection(3);
            selectedMonthNum = 3;
        }





        //tab listener
        tab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if(tab.getPosition() == 0){
                    swipeRefreshAiring.setVisibility(View.VISIBLE);
                    reAnimation(recyclerViewLatest);
                    if(!tabselected[0]){
                        ReceiveData();
                        tabselected[0] = true;
                    }
                }
                else if(tab.getPosition() == 1){
                    swipeRefreshUpcoming.setVisibility(View.VISIBLE);
                    reAnimation(recyclerViewOngoing);
                    if(!tabselected[1]){
                        ReceiveDataUpcoming();
                        tabselected[1] = true;
                    }
                }
                else if(tab.getPosition() == 2){
                    swipeRefreshLeftover.setVisibility(View.VISIBLE);
                    reAnimation(RecyclerviewLeftover);
                    if(!tabselected[2]){
                        ReceiveDataLeftover();
                        tabselected[2] = true;
                    }
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                if(tab.getPosition() == 0){
                    swipeRefreshAiring.setVisibility(View.GONE);
                }
                else if(tab.getPosition() == 1){
                    swipeRefreshUpcoming.setVisibility(View.GONE);
                }
                else if(tab.getPosition() == 2){
                    swipeRefreshLeftover.setVisibility(View.GONE);
                }
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


        //searchviewfilter
        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Latest.searchTitle(newText);
                runAnimation(recyclerViewLatest, 0, Latest.getAnimeResult());
                return true;
            }
        });


        //bottomnavigationview
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Menu menu = bottomNavigationView.getMenu();
                switch (item.getItemId()){
                    case R.id.home :
                        return true;
                    case R.id.favorites :
                        return true;
                    case R.id.recent :
                        return true;
                }
                return false;
            }
        });


        //initialize

        Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firebaseAuth.signOut();
                finish();
                startActivity(new Intent(getApplicationContext(), Login.class));
            }
        });

    }


    private ArrayList<Anime> animeListReceive = new ArrayList<>();
    private ArrayList<Anime> animeListReceive2 = new ArrayList<>();
    int runCount = 1;
    private void ReceiveData() {

            checkSearch();

        animeListReceive.clear();
        animeListReceive2.clear();

        swipeRefreshAiring.setRefreshing(true);
        threadLatest = new Thread(new Runnable() {
            @Override
            public void run() {


                Log.d("CountRun", "" + runCount);

                final SpinnerSelectedMonth selectedMonth = new SpinnerSelectedMonth(spinner);
                listenerAiring = db.collection("AnimeInfo").whereEqualTo("Season", selectedMonth.getSeasonName()).whereEqualTo("Status", "Ongoing")
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
                                    if (runCount == 1) {
                                        animeListReceive.add(anime);
                                        animeListReceive2.clear();
                                    } else {
                                        animeListReceive2.add(anime);
                                        animeListReceive.clear();
                                    }
                                }
                            }
                            Log.d("CountRun", "" + runCount);
                            if (runCount == 1) {
                                if(animeListReceive.size() != 0) {
                                    animeListReceive.add(new Anime("dsf", "dsf", "dsf", "dsf", "dsf"
                                            , "dsf", "dsf", "dsf", "dsf", "dsf", "dsf", "dsf"
                                            , "dsf", "dsf", "dsf", "dsf", "dsf", "dsf", "dsf", "dsf"));
                                }
                                Log.d("animelistleft", animeListReceive.size() + "");
                                runCount++;
                                runAnimation(recyclerViewLatest, 0, animeListReceive);
                                Latest.setArrayList(animeListReceive);
                            } else {
                                if(animeListReceive2.size() != 0) {
                                    animeListReceive2.add(new Anime("dsf", "dsf", "dsf", "dsf", "dsf"
                                            , "dsf", "dsf", "dsf", "dsf", "dsf", "dsf", "dsf"
                                            , "dsf", "dsf", "dsf", "dsf", "dsf", "dsf", "dsf", "dsf"));
                                }
                                Log.d("animelistleft", animeListReceive2.size() + "");
                                runCount--;

                                runAnimation(recyclerViewLatest, 0, animeListReceive2);
                                Latest.setArrayList(animeListReceive2);
                            }

                            }catch(Exception w){

                            }
                        swipeRefreshAiring.setRefreshing(false);

                        threadLatest.interrupt();
                    }
                });
            }
        });
        threadLatest.start();
    }
    private void ReceiveDataUpcoming() {

        final ArrayList<Anime> animeList = new ArrayList<>();
        swipeRefreshUpcoming.setRefreshing(true);
        if(animeList != null){ animeList.clear(); }
        threadUpcoming = new Thread(new Runnable() {
            @Override
            public void run() {
                listenerUpcoming = db.collection("AnimeInfo").orderBy("Title").whereEqualTo("Status", "Not yet Aired").addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(QuerySnapshot queryDocumentSnapshots, FirebaseFirestoreException e) {
                        try {
                            for (DocumentSnapshot ds : queryDocumentSnapshots.getDocuments()) {
                                Anime anime = new Anime(ds.getString("AnimeID"), ds.getString("Day"), ds.getString("Description"), ds.getString("Duration"),
                                        ds.getString("Episode"), ds.getString("ImageLink"), ds.getString("ImageLinkHeader"), ds.getString("Month"),
                                        ds.getString("Rating"), ds.getString("Score"), ds.getString("Season"), ds.getString("Source"), ds.getString("Start"), ds.getString("End"),
                                        ds.getString("Status"), ds.getString("Studio"), ds.getString("Title"), ds.getString("Type"), ds.getString("Year"),
                                        ds.getString("Time"));
                                animeList.add(anime);
                                Log.d("Refresh", ds.getString("Title"));
                            }
                        } catch (Exception w) {

                        }


                        /*AnimeAdapter adapter = new AnimeAdapter(getApplicationContext(), animeList, "", Main.this);
                        recyclerViewOngoing.hasFixedSize();
                        LinearLayoutManager manager = new LinearLayoutManager(getApplicationContext());
                        recyclerViewOngoing.setLayoutManager(manager);
                        recyclerViewOngoing.setAdapter(adapter);*/

                        swipeRefreshUpcoming.setRefreshing(false);

                        threadUpcoming.interrupt();
                    }
                });
            }
        });
        threadUpcoming.start();
    }

    private ArrayList<Anime> animeListLeft = new ArrayList<>();
    private ArrayList<Anime> animeListLeft2 = new ArrayList<>();
    private int runCountLeft = 1;
    private void ReceiveDataLeftover() {

        animeListLeft.clear();
        animeListLeft2.clear();

        final SpinnerSelectedMonth selectedMonth = new SpinnerSelectedMonth(spinner);
        swipeRefreshLeftover.setRefreshing(true);
        threadLeftOver = new Thread(new Runnable() {
            @Override
            public void run() {
                listenerLeftOver = db.collection("AnimeInfo").whereLessThan("Month", ""+selectedMonth.getSeasonalMonth()).whereEqualTo("Status", "Ongoing")
                        .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(QuerySnapshot queryDocumentSnapshots, FirebaseFirestoreException e) {
                        try {
                            for (DocumentSnapshot ds : queryDocumentSnapshots.getDocuments()) {
                                Anime anime = new Anime(ds.getString("AnimeID"), ds.getString("Day"), ds.getString("Description"), ds.getString("Duration"),
                                        ds.getString("Episode"), ds.getString("ImageLink"), ds.getString("ImageLinkHeader"), ds.getString("Month"),
                                        ds.getString("Rating"), ds.getString("Score"), ds.getString("Season"), ds.getString("Source"), ds.getString("Start"), ds.getString("End"),
                                        ds.getString("Status"), ds.getString("Studio"), ds.getString("Title"), ds.getString("Type"), ds.getString("Year"),
                                        ds.getString("Time"));

                                if(runCountLeft == 1){
                                    if (animeListLeft2 != null) animeListLeft2.clear();
                                    ArrayList<Anime> animeList = null;
                                    if(runCount == 1){
                                        animeList = animeListReceive2;
                                    }
                                    else {
                                        animeList = animeListReceive;
                                    }
                                    CompareTwoList compare = new CompareTwoList(animeList, ds.getString("AnimeID"));

                                    if(!compare.itemExist()) {
                                        animeListLeft.add(anime);
                                    }
                                }
                                else{
                                    if (animeListLeft != null) animeListLeft.clear();
                                    ArrayList<Anime> animeList = null;
                                    if(runCount == 1){
                                        animeList = animeListReceive2;
                                    }
                                    else {
                                        animeList = animeListReceive;
                                    }
                                    CompareTwoList compare = new CompareTwoList(animeList, ds.getString("AnimeID"));

                                    if(!compare.itemExist()) {
                                        animeListLeft2.add(anime);
                                    }
                                }
                            }
                            if(runCountLeft == 1){
                                if(animeListLeft.size() != 0) {
                                    animeListLeft.add(new Anime("dsf", "dsf", "dsf", "dsf", "dsf"
                                            , "dsf", "dsf", "dsf", "dsf", "dsf", "dsf", "dsf"
                                            , "dsf", "dsf", "dsf", "dsf", "dsf", "dsf", "dsf", "dsf"));
                                }
                                runCountLeft++;
                                runAnimation(RecyclerviewLeftover, 0, animeListLeft);
                            }
                            else {
                                if(animeListLeft.size() != 0) {
                                    animeListLeft2.add(new Anime("dsf", "dsf", "dsf", "dsf", "dsf"
                                            , "dsf", "dsf", "dsf", "dsf", "dsf", "dsf", "dsf"
                                            , "dsf", "dsf", "dsf", "dsf", "dsf", "dsf", "dsf", "dsf"));
                                }
                                runCountLeft--;
                                runAnimation(RecyclerviewLeftover, 0, animeListLeft2);
                            }
                        }catch(Exception w){

                        }

                        /*AnimeAdapter adapter = new AnimeAdapter(Main.this, animeList, "", Main.this);

                        RecyclerviewLeftover.hasFixedSize();
                        LinearLayoutManager manager = new LinearLayoutManager(getApplicationContext());
                        RecyclerviewLeftover.setLayoutManager(manager);
                        RecyclerviewLeftover.setAdapter(adapter);*/

                        swipeRefreshLeftover.setRefreshing(false);

                        threadLeftOver.interrupt();
                    }
                });
            }
        });
        threadLeftOver.start();
    }

    private void checkSearch(){
        if(searchView.isSearchOpen()){
            searchView.closeSearch();
        }
    }

    private void runAnimation(RecyclerView recyclerView, int type, ArrayList<Anime> animeList){
        Context context = recyclerView.getContext();
        LayoutAnimationController controller = null;


        if(type == 0){
            controller = AnimationUtils.loadLayoutAnimation(context, R.anim.layout_falldown);

            AnimeAdapter adapter = new AnimeAdapter(Main.this, animeList, "");
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutAnimation(controller);
            recyclerView.getAdapter().notifyDataSetChanged();
            recyclerView.scheduleLayoutAnimation();
        }
    }
    private void reAnimation(RecyclerView recyclerView){
        recyclerView.scheduleLayoutAnimation();
    }

    public void noFilter(boolean checked){
        if(checked) {
            spinner.setVisibility(View.GONE);
            tab.removeTabAt(2);
            getSupportActionBar().setDisplayShowTitleEnabled(true);
            getSupportActionBar().setTitle("No Filter");
        }
        else{
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            spinner.setVisibility(View.VISIBLE);
            tab.addTab(tab.newTab().setText("Leftovers"), 2);
            if(listenerAiring != null){ listenerAiring.remove(); }
            ReceiveData();
        }
    }

    private void removeTab(int position) {
        tab.removeTabAt(position);
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer =  findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if (searchView.isSearchOpen()) {
            searchView.closeSearch();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {
            firebaseAuth.signOut();
                finish();
                startActivity(new Intent(getApplicationContext(), Login.class));
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.filter) {
            startActivity(new Intent(Main.this, Popup.class));
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem item = menu.findItem(R.id.search);
        searchView.setMenuItem(item);
        return true;
    }

}
