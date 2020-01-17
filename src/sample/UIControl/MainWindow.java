package sample.UIControl;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import sample.ForProject.MessageType;
import sample.ForProject.Track;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;

public class MainWindow {
    private Stage stage;
    private List<Track> trackList;


    @FXML
    private TableView<Track> tableTracks;

    @FXML
    private TableColumn<Track, Integer> id;

    @FXML
    private TableColumn<Track, String> performer;

    @FXML
    private TableColumn<Track, String> nameTrack;

    @FXML
    private TableColumn<Track, String> genre;

    @FXML
    private TableColumn<Track, String> album;

    @FXML
    private TableColumn<Track, String> time;

    @FXML
    private TableColumn<Track, String> date;


    @FXML
    private Label labelM;

    @FXML
    private Label labelS;

    @FXML
    private Button add;

    @FXML
    private Button exit;

    @FXML
    private Button delete;

    @FXML
    private Button edit;

    @FXML
    private TextField searchField;

    @FXML
    private Button search;

    @FXML
    private Button getTracks;


/*
      Инициализация таблицы и присвоение типов переменным
*/
    @FXML
    private void initialize() {

        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        performer.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Track, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Track, String> param) {
                return getPerformer(param);
            }
        });
        nameTrack.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Track, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Track, String> param) {
                return getName(param);
            }
        });
        genre.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Track, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Track, String> param) {
                return getGenre(param);
            }
        });
        album.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Track, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Track, String> param) {
                return getAlbum(param);
            }
        });
        date.setCellValueFactory(param -> getDateFromDate(param));
        time.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Track, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Track, String> param) {
                return getTime(param);
            }
        });
    }

/*
    Методы, переводящие значения в значения,понятные таблице
*/
    private StringProperty getDateFromDate(TableColumn.CellDataFeatures<Track, String> param) {
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
        StringProperty formatDate = new SimpleStringProperty(format.format(param.getValue().getRecordLength()));
        System.out.println(format.format(param.getValue().getRecordLength()));
        return formatDate;
    }
    private StringProperty getPerformer(TableColumn.CellDataFeatures<Track, String> param) {
        return new SimpleStringProperty(param.getValue().getPerformerName());
    }

    private StringProperty getName(TableColumn.CellDataFeatures<Track, String> param) {
        return new SimpleStringProperty(param.getValue().getNameTrack());
    }

    private StringProperty getGenre(TableColumn.CellDataFeatures<Track, String> param) {
        return new SimpleStringProperty(param.getValue().getNameGenre());
    }
    public void start(Stage stage) {
        this.stage = stage;


    }

    private StringProperty getAlbum(TableColumn.CellDataFeatures<Track, String> param) {
        return new SimpleStringProperty(param.getValue().getNameGenre());
    }

    private StringProperty getTime(TableColumn.CellDataFeatures<Track, String> param) {
        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        StringProperty formatDate = new SimpleStringProperty(format.format(param.getValue().getRecordLength()));
        System.out.println(format.format(param.getValue().getRecordLength()));
        return formatDate;
    }
    /**
     * Метод для сортировки списка треков и преобразования списка в тип ObservableArrayList
     */
    public void listTracks(List<Track> list ) {
        trackList = list;
        Collections.sort(trackList);
        initTableData(FXCollections.observableArrayList(trackList));
    }

    /*Метод инициализации таблицы*/
    public void initTableData(ObservableList<Track> list) {
        tableTracks.setItems(list);
    }
  /* Метод, реагирующий на нажатие кнопки клиентом
    Создание новой сцены, где вводятся значения*/
    @FXML
    public void add(ActionEvent actionEvent) {
        Stage newStage = new Stage();
        FXMLLoader addLoader = new FXMLLoader();
        newStage.setTitle(MessageType.addTrack.name());
        URL xmlUrl = getClass().getResource("/sample/fxml/addSample.fxml");
        addLoader.setLocation(xmlUrl);
        try {
            Parent newRoot = addLoader.load();
            Scene newScene = new Scene(newRoot);
            newStage.setScene(newScene);
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error");
            alert.setHeaderText(e.getMessage());
            alert.showAndWait();
        }
        newStage.initModality(Modality.APPLICATION_MODAL);
        AddWindow controller = addLoader.getController();
        controller.setStage(newStage,trackList);
        newStage.showAndWait();
    }

    @FXML
    public void exit(ActionEvent actionEvent) {
        if (1 == showConfirmation())
            actionEvent.consume();
    }

    public int showConfirmation() {
        Alert exitAlert = new Alert(Alert.AlertType.CONFIRMATION);
        exitAlert.setTitle("Window exit");
        exitAlert.setHeaderText("Вы действительно хотите выйти?");
        Optional<ButtonType> optional = exitAlert.showAndWait();
        if (optional.get() == ButtonType.OK) {
            System.exit(0);

        }
        if (optional.get() == ButtonType.CANCEL) {
            try {
                return 1;
            } catch (Exception e) {
            }
        }
        return 0;
    }

    @FXML
    public void delete(ActionEvent actionEvent) throws IOException {
        int index = tableTracks.getSelectionModel().getSelectedIndex();
        if (index > -1) {
            tableTracks.getItems().remove(index);
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No Selection");
            alert.setHeaderText("Удаляемый трек не выбран!");
            alert.setContentText("Выберите трек в таблице");
            alert.showAndWait();
        }
    }
    @FXML
    public void edit(ActionEvent actionEvent) throws IOException {
        Stage editStage = new Stage();
        FXMLLoader editLoader = new FXMLLoader();
        editStage.setTitle(MessageType.editTrack.name());
        URL editURL = getClass().getResource("/sample/fxml/editSample.fxml");
        editLoader.setLocation(editURL);
        Parent root = editLoader.load();
        Scene editScene = new Scene(root);
        editStage.setScene(editScene);
        editStage.initModality(Modality.APPLICATION_MODAL);
        EditWindow controller = editLoader.getController();

            controller.setEditStage(editStage);
            Track trackEdit = tableTracks.getSelectionModel().getSelectedItem();
            controller.setEditTrack(trackEdit);
            editStage.showAndWait();

    }


    public void search(ActionEvent actionEvent) {
        List<Track> listSearch = new ArrayList<Track>();
        if (isClick()) {
            String searchString = searchField.getText();
            for (Track track : trackList) {
                int i = 0; //текущее положение курсора
                boolean flag = false; //зашел в цикл поиска
                int lengthSearch = searchString.length();
                int lengthTrack = track.toString().length();
                exit:
                while (i + lengthSearch < lengthTrack - 1) {
                    int j = 0;
                    flag = false;
                    while ((searchString.charAt(j) == track.toString().charAt(i)) && (j <= lengthSearch - 1)) {
                        if (j == lengthSearch - 1) {
                            listSearch.add(track);
                            System.out.println(track);
                            break exit;
                        }
                        flag = true;
                        i++;
                        j++;
                    }
                    if (!flag) {
                        i++;
                    }
                }
            }
        }
    }
    private boolean isClick() {
        String messageError = "";
        if (searchField.getText().length() == 0) {
            messageError += "Nothing entered ib the field";
        }
        if (messageError.length() != 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(stage);
            alert.setTitle("Invalid Fields");
            alert.setHeaderText("Please correct invalid fields");
            alert.setContentText(messageError);
            alert.showAndWait();

            return false;
        } else {
            return true;
        }
    }

    @FXML
    void getTracks(ActionEvent event) {
        start(stage);
    }


}
















