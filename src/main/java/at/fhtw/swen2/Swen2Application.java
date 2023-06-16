package at.fhtw.swen2;

import at.fhtw.swen2.presentation.Swen2ApplicationFX;
import javafx.application.Application;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Swen2Application {

	public static void main(String[] args) {
		Application.launch(Swen2ApplicationFX.class, args);
	}

}
