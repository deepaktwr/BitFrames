package proj.me.bitframe.frames;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by root on 20/8/09.
 */
public class AvailableTypes {
    //max 8 types, so 8 bit structure
    public static final int HF = 128;//Integer.parseInt("10000000", 2);
    public static final int VF = 64;//Integer.parseInt("01000000", 2);

    public static final int HH = 32;//Integer.parseInt("00100000", 2);
    public static final int VH = 16;//Integer.parseInt("00010000", 2);

    public static final int HT = 8;//Integer.parseInt("00001000", 2);
    public static final int VT = 4;//Integer.parseInt("00000100", 2);

    public static final int HQ = 2;//Integer.parseInt("00000010", 2);
    public static final int VQ = 1;//Integer.parseInt("00000001", 2);

    public static final int TYPES = 8;
    public static final int HEIGHT = 6;
    public static int MAX_ROW_OR_COLUMN = 4;

    //8 bit restriction
    //minDiv can't be greater than imageCount
    //if only one row or col is available then the col or row will must have at least imageCount col or row respectively
    //row - v, col - h
    //hDiv == -1 -> coming after vertical placement
    //vDiv == -1 -> coming after horizontal placement
    private int restriction(Node node, int imageCount, int rowAvl, int colAvl, int hDiv, int vDiv){
        if(imageCount == 0 || rowAvl == 0 || colAvl == 0) return 0;

        int s = 0;
        switch(imageCount){
            case 1:
                node.setSubType(HF);
                node.setSubType(VF);
                s |=HF | VF;
                return s;
            case 2:
                if(vDiv == -1 && hDiv <= 1 && colAvl >= 2 && rowAvl >= 1){
                    node.setSubType(HH);
                    s |= HH;
                }
                if(hDiv == -1 && vDiv <= 1 && rowAvl >= 2 && colAvl >= 1){
                    node.setSubType(VH);
                    s |= VH;
                }
                return s;
            case 3:
                if(rowAvl == 1 && colAvl >= 3){
                    node.setSubType(HT);
                    s |= HT;
                } else if(colAvl == 1 && rowAvl >= 3){
                    node.setSubType(VT);
                    s |= VT;
                } else{
                    if((vDiv == -1 && hDiv <= 0 && rowAvl >= 2 && colAvl >= 2) || (hDiv == -1 && vDiv <= 1 && rowAvl >= 2 && colAvl >= 2)){
                        int next = restriction(node.setSubType(HF), imageCount - 1, rowAvl - 1, colAvl, 0, -1);
                        if (next != 0) s |= HF;
                        else node.deleteSubType();
                    }
                    if((hDiv == -1 && vDiv <= 0 && colAvl >= 2 && rowAvl >= 2) || (vDiv == -1 && hDiv <= 1 && colAvl >= 2 && rowAvl >= 2)){
                        int next = restriction(node.setSubType(VF), imageCount - 1, rowAvl, colAvl - 1, -1, 0);
                        if (next != 0) s |= VF;
                        else node.deleteSubType();
                    }

                    if(vDiv == -1 && hDiv <= 2 && colAvl >= 3 && rowAvl >= 1){
                        node.setSubType(HT);
                        s |= HT;
                    }
                    if(hDiv == -1 && vDiv <= 2 && rowAvl >= 3 && colAvl >= 1){
                        node.setSubType(VT);
                        s |= VT;
                    }
                }

                return s;
            case 4:
                if(rowAvl == 1 && colAvl == 4){
                    node.setSubType(HQ);
                    s |= HQ;
                } else if(colAvl == 1 && rowAvl == 4){
                    node.setSubType(VQ);
                    s |= VQ;
                } else{
                    if((vDiv == -1 && hDiv <= 0 && rowAvl >= 2 && colAvl >= 2) || (hDiv == -1 && vDiv <= 2 && rowAvl >= 2 && colAvl >= 2)) {
                        int next = restriction(node.setSubType(HF), imageCount - 1, rowAvl - 1, colAvl, 0, -1);
                        if (next != 0) s |= HF;
                        else node.deleteSubType();
                    }
                    if((hDiv == -1 && vDiv <= 0 && colAvl >= 2 && rowAvl >= 2) || (vDiv == -1 && hDiv <= 2 && colAvl >= 2 && rowAvl >= 2)) {
                        int next = restriction(node.setSubType(VF), imageCount - 1, rowAvl, colAvl - 1, -1, 0);
                        if (next != 0) s |= VF;
                        else node.deleteSubType();
                    }

                    if((vDiv == -1 && hDiv <= 1 && colAvl >= 2 && rowAvl >= 2) || (hDiv == -1 && vDiv <= 1 && rowAvl >= 2 && colAvl >= 2)){
                        int next = restriction(node.setSubType(HH), imageCount - 2, rowAvl - 1, colAvl, 1, -1);
                        if(next != 0) s |= HH;
                        else node.deleteSubType();
                    }
                    if((hDiv == -1 && vDiv <= 1 && rowAvl >= 2 && colAvl >= 2) || (vDiv == -1 && hDiv <= 1 && rowAvl >= 2 && colAvl >= 2)){
                        int next = restriction(node.setSubType(VH), imageCount - 2, rowAvl, colAvl - 1, -1, 1);
                        if(next != 0) s |= VH;
                        else node.deleteSubType();
                    }

                    if(vDiv == -1 && hDiv <= 3 && colAvl == 4 && rowAvl >= 1){
                        node.setSubType(HQ);
                        s |= HQ;
                    }
                    if(hDiv == -1 && vDiv <= 3 && rowAvl == 4 && colAvl >= 1){
                        node.setSubType(VQ);
                        s |= VQ;
                    }
                }

                return s;
            case 5:
                if((vDiv == -1 && hDiv <= 0 && rowAvl >= 2 && colAvl >= 2) || (hDiv == -1 && vDiv <= 3 && rowAvl >= 2 && colAvl >= 2)) {
                    int next = restriction(node.setSubType(HF), imageCount - 1, rowAvl - 1, colAvl, 0, -1);
                    if (next != 0) s |= HF;
                    else node.deleteSubType();
                }
                if((hDiv == -1 && vDiv <= 0 && colAvl >= 2 && rowAvl >= 2) || (vDiv == -1 && hDiv <= 3 && colAvl >= 2 && rowAvl >= 2)) {
                    int next = restriction(node.setSubType(VF), imageCount - 1, rowAvl, colAvl - 1, -1, 0);
                    if (next != 0) s |= VF;
                    else node.deleteSubType();
                }

                if((vDiv == -1 && hDiv <= 1 && colAvl >= 2 && rowAvl >= 2) || (hDiv == -1 && vDiv <= 1 && colAvl >= 2 && rowAvl >= 2)){
                    int next = restriction(node.setSubType(HH), imageCount - 2, rowAvl - 1, colAvl, 1, -1);
                    if(next != 0) s |= HH;
                    else node.deleteSubType();
                }
                if((vDiv == -1 && hDiv <= 1 && rowAvl >= 2 && colAvl >= 2) || (hDiv == -1 && vDiv <= 1 && rowAvl >= 2 && colAvl >= 2)){
                    int next = restriction(node.setSubType(VH), imageCount - 2, rowAvl, colAvl - 1, -1, 1);
                    if(next != 0) s |= VH;
                    else node.deleteSubType();
                }

                return s;
            case 6:
                if((vDiv == -1 && hDiv <= 0 && rowAvl >= 3 && colAvl >= 2) || (hDiv == -1 && vDiv <= 3 && rowAvl >= 3 && colAvl >= 2)) {
                    int next = restriction(node.setSubType(HF), imageCount - 1, rowAvl - 1, colAvl, 0, -1);
                    if (next != 0) s |= HF;
                    else node.deleteSubType();
                }
                if((hDiv == -1 && vDiv <= 0 && colAvl >= 3 && rowAvl >= 2) || (vDiv == -1 && hDiv <= 3 && colAvl >= 3 && rowAvl >= 2)) {
                    int next = restriction(node.setSubType(VF), imageCount - 1, rowAvl, colAvl - 1, -1, 0);
                    if (next != 0) s |= VF;
                    else node.deleteSubType();
                }

                if((vDiv == -1 && hDiv <= 1 && colAvl >= 2 && rowAvl >= 2) || (hDiv == -1 && vDiv <= 2 && colAvl >= 2 && rowAvl >= 2)){
                    int next = restriction(node.setSubType(HH), imageCount - 2, rowAvl - 1, colAvl, 1, -1);
                    if(next != 0) s |= HH;
                    else node.deleteSubType();
                }
                if((vDiv == -1 && hDiv <= 2 && rowAvl >= 2 && colAvl >= 2) || (hDiv == -1 && vDiv <= 1 && rowAvl >= 2 && colAvl >= 2)){
                    int next = restriction(node.setSubType(VH), imageCount - 2, rowAvl, colAvl - 1, -1, 1);
                    if(next != 0) s |= VH;
                    else node.deleteSubType();
                }

                if((vDiv == -1 && hDiv <= 2 && colAvl >= 3 && rowAvl >= 2) || (hDiv == -1 && vDiv <= 1 && colAvl >= 3 && rowAvl >= 2)){
                    int next = restriction(node.setSubType(HT), imageCount - 3, rowAvl - 1, colAvl, 2, -1);
                    if(next != 0) s |= HT;
                    else node.deleteSubType();
                }
                if((vDiv == -1 && hDiv <= 1 && rowAvl >= 3 && colAvl >=  2) || (hDiv == -1 && vDiv <= 2 && rowAvl >= 3 && colAvl >= 2)){
                    int next = restriction(node.setSubType(VT), imageCount - 3, rowAvl, colAvl - 1, -1, 2);
                    if(next != 0) s |= VT;
                    else node.deleteSubType();
                }

                return s;
            case 7:
                if((vDiv == -1 && hDiv <= 0 && rowAvl >= 3 && colAvl >= 2) || (hDiv == -1 && vDiv <= 3 && rowAvl >= 3 && colAvl >= 2)) {
                    int next = restriction(node.setSubType(HF), imageCount - 1, rowAvl - 1, colAvl, 0, -1);
                    if (next != 0) s |= HF;
                    else node.deleteSubType();
                }
                if((hDiv == -1 && vDiv <= 0 && colAvl >= 3 && rowAvl >= 2) || (vDiv == -1 && hDiv <= 3 && colAvl >= 3 && rowAvl >= 2)) {
                    int next = restriction(node.setSubType(VF), imageCount - 1, rowAvl, colAvl - 1, -1, 0);
                    if (next != 0) s |= VF;
                    else node.deleteSubType();
                }

                if((vDiv == -1 && hDiv <= 1 && colAvl >= 2 && rowAvl >= 3) || (hDiv == -1 && vDiv <= 2 && colAvl >= 2 && rowAvl >= 3)){
                    int next = restriction(node.setSubType(HH), imageCount - 2, rowAvl - 1, colAvl, 1, -1);
                    if(next != 0) s |= HH;
                    else node.deleteSubType();
                }
                if((vDiv == -1 && hDiv <= 2 && rowAvl >= 2 && colAvl >= 3) || (hDiv == -1 && vDiv <= 1 && rowAvl >= 2 && colAvl >= 3)){
                    int next = restriction(node.setSubType(VH), imageCount - 2, rowAvl, colAvl - 1, -1, 1);
                    if(next != 0) s |= VH;
                    else node.deleteSubType();
                }

                if((vDiv == -1 && hDiv <= 2 && colAvl >= 3 && rowAvl >= 2) || (hDiv == -1 && vDiv <= 1 && colAvl >= 3 && rowAvl >= 2)){
                    int next = restriction(node.setSubType(HT), imageCount - 3, rowAvl - 1, colAvl, 2, -1);
                    if(next != 0) s |= HT;
                    else node.deleteSubType();
                }
                if((vDiv == -1 && hDiv <= 1 && rowAvl >= 3 && colAvl >=  2) || (hDiv == -1 && vDiv <= 2 && rowAvl >= 3 && colAvl >= 2)){
                    int next = restriction(node.setSubType(VT), imageCount - 3, rowAvl, colAvl - 1, -1, 2);
                    if(next != 0) s |= VT;
                    else node.deleteSubType();
                }

                return s;
            case 8:
                if((vDiv == -1 && hDiv <= 0 && rowAvl >= 3 && colAvl >= 2) || (hDiv == -1 && vDiv <= 3 && rowAvl == 4 && colAvl >= 2)) {
                    int next = restriction(node.setSubType(HF), imageCount - 1, rowAvl - 1, colAvl, 0, -1);
                    if (next != 0) s |= HF;
                    else node.deleteSubType();
                }
                if((hDiv == -1 && vDiv <= 0 && colAvl >= 3 && rowAvl >= 2) || (vDiv == -1 && hDiv <= 3 && colAvl == 4 && rowAvl >= 2)) {
                    int next = restriction(node.setSubType(VF), imageCount - 1, rowAvl, colAvl - 1, -1, 0);
                    if (next != 0) s |= VF;
                    else node.deleteSubType();
                }

                if((vDiv == -1 && hDiv <= 1 && colAvl >= 2 && rowAvl >= 3) || (hDiv == -1 && vDiv <= 2 && colAvl >= 2 && rowAvl >= 3)){
                    int next = restriction(node.setSubType(HH), imageCount - 2, rowAvl - 1, colAvl, 1, -1);
                    if(next != 0) s |= HH;
                    else node.deleteSubType();
                }
                if((vDiv == -1 && hDiv <= 2 && rowAvl >= 2 && colAvl >= 3) || (hDiv == -1 && vDiv <= 1 && rowAvl >= 2 && colAvl >= 3)){
                    int next = restriction(node.setSubType(VH), imageCount - 2, rowAvl, colAvl - 1, -1, 1);
                    if(next != 0) s |= VH;
                    else node.deleteSubType();
                }

                if((vDiv == -1 && hDiv <= 2 && colAvl >= 3 && rowAvl >= 3) || (hDiv == -1 && vDiv <= 1 && colAvl >= 3 && rowAvl >= 3)){
                    int next = restriction(node.setSubType(HT), imageCount - 3, rowAvl - 1, colAvl, 2, -1);
                    if(next != 0) s |= HT;
                    else node.deleteSubType();
                }
                if((vDiv == -1 && hDiv <= 1 && rowAvl >= 3 && colAvl >=  3) || (hDiv == -1 && vDiv <= 2 && rowAvl >= 3 && colAvl >= 3)){
                    int next = restriction(node.setSubType(VT), imageCount - 3, rowAvl, colAvl - 1, -1, 2);
                    if(next != 0) s |= VT;
                    else node.deleteSubType();
                }

                if(vDiv == -1 && hDiv <= 3 && colAvl == 4 && rowAvl >= 2){
                    int next = restriction(node.setSubType(HQ), imageCount - 4, rowAvl - 1, colAvl, 3, -1);
                    if(next != 0) s |= HQ;
                    else node.deleteSubType();
                }
                if(hDiv == -1 && vDiv <= 3 && rowAvl == 4 && colAvl >= 2){
                    int next = restriction(node.setSubType(VQ), imageCount - 4, rowAvl, colAvl - 1, -1, 3);
                    if(next != 0) s |= VQ;
                    else node.deleteSubType();
                }

                return s;
            case 9:
                if((vDiv == -1 && hDiv <= 0 && rowAvl >= 3 && colAvl >= 2) || (hDiv == -1 && vDiv <= 3 && rowAvl == 4 && colAvl >= 2)) {
                    int next = restriction(node.setSubType(HF), imageCount - 1, rowAvl - 1, colAvl, 0, -1);
                    if (next != 0) s |= HF;
                    else node.deleteSubType();
                }
                if((hDiv == -1 && vDiv <= 0 && colAvl >= 3 && rowAvl >= 2) || (vDiv == -1 && hDiv <= 3 && colAvl == 4 && rowAvl >= 2)) {
                    int next = restriction(node.setSubType(VF), imageCount - 1, rowAvl, colAvl - 1, -1, 0);
                    if (next != 0) s |= VF;
                    else node.deleteSubType();
                }

                if((vDiv == -1 && hDiv <= 1 && colAvl >= 2 && rowAvl >= 3) || (hDiv == -1 && vDiv <= 3 && colAvl >= 2 && rowAvl == 4)){
                    int next = restriction(node.setSubType(HH), imageCount - 2, rowAvl - 1, colAvl, 1, -1);
                    if(next != 0) s |= HH;
                    else node.deleteSubType();
                }
                if((vDiv == -1 && hDiv <= 3 && rowAvl >= 2 && colAvl == 4) || (hDiv == -1 && vDiv <= 1 && rowAvl >= 2 && colAvl >= 3)){
                    int next = restriction(node.setSubType(VH), imageCount - 2, rowAvl, colAvl - 1, -1, 1);
                    if(next != 0) s |= VH;
                    else node.deleteSubType();
                }

                if((vDiv == -1 && hDiv <= 2 && colAvl >= 3 && rowAvl >= 3) || (hDiv == -1 && vDiv <= 2 && colAvl >= 3 && rowAvl >= 3)){
                    int next = restriction(node.setSubType(HT), imageCount - 3, rowAvl - 1, colAvl, 2, -1);
                    if(next != 0) s |= HT;
                    else node.deleteSubType();
                }
                if((vDiv == -1 && hDiv <= 2 && rowAvl >= 3 && colAvl >=  3) || (hDiv == -1 && vDiv <= 2 && rowAvl >= 3 && colAvl >= 3)){
                    int next = restriction(node.setSubType(VT), imageCount - 3, rowAvl, colAvl - 1, -1, 2);
                    if(next != 0) s |= VT;
                    else node.deleteSubType();
                }

                if(vDiv == -1 && hDiv <= 3 && colAvl == 4 && rowAvl >= 3){
                    int next = restriction(node.setSubType(HQ), imageCount - 4, rowAvl - 1, colAvl, 3, -1);
                    if(next != 0) s |= HQ;
                    else node.deleteSubType();
                }
                if(hDiv == -1 && vDiv <= 3 && rowAvl == 4 && colAvl >= 3){
                    int next = restriction(node.setSubType(VQ), imageCount - 4, rowAvl, colAvl - 1, -1, 3);
                    if(next != 0) s |= VQ;
                    else node.deleteSubType();
                }

                return s;
            case 10:
                if((vDiv == -1 && hDiv <= 0 && rowAvl == 4 && colAvl >= 2) || (hDiv == -1 && vDiv <= 3 && rowAvl == 4 && colAvl >= 2)) {
                    int next = restriction(node.setSubType(HF), imageCount - 1, rowAvl - 1, colAvl, 0, -1);
                    if (next != 0) s |= HF;
                    else node.deleteSubType();
                }
                if((hDiv == -1 && vDiv <= 0 && colAvl == 4 && rowAvl >= 2) || (vDiv == -1 && hDiv <= 3 && colAvl == 4 && rowAvl >= 2)) {
                    int next = restriction(node.setSubType(VF), imageCount - 1, rowAvl, colAvl - 1, -1, 0);
                    if (next != 0) s |= VF;
                    else node.deleteSubType();
                }

                if((vDiv == -1 && hDiv <= 1 && colAvl >= 2 && rowAvl >= 3) || (hDiv == -1 && vDiv <= 3 && colAvl >= 2 && rowAvl == 4)){
                    int next = restriction(node.setSubType(HH), imageCount - 2, rowAvl - 1, colAvl, 1, -1);
                    if(next != 0) s |= HH;
                    else node.deleteSubType();
                }
                if((vDiv == -1 && hDiv <= 3 && rowAvl >= 2 && colAvl == 4) || (hDiv == -1 && vDiv <= 1 && rowAvl >= 2 && colAvl >= 3)){
                    int next = restriction(node.setSubType(VH), imageCount - 2, rowAvl, colAvl - 1, -1, 1);
                    if(next != 0) s |= VH;
                    else node.deleteSubType();
                }

                if((vDiv == -1 && hDiv <= 2 && colAvl >= 3 && rowAvl >= 3) || (hDiv == -1 && vDiv <= 2 && colAvl >= 3 && rowAvl == 4)){
                    int next = restriction(node.setSubType(HT), imageCount - 3, rowAvl - 1, colAvl, 2, -1);
                    if(next != 0) s |= HT;
                    else node.deleteSubType();
                }
                if((vDiv == -1 && hDiv <= 2 && rowAvl >= 3 && colAvl ==  4) || (hDiv == -1 && vDiv <= 2 && rowAvl >= 3 && colAvl >= 3)){
                    int next = restriction(node.setSubType(VT), imageCount - 3, rowAvl, colAvl - 1, -1, 2);
                    if(next != 0) s |= VT;
                    else node.deleteSubType();
                }

                if(vDiv == -1 && hDiv <= 3 && colAvl == 4 && rowAvl >= 3){
                    int next = restriction(node.setSubType(HQ), imageCount - 4, rowAvl - 1, colAvl, 3, -1);
                    if(next != 0) s |= HQ;
                    else node.deleteSubType();
                }
                if(hDiv == -1 && vDiv <= 3 && rowAvl == 4 && colAvl >= 3){
                    int next = restriction(node.setSubType(VQ), imageCount - 4, rowAvl, colAvl - 1, -1, 3);
                    if(next != 0) s |= VQ;
                    else node.deleteSubType();
                }

                return s;
            case 11:
                if(vDiv == -1 && hDiv <= 0 && rowAvl == 4 && colAvl >= 2) {
                    int next = restriction(node.setSubType(HF), imageCount - 1, rowAvl - 1, colAvl, 0, -1);
                    if (next != 0) s |= HF;
                    else node.deleteSubType();
                }
                if(hDiv == -1 && vDiv <= 0 && colAvl == 4 && rowAvl >= 2) {
                    int next = restriction(node.setSubType(VF), imageCount - 1, rowAvl, colAvl - 1, -1, 0);
                    if (next != 0) s |= VF;
                    else node.deleteSubType();
                }

                if((vDiv == -1 && hDiv <= 1 && colAvl >= 2 && rowAvl == 4) || (hDiv == -1 && vDiv <= 3 && colAvl >= 2 && rowAvl == 4)){
                    int next = restriction(node.setSubType(HH), imageCount - 2, rowAvl - 1, colAvl, 1, -1);
                    if(next != 0) s |= HH;
                    else node.deleteSubType();
                }
                if((vDiv == -1 && hDiv <= 3 && rowAvl >= 2 && colAvl == 4) || (hDiv == -1 && vDiv <= 1 && rowAvl >= 2 && colAvl == 4)){
                    int next = restriction(node.setSubType(VH), imageCount - 2, rowAvl, colAvl - 1, -1, 1);
                    if(next != 0) s |= VH;
                    else node.deleteSubType();
                }

                if((vDiv == -1 && hDiv <= 2 && colAvl >= 3 && rowAvl >= 3) || (hDiv == -1 && vDiv <= 2 && colAvl >= 3 && rowAvl == 4)){
                    int next = restriction(node.setSubType(HT), imageCount - 3, rowAvl - 1, colAvl, 2, -1);
                    if(next != 0) s |= HT;
                    else node.deleteSubType();
                }
                if((vDiv == -1 && hDiv <= 2 && rowAvl >= 3 && colAvl ==  4) || (hDiv == -1 && vDiv <= 2 && rowAvl >= 3 && colAvl >= 3)){
                    int next = restriction(node.setSubType(VT), imageCount - 3, rowAvl, colAvl - 1, -1, 2);
                    if(next != 0) s |= VT;
                    else node.deleteSubType();
                }

                if(vDiv == -1 && hDiv <= 3 && colAvl == 4 && rowAvl >= 3){
                    int next = restriction(node.setSubType(HQ), imageCount - 4, rowAvl - 1, colAvl, 3, -1);
                    if(next != 0) s |= HQ;
                    else node.deleteSubType();
                }
                if(hDiv == -1 && vDiv <= 3 && rowAvl == 4 && colAvl >= 3){
                    int next = restriction(node.setSubType(VQ), imageCount - 4, rowAvl, colAvl - 1, -1, 3);
                    if(next != 0) s |= VQ;
                    else node.deleteSubType();
                }

                return s;
            case 12:
                if(vDiv == -1 && hDiv <= 0 && rowAvl == 4 && colAvl >= 2) {
                    int next = restriction(node.setSubType(HF), imageCount - 1, rowAvl - 1, colAvl, 0, -1);
                    if (next != 0) s |= HF;
                    else node.deleteSubType();
                }
                if(hDiv == -1 && vDiv <= 0 && colAvl == 4 && rowAvl >= 2) {
                    int next = restriction(node.setSubType(VF), imageCount - 1, rowAvl, colAvl - 1, -1, 0);
                    if (next != 0) s |= VF;
                    else node.deleteSubType();
                }


                if(vDiv == -1 && hDiv <= 1 && colAvl >= 2 && rowAvl == 4){
                    int next = restriction(node.setSubType(HH), imageCount - 2, rowAvl - 1, colAvl, 1, -1);
                    if(next != 0) s |= HH;
                    else node.deleteSubType();
                }
                if(hDiv == -1 && vDiv <= 1 && rowAvl >= 2 && colAvl == 4){
                    int next = restriction(node.setSubType(VH), imageCount - 2, rowAvl, colAvl - 1, -1, 1);
                    if(next != 0) s |= VH;
                    else node.deleteSubType();
                }

                if((vDiv == -1 && hDiv <= 2 && colAvl >= 3 && rowAvl == 4) || (hDiv == -1 && vDiv <= 3 && colAvl >= 3 && rowAvl == 4)){
                    int next = restriction(node.setSubType(HT), imageCount - 3, rowAvl - 1, colAvl, 2, -1);
                    if(next != 0) s |= HT;
                    else node.deleteSubType();
                }
                if((vDiv == -1 && hDiv <= 3 && rowAvl >= 3 && colAvl ==  4) || (hDiv == -1 && vDiv <= 2 && rowAvl >= 3 && colAvl == 4)){
                    int next = restriction(node.setSubType(VT), imageCount - 3, rowAvl, colAvl - 1, -1, 2);
                    if(next != 0) s |= VT;
                    else node.deleteSubType();
                }

                if(vDiv == -1 && hDiv <= 3 && colAvl == 4 && rowAvl >= 3){
                    int next = restriction(node.setSubType(HQ), imageCount - 4, rowAvl - 1, colAvl, 3, -1);
                    if(next != 0) s |= HQ;
                    else node.deleteSubType();
                }
                if(hDiv == -1 && vDiv <= 3 && rowAvl == 4 && colAvl >= 3){
                    int next = restriction(node.setSubType(VQ), imageCount - 4, rowAvl, colAvl - 1, -1, 3);
                    if(next != 0) s |= VQ;
                    else node.deleteSubType();
                }

                return s;
            case 13:
                if(vDiv == -1 && hDiv <= 0 && rowAvl == 4 && colAvl >= 2) {
                    int next = restriction(node.setSubType(HF), imageCount - 1, rowAvl - 1, colAvl, 0, -1);
                    if (next != 0) s |= HF;
                    else node.deleteSubType();
                }
                if(hDiv == -1 && vDiv <= 0 && colAvl == 4 && rowAvl >= 2) {
                    int next = restriction(node.setSubType(VF), imageCount - 1, rowAvl, colAvl - 1, -1, 0);
                    if (next != 0) s |= VF;
                    else node.deleteSubType();
                }

                if(vDiv == -1 && hDiv <= 1 && colAvl >= 2 && rowAvl == 4){
                    int next = restriction(node.setSubType(HH), imageCount - 2, rowAvl - 1, colAvl, 1, -1);
                    if(next != 0) s |= HH;
                    else node.deleteSubType();
                }
                if(hDiv == -1 && vDiv <= 1 && rowAvl >= 2 && colAvl == 4){
                    int next = restriction(node.setSubType(VH), imageCount - 2, rowAvl, colAvl - 1, -1, 1);
                    if(next != 0) s |= VH;
                    else node.deleteSubType();
                }

                if(vDiv == -1 && hDiv <= 2 && colAvl >= 3 && rowAvl == 4){
                    int next = restriction(node.setSubType(HT), imageCount - 3, rowAvl - 1, colAvl, 2, -1);
                    if(next != 0) s |= HT;
                    else node.deleteSubType();
                }
                if(hDiv == -1 && vDiv <= 2 && rowAvl >= 3 && colAvl == 4){
                    int next = restriction(node.setSubType(VT), imageCount - 3, rowAvl, colAvl - 1, -1, 2);
                    if(next != 0) s |= VT;
                    else node.deleteSubType();
                }

                if(vDiv == -1 && hDiv <= 3 && colAvl == 4 && rowAvl == 4){
                    int next = restriction(node.setSubType(HQ), imageCount - 4, rowAvl - 1, colAvl, 3, -1);
                    if(next != 0) s |= HQ;
                    else node.deleteSubType();
                }
                if(hDiv == -1 && vDiv <= 3 && rowAvl == 4 && colAvl == 4){
                    int next = restriction(node.setSubType(VQ), imageCount - 4, rowAvl, colAvl - 1, -1, 3);
                    if(next != 0) s |= VQ;
                    else node.deleteSubType();
                }

                return s;
            case 14:
                if(vDiv == -1 && hDiv <= 1 && colAvl >= 2 && rowAvl == 4){
                    int next = restriction(node.setSubType(HH), imageCount - 2, rowAvl - 1, colAvl, 1, -1);
                    if(next != 0) s |= HH;
                    else node.deleteSubType();
                }
                if(hDiv == -1 && vDiv <= 1 && rowAvl >= 2 && colAvl == 4){
                    int next = restriction(node.setSubType(VH), imageCount - 2, rowAvl, colAvl - 1, -1, 1);
                    if(next != 0) s |= VH;
                    else node.deleteSubType();
                }

                if(vDiv == -1 && hDiv <= 2 && colAvl >= 3 && rowAvl == 4){
                    int next = restriction(node.setSubType(HT), imageCount - 3, rowAvl - 1, colAvl, 2, -1);
                    if(next != 0) s |= HT;
                    else node.deleteSubType();
                }
                if(hDiv == -1 && vDiv <= 2 && rowAvl >= 3 && colAvl == 4){
                    int next = restriction(node.setSubType(VT), imageCount - 3, rowAvl, colAvl - 1, -1, 2);
                    if(next != 0) s |= VT;
                    else node.deleteSubType();
                }

                if(vDiv == -1 && hDiv <= 3 && colAvl == 4 && rowAvl == 4){
                    int next = restriction(node.setSubType(HQ), imageCount - 4, rowAvl - 1, colAvl, 3, -1);
                    if(next != 0) s |= HQ;
                    else node.deleteSubType();
                }
                if(hDiv == -1 && vDiv <= 3 && rowAvl == 4 && colAvl == 4){
                    int next = restriction(node.setSubType(VQ), imageCount - 4, rowAvl, colAvl - 1, -1, 3);
                    if(next != 0) s |= VQ;
                    else node.deleteSubType();
                }

                return s;
            case 15:
                if(vDiv == -1 && hDiv <= 2 && colAvl >= 3 && rowAvl == 4){
                    int next = restriction(node.setSubType(HT), imageCount - 3, rowAvl - 1, colAvl, 2, -1);
                    if(next != 0) s |= HT;
                    else node.deleteSubType();
                }
                if(hDiv == -1 && vDiv <= 2 && rowAvl >= 3 && colAvl == 4){
                    int next = restriction(node.setSubType(VT), imageCount - 3, rowAvl, colAvl - 1, -1, 2);
                    if(next != 0) s |= VT;
                    else node.deleteSubType();
                }

                if(vDiv == -1 && hDiv <= 3 && colAvl == 4 && rowAvl == 4){
                    int next = restriction(node.setSubType(HQ), imageCount - 4, rowAvl - 1, colAvl, 3, -1);
                    if(next != 0) s |= HQ;
                    else node.deleteSubType();
                }
                if(hDiv == -1 && vDiv <= 3 && rowAvl == 4 && colAvl == 4){
                    int next = restriction(node.setSubType(VQ), imageCount - 4, rowAvl, colAvl - 1, -1, 3);
                    if(next != 0) s |= VQ;
                    else node.deleteSubType();
                }

                return s;
            case 16:
                if(vDiv == -1 && hDiv <= 3 && colAvl == 4 && rowAvl == 4){
                    int next = restriction(node.setSubType(HQ), imageCount - 4, rowAvl - 1, colAvl, 3, -1);
                    if(next != 0) s |= HQ;
                    else node.deleteSubType();
                }
                if(hDiv == -1 && vDiv <= 3 && rowAvl == 4 && colAvl == 4){
                    int next = restriction(node.setSubType(VQ), imageCount - 4, rowAvl, colAvl - 1, -1, 3);
                    if(next != 0) s |= VQ;
                    else node.deleteSubType();
                }

                return s;
        }
        return s;
    }

    void setRepetation(IndexQueue<Node> queue, int startIndex, int lastIndex){
        if(startIndex > lastIndex) return;

        for(int index = startIndex;index <= lastIndex;index++){
            Node node = queue.itemAt(index);

            for(Node sub : node.getSubTypes()){
                if(sub == null) break;
                queue.enqueue(sub);
            }

        }
        setRepetation(queue, lastIndex + 1, queue.currentFront());

        Set<Integer> matrixTypes = new HashSet<>();
        for(int index = startIndex;index <= lastIndex;index++) {
            Node node = queue.itemAt(index);
            node.insertMatrixType();
            for(int matrixType : node.getMatrixSet()){
                if(matrixTypes.contains(matrixType)) node.setSubRepeating(node, matrixType, node.getSubMatrixSet(matrixType));
                else matrixTypes.add(matrixType);
            }
        }
    }
}
