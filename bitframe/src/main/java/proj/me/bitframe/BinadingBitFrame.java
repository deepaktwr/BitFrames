package proj.me.bitframe;

import android.content.res.ColorStateList;
import android.os.Build;
import android.view.View;
import android.widget.ProgressBar;

/**
 * Created by root on 13/3/16.
 */
 public class BinadingBitFrame {

     private View root;

     View bind(View root) {
         this.root = root;
         return root;
     }

    public void setProgressBarVisibility(boolean progressBarVisibility) {
        ProgressBar progressBar = root.findViewById(R.id.progress_loader);
        progressBar.setVisibility(progressBarVisibility ? View.VISIBLE : View.GONE);
    }

    public void setProgressBarColor(int progressBarColor) {
        ProgressBar progressBar = root.findViewById(R.id.progress_loader);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            progressBar.setBackgroundTintList(ColorStateList.valueOf(progressBarColor));
        }
    }

}
