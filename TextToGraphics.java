import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class TextToGraphics {
    
    public static void main(String[] args){
        System.out.println(args[0]);
        File file = new File(args[0]);
        try{
        BufferedReader br = new BufferedReader(new FileReader(file));
        StringBuilder sb = new StringBuilder();
        String line;
        while((line = br.readLine())!=null){

            sb.append(line).append("\n");
        }
        convert(sb.toString(),args[0]+"_img");
        System.out.println("Done.");
    }
    catch(FileNotFoundException e){
        e.printStackTrace();
    }

    }

    public static void convert(String text, String img_name) {
        String[] text_array = text.split("[\n]");
        BufferedImage img = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = img.createGraphics();
        Font font = new Font("Consolas", Font.BOLD, 12);
        g2d.setFont(font);
        FontMetrics fm = g2d.getFontMetrics();
        int width = fm.stringWidth(getLongestLine(text_array));
        int lines = getLineCount(text);
        int height = fm.getHeight() * (lines + 4);
        g2d.dispose();
        img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        g2d = img.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_ENABLE);
        g2d.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
        g2d.setFont(font);
        fm = g2d.getFontMetrics();
        g2d.setColor(Color.BLACK);

        for (int i = 1; i <= lines; ++i) {
            g2d.drawString(text_array[i - 1], 0, fm.getAscent() * i);
        }
        g2d.dispose();
        try {
            String img_path = System.getProperty("user.dir") + "/" + img_name + ".png";
            ImageIO.write(img, "png", new File(img_path));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static int getLineCount(String text) {
        return text.split("[\n]").length;
    }

    private static String getLongestLine(String[] arr) {
        String max = arr[0];
        for (int i = 1; i < arr.length; i++) {
            if (max.length() < arr[i].length()) {
                max = arr[i];
            }
        }
        return max;
    }
}
