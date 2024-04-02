import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;


public class DeckDatabase {

    public DeckDatabase(){}

    public void write(Deck deck) {
        try (FileWriter file = new FileWriter(deck.deck_name + ".json")) {
            file.write(get_json(deck).toString());
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private JSONArray get_json(Deck deck) {
        JSONArray json_deck = new JSONArray();
        for (int i = 0; i < deck.size(); i++) {
            JSONObject obj = new JSONObject();
            obj.put(deck.get(i).question, deck.get(i).answer);
            json_deck.add(obj);
        }
        return json_deck;
    }


}