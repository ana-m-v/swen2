package at.fhtw.swen2.presentation.view;

import at.fhtw.swen2.model.TourDTO;
import at.fhtw.swen2.model.TransportType;
import at.fhtw.swen2.presentation.Swen2ApplicationFX;
import at.fhtw.swen2.presentation.viewmodel.TourListViewModel;
import at.fhtw.swen2.presentation.viewmodel.TourViewModel;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.*;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.awt.*;
import java.net.URL;
import java.util.ResourceBundle;

@Component
@Scope("prototype")
@Slf4j
public class TourView implements Initializable {

    private Logger logger = LoggerFactory.getLogger(Swen2ApplicationFX.class);

    @Autowired
    private TourViewModel tourViewModel;

    @Autowired
    private TourListViewModel tourListViewModel;

    @FXML
    public TextField nameTextField;

    @FXML
    private TextField descriptionTextField;
    @FXML
    private TextField fromTextField;
    @FXML
    private TextField toTextField;
    @FXML
    private ChoiceBox<TransportType> transportTypeChoiceBox;
    @FXML
    private Button createButton;
    @FXML
    private Button deleteButton;
    @FXML
    private Text feedbackText;

    public TourView(TourDTO tourDTO) {
        this.tourViewModel = new TourViewModel(tourDTO);
    }

    @Override
    public void initialize(URL location, ResourceBundle rb) {
        logger.info("TourView initialized");
        nameTextField.textProperty().bindBidirectional(tourViewModel.nameProperty());
        descriptionTextField.textProperty().bindBidirectional(tourViewModel.descriptionProperty());
        fromTextField.textProperty().bindBidirectional(tourViewModel.fromProperty());
        toTextField.textProperty().bindBidirectional(tourViewModel.toProperty());
        transportTypeChoiceBox.setItems(FXCollections.observableArrayList(TransportType.values()));
        transportTypeChoiceBox.valueProperty().bindBidirectional(tourViewModel.transportTypeProperty());
    }
    public void submitButtonAction(ActionEvent event) {
        if (nameTextField.getText() == null || descriptionTextField.getText() == null || fromTextField.getText() == null || toTextField.getText() == null || transportTypeChoiceBox.getValue() == null) {
            feedbackText.setText("All of the fields must be filled out.");
            logger.warn("Empty fields in Create New Tour Form");
            return;
        }
        try {
            int intTest = Integer.parseInt(fromTextField.getText());
            feedbackText.setText("Destination can't be a number");
            return;
        } catch (NumberFormatException e) {
            System.out.println("Input String cannot be parsed to Integer. Good!");
        }
        try {
            int intTest = Integer.parseInt(toTextField.getText());
            feedbackText.setText("Destination can't be a number");
            return;
        } catch (NumberFormatException e) {
            System.out.println("Input String cannot be parsed to Integer. Good!");
        }
        tourViewModel.createTour();
    }
}
