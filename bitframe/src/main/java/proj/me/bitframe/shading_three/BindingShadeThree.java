package proj.me.bitframe.shading_three;

import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import proj.me.bitframe.ImageClickHandler;
import proj.me.bitframe.R;

/**
 * Created by root on 16/3/16.
 */
public class BindingShadeThree {
    private static final String TRIPLE_IMG1_CONTAINER = "triple_img1_container";
    private static final String TRIPLE_IMG2_CONTAINER = "triple_img2_container";
    private static final String TRIPLE_IMG3_CONTAINER = "triple_img3_container";

    private View root;

    private int firstImageBgColor;
    private String firstComment;

    private int secondImageBgColor;
    private String secondComment;

    private int thirdImageBgColor;
    private String thirdComment;

    View bind(View root, final ImageClickHandler clickHandler) {
        this.root = root;

        root.findViewById(R.id.view_triple_image1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickHandler.onImageShadeClick(view);
            }
        });

        root.findViewById(R.id.view_triple_image2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickHandler.onImageShadeClick(view);
            }
        });

        root.findViewById(R.id.view_triple_image3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickHandler.onImageShadeClick(view);
            }
        });

        return root;
    }

    public void setDividerColor(int dividerColor) {
        RelativeLayout relativeLayout = root.findViewById(R.id.triple_shade_parent);
        relativeLayout.setBackgroundColor(dividerColor);
    }

    public int getFirstImageBgColor() {
        return firstImageBgColor;
    }

    public void setFirstImageBgColor(int firstImageBgColor) {
        this.firstImageBgColor = firstImageBgColor;
        RelativeLayout relativeLayout = root.findViewWithTag(TRIPLE_IMG1_CONTAINER);
        relativeLayout.setBackgroundColor(firstImageBgColor);
    }

    public void setFirstCommentBgColor(int firstCommentBgColor) {
        TextView textView = root.findViewById(R.id.view_triple_text1);
        textView.setBackgroundColor(firstCommentBgColor);
    }

    public String getFirstComment() {
        return firstComment;
    }

    public void setFirstComment(String firstComment) {
        this.firstComment = firstComment;
		TextView textView = root.findViewById(R.id.view_triple_text1);
        textView.setText(firstComment);
    }

    public void setFirstCommentVisibility(boolean firstCommentVisibility) {
        TextView textView = root.findViewById(R.id.view_triple_text1);
        textView.setVisibility(firstCommentVisibility ? View.VISIBLE : View.GONE);
    }

    public void setFirstImageScaleType(ImageView.ScaleType firstImageScaleType) {
        ImageView imageView = root.findViewById(R.id.view_triple_image1);
        imageView.setScaleType(firstImageScaleType);
    }

    public int getSecondImageBgColor() {
        return secondImageBgColor;
    }

    public void setSecondImageBgColor(int secondImageBgColor) {
        this.secondImageBgColor = secondImageBgColor;
		RelativeLayout relativeLayout = root.findViewWithTag(TRIPLE_IMG2_CONTAINER);
        relativeLayout.setBackgroundColor(secondImageBgColor);
    }

    public void setSecondCommentBgColor(int secondCommentBgColor) {
        TextView textView = root.findViewById(R.id.view_triple_text2);
        textView.setBackgroundColor(secondCommentBgColor);
    }

    public String getSecondComment() {
        return secondComment;
    }

    public void setSecondComment(String secondComment) {
        this.secondComment = secondComment;
        TextView textView = root.findViewById(R.id.view_triple_text2);
        textView.setText(secondComment);
    }

    public void setSecondCommentVisibility(boolean secondCommentVisibility) {
        TextView textView = root.findViewById(R.id.view_triple_text2);
        textView.setVisibility(secondCommentVisibility ? View.VISIBLE : View.GONE);
    }

    public void setSecondImageScaleType(ImageView.ScaleType secondImageScaleType) {
        ImageView imageView = root.findViewById(R.id.view_triple_image2);
        imageView.setScaleType(secondImageScaleType);
    }

    public int getThirdImageBgColor() {
        return thirdImageBgColor;
    }

    public void setThirdImageBgColor(int thirdImageBgColor) {
        this.thirdImageBgColor = thirdImageBgColor;
        RelativeLayout relativeLayout = root.findViewWithTag(TRIPLE_IMG3_CONTAINER);
        relativeLayout.setBackgroundColor(thirdImageBgColor);
    }

    public void setThirdCommentBgColor(int thirdCommentBgColor) {
        TextView textView = root.findViewById(R.id.view_triple_text3);
        textView.setBackgroundColor(thirdCommentBgColor);
    }

    public String getThirdComment() {
        return thirdComment;
    }

    public void setThirdComment(String thirdComment) {
        this.thirdComment = thirdComment;
        TextView textView = root.findViewById(R.id.view_triple_text3);
        textView.setText(thirdComment);
    }

    public void setThirdCommentVisibility(boolean thirdCommentVisibility) {
        TextView textView = root.findViewById(R.id.view_triple_text3);
        textView.setVisibility(thirdCommentVisibility ? View.VISIBLE : View.GONE);
    }

    public void setThirdImageScaleType(ImageView.ScaleType thirdImageScaleType) {
        ImageView imageView = root.findViewById(R.id.view_triple_image3);
        imageView.setScaleType(thirdImageScaleType);
    }

    public void setCounterText(String counterText) {
        TextView textView = root.findViewById(R.id.image_triple_counter);
        textView.setText(counterText);
    }

    public void setCounterVisibility(boolean counterVisibility) {
        LinearLayout linearLayout = root.findViewById(R.id.image_triple_counter_container);
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

}
