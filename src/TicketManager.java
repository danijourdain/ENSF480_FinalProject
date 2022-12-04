import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class TicketManager extends Manager {
    private static TicketManager instance;

    public static TicketManager getInstance(){
        if(instance == null){
            instance = new TicketManager();
        }
        return instance;
    }

    public Ticket PurchaseTicket(int ticketNo, String email, LocalDateTime date, String title, int roomNo, String theaterName) throws SQLException{
        Connection connection = Database.getConnection();
        
        String query = "SELECT * FROM Ticket WHERE TNo=? AND ShowDateTime=? AND MTitle=? AND RNumber=? AND TName=?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(0, ticketNo);
        statement.setObject(1, date);
        statement.setString(2, title);
        statement.setInt(3, roomNo);
        statement.setString(4, theaterName);
        ResultSet resultSet = statement.executeQuery();
        //get the ticket from the database

        if(resultSet.next() == false) {
            throw new SQLException("Ticket does not exist");
            //if the ticket does not exist, throw an exception
        }

        String ticketEmail = resultSet.getString("Email");
        if(ticketEmail == null) {
            //if the ticketEmail does not exist, the ticket can be purchased 
            String updateQuery = "UPDATE Ticket SET Email=? WHERE TNo=? AND ShowDateTime=? AND MTitle=? AND RNumber=? AND TName=?";
            PreparedStatement updateStatement = connection.prepareStatement(updateQuery);
            updateStatement.setString(0, email);
            updateStatement.setInt(1, ticketNo);
            updateStatement.setObject(2, date);
            updateStatement.setString(3, title);
            updateStatement.setInt(4, roomNo);
            updateStatement.setString(5, theaterName);
            updateStatement.executeUpdate();
            //update the email of the ticket to indicate the ticket has been purchased

            String movieQuery = "SELECT * FROM Movie WHERE Title=?";
            PreparedStatement movieStatement = connection.prepareStatement(movieQuery);
            movieStatement.setString(0, title);
            ResultSet movieResult = movieStatement.executeQuery();
            if(movieResult.next() == false) throw new SQLException("Movie doesn't exist!");
            Movie m = new Movie(title, movieResult.getInt("Duration"));
            //get the movie associated from the ticket

            String theaterQuery = "SELECT * FROM TheaterRoom WHERE RNumber=? AND TheatreName=?";
            PreparedStatement theaterStatement = connection.prepareStatement(theaterQuery);
            theaterStatement.setInt(0, roomNo);
            theaterStatement.setString(1, theaterName);
            ResultSet theaterRestult = theaterStatement.executeQuery();
            if(theaterRestult.next() == false) throw new SQLException("TheaterRoom doesn't exist!");
            TheatreRoom r = new TheatreRoom(theaterRestult.getInt("Capacity"), roomNo);
            //get the theater room associated with the ticket

            Ticket t = new Ticket(ticketNo, ticketNo, roomNo, date, m, r);
            FinanceManager f = FinanceManager.getInstance();
            f.purchaseTicket(t, null); //do i need to create the user here??

            return t;
        }
        else {
            throw new SQLException("Ticket is already purchased!");
        }
    }

    public Ticket RefundTicket(int ticketNo, String email, LocalDateTime date, String title, int roomNo, String theaterName) throws SQLException{
        Connection connection = Database.getConnection();
        
        String query = "SELECT * FROM Ticket WHERE TNo=? AND ShowDateTime=? AND MTitle=? AND RNumber=? AND TName=?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(0, ticketNo);
        statement.setObject(1, date);
        statement.setString(2, title);
        statement.setInt(3, roomNo);
        statement.setString(4, theaterName);
        ResultSet resultSet = statement.executeQuery();
        //get the ticket from the database

        if(resultSet.next() == false) {
            throw new SQLException("Ticket does not exist");
            //if the ticket does not exist, throw an exception
        }

        String ticketEmail = resultSet.getString("Email");
        if(ticketEmail != null) {
            //if the ticketEmail exists, the ticket can be refunded 
            String updateQuery = "UPDATE Ticket SET Email=NULL WHERE TNo=? AND ShowDateTime=? AND MTitle=? AND RNumber=? AND TName=?";
            PreparedStatement updateStatement = connection.prepareStatement(updateQuery);
            updateStatement.setInt(0, ticketNo);
            updateStatement.setObject(1, date);
            updateStatement.setString(2, title);
            updateStatement.setInt(3, roomNo);
            updateStatement.setString(4, theaterName);
            updateStatement.executeUpdate();
            //update the email of the ticket to indicate the ticket has been refunded

            String movieQuery = "SELECT * FROM Movie WHERE Title=?";
            PreparedStatement movieStatement = connection.prepareStatement(movieQuery);
            movieStatement.setString(0, title);
            ResultSet movieResult = movieStatement.executeQuery();
            if(movieResult.next() == false) throw new SQLException("Movie doesn't exist!");
            Movie m = new Movie(title, movieResult.getInt("Duration"));
            //get the movie associated from the ticket

            String theaterQuery = "SELECT * FROM TheaterRoom WHERE RNumber=? AND TheatreName=?";
            PreparedStatement theaterStatement = connection.prepareStatement(theaterQuery);
            theaterStatement.setInt(0, roomNo);
            theaterStatement.setString(1, theaterName);
            ResultSet theaterRestult = theaterStatement.executeQuery();
            if(theaterRestult.next() == false) throw new SQLException("TheaterRoom doesn't exist!");
            TheatreRoom r = new TheatreRoom(theaterRestult.getInt("Capacity"), roomNo);
            //get the theater room associated with the ticket

            Ticket t = new Ticket(ticketNo, ticketNo, roomNo, date, m, r);
            FinanceManager f = FinanceManager.getInstance();
            f.issueRefund(t, null, 0); //do i need to create the user here??
            //also how do i find the admin fee??

            return t;
        }
        else {
            throw new SQLException("Ticket hasn't been purchased");
        }
    }
}
