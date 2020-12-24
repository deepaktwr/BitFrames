package proj.me.bitframedemo.binders;


import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import proj.me.bitframedemo.R;

/**
 * Created by root on 20/9/16.
 */

public class FrameBinder {

    private View root;
    public View bind(View root, final View.OnClickListener clickHandler) {
        this.root = root;

        root.findViewById(R.id.error_text).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickHandler.onClick(view);
            }
        });

        root.findViewById(R.id.fab).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickHandler.onClick(view);
            }
        });

        return root;
    }

    public RecyclerView recyclerFrame() {
        return root.findViewById(R.id.recycler_frame);
    }

    public FloatingActionButton fab() {
        return root.findViewById(R.id.fab);
    }

    public void setErrorText(String errorText) {
        TextView textView = root.findViewById(R.id.error_text);
        textView.setText(errorText);
    }

    public void setHasData(boolean hasData) {
        root.findViewById(R.id.recycler_frame).setVisibility(hasData ? View.VISIBLE : View.GONE);
        root.findViewById(R.id.error_text).setVisibility(hasData ? View.GONE : View.VISIBLE);
    }
}
