package com.educluster.mahen.ctrl;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JProgressBar;

/**
 *
 * @author Mahen Samaranayake
 */
public class Cartoonifier {
    
    String input_folder = "input";
    String output_folder = "output";
    
    public void cartoonifyAll(JProgressBar progressBar, JButton button) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                button.setEnabled(false);
                File folder = new File(input_folder);
                File[] files = folder.listFiles();
                int number_of_files = files.length;
                int progress = 0;
                for (File file : files) {
                    progress++;
                    progressBar.setValue((100 * progress) / number_of_files);
                    cartoonify(file.getName());
                }
                progressBar.setValue(0);
                button.setEnabled(true);
            }
        }).start();
    }
    
    public void cartoonify(String url) {
        try {
            BufferedImage bi = ImageIO.read(new File(input_folder + "\\" + url));
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
            }
            for (int i = 0; i < x; i++) {
                for (int j = 0; j < y; j++) {
                    Color col = new Color(bi.getRGB(i, j));
                    int red = col.getRed();
                    int green = col.getGreen();
                    int blue = col.getBlue();
                    Color color = Color.white;
                    int var = 30;
                    if (red < lines_r.get(i) - var || green < lines_g.get(i) - var || blue < lines_b.get(i) - var) {
                        color = Color.BLACK;
                    }
                    try {
                        bi.setRGB(i, j, color.getRGB());
                    } catch (Exception e) {
                    }
                }
            }
            ImageIO.write(bi, "jpg", new File(output_folder + "\\" + url));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
