package com.raju.ipl;

import jdk.jshell.JShell;

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

    public static HashMap<String, Integer> extraRunsConcededPerTeamIn2016 = new HashMap<>();

    public static void main(String[] args) {
        List<Match> matches = getMatchesData();
        List<Deliveries> deliveries = getDeliveriesData();

        findNumberOfMatchesPlayedPerYearOfAllTheYears(matches);
        findNumberOfMatchesWonPerTeamInAllSeasons(matches);
        findExtraRunsConcededPerTeamIn2016(matches, deliveries);
        findMostEconomicalBowlerIn2015(matches, deliveries);
        findHighestRunScorerInSeason2015(matches, deliveries);
    }

    private static void findHighestRunScorerInSeason2015(List<Match> matches, List<Deliveries> deliveries) {
        List<String> matchIdsIn2015 = new ArrayList<>();
        HashMap<String, Integer> individualBatsmanRunsIn2015 = new HashMap<>();

        for (Match obj : matches) {
            if (obj.getSeason().equals("2015")) {
                matchIdsIn2015.add(obj.getId());
            }
        }

        for (Deliveries obj : deliveries) {
            if(matchIdsIn2015.contains(obj.getMatchId())) {
                    if (individualBatsmanRunsIn2015.containsKey(obj.getBatsman())) {
                        individualBatsmanRunsIn2015.put(obj.getBatsman(), individualBatsmanRunsIn2015.get(obj.getBatsman()) + Integer.parseInt(obj.getBatsmanRuns()));
                    } else {
                        individualBatsmanRunsIn2015.put(obj.getBatsman(), Integer.parseInt(obj.getBatsmanRuns()));
                    }
            }
        }

        List<Map.Entry<String, Integer>> individualBatsmanRunsIn2015List = new ArrayList<Map.Entry<String, Integer>>();
        individualBatsmanRunsIn2015List.addAll(individualBatsmanRunsIn2015.entrySet());

        Collections.sort(individualBatsmanRunsIn2015List, new Comparator<Map.Entry<String, Integer>>() {
            @Override
            public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                return o2.getValue().compareTo(o1.getValue());
            }
        });

        System.out.println();
        System.out.println("Highest Run Scorer In The Year 2015 :");
        System.out.println(individualBatsmanRunsIn2015List.get(0));
    }

    private static void findMostEconomicalBowlerIn2015(List<Match> matches, List<Deliveries> deliveries) {
        List<String> matchIdsIn2015 = new ArrayList<>();
        HashMap<String, Integer> noOfBallsByBowler = new HashMap<>();
        HashMap<String, Integer> totalRunsByBowler = new HashMap<>();

        for (Match obj : matches) {
            if (obj.getSeason().equals("2015")) {
                matchIdsIn2015.add(obj.getId());
            }
        }

        for (Deliveries obj : deliveries) {
            if(matchIdsIn2015.contains(obj.getMatchId())) {
                    if (noOfBallsByBowler.containsKey(obj.getBowler())) {
                        noOfBallsByBowler.put(obj.getBowler(), noOfBallsByBowler.get(obj.getBowler()) + 1);
                    } else {
                        noOfBallsByBowler.put(obj.getBowler(), 1);
                    }
            }
        }

        for (Deliveries obj : deliveries) {
            if(matchIdsIn2015.contains(obj.getMatchId())) {
                    if (totalRunsByBowler.containsKey(obj.getBowler())) {
                        totalRunsByBowler.put(obj.getBowler(), totalRunsByBowler.get(obj.getBowler()) + Integer.parseInt(obj.getTotalRuns()));
                    } else {
                        totalRunsByBowler.put(obj.getBowler(), Integer.parseInt(obj.getTotalRuns()));
                    }
            }
        }

        HashMap<String, Integer> noOfOversByBowler = new HashMap<>();

        for (String key : noOfBallsByBowler.keySet()) {
            noOfOversByBowler.put(key, noOfBallsByBowler.get(key) / 6);
        }

        HashMap<String, Double> economyOfTheBowlers = new HashMap<>();

        for (String key : noOfOversByBowler.keySet()) {
            economyOfTheBowlers.put(key, ((double) totalRunsByBowler.get(key) / (double) noOfOversByBowler.get(key)));
        }

        List<Map.Entry<String, Double>> economyOfTheBowlersList = new ArrayList<Map.Entry<String, Double>>();

        economyOfTheBowlersList.addAll(economyOfTheBowlers.entrySet());

        Collections.sort(economyOfTheBowlersList, new Comparator<Map.Entry<String, Double>>() {
            @Override
            public int compare(Map.Entry<String, Double> o1, Map.Entry<String, Double> o2) {
                return o1.getValue().compareTo(o2.getValue());
            }
        });

        System.out.println();
        System.out.println("For the year 2015 get the top economical bowlers :");
        System.out.println(economyOfTheBowlersList);
    }

    private static void findExtraRunsConcededPerTeamIn2016(List<Match> matches, List<Deliveries> deliveries) {
        List<String> matchIdsIn2016 = new ArrayList<>();

        for (Match obj : matches) {
            if (obj.getSeason().equals("2016")) {
                matchIdsIn2016.add(obj.getId());
            }
        }

        for (Deliveries obj : deliveries) {
            if(matchIdsIn2016.contains(obj.getMatchId())) {
                    if (extraRunsConcededPerTeamIn2016.containsKey(obj.getBattingTeam())) {
                        extraRunsConcededPerTeamIn2016.put(obj.getBattingTeam(), extraRunsConcededPerTeamIn2016.get(obj.getBattingTeam()) + Integer.parseInt(obj.getExtraRuns()));
                    } else {
                        extraRunsConcededPerTeamIn2016.put(obj.getBattingTeam(), Integer.parseInt(obj.getExtraRuns()));
                    }
            }
        }

        System.out.println();
        System.out.println("For the year 2016 get the extra runs conceded per team :");
        System.out.println(extraRunsConcededPerTeamIn2016);
    }

    private static void findNumberOfMatchesWonPerTeamInAllSeasons(List<Match> matches) {
        for (Match obj : matches) {
            if (noOfMatchesWonOfAllTeamsOverAllTheYears.containsKey(obj.getWinner())) {
                noOfMatchesWonOfAllTeamsOverAllTheYears.put(obj.getWinner(), noOfMatchesWonOfAllTeamsOverAllTheYears.get(obj.getWinner()) + 1);
            } else {
                if (obj.getWinner().equals("")) {
                    continue;
                }
                noOfMatchesWonOfAllTeamsOverAllTheYears.put(obj.getWinner(), 1);
            }
        }

        System.out.println();
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

        System.out.println();
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
//                obj.setUmpire2(data[UMPIRE3]);
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
                obj.setMatchId(data[MATCH_ID]);
                obj.setInning(data[INNING]);
                obj.setBattingTeam(data[BATTING_TEAM]);
                obj.setBowlingTeam(data[BOWLING_TEAM]);
                //obj.setOver(data[OVER]);
                //obj.setBall(data[BALL]);
                obj.setBatsman(data[BATSMAN]);
                //obj.setNonStriker(data[NON_STRIKER]);
                obj.setBowler(data[BOWLER]);
                //obj.setIsSuperOver(data[IS_SUPER_OVER]);
                //obj.setWideRuns(data[WIDE_RUNS]);
                //obj.setByeRuns(data[BYE_RUNS]);
                //obj.setLegByeRuns(data[LEGBYE_RUNS]);
                //obj.setNoBallRuns(data[NOBALL_RUNS]);
                //obj.setPenaltyRuns(data[PENALTY_RUNS]);
                obj.setBatsmanRuns(data[BATSMAN_RUNS]);
                obj.setExtraRuns(data[EXTRA_RUNS]);
                obj.setTotalRuns(data[TOTAL_RUNS]);
                //obj.setPlayerDismissed(data[PLAYER_DISMISSED]);
                //obj.setDismissalKind(data[DISMISSAL_KIND]);
                //obj.setFielder(data[FIELDER]);
                deliveries.add(obj);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return deliveries;
    }

}
