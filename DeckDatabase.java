import java.io.File;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import org.json.JSONObject;

public class DeckDatabase {
    public DeckDatabase(){}

    public void write(Deck deck) {
        try (FileWriter file = new FileWriter("json_decks/" + deck.deck_name + ".json")) {
            file.write(get_json(deck).toString());
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Deck> read() {
        ArrayList<Deck> decks = new ArrayList<Deck>();
        File json_decks_dir = new File("/json_decks");

        if (json_decks_dir.exists() && json_decks_dir.isDirectory()) {
            File[] files = json_decks_dir.listFiles();

            if (files!= null) {
                for (File file : files) {
                    if (file.isFile() && file.getName().endsWith(".json")) {
                        try {
                            FileReader reader = new FileReader(file);
                            decks.add(read_json(new JSONObject(reader), file.getName().split(".")[0]));
                        } catch(IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
        return decks;
    }


    private JSONObject get_json(Deck deck) {
        JSONObject json_deck = new JSONObject();
        for (int i = 0; i < deck.size(); i++) {
            json_deck.put(deck.get(i).question, deck.get(i).answer);
        }
        return json_deck;
    }

    private Deck read_json(JSONObject obj, String name) {
        Deck deck = new Deck(name);
        Iterator<String> keys = obj.keys();

        while(keys.hasNext()) {
            String key = keys.next();
            FlashCard card = new FlashCard();
            card.question = key;
            card.answer = obj.get(key).toString();
            deck.add(card);
        }
        return deck;
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
        System.out.println("here");
    }
}
