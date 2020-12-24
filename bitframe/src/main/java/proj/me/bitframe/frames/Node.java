package proj.me.bitframe.frames;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Node {

    private int type;
    private Node[] subTypes;
    private int size;

    private boolean isRepeated;
    private int row;
    private int col;
    private int repeatSize;
    private Map<Integer, Set<SubMatrix>> subMatrixMap;

    Node(int type) {
        this.type = type;
        subTypes = new Node[8];
        subMatrixMap = new HashMap<>();
        assignRowAndCol(type);
    }

    Node setSubType(int type) {
        subTypes[size++] = new Node(type);
        return subTypes[size - 1];
    }

    void deleteSubType() {
        subTypes[--size] = null;
    }

    public void insertMatrixType() {
        int index = 0;

        while (index < subTypes.length) {
            if(subTypes[index] == null) break;
            Set<Integer> subMatrixSet = new HashSet<>();
            subMatrixSet.addAll(subTypes[index].subMatrixMap.keySet());

            if(subMatrixSet.size() == 0){
                int subMatrixType = rowColToMatrix(subTypes[index].row, subTypes[index].col);
                subMatrixSet.add(subMatrixType);
            }

            for (int subMatrixType : subMatrixSet) {
                int rowCol = matrixToRowCol(subMatrixType);
                int subRow = (rowCol >> 4) << 1;
                int subCol = rowCol << 5;

                int max = Math.max(subRow, subCol);

                int newRow = row, newCol = col;
                if (max == row) newCol = col + subCol;
                else newRow = row + subRow;
                int matrixType = rowColToMatrix(newRow, newCol);
                SubMatrix subMatrix = new SubMatrix(index, subMatrixType);
                if (!subMatrixMap.containsKey(matrixType)) subMatrixMap.put(matrixType, new HashSet<SubMatrix>());
                subMatrixMap.get(matrixType).add(subMatrix);
            }
            index++;
        }
    }

    public boolean setSubRepeating(Node node, int matrixType,  Set<SubMatrix> subMatrixSet){
        if(node.isRepeated || subMatrixSet == null){
            node.isRepeated = true;
            return true;
        }

        int rowCol = matrixToRowCol(matrixType);
        int subRow = (rowCol >> 4) << 1;
        int subCol = rowCol << 5;

        int max = Math.max(node.row, node.col);

        if (max == subRow) subCol = subCol - node.col;
        else subRow = subRow - node.row;

        matrixType = rowColToMatrix(subRow, subCol);

        SubMatrix subMatrix = null;
        for(SubMatrix subMatrix1 : subMatrixSet){
            if(subMatrix1.matrixType == matrixType) {
                subMatrix = subMatrix1;
                break;
            }
        }

        if(subMatrix == null) return false;

        Node subNode = node.subTypes[subMatrix.index];
        subMatrixSet = subNode.subMatrixMap.get(matrixType);
        if(setSubRepeating(subNode, matrixType, subMatrixSet)){
            node.repeatSize++;
            node.isRepeated = node.repeatSize == node.subMatrixMap.size();
        }

        return node.isRepeated;
    }

    private int rowColToMatrix(int row, int col){
        int offset = -1;
        switch (row) {
            case 1:
                offset = -1;
                break;
            case 2:
                offset = 3;
                break;
            case 3:
                offset = 7;
                break;
            case 4:
                offset = 11;
                break;
        }
        return 1 << (col + offset);
    }

    private int matrixToRowCol(int matrixType){
        int subRow = 0, subCol = 0;
        int offset = -1;

        while (subRow <= 4) {
            if ((matrixType & (15 << (4 * subRow))) != 0) {
                subRow++;
                int rowCol = matrixType >> (offset + 1);
                while ((rowCol >> subCol) != 1) subCol++;
                subCol++;
                break;
            }
            offset += 4;
            subRow++;
        }

        return ((8 | subRow) << 4) | subCol;
    }

    private void assignRowAndCol(int type) {
        switch (type) {
            case AvailableTypes.HF:
                row = 1;
                col = 1;
                break;
            case AvailableTypes.VF:
                row = 1;
                col = 1;
                break;
            case AvailableTypes.HH:
                row = 1;
                col = 2;
                break;
            case AvailableTypes.VH:
                row = 2;
                col = 1;
                break;
            case AvailableTypes.HT:
                row = 1;
                col = 3;
                break;
            case AvailableTypes.VT:
                row = 3;
                col = 1;
                break;
            case AvailableTypes.HQ:
                row = 1;
                col = 4;
                break;
            case AvailableTypes.VQ:
                row = 4;
                col = 1;
                break;
        }
    }

    public int getType() {
        return type;
    }

    public Node[] getSubTypes() {
        return subTypes;
    }

    public Set<Integer> getMatrixSet(){
        return subMatrixMap.keySet();
    }
    public Set<SubMatrix> getSubMatrixSet(int matrix){
        return subMatrixMap.get(matrix);
    }

    private static class SubMatrix {
        int index;
        int matrixType;

        SubMatrix(int index, int matrixType) {
            this.index = index;
            this.matrixType = matrixType;
        }
    }
}