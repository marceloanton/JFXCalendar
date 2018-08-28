package gr.jfxcalendar.views;

import java.time.DayOfWeek;
import java.time.LocalDate;

import javafx.geometry.Insets;
import javafx.geometry.NodeOrientation;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class CalendarWeekView extends CalendarView {

	private ScrollPane scrollPane;
	private AnchorPane eventPane;

	private LocalDate prevDate;

	private GridPane gridPane;

	public CalendarWeekView(StackPane parentPane) {
		super(parentPane);

		scrollPane = new ScrollPane();
		scrollPane.setPrefSize(650, 500);
		scrollPane.setStyle("-fx-background-color: WHITE;");
		getChildren().add(0, scrollPane);

		scrollPane.setHbarPolicy(ScrollBarPolicy.NEVER);

		StackPane mainPane = new StackPane();
		scrollPane.setContent(mainPane);

		eventPane = new AnchorPane();
		StackPane.setMargin(eventPane, new Insets(98, 0, 0, 53));

		// for debugging turn the pane to red
		eventPane.setStyle("-fx-background-color : transparent");

		// TODO : Add listener on eventPane in order to add
		// event by mouse by clicking on the pane.
		// addEventListeners();

		BorderPane calendarPane = new BorderPane();
		calendarPane.prefWidthProperty().bind(scrollPane.widthProperty());
		calendarPane.setStyle("-fx-background-color : white");
		calendarPane.setPadding(new Insets(0, 16, 0, 0));

		mainPane.getChildren().addAll(calendarPane, eventPane);

		Pane gridEmptyPane = new Pane();
		gridEmptyPane.setPrefSize(50, 97);
		gridEmptyPane.getStyleClass().add("grid_empty_cell");

		Pane emptyPane = new Pane();
		emptyPane.setPrefSize(50, 22.5);

		VBox hourPane = new VBox();
		hourPane.setId("hourPane");
		hourPane.getChildren().addAll(gridEmptyPane, emptyPane);

		for (int i = 1; i <= 11; i++) {

			Label timeLabel = new Label(i + " am");
			timeLabel.setPrefSize(50, 49.5);
			timeLabel.setPadding(new Insets(0, 0, 0, 10));
			timeLabel.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);

			hourPane.getChildren().add(timeLabel);
		}

		Label timeLabel = new Label("12 pm");
		timeLabel.setPrefSize(50, 49.5);
		timeLabel.setPadding(new Insets(0, 0, 0, 10));
		timeLabel.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);

		hourPane.getChildren().add(timeLabel);

		for (int i = 1; i <= 11; i++) {

			timeLabel = new Label(i + " pm");
			timeLabel.setPrefSize(50, 49.5);
			timeLabel.setPadding(new Insets(0, 0, 0, 10));
			timeLabel.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);

			hourPane.getChildren().add(timeLabel);
		}

		gridPane = new GridPane();

		calendarPane.setLeft(hourPane);
		calendarPane.setCenter(gridPane);

		refreshCalendar(LocalDate.now());
	}

	public void refreshCalendar(LocalDate selectedDate) {
		
		// Don't update if nothing change
		if (prevDate == selectedDate) {
			return;
		}

		gridPane.getChildren().clear();

		// Go backward to get Sunday
		LocalDate sunday = selectedDate;
		while (sunday.getDayOfWeek() != DayOfWeek.SUNDAY) {
			sunday = sunday.minusDays(1);
		}

		String dayText[] = { "Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat" };
		for (int i = 0; i < 7; i++) {
			VBox columnEntry = new VBox();
			columnEntry.getStyleClass().add("calendar_cell");
			columnEntry.prefWidthProperty().bind(gridPane.widthProperty().divide(7.0));
			columnEntry.setPrefHeight(25);
			columnEntry.setPadding(new Insets(3, 0, 0, 5));

			Label dayName = new Label(dayText[i]);
			dayName.setPadding(new Insets(5, 0, 0, 5));

			Label dayCount = new Label(String.valueOf(sunday.getDayOfMonth()));
			dayCount.getStyleClass().add("calendar_label_xl");

			columnEntry.getChildren().addAll(dayName, dayCount);
			gridPane.add(columnEntry, i, 0);

			sunday = sunday.plusDays(1);
		}

		for (int i = 1; i <= 24; i++) {
			for (int j = 0; j < 7; j++) {
				Pane emptyPane = new Pane();
				emptyPane.getStyleClass().add("calendar_cell");
				emptyPane.prefWidthProperty().bind(gridPane.widthProperty().divide(7.0));
				emptyPane.setPrefHeight(50);
				gridPane.add(emptyPane, j, i);
			}
		}

		prevDate = selectedDate;
	}

}
