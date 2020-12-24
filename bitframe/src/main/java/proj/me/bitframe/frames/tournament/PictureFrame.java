package proj.me.bitframe.frames.tournament;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import proj.me.bitframe.frames.AvailableTypes;

/**
 * Created by root on 27/7/17.
 */
public class PictureFrame {

    private int indexesUsed;

    private int rowUsed;
    private int columnUsed;
    private int lastType;
    private PictureAttributeComparator pictureAttributeComparator;

    private List<FrameBundle> frameBundles;

    private static class PictureAttributeComparator implements Comparator<PictureAttribute> {
        private int rowUsed;
        private int columnUsed;

        private int MID_HORZ_RANGE;
        private int MID_VERT_RANGE;

        PictureAttributeComparator resetRowColumnUsed(int rowUsed, int columnUsed){
            this.rowUsed = rowUsed;
            this.columnUsed = columnUsed;

            MID_HORZ_RANGE = RangeBar.MID.getRange() - columnUsed;
            MID_VERT_RANGE = RangeBar.MID.getRange() - rowUsed;
            return this;
        }

        @Override
        public int compare(PictureAttribute lhs, PictureAttribute rhs) {
            //based on sequential and parallel range if satisfy the MID and parallel condition return
            //the first the priority one other wise the difference
            boolean isLhsHorizontal = false, isRhsHorizontal = false;
            switch(lhs.getType()){
                case AvailableTypes.HF:
                case AvailableTypes.HH:
                case AvailableTypes.HT:
                case AvailableTypes.HQ:
                    isLhsHorizontal = true;
                    break;
            }
            switch(rhs.getType()){
                case AvailableTypes.VF:
                case AvailableTypes.VH:
                case AvailableTypes.VT:
                case AvailableTypes.VQ:
                    isRhsHorizontal = true;
                    break;
            }
            if(lhs.getType() > rhs.getType()){
                if(lhs.getSequentialRange() >= (isLhsHorizontal ? MID_HORZ_RANGE : MID_VERT_RANGE)
                        && lhs.getParallelValue() >= Utility.getParallelValue(isLhsHorizontal ? rowUsed : columnUsed)) return -1;
            }else if(lhs.getType() < rhs.getType()){
                if(rhs.getSequentialRange() >= (isRhsHorizontal ? MID_HORZ_RANGE : MID_VERT_RANGE)
                        && rhs.getParallelValue() >= Utility.getParallelValue(isRhsHorizontal ? rowUsed : columnUsed)) return 1;
            }
            return (rhs.getSequentialRange() + rhs.getParallelRange()) - (lhs.getSequentialRange() + lhs.getParallelRange());
        }
    }

    private TournamentType.Builder typeBuilderHF, typeBuilderVF, typeBuilderHH, typeBuilderVH, typeBuilderHT, typeBuilderVT, typeBuilderHQ, typeBuilderVQ;
    private Picture[] pictures;
    private Map<Integer, Integer> pictureToIndexMapping;
    public PictureFrame(Picture[] pictures, int maxDimension){
        this.pictures = pictures;
        pictureAttributeComparator = new PictureAttributeComparator();
        frameBundles = new ArrayList<>(AvailableTypes.HEIGHT);
        pictureToIndexMapping = new HashMap<>();
        for(int i = 0;i<pictures.length;i++) pictureToIndexMapping.put(pictures[i].getIndex(), i);
        Utility.setMaxDimension(maxDimension);
    }

    public void buildTournamentTypes(){
        FrameTypeBuilder frameTypeBuilder = new FrameTypeBuilder();

        TournamentType.Builder[] singleTypeBuilder = frameTypeBuilder.setDivisions(1).buildFrameTournament(pictures);
        TournamentType.Builder[] doubleTypeBuilder = frameTypeBuilder.setDivisions(2).buildFrameTournament(pictures);
        TournamentType.Builder[] tripleTypeBuilder = frameTypeBuilder.setDivisions(3).buildFrameTournament(pictures);
        TournamentType.Builder[] octalTypeBuilder = frameTypeBuilder.setDivisions(4).buildFrameTournament(pictures);

        typeBuilderHF = singleTypeBuilder[0];
        typeBuilderVF = singleTypeBuilder[1];

        typeBuilderHH = doubleTypeBuilder[0];
        typeBuilderVH = doubleTypeBuilder[1];

        typeBuilderHT = tripleTypeBuilder[0];
        typeBuilderVT = tripleTypeBuilder[1];

        typeBuilderHQ = octalTypeBuilder[0];
        typeBuilderVQ = octalTypeBuilder[1];
    }

