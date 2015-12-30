package it.burningboots.join.shared;

import com.google.gwt.user.client.rpc.SerializationException;
import com.google.gwt.user.client.rpc.SerializationStreamReader;
import com.google.gwt.user.client.rpc.SerializationStreamWriter;
import com.google.gwt.user.client.rpc.impl.ReflectionHelper;

@SuppressWarnings("deprecation")
public class SystemException_FieldSerializer implements com.google.gwt.user.client.rpc.impl.TypeHandler {
  private static native java.lang.String getMessage(it.burningboots.join.shared.SystemException instance) /*-{
    return instance.@it.burningboots.join.shared.SystemException::message;
  }-*/;
  
  private static native void setMessage(it.burningboots.join.shared.SystemException instance, java.lang.String value) 
  /*-{
    instance.@it.burningboots.join.shared.SystemException::message = value;
  }-*/;
  
  public static void deserialize(SerializationStreamReader streamReader, it.burningboots.join.shared.SystemException instance) throws SerializationException {
    setMessage(instance, streamReader.readString());
    
    com.google.gwt.user.client.rpc.core.java.lang.Exception_FieldSerializer.deserialize(streamReader, instance);
  }
  
  public static it.burningboots.join.shared.SystemException instantiate(SerializationStreamReader streamReader) throws SerializationException {
    return new it.burningboots.join.shared.SystemException();
  }
  
  public static void serialize(SerializationStreamWriter streamWriter, it.burningboots.join.shared.SystemException instance) throws SerializationException {
    streamWriter.writeString(getMessage(instance));
    
    com.google.gwt.user.client.rpc.core.java.lang.Exception_FieldSerializer.serialize(streamWriter, instance);
  }
  
  public Object create(SerializationStreamReader reader) throws SerializationException {
    return it.burningboots.join.shared.SystemException_FieldSerializer.instantiate(reader);
  }
  
  public void deserial(SerializationStreamReader reader, Object object) throws SerializationException {
    it.burningboots.join.shared.SystemException_FieldSerializer.deserialize(reader, (it.burningboots.join.shared.SystemException)object);
  }
  
  public void serial(SerializationStreamWriter writer, Object object) throws SerializationException {
    it.burningboots.join.shared.SystemException_FieldSerializer.serialize(writer, (it.burningboots.join.shared.SystemException)object);
  }
  
}
