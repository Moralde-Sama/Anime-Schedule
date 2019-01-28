package epm.animeschedule.Adapter;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Pair;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import epm.animeschedule.AnimeInfo;
import epm.animeschedule.CustomClasses.AnimeType;
import epm.animeschedule.CustomClasses.CountDownTimer;
import epm.animeschedule.Main;
import epm.animeschedule.MainClasses.Anime;
import epm.animeschedule.R;

public class AnimeAdapter extends RecyclerView.Adapter<AnimeAdapter.ViewHolder> {

    private Context context;
    private ArrayList<Anime> animelist;
    private String tab;
    private ArrayList<Anime> newcopy;
    private Main main;
    private int previousPosiition = 0;
    private boolean[] AnimeTypeBool = {true, true, true};
    private int currentPosition = 0;
    private CountDownTimer countDownTimer;
    private AnimeType animeType;

    public AnimeAdapter(Main context, ArrayList<Anime> animelist, String tab){
        this.context = context;
        this.animelist = animelist;
        this.tab = tab;
        newcopy = animelist;
        main = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        animeType = new AnimeType(parent, animelist, currentPosition, AnimeTypeBool);
        AnimeTypeBool = animeType.getBoolean();
        currentPosition++;
        return animeType.getViewHolder();
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        try {
            final Anime anime = animelist.get(animeType.getDataInfoPosition());

            if (animeType.getViewHolderStatus()) {
                holder.Type.setText(animeType.getType());
            } else {
                holder.progressBar.setVisibility(View.VISIBLE);
                final ProgressBar bar = holder.progressBar;
                Picasso.get()
                        .load(anime.getImageLink())
                        .error(R.drawable.unavailble)
                        .into(holder.imgAnime, new Callback() {
                            @Override
                            public void onSuccess() {
                                bar.setVisibility(View.GONE);
                            }

                            @Override
                            public void onError(Exception e) {
                                holder.progressBar.setVisibility(View.INVISIBLE);
                            }
                        });
                holder.Description.setText(anime.getDescription());
                holder.Title.setVisibility(View.INVISIBLE);
                holder.Title.setText(anime.getTitle());
                holder.Title.post(new Runnable() {
                    @Override
                    public void run() {
                        if (holder.Title.getLineCount() == 2) {
                            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                            params.setMargins(0, 18, 0, 0);
                            holder.Timer.setLayoutParams(params);
                        } else {
                            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                            params.setMargins(0, 34, 0, 0);
                            holder.Timer.setLayoutParams(params);
                        }
                        holder.Title.setVisibility(View.VISIBLE);
                    }
                });

                holder.Status.setText(anime.getStatus() + " - " + anime.getType());


                countDownTimer = new CountDownTimer(1000000000, 1000, holder.Timer, anime.getTime(),
                        anime.getMonth(), anime.getDay(), main);
                countDownTimer.start();

                holder.cardView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        main.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Pair[] pairs = new Pair[1];
                                pairs[0] = new Pair<View, String>(holder.imgAnime, "imageTransition");

                                Bundle bundle = new Bundle();
                                bundle.putString("AnimeID", anime.getAnimeID());
                                bundle.putString("ImageLinkHeader", anime.getImageLinkHeader());
                                bundle.putString("ImageLink", anime.getImageLink());
                                bundle.putString("Title", anime.getTitle());
                                bundle.putString("Description", anime.getDescription());
                                bundle.putString("Episode", anime.getEpisode());
                                bundle.putString("Season", anime.getSeason());
                                bundle.putString("Year", anime.getYear());
                                bundle.putString("Studio", anime.getStudio());
                                bundle.putString("Duration", anime.getDuration());
                                bundle.putString("Source", anime.getSource());
                                bundle.putString("Rating", anime.getRating());
                                bundle.putString("StartDate", anime.getStart());
                                bundle.putString("EndDate", anime.getEnd());
                                bundle.putString("Score", anime.getScore());
                                bundle.putString("Episode", anime.getEpisode());
                                bundle.putInt("CurrentEpisode", countDownTimer.getEpisode());

                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && holder.imgAnime != null) {
                                    ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(main, pairs);
                                    context.startActivity(new Intent(context.getApplicationContext(), AnimeInfo.class).putExtras(bundle), options.toBundle());
                                } else {
                                    context.startActivity(new Intent(context.getApplicationContext(), AnimeInfo.class).putExtras(bundle));
                                }

                            }
                        });

                    }
                });
            }
        }catch(Exception e){

        }


    }


    @Override
    public int getItemCount() {
        if(animelist == null){
            return 0;
        }
        else {
            return animelist.size();
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public CardView cardView;
        public ProgressBar progressBar;
        public ImageView imgAnime;
        public TextView Title;
        public TextView Status;
        public TextView Timer;
        public TextView Description;
        public TextView Type;

        public ViewHolder(View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.cardview);
            Title = itemView.findViewById(R.id.lblTitlecard);
            Status = itemView.findViewById(R.id.lblStatuscard);
            imgAnime = itemView.findViewById(R.id.imgAnime);
            progressBar = itemView.findViewById(R.id.progressbarcard);
            Timer = itemView.findViewById(R.id.lblTime);
            Description = itemView.findViewById(R.id.lblDescriptionCard);
            Type = itemView.findViewById(R.id.lblType);

            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                if(imgAnime != null) {
                    imgAnime.setClipToOutline(true);
                }
            }

        }
    }
}
