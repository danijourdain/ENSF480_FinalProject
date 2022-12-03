public class TicketManager{
    private static TicketManager instance;
    public static TicketManager getInstance(){
        if(instance == null){
            instance = new TicketManager();
        }
        return instance;

    };
}
