package proj.me.bitframe;

/**
 * Created by root on 29/9/16.
 */

public class DimensionsModel {

    public static Dimension[] DIMENSIONS = {
            new Dimension(1080, 1920),
            new Dimension(1440, 2560),
            new Dimension(1200, 1920),
            new Dimension(768, 1280),
            new Dimension(800, 1280),
            new Dimension(720, 1280),
            new Dimension(480, 800),
            new Dimension(240, 320),
            new Dimension(240, 400),
            new Dimension(240, 432),
            new Dimension(480, 854),
            new Dimension(320, 480),

            //inverse dimensions, for tablets
            new Dimension(2560, 1800),
            new Dimension(2048, 1536),
            new Dimension(2560, 1600),
            new Dimension(1280, 800),
            new Dimension(1280, 720),
            new Dimension(1024, 600)
    };


    public boolean vertLayout;
    public boolean firstImageLeftOrTop;
    public int[] params;
    public int widthPixels;
    public int heightPixels;
    public DimensionsModel(int[] params, boolean firstImageLeftOrTop, boolean vertLayout, int widthPixels, int heightPixels){
        this.vertLayout = vertLayout;
        this.firstImageLeftOrTop = firstImageLeftOrTop;
        this.params = params;
        this.widthPixels = widthPixels;
        this.heightPixels = heightPixels;
    }
}
