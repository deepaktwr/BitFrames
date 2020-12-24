package proj.me.bitframe.frames.tournament;

import java.util.List;

import proj.me.bitframe.frames.AvailableTypes;

public class FramedPictures {
    private int type;
    private Picture[] pictures;
    private boolean isHorizontal;

    private FramedPictures nextPictures;
    private FramedPictures lastPictures;

    public FramedPictures(int type, Picture[] pictures) {
        this.type = type;
        this.pictures = pictures;
        switch(type){
            case AvailableTypes.HF:
            case AvailableTypes.HH:
            case AvailableTypes.HT:
            case AvailableTypes.HQ:
                isHorizontal = true;
                break;
        }
    }

    public int getType() {
        return type;
    }

    public Picture[] getPictures() {
        return pictures;
    }


    private int minVertBlock, minHorzBlock;
    private int totalWidth, totalHeight;

    public int getTotalWidth() {
        return totalWidth;
    }

    public int getTotalHeight() {
        return totalHeight;
    }

    public int getMinVertBlock() {
        return minVertBlock;
    }

    public int getMinHorzBlock() {
        return minHorzBlock;
    }

    //for fixed or custom
    //send nextPictures as null for fixed type dimensions
    public int assignCustomDimensionAndGetBlocks(int blocksCovered, int minHorzBlock, int minVertBlock,
                                                 FramedPictures nextPictures){
        this.nextPictures = nextPictures;
        if(isHorizontal) return calculateHorizontalTypeDimensions(blocksCovered, minHorzBlock, minVertBlock);
        else return calculateVerticalTypeDimensions(blocksCovered, minHorzBlock, minVertBlock);
    }

    //for custom

    private int calculateHorizontalTypeDimensions(int colCovered, int minHorzBlock, int minVertBlock) {
        int divisions = colCovered < pictures.length ? pictures.length : colCovered;
        int totalMin = divisions * minHorzBlock;

        this.minHorzBlock = Utility.upperBound(totalMin, pictures.length);
        this.minVertBlock = minVertBlock;

        for(Picture picture : pictures){
            totalHeight += picture.getHeight();
            picture.setWidth(picture.getWidth() < this.minHorzBlock ? this.minHorzBlock : picture.getWidth());
            totalWidth += picture.getWidth();
        }

        totalHeight = Utility.upperBound(totalHeight, pictures.length);
        totalHeight = totalHeight < minVertBlock ? minVertBlock : totalHeight;

        for(Picture picture : pictures) picture.setHeight(totalHeight);

        return divisions;
    }

    private int calculateVerticalTypeDimensions(int rowCovered, int minHorzBlock, int minVertBlock) {
        int divisions = rowCovered < pictures.length ? pictures.length : rowCovered;
        int totalMin = divisions * minVertBlock;

        this.minVertBlock = Utility.upperBound(totalMin, pictures.length);
        this.minHorzBlock = minHorzBlock;

        for(Picture picture : pictures){
            totalWidth += picture.getWidth();
            picture.setHeight(picture.getHeight() < this.minVertBlock ? this.minVertBlock : picture.getHeight());
            totalHeight += picture.getHeight();
        }

        totalWidth = Utility.upperBound(totalWidth, pictures.length);
        totalWidth = totalWidth < minHorzBlock ? minHorzBlock : totalWidth;

        for(Picture picture : pictures) picture.setWidth(totalWidth);

        return divisions;
    }

