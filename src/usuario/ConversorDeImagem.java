package usuario;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;


public class ConversorDeImagem {
    private static final int[] medidasFoto = {130, 180, 100};

    public static ImageIcon formatarImagem(ImageIcon img, int w, int h, int hints){
        img.setImage(img.getImage().getScaledInstance(w, h, hints));
        return img;
    }

    public static byte[] converterImagemParaBytes(String url) {
        try {
            File file = new File(url);
            BufferedImage bufferedImage = ImageIO.read(file);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ImageIO.write(bufferedImage, "jpg", byteArrayOutputStream);
            return byteArrayOutputStream.toByteArray();

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static ImageIcon converterBlobParaImagem(byte[] fotoBytes) {
        try {
            InputStream is = new java.io.ByteArrayInputStream(fotoBytes);
            BufferedImage bufImg = ImageIO.read(is);
            return formatarImagem(new ImageIcon(bufImg), medidasFoto[0], medidasFoto[1], medidasFoto[2]);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
