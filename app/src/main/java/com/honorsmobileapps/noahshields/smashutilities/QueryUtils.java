package com.honorsmobileapps.noahshields.smashutilities;

import android.util.Log;

import com.honorsmobileapps.noahshields.smashutilities.CharacterDataFeature.AttackData;
import com.honorsmobileapps.noahshields.smashutilities.CharacterDataFeature.BasicCharacterInfo;
import com.honorsmobileapps.noahshields.smashutilities.CharacterDataFeature.CharacterData;
import com.honorsmobileapps.noahshields.smashutilities.CharacterDataFeature.CharacterDataSelectionActivity;
import com.honorsmobileapps.noahshields.smashutilities.CharacterDataFeature.MovementValData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

//This is a static class for all things networking related
public final class QueryUtils {
    private static final String LOG_TAG = "Networking Error";

    //Stops the instantiation of QueryUtils
    private QueryUtils() { }

    //Fetches the JSON String from a given URL
    //Params:
    //  requestUrl - the URL to retrieve get the JSON data from
    //Returns: a String representation of a JSON object
    public static String fetchJSONString(String requestUrl){
        //Makes url object
        URL url = createUrl(requestUrl);

        String jsonResponse = null;
        //Tries to make the request for data if there
        //exception is thrown it's caught
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem making the HTTP request", e);
        }

