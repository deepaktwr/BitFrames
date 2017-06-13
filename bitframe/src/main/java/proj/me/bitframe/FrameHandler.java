package proj.me.bitframe;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.v7.graphics.Palette;

import java.lang.ref.WeakReference;
import java.util.List;

import proj.me.bitframe.exceptions.FrameException;
import proj.me.bitframe.helper.Utils;

class FrameHandler extends Handler {
    WeakReference<ImageResult> imageResultWeakReference;
    String lastReference;
    int position;

    FrameHandler(ImageResult imageResult, int position) {
        imageResultWeakReference = new WeakReference<>(imageResult);
        this.position = position;
    }

    @Override
    public void handleMessage(Message msg) {
        final ImageResult imageResult = imageResultWeakReference.get();
        if (imageResult == null) {
            Utils.logError("handling null : imageresult : "+lastReference+"  handler : "+this.toString()+"  "+position);
            super.handleMessage(msg);
            return;
        }

        lastReference = imageResult.toString();
        Utils.logError("process message : imageesult : "+lastReference+"  handler : "+this.toString()+"  "+position);

        switch (msg.what) {
            case 1:
                final BeanHandler beanHandler = (BeanHandler) msg.obj;
                final Bitmap bitmap = beanHandler.getBitmap();
                if (imageResult.getCounter() >= imageResult.getFrameModel().getMaxFrameCount()) {
                    imageResult.updateCounter();
                    final BeanBitFrame beanBitFrame = new BeanBitFrame();
                    beanBitFrame.setWidth(bitmap.getWidth());
                    beanBitFrame.setHeight(bitmap.getHeight());
                    beanBitFrame.setLoaded(true);
                    Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
                        @Override
                        public void onGenerated(Palette palette) {
                            int defaultColor = Color.parseColor("#ffffffff");
                            Palette.Swatch vibrantSwatch = palette.getVibrantSwatch();
                            Palette.Swatch mutedSwatch = palette.getMutedSwatch();
                            int vibrantPopulation = vibrantSwatch == null ? 0 : vibrantSwatch.getPopulation();
                            int mutedPopulation = mutedSwatch == null ? 0 : mutedSwatch.getPopulation();

                            int vibrantColor = palette.getVibrantColor(defaultColor);
                            int mutedColor = palette.getMutedColor(defaultColor);

                            beanBitFrame.setHasGreaterVibrantPopulation(vibrantPopulation > mutedPopulation);
                            beanBitFrame.setMutedColor(mutedColor);
                            beanBitFrame.setVibrantColor(vibrantColor);

                            BeanImage beanImage = beanHandler.getBeanImage();

                            beanBitFrame.setImageComment(beanImage.getImageComment());
                            beanBitFrame.setImageLink(beanImage.getImageLink());
                            beanBitFrame.setPrimaryCount(beanImage.getPrimaryCount());
                            beanBitFrame.setSecondaryCount(beanImage.getSecondaryCount());

                            imageResult.getImageCallback().frameResult(beanBitFrame);
                            Utils.logVerbose("exppp pallet width " + bitmap.getWidth() + " height " + bitmap.getHeight());
                            if (!bitmap.isRecycled()) bitmap.recycle();
                        }
                    });
                } else {
                    boolean doneLoading = imageResult.getCounter() == (imageResult.getFrameModel().getMaxFrameCount()
                            >= imageResult.getTotalImages() ? imageResult.getTotalImages()
                            : imageResult.getFrameModel().getMaxFrameCount()) - 1;
                    imageResult.setDoneLoading(doneLoading);
                    try {
                        imageResult.onImageLoaded(true, bitmap, beanHandler.getBeanImage());
                    } catch (FrameException e) {
                        e.printStackTrace();
                    }
                    imageResult.updateCounter();
                }
                break;
            case 2:
                List<Bitmap> bitmaps = imageResult.getImages();
                for (Bitmap bitmap1 : bitmaps) {
                    Utils.logVerbose("exppp normal width " + bitmap1.getWidth() + " height " + bitmap1.getHeight());
                    if (!bitmap1.isRecycled()) bitmap1.recycle();
                    bitmap1 = null;
                }
                bitmaps.clear();
                break;
            case 3:
                Bitmap bit = (Bitmap) msg.obj;
                if (!bit.isRecycled()) bit.recycle();
                bit = null;
                break;
        }
    }
}