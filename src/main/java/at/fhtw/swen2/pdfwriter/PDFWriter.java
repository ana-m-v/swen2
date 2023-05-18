package at.fhtw.swen2.pdfwriter;

import com.itextpdf.kernel.pdf.*;
import com.itextpdf.layout.Document;
import com.itextpdf.kernel.pdf.PdfDocument;

import java.io.FileNotFoundException;

public class PDFWriter {
    //src/main/java/at/fhtw/swen2/pdfwriter/giveitaname.pdf
    String dest = "C:/itextExamples/sample.pdf";
    //creating a PDFWriter
    PdfWriter writer = new PdfWriter(dest);

    // Creating a PdfDocument
    PdfDocument pdfDoc = new PdfDocument(writer);

    // Adding an empty page
   // pdfDoc.addNewPage();
    // Creating a Document
    Document document = new Document(pdfDoc);

    public PDFWriter() throws FileNotFoundException {
    }
}
