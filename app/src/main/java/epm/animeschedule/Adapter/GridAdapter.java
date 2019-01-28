package epm.animeschedule.Adapter;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ProgressBar;

import java.util.List;

import epm.animeschedule.CustomClasses.GridViewLoading;
import epm.animeschedule.MainClasses.Genres;
import epm.animeschedule.R;
import me.zhanghai.android.materialprogressbar.IndeterminateHorizontalProgressDrawable;

public class GridAdapter extends BaseAdapter {

    private Context context;
    private List<Genres> values;
    private View view2;

    public GridAdapter(Context context, List<Genres> values) {
        this.context = context;
        this.values = values;
    }

    @Override
    public int getCount() {
        return values.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view == null){
            GridViewLoading viewLoading = new GridViewLoading(values, context, viewGroup);
            view2 = viewLoading.getView();
            ProgressBar progressBar = view2.findViewById(R.id.progressBarInfoGrid);
                if(!viewLoading.getLoading()) {
                    progressBar.setIndeterminateDrawable(new IndeterminateHorizontalProgressDrawable(context));
                    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                         progressBar.setIndeterminateTintList(ColorStateList.valueOf(Color.rgb(36, 184, 239)));
                    }
                }
            if(viewLoading.getLoading()) {
                Button button = view2.findViewById(R.id.btnGenre);
                button.setText(values.get(i).getGenres());

                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                });
            }
        }
        return view2;
    }
}
