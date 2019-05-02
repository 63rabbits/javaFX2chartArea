package javaFX2chartArea;

import java.net.URL;
import java.util.Iterator;
import java.util.TreeMap;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.StackedAreaChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class ChartAreaController {

	@FXML
	private VBox vbx;
	@FXML
	private Button btn;

	enum AreaStyle {
		NORMAL, STACKED;
	}

	private AreaStyle style = AreaStyle.NORMAL;

	private XYChart.Series<Number, Number> series01 = new XYChart.Series<>();
	private XYChart.Series<Number, Number> series02 = new XYChart.Series<>();
	// NOTE : Set NumberAxis parameter to use setLowerBound etc. .
	private NumberAxis xAxis = new NumberAxis(0, 100, 1);
	private NumberAxis yAxis = new NumberAxis();
	private AreaChart<Number, Number> areac = new AreaChart<Number, Number>(xAxis, yAxis);

	private XYChart.Series<Number, Number> saseries01 = new XYChart.Series<>();
	private XYChart.Series<Number, Number> saseries02 = new XYChart.Series<>();
	// NOTE : Set NumberAxis parameter to use setLowerBound etc. .
	private NumberAxis saxAxis = new NumberAxis(0, 100, 1);
	private NumberAxis sayAxis = new NumberAxis();
	private StackedAreaChart<Number, Number> sareac = new StackedAreaChart<>(saxAxis, sayAxis);

	private boolean flag = true;
	private double xAxisMin = 0.0d;
	private double xAxisMax = 0.0d;

	@FXML
	void initialize() {
		assert vbx != null : "fx:id=\"vbx\" was not injected: check your FXML file 'ChartArea.fxml'.";
		assert btn != null : "fx:id=\"btn\" was not injected: check your FXML file 'ChartArea.fxml'.";

		//////////////////////////////
		// Normal Area Chart
		areac.prefWidthProperty().bind(vbx.widthProperty());
		areac.prefHeightProperty().bind(vbx.heightProperty());

		areac.setTitle("Area Chart Sample");
		xAxis.setLabel("X-Axis label");
		yAxis.setLabel("Y-Axis label");

		//////////////////////////////
		// Stacke Area Chart
		sareac.prefWidthProperty().bind(vbx.widthProperty());
		sareac.prefHeightProperty().bind(vbx.heightProperty());

		sareac.setTitle("Stacked Area Chart Sample");
		saxAxis.setLabel("X-Axis label");
		sayAxis.setLabel("Y-Axis label");

		//////////////////////////////
		// set Data
		series01.setName("data01");
		saseries01.setName("data01");
		{
			URL url = this.getClass().getResource("res/data01.csv");
			OpCsv csv = new OpCsv(url);

			TreeMap<Integer, String[]> map = csv.getNumberSortedCsv(0);
			Iterator<Integer> it = map.keySet().iterator();
			while (it.hasNext()) {
				int no = it.next();
				String[] words = map.get(no);
				Double d01 = Double.parseDouble(words[0]);
				Double d02 = Double.parseDouble(words[1]);

				series01.getData().add(new XYChart.Data<Number, Number>(d01, d02));
				saseries01.getData().add(new XYChart.Data<Number, Number>(d01, d02));

				if (flag) {
					flag = false;
					xAxisMin = d01;
					xAxisMax = d01;
				}
				else {
					if (d01 < xAxisMin) {
						xAxisMin = d01;
					}
					if (d01 > xAxisMax) {
						xAxisMax = d01;
					}
				}
			}
		}
		areac.getData().add(series01);
		sareac.getData().add(saseries01);

		series02.setName("data02");
		saseries02.setName("data02");
		{
			URL url = this.getClass().getResource("res/data02.csv");
			OpCsv csv = new OpCsv(url);

			TreeMap<Integer, String[]> map = csv.getNumberSortedCsv(0);
			Iterator<Integer> it = map.keySet().iterator();
			while (it.hasNext()) {
				int no = it.next();
				String[] words = map.get(no);
				Double d01 = Double.parseDouble(words[0]);
				Double d02 = Double.parseDouble(words[1]);

				series02.getData().add(new XYChart.Data<Number, Number>(d01, d02));
				saseries02.getData().add(new XYChart.Data<Number, Number>(d01, d02));

				if (flag) {
					flag = false;
					xAxisMin = d01;
					xAxisMax = d01;
				}
				else {
					if (d01 < xAxisMin) {
						xAxisMin = d01;
					}
					if (d01 > xAxisMax) {
						xAxisMax = d01;
					}
				}
			}
		}
		areac.getData().add(series02);
		sareac.getData().add(saseries02);

		xAxis.setLowerBound(xAxisMin);
		xAxis.setUpperBound(xAxisMax);
		//		xAxis.setTickUnit(1);
		//		xAxis.setMinorTickCount(2);

		saxAxis.setLowerBound(xAxisMin);
		saxAxis.setUpperBound(xAxisMax);
		//		saxAxis.setTickUnit(1);
		//		saxAxis.setMinorTickCount(2);

		setStyleNormalArea();
	}

	@FXML
	void btnOnAction(ActionEvent event) {
		switch (style) {
		case NORMAL:
			setStyleStackedArea();
			break;
		case STACKED:
			setStyleNormalArea();
			break;
		default:
			break;
		}
	}

	private void setStyleNormalArea() {
		style = AreaStyle.NORMAL;
		vbx.getChildren().clear();
		vbx.getChildren().add(areac);
	}

	private void setStyleStackedArea() {
		style = AreaStyle.STACKED;
		vbx.getChildren().clear();
		vbx.getChildren().add(sareac);
	}
}
