package com.honorsmobileapps.noahshields.smashutilities.CharacterDataFeature;

//This class is a data class used to store all of the data that a attack consists of
public class AttackData {

    //Fields
    private String name;
    private String hitboxActive;
    private String firstActionableFrame;
    private String baseDamage;
    private String angle;
    private String baseKnockBackSetKnockback;
    private String landingLag;
    private String autoCancel;
    private String knockBackGrowth;
    private String moveType;
    private boolean isWeightDependent;
    private String[] values;

    //Initializes all of the attack values
    public AttackData(String[] values, boolean isWeightDependent){
        this.name = values[0];
        this.hitboxActive = values[1];
        this.firstActionableFrame = values[2];
        this.baseDamage = values[3];
        this.angle = values[4];
        this.baseKnockBackSetKnockback = values[5];
        this.landingLag = values[6];
        this.autoCancel = values[7];
        this.knockBackGrowth = values[8];
        this.moveType = values[9];
        this.isWeightDependent = isWeightDependent;
        this.values = values;
    }

    public String getAngle() {
        return angle;
    }

    public String getBaseDamage() {
        return baseDamage;
    }

    public String getAutoCancel() {
        return autoCancel;
    }

    public String getBaseKnockBackSetKnockback() {
        return baseKnockBackSetKnockback;
    }

    public String getFirstActionableFrame() {
        return firstActionableFrame;
    }

    public String getHitboxActive() {
        return hitboxActive;
    }

    public String getKnockBackGrowth() {
        return knockBackGrowth;
    }

    public String getLandingLag() {
        return landingLag;
    }

    public String getMoveType() {
        return moveType;
    }

    public String getName() {
        return name;
    }

    public boolean isWeightDependent() {
        return isWeightDependent;
    }

    public String[] getValues() {
        return values;
    }
}
