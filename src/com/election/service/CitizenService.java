package com.election.service;

import com.election.valueobject.Citizen;

import java.util.List;
import java.util.Scanner;

public class CitizenService {
    private final Scanner in = new Scanner(System.in);

    public void addCitizenToListFromConsole(List<Citizen> citizenList) {
        try {
            System.out.println("Введите id гражданина: ");
            int id = Integer.parseInt(in.nextLine());
            if (checkIsExistCitizenWithIdInList(id, citizenList)) {
                throw new Exception("Гражданин с данным id уже существует");
            }
            System.out.println("Введите имя гражданина: ");
            String name = in.nextLine();
            System.out.println("Введите номер паспорта гражданина: ");
            String passNumber = in.nextLine();
            System.out.println("Введите ИНН гражданина: ");
            long inn = getInn();
            System.out.println("Введите год рождения гражданина: ");
            int yearOfBirth = Integer.parseInt(in.nextLine());
            System.out.println("Является ли избиратель военным или сотрудником спецслужб: 1 - Да, 2 - Нет.");
            boolean ifMilitary = Integer.parseInt(in.nextLine()) == 1;
            System.out.println("Находится ли избиратель на карантине: 1 - Да, 2 - Нет.");
            boolean ifOnQuarantine = Integer.parseInt(in.nextLine()) == 1;
            citizenList.add(new Citizen(id, name, passNumber, inn, yearOfBirth, ifMilitary, ifOnQuarantine));
        } catch (Exception e) {
            System.err.println(e.getMessage());
            System.out.println();
        }
    }

    public void delCitizen(List<Citizen> list) {
        if (list.size() > 0) {
            System.out.println("Укажите порядковый номер избирателя, которого хотите удалить: ");
            list.remove(Integer.parseInt(in.nextLine()));
        } else {
            System.out.println("Список избирателей пуст!");
        }
    }

    private long getInn() throws Exception {
        long inn = Long.parseLong(in.nextLine());
        int length = (int) (Math.log10(inn) + 1);
        if (length != 10) {
            throw new Exception("ИНН не равен 10 символам!");
        }
        return inn;
    }

    public boolean checkIsExistCitizenWithIdInList(int id, List<Citizen> citizens) {
        return citizens.stream().anyMatch(citizen -> id == citizen.getId());
    }
}
