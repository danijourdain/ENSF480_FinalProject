import java.sql.*;
import java.time.*;
import java.util.*;

public class TicketManager extends Manager {
    private static TicketManager instance;

    public static TicketManager getInstance() {
        if (instance == null) {
            instance = new TicketManager();
        }
        return instance;
    }

    public Movie getMovie(String MTitle) throws SQLException{
        Connection connection = Database.getConnection();
        String movie_query = "SELECT * FROM Movie WHERE Movie.MTitle = ?";
        PreparedStatement statement = connection.prepareStatement(movie_query);
        statement.setString(1,MTitle);
        ResultSet result = statement.executeQuery();
        result.next();
        return new Movie(result.getString(1),result.getInt(2),result.getDate(3).toLocalDate());
    }

    public ArrayList<Showtime> getShowtimes() {
        try {
            Connection connection = Database.getConnection();
            String showtime_query = "SELECT * FROM Showtime";
            PreparedStatement statement = connection.prepareStatement(showtime_query);
            ResultSet result = statement.executeQuery();
           
            ArrayList<Showtime> showtimes = new ArrayList<Showtime>();
            while(result.next()){
                java.util.Date in = result.getDate(1);
                LocalDateTime ldt = LocalDateTime.ofInstant(in.toInstant(), ZoneId.systemDefault());
                showtimes.add(new Showtime(ldt, result.getString(2), result.getInt(3), result.getInt(4), result.getInt(5), result.getString(6)));
            }
            return null;
        } catch (SQLException e) {
            return null;
        }

    }

    public ArrayList<Ticket> getShowtimeTickets(Showtime showtime) {
        try {
            Connection connection = Database.getConnection();

            String query = "SELECT * FROM Ticket AS T WHERE T.ShowDateTime = ? AND T.RNumber = ? AND T.MTitle = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setObject(1, showtime.getShowDateTime());
            statement.setInt(2, showtime.getRNumber());
            statement.setString(3, showtime.getMTitle());
            ResultSet result = statement.executeQuery();

            ArrayList<Ticket> tickets = new ArrayList<Ticket>();
            while (result.next()) {
                tickets.add(new Ticket(result.getInt(1),result.getInt(6),showtime,result.getString(7)));
            }
            Collections.sort(tickets, Comparator.comparing(Ticket::getTicketNo));
            return tickets;
        } catch (SQLException e) {
            return null;
        }
    }

    public ArrayList<Ticket> getUserTickets(User u) {
        try {
            Connection connection = Database.getConnection();

            String query = "SELECT * FROM Ticket AS T WHERE T.Email=?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, u.getEmail());
            ResultSet result = statement.executeQuery();

            ArrayList<Ticket> tickets = new ArrayList<Ticket>();
            while (result.next()) {
                java.util.Date in = result.getDate(3);
                LocalDateTime ldt = LocalDateTime.ofInstant(in.toInstant(), ZoneId.systemDefault());
                String mTitle = result.getString(2);
                int rNo = result.getInt(3);
                String tName = result.getString(5);

                String showQuery = "SELECT * FROM Showtime WHERE ShowDateTime=? AND MTitle=? AND RNumber=? AND TName=?";
                PreparedStatement showStatement = connection.prepareStatement(showQuery);
                showStatement.setObject(1, ldt);
                showStatement.setString(2, mTitle);
                showStatement.setInt(3, rNo);
                showStatement.setString(4, tName);
                ResultSet r2 = showStatement.executeQuery();
                r2.next();

                Showtime s = new Showtime(ldt, mTitle, rNo, r2.getInt(4), r2.getInt(5), tName);

                tickets.add(new Ticket(result.getInt(1), result.getInt(6), s, result.getString(7)));
            }

            Collections.sort(tickets, Comparator.comparing(Ticket::getTicketNo));
            return tickets;
        } catch (SQLException e) {
            return null;
        }
    }

