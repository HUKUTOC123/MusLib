package Server.gsonConverter;

import com.google.gson.*;
import sample.ForProject.Track;
import sample.ForProject.MessageType;
import Server.request.GeneralMessage;
import Server.request.Message;

public class CustomConverter implements JsonSerializer<Message>, JsonDeserializer<Message> {
    public JsonElement serialize(Message src, java.lang.reflect.Type type,
                                 JsonSerializationContext context) {
        JsonObject object = new JsonObject();
        object.addProperty("message", src.getMessage().name());
        object.add("object", context.serialize(src.getObject(), Track.class));
        object.addProperty("index", src.getIndex());
        return object;
    }

    @Override
    public Message deserialize(JsonElement json, java.lang.reflect.Type type, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        Message message = new GeneralMessage();
        message.setMessage(MessageType.valueOf(jsonObject.get("message").getAsString()));
        message.setObject(context.deserialize(jsonObject.get("object"), Track.class));
        message.setIndex(jsonObject.get("index").getAsInt());
        return message;
    }
}
