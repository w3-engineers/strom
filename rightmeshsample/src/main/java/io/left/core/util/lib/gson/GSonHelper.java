package io.left.core.util.lib.gson;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

import timber.log.Timber;

/*
* ****************************************************************************
* * Copyright © 2018 W3 Engineers Ltd., All rights reserved.
* *
* * Created by:
* * Name : Mohd. Asfaq-E-Azam Rifat
* * Date : 2/1/18
* * Email : rifat@w3engineers.com
* *
* * Purpose : Generic Class for convert any type of model
* *
* * Last Edited by : Mohd. Asfaq-E-Azam Rifat on 2/1/18.
* * History:
* * 1:
* * 2:
* *
* * Last Reviewed by : Sudipta K Paik on 03/13/18.
* ****************************************************************************
*/

public class GSonHelper {

    // Static fields
    private static Gson sGson = new GsonBuilder().setExclusionStrategies(new AnnotationExclusionStrategy()).create();

    /*------------------Methods to get JSON from different OBJECTS------------------*/
    /**
     * @author Mohd. Asfaq-E-Azam Rifat
     * @param <T> the 1st type of the desired object
     * @param modelClassObject an object of model class
     * @return String the desired json
     *
     * i.e: String json = GsonHelper.toJson(modelClassObject);
     */
    public static <T> String toJson(T modelClassObject) {

        Type type = new TypeToken<T>() {}.getType();

        try {
            return sGson.toJson(modelClassObject, type);
        } catch (Exception e) {
            Timber.e(e);
        }

        return null;
    }

    /**
     * @author Mohd. Asfaq-E-Azam Rifat
     * @param <T> the 1st type of the desired object
     * @param listOfModelClassObjects a list of objects of model class
     * @return String the desired json
     *
     * i.e: String json = GsonHelper.toJson(listOfModelClassObjects);
     */
    public static <T> String toJson(List<T> listOfModelClassObjects) {

        Type type = new TypeToken<List<T>>() {}.getType();

        try {
            return sGson.toJson(listOfModelClassObjects, type);
        } catch (Exception e) {
            Timber.e(e);
        }

        return null;
    }

    /**
     * @author Mohd. Asfaq-E-Azam Rifat
     * @param <T1> the 1st type of the desired object
     * @param <T2> the 2nd type of the desired object
     * @param keyValuePairsOfModelClassObjects an map of objects of model class
     * @return String the desired json
     *
     * i.e: String json = GsonHelper.toJson(keyValuePairsOfModelClassObjects);
     */
    public static <T1, T2> String toJson(Map<T1, T2> keyValuePairsOfModelClassObjects) {

        Type type = new TypeToken<Map<T1, T2>>() {}.getType();

        try {
            return sGson.toJson(keyValuePairsOfModelClassObjects, type);
        } catch (Exception e) {
            Timber.e(e);
        }

        return null;
    }

    /**
     * @author Mohd. Asfaq-E-Azam Rifat
     * @param <T> the type of the desired object
     * @param arrayOfModelClassObjects an array of objects of model class
     * @return String the desired json
     *
     * i.e: String json = GsonHelper.toJson(arrayOfModelClassObjects);
     */
    public static <T> String toJson(T[] arrayOfModelClassObjects) {

        Type type = new TypeToken<T[]>() {}.getType();

        try {
            return sGson.toJson(arrayOfModelClassObjects, type);
        } catch (Exception e) {
            Timber.e(e);
        }

        return null;
    }

    /*------------------Methods to get OBJECTS from JSON------------------*/
    /**
     * @author Mohd. Asfaq-E-Azam Rifat
     * @param <T> the type of the desired object
     * @param json the string from which the object is to be deserialized
     * @param modelClass the specific class of which the object is to be generated
     * @return T an object of the model class
     *
     * i.e: ModelClass modelClassObject = GsonHelper.fromJson(json, ModelClass.class);
     */
    public static <T> T fromJson(String json, Class<T> modelClass) {

        try {
            return sGson.fromJson(json, modelClass);
        } catch (Exception e) {
            Timber.e(e);
        }

        return null;
    }

    /**
     * @author Mohd. Asfaq-E-Azam Rifat
     * @param json the string from which the object is to be deserialized
     * @return JsonObject a json object to be used
     *
     * i.e: JsonObject jsonObject = GsonHelper.fromJson(json);
     */
    public static JsonObject fromJson(String json) {

        try {
            return sGson.fromJson(json, JsonObject.class);
        } catch (Exception e) {
            Timber.e(e);
        }

        return null;
    }

    /**
     * @author Mohd. Asfaq-E-Azam Rifat
     * @param <T> the type of the desired object
     * @param json the string from which the object is to be deserialized
     * @param desiredType the specific genericized type of source.
     * @return List<T> list of the desired objects
     *
     * i.e:
     * List<ModelClass> listOfModelClassObjects
     *                  = GsonHelper.fromJson(json, new TypeToken<List<ModelClass>>(){}.getType());
     */
    public static <T> List<T> fromJson(String json, Type desiredType) {

        try {
            return sGson.fromJson(json, desiredType);
        } catch (Exception e) {
            Timber.e("fromJson(String json, Type desiredType) Method -> " + e);
        }

        return null;
    }
}