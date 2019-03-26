package controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Paint;
import model.PlainCurrentWeather;
import model.PlainCurrentWeatherLoader;

import java.util.*;

public class MainController {

    @FXML
    private TableView<PlainCurrentWeather> table;

    @FXML
    private ToggleButton update;

    @FXML
    private TextField input;

    private Timer updateTimer;

    @FXML
    public void initialize(){
        updateTimer = new Timer();
        input.setOnKeyPressed((e)->{
            if(e.getCode() == KeyCode.ENTER)addAction();
        });

        table.getColumns().clear();
        pushTableColumn("Miejscowość","city");
        pushTableColumn("Państwo","country");
        pushTableColumn("Temperatura","temperature");
        pushTableColumn("Wilgotność powietrza","humidity");
        pushTableColumn("Ciśnienie atmosferyczne","pressure");
        pushTableColumn("Prędkość wiatru","windSpeed");
        pushTableColumn("Pogoda","weatherName");
    }

    private void pushTableColumn(String columnName, String propertyName){
        TableColumn<PlainCurrentWeather, String> column = new TableColumn<>(columnName);
        column.setCellValueFactory(new PropertyValueFactory<>(propertyName));
        table.getColumns().add(column);
    }

    @FXML
    public void addAction(){
        PlainCurrentWeather weather = PlainCurrentWeatherLoader.load(input.getText());
        if(weather != null) {
            table.getItems().add(weather);
            input.setText("");
        }
    }

    @FXML
    public void updateAction(){
        if(update.isSelected()) {
            updateTimer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    Platform.runLater(MainController.this::updateTable);
                }
            },0,6000);
        } else {
            updateTimer.cancel();
            updateTimer = new Timer();
        }
    }

    private void updateTable(){
        List<PlainCurrentWeather> newList = new ArrayList<>();
        PlainCurrentWeather plainCurrentWeather;
        for(PlainCurrentWeather it : table.getItems()){
           plainCurrentWeather = PlainCurrentWeatherLoader.load(it.toString());
           if(plainCurrentWeather != null){
               newList.add(plainCurrentWeather);
           }
        }
        table.getItems().setAll(newList);
        update.setTextFill(Paint.valueOf("#46d04d"));
        updateTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(()->update.setTextFill(Paint.valueOf("black")));
            }
        },1000);
    }

    @FXML
    public void exitAction(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Kończenie pracy");
        alert.setHeaderText("Czy napewno chcesz wyjść z programu?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK){
            Platform.exit();
        }
    }
}
