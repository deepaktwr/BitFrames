package proj.me.bitframe.frames.tournament;

import java.util.Arrays;
import java.util.Comparator;

import proj.me.bitframe.frames.AvailableTypes;

/**
 * Created by root on 27/7/17.
 */
public class FrameTypeBuilder {

    private int divisions;
    public FrameTypeBuilder(){}

    public FrameTypeBuilder setDivisions(int divisions){
        this.divisions = divisions;
        return this;
    }

    private static class PictureComparator implements Comparator<Picture> {
        boolean forHorizontal;
        private PictureComparator setHorizontal(boolean forHorizontal){
            this.forHorizontal = forHorizontal;
            return this;
        }
        @Override
        public int compare(Picture lhs, Picture rhs) {
            if(forHorizontal){
                if(lhs.getWidth() > rhs.getWidth()) return -1;
                else if(lhs.getWidth() == rhs.getWidth()){
                    if(lhs.getHeight() > rhs.getHeight()) return -1;
                    else if(lhs.getHeight() < rhs.getHeight()) return 1;
                    else return 0;
                }else return 1;
            }else{
                if(lhs.getHeight() > rhs.getHeight()) return -1;
                else if(lhs.getHeight() == rhs.getHeight()){
                    if(lhs.getWidth() > rhs.getWidth()) return -1;
                    else if(lhs.getWidth() < rhs.getWidth()) return 1;
                    else return 0;
                }else return 1;
            }
        }
    }

    public TournamentType.Builder[] buildFrameTournament(Picture[] pictures/*, int totalTournaments*/){
        TournamentType horzTournament = new TournamentType(getType(true));
        TournamentType vertTournament = new TournamentType(getType(false));

        Picture[] picturesHorz = Arrays.copyOf(pictures, pictures.length);
        Picture[] picturesVert = Arrays.copyOf(pictures, pictures.length);

        PictureComparator pictureComparator = new PictureComparator();

        Arrays.sort(picturesHorz, pictureComparator.setHorizontal(true));
        Arrays.sort(picturesVert, pictureComparator.setHorizontal(false));

        //creating combinations
        int totalCombinations = Utility.binomialCoefficient(pictures.length, divisions);
        int totalTournaments = Utility.getTotalTournaments(totalCombinations);

        PictureAttribute[] pictureHorzAttributes = new PictureAttribute[totalCombinations];
        PictureAttribute[] pictureVertAttributes = new PictureAttribute[totalCombinations];



        createCombinations(picturesHorz, picturesVert, pictureHorzAttributes, pictureVertAttributes, new Picture[divisions], new Picture[divisions], 0, -1, -1);

        //creating tournament
        return new TournamentType.Builder[]{horzTournament.build(pictureHorzAttributes, totalTournaments),
                vertTournament.build(pictureVertAttributes, totalTournaments)};
    }

    private int createCombinations(Picture[] picturesHorz, Picture[] picturesVert, PictureAttribute[] pictureHorzAttributes, PictureAttribute[] pictureVertAttributes, Picture[] indexesHorz, Picture[] indexesVert, int pos, int lastCombo, int lastFixedIndex){
        if(lastFixedIndex == indexesHorz.length - 1){
            pictureHorzAttributes[pos] = setHorizontalAttributes(indexesHorz);
            pictureHorzAttributes[pos] = setVerticalAttributes(indexesVert);
            return pos + 1;
        }

        int fixedIndex = lastFixedIndex + 1;
        int combo = lastCombo + 1;

        for(int i = combo;i<picturesHorz.length;i++){
            indexesHorz[fixedIndex] = picturesHorz[i];
            indexesVert[fixedIndex] = picturesVert[i];
            pos = createCombinations(picturesHorz, picturesVert, pictureHorzAttributes, pictureVertAttributes, indexesHorz, indexesVert, pos, i, fixedIndex);
        }

        return pos;
    }

    private PictureAttribute setHorizontalAttributes(Picture[] pictures){
        int[] indexes = new int[pictures.length];
        String sequentialAttributes = "";
        int[] diffRange = new int[pictures.length - 1];
        int sequentialDiffRange = 0;
        int rangeValue = 0;
        int parallelDiff = 0;

        int sequentialTotal = 0;
        int parallelTotal = 0;

        for(int i = 0;i< pictures.length;i++){
            Picture picture = pictures[i];
            indexes[i] = picture.getIndex();
            sequentialTotal += picture.getWidth();
            parallelTotal += picture.getHeight();
            sequentialAttributes += Utility.getValueRange(pictures[i].getWidth()).getRange();
            if(i < pictures.length - 1){
                rangeValue = Utility.getValueRange(pictures[i].getWidth()).getRange()
                        - Utility.getValueRange(pictures[i + 1].getWidth()).getRange();
                sequentialDiffRange += rangeValue;
                diffRange[i] = rangeValue;
                parallelDiff = Math.max(Math.abs(pictures[i].getHeight() - pictures[i + 1].getHeight()), parallelDiff);
            }
        }

        parallelTotal /= pictures.length;

        return new PictureAttribute(getType(true), Utility.getValueRange(sequentialTotal).getRange(),
                Utility.getValueRange(parallelTotal).getRange(), parallelTotal, sequentialDiffRange + 1,
                Utility.getValueRange(parallelDiff).getRange() + 1, getSequentialAttributes(diffRange), indexes);
    }

    private PictureAttribute setVerticalAttributes(Picture[] pictures){
        int[] indexes = new int[pictures.length];
        int[] diffRange = new int[pictures.length - 1];
        String sequentialAttributes = "";
        int sequentialDiffRange = 0;
        int rangeValue = 0;
        int parallelDiff = 0;

        int sequentialTotal = 0;
        int parallelTotal = 0;

        for(int i = 0;i< pictures.length;i++){
            Picture picture = pictures[i];
            indexes[i] = picture.getIndex();
            sequentialTotal += picture.getHeight();
            parallelTotal += picture.getWidth();
            sequentialAttributes += Utility.getValueRange(pictures[i].getHeight()).getRange();
            if (i < pictures.length - 1){
                rangeValue = Utility.getValueRange(pictures[i].getHeight()).getRange()
                        - Utility.getValueRange(pictures[i + 1].getHeight()).getRange();
                sequentialDiffRange += rangeValue;
                diffRange[i] = rangeValue;
                parallelDiff = Math.max(Math.abs(pictures[i].getWidth() - pictures[i + 1].getWidth()), parallelDiff);
            }
        }

        parallelTotal /= pictures.length;

        return new PictureAttribute(getType(false), Utility.getValueRange(sequentialTotal).getRange(),
                Utility.getValueRange(parallelTotal).getRange(), parallelTotal, sequentialDiffRange + 1,
                Utility.getValueRange(parallelDiff).getRange() + 1, getSequentialAttributes(diffRange), indexes);
    }

    private int getType(boolean isHorizontal){
        switch(divisions){
            case 1:
                return isHorizontal ? AvailableTypes.HF : AvailableTypes.VF;
            case 2:
                return isHorizontal ? AvailableTypes.HH : AvailableTypes.VH;
            case 3:
                return isHorizontal ? AvailableTypes.HT : AvailableTypes.VT;
            case 4:
                return isHorizontal ? AvailableTypes.HQ : AvailableTypes.VQ;
        }

        return isHorizontal ? AvailableTypes.HF : AvailableTypes.VF;
    }

    private String getSequentialAttributes(int[] diffRange){
        Arrays.sort(diffRange);
        String attributes = "";
        for(int ele : diffRange) attributes += ele;
        return attributes;
    }
}
