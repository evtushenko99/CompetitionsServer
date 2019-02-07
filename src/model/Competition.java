package model;

public class Competition {
    private int competitions_id;
    private String tournament_name;
    private String location;
    private String kind_of_sport;
    private String time_of_comp;
    private String participating_team;
    private String participating_athletes;
    private String seat;
    private String results;//счет или очки, распределение местсчет или очки, распределение мест

    public Competition(int competitions_id, String tournament_name, String location, String kind_of_sport, String time_of_comp, String results){
        this.competitions_id = competitions_id;
        this.tournament_name = tournament_name;
        this.location = location;
        this.kind_of_sport = kind_of_sport;
        this.time_of_comp = time_of_comp;
        this.results = results;
    }
    public Competition(int competitions_id, String tournament_name, String location, String kind_of_sport, String time_of_comp,String participating_team, String participating_athletes, String seat,  String results){
        this.competitions_id = competitions_id;
        this.tournament_name = tournament_name;
        this.location = location;
        this.kind_of_sport = kind_of_sport;
        this.time_of_comp = time_of_comp;
        this.participating_team = participating_team;
        this.participating_athletes = participating_athletes;
        this.seat = seat;
        this.results = results;
    }
    public void setTAS(String participating_team, String participating_athletes, String seat){
        this.participating_team = participating_team;
        this.participating_athletes = participating_athletes;
        this.seat = seat;
    }

    public int getCompetitions_id(){
        return competitions_id;
    }

    public void setCompetitions_id(int competitions_id){
        this.competitions_id = competitions_id;
    }

    public String getTournament_name(){
        return tournament_name;
    }

    public void setTournament_name(String tournament_name){
        this.tournament_name = tournament_name;
    }

    public String getLocation(){
        return location;
    }

    public void setLocation(String location){
        this.location = location;
    }

    public String getKind_of_sport(){
        return kind_of_sport;
    }

    public void setKind_of_sport(String kind_of_sport){
        this.kind_of_sport = kind_of_sport;
    }

    public String getTime_of_comp(){
        return time_of_comp;
    }

    public void setTime_of_comp(String time_of_comp){
        this.time_of_comp = time_of_comp;
    }

    public String getParticipating_team(){
        return participating_team;
    }

    public void setParticipating_team(String participating_team){
        this.participating_team = participating_team;
    }

    public String getParticipating_athletes(){
        return participating_athletes;
    }

    public void setParticipating_athletes(String participating_athletes){
        this.participating_athletes = participating_athletes;
    }

    public String getSeat(){
        return seat;
    }

    public void setSeat(String seat){
        this.seat = seat;
    }

    public String getResults(){
        return results;
    }

    public void setResults(String results){
        this.results = results;
    }

    @Override
    public String toString(){
        if (participating_athletes != null && participating_team == null && seat == null) {
            return "ID: " + getCompetitions_id() + "\n" +
                    "Tournament name: " + getTournament_name() + "\n" +
                    "Location: " + getLocation() + "\n" +
                    "Kind of sport: " + getKind_of_sport() + "\n" +
                    "Time of competition: " + getTime_of_comp() + "\n" +
                    "Athlet: " + getParticipating_athletes() + "\n" +
                    "Results:" + getResults();
        } else if (participating_team != null && participating_athletes != null && seat == null) {
            return "ID: " + getCompetitions_id() + "\n" +
                    "Tournament name: " + getTournament_name() + "\n" +
                    "Location: " + getLocation() + "\n" +
                    "Kind of sport: " + getKind_of_sport() + "\n" +
                    "Time of competition: " + getTime_of_comp() + "\n" +
                    "Team: " + getParticipating_team() + "\n" +
                    "Athlet: " + getParticipating_athletes() + "\n" +
                    "Results:" + getResults();
        } else if (seat != null && participating_team != null && participating_athletes != null ) {
            return "ID: " + getCompetitions_id() + "\n" +
                    "Tournament name: " + getTournament_name() + "\n" +
                    "Location: " + getLocation() + "\n" +
                    "Kind of sport: " + getKind_of_sport() + "\n" +
                    "Time of competition: " + getTime_of_comp() + "\n" +
                    "Team: " + getParticipating_team() + "\n" +
                    "Athlet: " + getParticipating_athletes() + "\n" +
                    "Free seat" +  getSeat() + "\n" +
                    "Results:" + getResults();
        } else {
            return "ID: " + getCompetitions_id() + "\n" +
                    "Tournament name: " + getTournament_name() + "\n" +
                    "Location: " + getLocation() + "\n" +
                    "Kind of sport: " + getKind_of_sport() + "\n" +
                    "Time of competition: " + getTime_of_comp() + "\n" +
                    "Results:" + getResults();
        }
    }

}

