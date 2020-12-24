package proj.me.bitframe.frames.tournament;


import proj.me.bitframe.frames.AvailableTypes;

/**
 * Created by root on 27/7/17.
 */
public class Utility {



    /*public static int binomialCoefficient_1c1_16c4(int n, int r){
        //n * (n - 1) .... (n - r + 1) / r * (r - 1) ... 1
        //here r is leeser value then those two

        //i from 0 to 3 max
        int result = 1;
        for(int i = 0;i<r;i++){

            int x = n - i;
            int times = 0;
            while(x % 2 == 0){
                x >>= 1;
                times++;
            }
            if(times != 0) result <<= times;
            result *= x;

            x = i + 1;
            times = 0;
            while(x % 2 == 0){
                x >>= 1;
                times++;
            }
            if(times != 0) result >>= times;
            result /= x;
        }

        return result;
    }*/

    public static int binomialCoefficient(int n, int r){
        //n * (n - 1) .... (n - r + 1) / r * (r - 1) ... 1
        //here r is leeser value then those two
        int result = 1;
        for(int i = 0;i<r;i++){
            result *= (n - i);
            result /= (i + 1);
        }

        return result;
    }

    public static int Z_LENGTH;
    private static int CELL_LENGTH;
    public static int MID, MID_5Q;
    private static int[] PARALLEL_VALUES = new int[4];
    private static int[] PARALLEL_VALUES_5Q = new int[4];

    public static void setMaxDimension(int Z){
        CELL_LENGTH = Z / 9;
        Z_LENGTH = CELL_LENGTH * 9;

        MID = upperBound(Z_LENGTH, 2);

        int firstMin = upperBound(MID, 2);
        int lastMin = upperBound(upperBound(MID, 4), 2);

        int i = (firstMin - lastMin) / 5;

        PARALLEL_VALUES[0] = firstMin - i;
        PARALLEL_VALUES[1] = firstMin - 2 * i;
        PARALLEL_VALUES[2] = firstMin - 3 * i;
        PARALLEL_VALUES[3] = firstMin - 4 * i;

        MID_5Q = upperBound(Z_LENGTH, 2) + upperBound(Z_LENGTH, 8);

        firstMin = upperBound(MID_5Q, 2);
        lastMin = upperBound(upperBound(MID_5Q, 4), 2);

        i = (firstMin - lastMin) / 5;

        PARALLEL_VALUES_5Q[0] = firstMin - i;
        PARALLEL_VALUES_5Q[1] = firstMin - 2 * i;
        PARALLEL_VALUES_5Q[2] = firstMin - 3 * i;
        PARALLEL_VALUES_5Q[3] = firstMin - 4 * i;
    }

    public static int getParallelValue(int rowOrColumnUsed){
        if(rowOrColumnUsed < 0) return PARALLEL_VALUES[0];
        if(rowOrColumnUsed > 3) return PARALLEL_VALUES[3];
        return PARALLEL_VALUES[rowOrColumnUsed];
    }


    public static int get5QParallelValue(int rowOrColumnUsed){
        if(rowOrColumnUsed < 0) return PARALLEL_VALUES_5Q[0];
        if(rowOrColumnUsed > 3) return PARALLEL_VALUES_5Q[3];
        return PARALLEL_VALUES_5Q[rowOrColumnUsed];
    }

    public static int upperBound(int x, int y){
        double result = Math.ceil(x / (double)y);
        return (int) result;
    }

    public static int getHorizontalMinValue(int columnUsed, int type){
        switch(type){
            case AvailableTypes.HF:
                switch(columnUsed){
                    case 0:
                        return MID >> 1;
                    case 1:
                        return (MID - (MID >> 2)) >> 1;
                    case 2:
                        return MID >> 2;
                }
                break;
            case AvailableTypes.HH:
                switch(columnUsed){
                    case 0:
                        return (MID >> 1) >> 1;
                    case 1:
                        return ((MID - (MID >> 2)) >> 1) >> 1;
                    case 2:
                        return (MID >> 2) >> 1;
                }
                break;
            case AvailableTypes.HT:
                switch(columnUsed){
                    case 0:
                        return (MID / 3) >> 1;
                    case 1:
                        return ((MID - (MID >> 2)) / 3) >> 1;
                }
                break;
            case AvailableTypes.HQ:
                return (MID >> 2) >> 1;
        }

        //assuming it's HF
        switch(columnUsed){
            case 0:
                return MID >> 1;
            case 1:
                return (MID - (MID >> 2)) >> 1;
            case 2:
                return MID >> 2;
        }

        return (MID >> 2) >> 1;
    }

    public static int getVerticalMinValue(int rowUsed, int type){
        switch(type){
            case AvailableTypes.VF:
                switch(rowUsed){
                    case 0:
                        return MID >> 1;
                    case 1:
                        return (MID - (MID >> 2)) >> 1;
                    case 2:
                        return MID >> 2;
                }
                break;
            case AvailableTypes.VH:
                switch(rowUsed){
                    case 0:
                        return (MID >> 1) >> 1;
                    case 1:
                        return ((MID - (MID >> 2)) >> 1) >> 1;
                    case 2:
                        return (MID >> 2) >> 1;
                }
                break;
            case AvailableTypes.VT:
                switch(rowUsed){
                    case 0:
                        return (MID / 3) >> 1;
                    case 1:
                        return ((MID - (MID >> 2)) / 3) >> 1;
                }
                break;
            case AvailableTypes.VQ:
                return (MID >> 2) >> 1;
        }

        //assuming it's VF
        switch(rowUsed){
            case 0:
                return MID >> 1;
            case 1:
                return (MID - (MID >> 2)) >> 1;
            case 2:
                return MID >> 2;
        }

        return (MID >> 2) >> 1;
    }

    public static int getTotalTournaments(int totalCombinations){
        if(totalCombinations <= 8) return 2;
        if(totalCombinations <= 28) return 4;
        if(totalCombinations <= 80) return 8;
        if(totalCombinations <= 240) return 16;
        if(totalCombinations <= 704) return 32;
        return 64;
    }

    public int getMaxDimension(){
        return Z_LENGTH;
    }

    public static RangeBar getValueRange(int value){
        int range = value / CELL_LENGTH;
        switch(range){
            case 0:
            case 1:
                return RangeBar.FIRST;
            case 2:
                return RangeBar.SECOND;
            case 3:
                return RangeBar.THIRD;
            case 4:
                return RangeBar.FOURTH;
            case 5:
                return RangeBar.MID;
            case 6:
                return RangeBar.SIXTH;
            case 7:
                return RangeBar.SEVENTH;
            case 8:
                return RangeBar.EIGHTH;
            case 9:
            default:
                return RangeBar.NINTH;
        }
    }



}
