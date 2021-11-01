package com.election.service;

import com.election.type.PrecinctType;
import com.election.valueobject.Citizen;
import com.election.valueobject.Precinct;

import java.util.*;
import java.util.stream.Collectors;

public class PrecinctService {

    Scanner in = new Scanner(System.in);
    int index;
    String address;
    PrecinctType typeOfPrecinct;
    List<Citizen> acceptedVoters = new ArrayList<>();
    double proportionOfVoters = 0.0;


    public void addPrecinct(List<Precinct> precinctList, List<Citizen> citizenList) {
        try {
            System.out.println("Введите уникальный индекс участка: ");
            index = Integer.parseInt(in.nextLine());
            if (checkIfIndexIsUnique(index, precinctList)) {
                throw new Exception("Введенный индекс участка не уникален!");
            }
            address = setAddress();
            if (citizenList.size() > 0) {
                acceptedVoters = getAcceptedVoters(citizenList);
                proportionOfVoters = getProportionOfVoters(citizenList);
            }
            setTypeOfPrecinct();
            precinctList.add(new Precinct(index, address, proportionOfVoters, typeOfPrecinct, acceptedVoters));
        } catch (Exception e) {
            System.err.println(e.getMessage());
            System.out.println();
        }
    }

    private void setTypeOfPrecinct() throws Exception {
        System.out.println("Выберите тип участка: 1 - Обычный, 2 - для граждан на карантине, 3 - для военных или работников спецслужб.");
        switch (Integer.parseInt(in.nextLine())) {
            case 1 -> typeOfPrecinct = PrecinctType.NORMAL;
            case 2 -> typeOfPrecinct = PrecinctType.QUARANTINE;
            case 3 -> typeOfPrecinct = PrecinctType.MILITARY;
            default -> throw new Exception("Указанный тип не найден!");
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
        return (float) (acceptedVoters.size() * 100 / list.size());
    }

    public void showAllPrecincts(List<Precinct> precinctList, List<Citizen> citizenList) throws Exception {
        System.out.println("1. Показать список избирательных участков.");
        System.out.println("2. Показать закрепленных избирателей для указанного участка.");
        System.out.println("3. Добавить избирателя к указанному участку.");
        System.out.println("4. Удалить избирателя из указанного участка.");
        switch (Integer.parseInt(in.nextLine())) {
            case 1 -> viewPrecinctList(precinctList);
            case 2 -> viewAcceptedCitizens(precinctList, citizenList);
            case 3 -> addCitizenToPrecinct(precinctList, citizenList);
            case 4 -> delCitizenFromPrecinct(precinctList, citizenList);
        }
    }

    private void viewPrecinctList(List<Precinct> precinctList) {
        try {
            if (checkIfPrecinctsIsExist(precinctList)) {
                throw new Exception("Список участков пуст! Добавьте участок");
            }
            precinctList.forEach(System.out::println);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            System.out.println();
        }

    }

    private void viewAcceptedCitizens(List<Precinct> precinctList, List<Citizen> citizenList) throws Exception {
        try {
            if (checkIfCitizensIsExist(citizenList)) {
                throw new Exception("Список граждан пуст! Добавьте гражданина.");
            }
            System.out.println("Укажите индекс участка: ");
            precinctList.forEach(System.out::println);
            int index = Integer.parseInt(in.nextLine());
            citizenList
                    .stream()
                    .filter(citizen -> citizen.getPrecinct().getIndex() == index)
                    .forEach(System.out::println);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            System.out.println();
        }
    }

    private void addCitizenToPrecinct(List<Precinct> precinctList, List<Citizen> citizenList) {
        try {
            checkIfPrecinctsIsExist(precinctList);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            System.out.println();
        }
        System.out.println("Укажите ID участка, который вы хотите закрепить за гражданином: ");
        precinctList.forEach(System.out::println);
        int indexOfPrecinct = Integer.parseInt(in.nextLine());
        System.out.println("Укажите ID гражданина, которого хотите закрепить за данным участком: ");
        citizenList.forEach(System.out::println);
        int idOfCitizen = Integer.parseInt(in.nextLine());
        Precinct precinctById = precinctList.stream()
                .filter(precinct -> precinct.getIndex() == indexOfPrecinct)
                .findFirst().orElse(null);
        citizenList.stream()
                .filter(citizen -> citizen.getId() == idOfCitizen)
                .filter(citizen -> citizen.getCitizenType().equals(Objects.requireNonNull(precinctById).getTypeOfPrecinct()))
                .forEach(citizen -> citizen.setPrecinct(choosePrecinctForCitizen(precinctList, indexOfPrecinct)));
    }

    private void delCitizenFromPrecinct(List<Precinct> precinctList, List<Citizen> citizenList) {
        try {
            checkIfPrecinctsIsExist(precinctList);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            System.out.println();
        }
        System.out.println("Укажите ID участка, которому нужно отвязать гражданина: ");
        precinctList.forEach(System.out::println);
        int indexOfPrecinct = Integer.parseInt(in.nextLine());
        System.out.println("Укажите ID гражданина, которого нужно открепить от данного участка: ");
        citizenList
                .stream()
                .filter(citizen -> citizen.getPrecinct().getIndex() == indexOfPrecinct)
                .forEach(citizen -> citizen.setPrecinct(null));
    }

    private Precinct choosePrecinctForCitizen(List<Precinct> list, int index) {
        return list.stream().filter(precinct -> precinct.getIndex() == index).findFirst().orElse(null);
    }

    private boolean checkIfPrecinctsIsExist(List<Precinct> precinctList) {
        return precinctList.size() < 1;
    }

    private boolean checkIfCitizensIsExist(List<Citizen> citizenList) {
        return citizenList.size() < 1;
    }
}
