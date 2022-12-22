package com.lab.util;

import com.lab.bean.ImageFontText;
import com.lab.bean.ImageWatermark;
import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.filters.ImageFilter;
import net.coobird.thumbnailator.geometry.Coordinate;
import org.apache.commons.lang3.StringUtils;

import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Base64;
import java.util.List;

/**
 * @program: DataBoard
 * @className: ImageUtil
 * @description:
 * @author:
 * @create: 2022-12-21 15:29
 * @Version 1.0
 **/
public class ImageUtil {
    public static BufferedImage watermarkImgBase64(String base64, List<ImageWatermark> images, List<ImageFontText> texts) throws Exception {
        InputStream inputStream;
        inputStream = null;
        base64 = base64.replaceFirst("data:image\\/.*;base64,", "");

        Base64.Decoder decoder = Base64.getDecoder();
        try {
            byte[] bytes = decoder.decode(base64);
            ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
            inputStream = bais;
        } catch (Exception e) {
            e.printStackTrace();
        }

        BufferedImage destImage = ImageIO.read(inputStream);
        BufferedImage tempImage = null;
        int w1 = destImage.getWidth(), h1 = destImage.getHeight(), startX, startY, endX, endY;
        System.out.println("w1" + w1);
        System.out.println("h1" + h1);
        //水印位置
        Coordinate coordinate = null;
        //水印位置坐标左上、右下
        List<Integer> points = null;

        for (ImageWatermark imageWatermark : images) {
            inputStream = getInputStream(imageWatermark.getImageUrl());
            if (null == inputStream) {
                continue;
            }
            points = imageWatermark.getPoints();
            startX = new BigDecimal(points.get(0)).intValue();
            startY = new BigDecimal(points.get(1)).intValue();
            endX = new BigDecimal(points.get(2)).intValue();
            endY = new BigDecimal(points.get(3)).intValue();
            //设置水印位置
            coordinate = new Coordinate(startX, startY);
            tempImage = Thumbnails.of(ImageIO.read(inputStream)).size(endX - startX, endY - startY).keepAspectRatio(false).asBufferedImage();
//            tempImage = Thumbnails.of(ImageIO.read(inputStream)).size(180,180).keepAspectRatio(false).asBufferedImage();
//            destImage = Thumbnails.of(destImage).size(w1,h1).watermark(coordinate,tempImage,1f).asBufferedImage();
            destImage = Thumbnails.of(destImage).size(w1, h1).watermark(coordinate, tempImage, 1f).asBufferedImage();
        }

        for (ImageFontText fontText : texts) {
            startX = new BigDecimal(fontText.getStartX()).intValue();
            startY = new BigDecimal(fontText.getStartY()).intValue();
            destImage = mergeFontText(destImage, fontText, startX, startY);
        }
        destImage = Thumbnails.of(destImage).addFilter(new ThumbnailsImgFilter()).size(w1, h1).asBufferedImage();
        return destImage;
    }

