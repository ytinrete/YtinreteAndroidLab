package com.ytinrete.tools;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Collection;


public class JsonSerializer {
  private static final String TAG = JsonSerializer.class.getName();

  private static JsonSerializer instance = new JsonSerializer();

  private ObjectMapper mapper;

  private JsonSerializer() {
    mapper = new ObjectMapper();
    mapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES,
        false);
  }

  public static JsonSerializer getInstance() {
    if (instance == null) {
      instance = new JsonSerializer();
    }
    return instance;
  }

  public ObjectMapper getMappter() {
    return mapper;
  }

  public String serialize(Object object) {
    try {
      return mapper.writeValueAsString(object);
    } catch (Exception e) {
      AppLog.e(TAG, e.getMessage());
      return null;
    }
  }

  public <T> T deserialize(String json, Class<T> clazz) {
    try {
      return mapper.readValue(json, clazz);
    } catch (Exception e) {
      AppLog.e(TAG, e.getMessage());
      return null;
    }
  }

  public <T> T deserialize(Reader reader, Class<T> clazz) {
    try {
      return mapper.readValue(reader, clazz);
    } catch (Exception e) {
      AppLog.e(TAG, e.getMessage());
      return null;
    }
  }

  /**
   * 不吞掉异常
   *
   * @param reader
   * @param clazz
   * @param <T>
   * @return
   */
  public <T> T deserializeFrankly(Reader reader, Class<T> clazz) throws IOException, JsonParseException, JsonMappingException {
    return mapper.readValue(reader, clazz);
  }

  public <T extends Collection<V>, V> T deserialize(Reader reader, Class<T> collection, Class<V> data) {
    try {
      return mapper.readValue(reader,
          mapper.getTypeFactory().constructCollectionType(collection, data));
    } catch (Exception e) {
      AppLog.e(TAG, e.getMessage(), e);
      return null;
    }
  }


  public <T> ArrayList<T> deserializeList(Reader reader, Class<T> data) {
    try {
      return mapper.readValue(reader,
          mapper.getTypeFactory().constructCollectionType(ArrayList.class, data));
    } catch (Exception e) {
      AppLog.e(TAG, e.getMessage(), e);
      return null;
    }
  }
}
