package at.fhtw.swen2.presentation.view;

import at.fhtw.swen2.model.TourDTO;
import at.fhtw.swen2.model.TourLogDTO;

import at.fhtw.swen2.presentation.viewmodel.TourLogListViewModel;
import at.fhtw.swen2.presentation.viewmodel.TourViewModel;
import at.fhtw.swen2.presentation.viewmodel.TourListViewModel;
import at.fhtw.swen2.presentation.viewmodel.TourLogViewModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.util.converter.NumberStringConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.sql.Timestamp;
import java.util.ResourceBundle;
@Component
@Scope("prototype")
public class LogListView implements Initializable {

    @Autowired
    public TourLogListViewModel tourLogListViewModel;
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

    private ApplicationContext applicationContext;

    @Override
    public void initialize(URL location, ResourceBundle rb){
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
        totalTimeTextField.textProperty().bindBidirectional(tourLogViewModel.totalTimeProperty(), new NumberStringConverter());
        ratingTextField.textProperty().bindBidirectional(tourLogViewModel.ratingProperty(), new NumberStringConverter());
        difficultyTextField.textProperty().bindBidirectional(tourLogViewModel.difficultyProperty(), new NumberStringConverter());
        commentTextField.textProperty().bindBidirectional(tourLogViewModel.commentProperty());
        tableViewTour.setItems(tourListViewModel.getTours());
        tableViewLog.setItems(tourLogListViewModel.getTourLogs());

        tableViewTour.setOnMouseClicked(event -> {
            TourDTO selectedTour = (TourDTO) tableViewTour.getSelectionModel().getSelectedItem();
       /*     if(selectedTour != null) {
                //tourListViewModel.setSelectedTour(selectedTour);
                tourViewModel.setSelectedTour(selectedTour);

            } */
            if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2) {
                if (selectedTour != null) {
                    // tourListViewModel.setSelectedTour(selectedTour);
                    tourViewModel.setSelectedTour(selectedTour);
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
        tourListViewModel.refreshTours();
        tourLogListViewModel.refreshTourLogs();
    }

    public void saveLogButtonAction(ActionEvent actionEvent) {
        System.out.println("Saviiing Prozsezzssss  krrr kkkkr");
        tourLogViewModel.createTourLog();

    }


}