    public int changeNextIndividualDimension(int changedDimension, FramedPictures lastPictures){
        this.lastPictures = lastPictures;
        if(isHorizontal){
            if(totalWidth <= changedDimension){
                if(totalWidth < changedDimension){
                    float amount = (changedDimension - totalWidth) / (float) totalWidth;
                    int total = 0;
                    for(Picture picture : pictures){
                        float width = picture.getWidth();
                        width += width * amount;
                        picture.setWidth((int)width);
                        total += width;
                    }

                    int diff = changedDimension - total;
                    pictures[0].setWidth(pictures[0].getWidth() + diff);
                    totalWidth = changedDimension;
                }
                if(nextPictures != null) return nextPictures.changeNextIndividualDimension(changedDimension, this);
            }else{
                int totalMin = minHorzBlock * pictures.length;
                if(changedDimension >= totalMin){
                    float amount = (changedDimension - totalWidth) / (float) totalWidth;
                    int total = 0;
                    for(Picture picture : pictures){
                        float width = picture.getWidth();
                        width += width * amount;
                        if(width < minHorzBlock) width = minHorzBlock;
                        picture.setWidth((int)width);
                        total += width;
                    }

                    if(total > changedDimension){
                        changedDimension = total;
                        totalWidth = changedDimension;
                        if(lastPictures != null) return lastPictures.changeLastIndividualDimension(changedDimension);
                        else if(nextPictures != null) return nextPictures.changeNextIndividualDimension(changedDimension, this);
                    }else {
                        int diff = changedDimension - total;
                        pictures[0].setWidth(pictures[0].getWidth() + diff);
                        totalWidth = changedDimension;
                        if(nextPictures != null) return nextPictures.changeNextIndividualDimension(changedDimension, this);
                    }
                }
            }
        }else{
            if(totalHeight <= changedDimension){
                if(totalHeight < changedDimension){
                    float amount = (changedDimension - totalHeight) / (float) totalHeight;
                    int total = 0;
                    for(Picture picture : pictures){
                        float height = picture.getHeight();
                        height += height * amount;
                        picture.setHeight((int) height);
                        total += height;
                    }

                    int diff = changedDimension - total;
                    pictures[0].setHeight(pictures[0].getHeight() + diff);
                    totalHeight = changedDimension;
                }
                if(nextPictures != null) return nextPictures.changeNextIndividualDimension(changedDimension, this);
            }else{
                int totalMin = minVertBlock * pictures.length;
                if(changedDimension >= totalMin){
                    float amount = (changedDimension - totalHeight) / (float) totalHeight;
                    int total = 0;
                    for(Picture picture : pictures){
                        float height = picture.getHeight();
                        height += height * amount;
                        if(height < minVertBlock) height = minVertBlock;
                        picture.setHeight((int)height);
                        total += height;
                    }

                    if(total > changedDimension){
                        changedDimension = total;
                        totalHeight = changedDimension;
                        if(lastPictures != null) return lastPictures.changeLastIndividualDimension(changedDimension);
                        else if(nextPictures != null) return nextPictures.changeNextIndividualDimension(changedDimension, this);
                    }else {
                        int diff = changedDimension - total;
                        pictures[0].setHeight(pictures[0].getHeight() + diff);
                        totalHeight = changedDimension;
                        if(nextPictures != null) return nextPictures.changeNextIndividualDimension(changedDimension, this);
                    }
                }
            }
        }

        return changedDimension;
    }

    //will not be called as will be disgarded from FrameBundle if less than total min value will come
    private int changeLastIndividualDimension(int changedDimension){
        if(isHorizontal){
            //surely an increased value
            float amount = (changedDimension - totalWidth) / (float) totalWidth;
            int total = 0;
            for(Picture picture : pictures){
                float width = picture.getWidth();
                width += width * amount;
                picture.setWidth((int)width);
                total += width;
            }

            int diff = changedDimension - total;
            pictures[0].setWidth(pictures[0].getWidth() + diff);
            totalWidth = changedDimension;

            if(lastPictures !=  null) return lastPictures.changeLastIndividualDimension(changedDimension);
            else if(nextPictures != null) return nextPictures.changeNextIndividualDimension(changedDimension, this);
        }else{
            //surely an increased value
            float amount = (changedDimension - totalHeight) / (float) totalHeight;
            int total = 0;
            for(Picture picture : pictures){
                float height = picture.getHeight();
                height += height * amount;
                picture.setHeight((int)height);
                total += height;
            }

            int diff = changedDimension - total;
            pictures[0].setHeight(pictures[0].getHeight() + diff);
            totalHeight = changedDimension;

            if(lastPictures !=  null) return lastPictures.changeLastIndividualDimension(changedDimension);
            else if(nextPictures != null) return nextPictures.changeNextIndividualDimension(changedDimension, this);
        }

        return changedDimension;
    }

