package View;


import Controller.MainController;
import Model.User_related.Ticket;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.util.List;

public class MainPage_GUI extends JFrame implements IDefaultPage_GUI{
    private MainController mainController;
    private JPanel panel;
    private JFrame frame;
    private JLabel label;
    private JButton group_button, new_ticket_button, ticket_history_button, logout, add_friend, create_group;
    private JTextField friend_name;


    public MainPage_GUI(){
    }

    @Override
    public void Initialize(){
        mainController = new MainController();

        frame = new JFrame();

        group_button = new JButton("Groups");
        new_ticket_button = new JButton("Add ticket");
        ticket_history_button = new JButton("See all tickets");
        logout = new JButton("Log out");
        friend_name = new JTextField(20);
        add_friend = new JButton("Add friend!");
        create_group = new JButton("Create a group");
        label = new JLabel("You are now logged in");

        label.setBounds(10,10, 300, 25);
        friend_name.setBounds(10, 40, 100, 25);
        add_friend.setBounds(130, 40, 150, 25);
        group_button.setBounds(10, 90, 80, 25);
        new_ticket_button.setBounds(100, 90, 80, 25);
        ticket_history_button.setBounds(190, 90, 80, 25);
        create_group.setBounds(280, 90, 80, 25);
        logout.setBounds(10, 130, 80, 25);

        create_group.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AddGroupPage_GUI addGroupPage_gui = new AddGroupPage_GUI();
                addGroupPage_gui.Initialize();
                frame.setVisible(false);
                frame.dispose();
            }
        });

        add_friend.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean check = mainController.addFriend(friend_name.getText());
                if (check){
                    label.setText("Friend " + friend_name.getText() + " added to friendlist!");
                    friend_name.setText("");
                } else {
                    label.setText("Unable to add friend, user might not exist or name was spelled wrong.");
                }
            }
        });
        new_ticket_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AddTicket_GUI addTicket_gui = new AddTicket_GUI();
                addTicket_gui.Initialize();
            }
        });
        ticket_history_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TicketHistory_GUI ticketHistory_gui = new TicketHistory_GUI();
                ticketHistory_gui.Initialize();
            }
        });
        group_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GroupPage_GUI groupPage_gui = new GroupPage_GUI();
                groupPage_gui.Initialize();
                frame.setVisible(false);
                frame.dispose();
            }
        });
        logout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LoginPage_GUI loginPage_gui = new LoginPage_GUI();
                loginPage_gui.Initialize();
                frame.setVisible(false);
                frame.dispose();
                mainController.logOut();
            }
        });



        panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(600, 600, 200, 600));
        panel.setLayout(null);
        panel.add(ticket_history_button);
        panel.add(new_ticket_button);
        panel.add(group_button);
        panel.add(logout);
        panel.add(label);
        panel.add(add_friend);
        panel.add(friend_name);
        panel.add(create_group);

        frame.add(panel, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Main Page");
        frame.setSize(700, 300);
        frame.setVisible(true);
    }
}
