package main;

import main.views.*;

//API TMDB : e19c69ceca18e691746326be70001c3a

public class App {
    
    // public App() {
    //     JFrame frame = new JFrame();

    //     JPanel panel = new JPanel();
    //     panel.setBorder(BorderFactory.createEmptyBorder(300, 300, 100, 300));
    //     panel.setLayout(new GridLayout(0, 1));

    //     frame.add(panel, BorderLayout.CENTER);
    //     frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    //     frame.setTitle("EasyStream");
    //     frame.pack();
    //     frame.setVisible(true);
    // }

    
    public static void main(String[] args) throws Exception {
        //new App();
        try {
            new EasyStreamView();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
