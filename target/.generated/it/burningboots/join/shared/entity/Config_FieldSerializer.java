package it.burningboots.join.shared.entity;

import com.google.gwt.user.client.rpc.SerializationException;
import com.google.gwt.user.client.rpc.SerializationStreamReader;
import com.google.gwt.user.client.rpc.SerializationStreamWriter;
import com.google.gwt.user.client.rpc.impl.ReflectionHelper;

@SuppressWarnings("deprecation")
public class Config_FieldSerializer implements com.google.gwt.user.client.rpc.impl.TypeHandler {
  private static native java.lang.Integer getId(it.burningboots.join.shared.entity.Config instance) /*-{
    return instance.@it.burningboots.join.shared.entity.Config::id;
  }-*/;
  
  private static native void setId(it.burningboots.join.shared.entity.Config instance, java.lang.Integer value) 
  /*-{
    instance.@it.burningboots.join.shared.entity.Config::id = value;
  }-*/;
  
  private static native java.lang.String getName(it.burningboots.join.shared.entity.Config instance) /*-{
    return instance.@it.burningboots.join.shared.entity.Config::name;
  }-*/;
  
  private static native void setName(it.burningboots.join.shared.entity.Config instance, java.lang.String value) 
  /*-{
    instance.@it.burningboots.join.shared.entity.Config::name = value;
  }-*/;
  
  private static native java.lang.String getVal(it.burningboots.join.shared.entity.Config instance) /*-{
    return instance.@it.burningboots.join.shared.entity.Config::val;
  }-*/;
  
  private static native void setVal(it.burningboots.join.shared.entity.Config instance, java.lang.String value) 
  /*-{
    instance.@it.burningboots.join.shared.entity.Config::val = value;
  }-*/;
  
  public static void deserialize(SerializationStreamReader streamReader, it.burningboots.join.shared.entity.Config instance) throws SerializationException {
    setId(instance, (java.lang.Integer) streamReader.readObject());
    setName(instance, streamReader.readString());
    setVal(instance, streamReader.readString());
    
  }
  
  public static it.burningboots.join.shared.entity.Config instantiate(SerializationStreamReader streamReader) throws SerializationException {
    return new it.burningboots.join.shared.entity.Config();
  }
  
  public static void serialize(SerializationStreamWriter streamWriter, it.burningboots.join.shared.entity.Config instance) throws SerializationException {
    streamWriter.writeObject(getId(instance));
    streamWriter.writeString(getName(instance));
    streamWriter.writeString(getVal(instance));
    
  }
  
  public Object create(SerializationStreamReader reader) throws SerializationException {
    return it.burningboots.join.shared.entity.Config_FieldSerializer.instantiate(reader);
  }
  
  public void deserial(SerializationStreamReader reader, Object object) throws SerializationException {
    it.burningboots.join.shared.entity.Config_FieldSerializer.deserialize(reader, (it.burningboots.join.shared.entity.Config)object);
  }
  
  public void serial(SerializationStreamWriter writer, Object object) throws SerializationException {
    it.burningboots.join.shared.entity.Config_FieldSerializer.serialize(writer, (it.burningboots.join.shared.entity.Config)object);
  }
  
}
