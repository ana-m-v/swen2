package at.fhtw.swen2.presentation.view;

import at.fhtw.swen2.jsonConverter.JSONConverter;
import at.fhtw.swen2.model.TourLogDTO;
import at.fhtw.swen2.pdfwriter.PDFWriter;

import at.fhtw.swen2.presentation.viewmodel.TourLogListViewModel;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import at.fhtw.swen2.model.TourDTO;
import at.fhtw.swen2.presentation.viewmodel.TourListViewModel;
import javafx.fxml.FXML;

import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;


import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;


@Component
@Scope("prototype")
public class TourListView implements Initializable {

    @Autowired
    public TourListViewModel tourListViewModel;
    ListView<TourDTO> tourList = new ListView<>();
    @Autowired
    public TourLogListViewModel tourLogListViewModel;
    @FXML
    public TableView tableView = new TableView<>();

    @FXML
    private TableColumn<TourDTO, String> nameColumn;
    @FXML
    private TableColumn<TourDTO, String> fromColumn;
    @FXML
    private TableColumn<TourDTO, String> toColumn;
    @FXML
    private TableColumn<TourDTO, String> transportTypeColumn;
    @FXML
    private TableColumn<TourDTO, Double> distanceColumn;
    @FXML
    private TableColumn<TourDTO, String> timeColumn;


    @FXML
    private ImageView tourImageView;
    private ApplicationContext applicationContext;
    @FXML
    public Button deleteButton;
    @FXML
    public static Button editButton;

    public PDFWriter pdfWriter= new PDFWriter();
    private TourDTO tourToPdf = new TourDTO();

    public JSONConverter jsonConverter = new JSONConverter();


    @Override
    public void initialize(URL location, ResourceBundle rb){
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        fromColumn.setCellValueFactory(new PropertyValueFactory<>("From"));
        toColumn.setCellValueFactory(new PropertyValueFactory<>("to"));
        transportTypeColumn.setCellValueFactory(new PropertyValueFactory<>("transportType"));
        distanceColumn.setCellValueFactory(new PropertyValueFactory<>("distance"));
        timeColumn.setCellValueFactory(new PropertyValueFactory<>("time"));
        toColumn.setCellValueFactory(new PropertyValueFactory<>("to"));
        tableView.setItems(tourListViewModel.getTours());

        tableView.setOnMouseClicked(event -> {
            TourDTO selectedTour = (TourDTO) tableView.getSelectionModel().getSelectedItem();
            if(selectedTour != null) {
                tourListViewModel.setSelectedTour(selectedTour);

            }
            if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2) {
                if (selectedTour != null) {
//                    tourListViewModel.setSelectedTour(selectedTour);
                    System.out.println("selected tour in click id name " + selectedTour.getId() + " " + selectedTour.getName());
                    // display the image for the selected tour
                    Image tourImage = new Image(selectedTour.getRouteImage());
                    tourImageView.setImage(tourImage);
                    //set PDF tour
                    tourToPdf = tourListViewModel.getSelectedTour();
                }
            }
        });
        tourListViewModel.refreshTours();
    }
    public void createTourPDFButtonAction()  {
//        try {
            tourListViewModel.createPDF(tourToPdf);
//            pdfWriter.createPdfTour(tourToPdf);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }
    public void createTourStatisticPDFButtonAction()  {
        tourListViewModel.createPDFStatistic();
//
//        try {
//            pdfWriter.createPdfTourStatistic(tourToPdf);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    public void importToursButtonAction() throws JsonProcessingException {
        try {
            jsonConverter.importJSONFile("abc");
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    public void exportToursButtonAction() throws IOException {
        List<TourDTO> tourList = tourListViewModel.getTours();
        String jsonTours = new ObjectMapper().writeValueAsString(tourList);
        System.out.println(jsonTours);
        List<TourLogDTO> tourLogList = tourLogListViewModel.getTourLogs();
        String jsonTourLogs = new ObjectMapper().writeValueAsString(tourLogList);
        try {
            jsonConverter.writeJSONFile(jsonTours, jsonTourLogs);
        } catch (IOException e){
            e.printStackTrace();
        }
    }

}
