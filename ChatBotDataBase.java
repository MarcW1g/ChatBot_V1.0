import java.util.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ChatBotDataBase{

    HashMap<String, String> categories = new HashMap<String, String>();

    public ChatBotDataBase(String filename){

        String currentLine;

        try{
            BufferedReader bf = new BufferedReader(new FileReader(filename));

            while((currentLine = bf.readLine()) != null){
                categories.put(getKeyword(currentLine), getCategoryHM(currentLine));
            }

        }catch (IOException ex){
            System.out.println("Error" + ex);
        }

    }

    public String getCategory(String keyWord){
        String kwLowerCase = keyWord.toLowerCase();
        String category;
        category = categories.get(kwLowerCase);

        if (category == null){
            return null;
        }else{
            return category;
        }
    }

    private String getKeyword(String line){
        int beginOfKeyword = 0;
        int endOfKeyword = line.indexOf(" ");

        return line.substring(beginOfKeyword, endOfKeyword);
    }

    private String getCategoryHM(String line){
        int beginOfAction = line.indexOf(" ") + 1;
        int endOfAction = line.length();

        return line.substring(beginOfAction, endOfAction);
    }

}
