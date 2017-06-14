package proj.me.bitframe;

import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.support.v7.graphics.Palette;

import proj.me.bitframe.exceptions.FrameException;

class FrameHandler extends Handler {
    ImageResult imageResult;

    FrameHandler(ImageResult imageResult) {
        this.imageResult = imageResult;
    }

    @Override
    public void handleMessage(Message msg) {

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

                    Palette.from(bitmap).generate(new PaletteListener(imageResult, beanBitFrame, beanHandler.getBeanImage(), false));
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
            case 3:
                Bitmap bit = (Bitmap) msg.obj;
                if (!bit.isRecycled()) bit.recycle();
                bit = null;
                break;
        }
    }
}