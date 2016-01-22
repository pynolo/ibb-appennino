package it.burningboots.join.client.service;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;

public interface DataServiceAsync
{

    /**
     * GWT-RPC service  asynchronous (client-side) interface
     * @see it.burningboots.join.client.service.DataService
     */
    void getPropertyBean( AsyncCallback<it.burningboots.join.shared.PropertyBean> callback );
    void getAccessKey(AsyncCallback<String> callback);
    void findConfigByKey( java.lang.String key, AsyncCallback<it.burningboots.join.shared.entity.Config> callback );
    void saveOrUpdateConfig( it.burningboots.join.shared.entity.Config config, AsyncCallback<java.lang.Integer> callback );

    void findParticipantById( java.lang.Integer id, AsyncCallback<it.burningboots.join.shared.entity.Participant> callback );
    void findParticipantByItemNumber( java.lang.String itemNumber, AsyncCallback<it.burningboots.join.shared.entity.Participant> callback );
    void findParticipants( boolean confirmed, AsyncCallback<java.util.List<it.burningboots.join.shared.entity.Participant>> callback );
    void countConfirmedParticipants(AsyncCallback<Integer> callback );
    void createTransientParticipant( AsyncCallback<it.burningboots.join.shared.entity.Participant> callback );
    void saveOrUpdateParticipant( it.burningboots.join.shared.entity.Participant prt, AsyncCallback<java.lang.Integer> callback );

    /**
     * Utility class to get the RPC Async interface from client-side code
     */
    public static final class Util 
    { 
        private static DataServiceAsync instance;

        public static final DataServiceAsync getInstance()
        {
            if ( instance == null )
            {
                instance = (DataServiceAsync) GWT.create( DataService.class );
            }
            return instance;
        }

        private Util()
        {
            // Utility class should not be instantiated
        }
    }
}
