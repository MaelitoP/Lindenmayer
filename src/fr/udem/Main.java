package fr.udem;

import fr.udem.lsystem.LSystem;
import fr.udem.lsystem.Symbol;
import fr.udem.turtle.BasicTurtle;
import fr.udem.utils.JSONFile;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

public class Main {
    public static void main(String[] args) {
        String inputFileName;
        int itr;

        if (args.length > 0) {
            try {
                // Setup input variable
                inputFileName = args[0];
                itr = Integer.parseInt(args[1]);

                LSystem lsystem = new LSystem();
                BasicTurtle turtle = new BasicTurtle("output" + itr + ".eps");
                JSONFile.readJSONFile("./" + inputFileName, lsystem, turtle);

                // Run Lindenmayer program
                Symbol.Seq<Symbol> sequence = lsystem.applyRules(lsystem.getAxiom(), itr);
                System.out.println(sequence.toString());
                for(Symbol sym : sequence) {
                    lsystem.tell(turtle, sym);
                }
                turtle.writeFile();
            } catch (NumberFormatException | IOException | NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
                System.exit(1);
            }
        }

    }
}
