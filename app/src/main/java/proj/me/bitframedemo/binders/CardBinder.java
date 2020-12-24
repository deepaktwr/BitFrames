package proj.me.bitframedemo.binders;


import android.view.View;
import android.widget.TextView;

import proj.me.bitframe.ViewFrame;
import proj.me.bitframedemo.R;

/**
 * Created by root on 20/9/16.
 */

public class CardBinder {

    private View root;

    public View bind(View root) {
        this.root = root;
        return root;
    }

    public ViewFrame viewFrame() {
        return root.findViewById(R.id.view_frame);
    }

    public void setTitle(String title) {
        TextView textView = root.findViewById(R.id.title_text);
        textView.setText(title);
    }

    public void setDescription(String description) {
        TextView textView = root.findViewById(R.id.description_text);
        textView.setText(description);
    }

    public void setTitleColor(int titleColor) {
        TextView textView = root.findViewById(R.id.title_text);
        textView.setTextColor(titleColor);
    }

    public void setDescriptionColor(int descriptionColor) {
        TextView textView = root.findViewById(R.id.description_text);
        textView.setTextColor(descriptionColor);
    }
}
