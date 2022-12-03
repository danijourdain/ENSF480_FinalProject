public class PasswordTooLongException extends Exception{
    public PasswordTooLongException(){
        super("Password must be less than or equal to 32 characters");
    }
}
