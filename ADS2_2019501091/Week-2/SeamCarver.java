import java.util.Arrays;
import edu.princeton.cs.algs4.Picture;

public class SeamCarver {
    private Picture pic;
    
    // create a seam carver object based on the given picture
    public SeamCarver(Picture picture){
        if(picture == null){
            throw new IllegalArgumentException();
        }
        this.pic = picture;
    }
 
    // current picture
    public Picture picture(){
        return this.pic;
    }
 
    // width of current picture
    public int width(){
        return pic.width();
    }
 
    // height of current picture
    public int height(){
        return pic.height();
    }
 
    // energy of pixel at column x and row y
    public double energy(int x, int y){
        if(x < 0||y < 0||x > width()-1||y > height()-1){
            throw new IllegalArgumentException();
        }
        if(x == 0||y == 0|| x == width()-1||y == height()-1){
            return 1000;
        }
        double red_x = (pic.getRGB(x+1, y) >> 16 & 0xFF) - (pic.getRGB(x-1, y) >> 16 & 0xFF);
        double green_x = (pic.getRGB(x+1, y) >> 8 & 0xFF) - (pic.getRGB(x-1, y) >> 8 & 0xFF);
        double blue_x = (pic.getRGB(x+1, y) & 0xFF) - (pic.getRGB(x-1, y) & 0xFF);
        double delta_1 = (red_x * red_x) + (green_x * green_x) + (blue_x * blue_x);
        double red_y = (pic.getRGB(x, y+1) >> 16 & 0xFF) - (pic.getRGB(x, y-1) >> 16 & 0xFF);
        double green_y = (pic.getRGB(x, y+1) >> 8 & 0xFF) - (pic.getRGB(x, y-1) >> 8 & 0xFF);
        double blue_y = (pic.getRGB(x, y+1) & 0xFF) - (pic.getRGB(x, y-1) & 0xFF);
        double delta_2 = (red_y * red_y) + (green_y * green_y) + (blue_y * blue_y);
        double energy = Math.sqrt(delta_1 + delta_2);
        return energy;
    }

    private double[][] energyMat(){
        double[][] eneMat = new double[height()][width()];
        for(int i = 0; i<height(); i++){
            for(int j = 0; j<width(); j++){
                eneMat[i][j] = energy(j, i);
            }
        }
        return eneMat;
    }

    private double[][] transpose(double[][] energyM){
        double[][] tEneMat = new double[width()][height()];
        for(int i = 0; i<width(); i++){
            for(int j = 0; j<height(); j++){
                tEneMat[i][j] = energyM[j][i];
            }
        }
        return tEneMat;
    }

    private double[][] cumulative(double[][] energyM){
        for(int i = 1; i<energyM.length; i++){
            for(int j = 0; j<energyM[i].length; j++){
                if(j == 0){
                    energyM[i][j] += Math.min(energyM[i-1][j],energyM[i-1][j+1]);
                }
                else if(j == energyM[0].length-1){
                    energyM[i][j] += Math.min(energyM[i-1][j], energyM[i-1][j-1]);
                }
                else{
                    double min1 = Math.min(energyM[i-1][j],energyM[i-1][j+1]);
                    double min2 = Math.min(min1,energyM[i-1][j-1]);
                    energyM[i][j] += min2;
                }
            }
        }
        return energyM;
    }

    private static int getMinValue(double[] numbers){
        double minValue = numbers[0];
        int index = 0;
        for(int i=1;i<numbers.length;i++){
          if(numbers[i] < minValue){
            minValue = numbers[i];
            index = i;
          }
        }
        return index;
    }
      
