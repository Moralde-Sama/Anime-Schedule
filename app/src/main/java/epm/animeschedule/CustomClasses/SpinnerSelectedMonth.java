package epm.animeschedule.CustomClasses;

import android.widget.Spinner;

public class SpinnerSelectedMonth {
    Spinner spinner;
    public SpinnerSelectedMonth(Spinner spinner) {
        this.spinner = spinner;
    }

    public int getSeasonalMonth() {
        if (spinner.getSelectedItemPosition() == 0) {
            return 12;
        } else if (spinner.getSelectedItemPosition() == 1) {
            return 4;
        } else if (spinner.getSelectedItemPosition() == 2) {
            return 7;
        } else {
            return 10;
        }
    }
    public String getSeasonName(){
        if (spinner.getSelectedItemPosition() == 0) {
            return "Winter";
        } else if (spinner.getSelectedItemPosition() == 1) {
            return "Spring";
        } else if (spinner.getSelectedItemPosition() == 2) {
            return "Summer";
        } else {
            return "Fall";
        }
    }
}
