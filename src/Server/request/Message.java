package Server.request;

import sample.ForProject.Track;
import sample.ForProject.MessageType;

public interface Message {
    /**
     * Сообщение запроса
     */
    MessageType getMessage();

    /**
     * Метод изменяющий сообщение запроса
     *
     * @param message сообщение запроса
     */
    void setMessage(MessageType message);

    /**
     * Метод возвращающий объект запроса
     */
    Track getObject();

    /**
     * Метод изменяющий объект запроса.
     *
     * @param object объект запроса
     */
    void setObject(Track object);

    /**
     * Метод возвращающий индекс запроса.
     * Если в запросе не нужне индекс, возвращает значение -1.
     *
     * @return
     */
    int getIndex();

    /**
     * Метод изменяющий индекс запроса
     *
     * @param index индекс запроса
     */
    void setIndex(int index);
}
