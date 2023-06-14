package at.fhtw.swen2.presentation.view;

import at.fhtw.swen2.model.TourDTO;
import at.fhtw.swen2.model.TourLogDTO;

import at.fhtw.swen2.persistence.entity.TourEntity;
import at.fhtw.swen2.persistence.entity.TourLogEntity;
import at.fhtw.swen2.presentation.Swen2ApplicationFX;
import at.fhtw.swen2.presentation.viewmodel.TourLogListViewModel;
import at.fhtw.swen2.presentation.viewmodel.TourViewModel;
import at.fhtw.swen2.presentation.viewmodel.TourListViewModel;
import at.fhtw.swen2.presentation.viewmodel.TourLogViewModel;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.text.Text;
import javafx.util.StringConverter;
import javafx.util.converter.LocalDateTimeStringConverter;
import javafx.util.converter.NumberStringConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.util.converter.DateTimeStringConverter;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;


import java.net.URL;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

@Component
@Scope("prototype")
public class LogListView implements Initializable {

    private Logger logger = LoggerFactory.getLogger(Swen2ApplicationFX.class);
    @Autowired
    public TourLogListViewModel tourLogListViewModel;
    public Button saveLogButton;
    public Button editLogButton;
    public Button deleteLogButton;
    ListView<TourLogDTO> tourLogList = new ListView<>();
    @Autowired
    public TourListViewModel tourListViewModel;
    ListView<TourDTO> tourList = new ListView<>();
    @Autowired
    public TourLogViewModel tourLogViewModel;
    @Autowired
    public TourViewModel tourViewModel;
    @FXML
    public TableView tableViewTour = new TableView<>();
    @FXML
    public TableView tableViewLog = new TableView<>();
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
    private TableColumn<TourLogDTO, String> totalTimeColumn;
    @FXML
    private TableColumn<TourLogDTO, Timestamp> dateTimeColumn;
    @FXML
    private TableColumn<TourLogDTO, String> commentColumn = new TableColumn<>("Comment");
    @FXML
    private TableColumn<TourLogDTO, String> difficultyColumn;
    @FXML
    private TableColumn<TourLogDTO, String> ratingColumn;
    @FXML
    private TextField totalTimeTextField;
    @FXML
    private TextField dateTimeTextField;
    @FXML
    private TextField ratingTextField;
    @FXML
    private TextField difficultyTextField;
    @FXML
    private TextField commentTextField;
    @FXML
    private Text feedbackText;
    private ApplicationContext applicationContext;

