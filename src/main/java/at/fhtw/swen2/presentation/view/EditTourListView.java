package at.fhtw.swen2.presentation.view;

import at.fhtw.swen2.model.TourDTO;
import at.fhtw.swen2.model.TransportType;
import at.fhtw.swen2.presentation.viewmodel.TourListViewModel;
import at.fhtw.swen2.presentation.viewmodel.TourLogListViewModel;

import at.fhtw.swen2.presentation.viewmodel.TourViewModel;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.util.converter.NumberStringConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;


@Component
@Scope("prototype")
public class EditTourListView implements Initializable {

    @Autowired
    public TourListViewModel tourListViewModel;
    ListView<TourDTO> tourList = new ListView<>();
    @Autowired
    public TourLogListViewModel tourLogListViewModel;
    ListView<TourDTO> tourLogList = new ListView<>();
    @Autowired
    public TourViewModel tourViewModel;
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
    public TextField nameEditTextField;

    @FXML
    private TextField descriptionEditTextField;
    @FXML
    private TextField fromEditTextField;
    @FXML
    private TextField toEditTextField;
    @FXML
    private TextField distanceEditTextField;
    @FXML
    private TextField timeEditTextField;

    @FXML
    private ImageView tourImageView;
    private ApplicationContext applicationContext;

    @Override
    public void initialize(URL location, ResourceBundle rb){
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        fromColumn.setCellValueFactory(new PropertyValueFactory<>("from"));
        toColumn.setCellValueFactory(new PropertyValueFactory<>("to"));
        transportTypeColumn.setCellValueFactory(new PropertyValueFactory<>("transportType"));
        distanceColumn.setCellValueFactory(new PropertyValueFactory<>("distance"));
        timeColumn.setCellValueFactory(new PropertyValueFactory<>("time"));

        nameEditTextField.textProperty().bindBidirectional(tourViewModel.nameProperty());
        descriptionEditTextField.textProperty().bindBidirectional(tourViewModel.descriptionProperty());
        fromEditTextField.textProperty().bindBidirectional(tourViewModel.fromProperty());
        toEditTextField.textProperty().bindBidirectional(tourViewModel.toProperty());
        distanceEditTextField.textProperty().bindBidirectional(tourViewModel.distanceProperty(), new NumberStringConverter());
        timeEditTextField.textProperty().bindBidirectional(tourViewModel.timeProperty(), new NumberStringConverter());
        tableView.setItems(tourListViewModel.getTours());
        tableView.setOnMouseClicked(event -> {
            TourDTO selectedTour = (TourDTO) tableView.getSelectionModel().getSelectedItem();
       /*     if(selectedTour != null) {
                //tourListViewModel.setSelectedTour(selectedTour);
                tourViewModel.setSelectedTour(selectedTour);

            } */
            if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2) {
                if (selectedTour != null) {
                   // tourListViewModel.setSelectedTour(selectedTour);
                    tourViewModel.setSelectedTour(selectedTour);
                    tourViewModel.setId(selectedTour.getId());
                    System.out.println("selected tour in click id name " + selectedTour.getId() + " " + selectedTour.getName() + "Distance: " + selectedTour.getDistance()
                    + "TourViewModel" + tourViewModel.nameProperty() + tourViewModel.toProperty());
                    nameEditTextField.setText(selectedTour.getName());
                    descriptionEditTextField.setText(selectedTour.getDescription());
                    fromEditTextField.setText(selectedTour.getFrom());
                    toEditTextField.setText(selectedTour.getTo());
                    distanceEditTextField.setText(Double.toString(selectedTour.getDistance()));
                    timeEditTextField.setText(String.valueOf(selectedTour.getTime()));
                }
            }
        });
        tourListViewModel.refreshTours();
    }
    //edit Tour (update row in db)
    public void saveEditButtonAction(ActionEvent actionEvent) {
        System.out.println("Saviiing Prozsezzssss  krrr kkkkr");
        TourDTO tour = TourDTO.builder()
                .name(nameEditTextField.getText())
                .description(descriptionEditTextField.getText())
                .distance(Double.parseDouble(distanceEditTextField.getText()))
                .time(Integer.parseInt(timeEditTextField.getText()))
                .from(fromEditTextField.getText())
                .transportType(tourViewModel.getTransportType())
                .routeImage(tourViewModel.getRouteImage())
                .build();
        tourViewModel.updateEditedTour(tour);

    }
    //saves edited Tour as new Tour
    public void editEditButtonAction(ActionEvent actionEvent) {
        System.out.println("editing tour id: " + tourViewModel.getId());
        tourViewModel.saveEditTour(tourViewModel.getId());

    }
}