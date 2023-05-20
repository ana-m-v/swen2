package at.fhtw.swen2.presentation.view;

import at.fhtw.swen2.presentation.Swen2ApplicationFX;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import at.fhtw.swen2.model.TourDTO;
import at.fhtw.swen2.presentation.viewmodel.TourListViewModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


@Component
@Scope("prototype")
public class TourListView implements Initializable {

    private Logger logger = LoggerFactory.getLogger(Swen2ApplicationFX.class);
    @Autowired
    public TourListViewModel tourListViewModel;
    ListView<TourDTO> tourList = new ListView<>();
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
    private TableColumn<TourDTO, String> popularityColumn;

    @FXML
    private TableColumn<TourDTO, String> childFriendlyColumn;

    @FXML
    private ImageView tourImageView;
    private ApplicationContext applicationContext;
    @FXML
    public Button deleteButton;
    @FXML
    public static Button editButton;

    @Override
    public void initialize(URL location, ResourceBundle rb){
        logger.info("TourListView initialized");
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        fromColumn.setCellValueFactory(new PropertyValueFactory<>("From"));
        toColumn.setCellValueFactory(new PropertyValueFactory<>("to"));
        transportTypeColumn.setCellValueFactory(new PropertyValueFactory<>("transportType"));
        distanceColumn.setCellValueFactory(new PropertyValueFactory<>("distance"));
        timeColumn.setCellValueFactory(new PropertyValueFactory<>("time"));
        toColumn.setCellValueFactory(new PropertyValueFactory<>("to"));
        popularityColumn.setCellValueFactory(new PropertyValueFactory<>("popularity"));
        childFriendlyColumn.setCellValueFactory(new PropertyValueFactory<>("childFriendly"));
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
                }
            }
        });
        tourListViewModel.refreshTours();
    }
}