    @Override
    public void initialize(URL location, ResourceBundle rb){
        logger.info("LogListView initialized");
        tableViewTour.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tableViewLog.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        // tour table
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        fromColumn.setCellValueFactory(new PropertyValueFactory<>("from"));
        toColumn.setCellValueFactory(new PropertyValueFactory<>("to"));
        distanceColumn.setCellValueFactory(new PropertyValueFactory<>("distance"));
        timeColumn.setCellValueFactory(new PropertyValueFactory<>("time"));
        transportTypeColumn.setCellValueFactory(new PropertyValueFactory<>("transportType"));

        //tourlog table
        ratingColumn.setCellValueFactory(new PropertyValueFactory<>("rating"));
        difficultyColumn.setCellValueFactory(new PropertyValueFactory<>("difficulty"));
        commentColumn.setCellValueFactory(new PropertyValueFactory<>("comment"));
        totalTimeColumn.setCellValueFactory(new PropertyValueFactory<>("totalTime"));
        dateTimeColumn.setCellValueFactory(new PropertyValueFactory<>("dateTime"));
        //log edit
      //  dateTimeTextField.textProperty().bindBidirectional(tourViewModel.nameProperty());
//        dateTimeTextField.textProperty().bindBidirectional(tourLogViewModel.dateTimeProperty(), new DateTimeStringConverter());

        // Bind the text property bidirectionally with the datetime property ??
        dateTimeTextField.textProperty().bindBidirectional(tourLogViewModel.dateTimeProperty());

        totalTimeTextField.textProperty().bindBidirectional(tourLogViewModel.totalTimeProperty(), new NumberStringConverter());
        ratingTextField.textProperty().bindBidirectional(tourLogViewModel.ratingProperty(), new NumberStringConverter());
        difficultyTextField.textProperty().bindBidirectional(tourLogViewModel.difficultyProperty(), new NumberStringConverter());
        commentTextField.textProperty().bindBidirectional(tourLogViewModel.commentProperty());
        tableViewTour.setItems(tourListViewModel.getTours());
//        tableViewLog.setItems(tourLogListViewModel.getTourLogs());

        tableViewTour.setOnMouseClicked(event -> {
            TourDTO selectedTour = (TourDTO) tableViewTour.getSelectionModel().getSelectedItem();
       /*     if(selectedTour != null) {
                //tourListViewModel.setSelectedTour(selectedTour);
                tourViewModel.setSelectedTour(selectedTour);

            } */
            if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2) {

                if (selectedTour != null) {
//                    tableViewLog.setItems(tourLogListViewModel.getTourLogs(selectedTour));
//                    tableViewLog.setItems(tourLogListViewModel.getTourLogsFromTour(selectedTour));

                    // tourListViewModel.setSelectedTour(selectedTour);
                    tourViewModel.setSelectedTour(selectedTour);
                    List<TourLogDTO> tourLogs = selectedTour.getTourLogs();
                    tableViewLog.setItems(FXCollections.observableList(selectedTour.getTourLogs()));

//                    tableViewLog.setItems(FXCollections.observableList(tourLogs));
                    //System.out.println("selected tour in click id name " + selectedTour.getId() + " " + selectedTour.getName() + "Distance: " + selectedTour.getDistance()
                      //      + "TourViewModel" + tourViewModel.nameProperty() + tourViewModel.toProperty());
                //    nameEditTextField.setText(selectedTour.getName());
               //     descriptionEditTextField.setText(selectedTour.getDescription());
                 //   fromEditTextField.setText(selectedTour.getFrom());
                   // toEditTextField.setText(selectedTour.getTo());
                   // distanceEditTextField.setText(Double.toString(selectedTour.getDistance()));
                   // timeEditTextField.setText(String.valueOf(selectedTour.getTime()));
                }
            }
        });

