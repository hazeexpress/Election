package com.election;
import com.election.service.CitizenService;
import com.election.service.PartyService;
import com.election.service.PrecinctService;
import com.election.valueobject.Citizen;
import com.election.valueobject.Party;
import com.election.valueobject.Precinct;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Menu {

    Scanner in = new Scanner(System.in);
    List<Precinct> precinctList = new ArrayList<>();
    List<Citizen> citizenList = new ArrayList<>();
    List<Party> partiesList = new ArrayList<>();

    public static void main(String[] args) {
        Menu menu = new Menu();
        menu.run();
    }

    private void run() {
        PrecinctService precinctService = new PrecinctService();
        CitizenService citizenService = new CitizenService();
        PartyService partyService = new PartyService();
        boolean f = true;
        while (f) {
            System.out.println("Make your choice...");
            System.out.println("1. Add precinct.");
            System.out.println("2. Add citizen.");
            System.out.println("3. Add party.");
            System.out.println("4. Select citizen as a candidate.");
            System.out.println("5. Show all precincts.");
            System.out.println("6. Show all citizen's.");
            System.out.println("7. Show all parties.");
            System.out.println("8. Run elections.");
            System.out.println("9. Show election results.");
            System.out.println("10. Out of program.");
            int menuChoice = in.nextInt();
            switch (menuChoice) {
                case 1 -> precinctService.addPrecinct(precinctList, citizenList);
                case 2 -> citizenService.addCitizenToListFromConsole(citizenList);
                case 3 -> partyService.addPartyToList(partiesList);
                case 4 -> partyService.addCandidateToParty(partiesList, citizenList);
                case 5 -> precinctService.showAllPrecincts(precinctList, citizenList);
                case 6 -> citizenService.viewAllCitizens(citizenList);
                case 7 -> partyService.viewAllParty(partiesList, citizenList);
                case 8 -> System.out.println("test");
                case 9 -> System.out.println("test");
                case 10 -> f = false;
            }
        }
    }
}