    public static BufferedImage watermarkImg(String baseImgUrl, List<ImageWatermark> images, List<ImageFontText> texts) throws Exception {
        InputStream inputStream = getInputStream(baseImgUrl);
        if (null == inputStream) {
            throw new RuntimeException("海报图片生成失败");
        }
        BufferedImage destImage = ImageIO.read(inputStream);
        BufferedImage tempImage = null;
        int w1 = destImage.getWidth(), h1 = destImage.getHeight(), startX, startY, endX, endY;
        System.out.println("w1" + w1);
        System.out.println("h1" + h1);
        //水印位置
        Coordinate coordinate = null;
        //水印位置坐标左上、右下
        List<Integer> points = null;
        for (ImageWatermark imageWatermark : images) {
            inputStream = getInputStream(imageWatermark.getImageUrl());
            if (null == inputStream) {
                continue;
            }
            points = imageWatermark.getPoints();
            startX = new BigDecimal(points.get(0)).intValue();
            startY = new BigDecimal(points.get(1)).intValue();
            endX = new BigDecimal(points.get(2)).intValue();
            endY = new BigDecimal(points.get(3)).intValue();
            //设置水印位置
            coordinate = new Coordinate(startX, startY);
            tempImage = Thumbnails.of(ImageIO.read(inputStream)).size(endX - startX, endY - startY).keepAspectRatio(false).asBufferedImage();
//            tempImage = Thumbnails.of(ImageIO.read(inputStream)).size(180,180).keepAspectRatio(false).asBufferedImage();
//            destImage = Thumbnails.of(destImage).size(w1,h1).watermark(coordinate,tempImage,1f).asBufferedImage();
            destImage = Thumbnails.of(destImage).addFilter(new ThumbnailsImgFilter()).size(w1, h1).watermark(coordinate, tempImage, 1f).asBufferedImage();
        }

        for (ImageFontText fontText : texts) {
            startX = new BigDecimal(fontText.getStartX()).intValue();
            startY = new BigDecimal(fontText.getStartY()).intValue();
            destImage = mergeFontText(destImage, fontText, startX, startY);
        }
        return destImage;
    }

    private static BufferedImage mergeFontText(BufferedImage bufferedImage, ImageFontText fontText,
                                               int left, int top) throws Exception {
        Graphics2D g = bufferedImage.createGraphics();
        g.setColor(getColor(fontText.getTextColor()));

        Font font = new Font(fontText.getTextFont(), Font.BOLD, fontText.getTextSize());
        g.setFont(font);

        g.setBackground(Color.white);

        if (fontText.getStartX() == -1) {
            //昵称居中设置
            FontMetrics fmNick = g.getFontMetrics(font);
            int nickWidth = fmNick.stringWidth(fontText.getText());
            int nickWidthX = (bufferedImage.getWidth() - nickWidth) / 2;
            //绘制文字
            g.drawString(new String(fontText.getText().getBytes(), "utf-8"), nickWidthX, top);
        } else {
            g.drawString(new String(fontText.getText().getBytes(), "utf-8"), left, top);
        }
        g.dispose();
        return bufferedImage;
//        AttributedString ats = new AttributedString("我是\n小雨哈哈哈");
//        ats.addAttribute(TextAttribute.FOREGROUND, f, 0,2 );
//        AttributedCharacterIterator iter = ats.getIterator();
//        g.drawString(iter,left,top);
    }

    private static Color getColor(String color) {
        if (StringUtils.isBlank(color) || color.length() < 7) {
            return null;
        }
        try {
            int r = Integer.parseInt(color.substring(1, 3), 16);
            int g = Integer.parseInt(color.substring(3, 5), 16);
            int b = Integer.parseInt(color.substring(5), 16);
            return new Color(r, g, b);
        } catch (NumberFormatException nfe) {
            return null;
        }
    }

    public static InputStream getInputStream(String baseUrl) {
        if (StringUtils.isBlank(baseUrl)) {
            return null;
        }
        try {
            InputStream inputStream = new FileInputStream(baseUrl);
            return inputStream;
        } catch (IOException e) {
            e.printStackTrace();
        }
//        try {
//            URL url = new URL(baseUrl);
//            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//            connection.setConnectTimeout(6000);
//            connection.setReadTimeout(6000);
//            int code = connection.getResponseCode();
//            if (HttpURLConnection.HTTP_OK == code) {
//                return connection.getInputStream();
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        return null;
    }

    /**
     * 将透明背景设置为白色
     */
    public static class ThumbnailsImgFilter implements ImageFilter {

        @Override
        public BufferedImage apply(BufferedImage img) {
            int w = img.getWidth();
            int h = img.getHeight();
            BufferedImage newImage = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
            Graphics2D graphic = newImage.createGraphics();
            graphic.setColor(Color.white);//背景设置为白色
            graphic.fillRect(0, 0, w, h);
            graphic.drawRenderedImage(img, null);
            graphic.dispose();
            return newImage;
        }
    }
}
