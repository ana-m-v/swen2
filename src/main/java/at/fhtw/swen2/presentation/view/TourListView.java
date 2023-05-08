package at.fhtw.swen2.presentation.view;

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
import javafx.scene.layout.VBox;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

@Component
@Scope("prototype")
public class TourListView implements Initializable {

    @Autowired
    public TourListViewModel tourListViewModel;
    ListView<TourDTO> tourList = new ListView<>();
    @FXML
    public TableView tableView = new TableView<>();
    @FXML
    private TableColumn<TourDTO, String> nameColumn;
    @FXML
    private ImageView tourImageView;
    @FXML
    private Button deleteButton;
    @FXML
    private Button editButton;

    @Override
    public void initialize(URL location, ResourceBundle rb){
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
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

    public void deleteButtonAction(ActionEvent actionEvent) {
        tourListViewModel.deleteTour((TourDTO) tableView.getSelectionModel().getSelectedItem());
        tourImageView.setImage(null);
    }
}
