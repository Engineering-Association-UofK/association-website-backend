package edu.uofk.ea.association_website_backend.service.certificates;

import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.interactive.digitalsignature.PDSignature;
import org.apache.pdfbox.pdmodel.interactive.digitalsignature.SignatureOptions;
import org.apache.pdfbox.pdmodel.interactive.digitalsignature.visible.PDVisibleSigProperties;
import org.apache.pdfbox.pdmodel.interactive.digitalsignature.visible.PDVisibleSignDesigner;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import org.apache.pdfbox.pdmodel.interactive.digitalsignature.SignatureInterface;
import org.bouncycastle.cert.jcajce.JcaCertStore;
import org.bouncycastle.cms.CMSProcessableByteArray;
import org.bouncycastle.cms.CMSSignedData;
import org.bouncycastle.cms.CMSSignedDataGenerator;
import org.bouncycastle.cms.jcajce.JcaSignerInfoGeneratorBuilder;
import org.bouncycastle.operator.ContentSigner;
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder;
import org.bouncycastle.operator.jcajce.JcaDigestCalculatorProviderBuilder;

@Service
public class PdfService {

    @Value("${app.keystore.password}")
    private String pin;
    @Value("${app.keystore.path}")
    private String keystorePath;

    public byte[] convertHtmlToPdf(String html) {
        try (ByteArrayOutputStream os = new ByteArrayOutputStream()) {
            PdfRendererBuilder builder = new PdfRendererBuilder();

            builder.withHtmlContent(html, null);
            builder.toStream(os);
            builder.run();

            return os.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException("Failed to render PDF", e);
        }
    }

    public enum Orientation {
        Landscape,
        Portrait
    }

    /// Signing a PDF works on three stages in this setup, first it
    /// Loads the keystore .p12 signature key from the specified location
    /// and loads the stamp image.
    ///
    /// The first step in this method is creating the designer options to set
    /// a fixed location, size, and image for the stamp. Then it configures
    /// the appearance properties (author details) and links them to the designer.
    ///
    /// After that it creates the signature dictionary which is the actual
    /// seal and will contain more metadata, Then it finalizes the Visibility options.
    ///
    /// The last step is using the inner class to actually create the signature,
    /// with the signature, the signature options, and designer options, everything
    /// is done and ready to be added to the PDF.
    ///
    /// No other operations should be done on the PDF content whatsoever as it will
    /// invalidate the signature and all this would be for nothing.
    public byte[] signPdf(byte[] pdfBytes, InputStream imageStream, Orientation orientation) {
        try (PDDocument document = PDDocument.load(pdfBytes);
             ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
             InputStream is = new FileInputStream(keystorePath)) {

            // Load the keystore
            KeyStore keystore = KeyStore.getInstance("PKCS12");
            keystore.load(is, pin.toCharArray());

            // Create and Configure the Designer
            // Note that these are points, not pixels. A4 is roughly 595x842.
            PDVisibleSignDesigner designer = new PDVisibleSignDesigner(document, imageStream, 1);
            designer.width(100).height(100);
            if (orientation == Orientation.Landscape) {
                designer.xAxis(632).yAxis(395).zoom(20);
            } else {
                designer.xAxis(445).yAxis(592).zoom(0);
            }

            // Configure Appearance Properties
            PDVisibleSigProperties options = new PDVisibleSigProperties();
            options.signerName("Engineering Association")
                    .signerLocation("University")
                    .signatureReason("Authenticity Verified")
                    .preferredSize(0)
                    .visualSignEnabled(true)
                    .setPdVisibleSignature(designer)
                    .buildSignature();

            // Create the Signature Dictionary
            PDSignature signature = new PDSignature();
            signature.setFilter(PDSignature.FILTER_ADOBE_PPKLITE);
            signature.setSubFilter(PDSignature.SUBFILTER_ADBE_PKCS7_DETACHED);
            signature.setName("Steering Engineering Association | University of Khartoum");
            signature.setLocation("University of Khartoum main campus");
            signature.setReason("Certificate Authenticity");
            signature.setSignDate(Calendar.getInstance());

            // Passing visible properties is done using a SignatureOptions
            SignatureOptions visibleOptions = new SignatureOptions();
            visibleOptions.setVisualSignature(options.getVisibleSignature());
            visibleOptions.setPage(0);

            // Apply the signature to the document
            document.addSignature(signature, new CreateSignature(keystore, pin), visibleOptions);

            document.saveIncremental(outputStream);
            return outputStream.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException("Digital Signing failed: " + e);
        }
    }

    // This is the "Engine" that does the actual math
    private static class CreateSignature implements SignatureInterface {
        private final PrivateKey privateKey;
        private final Certificate[] certificateChain;

        public CreateSignature(KeyStore keystore, String pin) throws Exception {
            String alias = keystore.aliases().nextElement();
            this.privateKey = (PrivateKey) keystore.getKey(alias, pin.toCharArray());
            this.certificateChain = keystore.getCertificateChain(alias);
        }

        @Override
        public byte[] sign(InputStream content) throws IOException {
            try {
                List<Certificate> certList = Arrays.asList(certificateChain);
                CMSSignedDataGenerator gen = new CMSSignedDataGenerator();
                ContentSigner sha256Signer = new JcaContentSignerBuilder("SHA256withRSA").build(privateKey);
                gen.addSignerInfoGenerator(new JcaSignerInfoGeneratorBuilder(new JcaDigestCalculatorProviderBuilder().build()).build(sha256Signer, (X509Certificate) certificateChain[0]));
                gen.addCertificates(new JcaCertStore(certList));
                CMSProcessableByteArray msg = new CMSProcessableByteArray(content.readAllBytes());
                CMSSignedData signedData = gen.generate(msg, false);
                return signedData.getEncoded();
            } catch (Exception e) {
                throw new IOException(e);
            }
        }
    }
}
