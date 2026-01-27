package edu.uofk.ea.association_website_backend.service.certificates;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@Service
public class ZXingService { // QR Code generator

    public String generateQRCodeBase64(String data, int height, int width) {
        try {
            Map<EncodeHintType, Object> hints = new HashMap<>();
            hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H); // 30% recovery for logo placement
            hints.put(EncodeHintType.MARGIN, 0);

            BitMatrix matrix = new MultiFormatWriter().encode(
                    data,
                    BarcodeFormat.QR_CODE,
                    1,
                    1,
                    hints
            );

            // Load and process logo
            BufferedImage qrImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g = qrImage.createGraphics();

            g.setColor(Color.WHITE);
            g.fillRect(0, 0, width, height);

            Color color = new Color(5, 175, 218);
            g.setColor(color);

            int moduleSize = width / matrix.getWidth(); // Calculate how big one "dot" is
            int matrixWidthPixels = matrix.getWidth() * moduleSize;
            int matrixHeightPixels = matrix.getHeight() * moduleSize;
            int offsetX = (width - matrixWidthPixels) / 2;
            int offsetY = (height - matrixHeightPixels) / 2;

            // Loop through the modules (dots) addresses and draw Gears, skipping corners.
            for (int x = 0; x < matrix.getWidth(); x++) {
                for (int y = 0; y < matrix.getHeight(); y++) {
                    if (matrix.get(x, y)) {
                        int xPos = offsetX + (x * moduleSize);
                        int yPos = offsetY + (y * moduleSize);

                        if (isInsideEye(x, y, matrix.getWidth())) {
                            g.fillRect(xPos, yPos, moduleSize, moduleSize);
                        } else {
                            drawGear(g, xPos, yPos, moduleSize);
                        }
                    }
                }
            }

            // Load Logo and to inside the QR Code
            InputStream logoStream = getClass().getResourceAsStream("/logo.png");
            if (logoStream == null) throw new RuntimeException("Logo not found in resources!");
            BufferedImage logo = ImageIO.read(logoStream);

            // Calculate the center of the QR Code
            int logoWidth = qrImage.getWidth() / 5;
            int logoHeight = qrImage.getHeight() / 5;
            int x = (qrImage.getWidth() - logoWidth) / 2;
            int y = (qrImage.getHeight() - logoHeight) / 2;

            // Draw logo at the center
            g.drawImage(logo, x, y, logoWidth, logoHeight, null);
            g.dispose();

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ImageIO.write(qrImage, "PNG", outputStream);

            byte[] imageBytes = outputStream.toByteArray();
            return Base64.getEncoder().encodeToString(imageBytes);

        } catch (WriterException | IOException e) {
            throw new RuntimeException("Failed to generate QR Code", e);
        }
    }

    private void drawGear(Graphics2D g, int x, int y, int size) {
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int centerX = x + size / 2;
        int centerY = y + size / 2;
        int toothCount = 8;
        int toothLength = size / 6;
        int coreRadius = (size / 2) - (toothLength / 2);

        g2d.translate(centerX, centerY);

        // Draw Teeth
        g2d.setColor(Color.BLACK);
        double angleStep = (2 * Math.PI) / toothCount;
        for (int i = 0; i < toothCount; i++) {
            g2d.fillRect(-(size / 8), -(size / 2), size / 4, size);
            g2d.rotate(angleStep);
        }

        // Draw Body
        int coreDiameter = coreRadius * 2;
        g2d.fillOval(-coreRadius, -coreRadius, coreDiameter, coreDiameter);

        g2d.dispose();
    }

    // Leave Corners be to make sure QR Codes are readable
    private boolean isInsideEye(int x, int y, int gridSize) {
        // Basic logic to skip the three corner squares
        if (x < 7 && y < 7) return true; // Top Left
        if (x > gridSize - 8 && y < 7) return true; // Top Right
        if (x < 7 && y > gridSize - 8) return true; // Bottom Left
        return false;
    }
}
