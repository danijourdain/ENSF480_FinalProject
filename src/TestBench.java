import java.sql.SQLException;

public class TestBench{
    public static void main(String args[]){
        boolean caught = false;
        try{
        LoginRegisterManager.getInstance().login("carter.marcelo@ucalgary.ca","password");
        }catch(SQLException e){
            e.printStackTrace();
            caught = true;
        }
        if(caught == false){
            System.out.println("Hello there");
        }
    }
    
}