    private int[] findSeam(double[][] matrix){
        int[] seam = new int[matrix.length];
        int j = getMinValue(matrix[matrix.length-1]);
        seam[matrix.length-1]  = j;
        for(int i = matrix.length-1; i>0; i--){
            if(j == 0){
                if(matrix[i-1][j+1]<matrix[i-1][j]){
                    seam[i-1] = j+1;
                    j = j+1;
                }
                else{
                    seam[i-1] = j;
                }
            }
            else if(j == matrix[i].length-1){
                if(matrix[i-1][j-1]<matrix[i-1][j]){
                    seam[i-1] = j-1;
                    j = j-1;
                }
                else{
                    seam[i-1] = j;
                }
            }
            else{
                if(matrix[i-1][j-1]<matrix[i-1][j] && matrix[i-1][j-1]<matrix[i-1][j+1]){
                    seam[i-1] = j-1;
                    j = j-1;
                }
                else if(matrix[i-1][j+1]<matrix[i-1][j-1] && matrix[i-1][j+1]<matrix[i-1][j]){
                    seam[i-1] = j+1;
                    j = j+1;
                }
                else if(matrix[i-1][j-1] == matrix[i-1][j] && matrix[i-1][j] == matrix[i-1][j+1]){
                    seam[i-1] = j-1;
                    j = j-1;
                }
                else{
                    seam[i-1] = j;
                }
            }
        }
        return seam;
    }

    // sequence of indices for horizontal seam
    public int[] findHorizontalSeam(){
        int[] i = new int[width()];
        if(width() == 1 || height() ==1){
            return i;
        }
        double[][] energyM = new double[height()][width()];
        energyM = energyMat(); 
        double[][] tEnergyM = new double[width()][height()];
        tEnergyM = transpose(energyM);
        double[][] hMat = new double[width()][height()];
        hMat = cumulative(tEnergyM);
        int[] hSeam = new int[width()];
        hSeam = findSeam(hMat);
        return hSeam;
    }
 
    // sequence of indices for vertical seam
    public int[] findVerticalSeam(){
        int[] i = new int[height()];
        if(width() == 1 || height() == 1){
            return i;
        }
        double[][] energyM = new double[height()][width()];
        energyM = energyMat(); 
        double[][] vMat = new double[height()][width()];
        vMat = cumulative(energyM);
        int[] vSeam = new int[height()];
        vSeam = findSeam(vMat);
        return vSeam;
    }
 
    // remove horizontal seam from current picture
    public void removeHorizontalSeam(int[] seam){
        if(seam == null  || seam.length != width() || height() <= 1){
            throw new IllegalArgumentException();
        }
        for(int i = 0; i<seam.length; i++){
            if(seam[i] < 0 || seam[i] >= height()){
                throw new IllegalArgumentException();
            }
        }
        for(int i = 0; i<seam.length - 1; i++){
            if(seam[i] < 0 || Math.abs(seam[i]-seam[i + 1]) > 1){
                throw new IllegalArgumentException();
            }
        }
        Picture rp = new Picture(width(),height()-1);
        for (int i = 0; i < rp.width(); i++) {
            for (int j = 0; j < rp.height();) {
                if (j >= seam[i]) {
                    rp.set(i, j, pic.get(i, ++j));
                } else {
                    rp.set(i, j, pic.get(i, j++));
                }
            }
        }
        pic = rp;
        rp = null;
    }
 
    // remove vertical seam from current picture
    public void removeVerticalSeam(int[] seam){
        if(seam == null  || seam.length != height() || width() <= 1){
            throw new IllegalArgumentException();
        }
        for(int i = 0; i<seam.length; i++){
            if(seam[i] <0 || seam[i] >= width()){
                throw new IllegalArgumentException();
            }
        }
        for(int i = 0; i < seam.length-1; i++){
            if(seam[i] < 0 || Math.abs(seam[i]-seam[i + 1]) > 1){
                throw new IllegalArgumentException();
            }
        }
        Picture rp = new Picture(width()-1,height());
        for (int i = 0; i < rp.height(); i++) {
            for (int j = 0; j < rp.width();) {
                if (j >= seam[i]) {
                    rp.set(j, i, pic.get(++j, i));
                } else {
                    rp.set(j, i, pic.get(j++, i));
                }
            }
        }
        pic = rp;
        rp = null;
    } 
}