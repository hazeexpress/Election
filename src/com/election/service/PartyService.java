package com.election.service;

import com.election.type.FractionType;
import com.election.valueobject.Citizen;
import com.election.valueobject.Party;

import java.nio.file.Path;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Stream;

public class PartyService {

    private final Scanner in = new Scanner(System.in);

    public void addPartyToList(List<Party> list) {
        try {
            System.out.println("Введите название политической партии: ");
            String nameOfParty = in.nextLine();
            System.out.println("Какой фракции принадлежит партия: 1 - Левая, 2 - Правая, 3 - Центр.");
            FractionType partyType = getPartyType();
            System.out.println("Введите дату создания партии в формате ДД.ММ.ГГГГ");
            Date dateOfCreationParty = getDateOfCreationParty(in.nextLine());
            System.out.println("Укажите ID партии: ");
            int id = Integer.parseInt(in.nextLine());
            if (checkIfIdIsUnique(list, id)) {
                throw new Exception("Партия с указанным ID существует!");
            }
            list.add(new Party(nameOfParty, partyType, dateOfCreationParty, id));
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

    }

    private FractionType getPartyType() throws Exception {
        String numberOfParty = in.nextLine();
        switch (numberOfParty) {
            case "1" -> {
                return FractionType.LEFT;
            }
            case "2" -> {
                return FractionType.RIGHT;
            }
            case "3" -> {
                return FractionType.CENTER;
            }
            default -> throw new Exception("Фракция не найдена");
        }
    }

    private Date getDateOfCreationParty(String date) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy");
        return simpleDateFormat.parse(date);
    }

    private boolean checkIfIdIsUnique(List<Party> partyList, int id) {
        return partyList.stream().anyMatch(party -> party.getId() == id);
    }

    private Party choosePartyForCandidate(List<Party> list) {
        System.out.println("Введите ID партии, к которой нужно присвоить кандидата.");
        list.forEach(System.out::println);
        int id = Integer.parseInt(in.nextLine());
        Stream<Party> temp = list.stream().filter(party -> party.getId() == id);
        return (Party) temp;
    }

    public void addCandidateToParty(List<Party> partyList, List<Citizen> citizenList) {
        if (citizenList.size() > 0) {
            System.out.println("Укажите гражданина, которого хотите указать как кандидата от партии.");
            citizenList.forEach(System.out::println);
            int citizenId = Integer.parseInt(in.nextLine());
            System.out.println("Укажите рейтинг который хотите установить кандидату.");
            double citizenRating = Double.parseDouble(in.nextLine());
            citizenList
                    .stream()
                    .filter(citizen -> citizen.getId() == citizenId)
                    .forEach(citizen -> {
                        citizen.setParty(choosePartyForCandidate(partyList));
                        citizen.setRating(citizenRating);
                    });
        } else {
            System.out.println("Список граждан пуст. Добавьте минимум одного гражданина!");
        }
    }

    public void viewAllParty(List<Party> partyList, List<Citizen> citizenList) {
        System.out.println("1. Показать список всех партий.");
        System.out.println("2. Добавить кандидата к указанной партии.");
        System.out.println("3. Удалить кандидата из указанной партии.");
        System.out.println("4. Показать список всех кандидатов к указанной партии.");
        switch (Integer.parseInt(in.nextLine())) {
            case 1 -> {
                if (partyList.size() > 0) {
                    partyList.forEach(System.out::println);
                } else {
                    System.out.println("Список партий пуст!");
                }
            }
            case 2 -> addCandidateToParty(partyList, citizenList);
            case 3 -> delCandidateFromParty(partyList, citizenList);
            case 4 -> viewAllCandidatesFromParty(partyList, citizenList);
        }
    }

    private void delCandidateFromParty(List<Party> partyList, List<Citizen> citizenList) {
        if (citizenList.size() > 0) {
            System.out.println("Укажите ID партии, из которой нужно удалить кандидата: ");
            partyList.forEach(System.out::println);
            int idParty = Integer.parseInt(in.nextLine());
            System.out.println("Укажите ID кандидата, которого нужно удалить из партии: ");
            citizenList.stream().filter(citizen -> citizen.getParty().getId() == idParty).forEach(System.out::println);
            int idCandidate = Integer.parseInt(in.nextLine());
            citizenList.stream().filter(citizen -> citizen.getId() == idCandidate).forEach(citizen -> citizen.setParty(null));
        } else {
            System.out.println("Список граждан пуст. Добавьте минимум одного гражданина!");
        }
    }

    private void viewAllCandidatesFromParty(List<Party> partyList, List<Citizen> citizenList) {
        if (citizenList.size() > 0) {
            System.out.println("Укажите ID партии, из которой нужно взять список всех кандидатов: ");
            partyList.forEach(System.out::println);
            int idParty = Integer.parseInt(in.nextLine());
            citizenList.stream().filter(citizen -> citizen.getParty().getId() == idParty).forEach(System.out::println);
        } else {
            System.out.println("Список граждан пуст. Добавьте минимум одного гражданина!");
        }
    }
}
