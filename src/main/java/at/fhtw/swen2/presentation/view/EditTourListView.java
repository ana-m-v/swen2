package at.fhtw.swen2.presentation.view;

import at.fhtw.swen2.model.TourDTO;
import at.fhtw.swen2.model.TransportType;
import at.fhtw.swen2.presentation.Swen2ApplicationFX;
import at.fhtw.swen2.presentation.viewmodel.TourListViewModel;
import at.fhtw.swen2.presentation.viewmodel.TourLogListViewModel;

import at.fhtw.swen2.presentation.viewmodel.TourViewModel;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.text.Text;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;


@Component
@Scope("prototype")
public class EditTourListView implements Initializable {
    private Logger logger = LoggerFactory.getLogger(Swen2ApplicationFX.class);

    @Autowired
    public TourListViewModel tourListViewModel;
    @Autowired
    public TourLogListViewModel tourLogListViewModel;
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
    private Button saveEditButton;
    @FXML
    private Button deleteTourButton;
    @FXML
    public TextField nameEditTextField;
    @FXML
    private TextField descriptionEditTextField;
    @FXML
    private TextField fromEditTextField;
    @FXML
    private TextField toEditTextField;
    @FXML
    private ChoiceBox<TransportType> transportTypeChoiceBox;
    @FXML
    private Text feedbackText;

    @Override
    public void initialize(URL location, ResourceBundle rb){
        logger.info("EditTourListView initialized.");
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        //set table for tours
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        fromColumn.setCellValueFactory(new PropertyValueFactory<>("from"));
        toColumn.setCellValueFactory(new PropertyValueFactory<>("to"));
        transportTypeColumn.setCellValueFactory(new PropertyValueFactory<>("transportType"));
        distanceColumn.setCellValueFactory(new PropertyValueFactory<>("distance"));
        timeColumn.setCellValueFactory(new PropertyValueFactory<>("time"));

        //set form field edit tour
        nameEditTextField.textProperty().bindBidirectional(tourViewModel.nameProperty());
        descriptionEditTextField.textProperty().bindBidirectional(tourViewModel.descriptionProperty());
        fromEditTextField.textProperty().bindBidirectional(tourViewModel.fromProperty());
        toEditTextField.textProperty().bindBidirectional(tourViewModel.toProperty());
        transportTypeChoiceBox.setItems(FXCollections.observableArrayList(TransportType.values()));
        transportTypeChoiceBox.valueProperty().bindBidirectional(tourViewModel.transportTypeProperty());

        tableView.setItems(tourListViewModel.getTours());
        tableView.setOnMouseClicked(event -> {
            TourDTO selectedTour = (TourDTO) tableView.getSelectionModel().getSelectedItem();
            if(selectedTour != null) {
                tourViewModel.setSelectedTour(selectedTour);
                System.out.println("IN EDIT TOUR SELECTED TOUR " + selectedTour.getName() + " id " + selectedTour.getId());
            }
            if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2) {
                if (selectedTour != null) {
                    tourViewModel.setSelectedTour(selectedTour);
                    tourViewModel.setId(selectedTour.getId());
                    System.out.println("selected tour in click id name " + selectedTour.getId() + " " + selectedTour.getName() + "Distance: " + selectedTour.getDistance()
                    + "TourViewModel" + tourViewModel.nameProperty() + tourViewModel.toProperty());
                    nameEditTextField.setText(selectedTour.getName());
                    descriptionEditTextField.setText(selectedTour.getDescription());
                    fromEditTextField.setText(selectedTour.getFrom());
                    toEditTextField.setText(selectedTour.getTo());
                    transportTypeChoiceBox.setValue(selectedTour.getTransportType());
                }
            }
        });
//        tourListViewModel.refreshTours();
    }
    //edit Tour (update row in db)
    public void saveEditButtonAction(ActionEvent actionEvent) {
        if (nameEditTextField.getText() == null || descriptionEditTextField.getText() == null || fromEditTextField.getText() == null || toEditTextField.getText() == null || transportTypeChoiceBox.getValue() == null || nameEditTextField.getText().isEmpty() || descriptionEditTextField.getText().isEmpty() || fromEditTextField.getText().isEmpty() || toEditTextField.getText().isEmpty()) {
            feedbackText.setText("All of the fields must be filled out.");
            logger.warn("Empty fields in Create New Tour Form");
            return;
        }
        try {
            int intTest = Integer.parseInt(fromEditTextField.getText());
            feedbackText.setText("Destination can't be a number");
            return;
        } catch (NumberFormatException e) {
            System.out.println("Input String cannot be parsed to Integer. Good!");
        }
        try {
            int intTest = Integer.parseInt(toEditTextField.getText());
            feedbackText.setText("Destination can't be a number");
            return;
        } catch (NumberFormatException e) {
            System.out.println("Input String cannot be parsed to Integer. Good!");
        }
        TourDTO tour = TourDTO.builder()
                .name(nameEditTextField.getText())
                .description(descriptionEditTextField.getText())
                .from(fromEditTextField.getText())
                .to(toEditTextField.getText())
                .transportType(tourViewModel.getTransportType())
                .build();
        tour.setId(tourViewModel.getId());
        System.out.println("Saviiing Prozsezzssss  krrr kkkkr tourname in text field" + nameEditTextField.getText() +
                " tourname in tour " + tour.getName() + "tourviewmod id " + tour.getId());
        tourViewModel.updateEditedTour(tour);
    }
    //saves Tour backward - unique feature
    public void saveBackwardTourButtonAction(ActionEvent actionEvent) {
        System.out.println("editing tour id: " + tourViewModel.getId());
        String from = tourViewModel.getFrom();
        String to = tourViewModel.getTo();
        tourViewModel.setTo(from);
        tourViewModel.setFrom(to);
        tourViewModel.setName(tourViewModel.getName() + "_back");
        tourViewModel.createTour();
    }

    public void deleteButtonAction(ActionEvent actionEvent) {
        TourDTO selectedTour = (TourDTO) tableView.getSelectionModel().getSelectedItem();
        if (selectedTour != null) {
            System.out.println("selected tour " + selectedTour.getName());
            tourViewModel.deleteTour(selectedTour);
        }
    }
}