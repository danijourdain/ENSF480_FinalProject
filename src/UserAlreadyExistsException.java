public class UserAlreadyExistsException extends Exception{
    public UserAlreadyExistsException(){
        super("A user with this email already exists");
    }
    
}
