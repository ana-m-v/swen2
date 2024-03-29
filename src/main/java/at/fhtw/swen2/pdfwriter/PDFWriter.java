package at.fhtw.swen2.pdfwriter;

import at.fhtw.swen2.model.TourDTO;
import at.fhtw.swen2.model.TourLogDTO;

import at.fhtw.swen2.presentation.viewmodel.TourLogListViewModel;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.Document;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.element.*;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.itextpdf.layout.element.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import org.springframework.stereotype.Component;
import java.io.FileNotFoundException;
import java.util.ArrayList;

@Component
public class PDFWriter {

    String path = "pdfwriter/pdfcreated/";
    private ObservableList<TourLogDTO> tourLogs = FXCollections.observableArrayList();
    // Create a List<String> to store the extracted information
    ArrayList<String> tourInfoList = new ArrayList<>();
    private TourLogListViewModel tourLogListViewModel = new TourLogListViewModel();
    //creating a PDFWriter

    public PDFWriter() {

    }

    public void createPdfTour(TourDTO tour) throws IOException, FileNotFoundException {
        // Get the current timestamp
        LocalDateTime currentTime = LocalDateTime.now();

        // format for the timestamp string
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss");
        String timestampString = currentTime.format(formatter);
        String filename = tour.getId() + "_" +tour.getName() + "_" + timestampString +".pdf";
        String dest = path + filename;
        System.out.println("PDFwriter activated");
        File file = new File(dest);
        file.getParentFile().mkdirs();

        //Initialize PDF writer, document, psfDoc
        PdfWriter writer = new PdfWriter(dest);
        PdfDocument pdfDoc = new PdfDocument(writer);
        Document document = new Document(pdfDoc);

        document.add(new Paragraph("Your selected Tour:"));
        List list = new List()
                .setSymbolIndent(12)
                .setListSymbol("\u2022");

        // Add tour details
        list.add(new ListItem("Name: " +tour.getName()))
                .add(new ListItem("Description: " +tour.getDescription()))
                .add(new ListItem("From: " + tour.getFrom()))
                .add(new ListItem("To: " + tour.getTo()))
                .add(new ListItem("Distance: "+ tour.getDistance()))
                .add(new ListItem("Time: "+ tour.getTime()))
                .add(new ListItem("Transport Type: "+ tour.getTransportType()));

        document.add(list);
        String imageUrl = tour.getRouteImage();

        // Create an ImageData object from the URL - needed to create new image
        ImageData imageData = ImageDataFactory.create(imageUrl);
        Image image = new Image(imageData);
        document.add(image);

        //table for logs
        document.add(new Paragraph("Logs attached to selected Tour:"));
        // get Logs by tourId
        tourLogListViewModel.refreshTourLogs();
        tourLogs = tourLogListViewModel.getTourLogs();
        // Iterate over the tourList and extract the desired information
        for (TourLogDTO tourLog : tour.getTourLogs()) {
            System.out.println("tourLog attached: " + tourLog.getComment());
            String tourLogInfo = "\nTotal Time: " + tourLog.getTotalTime() +
                    "\nDate/Time: " + tourLog.getDateTime() +
                    "\nRating 1-10: " + tourLog.getRating() +
                    "\nDifficulty 1-10: " + tourLog.getDifficulty() +
                    "\nComment: " + tourLog.getComment() +
                    "\n\n";
            tourInfoList.add(tourLogInfo);
        }
        // Create a List object and add the tourInfoList to it
        List logList = new List()
                .setSymbolIndent(12)
                .setListSymbol("\u2022");

        for (String tourInfo : tourInfoList) {
            logList.add(new ListItem(tourInfo));
        }
        document.add(logList);
        document.close();
        //user feedback
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("PDF Generation");
        alert.setHeaderText(null);
        alert.setContentText("PDF generated successfully! \nView: /SVEN2-routePlanner/swen2/" + path + filename);
        alert.showAndWait();
    }
    public void createPdfTourStatistic(TourDTO[] tour) throws IOException, FileNotFoundException {
        // Get the current timestamp
        LocalDateTime currentTime = LocalDateTime.now();

        // format for the timestamp string
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss");
        String timestampString = currentTime.format(formatter);
        String dest = path +  "_statistic_" + timestampString + ".pdf";
        //System.out.println("PDFwriter activated");
        File file = new File(dest);
        file.getParentFile().mkdirs();

        //Initialize PDF writer, PDF document and document
        PdfWriter writer = new PdfWriter(dest);
        PdfDocument pdfDoc = new PdfDocument(writer);
        Document document = new Document(pdfDoc);
        document.add(new Paragraph("Your selected Tour:"));
        // Create a List
        for(TourDTO singleTour : tour){
            List list = new List()
                    .setSymbolIndent(12)
                    .setListSymbol("\u2022");
            // Add tour details
            list.add(new ListItem("Name: " + singleTour.getName()))
                    .add(new ListItem("Description: " + singleTour.getDescription()))
                    .add(new ListItem("From: " + singleTour.getFrom()))
                    .add(new ListItem("To: " + singleTour.getTo()))
                    .add(new ListItem("Distance: " + singleTour.getDistance()))
                    .add(new ListItem("Time: " + singleTour.getTime()))
                    .add(new ListItem("Transport Type: " + singleTour.getTransportType()));
            document.add(list);
            // add the image
            String imageUrl = singleTour.getRouteImage();
            ImageData imageData = ImageDataFactory.create(imageUrl);
            Image image = new Image(imageData);
            document.add(image);
            document.add(new Paragraph("Statistics from TourLogs:"));
            // get Logs by tourId
            tourLogListViewModel.refreshTourLogs();
            tourLogs = tourLogListViewModel.getTourLogs();
            // Iterate over the tourList and extract the desired information
            double countTotalTime = 0;
            double countRating = 0;
            double countDifficulty = 0;
            double totalTime;
            double rating;
            double difficulty;
            int counter = 0;
            for (TourLogDTO tourLog : singleTour.getTourLogs()) {
                System.out.println("tourLog attached: " + tourLog.getComment());
                countTotalTime += tourLog.getTotalTime();
                countRating += tourLog.getRating();
                countDifficulty += tourLog.getDifficulty();
                counter++;
            }
            totalTime = countTotalTime / counter;
            rating = countRating / counter;
            difficulty = countDifficulty / counter;
            // Create a List
            List statisticList = new List()
                    .setSymbolIndent(12)
                    .setListSymbol("\u2022");

            // Add tour statistic
            statisticList.add(new ListItem("Total Time on average: " + totalTime))
                    .add(new ListItem("Popularity (average rating): " + rating))
                    .add(new ListItem("Difficulty on average: " + difficulty))
                    .add(new ListItem("To: " + singleTour.getTo()));
            document.add(statisticList);

            document.add(new Paragraph());

        }
        document.close();
        //user feedback
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("PDF Generation");
        alert.setHeaderText(null);
        alert.setContentText("PDF generated successfully! \nView: /SVEN2-routePlanner/swen2/" + dest);
        alert.showAndWait();
    }
}
