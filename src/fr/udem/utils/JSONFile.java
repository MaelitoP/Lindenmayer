package fr.udem.utils;

import fr.udem.turtle.Turtle;
import fr.udem.lsystem.LSystem;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.awt.geom.Point2D;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Iterator;

public class JSONFile {
    /**
     * Read JSON File to execute LSystem
     * @param file JSON file
     * @param S LSystem object
     * @throws IOException if the file isn't found
     */
    public static void readJSONFile(String file, LSystem S, Turtle T) throws IOException {
        JSONObject input = new JSONObject(new JSONTokener(new java.io.FileReader(file)));

        JSONArray alphabet = input.getJSONArray("alphabet");
        JSONObject actions = input.getJSONObject("actions");
        JSONObject rules = input.getJSONObject("rules");
        String axiom = input.getString("axiom");
        JSONObject parameters = input.getJSONObject("parameters");
        int step = parameters.getInt("step");
        BigDecimal angle = parameters.getBigDecimal("angle");
        JSONArray start = parameters.getJSONArray("start");

        // Register alphabet
        for (int i = 0; i < alphabet.length(); i++) {
            S.addSymbol(alphabet.getString(i).charAt(0));
        }

        // Assigned rule to symbol
        for (Iterator<String> it = rules.keys(); it.hasNext();) {
            String value = it.next();
            JSONArray valueRule = (JSONArray) rules.get(value);
            valueRule.forEach(rule -> { S.addRule(S.getAssociations().get(value.charAt(0)), rule.toString()); });
        }

        // Assigned symbol to a turtle action
        for (Iterator<String> it = actions.keys(); it.hasNext();) {
            String value = it.next();
            String valueAction = (String) actions.get(value);
            S.setAction(S.getAssociations().get(value.charAt(0)), valueAction);
        }
        S.setAxiom(axiom);
        T.init(new Point2D.Double(start.getDouble(0),start.getDouble(1)), start.getDouble(2));
        T.setUnits(step, angle.doubleValue());
    }
}