        return jsonResponse;
    }

    //Helper method used to handle creation of a URL object from a String
    //Params:
    //  stringUrl - a String version of the URL
    //Returns: a new URL object of the passed in String
    private static URL createUrl(String stringUrl) {
        URL url;

        //Try's to make a url. If the stringUrl is not valid the exception is logged
        try
        {
            url = new URL(stringUrl);
        }
        catch (MalformedURLException e)
        {
            Log.e(LOG_TAG, "Error with creating URL", e);
            return null;
        }

        return url;
    }

    //Makes a HTTP request for data using a url object
    //Params:
    //  url - a url object of the url that the data is at
    //Returns: a JSON response in string format
    //Exceptions: Has the ability to throw a IOException
    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";
        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;

        //Trys to make the request for data
        try{
            //Opens the connection
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");

            //10 second time out if
            urlConnection.setConnectTimeout(10000);
            urlConnection.setReadTimeout(10000);

            //If the request is successful take in the stream
            if(urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            }
            else{
                Log.e(LOG_TAG, "Error code: " + urlConnection.getResponseCode());
            }
        }catch (IOException e){
            Log.e(LOG_TAG,"IO Exception: ", e);
        }
        //Makes sure the connection is always closed
        finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }

        return jsonResponse;
    }

    //Reads in data from the previously retrieved String
    //Params:
    //  inputStream - the stream to read data from
    //Returns: a string of data read from the stream
    //Exceptions: can throw a IOException
    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        InputStreamReader inputStreamReader = null;
        BufferedReader reader = null;

        try{
            //Makes sure the inputStream is not null before reading anything
            if (inputStream != null) {
                //Makes a reader with the retrieved String
                inputStreamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
                reader = new BufferedReader(inputStreamReader);

                //Loops until all of the data has been read in from the stream
                String line = reader.readLine();
                while (line != null) {
                    output.append(line);
                    line = reader.readLine();
                }
            }
        }
        catch(IOException ioException){
            Log.e(LOG_TAG, "Stream reader error", ioException);
        }
        //Makes sure the readers are closed after use
        finally {
            if (reader != null){
                reader.close();
            }

            if (inputStreamReader != null) {
                inputStreamReader.close();
            }

            if (inputStream != null){
                inputStream.close();
            }
        }

        return output.toString();
    }

    //Parses JSON string data into a MovementValData object
    //Params:
    //  jsonResponse - the JSON data in String form
    //Returns: a new MovementValData object created from data
    //         retrieved from the JSON response
    public static MovementValData extractMovementData(String jsonResponse) {
        //makes sure JSON response isn't empty
        if (!jsonResponse.equals("")) {
            try {
                JSONArray ultimateMovementJSONArray = new JSONArray(jsonResponse);

                //Looping through all of the movement data and getting all of the
                //values needed to create a MovementValData object
                String[] movementValues = new String[18];
                for (int i = 0; i < ultimateMovementJSONArray.length(); i++) {
                    movementValues[i] = ultimateMovementJSONArray.getJSONObject(i).getString("Value");
                }

                return new MovementValData(movementValues);
            } catch (JSONException e) {
                Log.e("QueryUtils", "Problem parsing the Movement JSON results", e);
            }
        }
        // Return the CharacterData
        return new MovementValData();
    }

    //Parses JSON string data into a AttackData objects
    //Params:
    //  jsonResponse - the JSON data in String form
    //Returns: a list of all of the AttackData objects
    //         created from the JSON response
    public static ArrayList<AttackData> extractAttackDatas(String jsonResponse) {
        ArrayList<AttackData> moves = new ArrayList<>();

        //Makes sure jsonResponse
        if (!jsonResponse.equals("")) {
            try {
                //Array of all of the vlaues need from the JSON response
                String[] values = {"Name", "HitboxActive", "FirstActionableFrame", "BaseDamage", "Angle", "BaseKnockBackSetKnockback", "LandingLag", "AutoCancel", "KnockbackGrowth", "MoveType"};

                //Array of all of the data
                JSONArray ultimateAttackJSONArray = new JSONArray(jsonResponse);

                //This is where all of the attack data is located in the JSON response
                String[] attackValues = new String[10];

                //Loops through creating all of the AttackDatas
                boolean isWeightDependent;
                for (int i = 0; i < ultimateAttackJSONArray.length(); i++) {
                    JSONObject currAttack = ultimateAttackJSONArray.getJSONObject(i);

                    //Loops through and gets all the needed values
                    for (int i1 = 0; i1 < values.length; i1++){
                        attackValues[i1] = currAttack.getString(values[i1]);
                    }

                    isWeightDependent = currAttack.getBoolean("IsWeightDependent");
                    moves.add(new AttackData(attackValues, isWeightDependent));
                    attackValues = new String[10];
                }
            } catch (JSONException e) {
                Log.e("QueryUtils", "Problem parsing the Movement JSON results", e);
            }
        }

        // Return the AttackDatas
        return moves;
    }

    //Parses JSON string data into a CharacterData object
    //Params:
    //  jsonResponse - the JSON dat in String form
    //Returns: a new Character Data object created from data
    //         retrieved from the JSON response
    public static CharacterData fetchCharacterData(String requestURL, int characterIndex){
        try {
            //Parses into JSON response for data
            JSONArray rootJSON = new JSONArray(fetchJSONString(requestURL));
            JSONObject currCharacter = rootJSON.getJSONObject(characterIndex);
            JSONObject related = currCharacter.getJSONObject("Related");
            JSONObject smashUltimateDataLinks = related.getJSONObject("Ultimate");

            //Gets all of the movement data of the Character
            MovementValData movementValData = extractMovementData(fetchJSONString("https" + smashUltimateDataLinks.getString("Movements")
                                                                                                        .substring(smashUltimateDataLinks.getString("Moves")
                                                                                                        .indexOf(":"))));
            //Gets all of the attack data of the Character
            ArrayList<AttackData> attacks = extractAttackDatas(fetchJSONString("https" + smashUltimateDataLinks.getString("Moves")
                                                                                                        .substring(smashUltimateDataLinks.getString("Moves")
                                                                                                        .indexOf(":"))));
            return new CharacterData(movementValData, attacks);
        } catch (JSONException e) {
            Log.e("QueryUtils", "Problem parsing the Movement JSON results", e);
        }
        return new CharacterData();
    }

    //Gets the all of the Characters basic character info
    //Params:
    //  requestUrl - the Url to get the data from
    //Returns: a list containing all of the BasicCharacterInfos
    public static ArrayList<BasicCharacterInfo> fetchBasicCharacterInfo(String requestUrl) {
        ArrayList<BasicCharacterInfo> characterInfos = new ArrayList<>();

        try {
            //Gets the JSON String
            JSONArray rootJSON = new JSONArray(fetchJSONString(requestUrl));

            //Loops through data retrieved and
            for (int i = 0; i < rootJSON.length(); i++) {
                JSONObject currCharacter = rootJSON.getJSONObject(i);

                //Makes objects
                characterInfos.add(new BasicCharacterInfo(currCharacter.getString("DisplayName"),
                                                            currCharacter.getString("ColorTheme"),
                                                    "https://" + currCharacter.getString("ThumbnailUrl").substring(7)));
            }
            return characterInfos;
        }
        catch (JSONException e) {
            Log.e("QueryUtils", "Problem parsing the JSON results", e);
        }
        return characterInfos;
    }
}
