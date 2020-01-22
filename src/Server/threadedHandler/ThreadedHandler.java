package Server.threadedHandler;

import Server.gsonConverter.CustomConverter;
import Server.gsonConverter.CustomConverterTrack;
import Server.request.GeneralMessage;
import Server.request.Message;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import sample.ForProject.Track;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.*;

public class ThreadedHandler implements Runnable {
    private Socket incoming;
    private List<Track> tracks =getTracks();


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
                Gson  gson = new GsonBuilder()
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
                        break;

                    case deleteTrack:
                        //получаем из запроса индекс элемента на удаление
                        int i= message.getIndex();
                        //удаяем его из списка
                        for(int j=0;j<tracks.size(); j++) {
                            if(tracks.get(j).getNumberTrack()==i){
                                tracks.remove(j);
                                break;
                            }
                        }
                        //получившийся список сериализуем и отправляем клиенту
                        getTracksJson = new Gson().toJson(tracks);
                        write.writeUTF(getTracksJson);
                        write.flush();
                        break;
                    case editTrack:
                        //получаем объект на изменение
                        Track trackEdit= message.getObject();
                        //получаем его индекс
                        int indexEdit= message.getIndex();
                        //сортируем список чтобы индекс не отличался от сортированного списка клиента
                        Collections.sort(tracks);
                        //изменяем объект списка
                        for(int j=0;j<tracks.size(); j++) {
                            if(tracks.get(j).getNumberTrack()==indexEdit){
                                tracks.set(j,trackEdit);
                                break;
                            }
                        }
                        //сериализуем список и отправляем клиенту
                        getTracksJson = new Gson().toJson(tracks);
                        write.writeUTF(getTracksJson);
                        write.flush();
                        break;
                    case addTrack:
                        Track trackAdd=(Track) message.getObject();
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
     *
     * @return возвращает список ArrayList типа с элементами типа Track
     */
    public ArrayList<Track> getTracks() {
        GregorianCalendar calendar1 = new GregorianCalendar(2019, 11, 05, 16, 00);
        Date date1 = calendar1.getTime();
        ArrayList<Track> list = new ArrayList<Track>();
        Track track1 = new Track(1,"Город 312", "Вне зоны доступа", "Поп", "Лучшее 2006", date1 );
        list.add(track1);
        return list;
    }
}
