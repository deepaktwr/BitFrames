package proj.me.bitframe.shading_two;

import android.graphics.Bitmap;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import proj.me.bitframe.ImageClickHandler;
import proj.me.bitframe.R;


/**
 * Created by root on 14/3/16.
 */
public class BindingShadeTwo {
    private static final String DOUBLE_IMG1_CONTAINER = "double_img1_container";
    private static final String DOUBLE_IMG2_CONTAINER = "double_img2_container";

    private View root;

    private String firstComment;
    private String secondComment;
    private int firstImageBgColor;
    private int secondImageBgColor;
    private boolean addVisibility;
    private boolean addAsCounter;

    View bind(View root, final ImageClickHandler clickHandler) {
        this.root = root;

        root.findViewById(R.id.view_double_image1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickHandler.onImageShadeClick(view);
            }
        });

        root.findViewById(R.id.view_double_image2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickHandler.onImageShadeClick(view);
            }
        });

        root.findViewById(R.id.add_text_container).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickHandler.onImageShadeClick(view);
            }
        });

        return root;
    }

    public void setAddAsCounter(boolean addAsCounter) {
        this.addAsCounter = addAsCounter;
        LinearLayout linearLayout = root.findViewById(R.id.add_text_container);
        linearLayout.setClickable(!addAsCounter);

        //check if the d_30 would take directly from xhdpi or others and doesn't multiply with density
        //otherwise getting from applyDimension makes more sense by passing raw value -- it won't be correct - as what defined in xml is very different for d_30 for xdpi than 30dp
        TextView textView = root.findViewById(R.id.add_text);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                root.getResources().getDimension(addAsCounter ? R.dimen.d_30 : R.dimen.d_50));
    }

    public void setDividerColor(int dividerColor) {
        RelativeLayout relativeLayout = root.findViewById(R.id.double_shade_parent);
        relativeLayout.setBackgroundColor(dividerColor);
    }

    public void setFirstImageBgColor(int firstImageBgColor) {
        this.firstImageBgColor = firstImageBgColor;
        RelativeLayout relativeLayout = root.findViewWithTag(DOUBLE_IMG1_CONTAINER);
        relativeLayout.setBackgroundColor(firstImageBgColor);
    }

    public void setFirstCommentBgColor(int firstCommentBgColor) {
        TextView textView = root.findViewById(R.id.first_comment);
        textView.setBackgroundColor(firstCommentBgColor);
    }

    public void setFirstComment(String firstComment) {
        this.firstComment = firstComment;
        TextView textView = root.findViewById(R.id.first_comment);
        textView.setText(firstComment);
    }

    public void setFirstCommentVisibility(boolean firstCommentVisibility) {
        TextView textView = root.findViewById(R.id.first_comment);
        textView.setVisibility(firstCommentVisibility ? View.VISIBLE : View.GONE);
    }

    public void setFirstImageScaleType(ImageView.ScaleType firstImageScaleType) {
        ImageView imageView = root.findViewById(R.id.view_double_image1);
        imageView.setScaleType(firstImageScaleType);
    }

    public void setSecondImageBgColor(int secondImageBgColor) {
        this.secondImageBgColor = secondImageBgColor;
        RelativeLayout relativeLayout = root.findViewWithTag(DOUBLE_IMG2_CONTAINER);
        relativeLayout.setBackgroundColor(secondImageBgColor);
    }

    public void setSecondCommentBgColor(int secondCommentBgColor) {
        TextView textView = root.findViewById(R.id.second_comment);
        textView.setBackgroundColor(secondCommentBgColor);
    }

    public void setSecondComment(String secondComment) {
        this.secondComment = secondComment;
        TextView textView = root.findViewById(R.id.second_comment);
        textView.setText(secondComment);
    }

    public void setSecondCommentVisibility(boolean secondCommentVisibility) {
        TextView textView = root.findViewById(R.id.second_comment);
        textView.setVisibility(secondCommentVisibility ? View.VISIBLE : View.GONE);
    }

    public void setSecondImageScaleType(ImageView.ScaleType secondImageScaleType) {
        ImageView imageView = root.findViewById(R.id.view_double_image2);
        imageView.setScaleType(secondImageScaleType);
    }

    public void setAddBgColor(int addBgColor) {
        LinearLayout linearLayout = root.findViewById(R.id.add_text_container);
        linearLayout.setBackgroundColor(addBgColor);
    }

    public void setAddTextColor(int addTextColor) {
        TextView textView = root.findViewById(R.id.add_text);
        textView.setTextColor(addTextColor);
    }

    public void setAddText(String addText) {
        TextView textView = root.findViewById(R.id.add_text);
        textView.setText(addText);
    }

    public void setAddVisibility(boolean addVisibility) {
        this.addVisibility = addVisibility;
        LinearLayout linearLayout = root.findViewById(R.id.add_text_container);
        linearLayout.setVisibility(addVisibility ? View.VISIBLE : View.GONE);
    }

    public void setCounterText(String counterText) {
        TextView textView = root.findViewById(R.id.counter_text);
        textView.setText(counterText);
    }

    public void setCounterVisibility(boolean counterVisibility) {
        LinearLayout linearLayout = root.findViewById(R.id.image_double_counter_container);
        linearLayout.setVisibility(counterVisibility ? View.VISIBLE : View.GONE);
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

    public String getFirstComment() {
        return firstComment;
    }

    public String getSecondComment() {
        return secondComment;
    }

    public int getFirstImageBgColor() {
        return firstImageBgColor;
    }

    public int getSecondImageBgColor() {
        return secondImageBgColor;
    }

    public boolean isAddVisibility() {
        return addVisibility;
    }

    public boolean isAddAsCounter() {
        return addAsCounter;
    }
}
