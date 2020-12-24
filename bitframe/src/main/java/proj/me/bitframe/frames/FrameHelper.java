package proj.me.bitframe.frames;

/**
 * Created by root on 20/7/17.
 */
public class FrameHelper {

    static class Frame{
        Image[] images; int cImg; int frameAvl; int rowAvl; int colAvl; int cFrm; int xW; int xH; int restc;
    }
    static class Image{
        int width;
        int height;
        String name;
        Image(int width, int height, String name){
            this.width = width;
            this.height = height;
            this.name = name;
        }
    }
    //can contain max 4 images
    static class Dimen{
        Image[] images;
        int last = 0;
        Dimen(int count){
            if(count > 4) count = 4;
            images = new Image[count];
        }
        void add(Image image){
            images[last++] = image;
        }
    }

    int m, x, y, z;
    Dimen[] dimen;
    public FrameHelper(int m, int x, int y, int z){
        this.m = m;
        this.x = x;
        this.y = y;
        this.z = z;
        dimen = new Dimen[6];
    }

    //priority -> HF, VF, HH, VH, HT, VT
    private void create(Image[] images, int cImg, int frameAvl, int rowAvl, int colAvl, int cFrm, int xW, int xH, int restc){
        int imageCount = images.length - cImg;
        if(imageCount == 0) return;
        if(imageCount > 0 && frameAvl == 0 || xW <= 0 || xH <= 0){
            System.out.println("failed");
            return;
        }

        int index = 0;
        boolean isFramed = false;
        //HF and other 3 -> 0100 = 4
        if((restc ^ (4 << (24 - 4 * index++))) > 0){

        }
        //VF and other 3 -> 1100 = 12
        if(!isFramed && (restc ^ (12 << (24 - 4 * index++))) > 0){

        }
        //HH -> 0010 = 2
        if(!isFramed && (restc ^ (2 << (24 - 4 * index++))) > 0){

        }
        //VH -> 1010 = 10
        if(!isFramed && (restc ^ (10 << (24 - 4 * index++))) > 0){

        }
        //HT -> 0001 = 1
        if(!isFramed && (restc ^ (1 << (24 - 4 * index++))) > 0){

        }
        //VT -> 1001 = 9
        if(!isFramed && (restc ^ (9 << (24 - 4 * index))) > 0){

        }

        //append restriction for next step
        //restriction 24 bit -> HF, VF, HH, VH, HT, VT
    }


    private Frame typeHF(Image[] images, int cImg, int frameAvl, int rowAvl, int colAvl, int cFrm, int xW, int xH, int restc){
        int imageCount = images.length - cImg;



        return new Frame();
    }


    private void createFrame(Image[] images, int index, Node root, int remainXW, int remainYH, int remainZW, int remainZH){
        
    }

}
