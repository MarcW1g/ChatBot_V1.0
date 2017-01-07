/*
 * ChatBotJava.java
 *
 * Marc Wiggerman
 *
 * 06/01/2017
 */

import acm.program.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;

public class ChatBotJava extends ConsoleProgram{

    /*
     * The private variables
     */
     private JLabel textLabel;
     private JTextField textField;
     private JButton submitButton;

     private  boolean initialSetup = true;

     public static String userName = "";

     private String DATA_BASE_FILE = "ChatBotKeywords.txt";
     private String SENTANCE_FILE = "ChatBotActions.txt";


     private ChatBotDataBase database;
     private ChatBotPerformAction sentanceAction;

    public void init(){
        createButtons();
        addActionListeners();

        database = new ChatBotDataBase(DATA_BASE_FILE);
        sentanceAction = new ChatBotPerformAction(SENTANCE_FILE);

        startBot();
    }

    /*
     * This method creates the JButtons and the JTextField at the bottom
     * of the view.
     */
    public void createButtons(){
        textLabel = new JLabel("Type here: ");
        textField = new JTextField(30);
        textField.addActionListener(this);
        submitButton = new JButton("Submit");

        add(textLabel, SOUTH);
        add(textField, SOUTH);
        add(submitButton, SOUTH);
    }

    private void startBot(){
        if (initialSetup == true){
            chatbotSays("Welcome what is your name");
        }else{
            chatbotSays("Welcome "+userName+", let me know if I can do anything for you!");
        }
    }

    public void chatbotSays(String text){
        println("[Bot] " + text);
    }

    public void userSays(String text){
        if (userName.equals("")){
            println("[You] "+ text);
        }else{
            println("["+userName+"] "+ text);
        }
    }

    /*
     * This method is called when the buttons get pressed or the enter button is
     * pressed. It firts gets the command, and then it checks which action has
     * to be peformd when a button is pressed. When "Graph" is pressed the
     * program also checks whether the name exists in the database.
     */
    public void actionPerformed(ActionEvent e){
        if (initialSetup == true){ //When just started
            String command = e.getActionCommand();

            if (command.equals("Submit")){
                userName = textField.getText();
                userSays(textField.getText());
            }else if (e.getSource() == textField){
                userName = textField.getText();
                userSays(textField.getText());
            }

            initialSetup = false;
            startBot();

        }else{

            String command = e.getActionCommand();

            if (command.equals("Submit")){
                String line = textField.getText();
                String cat = categoryOfSentance(line);

                userSays(line);
                pause(100);

                if (cat == null){
                    chatbotSays("I don't know what you just said there");
                }else{
                    String chatBotLine = sentanceAction.getSentance(cat);
                    chatbotSays(chatBotLine);
                }

            }else if (e.getSource() == textField){
                String line = textField.getText();
                String cat = categoryOfSentance(line);

                userSays(line);
                pause(100);

                if (cat == null){
                    chatbotSays("I don't know what you just said there");
                }else{
                    String chatBotLine = sentanceAction.getSentance(cat);
                    chatbotSays(chatBotLine);
                }

            }
        }

        textField.setText("");

    }

    private String categoryOfSentance(String line){

        ArrayList<String> categoriesInLine = new ArrayList<String>();

        String[] splittedLine = line.split(" ");

        for (int i = 0; i<splittedLine.length; i++){
            String cat = database.getCategory(splittedLine[i]);

            if (cat != null){
                categoriesInLine.add(cat);
            }
        }

        int arraySize = categoriesInLine.size();

        if (arraySize != 0){
            String lastCategory = categoriesInLine.get(arraySize-1);

            return lastCategory;

        }else{
            return null;
        }


    }
}
