package com.raju.ipl;

import java.io.File;
import java.util.*;

public class Main {

    public static final int ID = 0;

    public static final int SEASON = 1;

    public static final int CITY = 2;

    public static final int DATE = 3;

    public static final int MATCH_TEAM1 = 4;

    public static final int MATCH_TEAM2 = 5;

    public static final int TOSS_WINNER = 6;

    public static final int TOSS_DECISION = 7;

    public static final int RESULT = 8;

    public static final int DL_APPLIED = 9;

    public static final int WINNER = 10;

    public static final int WIN_BY_RUNS = 11;

    public static final int WIN_BY_WICKETS = 12;

    public static final int PLAYER_OF_THE_MATCH = 13;

    public static final int VENUE = 14;

    public static final int UMPIRE1 = 15;

    public static final int UMPIRE2 = 16;

    public static final int UMPIRE3 = 17;

    public static final int MATCH_ID = 0;

    public static final int INNING = 1;

    public static final int BATTING_TEAM = 2;

    public static final int BOWLING_TEAM = 3;

    public static final int OVER = 4;

    public static final int BALL = 5;

    public static final int BATSMAN = 6;

    public static final int NON_STRIKER = 7;

    public static final int BOWLER = 8;

    public static final int IS_SUPER_OVER = 9;

    public static final int WIDE_RUNS = 10;

    public static final int BYE_RUNS = 11;

    public static final int LEGBYE_RUNS = 12;

    public static final int NOBALL_RUNS = 13;

    public static final int PENALTY_RUNS = 14;

    public static final int BATSMAN_RUNS = 15;

    public static final int EXTRA_RUNS = 16;

    public static final int TOTAL_RUNS = 17;

    public static final int PLAYER_DISMISSED = 18;

    public static final int DISMISSAL_KIND = 19;

    public static final int FIELDER = 20;

    public static HashMap<String, Integer> noOfMatchesPlayedPerYearOfAllTheYears = new HashMap<>();
    public static HashMap<String, Integer> noOfMatchesWonOfAllTeamsOverAllTheYears = new HashMap<>();

    public static void main(String[] args) {
        List<Match> matches = getMatchesData();
        List<Deliveries> deliveries = getDeliveriesData();

        findNumberOfMatchesPlayedPerYearOfAllTheYears(matches);
        findNumberOfMatchesWonPerTeamInAllSeasons(matches);
    }

    private static void findNumberOfMatchesWonPerTeamInAllSeasons(List<Match> matches) {
        for (Match obj : matches) {
            if (noOfMatchesWonOfAllTeamsOverAllTheYears.containsKey(obj.getWinner())) {
                noOfMatchesWonOfAllTeamsOverAllTheYears.put(obj.getWinner(),noOfMatchesWonOfAllTeamsOverAllTheYears.get(obj.getWinner())+1);
            } else {
                if(obj.getWinner().equals("")){
                    continue;
                }
                noOfMatchesWonOfAllTeamsOverAllTheYears.put(obj.getWinner(), 1);
            }
        }
        System.out.println("Number of matches won of all teams over all the years of IPL :");
        System.out.println(noOfMatchesWonOfAllTeamsOverAllTheYears);
    }

    private static void findNumberOfMatchesPlayedPerYearOfAllTheYears(List<Match> matches) {
        for (Match obj : matches) {
            if (noOfMatchesPlayedPerYearOfAllTheYears.containsKey(obj.getSeason())) {
                noOfMatchesPlayedPerYearOfAllTheYears.put(obj.getSeason(), noOfMatchesPlayedPerYearOfAllTheYears.get(obj.getSeason()) + 1);
            } else {
                noOfMatchesPlayedPerYearOfAllTheYears.put(obj.getSeason(), 1);
            }
        }
        System.out.println("Number of matches played per year of all the years in IPL :");
        System.out.println(noOfMatchesPlayedPerYearOfAllTheYears);
    }

    public static List<Match> getMatchesData() {
        List<Match> matches = new ArrayList<>();

        try {
            File fileObj = new File("./matches.csv");
            Scanner readObj = new Scanner(fileObj);
            boolean skipFirstIteration = true;
            while (readObj.hasNextLine()) {
                String line = readObj.nextLine();
                if (skipFirstIteration) {
                    skipFirstIteration = false;
                    continue;
                }
                String[] data = line.split(",");
                Match obj = new Match();
                obj.setMatchId(data[ID]);
                obj.setSeason(data[SEASON]);
//                obj.setCity(data[CITY]);
//                obj.setDate(data[DATE]);
//                obj.setTeam1(data[MATCH_TEAM1]);
//                obj.setTeam2(data[MATCH_TEAM2]);
//                obj.setTossWinner(data[TOSS_WINNER]);
//                obj.setTossDecision(data[TOSS_DECISION]);
//                obj.setResult(data[RESULT]);
//                obj.setDlApplied(data[DL_APPLIED]);
                obj.setWinner(data[WINNER]);
//                obj.setWinByRuns(data[WIN_BY_RUNS]);
//                obj.setWinByWickets(data[WIN_BY_WICKETS]);
//                obj.setPlayerOfMatch(data[PLAYER_OF_THE_MATCH]);
//                obj.setVenue(data[VENUE]);
//                obj.setUmpire1(data[UMPIRE1]);
//                obj.setUmpire2(data[UMPIRE2]);
                //               obj.setUmpire2(data[UMPIRE3]);
                matches.add(obj);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return matches;
    }

    public static List<Deliveries> getDeliveriesData() {
        List<Deliveries> deliveries = new ArrayList<Deliveries>();
        try {
            File fileObj = new File("./deliveries.csv");
            Scanner readObj = new Scanner(fileObj);
            boolean skipFirstIteration = true;
            while (readObj.hasNextLine()) {
                String line = readObj.nextLine();
                if (skipFirstIteration) {
                    skipFirstIteration = false;
                    continue;
                }
                String[] data = line.split(",");
                Deliveries obj = new Deliveries();
                deliveries.add(obj);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return deliveries;
    }
}