    public void changeCollectiveDimension(int changedDimension){
        if(isHorizontal){
            totalHeight = changedDimension;
            for(Picture picture : pictures) picture.setHeight(totalHeight);
        }else{
            totalWidth = changedDimension;
            for(Picture picture : pictures) picture.setWidth(totalWidth);
        }
    }

    //for fixed

    public int assignFixed1DimensionAndGetBlocks(int blocksCovered, int fromRow, int fromCol,
                                                 List<FramedPictures> framedPictures, int nextIndex){
        if(nextIndex < framedPictures.size()){
            blocksCovered = framedPictures.get(nextIndex).assignFixed1DimensionAndGetBlocks(blocksCovered,
                    isHorizontal ? fromRow + 1 : fromRow, isHorizontal ? fromCol : fromCol + 1, framedPictures, nextIndex + 1);
        }
        //calculate the min block value
        //calculate all parallel values for x and y
        //put it their

        //but all the dividers will surely be equally divided not based on parallel width or height
        //the variation will only be for types to types
        if(isHorizontal){
            blocksCovered = blocksCovered < pictures.length ? pictures.length : blocksCovered;
            totalHeight = Utility.get5QParallelValue(fromRow);
            int size = 0;
            while(size < blocksCovered){
                totalWidth += Utility.get5QParallelValue(fromCol++);
                size++;
            }

            int total = totalWidth / pictures.length;
            for(Picture picture : pictures){
                picture.setWidth(total);
                picture.setHeight(totalHeight);
            }

            int diff = totalWidth - total;
            pictures[0].setWidth(pictures[0].getWidth() + diff);
        }else{
            blocksCovered = blocksCovered < pictures.length ? pictures.length : blocksCovered;
            totalWidth = Utility.get5QParallelValue(fromCol);
            int size = 0;
            while(size < blocksCovered){
                totalHeight += Utility.get5QParallelValue(fromRow++);
                size++;
            }

            int total = totalHeight / pictures.length;
            for(Picture picture : pictures){
                picture.setHeight(total);
                picture.setWidth(totalWidth);
            }

            int diff = totalHeight - total;
            pictures[0].setHeight(pictures[0].getHeight() + diff);
        }

        return blocksCovered;
    }

    public int assignFixed2DimensionAndGetBlocks(int rowBlockSize, int colBlockSize, int blocksCovered,
                                                 List<FramedPictures> framedPictures, int nextIndex){
        //based on max width and height calculation divide everything in
        //equal blocks

        if(nextIndex < framedPictures.size()){
            blocksCovered = framedPictures.get(nextIndex).assignFixed2DimensionAndGetBlocks(rowBlockSize, colBlockSize,
                    blocksCovered, framedPictures, nextIndex + 1);
        }

        if(isHorizontal){
            blocksCovered = blocksCovered < pictures.length ? pictures.length : blocksCovered;
            totalWidth = colBlockSize * blocksCovered;
            totalHeight = rowBlockSize;

            int total = totalWidth / pictures.length;

            for(Picture picture : pictures){
                picture.setWidth(total);
                picture.setHeight(totalHeight);
            }

            int diff = totalWidth - total;
            pictures[0].setWidth(pictures[0].getWidth() + diff);
        }else{
            blocksCovered = blocksCovered < pictures.length ? pictures.length : blocksCovered;
            totalWidth = colBlockSize;
            totalHeight = rowBlockSize * blocksCovered;

            int total = totalHeight / pictures.length;

            for(Picture picture : pictures){
                picture.setWidth(totalWidth);
                picture.setHeight(total);
            }

            int diff = totalHeight - total;
            pictures[0].setHeight(pictures[0].getHeight() + diff);
        }

        return blocksCovered;
    }
}