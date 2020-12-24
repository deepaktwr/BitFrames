package proj.me.bitframedemo;

import android.content.res.ColorStateList;
import android.os.Build;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

/**
 * Created by root on 23/4/16.
 */
public class BindingAddText {
    private View root;

    View bind(View root, final IntentAction clickHandler) {
        this.root = root;

        root.findViewById(R.id.extra_text).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickHandler.addMoreImages(view);
            }
        });

        return root;
    }

    public void setTextVisibility(boolean textVisibility) {
        FloatingActionButton floatingActionButton = root.findViewById(R.id.extra_text);
        floatingActionButton.setVisibility(textVisibility ? View.VISIBLE : View.GONE);
    }

    public void setTextColor(int textColor) {
        FloatingActionButton floatingActionButton = root.findViewById(R.id.extra_text);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            floatingActionButton.setImageTintList(ColorStateList.valueOf(textColor));
        } else floatingActionButton.setSupportImageTintList(ColorStateList.valueOf(textColor));
    }

    public void setErrorVisibility(boolean errorVisibility) {
        TextView textView = root.findViewById(R.id.error_text);
        textView.setVisibility(errorVisibility ? View.VISIBLE : View.GONE);
    }
}
