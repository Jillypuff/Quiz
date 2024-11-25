package GUI.panels;

import Modules.Category;
import Modules.QuestionInPanel;
import GUI.panels.GameBoardPanel;
import client.Client;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameBoardPanel extends JPanel implements ActionListener {                           //DETTA ÄR EN TESTKLASS, KOMMER NÄSTAN GARANTERAT BEHÖVA SKRIVAS OM AV NÅN SOM KAN SWING

    int[][] responeList;
    JPanel gameBoardPannelPlayer1;
    JPanel gameBoardPannelPlayer2;



    public GameBoardPanel(){

//        JFrame frame = new JFrame("GameBoard");
//
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.setSize(800, 800);
//        frame.setLocationRelativeTo(null);

        gameBoardPannelPlayer1 = new JPanel();
        gameBoardPannelPlayer2 = new JPanel();

        //JLabel category = new JLabel();
        
        

//        JButton[][] game_board_dispaly_button= new JButton[gameBoard.length][gameBoard[0].length];
//        gameBoardPannelPlayer1.setLayout(new GridLayout(gameBoard.length, gameBoard[0].length));
//        gameBoardPannelPlayer1.setBackground(Color.WHITE);
//        gameBoardPannelPlayer2.setLayout(new GridLayout(gameBoard.length, gameBoard[0].length));
//        gameBoardPannelPlayer2.setBackground(Color.WHITE);
//
//
//
//
//
//
//        for (int i = 0; i < gameBoard.length; i++) {
//
//            for (int j = 0; j < gameBoard[0].length; j++) {
//
//                game_board_dispaly_button[i][j] = new JButton();
//
//                gameBoardPannelPlayer1.add(game_board_dispaly_button[i][j]);
//
//                if(gameBoard[i][j].getReceivedAnswerPlayer1() != 0){
//                    if (gameBoard[i][j].isCorrectPlayer1()) {
//                        game_board_dispaly_button[i][j].setBackground(new Color(102, 225, 51));
//                    }
//                    else{
//                        game_board_dispaly_button[i][j].setBackground(new Color(255, 0, 0));
//                    }
//                } else if (gameBoard[i][j].getReceivedAnswerPlayer1()==0 && gameBoard[i][j].getValdkategori()!=null) {
//                    game_board_dispaly_button[i][j].setText(j+"");
//                    game_board_dispaly_button[i][j].setForeground(game_board_dispaly_button[i][j].getBackground());
//
//                    game_board_dispaly_button[i][j].setBackground(new Color(153, 204, 255));
//                    game_board_dispaly_button[i][j].addActionListener(this);
//                } else if (gameBoard[i][j].getValdkategori()==null) {
//                    game_board_dispaly_button[i][j].setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
//                    game_board_dispaly_button[i][j].setBackground(new Color(128, 128, 128));
//                }
//
//
//
//
//                game_board_dispaly_button[i][j].setFocusable(false);
//                //game_board_dispaly_button[i][j].addActionListener(this);
//
//            }
//        }
//
//        for (int i = 0; i < gameBoard.length; i++) {
//
//            for (int j = 0; j < gameBoard[0].length; j++) {
//
//                game_board_dispaly_button[i][j] = new JButton();
//
//                gameBoardPannelPlayer2.add(game_board_dispaly_button[i][j]);
//
//                if(gameBoard[i][j].getReceivedAnswerPlayer2() != 0){
//                    if (gameBoard[i][j].isCorrectPlayer2()) {
//                        game_board_dispaly_button[i][j].setBackground(new Color(102, 225, 51));
//                    }
//                    else{
//                        game_board_dispaly_button[i][j].setBackground(new Color(255, 0, 0));
//                    }
//                } else if (gameBoard[i][j].getReceivedAnswerPlayer2()==0 && gameBoard[i][j].getValdkategori()!=null) {
//                    game_board_dispaly_button[i][j].setBackground(new Color(153, 204, 255));
//                } else if (gameBoard[i][j].getValdkategori()==null) {
//                    game_board_dispaly_button[i][j].setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
//                    game_board_dispaly_button[i][j].setBackground(new Color(128, 128, 128));
//                }
//
//
//
//
//                game_board_dispaly_button[i][j].setFocusable(false);
//                //game_board_dispaly_button[i][j].addActionListener(this);
//
//            }
//        }



        add(gameBoardPannelPlayer1,BorderLayout.WEST);
        add(gameBoardPannelPlayer2,BorderLayout.EAST);

        gameBoardPannelPlayer1.revalidate();
        gameBoardPannelPlayer1.repaint();
        gameBoardPannelPlayer2.revalidate();
        gameBoardPannelPlayer2.repaint();
        revalidate();
        repaint();
        setVisible(true);
    }
    
    public void uppdateGameBoardPannel(QuestionInPanel[][] gameBoard){
        
        JButton[][] game_board_dispaly_button= new JButton[gameBoard.length][gameBoard[0].length];
        gameBoardPannelPlayer1.setLayout(new GridLayout(gameBoard.length, gameBoard[0].length));
        gameBoardPannelPlayer1.setBackground(Color.WHITE);
        gameBoardPannelPlayer2.setLayout(new GridLayout(gameBoard.length, gameBoard[0].length));
        gameBoardPannelPlayer2.setBackground(Color.WHITE);






        for (int i = 0; i < gameBoard.length; i++) {

            for (int j = 0; j < gameBoard[0].length; j++) {

                game_board_dispaly_button[i][j] = new JButton();

                this.gameBoardPannelPlayer1.add(game_board_dispaly_button[i][j]);

                if(gameBoard[i][j].getReceivedAnswerPlayer1() != null){
                    if (gameBoard[i][j].isCorrectPlayer1()) {
                        game_board_dispaly_button[i][j].setBackground(new Color(102, 225, 51));
                    }
                    else{
                        game_board_dispaly_button[i][j].setBackground(new Color(255, 0, 0));
                    }
                } else if (gameBoard[i][j].getReceivedAnswerPlayer1()==null && gameBoard[i][j].getValdkategori()!=null) {
                    game_board_dispaly_button[i][j].setText(j+"");
                    game_board_dispaly_button[i][j].setForeground(game_board_dispaly_button[i][j].getBackground());

                    game_board_dispaly_button[i][j].setBackground(new Color(153, 204, 255));
                    game_board_dispaly_button[i][j].addActionListener(this);
                } else if (gameBoard[i][j].getValdkategori()==null) {
                    game_board_dispaly_button[i][j].setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
                    game_board_dispaly_button[i][j].setBackground(new Color(128, 128, 128));
                }




                game_board_dispaly_button[i][j].setFocusable(false);
                //game_board_dispaly_button[i][j].addActionListener(this);

            }
        }

        for (int i = 0; i < gameBoard.length; i++) {

            for (int j = 0; j < gameBoard[0].length; j++) {

                game_board_dispaly_button[i][j] = new JButton();

                this.gameBoardPannelPlayer2.add(game_board_dispaly_button[i][j]);

                if(gameBoard[i][j].getReceivedAnswerPlayer2() != null){
                    if (gameBoard[i][j].isCorrectPlayer2()) {
                        game_board_dispaly_button[i][j].setBackground(new Color(102, 225, 51));
                    }
                    else{
                        game_board_dispaly_button[i][j].setBackground(new Color(255, 0, 0));
                    }
                } else if (gameBoard[i][j].getReceivedAnswerPlayer2()==null && gameBoard[i][j].getValdkategori()!=null) {
                    game_board_dispaly_button[i][j].setBackground(new Color(153, 204, 255));
                } else if (gameBoard[i][j].getValdkategori()==null) {
                    game_board_dispaly_button[i][j].setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
                    game_board_dispaly_button[i][j].setBackground(new Color(128, 128, 128));
                }




                game_board_dispaly_button[i][j].setFocusable(false);
                //game_board_dispaly_button[i][j].addActionListener(this);

            }
        }

        add(gameBoardPannelPlayer1,BorderLayout.WEST);
        add(gameBoardPannelPlayer2,BorderLayout.EAST);

        gameBoardPannelPlayer1.revalidate();
        gameBoardPannelPlayer1.repaint();
        gameBoardPannelPlayer2.revalidate();
        gameBoardPannelPlayer2.repaint();
        revalidate();
        repaint();
        setVisible(true);
        
        
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton button = (JButton) e.getSource();
        if(button.getText().equals("1")){
            
        }
    }



    /*
    public static void main(String[] args) {


        QuestionInPanel[][] testBoard = new QuestionInPanel[4][3];

        for (int i = 0; i < testBoard.length; i++) {
            for (int j = 0; j < testBoard[0].length; j++) {
                testBoard[i][j] = new QuestionInPanel();
            }
        }


        testBoard[0][0].setCorrectPlayer1(true);
        testBoard[0][1].setCorrectPlayer1(false);
        testBoard[0][2].setCorrectPlayer1(true);
        testBoard[0][0].setCorrectPlayer2(false);
        testBoard[0][1].setCorrectPlayer2(true);
        testBoard[0][2].setCorrectPlayer2(true);

        testBoard[0][0].setRecivedAwnser(1);
        testBoard[0][1].setRecivedAwnser(1);
        testBoard[0][2].setRecivedAwnser(1);
        testBoard[0][0].setRecivedAwnser(1);
        testBoard[0][1].setRecivedAwnser(1);
        testBoard[0][2].setRecivedAwnser(1);

        testBoard[0][0].setValdkategori(Category.MUSIC);
        testBoard[0][1].setValdkategori(Category.MUSIC);
        testBoard[0][2].setValdkategori(Category.MUSIC);
        testBoard[1][0].setValdkategori(Category.HISTORY);
        testBoard[1][1].setValdkategori(Category.HISTORY);
        testBoard[1][2].setValdkategori(Category.HISTORY);



        GameBoardPanel gameBoardPanel = new GameBoardPanel(testBoard);
    }

     */
}
