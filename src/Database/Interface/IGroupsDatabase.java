package Database.Interface;

import Model.User_related.Groups;
import Model.User_related.Users;

import java.util.List;

public interface IGroupsDatabase extends IDefaultDatabase{
    void editGroupEntry(Groups g);
    Groups getGroupEntry(int id);
    void addMemberToGroup(int id, Users u);
    List<Integer> getUsersFromGroup(int id);
    void addTicketToGroup(int g_id, int ticket_id);
}
