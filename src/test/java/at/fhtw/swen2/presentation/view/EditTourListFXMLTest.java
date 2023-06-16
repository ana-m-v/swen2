package at.fhtw.swen2.presentation.view;

import at.fhtw.swen2.Swen2Application;
import at.fhtw.swen2.presentation.Swen2ApplicationFX;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import org.junit.BeforeClass;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.loadui.testfx.GuiTest;
import org.loadui.testfx.utils.FXTestUtils;
import org.testfx.api.FxAssert;
import org.testfx.api.FxToolkit;
import org.testfx.matcher.control.TableViewMatchers;
import org.testfx.matcher.control.TextInputControlMatchers;
import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.api.FxToolkit.registerPrimaryStage;
import java.io.IOException;

import javafx.scene.Parent;

import org.loadui.testfx.GuiTest;
import org.loadui.testfx.utils.FXTestUtils;


import org.testfx.framework.junit5.ApplicationTest;

import java.io.IOException;

public class EditTourListFXMLTest extends GuiTest {


    @Override
    protected Parent getRootNode() {
        Parent parent = null;
        try {
            parent = FXMLLoader.load(getClass().getResource("EditTourList.fxml"));
            return parent;
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
    }
    @BeforeAll
    public static void setupSpec() throws Exception {
        FxToolkit.registerPrimaryStage();
    }


    @BeforeEach
    public void setUp() throws Exception {
        // Prepare the application for testing
        // ...

        ApplicationTest.launch(Swen2ApplicationFX.class);
    }


    @Test
    public void testEditTourListView() throws Exception {
        // Load the FXML file
//        FXMLLoader loader = new FXMLLoader(getClass().getResource("EditTourList.fxml"));
        Parent root = getRootNode();

        // Get references to UI elements
        TableView tableView = (TableView) root.lookup("#tableView");
        TextField nameEditTextField = (TextField) root.lookup("#nameEditTextField");
        TextField descriptionEditTextField = (TextField) root.lookup("#descriptionEditTextField");
        TextField fromEditTextField = (TextField) root.lookup("#fromEditTextField");
        TextField toEditTextField = (TextField) root.lookup("#toEditTextField");
        ChoiceBox transportTypeChoiceBox = (ChoiceBox) root.lookup("#transportTypeChoiceBox");
        Button deleteTourButton = (Button) root.lookup("#deleteTourButton");
        Button saveEditButton = (Button) root.lookup("#saveEditButton");
        Button backwardTourButton = (Button) root.lookup("#backwardTourButton");

        // Verify the presence of UI elements
        verifyThat(tableView, TableViewMatchers.hasNumRows(0));
        verifyThat(nameEditTextField, TextInputControlMatchers.hasText(""));
        verifyThat(descriptionEditTextField, TextInputControlMatchers.hasText(""));
        verifyThat(fromEditTextField, TextInputControlMatchers.hasText(""));
        verifyThat(toEditTextField, TextInputControlMatchers.hasText(""));

    }
}
