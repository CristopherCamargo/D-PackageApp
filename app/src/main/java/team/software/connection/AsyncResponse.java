package team.software.connection;

import org.json.JSONException;

/**
 * Created by Caceres on 28-11-2016.
 */

public interface AsyncResponse {
    void processFinish(String output) throws JSONException;
}
