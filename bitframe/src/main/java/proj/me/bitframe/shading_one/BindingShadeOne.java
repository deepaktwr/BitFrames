package proj.me.bitframe.shading_one;

import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import proj.me.bitframe.ImageClickHandler;
import proj.me.bitframe.R;


/**
 * Created by root on 11/3/16.
 */
public class BindingShadeOne {

    private View root;
    private String comment;

    View bind(View root, final ImageClickHandler clickHandler) {
        this.root = root;
        root.findViewById(R.id.view_single_image).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickHandler.onImageShadeClick(view);
            }
        });
        return root;
    }

    public void setImageScaleType(ImageView.ScaleType imageScaleType) {
        ImageView imageView = root.findViewById(R.id.view_single_image);
        imageView.setScaleType(imageScaleType);
    }

    public void setImageBackgroundColor(int imageBackgroundColor) {
        ImageView imageView = root.findViewById(R.id.view_single_image);
        imageView.setBackgroundColor(imageBackgroundColor);
    }

    public void setImageCounterText(String imageCounterText) {
        TextView textView = root.findViewById(R.id.counter_text);
        textView.setText(imageCounterText);
    }

    public void setImageCounterVisibility(boolean imageCounterVisibility) {
        LinearLayout linearLayout = root.findViewById(R.id.image_one_counter_container);
        linearLayout.setVisibility(imageCounterVisibility ? View.VISIBLE : View.GONE);
    }

    public void setShouldCommentVisible(boolean shouldCommentVisible) {
        TextView textView = root.findViewById(R.id.comment_text);
        textView.setVisibility(shouldCommentVisible ? View.VISIBLE : View.INVISIBLE);
    }

    public void setCommentTextBackgroundColor(int commentTextBackgroundColor) {
        TextView textView = root.findViewById(R.id.comment_text);
        textView.setBackgroundColor(commentTextBackgroundColor);
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
       this.comment = comment;
        TextView textView = root.findViewById(R.id.comment_text);
        textView.setText(comment);
    }

    public static void setLayoutWidth(View view, int width){
        ViewGroup.LayoutParams params = view.getLayoutParams();
        params.width = width;
        view.setLayoutParams(params);
    }

    public static void setLayoutHeight(View view, int height){
        ViewGroup.LayoutParams params = view.getLayoutParams();
        params.height = height;
        view.setLayoutParams(params);
    }

    public static void setBitmap(ImageView view, Bitmap bitmap){
        view.setImageBitmap(bitmap);
    }
}
