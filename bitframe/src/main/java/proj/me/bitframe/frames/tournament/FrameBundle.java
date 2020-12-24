package proj.me.bitframe.frames.tournament;

import java.util.ArrayList;
import java.util.List;

import proj.me.bitframe.frames.AvailableTypes;

public class FrameBundle {
    private boolean isHorizontal;
    private List<FramedPictures> framedPictures;

    private int totalWidth;
    private int totalHeight;

    private int rowCovered;
    private int colCovered;

    private int totalMinWidth;
    private int totalMinHeight;

    private FrameBundle nextBundle;

    public FrameBundle(boolean isHorizontal) {
        this.isHorizontal = isHorizontal;
        framedPictures = new ArrayList<>(AvailableTypes.MAX_ROW_OR_COLUMN);
    }

    public void addFramePicture(FramedPictures framedPictures) {
        this.framedPictures.add(framedPictures);
    }

    //build fixed frame
    //build custom frame

    public int[] assignCustomDimensions(int minHorzBlock, int minVertBlock, int[] divisions, FrameBundle nextBundle){
        int blocksCovered = isHorizontal ? divisions[1] : divisions[0];

        for(int i = framedPictures.size(); i >= 0; i--){
            FramedPictures framedPicture = framedPictures.get(i);
            blocksCovered = framedPicture.assignCustomDimensionAndGetBlocks(blocksCovered, minHorzBlock,
                    minVertBlock, i == framedPictures.size() - 1 ? null : framedPictures.get(i + 1));

            totalWidth += framedPicture.getTotalWidth();
            totalHeight += framedPicture.getTotalHeight();
        }

        if(isHorizontal){
            totalWidth = Utility.upperBound(totalWidth, framedPictures.size());
            FramedPictures framedPicture = framedPictures.get(0);
            totalWidth = framedPicture.changeNextIndividualDimension(totalWidth, null);

            colCovered = blocksCovered;
            rowCovered = framedPictures.size();
        } else{
            totalHeight = Utility.upperBound(totalHeight, framedPictures.size());
            FramedPictures framedPicture = framedPictures.get(0);
            totalHeight = framedPicture.changeNextIndividualDimension(totalHeight, null);

            rowCovered = blocksCovered;
            colCovered = framedPictures.size();
        }

        for(FramedPictures framedPicture : framedPictures) {
            if(isHorizontal){
                totalHeight += framedPicture.getTotalHeight();
                totalWidth = framedPicture.getTotalWidth();
            }else{
                totalWidth += framedPicture.getTotalWidth();
                totalHeight = framedPicture.getTotalHeight();
            }
        }

        totalMinWidth = colCovered * minHorzBlock;
        totalMinHeight = rowCovered * minVertBlock;
        this.nextBundle = nextBundle;

        return isHorizontal ? new int[]{framedPictures.size() + divisions[0], blocksCovered}
                : new int[]{blocksCovered, framedPictures.size() + divisions[1]};
    }

    public void changeBlockDimensions(int MAX_WIDTH, int MAX_HEIGHT){
        if(nextBundle == null) return;

        if(isHorizontal){
            int nextTotalWidth = nextBundle.getTotalWidth();
            int nextTotalMinWidth = nextBundle.getTotalMinWidth();

            int average = Utility.upperBound(totalWidth + nextTotalWidth, 2);
            if (average < nextTotalMinWidth) average = nextTotalMinWidth;
            else if(average > MAX_WIDTH) average = MAX_WIDTH;

            FramedPictures framedPicture = framedPictures.get(0);
            average = framedPicture.changeNextIndividualDimension(average, null);

            totalWidth = average;

            float amount = (average - nextTotalWidth) / (float) nextTotalWidth;
            int diff = nextBundle.changeWidthAmountAndGetDifference(amount);
            if(diff != 0) average = framedPicture.changeNextIndividualDimension(average + diff, null);
            totalWidth = average;

            average = Utility.upperBound(nextBundle.getTotalHeight(), nextBundle.getNextBundleCount());
            int sum = totalHeight + average;
            if(sum > MAX_HEIGHT){
                amount = (sum - MAX_HEIGHT) / (float)MAX_HEIGHT;
                int height = totalHeight - (int)Math.floor(totalHeight * amount);
                if(height > totalMinHeight) framedPicture.changeCollectiveDimension(height);
            }

            nextBundle.changeBlockDimensions(totalWidth, MAX_HEIGHT - totalHeight);
        }else {
            int nextTotalHeight = nextBundle.getTotalHeight();
            int nextTotalMinHeight = nextBundle.getTotalMinHeight();

            int average = Utility.upperBound(totalHeight + nextTotalHeight, 2);
            if (average < nextTotalMinHeight) average = nextTotalMinHeight;
            if(average > MAX_HEIGHT) average = MAX_HEIGHT;

            FramedPictures framedPicture = framedPictures.get(0);
            average = framedPicture.changeNextIndividualDimension(average, null);

            totalHeight = average;

            float amount = (average - nextTotalHeight) / (float) nextTotalHeight;
            int diff = nextBundle.changeHeightAmountAndGetDifference(amount);
            if(diff != 0) average = framedPicture.changeNextIndividualDimension(average + diff, null);
            totalHeight = average;

            average = Utility.upperBound(nextBundle.getTotalWidth(), nextBundle.getNextBundleCount());
            int sum = totalWidth + average;
            if(sum > MAX_WIDTH){
                amount = (sum - MAX_WIDTH) / (float)MAX_WIDTH;
                int width = totalWidth - (int)Math.floor(totalWidth * amount);
                if(width > totalMinWidth) framedPicture.changeCollectiveDimension(width);
            }

            nextBundle.changeBlockDimensions(MAX_WIDTH - totalWidth, totalHeight);
        }
    }

