package it.burningboots.join.shared.entity;

import com.google.gwt.user.client.rpc.SerializationException;
import com.google.gwt.user.client.rpc.SerializationStreamReader;
import com.google.gwt.user.client.rpc.SerializationStreamWriter;
import com.google.gwt.user.client.rpc.impl.ReflectionHelper;

@SuppressWarnings("deprecation")
public class Participant_Array_Rank_1_FieldSerializer implements com.google.gwt.user.client.rpc.impl.TypeHandler {
  public static void deserialize(SerializationStreamReader streamReader, it.burningboots.join.shared.entity.Participant[] instance) throws SerializationException {
    com.google.gwt.user.client.rpc.core.java.lang.Object_Array_CustomFieldSerializer.deserialize(streamReader, instance);
  }
  
  public static it.burningboots.join.shared.entity.Participant[] instantiate(SerializationStreamReader streamReader) throws SerializationException {
    int size = streamReader.readInt();
    return new it.burningboots.join.shared.entity.Participant[size];
  }
  
  public static void serialize(SerializationStreamWriter streamWriter, it.burningboots.join.shared.entity.Participant[] instance) throws SerializationException {
    com.google.gwt.user.client.rpc.core.java.lang.Object_Array_CustomFieldSerializer.serialize(streamWriter, instance);
  }
  
  public Object create(SerializationStreamReader reader) throws SerializationException {
    return it.burningboots.join.shared.entity.Participant_Array_Rank_1_FieldSerializer.instantiate(reader);
  }
  
  public void deserial(SerializationStreamReader reader, Object object) throws SerializationException {
    it.burningboots.join.shared.entity.Participant_Array_Rank_1_FieldSerializer.deserialize(reader, (it.burningboots.join.shared.entity.Participant[])object);
  }
  
  public void serial(SerializationStreamWriter writer, Object object) throws SerializationException {
    it.burningboots.join.shared.entity.Participant_Array_Rank_1_FieldSerializer.serialize(writer, (it.burningboots.join.shared.entity.Participant[])object);
  }
  
}
