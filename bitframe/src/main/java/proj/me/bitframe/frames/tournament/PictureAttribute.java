package proj.me.bitframe.frames.tournament;

public class PictureAttribute implements Comparable<PictureAttribute> {
    private int type;

    private int sequentialRange;
    private int parallelRange;
    private int parallelValue;
    private int sequentialDiffRange;
    private int parallelDiffRange;
    private String sequentialAttributes;
    private int[] indexes;

    public PictureAttribute(int type, int sequentialRange, int parallelRange, int parallelValue, int sequentialDiffRange, int parallelDiffRange, String sequentialAttributes, int[] indexes){
        this.type = type;
        this.sequentialRange = sequentialRange;
        this.parallelRange = parallelRange;
        this.parallelValue = parallelValue;
        this.sequentialDiffRange = sequentialDiffRange;
        this.parallelDiffRange = parallelDiffRange;
        this.indexes = indexes;
        this.sequentialAttributes = sequentialAttributes;
    }


    //all indexes are 2^index
    public int getIndexes() {
        int allUsedIndexes = 0;
        for(int index : indexes) allUsedIndexes ^=1 << index;
        return allUsedIndexes;
    }

    public int[] getRawIndexes(){
        return indexes;
    }

    public int getType(){
        return type;
    }

    public int getSequentialRange() {
        return sequentialRange;
    }

    public int getParallelRange() {
        return parallelRange;
    }

    public int getParallelValue() {
        return parallelValue;
    }

    @Override
    public int compareTo(PictureAttribute another) {
        if(another == null) return 1;
        return attributeBasedComparison(another);
    }

    private int attributeBasedComparison(PictureAttribute another){
        int diff = (another.sequentialRange + another.sequentialDiffRange) - (sequentialRange + sequentialDiffRange);
        if(diff != 0) return diff;
        diff = (another.parallelRange - another.parallelDiffRange) - (parallelRange - parallelDiffRange);
        if(diff != 0) return diff;
        diff = another.sequentialRange - sequentialRange;
        if(diff != 0) return diff;
        diff = another.sequentialAttributes.compareTo(sequentialAttributes);
        if(diff != 0) return diff;
        diff = parallelDiffRange - another.parallelDiffRange;
        if(diff != 0) return diff;
        return another.parallelRange - parallelRange;
    }

