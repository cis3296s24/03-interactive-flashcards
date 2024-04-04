import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import org.json.JSONObject; //Dependency org.json required - https://repo1.maven.org/maven2/org/json/json/20240303/json-20240303.jar
import org.json.JSONArray;  //Dependency org.json required - https://repo1.maven.org/maven2/org/json/json/20240303/json-20240303.jar

/**
 * Creates and manages JSON database (file)
 * Functionality to write to file and read from file
 * Save, update, delete, and clear user Decks of FlashCards.
 */
public class DeckDatabase {
    private static final String PATH = "user_data.json";
    public DeckDatabase(){}

    /**
     * Writes new deck to user_data.json file
     * Reads and updates existing data in file
     * Updates existing deck (overwrites)
     * @param deck
     */
    public void write(Deck deck) {
        JSONArray current_json;
        //Check if file is empty
        if (new File(PATH).length() == 0) {
            current_json = new JSONArray();
        } else {
            current_json = remove_obj(deck.deck_name);
        }
        //Create JSONObject wrapper and append to JSONArray
        JSONObject deck_json = new JSONObject();
        deck_json.put(deck.deck_name, get_json(deck));
        current_json.put(deck_json);
        //Write json string to file
        try (FileWriter file = new FileWriter(PATH)) {
            file.write(current_json.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Read JSONArray from file as ArrayList of type Deck
     * Return empty arraylist if empty user_data file
     * @return ArrayList of type Deck
     */
    public ArrayList<Deck> read() {
        ArrayList<Deck> decks = new ArrayList<>();
        //Check for empty file
        if (new File(PATH).length() == 0) {
            return decks;
        }
        JSONArray arr = read_json_arr();
        return json_to_decks(arr);
    }

    /**
     * Removes the deck from the user_data.json file
     * Deletes deck JSONObj wrapper from JSONArray
     * @param deck
     */
    public void delete(Deck deck) {
        JSONArray current_json;
        //Check if file is empty
        if (new File(PATH).length() == 0) {
            current_json = new JSONArray();
        } else {
            current_json = remove_obj(deck.deck_name);
        }
        //Write json string to file
        try (FileWriter file = new FileWriter(PATH)) {
            file.write(current_json.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Clean all user data from file
     */
    public void clean() {
        //Write empty string to file
        try (FileWriter file = new FileWriter(PATH)) {
            file.write("");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Read content from file and convert to JSONArray
     * @return JSONArray
     */
    private JSONArray read_json_arr() {
        //Read content from file as bytes, pass to String constructor, return arr
        try {
            String content = new String(Files.readAllBytes(Paths.get(PATH)));
            return new JSONArray(content);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Take Deck argument and return JSONObject
     * Key: Deck.question
     * Value: Deck.answer
     * @param deck
     * @return JSONObject
     */
    private JSONObject get_json(Deck deck) {
        JSONObject json_deck = new JSONObject();
        //Iterate through flashcards in deck
        for (int i = 0; i < deck.size(); i++) {
            json_deck.put(deck.get(i).question, deck.get(i).answer);
        }
        return json_deck;
    }

    /**
     * Takes argument JSONArray and
     * converts it to ArrayList of type Deck
     * @param arr
     * @return ArrayList of type Deck
     */
    private ArrayList<Deck> json_to_decks(JSONArray arr) {
        ArrayList<Deck> decks = new ArrayList<>();
        //Iterate through objects in arr
        for (int i = 0; i < arr.length(); i++) {
            //Get json wrapper obj, key (deck.name) creates Deck obj, value is json obj w/ q&a
            JSONObject json_decks = arr.getJSONObject(i);
            JSONObject json_deck = json_decks.getJSONObject(json_decks.keys().next());
            Deck deck = new Deck(json_decks.keys().next());
            //Create iterator of JSONObj keys
            Iterator<String> keys = json_deck.keys();
            //Iterates keys, add question (key) & answer (value) to FlashCard obj
            while(keys.hasNext()) {
                String key = keys.next();
                FlashCard card = new FlashCard();
                card.question = key;
                card.answer = json_deck.get(key).toString();
                deck.add(card);
            }
            //Add deck to ArrayList
            decks.add(deck);
        }
        return decks;
    }

    /**
     * Take key, read jsonarr from file and delete wrapper obj w/ dup key
     * Otherwise return read JSONArray
     * Allows for write() to add updated deck (jsonobj wrapper) to JSONArray
     * @param key
     * @return JSONArray
     */
    private JSONArray remove_obj(String key) {
        JSONArray arr = read_json_arr();
        //Iterate through JSONArray check for duplicate JSONObj wrapper
        for (int i = 0; i < arr.length(); i++) {
            //Remove obj if found
            if (arr.getJSONObject(i).keys().next().equals(key)) {
                arr.remove(i);
            }
        }
        return arr;
    }

    public static void main(String[] args) {
        Deck test1 = new Deck("test1");
        Deck test2 = new Deck("test2");
        FlashCard t1 = new FlashCard();
        t1.question = "aaaa";
        t1.answer = "a1";
        FlashCard t2 = new FlashCard();
        t2.question = "weird";
        t2.answer = "a2";
        test1.add(t1);
        test1.add(t2);
        test2.add(t1);
        test2.add(t2);

        DeckDatabase database = new DeckDatabase();
        database.write(test1);
        database.write(test2);
    }
}