        tableViewLog.setOnMouseClicked(event -> {
            TourLogDTO selectedTourLog = (TourLogDTO) tableViewLog.getSelectionModel().getSelectedItem();

            if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2) {
                if(selectedTourLog != null) {
                    tourLogViewModel.setSelectedTourLog(selectedTourLog);
                    tourLogViewModel.setId(selectedTourLog.getId());
                    Timestamp dateTime = selectedTourLog.getDateTime();
                    if (dateTime != null) {
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                        String dateTimeString = dateTime.toLocalDateTime().format(formatter);
                        dateTimeTextField.setText(dateTimeString);
                    } else {
                        dateTimeTextField.setText(""); // Set the text field to empty if datetime is null
                    }
                    totalTimeTextField.setText(String.valueOf(selectedTourLog.getTotalTime()));
                    ratingTextField.setText(String.valueOf(selectedTourLog.getRating()));
                    difficultyTextField.setText(String.valueOf(selectedTourLog.getDifficulty()));
                    commentTextField.setText(selectedTourLog.getComment());
                }
            }
        });
        tourListViewModel.refreshTours();
        tourLogListViewModel.refreshTourLogs();
    }

    public void saveLogButtonAction(ActionEvent actionEvent) {
        if (dateTimeTextField.getText() == null || totalTimeTextField.getText() == null || ratingTextField.getText() == null || difficultyTextField.getText() == null || commentTextField.getText() == null) {
            feedbackText.setText("All of the fields must be filled out.");
            logger.warn("Empty fields in Create New Tour Form");
            return;
        }
        try {
            int parsedTotalTime = Integer.parseInt(totalTimeTextField.getText());
        } catch (NumberFormatException e) {
            feedbackText.setText("'Total Time' has to be a number.");
            logger.warn("Non-numeric value in Create New Tour Form");
            return;
        }

        try {
            int parsedRating = Integer.parseInt(ratingTextField.getText());
        } catch (NumberFormatException e) {
            feedbackText.setText("'Rating' has to be a number.");
            logger.warn("Non-numeric value in Create New Tour Form");
            return;
        }

        try {
            int parsedDifficulty = Integer.parseInt(difficultyTextField.getText());
        } catch (NumberFormatException e) {
            feedbackText.setText("'Difficulty' has to be a number.");
            logger.warn("Non-numeric value in Create New Tour Form");
            return;
        }

        System.out.println("Saviiing Prozsezzssss  krrr kkkkr");
        TourDTO selectedTour = (TourDTO) tableViewTour.getSelectionModel().getSelectedItem();
        if (selectedTour != null) {
            TourLogEntity tourLog = new TourLogEntity();
            tourLog.setTour(new TourEntity(selectedTour)); // set the associated TourEntity

            tourLogViewModel.createTourLog(selectedTour);
        } else {
            logger.warn("SelectedTour is null");
            feedbackText.setText("All of the fields must be filled out.");
        }
    }


    public void editLogButtonAction(ActionEvent actionEvent) {
        if (dateTimeTextField.getText() == null || totalTimeTextField.getText() == null || ratingTextField.getText() == null || difficultyTextField.getText() == null || commentTextField.getText() == null) {
            feedbackText.setText("All of the fields must be filled out.");
            logger.warn("Empty fields in Create New Tour Form");
            return;
        }

        try {
            int parsedTotalTime = Integer.parseInt(totalTimeTextField.getText());
        } catch (NumberFormatException e) {
            feedbackText.setText("'Total Time' has to be a number.");
            logger.warn("Non-numeric value in Create New Tour Form");
            return;
        }
        try {
            int parsedRating = Integer.parseInt(ratingTextField.getText());
        } catch (NumberFormatException e) {
            feedbackText.setText("'Rating' has to be a number.");
            logger.warn("Non-numeric value in Create New Tour Form");
            return;
        }
        try {
            int parsedDifficulty = Integer.parseInt(difficultyTextField.getText());
        } catch (NumberFormatException e) {
            feedbackText.setText("'Difficulty' has to be a number.");
            logger.warn("Non-numeric value in Create New Tour Form");
            return;
        }

        String dateTimeString = dateTimeTextField.getText();
        System.out.println("DATETIME IN UPDATE " + dateTimeString);
        LocalDateTime localDateTime = LocalDateTime.parse(dateTimeString, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        Timestamp dateTime = Timestamp.valueOf(localDateTime);

        System.out.println("DATETIME timestamp IN UPDATE " + dateTime);


        TourLogDTO tourLog = TourLogDTO.builder()
                .dateTime(dateTime)
                .totalTime(Integer.parseInt(totalTimeTextField.getText()))
                .rating(Integer.parseInt(ratingTextField.getText()))
                .difficulty(Integer.parseInt(difficultyTextField.getText()))
                .comment(commentTextField.getText())
//                .routeImage(tourViewModel.getRouteImage())
                .build();

        tourLog.setId(tourLogViewModel.getId());

        tourLogViewModel.updateTourLog(tourLog);
    }

    public void deleteLogButtonAction(ActionEvent actionEvent) {
        TourLogDTO selectedTourLog = (TourLogDTO) tableViewLog.getSelectionModel().getSelectedItem();
        if (selectedTourLog != null) {

            tourLogViewModel.deleteTourLog(selectedTourLog);
        }
        else {
            feedbackText.setText("You have not selected anything.");
            logger.warn("SelectedTour is null");
        }
    }
}
