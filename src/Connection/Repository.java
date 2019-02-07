package Connection;

import model.Athlet;
import model.Competition;
import model.Seat;
import model.Team;

import java.util.List;

/**
 * Represents all operations that can be executed to get or set data from storage (lke MySQL database)
 */
public interface Repository {
    List<Team> getTeams();

    Team getTeamById(int id);

    void addTeam(Team team);

    void removeTeam(int team_id);

    void updateTeam(String nameOfTeamOld, Team team);

    List<Athlet> getAthletes();

    Athlet getAthletById(int id);

    void addAthlet(Athlet athlet);

    void removeAthlet(int athlet_id);

    void updateAthlet(String athletesLastName, Athlet athlet);

    List<Competition> getCompetition();

    Competition getCompetitionById(int id);

    void addCompetition(Competition competition);

    void removeCompetition(int competition_id);

    void updateCompetition(int id, Competition competition);

    List<Seat> getSeat();

    Seat getSeatById(int id);

}
