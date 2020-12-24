package proj.me.bitframe.frames.tournament;

import java.util.List;

/**
 * Created by root on 31/7/17.
 */
public class FrameBuilder {
    private int rowUsed;
    private int colUsed;
    private List<FrameBundle> frameBundles;
    public FrameBuilder(int rowUsed, int colUsed, List<FrameBundle> frameBundles){
        this.rowUsed = rowUsed;
        this.colUsed = colUsed;
        this.frameBundles = frameBundles;
    }

    public void buildCustomFrame(){
        int minHorzBlock = Utility.upperBound(Utility.upperBound(Utility.MID, colUsed), 2);
        int minVertBlock = Utility.upperBound(Utility.upperBound(Utility.MID, rowUsed), 2);

        int[] rowColCovered = new int[2];
        for(int i = frameBundles.size() - 1; i>=0 ; i--)
            rowColCovered = frameBundles.get(i).assignCustomDimensions(minHorzBlock, minVertBlock, rowColCovered,
                    i < frameBundles.size() - 1 ? frameBundles.get(i + 1) : null);

        frameBundles.get(0).changeBlockDimensions(Utility.Z_LENGTH, Utility.Z_LENGTH);
    }

    public void buildFixed1Frame(){
        frameBundles.get(0).assignFixed1Dimensions(0, 0, new int[]{0, 0}, frameBundles, 1);
    }

    public void buildFixed2Frame(){
        int rowBlockSize = Utility.upperBound(Utility.MID_5Q , colUsed);
        int colBlockSize = Utility.upperBound(Utility.MID_5Q , rowUsed);
        int[] rowColCovered = new int[2];
        for(int i = frameBundles.size() - 1; i>=0 ; i--)
            rowColCovered = frameBundles.get(i).assignFixed2Dimensions(rowBlockSize, colBlockSize, rowColCovered);
    }
}
