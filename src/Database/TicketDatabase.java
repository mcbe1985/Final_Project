package Database;

import Controller.AbstractController;
import Database.Interface.ITicketDatabase;
import Model.Factories.TicketFactoryProvider;
import Model.User_related.Ticket;

import java.util.ArrayList;
import java.util.List;
import java.util.Observer;
import java.util.Vector;

public class TicketDatabase extends Database implements ITicketDatabase {
    protected final Vector<Ticket> ticketDB;
    private static TicketDatabase single_instance;

    public TicketDatabase() {
        this.ticketDB = new Vector<>();
    }

    public static TicketDatabase getInstance(){
        if (single_instance == null){
            single_instance = new TicketDatabase();
        }
        return single_instance;
    }

    @Override
    public void editTicketEntry(Ticket t) {
        ticketDB.set(t.getId(), t);
    }

    @Override
    public Ticket getTicketEntry(int id) {
        return this.ticketDB.get(id);
    }

    @Override
    public int addTicketEntry(int _group_id, List<Integer> u_ids, List<Double> _amount_per_user, boolean split_evenly, int k) {
        int id = generateAuthenticId(ticketDB);
        if (id != -1){
            System.out.println(_amount_per_user);
            Ticket t = new TicketFactoryProvider().getTicketFactory(id, _group_id, u_ids, _amount_per_user, split_evenly, k);
            this.ticketDB.add(t);
        }
        return id;
    }
}
