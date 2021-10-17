package fr.udem.lsystem;

import fr.udem.turtle.Turtle;

import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

public class LSystem extends AbstractLSystem<Symbol> {
    /**
     * Alphabet
     */
    private Map<Character, Symbol> associations;

    /**
     * Rules associated with each symbol.
     * There can be several rules for the same symbol.
     * A rule is then chosen at random.
     */
    private Map<Symbol, List<Symbol.Seq<Symbol>>> rules;

    /**
     * Axiom of LSystem
     */
    private SymbolSeq<Symbol> axiom;

    private SymbolSeq<Symbol> result;

    private Rectangle2D rec;

    /**
     * Constructor of LSystem
     */
    public LSystem() {
        this.associations = new HashMap<>();
        this.rules = new HashMap<>();
        this.axiom = null;
        this.result = null;
        this.rec = new Rectangle2D.Double();
    }

    /**
     * Add a symbol to the alphabet
     * @param sym character used in the input to denote this symbol
     * @return new {@link Symbol}
     */
    @Override
    public Symbol addSymbol(char sym) {
        Symbol s = new Symbol(Character.toString(sym));
        associations.put(sym, s);
        return s;
    }

    /**
     * Add a new rule to symbol <var>sym</var>
     * @param sym symbol on left-hand side that is rewritten by this rule
     * @param expansion sequence on right-hand side
     */
    @Override
    public void addRule(Symbol sym, String expansion) {
        List<Symbol.Seq<Symbol>> list;
        SymbolSeq<Symbol> seq = new SymbolSeq<>(expansion);

        if(rules.containsKey(sym))
            rules.get(sym).add(seq);
        else {
            list = new ArrayList<>();
            list.add(seq);
            rules.put(sym, list);
        }
    }

    /**
     * Defines the turtle action for the given symbol
     * @param sym a symbol corresponding to a turtle action
     * @param action a turtle action
     */
    @Override
    public void setAction(Symbol sym, String action) {
        sym.setAction(action);
    }

    /**
     * Store the axiom
     * @param str starting sequence
     */
    @Override
    public void setAxiom(String str) {
        axiom = new SymbolSeq<>(str);;
    }

    /**
     * Get stored axiom
     * @return stored {@link Symbol.Seq}
     */
    @Override
    public Symbol.Seq<Symbol> getAxiom() {
        return axiom;
    }

    /**
     * Retrieve the substitution according to a rule randomly
     * chosen among those with sym.
     * @param sym a symbol that would be rewritten.
     * @return {@link Symbol.Seq} associated with <var>sym</var>
     */
    @Override
    public Symbol.Seq<Symbol> rewrite(Symbol sym) {
        List<Symbol.Seq<Symbol>> symRules = rules.get(sym);
        return symRules == null ? null : symRules.get(new Random().nextInt(symRules.size()));
    }

    /**
     * Tell turtle to exec associated instruction with <var>sym</var>.
     * (By specified action with {@link LSystem#setAction(Symbol, String)}).
     * @param turtle used for executing the action
     * @param sym symbol that needs to be executed
     */
    @Override
    public void tell(Turtle turtle, Symbol sym) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method method = turtle.getClass().getDeclaredMethod(associations.get(sym.getSymbol().charAt(0)).getAction());
        method.invoke(turtle);
    }

    /**
     * Calculates the result of multiple rounds of rewriting. Symbols with no rewriting rules are simply copied
     * at each round.
     * @param seq starting sequence
     * @param n number of rounds
     * @return {@link Symbol.Seq} at <var>n</var>th iterations
     */
    @Override
    public Symbol.Seq<Symbol> applyRules(Symbol.Seq<Symbol> seq, int n) {
        if(n == 0) result = (SymbolSeq<Symbol>) seq;
        else {
            applyRules(seq, n - 1);
            SymbolSeq[] save = new SymbolSeq[result.size()];
            for(int itr = 0; itr < result.size(); itr++) {
                Symbol currSym = getAssociations().get(result.get(itr).toString().charAt(0));
                if(rules.get(currSym) != null) {
                    SymbolSeq<Symbol> symbolRule = (SymbolSeq<Symbol>) rewrite(currSym);
                    save[itr] = symbolRule;
                } else save[itr] = new SymbolSeq<>(currSym);
            }

            SymbolSeq<Symbol> newSequence = new SymbolSeq<>();
            for (SymbolSeq symbolSeq : save) newSequence.append(symbolSeq);
            result = newSequence;
        }
        return result;
    }

    @Override
    public Rectangle2D tell(Turtle turtle, Symbol.Seq<Symbol> sym, int rounds) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        if(rounds == 0) {
            tell(turtle, sym.get(0));
            rec.add(turtle.getPos());
        }
        else {
            tell(turtle, sym, rounds - 1);
            rec.add(turtle.getPos());
        }
        return this.rec;
    }

    public Map<Character, Symbol> getAssociations() {
        return this.associations;
    }
}