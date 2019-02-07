import Connection.Repository;
import Connection.RepositoryMySQL;

import java.io.IOException;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args){
        try {
            int port = 4026;
           Repository repository = new RepositoryMySQL("127.0.0.1", "evtushenko", "evtushenko", "qwertyuio");
           // Repository repository = new RepositoryMySQL("127.0.0.1", "sportcompetitions", "root", "root");
            Server server = new Server(repository, port);
            server.run();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            System.out.println("Shutting down.");
        }
    }
}
