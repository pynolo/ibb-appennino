package it.burningboots.join.shared.entity;

import com.google.gwt.user.client.rpc.SerializationException;
import com.google.gwt.user.client.rpc.SerializationStreamReader;
import com.google.gwt.user.client.rpc.SerializationStreamWriter;
import com.google.gwt.user.client.rpc.impl.ReflectionHelper;

@SuppressWarnings("deprecation")
public class Participant_FieldSerializer implements com.google.gwt.user.client.rpc.impl.TypeHandler {
  private static native java.lang.Integer getAccommodationType(it.burningboots.join.shared.entity.Participant instance) /*-{
    return instance.@it.burningboots.join.shared.entity.Participant::accommodationType;
  }-*/;
  
  private static native void setAccommodationType(it.burningboots.join.shared.entity.Participant instance, java.lang.Integer value) 
  /*-{
    instance.@it.burningboots.join.shared.entity.Participant::accommodationType = value;
  }-*/;
  
  private static native java.lang.Double getAmount(it.burningboots.join.shared.entity.Participant instance) /*-{
    return instance.@it.burningboots.join.shared.entity.Participant::amount;
  }-*/;
  
  private static native void setAmount(it.burningboots.join.shared.entity.Participant instance, java.lang.Double value) 
  /*-{
    instance.@it.burningboots.join.shared.entity.Participant::amount = value;
  }-*/;
  
  private static native java.lang.String getArrivalTime(it.burningboots.join.shared.entity.Participant instance) /*-{
    return instance.@it.burningboots.join.shared.entity.Participant::arrivalTime;
  }-*/;
  
  private static native void setArrivalTime(it.burningboots.join.shared.entity.Participant instance, java.lang.String value) 
  /*-{
    instance.@it.burningboots.join.shared.entity.Participant::arrivalTime = value;
  }-*/;
  
  private static native java.lang.String getCountryName(it.burningboots.join.shared.entity.Participant instance) /*-{
    return instance.@it.burningboots.join.shared.entity.Participant::countryName;
  }-*/;
  
  private static native void setCountryName(it.burningboots.join.shared.entity.Participant instance, java.lang.String value) 
  /*-{
    instance.@it.burningboots.join.shared.entity.Participant::countryName = value;
  }-*/;
  
  private static native java.util.Date getCreated(it.burningboots.join.shared.entity.Participant instance) /*-{
    return instance.@it.burningboots.join.shared.entity.Participant::created;
  }-*/;
  
  private static native void setCreated(it.burningboots.join.shared.entity.Participant instance, java.util.Date value) 
  /*-{
    instance.@it.burningboots.join.shared.entity.Participant::created = value;
  }-*/;
  
  private static native java.lang.String getEmail(it.burningboots.join.shared.entity.Participant instance) /*-{
    return instance.@it.burningboots.join.shared.entity.Participant::email;
  }-*/;
  
  private static native void setEmail(it.burningboots.join.shared.entity.Participant instance, java.lang.String value) 
  /*-{
    instance.@it.burningboots.join.shared.entity.Participant::email = value;
  }-*/;
  
  private static native java.lang.String getFirstName(it.burningboots.join.shared.entity.Participant instance) /*-{
    return instance.@it.burningboots.join.shared.entity.Participant::firstName;
  }-*/;
  
  private static native void setFirstName(it.burningboots.join.shared.entity.Participant instance, java.lang.String value) 
  /*-{
    instance.@it.burningboots.join.shared.entity.Participant::firstName = value;
  }-*/;
  
  private static native java.lang.String getFoodRestrictions(it.burningboots.join.shared.entity.Participant instance) /*-{
    return instance.@it.burningboots.join.shared.entity.Participant::foodRestrictions;
  }-*/;
  
  private static native void setFoodRestrictions(it.burningboots.join.shared.entity.Participant instance, java.lang.String value) 
  /*-{
    instance.@it.burningboots.join.shared.entity.Participant::foodRestrictions = value;
  }-*/;
  
  private static native java.util.Set getIpnResponses(it.burningboots.join.shared.entity.Participant instance) /*-{
    return instance.@it.burningboots.join.shared.entity.Participant::ipnResponses;
  }-*/;
  
  private static native void setIpnResponses(it.burningboots.join.shared.entity.Participant instance, java.util.Set value) 
  /*-{
    instance.@it.burningboots.join.shared.entity.Participant::ipnResponses = value;
  }-*/;
  
  private static native java.lang.String getItemNumber(it.burningboots.join.shared.entity.Participant instance) /*-{
    return instance.@it.burningboots.join.shared.entity.Participant::itemNumber;
  }-*/;
  
