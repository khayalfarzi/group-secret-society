package az.iktlab.group.secret_society.DAO;

import az.iktlab.group.secret_society.entity.User;

public interface TicketDAO {
    public void setTicketData(User user, String nickN, String nickS);
}
