package proj.me.bitframedemo;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import proj.me.bitframe.BeanImage;
import proj.me.bitframe.FrameCallback;
import proj.me.bitframe.helper.Utils;


/**
 * Created by Deepak.Tiwari on 30-10-2015.
 */
public class ImageIntent {
    private Activity activity;
    private CustomDialogFrag customDialogFrag;
    private static final int TAKE_PHOTO = 1;
    private static final int FROM_GALLERY = 2;
    public ImageIntent(Activity activity){
        this.activity = activity;
    }

    public void callIntentForImage() {
        customDialogFrag = new CustomDialogFrag(ImageIntent.this);
        customDialogFrag.show(activity.getFragmentManager(), "myImageDialog");
    }

    void takePhoto() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        Uri fileUri = null;
        try {
            fileUri = createImageFile();
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
            ((FrameCallback)activity).picIntentInitiated(fileUri.toString());
            activity.startActivityForResult(takePictureIntent, TAKE_PHOTO);
        } catch (IOException ex) {
            // Error occurred while creating the File
        }

    }

    void fetchFromGallery() {
        ((FrameCallback)activity).picIntentInitiated(null);
        Intent intent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        activity.startActivityForResult(intent, FROM_GALLERY);
    }

    Uri createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss")
                .format(new Date());

        ContentValues values = new ContentValues();
        String fileName = "IMG_" + timeStamp + ".jpg";
        values.put(MediaStore.Images.Media.TITLE, fileName);
        return activity.getContentResolver().insert(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
    }
    public String getImageResultPath(Intent data, String filePath) {
        if((data == null || data.getData() == null) && filePath == null) return null;
        //Constants.logMessage("comment on image -> "+comment);
        String imagePath = "";
            String extention = ".jpg";
            if (data != null && data.getData() != null) {
                Uri img = data.getData();
                String[] filePathClm = { MediaStore.Images.Media.DATA };
                Cursor c = activity.getContentResolver().query(img, filePathClm,
                        null, null, null);
                if(c != null) {
                    c.moveToFirst();

                    int clmIndex = c.getColumnIndex(filePathClm[0]);
                    //imagePath = c.getString(clmIndex);
                    c.close();
                /*if(!TextUtils.isEmpty(path))
                    extention = path.substring(path.lastIndexOf("."));*/
                }
                imagePath = img.toString();
                //imagePath = getPathFromURI(data.getData());
            }else
                imagePath = filePath;
        return imagePath;
    }

    /**
     * Method to get file path from Uri
     *
     * @param contentUri uri of selected file
     * @return
     */
    public String getPathFromURI(Uri contentUri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        CursorLoader cursorLoader = new CursorLoader(activity, contentUri, projection, null, null, null);
        Cursor cursor = cursorLoader.loadInBackground();
        if(cursor == null) return null;
        int column_index =
                cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }
}
