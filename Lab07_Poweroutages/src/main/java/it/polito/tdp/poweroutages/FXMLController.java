package it.polito.tdp.poweroutages;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.poweroutages.model.Model;
import it.polito.tdp.poweroutages.model.Nerc;
import it.polito.tdp.poweroutages.model.PowerOutage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class FXMLController {
	Model model;

	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private TextArea txtResult;

	@FXML
	private ChoiceBox<Nerc> boxNerc;

	@FXML
	private TextField txtYears;

	@FXML
	private TextField txtHours;

	@FXML
	private Button btnWorstCase;

	@FXML
	void boxNerc(MouseEvent event) {

	}

	@FXML
	void doWorstCase(ActionEvent event) {
		txtResult.clear();
		Nerc n = boxNerc.getValue();
		Integer years = null;
		Integer hours = null;

		try {
			years = Integer.parseInt(txtYears.getText());
			hours = Integer.parseInt(txtHours.getText());
		} catch (NumberFormatException e) {
			txtResult.setText("Attenzione! Formato anno o ore non valido. Inserire valori numerici.");
			return;
		}
		
		Long start = System.currentTimeMillis();
		List<PowerOutage> blackout = model.getWorstCase(n, years, hours);
		Long end = System.currentTimeMillis();
		
		if(blackout==null) {
			txtResult.setText("Non si sono verificati eventi di blackout presso quel NERC o che rispettino le condizioni inserite");
			return;
		}
		
		String s = "";
		for (PowerOutage p : blackout) {
			if (s.equals("")) {
				s += p.toString();
			} else {
				s += "\n" + p.toString();
			}
		}
		txtResult.appendText("TEMPO IMPIEGATO: " + (end-start) + " ms\n");
		txtResult.appendText("Tot people affected: " + model.getBestPersoneCoinvolte() + "\n");
		txtResult.appendText("Tot hours of outage: " + model.getHoursSoluzine() + "\n");
		txtResult.appendText("Tot years: " + model.getYearsSoluzione() + "\n");
		txtResult.appendText(s);

	}

	@FXML
	void initialize() {
		assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";
		assert boxNerc != null : "fx:id=\"boxNerc\" was not injected: check your FXML file 'Scene.fxml'.";
		assert txtYears != null : "fx:id=\"txtYears\" was not injected: check your FXML file 'Scene.fxml'.";
		assert txtHours != null : "fx:id=\"txtHours\" was not injected: check your FXML file 'Scene.fxml'.";
		assert btnWorstCase != null : "fx:id=\"btnWorstCase\" was not injected: check your FXML file 'Scene.fxml'.";

	}

	public void setModel(Model model) {
		this.model = model;
		setBoxItems();
	}

	private void setBoxItems() {
		boxNerc.getItems().addAll(model.getNercList());

	}
}