    public boolean PurchaseTickets(ArrayList<Ticket> tickets, User u, String cardNo) throws SQLException {
        int total_price = 0;

        for(Ticket t: tickets) {
            total_price += t.getPrice();
            //sum up total price of all tickets
        }

        FinanceManager f = FinanceManager.getInstance();
        boolean purchase_success = false;

        if(u.getType().equals("Registered")) {
            purchase_success = f.doTransaction(total_price, u, u.getCreditCard());
        }
        else {
            purchase_success = f.doTransaction(total_price, u, cardNo);
        }
        //charge the user for the tickets

        if(purchase_success) {
            Connection connection = Database.getConnection();
            
            for(Ticket t: tickets) {
                Showtime s = t.getShowtime();

                String update = "UPDATE Ticket SET Email=? WHERE TNo=? AND MTitle=? AND ShowDateTime=? AND RNumber=? AND TName=?";
                PreparedStatement statement = connection.prepareStatement(update);
                statement.setString(1, u.getEmail());
                statement.setInt(2, t.getPrice());
                statement.setString(3, s.getMTitle());
                statement.setObject(4, s.getShowDateTime());
                statement.setInt(5, s.getRNumber());
                statement.setString(6, s.getTName());

                statement.executeUpdate();
                //update the email for the tickets
            }
        }

        return purchase_success;
    }

    public boolean RefundTicket(Ticket t, User u) throws SQLException {
        Showtime s = t.getShowtime();
        if(s.getShowDateTime().isAfter(LocalDateTime.now().minusDays(3))) {
            //if showtime is more than 72 hours away, refund is allowed
            FinanceManager f = FinanceManager.getInstance();
            f.issueRefund(t.getPrice(), u); 
            Connection connection = Database.getConnection();
            
            String update = "UPDATE Ticket SET Email=NULL WHERE TNo=? AND MTitle=? AND ShowDateTime=? AND RNumber=? AND TName=?";
            PreparedStatement statement = connection.prepareStatement(update);
            statement.setInt(1, t.getPrice());
            statement.setString(2, s.getMTitle());
            statement.setObject(3, s.getShowDateTime());
            statement.setInt(4, s.getRNumber());
            statement.setString(5, s.getTName());

            statement.executeUpdate();
            //update the email for the tickets
            
            return true;
        }
        else {
            return false;
            //if the showtime is less than 72 hours away, refund fails
        }
    }

    public Ticket RetrieveTicket(int ticketNo, LocalDateTime date, String title, int roomNo, String theaterName) throws SQLException {
        /*
        Connection connection = Database.getConnection();

        String query = "SELECT * FROM Ticket WHERE TNo=? AND ShowDateTime=? AND MTitle=? AND RNumber=? AND TName=?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, ticketNo);
        statement.setObject(2, date);
        statement.setString(3, title);
        statement.setInt(4, roomNo);
        statement.setString(5, theaterName);
        ResultSet resultSet = statement.executeQuery();
        // get the ticket from the database

        String email = new String();
        if (resultSet.next() != false) {
            email = resultSet.getString("Email");
        }

        String movieQuery = "SELECT * FROM Movie WHERE Title=?";
        PreparedStatement movieStatement = connection.prepareStatement(movieQuery);
        movieStatement.setString(1, title);
        ResultSet movieResult = movieStatement.executeQuery();
        if (movieResult.next() == false)
            throw new SQLException("Movie doesn't exist!");
        Movie m = new Movie(title, movieResult.getInt("Duration"));
        // get the movie associated from the ticket

        String theaterQuery = "SELECT * FROM TheaterRoom WHERE RNumber=? AND TheatreName=?";
        PreparedStatement theaterStatement = connection.prepareStatement(theaterQuery);
        theaterStatement.setInt(1, roomNo);
        theaterStatement.setString(2, theaterName);
        ResultSet theaterRestult = theaterStatement.executeQuery();
        if (theaterRestult.next() == false)
            throw new SQLException("TheaterRoom doesn't exist!");
        TheatreRoom r = new TheatreRoom(theaterRestult.getInt("Capacity"), roomNo);
        // get the theater room associated with the ticket

        Ticket t = new Ticket(ticketNo, ticketNo, roomNo, date, m, r, email);
        return t;
        */

        return null;
    }
}
