package Server.threadedHandler;

import Server.gsonConverter.CustomConverter;
import Server.gsonConverter.CustomConverterTrack;
import Server.request.GeneralMessage;
import Server.request.Message;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import sample.ForProject.Track;
import sample.UIControl.AddWindow;

import java.io.*;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.*;

public class ThreadedHandler implements Runnable {
    private Socket incoming;
    private List<Track> tracks = getTracks();


    public ThreadedHandler(Socket incoming) {
        this.incoming = incoming;
    }

    @Override
    public void run() {
        try (DataOutputStream write = new DataOutputStream(
                incoming.getOutputStream());
             DataInputStream read = new DataInputStream(
                     incoming.getInputStream())
        ) {
            while (true) {
                while (read.available() == 0) {
                }
                String json = "";
                while (read.available() > 0) {
                    try {
                        json = read.readUTF();
                    } catch (IOException e) {
                        System.out.println("Ошибка приема сообщения");
                    }
                }
                String getTracksJson;
                // сериализатор-десериализатор
                Gson gson = new GsonBuilder()
                        .setPrettyPrinting()
                        .registerTypeAdapter(GeneralMessage.class, new CustomConverter())
                        .registerTypeAdapter(Track.class, new CustomConverterTrack())
                        .create();
                //десериализуем запрос
                Message message = gson.fromJson(json, GeneralMessage.class);
                switch (message.getMessage()) {
                    case getTrack:
                        //сериализуем список треков и отправляем клиенту
                        getTracksJson = new Gson().toJson(tracks);
                        write.writeUTF(getTracksJson);
                        write.flush();
                        getTracks();
                        break;

                    case deleteTrack:
                        //получаем из запроса индекс элемента на удаление
                        int i = message.getIndex();
                        //удаяем его из списка

                        for (int j = 0; j < tracks.size(); j++) {
                            if (tracks.get(j).getNumberTrack() == i) {
                                tracks.remove(j);
                                break;
                            }
                        }
                        AddWindow.serialisationTrackLib((ArrayList<Track>) tracks);
                        //получившийся список сериализуем и отправляем клиенту
                        getTracksJson = new Gson().toJson(tracks);
                        write.writeUTF(getTracksJson);
                        write.flush();

                        break;
                    case editTrack:
                        //получаем объект на изменение
                        Track trackEdit = message.getObject();
                        //получаем его индекс
                        int indexEdit = message.getIndex();
                        //сортируем список чтобы индекс не отличался от сортированного списка клиента
                        Collections.sort(tracks);
                        //изменяем объект списка
                        for (int j = 0; j < tracks.size(); j++) {
                            if (tracks.get(j).getNumberTrack() == indexEdit) {
                                tracks.set(j, trackEdit);
                                break;
                            }
                        }
                        AddWindow.serialisationTrackLib((ArrayList<Track>) tracks);
                        //сериализуем список и отправляем клиенту
                        getTracksJson = new Gson().toJson(tracks);
                        write.writeUTF(getTracksJson);
                        write.flush();

                        break;
                    case addTrack:
                        Track trackAdd = (Track) message.getObject();
                        tracks.add(trackAdd);
                        Collections.sort(tracks);
                        getTracksJson = new Gson().toJson(tracks);
                        write.writeUTF(getTracksJson);
                        write.flush();
                        break;
                    default:
                        System.out.println("Нет такого сообщения");
                        break;
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * тестовый список
     */
    public ArrayList<Track> getTracks()  {
        /*Track track = new Track();
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
    }*/
        ArrayList <Track> Out = AddWindow.deserialisationTrackLib();
       return  Out;
    }
}
