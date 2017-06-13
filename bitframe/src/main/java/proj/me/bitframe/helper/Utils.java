package proj.me.bitframe.helper;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import proj.me.bitframe.R;
import proj.me.bitframe.exceptions.FrameException;

/**
 * Created by deepak on 24/5/15.
 */
public class Utils {
    /**
     * Convert Dp to Pixel
     * dp-pixel
     */
    public static int dpToPx(float dp, Resources resources){
        float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, resources.getDisplayMetrics());
        return (int) px;
    }
    public static int spToPx(float sp, Resources resources){
        float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, resources.getDisplayMetrics());
        return (int) px;
    }
    private static final String TAG = "Bit_Frame";
    public static void logMessage(String message){
        Log.i(TAG, message);
    }
    public static void logVerbose(String message){
        Log.v(TAG, message);
    }
    public static void logError(String message){
        Log.e(TAG, message);
    }

    public static void showToast(Context context,String message){
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
    public static String formatMessage(String message, Object ... values){
        return String.format(message, values);
    }

    public static ColorPallet getColorPallet(){
        return ColorPallet.VIBRANT;
    }
    /**
     * for max 6 colors it'll do mixing at a time
     * it's simply taking average of the colors
     * */
    private static int getMixedColor(int ... colors){
        //for 1
        //return same color
        //for 2
        //add colors then right shift them with two to find average
        //for 3
        //add all then right shift by 1 then multiply by 2/3 by shift/add 101010...
        //for 4 add them then right shift 2
        //for 5 add them then divide by 10 then multiply by 2
        //for 6 add them all then divide by 3 then divide by 2
        int finalColor = 0;

        switch(colors.length){
            case 1:
                return colors[0];
            case 2:
                return (colors[0] + colors[1]) >> 1;
            case 3:
                int number = colors[0] + colors[1] + colors[2];
                int resultInt = number >> 1;
                int v = number;int n = 1;

                //calculating leading zeros
                if(v >>> 16 == 0){ n+=16; v <<= 16;}
                if(v >>> 24 == 0){ n+=8; v <<= 8; }
                if(v >>> 28 == 0){ n+=4; v <<= 4; }
                if(v >>> 30 == 0){ n+=2; v <<= 2; }
                n-=v >>> 31;

                int bitLength = 32 - n;
                finalColor = 0;
                int i = 1;
                while(bitLength > 0){
                    //shifting to left as 1, 3, 5.....
                    finalColor += resultInt << i;
                    bitLength--;
                    i+=2;
                }

                bitLength = 32 - n;
                int bits = bitLength * 2;
                int val = 1 << (bits - 1);

                if((val & finalColor) != 0){ finalColor = finalColor >> bits;  return finalColor + 1;}
                else return finalColor >> bits;
            case 4:
                return (colors[0] + colors[1] + colors[2] + colors[3]) >> 2;
            case 5:
                finalColor = colors[0] + colors[1] + colors[2] + colors[3] + colors[4];
                int q = (finalColor >> 1) + (finalColor >> 2);
                q = q + (q >> 4);
                q = q + (q >> 8);
                q = q + (q >> 16);
                q = q >> 3;
                int r = finalColor - (((q << 2) + q) << 1);
                return (q + ((r > 9) ? 1 : 0)) << 1;
            case 6:
                finalColor = getMixedColor(colors[0]+colors[1]+colors[2]+colors[3], colors[4], colors[5]);
                return finalColor >> 1;
        }
        return finalColor;
    }

    public static int getMixedArgbColor(int ... colors) throws FrameException{
        if(colors == null) throw new IllegalArgumentException("can not mix empty colors");
        if(colors.length > 6) throw new IllegalArgumentException("only support maximum six color mixing, found : "+colors.length);
        int mixedAlpha = 0;
        int mixedRed = 0;
        int mixedGreen = 0;
        int mixedBlue = 0;
        switch(colors.length){
            case 1:
                mixedAlpha = getMixedColor(colors[0] >>> 24);
                mixedRed = getMixedColor(colors[0] >> 16 & 0xFF);
                mixedGreen = getMixedColor(colors[0] >> 8 & 0xFF);
                mixedBlue = getMixedColor(colors[0] & 0xFF);
                break;
            case 2:
                mixedAlpha = getMixedColor(colors[0] >>> 24, colors[1] >>> 24);
                mixedRed = getMixedColor(colors[0] >> 16 & 0xFF, colors[1] >> 16 & 0xFF);
                mixedGreen = getMixedColor(colors[0] >> 8 & 0xFF, colors[1] >> 8 & 0xFF);
                mixedBlue = getMixedColor(colors[0] & 0xFF, colors[1] & 0xFF);
                break;
            case 3:
                mixedAlpha = getMixedColor(colors[0] >>> 24, colors[1] >>> 24, colors[2] >>> 24);
                mixedRed = getMixedColor(colors[0] >> 16 & 0xFF, colors[1] >> 16 & 0xFF, colors[2] >> 16 & 0xFF);
                mixedGreen = getMixedColor(colors[0] >> 8 & 0xFF, colors[1] >> 8 & 0xFF, colors[2] >> 8 & 0xFF);
                mixedBlue = getMixedColor(colors[0] & 0xFF, colors[1] & 0xFF, colors[2] & 0xFF);
                break;
            case 4:
                mixedAlpha = getMixedColor(colors[0] >>> 24, colors[1] >>> 24, colors[2] >>> 24,
                        colors[3] >>> 24);
                mixedRed = getMixedColor(colors[0] >> 16 & 0xFF, colors[1] >> 16 & 0xFF, colors[2] >> 16 & 0xFF,
                        colors[3] >> 16 & 0xFF);
                mixedGreen = getMixedColor(colors[0] >> 8 & 0xFF, colors[1] >> 8 & 0xFF, colors[2] >> 8 & 0xFF,
                        colors[3] >> 8 & 0xFF);
                mixedBlue = getMixedColor(colors[0] & 0xFF, colors[1] & 0xFF, colors[2] & 0xFF,
                        colors[3] & 0xFF);
                break;
            case 5:
                mixedAlpha = getMixedColor(colors[0] >>> 24, colors[1] >>> 24, colors[2] >>> 24,
                        colors[3] >>> 24, colors[4] >>> 24);
                mixedRed = getMixedColor(colors[0] >> 16 & 0xFF, colors[1] >> 16 & 0xFF, colors[2] >> 16 & 0xFF,
                        colors[3] >> 16 & 0xFF, colors[4] >> 16 & 0xFF);
                mixedGreen = getMixedColor(colors[0] >> 8 & 0xFF, colors[1] >> 8 & 0xFF, colors[2] >> 8 & 0xFF,
                        colors[3] >> 8 & 0xFF, colors[4] >> 8 & 0xFF);
                mixedBlue = getMixedColor(colors[0] & 0xFF, colors[1] & 0xFF, colors[2] & 0xFF,
                        colors[3] & 0xFF, colors[4] & 0xFF);
                break;
            case 6:
                mixedAlpha = getMixedColor(colors[0] >>> 24, colors[1] >>> 24, colors[2] >>> 24,
                        colors[3] >>> 24, colors[4] >>> 24, colors[5] >>> 24);
                mixedRed = getMixedColor(colors[0] >> 16 & 0xFF, colors[1] >> 16 & 0xFF, colors[2] >> 16 & 0xFF,
                        colors[3] >> 16 & 0xFF, colors[4] >> 16 & 0xFF, colors[5] >> 16 & 0xFF);
                mixedGreen = getMixedColor(colors[0] >> 8 & 0xFF, colors[1] >> 8 & 0xFF, colors[2] >> 8 & 0xFF,
                        colors[3] >> 8 & 0xFF, colors[4] >> 8 & 0xFF, colors[5] >> 8 & 0xFF);
                mixedBlue = getMixedColor(colors[0] & 0xFF, colors[1] & 0xFF, colors[2] & 0xFF,
                        colors[3] & 0xFF, colors[4] & 0xFF, colors[5] & 0xFF);
                break;
        }
        return Color.argb(mixedAlpha, mixedRed, mixedGreen, mixedBlue);
    }
    public static int getInverseColor(int color){
        float hsv[] = new float[3];
        Color.colorToHSV(color, hsv);
        //inverting color
        float hue = hsv[2];
        hue = (hue + 180) % 360;
        hsv[2] = hue;
        return Color.HSVToColor(hsv);
    }

    public static int getColorWithTransparency(int color, int transparencyPercent){
        int alpha = 235 * transparencyPercent / 100;
        return Color.argb(alpha, color >> 16 & 0xFF, color >> 8 & 0xFF, color & 0xFF);
    }

   /* public static final int COMMENT_TRANSPARENCY_PERCENT = 70;

    public static float MIN_WIDTH = 300;
    public static float MIN_HIGHT = 300;
    public static float MAX_WIDTH = 700;
    public static float MAX_HEIGHT = 900;

    //show be less than or equal to 4 for now
    public static int MAX_IMAGES_TO_SHOW = 4;

    *//*public static final float MIN_RATIO = 0.3f;
    public static final float MAX_RATIO = 0.7f;*//*

    public static final float MIN_ADD_RATIO = 0.25f;

    //for add in layout
    public static boolean shouldShowAddInLayout(){
        return false;
    }
    //for comments
    public static boolean shouldShowComments(){
        return false;
    }

    public static boolean showShowDivider(){
        return true;
    }

    public static ColorCombination getColorCombo(){
        return ColorCombination.VIBRANT_TO_MUTED;
    }

    //for image
    public static int getErrorDrawableId(){
        return 0;
    }
    public static ImageView.ScaleType getImageScaleType(){
        return ImageView.ScaleType.CENTER_CROP;
    }*/

    public static BeanBitmapResult getScaledResult(Bitmap bitmap, int reqWidth, int reqHeight) throws FrameException{
        if(bitmap == null || reqWidth == 0 || reqHeight == 0) throw new IllegalArgumentException("scaling can not perform on invalid arguments");
        BeanBitmapResult beanBitmapResult = new BeanBitmapResult();
        beanBitmapResult.setHeight(bitmap.getHeight());
        beanBitmapResult.setWidth(bitmap.getWidth());

        int inSampleSize = 1;

        if(beanBitmapResult.getHeight() > reqHeight || beanBitmapResult.getWidth() > reqWidth){

            final int heightRatio = Math.round((float) beanBitmapResult.getHeight() / (float) reqHeight);
            final int widthRatio = Math.round((float) beanBitmapResult.getWidth() / (float) reqWidth);

            inSampleSize = (heightRatio <= widthRatio && beanBitmapResult.getHeight() > reqHeight) ? heightRatio :
                    (widthRatio <= heightRatio && beanBitmapResult.getWidth() > reqWidth) ? widthRatio : 1;
        }

        if(inSampleSize == 0) inSampleSize = 1;

        Bitmap btmp = Bitmap.createScaledBitmap(bitmap, beanBitmapResult.getWidth()/inSampleSize,
                beanBitmapResult.getHeight()/inSampleSize, false);

        beanBitmapResult.setBitmap(btmp);

        Utils.logMessage("width : " + reqWidth+" height : "+reqHeight);
        Utils.logMessage("width : " + beanBitmapResult.getWidth()+" height : "+beanBitmapResult.getHeight());
        Utils.logMessage("width : " + beanBitmapResult.getWidth()/inSampleSize+" height : "+beanBitmapResult.getHeight()/inSampleSize);

        return beanBitmapResult;
    }

    public static Bitmap getScaledBitmap(Bitmap bitmap, int reqWidth, int reqHeight) throws FrameException{
        if(bitmap == null || reqWidth == 0 || reqHeight == 0) throw new IllegalArgumentException("scaling can not perform on invalid arguments");
        int inSampleSize = 1;

        final int height = bitmap.getHeight();
        final int width = bitmap.getWidth();
        if(height > reqHeight || width > reqWidth){

            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);

            inSampleSize = (heightRatio <= widthRatio && height > reqHeight) ? heightRatio :
                    (widthRatio <= heightRatio && width > reqWidth) ? widthRatio : 1;
        }

        if(inSampleSize == 0) inSampleSize = 1;

        Bitmap btmp = Bitmap.createScaledBitmap(bitmap, width/inSampleSize, height/inSampleSize, false);
        if(btmp != bitmap) bitmap.recycle();

        Utils.logMessage("width : " + reqWidth+" height : "+reqHeight);
        Utils.logMessage("width : " + width+" height : "+height);
        Utils.logMessage("width : " + width/inSampleSize+" height : "+height/inSampleSize);

        return btmp;
    }


    public static BeanResult decodeBitmapFromPath(int reqWidth, int reqHeight, String imagePath, Context context) throws FrameException{
        if(reqWidth == 0 || reqHeight == 0 || TextUtils.isEmpty(imagePath) || context == null)
            throw new IllegalArgumentException("Failed to decode bitmap from path, invalid arguments");
        boolean hasExtension = !TextUtils.isEmpty(MimeTypeMap.getFileExtensionFromUrl(imagePath));
        if(hasExtension && imagePath.contains(".")) imagePath=imagePath.substring(0,imagePath.lastIndexOf('.'));
        Uri fileUri = Uri.parse(imagePath);

        BitmapFactory.Options options=new BitmapFactory.Options();
        options.inJustDecodeBounds=true;
        InputStream inputStream = null;
        try {
            inputStream = context.getContentResolver().openInputStream(fileUri);
            BitmapFactory.decodeStream(inputStream, null, options);
            inputStream.close();
            BeanResult beanResult = calculateInSmapleSize(options, reqWidth, reqHeight);
            options.inSampleSize = beanResult.getInSampleSize();

            options.inJustDecodeBounds = false;
            inputStream = context.getContentResolver().openInputStream(fileUri);
            beanResult.setBitmap(BitmapFactory.decodeStream(inputStream, null, options));
            inputStream.close();
            Utils.logMessage("width : " + reqWidth+" height : "+reqHeight);
            Utils.logMessage("width : " + beanResult.getWidth()+" height : "+beanResult.getHeight());
            Utils.logMessage("width : " + options.outWidth+" height : "+options.outHeight);
            return beanResult;
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            if(inputStream != null) {
                try {
                    inputStream.close();
                    throw new FrameException("Could not found file on path : "+imagePath, e);
                } catch (IOException e1) {
                    e1.printStackTrace();
                    throw new FrameException("Could not close stream", e1);
                }
            }
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            if(inputStream != null) {
                try {
                    inputStream.close();
                    throw new FrameException("could not decode file : "+imagePath, e);
                } catch (IOException e1) {
                    e1.printStackTrace();
                    throw new FrameException("could not close stream", e1);

                }
            }
            return null;
        }
    }

    private static BeanResult calculateInSmapleSize(BitmapFactory.Options options, int reqWidth, int reqHeight){
        BeanResult beanResult = new BeanResult();

        final int width = options.outWidth;
        final int height = options.outHeight;

        int inSampleSize = 1;

        beanResult.setHeight(height);
        beanResult.setWidth(width);

        if(height > reqHeight || width > reqWidth){
            /*final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            while((halfHeight / inSampleSize > reqHeight) && (halfWidth / inSampleSize) > reqWidth){
                inSampleSize *= 2;
            }*/
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);

            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;

            beanResult.setInSampleSize(inSampleSize);
        }
        return beanResult;
    }

    public static void unbindDrawables(View view, boolean unbindItself, boolean shouldRecycleBitmaps){
        if(view == null) return;
        if(unbindItself && view.getBackground() != null) view.getBackground().setCallback(null);
        if(view instanceof ImageView && shouldRecycleBitmaps) {
            ImageView imageView = ((ImageView)view);
            if(imageView.getBackground() != null) imageView.getBackground().setCallback(null);
            Drawable drawable = imageView.getDrawable();
            if(drawable instanceof BitmapDrawable) {
                Bitmap bitmap = ((BitmapDrawable)drawable).getBitmap();
                if(bitmap != null && !bitmap.isRecycled()) bitmap.recycle();
                bitmap = null;
            }else if(drawable instanceof LayerDrawable){
                Bitmap bitmap = ((BitmapDrawable)((LayerDrawable)drawable).getDrawable(0)).getBitmap();
                if(bitmap != null && !bitmap.isRecycled()) bitmap.recycle();
                bitmap = null;
            }
            imageView.setImageBitmap(null);
            imageView.setImageDrawable(null);
            imageView.setImageResource(0);
        }

        if(view instanceof ViewGroup){
            for(int i = 0;i<((ViewGroup)view).getChildCount();i++) unbindDrawables(((ViewGroup)view).getChildAt(i), true, shouldRecycleBitmaps);
            try{
                ((ViewGroup)view).removeAllViews();
            }catch(UnsupportedOperationException e){}
            if(unbindItself) view.setBackgroundResource(0);
        }
    }

    public static boolean hasNetwork(Context context){
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }

    public static boolean isLocalPath(String imagePath){
        if(TextUtils.isEmpty(imagePath)) return false;
        Uri uri = Uri.parse(imagePath);
        return (uri != null && uri.getScheme() != null) && (uri.getScheme().equals("content") || uri.getScheme().equals("file"));
    }

    public static int getVersionColor(Context context, int colorId){
        if(Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1)
            return context.getResources().getColor(colorId, context.getTheme());
        else return context.getResources().getColor(colorId);
    }

    public static Drawable getVersionDrawable(Context context, int drawableId){
        if(Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1)
            return context.getResources().getDrawable(drawableId, context.getTheme());
        else return context.getResources().getDrawable(drawableId);
    }

}