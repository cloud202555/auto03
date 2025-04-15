package com.spring.jwt.utils;

import lombok.SneakyThrows;
import net.coobird.thumbnailator.Thumbnails;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.concurrent.CompletableFuture;

public class ImageCompressionUtil {

    private static final int TARGET_WIDTH = 800;
    private static final int TARGET_HEIGHT = 800;
    private static final float INITIAL_QUALITY = 0.9f;
    private static final float QUALITY_STEP = 0.1f;
    private static final long TARGET_SIZE_KB = 100;

    @SneakyThrows
    public static byte[] compressImage(byte[] originalImageBytes) throws IOException {
        if (originalImageBytes == null || originalImageBytes.length == 0) return null;

        ByteArrayInputStream inputStream = new ByteArrayInputStream(originalImageBytes);
        BufferedImage originalImage = ImageIO.read(inputStream);

        if (originalImage == null) {
            throw new IllegalArgumentException("Invalid image data");
        }

        BufferedImage resizedImage = resizeImage(originalImage, TARGET_WIDTH, TARGET_HEIGHT);

        return compressToTargetSize(resizedImage, TARGET_SIZE_KB);
    }

    private static BufferedImage resizeImage(BufferedImage originalImage, int targetWidth, int targetHeight) {
        try {
            return Thumbnails.of(originalImage)
                    .size(targetWidth, targetHeight)
                    .asBufferedImage();
        } catch (IOException e) {
            throw new RuntimeException("Failed to resize image", e);
        }
    }

    private static byte[] compressToTargetSize(BufferedImage image, long targetSizeKB) throws IOException {
        float quality = INITIAL_QUALITY;
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        while (quality >= 0.1f) {
            outputStream.reset();

            ImageWriter writer = ImageIO.getImageWritersByFormatName("jpg").next();
            ImageOutputStream ios = ImageIO.createImageOutputStream(outputStream);
            writer.setOutput(ios);

            ImageWriteParam param = writer.getDefaultWriteParam();
            param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
            param.setCompressionQuality(quality);

            writer.write(null, new IIOImage(image, null, null), param);
            writer.dispose();
            ios.close();

            long sizeInKB = outputStream.size() / 1024;
            if (sizeInKB <= targetSizeKB) {
                break;
            }

            quality -= QUALITY_STEP;
        }

        return outputStream.toByteArray();
    }

    public static CompletableFuture<byte[]> compressImageAsync(byte[] originalImageBytes) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                return compressImage(originalImageBytes);
            } catch (IOException e) {
                throw new RuntimeException("Failed to compress image", e);
            }
        });
    }
}