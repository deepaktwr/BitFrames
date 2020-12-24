package proj.me.bitframe.frames.tournament;

/**
 * Created by root on 27/7/17.
 */
public enum RangeBar {
    FIRST(1),
    SECOND(2),
    THIRD(3),
    FOURTH(4),
    MID(5),
    SIXTH(6),
    SEVENTH(7),
    EIGHTH(8),
    NINTH(9);

    private int range;
    RangeBar(int range){
        this.range = range;
    }

    public int getRange(){
        return range;
    }
}
