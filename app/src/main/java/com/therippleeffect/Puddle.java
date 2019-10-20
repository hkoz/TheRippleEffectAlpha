package com.therippleeffect;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class Puddle {

    private String imageURL, initiatorName, puddleName, puddleDateCreated, puddleQuest,
            puddleStatus, puddleType, puddleDetails, puddleCountryLocation, puddleCityLocation,
            locationLongitude, locationLatitude = " ";

    private int puddleRequiredRipples, puddleCreatedRipples, puddleCredibilityBoostsNumber, puddleCredibilityReportsNumber = 0;

    private ArrayList<String> puddleHeroes = new ArrayList<>();
    private ArrayList<ImageListItem> puddleImagesSources = new ArrayList<>();



    private String puddleKey;

    public static String key = "puddleKey";
    public static String nameKey = "puddleName";
    public static String initiatorKey = "initiatorName";
    public static String questKey = "puddleQuest";
    public static String countryKey = "puddleCountryLocation";
    public static String cityKey = "puddleCityLocation";
    public static String reqRipplesKey = "puddleRequiredRipples";
    public static String createdRipplesKey = "puddleCreatedRipples";
    public static String typeKey = "puddleType";
    public static String statusKey = "puddleStatus";
    public static String credibilityKey = "puddleCredibilityBoostsNumber";
    public static String reportsKey = "puddleCredibilityReportsNumber";
    public static String detailsKey = "puddleDetails";
    public static String dateKey = "dateCreated";
    public static String locationLongitudeKey = "locationLongitude";
    public static String locationLatitudeKey = "locationLatitude";
    public static String  mainImageKey= "imageURL";
    public static String imagesArrayKey = "puddleImagesSources";
    public static String heroesArrayKey = "puddleHeroesArray";

    public Puddle(){}


    /**the class constructor*/
    public Puddle(String puddleKey, String imageURL, String puddleName, String initiatorName, String puddleQuest,
                  String puddleCountryLocation, String puddleCityLocation, String locationLongitude, String locationLatitude,
                  int puddleRequiredRipples, int puddleCreatedRipples, String puddleType, String puddleStatus,
                  int puddleCredibilityBoostsNumber, int puddleCredibilityReportsNumber, String puddleDetails, String dateCreated ,
                  ArrayList<String> puddleHeroesArray, ArrayList<ImageListItem> puddleImagesSources) {
        this.puddleKey = puddleKey;
        this.imageURL = imageURL;
        this.initiatorName = initiatorName;
        this.puddleName = puddleName;
        this.puddleQuest = puddleQuest;
        this.puddleStatus = puddleStatus;
        this.puddleType = puddleType;
        this.puddleCountryLocation = puddleCountryLocation;
        this.puddleCityLocation = puddleCityLocation;
        this.puddleRequiredRipples = puddleRequiredRipples;
        this.puddleCreatedRipples = puddleCreatedRipples;
        this.puddleCredibilityBoostsNumber = puddleCredibilityBoostsNumber;
        this.puddleCredibilityReportsNumber = puddleCredibilityReportsNumber;
        this.puddleDetails = puddleDetails;
        this.puddleDateCreated = dateCreated;
        this.locationLatitude = locationLatitude;
        this.locationLongitude = locationLongitude;
        this.puddleHeroes = puddleHeroesArray;
        this.puddleImagesSources = puddleImagesSources;}


    public String getImageResourceURL() { return this.imageURL; }

    public boolean puddleHasImage(){ return this.imageURL != null;}

    public String getPuddleInitiator() { return this.initiatorName; }

    public String getPuddleName() { return this.puddleName; }

    public String getPuddleDateCreated(){return this.puddleDateCreated;}

    public String getPuddleQuest() { return this.puddleQuest; }

    public String getPuddleStatus() { return this.puddleStatus; }

    public String getPuddleType() { return this.puddleType; }

    public String getPuddleCountryLocation() { return this.puddleCountryLocation; }

    public String getPuddleCityLocation() { return this.puddleCityLocation; }

    public int getPuddleRequiredRipples() { return this.puddleRequiredRipples; }

    public int getPuddleCreatedRipples() { return this.puddleCreatedRipples; }

    public ArrayList<String> getPuddleHeroes() { return this.puddleHeroes; }

    public ArrayList<ImageListItem> getPuddleImagesSources() { return this.puddleImagesSources; }

    public String getLocationLatitude() { return this.locationLatitude; }

    public String getLocationLongitude() { return this.locationLongitude; }

    public int getPuddleCredibilityBoostsNumber() { return this.puddleCredibilityBoostsNumber; }

    public int getPuddleCredibilityReportsNumber() { return this.puddleCredibilityReportsNumber; }

    public String getPuddleDetails() { return this.puddleDetails; }

    public String getPuddleKey(){return this.puddleKey;}

    public void setPuddleKey(String puddleKey) { this.puddleKey = puddleKey; }

    public void setPamgeResource(String mimageResource) { this.imageURL = mimageResource; }

    public void setPuddleInitiator(String mpuddleInitiator) { this.initiatorName = mpuddleInitiator; }

    public void setPuddleName(String mpuddleName) { this.puddleName = mpuddleName; }

    public void setPuddleQuest(String mpuddleQuest) { this.puddleQuest = mpuddleQuest; }

    public void setPuddleStatus(String mpuddleStatus) { this.puddleStatus = mpuddleStatus; }

    public void setPuddleType(String mpuddleType) { this.puddleType = mpuddleType; }

    public void setPuddleCountryLocation(String mpuddleCountryLocation) { this.puddleCountryLocation = mpuddleCountryLocation; }

    public void setPuddleCityLocation(String mpuddleCityLocation) { this.puddleCityLocation = mpuddleCityLocation; }

    public void setPuddleRequiredRipples(int mpuddleRequiredRipples) { this.puddleRequiredRipples = mpuddleRequiredRipples; }

    public void setPuddleCreatedRipples(int mpuddleCreatedRipples) { this.puddleCreatedRipples = mpuddleCreatedRipples; }

    public void setPuddleHeroes(ArrayList<String> puddleHeroes) { this.puddleHeroes = puddleHeroes;}

    public void setLocationLatitude(String locationLatitude) { this.locationLatitude = locationLatitude; }

    public void setLocationLongitude(String locationLongitude) { this.locationLongitude = locationLongitude; }

    public void setPuddleImagesSources(ArrayList<ImageListItem> puddleImagesSources) { this.puddleImagesSources = puddleImagesSources; }

    public void setPuddleCredibilityBoostsNumber(int puddleCredibilityBoostsNumber) {
        this.puddleCredibilityBoostsNumber = puddleCredibilityBoostsNumber; }

    public void setPuddleCredibilityReportsNumber(int puddleCredibilityReportsNumber) {
        this.puddleCredibilityReportsNumber = puddleCredibilityReportsNumber; }

    public void setPuddleDetails(String mpuddledetails) { this.puddleDetails = mpuddledetails; }

    public static String getCurrentDate(){

        Calendar calendar = Calendar.getInstance();
        Date now = calendar.getTime();
        SimpleDateFormat simpleDateFormat =  new SimpleDateFormat("yyyy-MM-dd' Time: 'HH:mm:ss", Locale.getDefault());
        return simpleDateFormat.format(now);
    }

    public static ArrayList<String> StringToArrayList (String string){
        String[] items = string.split("\\s*,\\s*");
        ArrayList<String> resultedArrayList = new ArrayList<>();
        Collections.addAll(resultedArrayList, items);
        return resultedArrayList; }

    public static String ArrayToString(ArrayList <String> arrayList){
        StringBuilder resultedString = new StringBuilder();
        for(String s : arrayList){
            resultedString.append(s);
            resultedString.append(",");
        }
        return resultedString.toString(); }
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put(nameKey, puddleName);
        result.put(initiatorKey, initiatorName);
        result.put(questKey, puddleQuest);
        result.put(cityKey, puddleCityLocation);
        result.put(countryKey, puddleCountryLocation);
        result.put(reqRipplesKey, puddleRequiredRipples);
        result.put(createdRipplesKey, puddleCreatedRipples);
        result.put(dateKey, puddleDateCreated);
        result.put(reportsKey, puddleCredibilityReportsNumber);
        result.put(credibilityKey, puddleCredibilityBoostsNumber);
        result.put(statusKey, puddleStatus);
        result.put(typeKey, puddleType);
        result.put(detailsKey, puddleDetails);
        result.put(heroesArrayKey, Puddle.ArrayToString(puddleHeroes));
        result.put (imagesArrayKey, ImageListItem.createStringFromImageListArrayList(puddleImagesSources));
        result.put(locationLongitudeKey, locationLongitude);
        result.put(locationLatitudeKey, locationLatitude);
        return result;
    }
    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        return super.equals(obj);
    }


    @Override
    protected void finalize() throws Throwable {
        super.finalize();
    }

    @NonNull
    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @NonNull
    @Override
    public String toString() {
        return super.toString();
    }
}