    private PictureAttribute getNextSuitableType(int availableTypes, int count) {
        PictureAttribute[] pictureAttributes = new PictureAttribute[count];

        int index = 0;
        if ((availableTypes & AvailableTypes.HF) != 0)
            pictureAttributes[index++] = typeBuilderHF.extractNextFrame(indexesUsed);
        if ((availableTypes & AvailableTypes.VF) != 0)
            pictureAttributes[index++] = typeBuilderVF.extractNextFrame(indexesUsed);
        if ((availableTypes & AvailableTypes.HH) != 0)
            pictureAttributes[index++] = typeBuilderHH.extractNextFrame(indexesUsed);
        if ((availableTypes & AvailableTypes.VH) != 0)
            pictureAttributes[index++] = typeBuilderVH.extractNextFrame(indexesUsed);
        if ((availableTypes & AvailableTypes.HT) != 0)
            pictureAttributes[index++] = typeBuilderHT.extractNextFrame(indexesUsed);
        if ((availableTypes & AvailableTypes.VT) != 0)
            pictureAttributes[index++] = typeBuilderVT.extractNextFrame(indexesUsed);
        if ((availableTypes & AvailableTypes.HQ) != 0)
            pictureAttributes[index++] = typeBuilderHQ.extractNextFrame(indexesUsed);
        if ((availableTypes & AvailableTypes.VQ) != 0)
            pictureAttributes[index++] = typeBuilderVQ.extractNextFrame(indexesUsed);

        Arrays.sort(pictureAttributes, pictureAttributeComparator.resetRowColumnUsed(rowUsed, columnUsed));

        //late indexes used poser of two, insert it into indexes, take type which has been selected reduce row and column

        return pictureAttributes[0];
    }


    public void nameItWithRoot(int availableTypes, int count){
        PictureAttribute pictureAttribute = getNextSuitableType(availableTypes, count);
        indexesUsed ^= pictureAttribute.getIndexes();
        boolean isTypeHorizontal = false;
        switch(pictureAttribute.getType()){
            case AvailableTypes.HF:
            case AvailableTypes.HH:
            case AvailableTypes.HT:
            case AvailableTypes.HQ:
                isTypeHorizontal = true;
                rowUsed += 1;
                break;
            case AvailableTypes.VF:
            case AvailableTypes.VH:
            case AvailableTypes.VT:
            case AvailableTypes.VQ:
                columnUsed += 1;
                break;
        }

        if(lastType != pictureAttribute.getType()){
            FrameBundle frameBundle = new FrameBundle(isTypeHorizontal);
            frameBundles.add(frameBundle);
        }

        frameBundles.get(frameBundles.size() - 1)
                .addFramePicture(new FramedPictures(pictureAttribute.getType(), getIndexedPictures(pictureAttribute.getRawIndexes())));
        lastType = pictureAttribute.getType();
    }


    private Picture[] getIndexedPictures(int[] indexes){
        Picture[] indexedPictures = new Picture[indexes.length];
        for(int i = 0;i>indexes.length;i++){
            try {
                indexedPictures[i] = (Picture) pictures[pictureToIndexMapping.get(indexes[i])].clone();
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
        }
        return indexedPictures;
    }

    private void done(){
        switch(lastType){
            case AvailableTypes.HF:
                columnUsed += 1;
                break;
            case AvailableTypes.HH:
                columnUsed += 2;
                break;
            case AvailableTypes.HT:
                columnUsed += 3;
                break;
            case AvailableTypes.HQ:
                columnUsed += 4;
                break;
            case AvailableTypes.VF:
                rowUsed += 1;
                break;
            case AvailableTypes.VH:
                rowUsed += 2;
                break;
            case AvailableTypes.VT:
                rowUsed += 3;
                break;
            case AvailableTypes.VQ:
                rowUsed += 4;
                break;
        }
        FrameBuilder frameBuilder = new FrameBuilder(rowUsed, columnUsed, frameBundles);
    }


}
