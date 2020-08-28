package com.honorsmobileapps.noahshields.smashutilities;

import android.content.Context;
import android.text.method.HideReturnsTransformationMethod;
import android.util.Log;
import android.widget.TextView;

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
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public final class QueryUtils {
    private static final String LOG_TAG = CharacterDataSelectionActivity.class.getSimpleName();
    private static boolean isChallonge = false;


    //Can't instantiate QueryUtils
    private QueryUtils() { }


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


    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";
        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;

        try{
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setConnectTimeout(30000);
            urlConnection.setReadTimeout(30000);
            if(urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            }
            else{
                Log.e(LOG_TAG, "Error code: " + urlConnection.getResponseCode());
            }
        }catch (IOException e){
            Log.e(LOG_TAG,"Problem retrieving the Tournament JSON results.",e);
        }finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return jsonResponse;
    }
    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }
    //Fetches the JSON String from a given URL
    public static String fetchJSONString(String requestUrl){
        URL url = createUrl(requestUrl);
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
            Log.d("Tendies",jsonResponse);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem making the HTTP request", e);
        }

        return jsonResponse;
    }
    public static MovementValData extractMovementData(String jsonResponse) {
        if (!jsonResponse.equals("")) {
            try {
                JSONArray ultimateMovementJSONArray = new JSONArray(jsonResponse);
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
    public static ArrayList<AttackData> extractAttackDatas(String jsonResponse) {
        ArrayList<AttackData> moves = new ArrayList<>();
        if (!jsonResponse.equals("")) {
            try {
                String[] values = {"Name", "HitboxActive", "FirstActionableFrame", "BaseDamage", "Angle", "BaseKnockBackSetKnockback", "LandingLag", "AutoCancel", "KnockbackGrowth", "MoveType"};
                JSONArray ultimateAttackJSONArray = new JSONArray(jsonResponse);
                String[] attackValues = new String[10];
                boolean isWeightDependent;
                for (int i = 0; i < ultimateAttackJSONArray.length(); i++) {
                    JSONObject currAttack = ultimateAttackJSONArray.getJSONObject(i);
                    for (int i1 = 0; i1 < values.length;i1++){
                        attackValues[i1] = currAttack.getString(values[i1]);
                    }
                    isWeightDependent = currAttack.getBoolean("IsWeightDependent");
                    moves.add(new AttackData(attackValues,isWeightDependent));
                    attackValues = new String[10];
                }
            } catch (JSONException e) {
                Log.e("QueryUtils", "Problem parsing the Movement JSON results", e);
            }
        }
        // Return the CharacterData
        return moves;
    }
    public static CharacterData fetchCharacterData(String requestURL, int charPos){
        try {
            JSONArray rootJSON = new JSONArray(fetchJSONString(requestURL));
            JSONObject currCharacter = rootJSON.getJSONObject(charPos);
            JSONObject related = currCharacter.getJSONObject("Related");
            JSONObject smashUltimateDataLinks = related.getJSONObject("Ultimate");
            MovementValData movementValData = extractMovementData(fetchJSONString("https" + smashUltimateDataLinks.getString("Movements").substring(smashUltimateDataLinks.getString("Moves").indexOf(":"))));
            ArrayList<AttackData> attacks = extractAttackDatas(fetchJSONString("https" + smashUltimateDataLinks.getString("Moves").substring(smashUltimateDataLinks.getString("Moves").indexOf(":"))));
            return new CharacterData(movementValData, attacks);
        } catch (JSONException e) {
            Log.e("QueryUtils", "Problem parsing the Movement JSON results", e);
        }
        return new CharacterData();
    }
    public static ArrayList<BasicCharacterInfo> fetchBasicCharacterInfo(String requestUrl) {
        ArrayList<BasicCharacterInfo> characterInfos = new ArrayList<>();
        try {
            JSONArray rootJSON = new JSONArray(fetchJSONString(requestUrl));
            for (int i = 0; i < rootJSON.length(); i++) {
                JSONObject currCharacter = rootJSON.getJSONObject(i);
                characterInfos.add(new BasicCharacterInfo(currCharacter.getString("DisplayName"), currCharacter.getString("ColorTheme"), "https://" + currCharacter.getString("ThumbnailUrl").substring(7)));
            }
            return characterInfos;
        } catch (JSONException e) {
            Log.e("QueryUtils", "Problem parsing the JSON results", e);
        }
        return characterInfos;
    }
}
