package proj.me.bitframe.frames.tournament;

import java.util.Arrays;

/**
 * Created by root on 25/7/17.
 */
public class TournamentType {
    private static class TournamentLeaves{
        int index = -1;
        PictureAttribute[] attrs;
        TournamentLeaves(PictureAttribute[] attrs){
            this.attrs = attrs;
            index = 0;
        }
    }

    private int type;
    private Builder builder;

    public TournamentType(int type){
        this.type = type;
    }

    public Builder build(PictureAttribute[] typeCombinations, int totalTournaments){
        if(builder != null) return builder.reset();
        builder = new Builder(totalTournaments)
                .buildTournamentTree(typeCombinations);
        return builder;
    }


    public static class Builder{
        private TournamentLeaves[] tournamentLeaves;
        private int totalTournaments;
        private PictureAttribute[] heap;
        private int heapSize;

        private Builder(int totalTournaments){
            this.totalTournaments = totalTournaments;
            tournamentLeaves = new TournamentLeaves[totalTournaments];
        }

        public Builder reset(){
            for(TournamentLeaves tournamentLeave : tournamentLeaves)
                if(tournamentLeave.index != -1) tournamentLeave.index = 0;
            return this;
        }

        private Builder buildTournamentTree(PictureAttribute[] typeCombinations){
            int size = typeCombinations.length;

            int comboSize = size / totalTournaments;

            for(int i = totalTournaments - 1;i>=0;i--){
                int length = comboSize < 0 ? 0 : comboSize;
                if(length == 0 && i != 0) continue;
                else if(i == 0) length = size - comboSize * (totalTournaments - 1);

                PictureAttribute[] combo = Arrays.copyOfRange(typeCombinations, 0, length);
                Arrays.sort(combo);
                tournamentLeaves[i] = new TournamentLeaves(combo);
            }

            int actualLeaves = 1;
            while(totalTournaments > actualLeaves) actualLeaves <<= 1;

            heap = new PictureAttribute[actualLeaves + actualLeaves - 1];

            int leaf = 0;
            for(int i = actualLeaves - 1;i< heap.length;i++){
                if(leaf < totalTournaments){
                    TournamentLeaves tournamentLeave = tournamentLeaves[leaf];
                    if(tournamentLeave.index == -1){
                        leaf++;
                        continue;
                    }
                    heap[i] = tournamentLeave.attrs[tournamentLeave.index++];
                    heapSize++;
                    leaf++;
                }else break;
            }

            heapSize += actualLeaves - 1;

            for(int i = actualLeaves - 1;i >= 0;i--) buildMaxHeap(i);

            return this;
        }

        private void buildMaxHeap(int index){
            if(index >= heapSize || index < 0) return;
            int left = index * 2 + 1;
            int right = index * 2 + 2;

            int position = index;
            if(left < heapSize && heap[left].compareTo(heap[position]) == 1) position = left;
            if(right < heapSize && heap[right].compareTo(heap[position]) == 1) position = right;

            if(position == index) return;

            PictureAttribute temp = heap[position];
            heap[position] = heap[index];
            heap[index] = temp;

            index = heap.length >> 1;
            if(position >= index && heap[position] == null){
                index = position - index;
                TournamentLeaves tournamentLeave = tournamentLeaves[index];
                if(tournamentLeave.index > -1 && tournamentLeave.index < tournamentLeave.attrs.length)
                    heap[position] = tournamentLeave.attrs[tournamentLeave.index++];
            }else buildMaxHeap(position);
        }

        private PictureAttribute extractMax(int indexesUsed){
            if(heapSize == 0) return null;
            PictureAttribute pictureAttribute = heap[0];
            heap[0] = heap[heapSize - 1];

            int index = heap.length >> 1;
            if(heapSize - 1 >= index){
                index = heapSize - 1 - index;

                TournamentLeaves tournamentLeave = tournamentLeaves[index];
                if(tournamentLeave.index == -1) heapSize--;
                else {
                    for (; tournamentLeave.index < tournamentLeave.attrs.length; tournamentLeave.index++)
                        if ((tournamentLeave.attrs[tournamentLeave.index].getIndexes() & indexesUsed) == 0)
                            break;

                    if (tournamentLeave.index < tournamentLeave.attrs.length) {
                        heap[heapSize - 1] = tournamentLeave.attrs[tournamentLeave.index++];
                    } else heapSize--;
                }

            }else heapSize--;

            buildMaxHeap(0);
            return pictureAttribute;
        }

        public PictureAttribute extractNextFrame(int indexesUsed){
            PictureAttribute pictureAttribute = extractMax(indexesUsed);

            while(pictureAttribute != null && (pictureAttribute.getIndexes() & indexesUsed) != 0)
                pictureAttribute = extractMax(indexesUsed);

            return pictureAttribute;
        }
    }
}
