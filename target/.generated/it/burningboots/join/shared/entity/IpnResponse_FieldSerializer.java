package it.burningboots.join.shared.entity;

import com.google.gwt.user.client.rpc.SerializationException;
import com.google.gwt.user.client.rpc.SerializationStreamReader;
import com.google.gwt.user.client.rpc.SerializationStreamWriter;
import com.google.gwt.user.client.rpc.impl.ReflectionHelper;

@SuppressWarnings("deprecation")
public class IpnResponse_FieldSerializer implements com.google.gwt.user.client.rpc.impl.TypeHandler {
  private static native java.lang.String getId(it.burningboots.join.shared.entity.IpnResponse instance) /*-{
    return instance.@it.burningboots.join.shared.entity.IpnResponse::id;
  }-*/;
  
  private static native void setId(it.burningboots.join.shared.entity.IpnResponse instance, java.lang.String value) 
  /*-{
    instance.@it.burningboots.join.shared.entity.IpnResponse::id = value;
  }-*/;
  
  private static native java.lang.String getItemNumber(it.burningboots.join.shared.entity.IpnResponse instance) /*-{
    return instance.@it.burningboots.join.shared.entity.IpnResponse::itemNumber;
  }-*/;
  
  private static native void setItemNumber(it.burningboots.join.shared.entity.IpnResponse instance, java.lang.String value) 
  /*-{
    instance.@it.burningboots.join.shared.entity.IpnResponse::itemNumber = value;
  }-*/;
  
  private static native java.lang.String getMcCurrency(it.burningboots.join.shared.entity.IpnResponse instance) /*-{
    return instance.@it.burningboots.join.shared.entity.IpnResponse::mcCurrency;
  }-*/;
  
  private static native void setMcCurrency(it.burningboots.join.shared.entity.IpnResponse instance, java.lang.String value) 
  /*-{
    instance.@it.burningboots.join.shared.entity.IpnResponse::mcCurrency = value;
  }-*/;
  
  private static native java.lang.String getMcGross(it.burningboots.join.shared.entity.IpnResponse instance) /*-{
    return instance.@it.burningboots.join.shared.entity.IpnResponse::mcGross;
  }-*/;
  
  private static native void setMcGross(it.burningboots.join.shared.entity.IpnResponse instance, java.lang.String value) 
  /*-{
    instance.@it.burningboots.join.shared.entity.IpnResponse::mcGross = value;
  }-*/;
  
  private static native boolean getParticipantFound(it.burningboots.join.shared.entity.IpnResponse instance) /*-{
    return instance.@it.burningboots.join.shared.entity.IpnResponse::participantFound;
  }-*/;
  
  private static native void setParticipantFound(it.burningboots.join.shared.entity.IpnResponse instance, boolean value) 
  /*-{
    instance.@it.burningboots.join.shared.entity.IpnResponse::participantFound = value;
  }-*/;
  
  private static native java.lang.String getPayerEmail(it.burningboots.join.shared.entity.IpnResponse instance) /*-{
    return instance.@it.burningboots.join.shared.entity.IpnResponse::payerEmail;
  }-*/;
  
  private static native void setPayerEmail(it.burningboots.join.shared.entity.IpnResponse instance, java.lang.String value) 
  /*-{
    instance.@it.burningboots.join.shared.entity.IpnResponse::payerEmail = value;
  }-*/;
  
  private static native java.lang.String getPaymentDate(it.burningboots.join.shared.entity.IpnResponse instance) /*-{
    return instance.@it.burningboots.join.shared.entity.IpnResponse::paymentDate;
  }-*/;
  
  private static native void setPaymentDate(it.burningboots.join.shared.entity.IpnResponse instance, java.lang.String value) 
  /*-{
    instance.@it.burningboots.join.shared.entity.IpnResponse::paymentDate = value;
  }-*/;
  
  private static native java.lang.String getPaymentStatus(it.burningboots.join.shared.entity.IpnResponse instance) /*-{
    return instance.@it.burningboots.join.shared.entity.IpnResponse::paymentStatus;
  }-*/;
  
  private static native void setPaymentStatus(it.burningboots.join.shared.entity.IpnResponse instance, java.lang.String value) 
  /*-{
    instance.@it.burningboots.join.shared.entity.IpnResponse::paymentStatus = value;
  }-*/;
  
  private static native java.lang.String getPaymentType(it.burningboots.join.shared.entity.IpnResponse instance) /*-{
    return instance.@it.burningboots.join.shared.entity.IpnResponse::paymentType;
  }-*/;
  
  private static native void setPaymentType(it.burningboots.join.shared.entity.IpnResponse instance, java.lang.String value) 
  /*-{
    instance.@it.burningboots.join.shared.entity.IpnResponse::paymentType = value;
  }-*/;
  
  private static native java.lang.String getPendingReason(it.burningboots.join.shared.entity.IpnResponse instance) /*-{
    return instance.@it.burningboots.join.shared.entity.IpnResponse::pendingReason;
  }-*/;
  
  private static native void setPendingReason(it.burningboots.join.shared.entity.IpnResponse instance, java.lang.String value) 
  /*-{
    instance.@it.burningboots.join.shared.entity.IpnResponse::pendingReason = value;
  }-*/;
  
  public static void deserialize(SerializationStreamReader streamReader, it.burningboots.join.shared.entity.IpnResponse instance) throws SerializationException {
    setId(instance, streamReader.readString());
    setItemNumber(instance, streamReader.readString());
    setMcCurrency(instance, streamReader.readString());
    setMcGross(instance, streamReader.readString());
    setParticipantFound(instance, streamReader.readBoolean());
    setPayerEmail(instance, streamReader.readString());
    setPaymentDate(instance, streamReader.readString());
    setPaymentStatus(instance, streamReader.readString());
    setPaymentType(instance, streamReader.readString());
    setPendingReason(instance, streamReader.readString());
    
  }
  
  public static it.burningboots.join.shared.entity.IpnResponse instantiate(SerializationStreamReader streamReader) throws SerializationException {
    return new it.burningboots.join.shared.entity.IpnResponse();
  }
  
  public static void serialize(SerializationStreamWriter streamWriter, it.burningboots.join.shared.entity.IpnResponse instance) throws SerializationException {
    streamWriter.writeString(getId(instance));
    streamWriter.writeString(getItemNumber(instance));
    streamWriter.writeString(getMcCurrency(instance));
    streamWriter.writeString(getMcGross(instance));
    streamWriter.writeBoolean(getParticipantFound(instance));
    streamWriter.writeString(getPayerEmail(instance));
    streamWriter.writeString(getPaymentDate(instance));
    streamWriter.writeString(getPaymentStatus(instance));
    streamWriter.writeString(getPaymentType(instance));
    streamWriter.writeString(getPendingReason(instance));
    
  }
  
  public Object create(SerializationStreamReader reader) throws SerializationException {
    return it.burningboots.join.shared.entity.IpnResponse_FieldSerializer.instantiate(reader);
  }
  
  public void deserial(SerializationStreamReader reader, Object object) throws SerializationException {
    it.burningboots.join.shared.entity.IpnResponse_FieldSerializer.deserialize(reader, (it.burningboots.join.shared.entity.IpnResponse)object);
  }
  
  public void serial(SerializationStreamWriter writer, Object object) throws SerializationException {
    it.burningboots.join.shared.entity.IpnResponse_FieldSerializer.serialize(writer, (it.burningboots.join.shared.entity.IpnResponse)object);
  }
  
}
