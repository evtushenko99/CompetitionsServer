package model;


public class Team {
    private int teams_id;
    private String name_of_team;
    private String coach;
    private String captain;

    public Team(int teams_id, String name_of_team, String coach,String captain){
        this.teams_id = teams_id;
        this.name_of_team = name_of_team;
        this.coach = coach;
        this.captain = captain;
    }
    public Team(int teams_id, String name_of_team, String coach){
        this.teams_id = teams_id;
        this.name_of_team = name_of_team;
        this.coach = coach;
    }
    public Team(String captain){
        this.captain = captain;
    }

    public int getTeams_id(){
        return teams_id;
    }

    public void setTeams_id(int teams_id){
        this.teams_id = teams_id;
    }

    public String getName_of_team(){
        return name_of_team;
    }

    public void setName_of_team(String name_of_team){
        this.name_of_team = name_of_team;
    }

    public String getCoach(){
        return coach;
    }

    public void setCoach(String coach){
        this.coach = coach;
    }

    public String getCaptain(){
        return captain;
    }

    public void setCaptain(String captain){
        this.captain = captain;
    }

    @Override
    public String toString(){
        if (getCaptain() != null) {
            return "ID: " + getTeams_id() + "\n" +
                    "Имя команды: " + getName_of_team() + "\n" +
                    "Имя главного стренера: " + getCoach() + "\n" +
                    "Капитан команды: " + getCaptain();
        } else { return "ID: " + getTeams_id() + "\n" +
                "Имя команды: " + getName_of_team() + "\n" +
                "Имя главного стренера: " + getCoach() + "\n";
        }
    }
}
