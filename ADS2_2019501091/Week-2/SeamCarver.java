import java.util.Arrays;
import edu.princeton.cs.algs4.Picture;

public class SeamCarver {
    private Picture pic;
    private int w, h;
    
    // create a seam carver object based on the given picture
    public SeamCarver(Picture picture){
        if(picture == null){
            throw new IllegalArgumentException();
        }
        this.pic = picture;
        this.w = pic.width();
        this.h = pic.height();
    }
 
    // current picture
    public Picture picture(){
        return pic;
    }
 
    // width of current picture
    public int width(){
        return w;
    }
 
    // height of current picture
    public int height(){
        return h;
    }
 
    // energy of pixel at column x and row y
    public double energy(int x, int y){
        if(x < 0||y < 0||x >= w||y >= h){
            throw new IllegalArgumentException();
        }
        if(x == 0||y == 0|| x == w-1||y == h-1){
            return 1000;
        }
        int red_x = (pic.getRGB(x+1, y) >> 16 & 0xFF) - (pic.getRGB(x-1, y) >> 16 & 0xFF);
        int green_x = (pic.getRGB(x+1, y) >> 8 & 0xFF) - (pic.getRGB(x-1, y) >> 8 & 0xFF);
        int blue_x = (pic.getRGB(x+1, y) & 0xFF) - (pic.getRGB(x-1, y) & 0xFF);
        int delta_1 = (red_x * red_x) + (green_x * green_x) + (blue_x * blue_x);
        int red_y = (pic.getRGB(x, y+1) >> 16 & 0xFF) - (pic.getRGB(x, y-1) >> 16 & 0xFF);
        int green_y = (pic.getRGB(x, y+1) >> 8 & 0xFF) - (pic.getRGB(x, y-1) >> 8 & 0xFF);
        int blue_y = (pic.getRGB(x, y+1) & 0xFF) - (pic.getRGB(x, y-1) & 0xFF);
        int delta_2 = (red_y * red_y) + (green_y * green_y) + (blue_y * blue_y);
        double energy = Math.sqrt(delta_1 + delta_2);
        return energy;
    }

    private double[][] energyMat(){
        double[][] eneMat = new double[height()][width()];
        for(int i = 0; i<h; i++){
            for(int j = 0; j<w; j++){
                eneMat[i][j] = energy(j, i);
            }
        }
        return eneMat;
    }

    // sequence of indices for horizontal seam
    public int[] findHorizontalSeam(){

    }
 
    // sequence of indices for vertical seam
    public int[] findVerticalSeam(){
        
        for(int i = 1; i<h; i++){

        }
    }
 
    // remove horizontal seam from current picture
    public void removeHorizontalSeam(int[] seam){

    }
 
    // remove vertical seam from current picture
    public void removeVerticalSeam(int[] seam){

    }
 
    //  unit testing (optional)
    public static void main(String[] args){
    }
 
 }