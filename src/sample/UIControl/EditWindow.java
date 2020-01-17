package sample.UIControl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import sample.ForProject.Track;

public class EditWindow {
    private Stage stage;
    private Track track;
    private DateTimeFormatter formatter;
    private SimpleDateFormat dateFormat;


    @FXML
    private ResourceBundle resources;

    @FXML
    private Button edit;

    @FXML
    private Button cancel;


    @FXML
    private Label id;

    @FXML
    private TextField nameTrack;

    @FXML
    private TextField performer;

    @FXML
    private TextField genre;

    @FXML
    private TextField album;

    @FXML
    private TextField time;

    @FXML
    private DatePicker dataPicker;


    @FXML
    private void initialize(){
        formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        StringConverter<Date> convertDate = new StringConverter<Date>() {
            @Override
            public String toString(Date date) {
                if (date != null) {
                    return dateFormat.format(date);
                } else {
                    return "";
                }
            }

            @Override
            public Date fromString(String stringDate) {
                if (stringDate != null && !stringDate.isEmpty()) {
                    try {
                        return dateFormat.parse(stringDate);
                    } catch (ParseException e) {
                        e.printStackTrace();

                    }
                } else {
                    return null;
                }
                return null;
            }
        };
    }
    public void  setEditStage(Stage stage){
        this.stage=stage;
    }
    public void edit(ActionEvent actionEvent) throws ParseException {
        track.setNameTrack(nameTrack.getText());
        track.setPerformerName(performer.getText());
        track.setNameGenre(genre.getText());
        track.setAlbumTitle(album.getText());
        String dateString = dataPicker.getValue().format(formatter) + " " + time.getText();
        track.setRecordLength(new SimpleDateFormat("dd.MM.yyyy HH:mm").parse(dateString));
        stage.close();

    }

    public void setEditTrack(Track track){
        /*this.track=track;
        id.setText(Integer.toString(track.getNumberTrack()));
        nameTrack.setText(track.getNameTrack());
        performer.setText(track.getPerformerName());
        genre.setText(track.getNameGenre());
        album.setText(track.getTitleAlbum());
        String[] dateString=converterDateToString(track.getRecordLength())[0].split("\\.");
        int day=Integer.parseInt(dateString[0]);
        int mouth=Integer.parseInt(dateString[1]);
        int year=Integer.parseInt(dateString[2]);
        dataPicker.setValue(LocalDate.of(year,mouth,day));
        time.setText(converterDateToString(track.getRecordLength())[1]);*/
    }

    private String[] converterDateToString (Date date){
        dateFormat=new SimpleDateFormat("dd.MM.yyyy");
        String[] masStringDate=new String[2];
        masStringDate[0]=dateFormat.format(date);
        SimpleDateFormat format2=new SimpleDateFormat("HH:mm");
        masStringDate[1]=format2.format(date);
        return masStringDate;
    }

    public void cancel(ActionEvent actionEvent) {
        stage.close();
    }


}