    private int changeWidthAmountAndGetDifference(float amount){
        if(amount == 0) return 0;
        int nextHeight = 0;
        if(nextBundle != null) nextHeight = nextBundle.changeWidthAmountAndGetDifference(amount);

        int diff = 0;
        if(isHorizontal){
            int totalW = amount < 0 ? totalWidth - (int)Math.floor(totalWidth * amount * -1)
                    : totalWidth + (int)Math.ceil(totalWidth * amount);
            FramedPictures framedPicture = framedPictures.get(0);
            totalWidth = framedPicture.changeNextIndividualDimension(totalW, null);
            diff = totalWidth - totalW;

        }else{
            //collective change in width
            int totalW = amount < 0 ? totalWidth - (int)Math.floor(totalWidth * amount * -1)
                    : totalWidth + (int)Math.ceil(totalWidth * amount);
            int total = 0;
            for(FramedPictures framedPicture : framedPictures){
                float width = framedPicture.getTotalWidth();
                width += width * amount;
                if(width < framedPicture.getMinHorzBlock()) width = framedPicture.getMinHorzBlock();
                framedPicture.changeCollectiveDimension((int)width);
                total += width;
            }

            totalWidth = total;

            if(total > totalW) diff = total - totalW;
            else framedPictures.get(0).changeCollectiveDimension(framedPictures.get(0).getTotalHeight() + totalW - total);
        }

        return diff + nextHeight;
    }

    private int changeHeightAmountAndGetDifference(float amount){
        if(amount == 0) return 0;
        int nextHeight = 0;
        if(nextBundle != null) nextHeight = nextBundle.changeHeightAmountAndGetDifference(amount);

        int diff = 0;
        if(isHorizontal){
            //collective change in height
            int totalH = amount < 0 ? totalHeight - (int)Math.floor(totalHeight * amount * -1)
                    : totalHeight + (int)Math.ceil(totalHeight * amount);
            int total = 0;
            for(FramedPictures framedPicture : framedPictures){
                float height = framedPicture.getTotalHeight();
                height += height * amount;
                if(height < framedPicture.getMinVertBlock()) height = framedPicture.getMinVertBlock();
                framedPicture.changeCollectiveDimension((int)height);
                total += height;
            }

            totalHeight = total;

            if(total > totalH) diff = total - totalH;
            else framedPictures.get(0).changeCollectiveDimension(framedPictures.get(0).getTotalHeight() + totalH - total);

        }else{
            int totalH = amount < 0 ? totalHeight - (int)Math.floor(totalHeight * amount * -1)
                    : totalHeight + (int)Math.ceil(totalHeight * amount);
            FramedPictures framedPicture = framedPictures.get(0);
            totalHeight = framedPicture.changeNextIndividualDimension(totalH, null);
            diff = totalHeight - totalH;
        }

        return diff + nextHeight;
    }

    public int getTotalWidth(){
        return totalWidth + (nextBundle != null ? nextBundle.getTotalWidth() : 0);
    }
    public int getTotalHeight(){
        return totalHeight + (nextBundle != null ? nextBundle.getTotalHeight() : 0);
    }
    public int getTotalMinWidth(){
        return totalMinWidth + (nextBundle != null ? nextBundle.getTotalMinWidth() : 0);
    }
    public int getTotalMinHeight(){
        return totalMinHeight + (nextBundle != null ? nextBundle.getTotalMinHeight() : 0);
    }
    public int getNextBundleCount(){
        return 1 + (nextBundle != null ? nextBundle.getNextBundleCount() : 0);
    }
    public boolean isHorizontal() {
        return isHorizontal;
    }



    public int[] assignFixed1Dimensions(int fromRow, int fromCol, int[] divisions, List<FrameBundle> frameBundles, int nextIndex){
        //the total with and height would be fixed based on row, column and block value

        if(nextIndex < frameBundles.size()){
            divisions = frameBundles.get(nextIndex).assignFixed1Dimensions(isHorizontal ? fromRow + 1 : fromRow,
                    isHorizontal ? fromCol : fromCol + 1, divisions, frameBundles, nextIndex + 1);
        }

        int rowCovered = divisions[0];
        int colCovered = divisions[1];

        int blocksCovered = framedPictures.get(0).assignFixed1DimensionAndGetBlocks(isHorizontal ? colCovered : rowCovered,
                fromRow, fromCol, framedPictures, 1);

        if(isHorizontal) return new int[]{rowCovered + framedPictures.size(), blocksCovered};
        else return new int[]{blocksCovered, colCovered + framedPictures.size()};
    }

    public int[] assignFixed2Dimensions(int rowBlockSize, int colBlockSize, int[] divisions){

        int rowCovered = divisions[0];
        int colCovered = divisions[1];

        int blocksCovered = framedPictures.get(0).assignFixed2DimensionAndGetBlocks(rowBlockSize, colBlockSize,
                isHorizontal ? colCovered : rowCovered, framedPictures, 1);

        if(isHorizontal) return new int[]{rowCovered + framedPictures.size(), blocksCovered};
        else return new int[]{blocksCovered, colCovered + framedPictures.size()};

    }
}