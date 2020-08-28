package com.honorsmobileapps.noahshields.smashutilities.CharacterDataFeature;

public class MovementValData {
    //Different values
    private String weight;
    private String maxJumps;
    private String runSpeed;
    private String wallJump;
    private String walkSpeed;
    private String wallCling;
    private String airSpeed;
    private String crawl;
    private String fallSpeed;
    private String tether;
    private String fastFallSpeed;
    private String jumpSquat;
    private String airAcceleration;
    private String softLandingLag;
    private String gravity;
    private String hardLandingLag;
    private String sHAirTime;
    private String fHAirTime;
    private String[] movementArray;

    public MovementValData(){
        movementArray = new String[18];
        for (int i = 0; i < movementArray.length; i++){
            movementArray[i] = "Null";
        }
    }
    public MovementValData(String[] movementValues){
        this.weight = movementValues[0];
        this.maxJumps = movementValues[1];
        this.runSpeed = movementValues[2];
        this.wallJump = movementValues[3];
        this.walkSpeed = movementValues[4];
        this.wallCling = movementValues[5];
        this.airSpeed = movementValues[6];
        this.crawl = movementValues[7];
        this.fallSpeed = movementValues[8];
        this.tether = movementValues[9];
        this.fastFallSpeed = movementValues[10];
        this.jumpSquat = movementValues[11];
        this.airAcceleration = movementValues[12];
        this.softLandingLag = movementValues[13];
        this.gravity = movementValues[14];
        this.hardLandingLag = movementValues[15];
        this.sHAirTime = movementValues[16];
        this.fHAirTime = movementValues[17];
        movementArray = movementValues;
    }

    public String getAirAcceleration() {
        return airAcceleration;
    }

    public String getAirSpeed() {
        return airSpeed;
    }

    public String getCrawl() {
        return crawl;
    }

    public String getFallSpeed() {
        return fallSpeed;
    }

    public String getFastFallSpeed() {
        return fastFallSpeed;
    }

    public String getfHAirTime() {
        return fHAirTime;
    }

    public String getGravity() {
        return gravity;
    }

    public String getHardLandingLag() {
        return hardLandingLag;
    }

    public String getJumpSquat() {
        return jumpSquat;
    }

    public String getMaxJumps() {
        return maxJumps;
    }

    public String getRunSpeed() {
        return runSpeed;
    }

    public String getsHAirTime() {
        return sHAirTime;
    }

    public String getSoftLandingLag() {
        return softLandingLag;
    }

    public String getTether() {
        return tether;
    }

    public String getWalkSpeed() {
        return walkSpeed;
    }

    public String getWallCling() {
        return wallCling;
    }

    public String getWallJump() {
        return wallJump;
    }

    public String getWeight() {
        return weight;
    }

    public String[] getMovementArray() {
        return movementArray;
    }
}
