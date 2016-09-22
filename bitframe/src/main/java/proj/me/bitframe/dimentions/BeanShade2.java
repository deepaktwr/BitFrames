package proj.me.bitframe.dimentions;

import java.util.List;

/**
 * Created by root on 23/3/16.
 */
public class BeanShade2 extends BeanShade1{
    private List<ImageOrder> imageOrderList;
    private LayoutType layoutType;
    private int width2;
    private int height2;

    private boolean addInLayout;

    public boolean isAddInLayout() {
        return addInLayout;
    }

    public void setAddInLayout(boolean addInLayout) {
        this.addInLayout = addInLayout;
    }

    public LayoutType getLayoutType() {
        return layoutType;
    }

    public void setLayoutType(LayoutType layoutType) {
        this.layoutType = layoutType;
    }

    public List<ImageOrder> getImageOrderList() {
        return imageOrderList;
    }

    public void setImageOrderList(List<ImageOrder> imageOrderList) {
        this.imageOrderList = imageOrderList;
    }

    public int getWidth2() {
        return width2;
    }

    public void setWidth2(int width2) {
        this.width2 = width2;
    }

    public int getHeight2() {
        return height2;
    }

    public void setHeight2(int height2) {
        this.height2 = height2;
    }
}
