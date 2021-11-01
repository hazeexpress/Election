package com.election.valueobject;

import com.election.type.PrecinctType;

import java.util.List;

public class Precinct {

    private int index;
    private String address;
    private double proportionOfVoter;
    private PrecinctType typeOfPrecinct;
    private List<Citizen> acceptedVoter;

    public Precinct(int index, String address, double proportionOfVoter, PrecinctType typeOfPrecinct, List<Citizen> acceptedVoter) {
        this.index = index;
        this.address = address;
        this.proportionOfVoter = proportionOfVoter;
        this.typeOfPrecinct = typeOfPrecinct;
        this.acceptedVoter = acceptedVoter;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getProportionOfVoter() {
        return proportionOfVoter;
    }

    public void setProportionOfVoter(double proportionOfVoter) {
        this.proportionOfVoter = proportionOfVoter;
    }

    public PrecinctType getTypeOfPrecinct() {
        return typeOfPrecinct;
    }

    public void setTypeOfPrecinct(PrecinctType typeOfPrecinct) {
        this.typeOfPrecinct = typeOfPrecinct;
    }

    public List<Citizen> getAcceptedVoter() {
        return acceptedVoter;
    }

    public void setAcceptedVoter(List<Citizen> acceptedVoter) {
        this.acceptedVoter = acceptedVoter;
    }

    @Override
    public String toString() {
        return "\n" +
                "╔═ Precinct index: " + index  + "\n" +
                "╠═ Address: " + address + "\n" +
                "╠═ Proportion Of Voter: " + proportionOfVoter + "\n" +
                "╚═ Type Of Precinct: " + typeOfPrecinct + "\n";
    }
}
