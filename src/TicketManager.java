public class TicketManager extends Manager {
    private static TicketManager instance;
    public static TicketManager getInstance(){
        if(instance == null){
            instance = new TicketManager();
        }
        return instance;
    };
}
