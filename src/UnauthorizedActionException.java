public class UnauthorizedActionException extends Exception{
    public UnauthorizedActionException(){
        super("Unauthorized Action or illegal input");
    }
    public UnauthorizedActionException(String msg){
        super(msg);
    }
}
