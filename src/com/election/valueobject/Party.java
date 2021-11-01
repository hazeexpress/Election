package com.election.valueobject;


import com.election.type.FractionType;

import java.text.SimpleDateFormat;
import java.util.*;

public class Party {
    private String name;
    private FractionType fraction;
    private Date dateOfCreatingParty;
    private Set<Citizen> candidates = new TreeSet<>();

    private final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy");

    public Party(String name, FractionType fraction, Date dateOfCreatingParty) {
        this.name = name;
        this.fraction = fraction;
        this.dateOfCreatingParty = dateOfCreatingParty;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public FractionType getFraction() {
        return fraction;
    }

    public void setFraction(FractionType fraction) {
        this.fraction = fraction;
    }

    public Date getDateOfCreatingParty() {
        return dateOfCreatingParty;
    }

    public void setDateOfCreatingParty(Date dateOfCreatingParty) {
        this.dateOfCreatingParty = dateOfCreatingParty;
    }

    public Set<Citizen> getCandidates() {
        return candidates;
    }

    public void setCandidates(Set<Citizen> candidates) {
        this.candidates = candidates;
    }

    public void setCandidatesByList(List<Citizen> candidates) {
        this.candidates = new TreeSet<>(candidates);
    }

    public void addCandidate(Citizen candidate) {
        candidate.setParty(this);
        candidates.add(candidate);
    }

    public void removeCandidate(Citizen candidate) {
        boolean isRemoved = candidates.removeIf(c -> c.getId() == candidate.getId());
        if (isRemoved) {
            candidate.setParty(null);
        }
    }

    @Override
    public String toString() {
        return "\n" +
                "╔═ Party name: " + name  + "\n" +
                "╠═ Fraction: " + fraction + "\n" +
                "╚═ Date of creating: " + simpleDateFormat.format(dateOfCreatingParty) + "\n";
    }
}
