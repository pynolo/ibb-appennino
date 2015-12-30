package it.burningboots.join.shared;

import com.google.gwt.user.client.rpc.SerializationException;
import com.google.gwt.user.client.rpc.SerializationStreamReader;
import com.google.gwt.user.client.rpc.SerializationStreamWriter;
import com.google.gwt.user.client.rpc.impl.ReflectionHelper;

@SuppressWarnings("deprecation")
public class PropertyBean_FieldSerializer implements com.google.gwt.user.client.rpc.impl.TypeHandler {
  private static native int getBedAvailableFrom(it.burningboots.join.shared.PropertyBean instance) /*-{
    return instance.@it.burningboots.join.shared.PropertyBean::bedAvailableFrom;
  }-*/;
  
  private static native void setBedAvailableFrom(it.burningboots.join.shared.PropertyBean instance, int value) 
  /*-{
    instance.@it.burningboots.join.shared.PropertyBean::bedAvailableFrom = value;
  }-*/;
  
  private static native int getBedAvailableUntil(it.burningboots.join.shared.PropertyBean instance) /*-{
    return instance.@it.burningboots.join.shared.PropertyBean::bedAvailableUntil;
  }-*/;
  
  private static native void setBedAvailableUntil(it.burningboots.join.shared.PropertyBean instance, int value) 
  /*-{
    instance.@it.burningboots.join.shared.PropertyBean::bedAvailableUntil = value;
  }-*/;
  
  private static native double getBedPrice(it.burningboots.join.shared.PropertyBean instance) /*-{
    return instance.@it.burningboots.join.shared.PropertyBean::bedPrice;
  }-*/;
  
  private static native void setBedPrice(it.burningboots.join.shared.PropertyBean instance, double value) 
  /*-{
    instance.@it.burningboots.join.shared.PropertyBean::bedPrice = value;
  }-*/;
  
  private static native boolean getClosed(it.burningboots.join.shared.PropertyBean instance) /*-{
    return instance.@it.burningboots.join.shared.PropertyBean::closed;
  }-*/;
  
  private static native void setClosed(it.burningboots.join.shared.PropertyBean instance, boolean value) 
  /*-{
    instance.@it.burningboots.join.shared.PropertyBean::closed = value;
  }-*/;
  
  private static native int getTentAvailableFrom(it.burningboots.join.shared.PropertyBean instance) /*-{
    return instance.@it.burningboots.join.shared.PropertyBean::tentAvailableFrom;
  }-*/;
  
  private static native void setTentAvailableFrom(it.burningboots.join.shared.PropertyBean instance, int value) 
  /*-{
    instance.@it.burningboots.join.shared.PropertyBean::tentAvailableFrom = value;
  }-*/;
  
  private static native int getTentAvailableUntil(it.burningboots.join.shared.PropertyBean instance) /*-{
    return instance.@it.burningboots.join.shared.PropertyBean::tentAvailableUntil;
  }-*/;
  
  private static native void setTentAvailableUntil(it.burningboots.join.shared.PropertyBean instance, int value) 
  /*-{
    instance.@it.burningboots.join.shared.PropertyBean::tentAvailableUntil = value;
  }-*/;
  
  private static native double getTentPrice(it.burningboots.join.shared.PropertyBean instance) /*-{
    return instance.@it.burningboots.join.shared.PropertyBean::tentPrice;
  }-*/;
  
  private static native void setTentPrice(it.burningboots.join.shared.PropertyBean instance, double value) 
  /*-{
    instance.@it.burningboots.join.shared.PropertyBean::tentPrice = value;
  }-*/;
  
  private static native java.lang.String getVersion(it.burningboots.join.shared.PropertyBean instance) /*-{
    return instance.@it.burningboots.join.shared.PropertyBean::version;
  }-*/;
  
  private static native void setVersion(it.burningboots.join.shared.PropertyBean instance, java.lang.String value) 
  /*-{
    instance.@it.burningboots.join.shared.PropertyBean::version = value;
  }-*/;
  
  public static void deserialize(SerializationStreamReader streamReader, it.burningboots.join.shared.PropertyBean instance) throws SerializationException {
    setBedAvailableFrom(instance, streamReader.readInt());
    setBedAvailableUntil(instance, streamReader.readInt());
    setBedPrice(instance, streamReader.readDouble());
    setClosed(instance, streamReader.readBoolean());
    setTentAvailableFrom(instance, streamReader.readInt());
    setTentAvailableUntil(instance, streamReader.readInt());
    setTentPrice(instance, streamReader.readDouble());
    setVersion(instance, streamReader.readString());
    
  }
  
  public static it.burningboots.join.shared.PropertyBean instantiate(SerializationStreamReader streamReader) throws SerializationException {
    return new it.burningboots.join.shared.PropertyBean();
  }
  
  public static void serialize(SerializationStreamWriter streamWriter, it.burningboots.join.shared.PropertyBean instance) throws SerializationException {
    streamWriter.writeInt(getBedAvailableFrom(instance));
    streamWriter.writeInt(getBedAvailableUntil(instance));
    streamWriter.writeDouble(getBedPrice(instance));
    streamWriter.writeBoolean(getClosed(instance));
    streamWriter.writeInt(getTentAvailableFrom(instance));
    streamWriter.writeInt(getTentAvailableUntil(instance));
    streamWriter.writeDouble(getTentPrice(instance));
    streamWriter.writeString(getVersion(instance));
    
  }
  
  public Object create(SerializationStreamReader reader) throws SerializationException {
    return it.burningboots.join.shared.PropertyBean_FieldSerializer.instantiate(reader);
  }
  
  public void deserial(SerializationStreamReader reader, Object object) throws SerializationException {
    it.burningboots.join.shared.PropertyBean_FieldSerializer.deserialize(reader, (it.burningboots.join.shared.PropertyBean)object);
  }
  
  public void serial(SerializationStreamWriter writer, Object object) throws SerializationException {
    it.burningboots.join.shared.PropertyBean_FieldSerializer.serialize(writer, (it.burningboots.join.shared.PropertyBean)object);
  }
  
}
