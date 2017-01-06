import java.util.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.*;

public class ChatBotPerformAction{

    HashMap<String, String> actions = new HashMap<String, String>();

    //private Random rgen = Random();
    Random rgen = new Random();
    String wholeLine;

    public ChatBotPerformAction(String filename){

        String currentLine;

        try{
            BufferedReader bf = new BufferedReader(new FileReader(filename));

            while((currentLine = bf.readLine()) != null){
                actions.put(getKeyword(currentLine), getAction(currentLine));
            }

        }catch (IOException ex){
            System.out.println("Error" + ex);
        }

    }

    private String getKeyword(String line){
        int beginOfKeyword = 0;
        int endOfKeyword = line.indexOf(" ");

        return line.substring(beginOfKeyword, endOfKeyword);
    }

    private String getAction(String line){
        int beginOfAction = line.indexOf(" ") + 1;
        int endOfAction = line.length();

        return line.substring(beginOfAction, endOfAction);
    }

    public String getSentance(String category){
        if (category == null){
            return "I don't know what you just said there";
        }else{
            String sentances = actions.get(category);

            ArrayList<String> sent = new ArrayList<String>();
            sent = splitLine(sentances);


            int i = rgen.nextInt(sent.size());

            return getCompleteString(sent.get(i));
        }

    }

    private ArrayList<String> splitLine(String line){

        ArrayList<String> sentances = new ArrayList<String>();
        String leftOfline = line;

        while(!(leftOfline.equals(""))){
            int beginOfSent = 0;
            int endOfSent = leftOfline.indexOf("|");
            int lineLenght = leftOfline.length();

            String line2 = leftOfline.substring(beginOfSent, endOfSent);

            sentances.add(line2);
            leftOfline = leftOfline.substring(endOfSent + 1, lineLenght);
        }

        return sentances;
    }



    private String getCompleteString(String line){

        String currentLine = line;

        while (currentLine.contains("$")){

            System.out.println(currentLine);

            int beginOfLine = 0;
            int beginOfCommand = currentLine.indexOf("$");
            System.out.println(beginOfCommand);
            int lineLength = currentLine.length();
            String endOfLine = currentLine.substring(beginOfCommand+1, lineLength);
            int endOfCommand = endOfLine.indexOf("$");

            String command = currentLine.substring(beginOfCommand, endOfCommand+beginOfCommand+2);

            if (command.equals("$username$")){
                String nameCommand = "$username$";
                String name = ChatBotJava.userName;

                currentLine = currentLine.replace(nameCommand, name);
            }else if (command.equals("$getTime$")){
                String timeCommand = "$getTime$";
                DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
                Date date = new Date();

                currentLine = currentLine.replace(timeCommand, dateFormat.format(date));

            }else{
                currentLine = line;
            }
        }

        return currentLine;


    }


}
