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


    /**
     * GWT-RPC service  asynchronous (client-side) interface
     * @see it.burningboots.join.client.service.DataService
     */
    void findConfigByKey( java.lang.String key, AsyncCallback<it.burningboots.join.shared.entity.Config> callback );


    /**
     * GWT-RPC service  asynchronous (client-side) interface
     * @see it.burningboots.join.client.service.DataService
     */
    void saveOrUpdateConfig( it.burningboots.join.shared.entity.Config config, AsyncCallback<java.lang.Integer> callback );

    /**
     * GWT-RPC service  asynchronous (client-side) interface
     * @see it.burningboots.join.client.service.DataService
     */
    void findParticipantById( java.lang.Integer id, AsyncCallback<it.burningboots.join.shared.entity.Participant> callback );

    /**
     * GWT-RPC service  asynchronous (client-side) interface
     * @see it.burningboots.join.client.service.DataService
     */
    void findParticipantByItemNumber( java.lang.String itemNumber, AsyncCallback<it.burningboots.join.shared.entity.Participant> callback );


    /**
     * GWT-RPC service  asynchronous (client-side) interface
     * @see it.burningboots.join.client.service.DataService
     */
    void findParticipants( boolean paid, AsyncCallback<java.util.List<it.burningboots.join.shared.entity.Participant>> callback );


    /**
     * GWT-RPC service  asynchronous (client-side) interface
     * @see it.burningboots.join.client.service.DataService
     */
    void createTransientParticipant( AsyncCallback<it.burningboots.join.shared.entity.Participant> callback );


    /**
     * GWT-RPC service  asynchronous (client-side) interface
     * @see it.burningboots.join.client.service.DataService
     */
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