  private static native void setItemNumber(it.burningboots.join.shared.entity.Participant instance, java.lang.String value) 
  /*-{
    instance.@it.burningboots.join.shared.entity.Participant::itemNumber = value;
  }-*/;
  
  private static native java.lang.String getLastName(it.burningboots.join.shared.entity.Participant instance) /*-{
    return instance.@it.burningboots.join.shared.entity.Participant::lastName;
  }-*/;
  
  private static native void setLastName(it.burningboots.join.shared.entity.Participant instance, java.lang.String value) 
  /*-{
    instance.@it.burningboots.join.shared.entity.Participant::lastName = value;
  }-*/;
  
  private static native java.util.Date getPaymentDt(it.burningboots.join.shared.entity.Participant instance) /*-{
    return instance.@it.burningboots.join.shared.entity.Participant::paymentDt;
  }-*/;
  
  private static native void setPaymentDt(it.burningboots.join.shared.entity.Participant instance, java.util.Date value) 
  /*-{
    instance.@it.burningboots.join.shared.entity.Participant::paymentDt = value;
  }-*/;
  
  private static native java.lang.String getVolunteering(it.burningboots.join.shared.entity.Participant instance) /*-{
    return instance.@it.burningboots.join.shared.entity.Participant::volunteering;
  }-*/;
  
  private static native void setVolunteering(it.burningboots.join.shared.entity.Participant instance, java.lang.String value) 
  /*-{
    instance.@it.burningboots.join.shared.entity.Participant::volunteering = value;
  }-*/;
  
  public static void deserialize(SerializationStreamReader streamReader, it.burningboots.join.shared.entity.Participant instance) throws SerializationException {
    com.google.gwt.core.client.impl.WeakMapping.set(instance, "server-enhanced-data-1", streamReader.readString());
    setAccommodationType(instance, (java.lang.Integer) streamReader.readObject());
    setAmount(instance, (java.lang.Double) streamReader.readObject());
    setArrivalTime(instance, streamReader.readString());
    setCountryName(instance, streamReader.readString());
    setCreated(instance, (java.util.Date) streamReader.readObject());
    setEmail(instance, streamReader.readString());
    setFirstName(instance, streamReader.readString());
    setFoodRestrictions(instance, streamReader.readString());
    setIpnResponses(instance, (java.util.Set) streamReader.readObject());
    setItemNumber(instance, streamReader.readString());
    setLastName(instance, streamReader.readString());
    setPaymentDt(instance, (java.util.Date) streamReader.readObject());
    setVolunteering(instance, streamReader.readString());
    
  }
  
  public static it.burningboots.join.shared.entity.Participant instantiate(SerializationStreamReader streamReader) throws SerializationException {
    return new it.burningboots.join.shared.entity.Participant();
  }
  
  public static void serialize(SerializationStreamWriter streamWriter, it.burningboots.join.shared.entity.Participant instance) throws SerializationException {
    streamWriter.writeString((String) com.google.gwt.core.client.impl.WeakMapping.get(instance, "server-enhanced-data-1"));
    streamWriter.writeObject(getAccommodationType(instance));
    streamWriter.writeObject(getAmount(instance));
    streamWriter.writeString(getArrivalTime(instance));
    streamWriter.writeString(getCountryName(instance));
    streamWriter.writeObject(getCreated(instance));
    streamWriter.writeString(getEmail(instance));
    streamWriter.writeString(getFirstName(instance));
    streamWriter.writeString(getFoodRestrictions(instance));
    streamWriter.writeObject(getIpnResponses(instance));
    streamWriter.writeString(getItemNumber(instance));
    streamWriter.writeString(getLastName(instance));
    streamWriter.writeObject(getPaymentDt(instance));
    streamWriter.writeString(getVolunteering(instance));
    
  }
  
  public Object create(SerializationStreamReader reader) throws SerializationException {
    return it.burningboots.join.shared.entity.Participant_FieldSerializer.instantiate(reader);
  }
  
  public void deserial(SerializationStreamReader reader, Object object) throws SerializationException {
    it.burningboots.join.shared.entity.Participant_FieldSerializer.deserialize(reader, (it.burningboots.join.shared.entity.Participant)object);
  }
  
  public void serial(SerializationStreamWriter writer, Object object) throws SerializationException {
    it.burningboots.join.shared.entity.Participant_FieldSerializer.serialize(writer, (it.burningboots.join.shared.entity.Participant)object);
  }
  
}
