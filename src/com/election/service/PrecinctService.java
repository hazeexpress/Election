package com.election.service;

import com.election.type.PrecinctType;
import com.election.valueobject.Citizen;
import com.election.valueobject.Precinct;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class PrecinctService {

    Scanner in = new Scanner(System.in);
    int index;
    String address;
    PrecinctType typeOfPrecinct;
    List<Citizen> acceptedVoters = new ArrayList<>();
    double proportionOfVoters;


    public void addPrecinct(List<Precinct> precinctList, List<Citizen> citizenList) {
        try {
            index = Integer.parseInt(in.nextLine());
            if (checkIfIndexIsUnique(index, precinctList)) {
                throw new Exception("Введенный индекс участка не уникален!");
            }
            address = setAddress();
            acceptedVoters = getAcceptedVoters(citizenList);
            proportionOfVoters = getProportionOfVoters(citizenList);
            setTypeOfPrecinct();
            precinctList.add(new Precinct(index, address, proportionOfVoters, typeOfPrecinct, acceptedVoters));
        } catch (Exception e) {
            System.err.println(e.getMessage());
            System.out.println();
        }
    }

    private void setTypeOfPrecinct() {
        System.out.println("Выберите тип участка: 1 - Обычный, 2 - для граждан на карантине, 3 - для военных или работников спецслужб.");
        switch (Integer.parseInt(in.nextLine())) {
            case 1 -> typeOfPrecinct = PrecinctType.NORMAL;
            case 2 -> typeOfPrecinct = PrecinctType.QUARANTINE;
            case 3 -> typeOfPrecinct = PrecinctType.MILITARY;
            default -> System.out.println("Выберите тип от 1 до 3.");
        }
    }

    private boolean checkIfIndexIsUnique(int index, List<Precinct> list) {
        return list.stream().anyMatch(precinct -> index == precinct.getIndex());
    }

    private String setAddress() {
        System.out.println("Введите адрес участка: ");
        String address;
        do {
            address = in.nextLine();
        } while (address.trim().isEmpty());
        return address;
    }

    private List<Citizen> getAcceptedVoters(List<Citizen> list) {
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        return list
                .stream()
                .filter(citizen -> citizen.getYearOfBirth() - currentYear > 18)
                .collect(Collectors.toList());
    }

    private double getProportionOfVoters(List<Citizen> list) {
        return (float)(acceptedVoters.size() * 100 / list.size());
    }
}
