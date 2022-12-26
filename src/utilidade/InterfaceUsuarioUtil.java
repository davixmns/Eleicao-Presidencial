package utilidade;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;


public class InterfaceUsuarioUtil {
    public static ImageIcon formatarFoto(ImageIcon foto, int compr, int altura) {
        Image imagem = foto.getImage();
        Image novaImagem = imagem.getScaledInstance(compr, altura, Image.SCALE_SMOOTH);
        return new ImageIcon(novaImagem);
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
            InputStream is = new ByteArrayInputStream(fotoBytes);
            BufferedImage bufImg = ImageIO.read(is);
            return new ImageIcon(bufImg);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}
