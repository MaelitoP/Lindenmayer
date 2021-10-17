package fr.udem.lsystem;

import fr.udem.turtle.Turtle;

import java.util.Iterator;

public class Symbol {
    private String c;
    private String action;

    public Symbol(String c) {
        this.c = c;
        this.action = null;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getAction() {
        return this.action;
    }

    public String getSymbol() {
        return this.c;
    }

    @Override
    public String toString() {
        return this.c;
    }

    public interface Seq<S> extends Iterable<Symbol> {
        Symbol get(int index);
        void append(Symbol.Seq seq);
        int size();
        Iterator<Symbol> iterator();
    }
}
