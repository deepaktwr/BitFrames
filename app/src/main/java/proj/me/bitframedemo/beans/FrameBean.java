package proj.me.bitframedemo.beans;

import java.util.List;

import proj.me.bitframe.BeanBitFrame;
import proj.me.bitframe.BeanImage;

/**
 * Created by root on 20/9/16.
 */

public class FrameBean {
    private String title;
    private String description;
    private List<BeanImage> beanBitFrameList;


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<BeanImage> getBeanBitFrameList() {
        return beanBitFrameList;
    }

    public void setBeanBitFrameList(List<BeanImage> beanBitFrameList) {
        this.beanBitFrameList = beanBitFrameList;
    }
}
