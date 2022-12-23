package usuario;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ConversorDeImagem {
    private static final int[] medidasFoto = {130, 180, 100};

    public static ImageIcon formatarImagem(ImageIcon img, int w, int h, int hints){
        img.setImage(img.getImage().getScaledInstance(w, h, hints));
        return img;
    }

    public static byte[] converterImagemParaBytes(String url) throws IOException {
        File file = new File(url);
        BufferedImage bufferedImage = ImageIO.read(file);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ImageIO.write(bufferedImage, "jpg", byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    public static ImageIcon converterBlobParaImagem(ResultSet rs) throws SQLException, IOException {
        InputStream is = rs.getBinaryStream("foto");
        BufferedImage bufImg = ImageIO.read(is);
        return formatarImagem(new ImageIcon(bufImg), medidasFoto[0], medidasFoto[1], medidasFoto[2]);
    }
}
