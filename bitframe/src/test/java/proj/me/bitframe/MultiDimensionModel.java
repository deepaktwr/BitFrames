package proj.me.bitframe;

import proj.me.bitframe.dimentions.ImageOrder;
import proj.me.bitframe.dimentions.LayoutType;

/**
 * Created by root on 29/9/16.
 */

public class MultiDimensionModel {
    public ImageOrder[] imageOrders;
    public LayoutType layoutType;
    public int[] params;
    public int widthPixels;
    public int heightPixels;
    public MultiDimensionModel(int[] params, ImageOrder[] imageOrders, LayoutType layoutType, int widthPixels, int heightPixels){
        this.layoutType = layoutType;
        this.imageOrders = imageOrders;
        this.params = params;
        this.widthPixels = widthPixels;
        this.heightPixels = heightPixels;
    }
}
