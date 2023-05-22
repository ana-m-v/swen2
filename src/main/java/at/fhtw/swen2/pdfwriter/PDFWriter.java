package at.fhtw.swen2.pdfwriter;

import at.fhtw.swen2.model.TourDTO;
import at.fhtw.swen2.model.TourLogDTO;

import at.fhtw.swen2.presentation.viewmodel.TourLogListViewModel;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.layout.element.Image;

import com.itextpdf.kernel.pdf.action.PdfAction;
import com.itextpdf.layout.Document;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.element.*;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.itextpdf.layout.element.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

import javax.swing.text.StyleConstants;
import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ArrayList;

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

        // Define the format for the timestamp string
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss");

        // Format the current timestamp as a string
        String timestampString = currentTime.format(formatter);
        String filename = tour.getId() + "_" +tour.getName() + "_" + timestampString +".pdf";
        String dest = path + filename;
        System.out.println("PDFwriter activated");
        File file = new File(dest);
        file.getParentFile().mkdirs();

        //Initialize PDF writer
        PdfWriter writer = new PdfWriter(dest);
        //new PDFWriter().createPdf(dest);

        //Initialize PDF document
        PdfDocument pdfDoc = new PdfDocument(writer);

        // Initialize document
        Document document = new Document(pdfDoc);


        // Add a Paragraph
        document.add(new Paragraph("Your selected Tour:"));
        // Create a List
        List list = new List()
                .setSymbolIndent(12)
                .setListSymbol("\u2022");

        // Add ListItem objects
        list.add(new ListItem("Name: " +tour.getName()))
                .add(new ListItem("Description: " +tour.getDescription()))
                .add(new ListItem("From: " + tour.getFrom()))
                .add(new ListItem("To: " + tour.getTo()))
                .add(new ListItem("Distance: "+ tour.getDistance()))
                .add(new ListItem("Time: "+ tour.getTime()))
                .add(new ListItem("Transport Type: "+ tour.getTransportType()));


        // Add the list
        document.add(list);
        // add the image
        String imageUrl = tour.getRouteImage();

        // Create an ImageData object from the URL
        ImageData imageData = ImageDataFactory.create(imageUrl);

        // Create an Image object from the ImageData
        Image image = new Image(imageData);

        // Add the image to your PDF document
        document.add(image);

        //table for logs
        document.add(new Paragraph("Logs attached to selected Tour:"));
        // get Logs by tourId
        tourLogListViewModel.refreshTourLogs();
        tourLogs = tourLogListViewModel.getTourLogs();
        // Iterate over the tourList and extract the desired information
        for (TourLogDTO tourLog : tourLogs) {
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
    public void createPdfTourStatistic(TourDTO tour) throws IOException, FileNotFoundException {
        // Get the current timestamp
        LocalDateTime currentTime = LocalDateTime.now();

        // Define the format for the timestamp string
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss");

        // Format the current timestamp as a string
        String timestampString = currentTime.format(formatter);
        String dest = path + tour.getId() + "_" + tour.getName() + "_statistic_"+ timestampString + ".pdf";
        System.out.println("PDFwriter activated");
        File file = new File(dest);
        file.getParentFile().mkdirs();

        //Initialize PDF writer
        PdfWriter writer = new PdfWriter(dest);
        //new PDFWriter().createPdf(dest);

        //Initialize PDF document
        PdfDocument pdfDoc = new PdfDocument(writer);

        // Initialize document
        Document document = new Document(pdfDoc);


        // Add a Paragraph
        document.add(new Paragraph("Your selected Tour:"));
        // Create a List
        List list = new List()
                .setSymbolIndent(12)
                .setListSymbol("\u2022");

        // Add ListItem objects
        list.add(new ListItem("Name: " +tour.getName()))
                .add(new ListItem("Description: " +tour.getDescription()))
                .add(new ListItem("From: " + tour.getFrom()))
                .add(new ListItem("To: " + tour.getTo()))
                .add(new ListItem("Distance: "+ tour.getDistance()))
                .add(new ListItem("Time: "+ tour.getTime()))
                .add(new ListItem("Transport Type: "+ tour.getTransportType()));
        document.add(list);
        // add the image
        String imageUrl = tour.getRouteImage();

        // Create an ImageData object from the URL
        ImageData imageData = ImageDataFactory.create(imageUrl);

        // Create an Image object from the ImageData
        Image image = new Image(imageData);

        // Add the image to your PDF document
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
        for (TourLogDTO tourLog : tourLogs) {
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

        // Add ListItem objects
        statisticList.add(new ListItem("Total Time on average: " + totalTime))
                .add(new ListItem("Rating 1-10 on average: " + rating))
                .add(new ListItem("Difficulty on average: " + difficulty))
                .add(new ListItem("To: " + tour.getTo()));
        document.add(statisticList);
        //Close document
        document.close();
        //user feedback
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("PDF Generation");
        alert.setHeaderText(null);
        alert.setContentText("PDF generated successfully! \nView: /SVEN2-routePlanner/swen2/" + dest);
        alert.showAndWait();
    }
}
