package Server.request;

import sample.ForProject.Track;
import sample.ForProject.Track;

public interface Request {
    /**
     * Сообщение запроса
     */
    String getMessage();

    /**
     * Метод изменяющий сообщение запроса
     *
     * @param message сообщение запроса
     */
    void setMessage(String message);

    /**
     * Метод возвращающий объект запроса
     */
    Object getObject();

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
