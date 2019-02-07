import Connection.Commands;
import Connection.Repository;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mysql.cj.protocol.a.NativeConstants;
import model.Athlet;
import model.Competition;
import model.Seat;
import model.Team;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.Date;
import java.util.List;

public class Server implements Runnable {
    private static final int DEFAULT_TIMEOUT = 10_000;
    private static final Gson GSON;

    private Repository repository;
    private ServerSocket serverSocket;
    private volatile boolean keepProcessing = true;

    static{
        GsonBuilder builder = new GsonBuilder();
        builder.serializeNulls().setPrettyPrinting(); //?
        GSON = builder.create();
    }

    Server(Repository repository, int port, int timeoutMilliseconds) throws IOException{
        serverSocket = new ServerSocket(port);
        serverSocket.setSoTimeout(timeoutMilliseconds);
        this.repository = repository;
    }

    Server(Repository repository, int port) throws IOException{
        this(repository, port, DEFAULT_TIMEOUT);
    }

    @Override
    public void run(){
        System.out.println("Server started at port: " + serverSocket.getLocalPort());
        while (keepProcessing) {
            try {
                System.out.println("Accepting client");
                Socket socket = serverSocket.accept();
                System.out.println("Got client:" + socket);
                process(socket);
            } catch (Exception e) {
                if (!(e instanceof SocketException)) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void process(Socket socket){
        if (socket == null) {
            return;
        }

        Runnable clientHandler = () -> {
            try {
                System.out.println(new Date() + " - getting message");
                handleCommand(socket);
                closeIgnoringException(socket);
            } catch (IOException e) {
                e.printStackTrace();
            }
        };

        Thread clientConnection = new Thread(clientHandler);
        clientConnection.start();
    }

    private void handleCommand(Socket socket) throws IOException{
        DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());
        DataInputStream inputStream = new DataInputStream(socket.getInputStream());

        String input = inputStream.readUTF();
        String[] parts = input.split("'");
        Commands command = Commands.valueOf(parts[0]);
        System.out.println(String.format(new Date().toString() + " - got message: %s", input));

        switch (command) {
            case GET_TEAMS:
                List<Team> teams = repository.getTeams();
                sendString(outputStream, GSON.toJson(teams));
                break;
            case GET_ONE_TEAM:
                Team team = repository.getTeamById(Integer.valueOf(parts[1]));
                sendString(outputStream, GSON.toJson(team));
                break;
            case ADD_TEAM:
                Team teamToAdd = GSON.fromJson(parts[1], Team.class);
                repository.addTeam(teamToAdd);
                sendString(outputStream, "Done");
                break;
            case UPDATE_TEAM:
                Team teamForUpdating = GSON.fromJson(parts[2], Team.class);
                repository.updateTeam(parts[1], teamForUpdating);
                sendString(outputStream, "Done");
                break;
            case DELETE_TEAM:
                repository.removeTeam(GSON.fromJson(parts[1], Integer.class));
                sendString(outputStream, "Done");
                break;

            case GET_ATHLETES:
                List<Athlet> athletes = repository.getAthletes();
                sendString(outputStream, GSON.toJson(athletes));
                break;
            case GET_ONE_ATHLET:
                Athlet athlet = repository.getAthletById(Integer.valueOf(parts[1]));
                sendString(outputStream, GSON.toJson(athlet));
                break;
            case ADD_ATHLET:
                Athlet athletToAdd = GSON.fromJson(parts[1], Athlet.class);
                repository.addAthlet(athletToAdd);
                sendString(outputStream, "Done");
                break;
            case UPDATE_ATHLET:
                Athlet athletForUpdating = GSON.fromJson(parts[2], Athlet.class);
                repository.updateAthlet(parts[1], athletForUpdating);
                sendString(outputStream, "Done");
                break;
            case DELETE_ATHLET:
                repository.removeAthlet(GSON.fromJson(parts[1], Integer.class));
                sendString(outputStream, "Done");
                break;

            case GET_COMPETITIONS:
                List<Competition> competitions = repository.getCompetition();
                sendString(outputStream, GSON.toJson(competitions));
                break;
            case GET_ONE_COMPETITION:
                Competition competition = repository.getCompetitionById(Integer.valueOf(parts[1]));
                sendString(outputStream, GSON.toJson(competition));
                break;
            case ADD_COMPETITION:
                Competition competitionToAdd = GSON.fromJson(parts[1], Competition.class);
                repository.addCompetition(competitionToAdd);
                sendString(outputStream, "Done");
                break;
            case UPDATE_COMPETITION:
                Competition competitionForUpdating = GSON.fromJson(parts[2], Competition.class);
                repository.updateCompetition(Integer.valueOf(parts[1]), competitionForUpdating);
                sendString(outputStream, "Done");
                break;
            case DELETE_COMPETITION:
                repository.removeCompetition(GSON.fromJson(parts[1], Integer.class));
                sendString(outputStream, "Done");
                break;

            case GET_SEATS:
                List<Seat> seats = repository.getSeat();
                sendString(outputStream, GSON.toJson(seats));
                break;
            case GET_ONE_SEAT:
                Seat seat = repository.getSeatById(Integer.valueOf(parts[1]));
                sendString(outputStream, GSON.toJson(seat));
                break;
        }
    }

    private void sendString(DataOutputStream outputStream, String string) throws IOException{
        outputStream.writeUTF(string);
    }

    //?
    private void closeIgnoringException(Socket socket){
        if (socket != null) {
            try {
                socket.close();
            } catch (IOException ignored) {
            }
        }
    }

    private void stopProcessing(){
        keepProcessing = false;
        closeIgnoringException(serverSocket);
    }

    private void closeIgnoringException(ServerSocket serverSocket){
        if (serverSocket != null) {
            try {
                serverSocket.close();
            } catch (IOException ignored) {

            }
        }
    }
}
