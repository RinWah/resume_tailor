import net.sourceforge.tess4j.Tesseract;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.nio.file.Files;

@Service
public class PdfService {

    public String extractText(MultipartFile file) throws Exception {
        // 1. Try standard text extraction first
        try (PDDocument document = PDDocument.load(file.getInputStream())) {
            PDFTextStripper stripper = new PDFTextStripper();
            String text = stripper.getText(document);

            // If we got actual text, return it
            if (text != null && text.trim().length() > 100) {
                return text;
            }
        }

        // 2. Fallback to OCR if the PDF is an image/scan
        return performOcr(file);
    }

    private String performOcr(MultipartFile file) throws Exception {
        Tesseract tesseract = new Tesseract();
        
        // POINT THIS TO YOUR TESSERACT INSTALLATION
        // Windows example: "C:/Program Files/Tesseract-OCR/tessdata"
        // Mac/Linux: usually "/usr/local/share/tessdata"
        tesseract.setDatapath("YOUR_TESSDATA_PATH_HERE");

        // Temporary file for processing
        File tempFile = Files.createTempFile("ocr_", ".pdf").toFile();
        file.transferTo(tempFile);

        String result = tesseract.doOCR(tempFile);
        tempFile.delete(); // Clean up
        return result;
    }
}