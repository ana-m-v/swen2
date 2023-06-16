package at.fhtw.swen2.presentation.view;

import at.fhtw.swen2.model.TourDTO;
import at.fhtw.swen2.model.TourLogDTO;
import at.fhtw.swen2.presentation.viewmodel.SearchViewModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.sql.Timestamp;
import java.util.ResourceBundle;

@Component
@Scope("prototype")
public class SearchView implements Initializable {
    @FXML
    public TableView tourTable;
    @FXML
    public TableView tourLogTable;
    @Autowired
    private SearchViewModel searchViewModel;
    @FXML
    public TextField searchField;
    ListView<TourLogDTO> tourLogList = new ListView<>();
    ListView<TourDTO> tourList = new ListView<>();
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
    private TableColumn<TourLogDTO, String> popularityColumn;
    @FXML
    private TableColumn<TourLogDTO, String> childFriendlyColumn;

    @Override
    public void initialize(URL location, ResourceBundle rb) {
        tourTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tourLogTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        // tour table
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        fromColumn.setCellValueFactory(new PropertyValueFactory<>("from"));
        toColumn.setCellValueFactory(new PropertyValueFactory<>("to"));
        distanceColumn.setCellValueFactory(new PropertyValueFactory<>("distance"));
        timeColumn.setCellValueFactory(new PropertyValueFactory<>("time"));
        transportTypeColumn.setCellValueFactory(new PropertyValueFactory<>("transportType"));
        popularityColumn.setCellValueFactory(new PropertyValueFactory<>("popularity"));
        childFriendlyColumn.setCellValueFactory(new PropertyValueFactory<>("childFriendly"));

        //tourlog table
        ratingColumn.setCellValueFactory(new PropertyValueFactory<>("rating"));
        difficultyColumn.setCellValueFactory(new PropertyValueFactory<>("difficulty"));
        commentColumn.setCellValueFactory(new PropertyValueFactory<>("comment"));
        totalTimeColumn.setCellValueFactory(new PropertyValueFactory<>("totalTime"));
        dateTimeColumn.setCellValueFactory(new PropertyValueFactory<>("dateTime"));

        searchField.textProperty().bindBidirectional(searchViewModel.searchStringProperty());

        tourTable.setItems(searchViewModel.getTours());
        tourLogTable.setItems(searchViewModel.getTourLogs());
    }

    public void searchButtonAction(ActionEvent actionEvent) {
        searchViewModel.search();
    }

}