    /*private int typeBasedComparison(PictureAttribute another){

        //we also need row and col used to check which minimal width or height required to satisfy
        //minimal condition though it will be a sorting based comparison not like taking best of available type

        int comboType = 0;
        if((type & (15 << 4)) != 0 && (another.type & (15 << 4)) != 0) comboType = 1;
        else if((type & 15) != 0 && (another.type & 15) != 0) comboType = 2;
        else if((type & (15 << 4)) != 0 && (another.type & 15) != 0) comboType = 3;
        else comboType = 4;

        int efficiency1 = -1;
        int efficiency2 = 1;

        switch(comboType){
            case 1:
                //HH
                if(type == AvailableTypes.HF || another.type == AvailableTypes.HF){
                    switch(type){
                        case AvailableTypes.HF:
                            if(wTotal >= 5 && hTotal >= 1) return efficiency1;
                            efficiency1 = wTotal + hTotal;
                            break;
                        case AvailableTypes.HH:
                            efficiency1 = wTotal - 1 + hTotal;
                            break;
                        case AvailableTypes.HT:
                            efficiency1 = wTotal - 2 + hTotal;
                            break;
                        case AvailableTypes.HQ:
                            efficiency1 = wTotal - 3 + hTotal;
                            break;
                    }

                    switch(another.type){
                        case AvailableTypes.HF:
                            if(another.wTotal >= 5 && another.hTotal >= 1) return efficiency2;
                            efficiency2 = another.wTotal + another.hTotal;
                            break;
                        case AvailableTypes.HH:
                            efficiency2 = another.wTotal - 1 + another.hTotal;
                            break;
                        case AvailableTypes.HT:
                            efficiency2 = another.wTotal - 2 + another.hTotal;
                            break;
                        case AvailableTypes.HQ:
                            efficiency2 = another.wTotal - 3 + another.hTotal;
                            break;
                    }

                    return efficiency2 - efficiency1 == 0 ? (type == AvailableTypes.HF ? -1 : 1) : efficiency2 - efficiency1;
                }else if(type == AvailableTypes.HH || another.type == AvailableTypes.HH){
                    switch(type){
                        case AvailableTypes.HH:
                            if(wTotal >= 5 && hTotal >= 1 && xDiff >= 3 && yDiff <= 1) return efficiency1;
                            efficiency1 = wTotal - 1 + hTotal + xDiff - yDiff;
                            break;
                        case AvailableTypes.HT:
                            efficiency1 = wTotal - 2 + hTotal + xDiff - yDiff;
                            break;
                        case AvailableTypes.HQ:
                            efficiency1 = wTotal - 3 + hTotal + xDiff - yDiff;
                            break;
                    }

                    switch(another.type){
                        case AvailableTypes.HH:
                            if(another.wTotal >= 5 && another.hTotal >= 1 && another.xDiff >= 3 && another.yDiff <= 1) return efficiency2;
                            efficiency2 = another.wTotal - 1 + another.hTotal + another.xDiff - another.yDiff;
                            break;
                        case AvailableTypes.HT:
                            efficiency2 = another.wTotal - 2 + another.hTotal + another.xDiff - another.yDiff;
                            break;
                        case AvailableTypes.HQ:
                            efficiency2 = another.wTotal - 3 + another.hTotal + another.xDiff - another.yDiff;
                            break;
                    }

                    return efficiency2 - efficiency1 == 0 ? (type == AvailableTypes.HH ? -1 : 1) : efficiency2 - efficiency1;
                }else{
                    switch(type){
                        case AvailableTypes.HT:
                            if(wTotal >= 6 && hTotal >= 1 && xDiff >= 2 && yDiff <= 1) return efficiency1;
                            efficiency1 = wTotal - 2 + hTotal + xDiff - yDiff;
                            break;
                        case AvailableTypes.HQ:
                            efficiency1 = wTotal - 3 + hTotal + xDiff - yDiff;
                            break;
                    }

                    switch(another.type){
                        case AvailableTypes.HT:
                            if(another.wTotal >= 6 && another.hTotal >= 1 && another.xDiff >= 2 && another.yDiff <= 1) return efficiency2;
                            efficiency2 = another.wTotal - 2 + another.hTotal + another.xDiff - another.yDiff;
                            break;
                        case AvailableTypes.HQ:
                            efficiency2 = another.wTotal - 3 + another.hTotal + another.xDiff - another.yDiff;
                            break;
                    }

                    return efficiency2 - efficiency1 == 0 ? (type == AvailableTypes.HT ? -1 : 1) : efficiency2 - efficiency1;
                }
            case 2:
                //VV
                if(type == AvailableTypes.VF || another.type == AvailableTypes.VF){
                    switch(type){
                        case AvailableTypes.VF:
                            if(hTotal >= 5 && wTotal >= 1) return efficiency1;
                            efficiency1 = hTotal + wTotal;
                            break;
                        case AvailableTypes.VH:
                            efficiency1 = hTotal - 1 + wTotal;
                            break;
                        case AvailableTypes.VT:
                            efficiency1 = hTotal - 2 + wTotal;
                            break;
                        case AvailableTypes.VQ:
                            efficiency1 = hTotal - 3 + wTotal;
                            break;
                    }

                    switch(another.type){
                        case AvailableTypes.VF:
                            if(another.hTotal >= 5 && another.wTotal >= 1) return efficiency2;
                            efficiency2 = another.hTotal + another.wTotal;
                            break;
                        case AvailableTypes.VH:
                            efficiency2 = another.hTotal - 1 + another.wTotal;
                            break;
                        case AvailableTypes.VT:
                            efficiency2 = another.hTotal - 2 + another.wTotal;
                            break;
                        case AvailableTypes.VQ:
                            efficiency2 = another.hTotal - 3 + another.wTotal;
                            break;
                    }

                    return efficiency2 - efficiency1 == 0 ? (type == AvailableTypes.VF ? -1 : 1) : efficiency2 - efficiency1;
                }else if(type == AvailableTypes.VH || another.type == AvailableTypes.VH){
                    switch(type){
                        case AvailableTypes.VH:
                            if(hTotal >= 5 && wTotal >= 1 && yDiff >= 3 && xDiff <= 1) return efficiency1;
                            efficiency1 = hTotal - 1 + wTotal + yDiff - xDiff;
                            break;
                        case AvailableTypes.VT:
                            efficiency1 = hTotal - 2 + wTotal + yDiff - xDiff;
                            break;
                        case AvailableTypes.VQ:
                            efficiency1 = hTotal - 3 + wTotal + yDiff - xDiff;
                            break;
                    }

                    switch(another.type){
                        case AvailableTypes.VH:
                            if(another.hTotal >= 5 && another.wTotal >= 1 && another.yDiff >= 3 && another.xDiff <= 1) return efficiency2;
                            efficiency2 = another.hTotal - 1 + another.wTotal + another.yDiff - another.xDiff;
                            break;
                        case AvailableTypes.VT:
                            efficiency2 = another.hTotal - 2 + another.wTotal + another.yDiff - another.xDiff;
                            break;
                        case AvailableTypes.VQ:
                            efficiency2 = another.hTotal - 3 + another.wTotal + another.yDiff - another.xDiff;
                            break;
                    }

                    return efficiency2 - efficiency1 == 0 ? (type == AvailableTypes.VH ? -1 : 1) : efficiency2 - efficiency1;
                }else{
                    switch(type){
                        case AvailableTypes.VT:
                            if(hTotal >= 6 && wTotal >= 1 && yDiff >= 2 && xDiff <= 1) return efficiency1;
                            efficiency1 = hTotal - 2 + wTotal + yDiff - xDiff;
                            break;
                        case AvailableTypes.VQ:
                            efficiency1 = hTotal - 3 + wTotal + yDiff - xDiff;
                            break;
                    }

                    switch(another.type){
                        case AvailableTypes.VT:
                            if(another.hTotal >= 6 && another.wTotal >= 1 && another.yDiff >= 2 && another.xDiff <= 1) return efficiency2;
                            efficiency2 = another.hTotal - 2 + another.wTotal + another.yDiff - another.xDiff;
                            break;
                        case AvailableTypes.VQ:
                            efficiency2 = another.hTotal - 3 + another.wTotal + another.yDiff - another.xDiff;
                            break;
                    }

                    return efficiency2 - efficiency1 == 0 ? (type == AvailableTypes.VT ? -1 : 1) : efficiency2 - efficiency1;
                }
            case 3:
                //HV
                switch(type){
                    case AvailableTypes.HF:
                        if(wTotal >= 5 && hTotal >= 1) return efficiency1;
                        efficiency1 = wTotal + hTotal;
                        break;
                    case AvailableTypes.HH:
                        efficiency1 = wTotal - 1 + hTotal + xDiff - yDiff;
                        break;
                    case AvailableTypes.HT:
                        efficiency1 = wTotal - 2 + hTotal + xDiff - yDiff;
                        break;
                    case AvailableTypes.HQ:
                        efficiency1 = wTotal - 3 + hTotal + xDiff - yDiff;
                        break;
                }

                switch(another.type){
                    case AvailableTypes.VF:
                        efficiency2 = another.hTotal + another.wTotal;
                        if(another.hTotal >= 5 && another.wTotal >= 1){
                            if(type == AvailableTypes.HF) return efficiency2 - efficiency1 == 0 ? -1 : efficiency2 - efficiency1;
                            return efficiency2;
                        }
                        return efficiency2 - efficiency1 == 0 ? (type == AvailableTypes.HF ? -1 : 1) : efficiency2 - efficiency1;
                    case AvailableTypes.VH:
                        efficiency2 = another.hTotal - 1 + another.wTotal + another.yDiff - another.xDiff;
                        return efficiency2 - efficiency1 == 0 ? (type == AvailableTypes.HF || type == AvailableTypes.HH ? -1 : 1) : efficiency2 - efficiency1;
                    case AvailableTypes.VT:
                        efficiency2 = another.hTotal - 2 + another.wTotal + another.yDiff - another.xDiff;
                        return efficiency2 - efficiency1 == 0 ? (type == AvailableTypes.HF || type == AvailableTypes.HH || type == AvailableTypes.HT  ? -1 : 1) : efficiency2 - efficiency1;
                    case AvailableTypes.VQ:
                        efficiency2 = another.hTotal - 3 + another.wTotal + another.yDiff - another.xDiff;
                        return efficiency2 - efficiency1 == 0 ? -1 : efficiency2 - efficiency1;
                }
                break;
            case 4:
                //VH
                switch(another.type){
                    case AvailableTypes.HF:
                        if(another.wTotal >= 5 && another.hTotal >= 1) return efficiency2;
                        efficiency2 = another.wTotal + another.hTotal;
                        break;
                    case AvailableTypes.HH:
                        efficiency2 = another.wTotal - 1 + another.hTotal + another.xDiff - another.yDiff;
                        break;
                    case AvailableTypes.HT:
                        efficiency2 = another.wTotal - 2 + another.hTotal + another.xDiff - another.yDiff;
                        break;
                    case AvailableTypes.HQ:
                        efficiency2 = another.wTotal - 3 + another.hTotal + another.xDiff - another.yDiff;
                        break;
                }

                switch(type){
                    case AvailableTypes.VF:
                        efficiency1 = hTotal + wTotal;
                        if(hTotal >= 5 && wTotal >= 1){
                            if(another.type == AvailableTypes.HF) return efficiency1 - efficiency2 == 0 ? 1 : efficiency1 - efficiency2;
                            return efficiency1;
                        }
                        return efficiency1 - efficiency2 == 0 ? (another.type == AvailableTypes.HF ? 1 : -1) : efficiency1 - efficiency2;
                    case AvailableTypes.VH:
                        efficiency1 = hTotal - 1 + wTotal + yDiff - xDiff;
                        return efficiency1 - efficiency2 == 0 ? (another.type == AvailableTypes.HF || another.type == AvailableTypes.HH ? 1 : -1) : efficiency1 - efficiency2;
                    case AvailableTypes.VT:
                        efficiency1 = hTotal - 2 + wTotal + yDiff - xDiff;
                        return efficiency1 - efficiency2 == 0 ? (another.type == AvailableTypes.HF || another.type == AvailableTypes.HH || another.type == AvailableTypes.HT  ? 1 : -1) : efficiency1 - efficiency2;
                    case AvailableTypes.VQ:
                        efficiency1 = hTotal - 3 + wTotal + yDiff - xDiff;
                        return efficiency1 - efficiency2 == 0 ? 1 : efficiency1 - efficiency2;
                }
                break;
        }

        return 0;
    }*/
}