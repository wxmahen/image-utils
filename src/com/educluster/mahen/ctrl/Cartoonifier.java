
package com.educluster.mahen.ctrl;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;

/**
 *
 * @author Mahen Samaranayake
 */
public class Cartoonifier {

    public static void main(String[] args) {
        File folder = new File("a");
        File[] files = folder.listFiles();
        for (File file : files) {
            System.out.println(file.getName());
            convert(file.getName());
        }
    }
    
    public static void convert(String url){try {
            BufferedImage bi = ImageIO.read(new File("a\\"+url));
            int x = bi.getWidth();
            int y = bi.getHeight();
            List<Integer> lines_r = new ArrayList<>();
            List<Integer> lines_g = new ArrayList<>();
            List<Integer> lines_b = new ArrayList<>();
            for (int i = 0; i < x; i++) {
                int val_r = 0;
                int val_g = 0;
                int val_b = 0;
                for (int j = 0; j < y; j++) {
                    Color col = new Color(bi.getRGB(i, j));
                    int red = col.getRed();
                    int green = col.getGreen();
                    int blue = col.getBlue();
                    val_r += red;
                    val_g += green;
                    val_b += blue;
                }
                lines_r.add(val_r / y);
                lines_g.add(val_g / y);
                lines_b.add(val_b / y);
                System.out.println(1 + " - " + i);
            }
            for (int i = 0; i < x; i++) {
                for (int j = 0; j < y; j++) {
                    Color col = new Color(bi.getRGB(i, j));
                    int red = col.getRed();
                    int green = col.getGreen();
                    int blue = col.getBlue();
                    Color color = Color.white;
                    int var = 30;
                    if (red < lines_r.get(i)-var || green < lines_g.get(i)-var || blue < lines_b.get(i)-var) {
                        color = Color.BLACK;
                    }
                    try {
                        bi.setRGB(i,j, color.getRGB());
                    } catch (Exception e) {
                    }
                }
                System.out.println(2 + " - " + i);
            }
            ImageIO.write(bi, "jpg", new File("a\\"+"converted "+url));
        } catch (Exception e) {
            e.printStackTrace();
        }}
}
