import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import org.json.JSONObject;
import org.json.JSONArray;

public class DeckDatabase {
    private static final String PATH = "user_data.json";
    public DeckDatabase(){}

    public void write(Deck deck) {
        JSONArray current_json;
        if (new File(PATH).length() == 0) {
            current_json = new JSONArray();
        }
        else {
            current_json = read_json_arr();
        }
        JSONObject deck_json = new JSONObject();
        deck_json.put(deck.deck_name, get_json(deck));
        current_json.put(deck_json);

        try (FileWriter file = new FileWriter(PATH)) {
            file.write(current_json.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Deck> read() {
        ArrayList<Deck> decks = new ArrayList<>();
        if (new File(PATH).length() == 0) {
            return decks;
        }
        JSONArray arr = read_json_arr();
        return json_to_decks(arr);
    }

    private JSONArray read_json_arr() {
        try {
            String content = new String(Files.readAllBytes(Paths.get(PATH)));
            return new JSONArray(content);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private JSONObject get_json(Deck deck) {
        JSONObject json_deck = new JSONObject();
        for (int i = 0; i < deck.size(); i++) {
            json_deck.put(deck.get(i).question, deck.get(i).answer);
        }
        return json_deck;
    }

    private ArrayList<Deck> json_to_decks(JSONArray arr) {
        ArrayList<Deck> decks = new ArrayList<>();
        for (int i = 0; i < arr.length(); i++) {
            JSONObject json_decks = arr.getJSONObject(i);
            JSONObject json_deck = json_decks.getJSONObject(json_decks.keys().next());
            Deck deck = new Deck(json_decks.keys().next());
            Iterator<String> keys = json_deck.keys();
            while(keys.hasNext()) {
                String key = keys.next();
                FlashCard card = new FlashCard();
                card.question = key;
                card.answer = json_deck.get(key).toString();
                deck.add(card);
            }
            decks.add(deck);
        }
        return decks;
    }

    public static void main(String[] args) {
        Deck test1 = new Deck("test1");
        Deck test2 = new Deck("test2");
        FlashCard t1 = new FlashCard();
        t1.question = "q1";
        t1.answer = "a1";
        FlashCard t2 = new FlashCard();
        t2.question = "q2";
        t2.answer = "a2";
        test1.add(t1);
        test1.add(t2);
        test2.add(t1);
        test2.add(t2);

        DeckDatabase database = new DeckDatabase();
        database.write(test1);

        System.out.println(database.read().get(0).deck_name);
    }
}
