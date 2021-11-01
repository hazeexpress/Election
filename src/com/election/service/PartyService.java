package com.election.service;

import com.election.type.FractionType;
import com.election.valueobject.Citizen;
import com.election.valueobject.Party;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class PartyService {

    private final Scanner in = new Scanner(System.in);

    public void addPartyToList(List<Party> list) {
        try {
            System.out.println("Введите название политической партии: ");
            String nameOfParty = in.nextLine();
            System.out.println("Какой фракции принадлежит партия: 1 - Левая, 2 - Правая, 3 - Центр.");
            FractionType partyType = getPartyTape();
            System.out.println("Введите дату создания партии в формате ДД.ММ.ГГГГ");
            Date dateOfCreationParty = getDateOfCreationParty(in.nextLine());
            list.add(new Party(nameOfParty, partyType, dateOfCreationParty));
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

    }

    private FractionType getPartyTape() throws Exception {
        String numberOfParty = in.nextLine();
        switch (numberOfParty) {
            case "1" -> { return FractionType.LEFT; }
            case "2" -> { return FractionType.RIGHT; }
            case "3" -> { return FractionType.CENTER; }
            default -> throw new Exception("Фракция не найдена");
        }
    }

    private Date getDateOfCreationParty(String date) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy");
        return simpleDateFormat.parse(date);
    }

    private void setCandidateFromParty(List<Citizen> citizenList) {
        System.out.println("Укажите гражданина, которого хотите указать как кандидата от партии.");
        citizenList.forEach(System.out::println);
        int citizenId = Integer.parseInt(in.nextLine());
        citizenList
                .stream()
                .filter(citizen -> citizen.getId() == citizenId)
    }
}
