package proj.me.bitframe.helper;

public enum ColorCombination {
        VIBRANT_TO_MUTED(0),
        MUTED_TO_VIBRANT(1);
        final int colorCombo;
        ColorCombination(int colorCombo){
            this.colorCombo = colorCombo;
        }
    }