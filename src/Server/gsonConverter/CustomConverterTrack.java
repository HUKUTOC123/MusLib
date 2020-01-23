package Server.gsonConverter;

import com.google.gson.*;
import sample.ForProject.Track;

import java.lang.reflect.Type;
import java.util.Date;

/**
 * Класс для сериализиции и десериалиции обекта типа Track
 */
public class CustomConverterTrack implements JsonSerializer<Track>, JsonDeserializer<Track> {

    /**
     * Дессериализия JsonObject объекта в объект типа Track
     *
     * @param json
     * @param type
     * @param context
     * @return возвращиет объект типа Track
     */
    @Override
    public Track deserialize(JsonElement json, Type type, JsonDeserializationContext context) {
        JsonObject jsonObject=json.getAsJsonObject();
        Track track=new Track();
        track.setNumberTrack(jsonObject.get("id").getAsInt());
        track.setNameTrack(jsonObject.get("nameTrack").getAsString());
        track.setNameGenre(jsonObject.get("nameGenre").getAsString());
        track.setAlbumTitle(jsonObject.get("titleAlbum").getAsString());
        track.setRecordLength(new Date(jsonObject.get("recordLength").getAsLong()));
        track.setPerformerName(jsonObject.get("performerName").getAsString());
        return track;
    }

    /**
     * Сериализаця Track объекта в JsonObject
     *
     * @param track объект который сериализуется
     * @param type тип сериализуемого объекта
     * @param context с его помощью, мы можим сериализовать не примитивные типы
     * @return возвращает объект типа JsonObject
     */
    @Override
    public JsonElement serialize(Track track, Type type, JsonSerializationContext context) {
        JsonObject result=new JsonObject();
        result.addProperty("id",track.getNumberTrack());
        result.addProperty("nameTrack",track.getNameTrack());
        result.addProperty("nameGenre",track.getNameGenre());
        result.addProperty("titleAlbum",track.getTitleAlbum());
        result.addProperty("recordLength", track.getRecordLength().getTime());
        result.addProperty("performerName",track.getPerformerName());
        return  result;
    }
}
