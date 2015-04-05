package com.hackdee;

/**
 * Created by Ivan on 05/04/2015.
 */
import com.estimote.sdk.Beacon;
import com.estimote.sdk.BeaconManager;

public class PersonalBeacon extends Beacon{
    private String localName;
    public PersonalBeacon(Beacon beacon){
        super(beacon.getProximityUUID(),beacon.getName(),beacon.getMacAddress(),beacon.getMajor(),beacon.getMinor(),beacon.getMeasuredPower(),beacon.getRssi());
        this.localName = null;
    }

    public void setLocalName(String localName){
        this.localName = localName;
    }
    public String getLocalName(){
        return this.localName;
    }

    public static void main(String[] args){
        Beacon bla = new Beacon("xxxx-xxxx-xxxx-xxxx", "s", "s", 1, 1, 1, 1);
        PersonalBeacon blabla = new PersonalBeacon(bla);
        blabla.setLocalName("bla");
        System.out.println(bla.getClass() == blabla.getClass());
    }


}
