package Connection;

import model.Athlet;
import model.Competition;
import model.Seat;
import model.Team;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RepositoryMySQL implements Repository {
    private static final int PORT_DEFAULT = 3306;
    private final String ip;
    private final int port;
    private final String databaseName;
    private final Connection connection;

    public RepositoryMySQL(String ip, int port, String databaseName, String databaseUser, String databasePassword) throws SQLException{
        this.ip = ip;
        this.port = port;
        this.databaseName = databaseName;
        this.connection = DriverManager.getConnection(getDatabaseUrl(), databaseUser, databasePassword);
    }

    public RepositoryMySQL(String ip, String databaseName, String databaseUser, String databasePassword) throws SQLException{
        this(ip, PORT_DEFAULT, databaseName, databaseUser, databasePassword);
    }

    private String getDatabaseUrl(){
        return String.format("jdbc:mysql://%s:%d/%s?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC",
                this.ip, this.port, this.databaseName);
    }

    @Override
    public List<Team> getTeams(){
        List<Team> teams = new ArrayList<>();
        try {
            ResultSet resultSet = executeQuery("Select teams.teams_id,teams.name_of_team,teams.coach,athletes.athlet_last_name From teams,athletes \n" +
                    "Where teams.captain = athletes.athletes_id;");
            while (resultSet.next()) {
                teams.add(readTeam(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return teams;
    }

    private Team readTeam(ResultSet resultSet) throws SQLException{
        return new Team(
                resultSet.getInt(1),
                resultSet.getString(2),
                resultSet.getString(3),
                resultSet.getString(4));
    }

    @Override
    public Team getTeamById(int id){
        Team team = null;
        try {
            String query = String.format(
                    "Select teams.teams_id,teams.name_of_team,teams.coach,athletes.athlet_last_name " +
                            "From teams,athletes " +
                            "Where teams.captain = athletes.athletes_id AND teams.teams_id = '%d';", id);
            ResultSet resultSet = executeQuery(query);
            while (resultSet.next()) {
                team = readTeam(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return team;
    }

    @Override
    public void addTeam(Team team){
        try {
            String query = String.format(
                    "INSERT INTO `teams` " +
                            "(teams_id, `name_of_team`, `coach`) " +
                            "VALUES ('%d', '%s', '%s');",
                    team.getTeams_id(), team.getName_of_team(), team.getCoach());
            executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removeTeam(int team_id){
        try {
            String delete = String.format(
                    "Delete FROM teams WHERE teams.teams_id = '%d';", team_id);
            executeUpdate(delete);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void updateTeam(String nameOfTeamOld, Team team){
        try {
            String update = String.format(
                    "UPDATE teams  " +
                            "SET name_of_team = '%s'," +
                            "coach = '%s' " +
                            "Where name_of_team = '%s';", team.getName_of_team(), team.getCoach(), nameOfTeamOld);
            executeUpdate(update);
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    @Override
    public List<Athlet> getAthletes(){

        List<Athlet> athletes = new ArrayList<>();
        try {
            ResultSet resultSet = executeQuery("SELECT athletes.athletes_id,athletes.athlet_first_name,athletes.athlet_last_name,athletes.athlet_age,teams.name_of_team " +
                    "FROM athletes,teams " +
                    "Where athletes.history_of_teams = teams.teams_id;");
            while (resultSet.next()) {
                athletes.add(readAthlet(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return athletes;
    }

    private Athlet readAthlet(ResultSet resultSet) throws SQLException{
        return new Athlet(
                resultSet.getInt(1),
                resultSet.getString(2),
                resultSet.getString(3),
                resultSet.getString(4),
                resultSet.getString(5));
    }

    @Override
    public Athlet getAthletById(int id){
        Athlet athlet = null;
        try {
            String query = String.format(
                    "SELECT athletes.athletes_id,athletes.athlet_first_name,athletes.athlet_last_name,athletes.athlet_age,teams.name_of_team " +
                            "FROM athletes,teams " +
                            "Where athletes.history_of_teams = teams.teams_id AND athletes.athletes_id = '%d';", id);
            ResultSet resultSet = executeQuery(query);
            while (resultSet.next()) {
                athlet = readAthlet(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return athlet;
    }

    @Override
    public void addAthlet(Athlet athlet){
        try {
            String query = String.format(
                    "INSERT INTO `athletes` " +
                            "(`athlet_first_name`, `athlet_last_name`, `athlet_age`) " +
                            "VALUES ('%s', '%s', '%s');",
                    athlet.getAthlet_first_name(), athlet.getAthlet_last_name(), athlet.getAthlet_age());
            executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removeAthlet(int athlet_id){
        try {
            String delete = String.format(
                    "Delete FROM athletes WHERE athletes.athletes_id = '%d';", athlet_id);
            executeUpdate(delete);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void updateAthlet(String athletesLastName, Athlet athlet){
        try {
            String update = String.format(
                    "UPDATE athletes  " +
                            "SET athlet_first_name = '%s'," +
                            "athlet_last_name = '%s'," +
                            "athlet_age = '%s' " +
                            "Where athlet_last_name = '%s';",
                    athlet.getAthlet_first_name(), athlet.getAthlet_last_name(), athlet.getAthlet_age(), athletesLastName);
            executeUpdate(update);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Competition> getCompetition(){
        List<Competition> competitions = new ArrayList<>();
        try {
            ResultSet resultSet = executeQuery("SELECT competitions.competitions_id,competitions.tournament_name, " +
                    "competitions.locations, competitions.kind_of_sport,competitions.time_of_comp, " +
                    "teams.name_of_team,athletes.athlet_last_name,seats.price_for_seat,competitions.results " +
                    "FROM competitions,teams,athletes,seats " +
                    "Where competitions.participating_athletes = athletes.athletes_id AND " +
                    "competitions.participating_teams = teams.teams_id AND competitions.seats = seats.seats_id;");
            while (resultSet.next()) {
                competitions.add(readCompetition(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return competitions;
    }

    private Competition readCompetition(ResultSet resultSet) throws SQLException{
        return new Competition(resultSet.getInt(1),
                resultSet.getString(2),
                resultSet.getString(3),
                resultSet.getString(4),
                resultSet.getString(5),
                resultSet.getString(6),
                resultSet.getString(7),
                resultSet.getString(8),
                resultSet.getString(9));
    }

    @Override
    public Competition getCompetitionById(int id){
        Competition competition = null;
        try {
            String query = String.format(
                    "SELECT competitions.competitions_id,competitions.tournament_name, " +
                            "competitions.locations, competitions.kind_of_sport,competitions.time_of_comp, " +
                            "teams.name_of_team,athletes.athlet_last_name,seats.price_for_seat,competitions.results " +
                            "FROM competitions,teams,athletes,seats " +
                            "Where competitions.participating_athletes = athletes.athletes_id AND " +
                            "competitions.participating_teams = teams.teams_id AND competitions.seats = seats.seats_id AND competitions_id = '%d';", id);
            ResultSet resultSet = executeQuery(query);
            while (resultSet.next()) {
                competition = readCompetition(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return competition;
    }

    @Override
    public void addCompetition(Competition competition){
        try {
            String query = String.format(
                    "INSERT INTO competitions " +
                            "(`tournament_name`, `locations`, `kind_of_sport`, `time_of_comp`,`results`) " +
                            "VALUES ('%s', '%s', '%s', '%s', '%s');",
                    competition.getTournament_name(), competition.getLocation(), competition.getKind_of_sport(),
                    competition.getTime_of_comp(), competition.getResults());
            executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removeCompetition(int competitions_id){
        try {
            String delete = String.format(
                    "Delete FROM competitions WHERE competitions.competitions_id = '%d';", competitions_id);
            executeUpdate(delete);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateCompetition(int id, Competition competition){
        try {
            String update = String.format(
                    "UPDATE competitions  " +
                            "SET tournament_name = '%s'," +
                            "locations = '%s'," +
                            "kind_of_sport = '%s', " +
                            "time_of_comp = '%s', " +
                            "results = '%s' " +
                            "Where competitions_id = '%d';",
                    competition.getTournament_name(), competition.getLocation(), competition.getKind_of_sport(),
                    competition.getTime_of_comp(), competition.getResults(), id);
            executeUpdate(update);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Seat> getSeat(){
        List<Seat> seats = new ArrayList<>();
        try {
            ResultSet resultSet = executeQuery("SELECT seats.seats_id,seats.booked_seats,seats.free_seats,seats.price_for_seat From seats;");
            while (resultSet.next()) {
                seats.add(readSeat(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return seats;
    }

    @Override
    public Seat getSeatById(int id){
        Seat seat = null;
        try {
            String query = String.format("SELECT seats.seats_id,seats.booked_seats,seats.free_seats,seats.price_for_seat From seats " +
                    "where seats.seats_id = '%d';", id);
            ResultSet resultSet = executeQuery(query);
            while (resultSet.next()) {
                seat = readSeat(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return seat;
    }

    private Seat readSeat(ResultSet resultSet) throws SQLException{
        return new Seat(
                resultSet.getInt(1),
                resultSet.getInt(2),
                resultSet.getInt(3),
                resultSet.getInt(4));
    }

    private ResultSet executeQuery(String query) throws SQLException{
        //Statement statement = connection.createStatement();
        return connection.createStatement().executeQuery(query);
    }

    private void executeUpdate(String update) throws SQLException{
        Statement statement = connection.createStatement();
        statement.executeUpdate(update);
    }
}
