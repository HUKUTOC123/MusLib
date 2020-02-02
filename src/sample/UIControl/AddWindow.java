package sample.UIControl;

import java.io.*;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.*;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import sample.ForProject.Track;

public class AddWindow implements Serializable{
    private DateTimeFormatter formatter;
    private Stage stage;
    private Track track;
    private SimpleDateFormat dateFormat;
    private ArrayList<Track> oldTracks = new ArrayList<>();


    private boolean flag = false;

    @FXML
    private DatePicker datePicker;

    @FXML
    private TextField id;

    @FXML
    private TextField performer;

    @FXML
    private TextField nameTrack;

    @FXML
    private TextField genre;

    @FXML
    private TextField album;

    @FXML
    private TextField time;


    public Track getTrack() {
        return track;
    }
/*
Инициализация formatter для перевода из LocalDate в String,
    dateFormat для вывода в таблицу*/
    @FXML
    void initialize() {

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
    public void setStage(Stage stage,ArrayList<Track> oldTracks) {
        this.stage = stage;
        this.oldTracks=oldTracks;
    }

    @FXML
    public void cancel(javafx.event.ActionEvent actionEvent) {
        stage.close();
    }

    @FXML
    public void add(ActionEvent actionEvent) throws ParseException {
        track = new Track();
        SimpleDateFormat format = new SimpleDateFormat();
        format.applyPattern("dd.MM.yyyy HH:mm");
        if (inputCheck()) {
            track.setNumberTrack(Integer.parseInt(id.getText()));
            track.setNameTrack(nameTrack.getText());
            track.setPerformerName(performer.getText());
            track.setNameGenre(genre.getText());
            track.setAlbumTitle(album.getText());
            String dateString = datePicker.getValue().format(formatter) + " " + time.getText();
            track.setRecordLength(new SimpleDateFormat("dd.MM.yyyy HH:mm").parse(dateString));
            oldTracks.add(track);
            serialisationTrackLib(oldTracks);
            stage.close();
            flag = true;
        }
    }

    public static void serialisationTrackLib( ArrayList<Track> TracksList)  {
        try{
            FileOutputStream fos= new FileOutputStream("file.txt");
            ObjectOutputStream oos= new ObjectOutputStream(fos);
            oos.writeObject(TracksList);
            oos.close();
            fos.close();
        }catch(IOException ioe){
            ioe.printStackTrace();
        }
    }

    public static ArrayList<Track> deserialisationTrackLib() {
        ArrayList<Track> newTrackList= new ArrayList<>();
        try
        {
            FileInputStream fis = new FileInputStream("file.txt");
            ObjectInputStream ois = new ObjectInputStream(fis);
            newTrackList = (ArrayList) ois.readObject();
            ois.close();
            fis.close();
        }catch(IOException ioe){
            ioe.printStackTrace();

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return newTrackList;
    }
public ArrayList<Track> listFromFile(){
    File sourceFile = new File("output.txt");
    String s ;
        SimpleDateFormat format = new SimpleDateFormat();
        format.applyPattern("dd.MM.yyyy HH:mm");
        ArrayList <Track> Out = new ArrayList<>();
    try {
        BufferedReader reader = new BufferedReader(new FileReader(sourceFile));
        while ((s = reader.readLine()) != null) {
            String[] string = s.split("//");
            String numberTrack = string[0];
            String nameTrack = string[1];
            String nameGenre = string[2];
            String PerformerName = string[3];
            String titleAlbum = string[4];
            String rec = string[5];
            track.setNumberTrack(Integer.parseInt(numberTrack));
            track.setNameTrack(nameTrack);
            track.setPerformerName(PerformerName);
            track.setNameGenre(nameGenre);
            track.setAlbumTitle(titleAlbum);
            track.setRecordLength(format.parse(rec));
            Out.add(track);
        }
    }
        catch (Exception ioe) {
            ioe.printStackTrace();

        }
    return Out;
}

    private boolean inputCheck() {
        String errorMessage = "";
        if ((id.getText() == null) || (id.getText().length() == 0)) {
            errorMessage += "Заполните поле номера!";
        } else {

            try {
                if(searchId(Integer.parseInt(id.getText()))){
                    errorMessage += "Значение повторяется!";
                }
            } catch (NumberFormatException e) {
                errorMessage += "Неверный формат!";
            }
        }

        if (nameTrack.getText() == null) {
            errorMessage += "Заполните поле названия!";
        }
        if ( (datePicker.getValue() == null) || ((time.getText() == null) || (time.getText().length() == 0))) {
            errorMessage += "Заполните дату и длительность!";
        } else {
            try {
                String dateString = datePicker.getValue().format(formatter) + " " + time.getText();
                Date date = new SimpleDateFormat("dd.MM.yyyy HH:mm").parse(dateString);
            } catch (ParseException e) {
                errorMessage += "Неверный формат!";
            }
        }
        if (performer.getText() == null) {
            errorMessage += "Заполните имя исполнителя!";
        }
        if (genre.getText() == null) {
            errorMessage += "Заполните жанр!";
        }
        if (album.getText()== null){
            errorMessage += "Заполните название альбома!";
        }
        if (errorMessage.length() == 0) {
            return true;
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(stage);
            alert.setTitle("Неверное значение");
            alert.setHeaderText("Введите корректные данные");
            alert.setContentText(errorMessage);
            alert.showAndWait();

            return false;
        }

    }

        public boolean searchId(int id){
            for(Track track:oldTracks){
                if (track.getNumberTrack()==id){
                    return true;
                }
            }
            return false;
        }
    public boolean isOnClick() {
        return flag;
    }
}
