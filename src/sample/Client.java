package sample;

import Server.gsonConverter.CustomConverter;
import Server.gsonConverter.CustomConverterTrack;
import Server.request.GeneralMessage;
import Server.request.Message;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javafx.application.Platform;

import sample.ForProject.MessageType;
import sample.ForProject.Track;
import sample.ForProject.TrackLST;
import sample.UIControl.MainWindow;
import sample.UIControl.ThreadAlert;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Properties;
import java.util.concurrent.locks.ReentrantLock;

public class Client {
    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;
    private Message message;
    private Gson gson;
    private String jsonRequest;
    private MainWindow controller;
    private boolean threadStop=false;
    private Thread thread;
    private ReentrantLock look;
    private Properties property;
    private FileInputStream fil;

    /**
     *
     * В конструкторе происходит инициализация сокета
     * и его потоков ввода/вывода
     */
    public Client(MainWindow controller)throws IOException{
        fil=new FileInputStream("src/sample/config.properties");
        property=new Properties();
        property.load(fil);
        String host=property.getProperty("may.host");
        String port=property.getProperty("may.port");
        socket = new Socket(host, Integer.parseInt(port));
        in = new DataInputStream(socket.getInputStream());
        out = new DataOutputStream(socket.getOutputStream());
        this.controller=controller;
        System.out.println("Init Client");
        look=new ReentrantLock();
        thread=new Thread(new ThreadAlert(this));
        thread.start();
    }

    /**
     * Метод создающий запрос, создающий Gson объект для серелиализаци и десериализации данных
     * и переносящий запрос в форму строки
     *
     */
    public void receivingMessage(MessageType message, Track obj, int index) throws IOException {
        this.message =new GeneralMessage(message,obj ,index);
        gson = new GsonBuilder()
                .setPrettyPrinting()
                .registerTypeAdapter(GeneralMessage.class, new CustomConverter())
                .registerTypeAdapter(Track.class, new CustomConverterTrack())
                .create();
        jsonRequest=gson.toJson(this.message);
        try {
            out.writeUTF(jsonRequest);
            out.flush();
        } catch (IOException e) {
            System.out.println("Ошибка отправки сообщения");
        }
    }

    /**
     * Перед закрытием приложения будут закрыты все потоки и соккет
     */
    public void stop() throws IOException {
        threadStop=true;
        out.close();
        in.close();
        socket.close();
    }

    /**
     * Метод постоянно ждущий оповещение об изменениях от сервера
     */
    public void handlerAlerts(String incomingMessage){
        Type type = new ListParameterizedType(Track.class);
        TrackLST list = new TrackLST(new Gson().fromJson(incomingMessage, type));
        controller.listTracks(list.getJournal(),"Список обновлен");
    }

    public void waitingMessage(){
        while (!threadStop) {
            try {
                if (in.available() > 0) {
                    look.lock();
                    String incomingMessage = in.readUTF();
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            handlerAlerts(incomingMessage);
                        }
                    });
                    look.unlock();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    /**
     * Класс реализующий десериализацию списка элементов в объект
     */
    private static class ListParameterizedType implements ParameterizedType {

        private Type type;

        private ListParameterizedType(Type type) {
            this.type = type;
        }

        @Override
        public Type[] getActualTypeArguments() {
            return new Type[] {type};
        }

        @Override
        public Type getRawType() {
            return ArrayList.class;
        }

        @Override
        public Type getOwnerType() {
            return null;
        }

    }
}

