package fr.udem.lsystem;

import fr.udem.turtle.Turtle;

import java.awt.geom.Rectangle2D;
import java.lang.reflect.InvocationTargetException;

/**
 * Class illustrating the interface to an L-system.
 *
 * <p><strong>Setting up the L-system. </strong>
 * The implementation builds its data structures
 * by calling {@link #addSymbol}, {@link #addRule}, {@link #setAxiom} and {@link #setAction}.
 *
 * <p><strong>Using the L-system. </strong>
 * The implementation provides access to symbols as instances of {@link Symbol}, and
 * to sequences as {@link Symbol.Seq}. (Note that implementing classes do not
 * need to store an actual backing list or array for the iterator, but could calculate
 * {@link java.util.Iterator#next() } on the fly.)
 */
public abstract class AbstractLSystem<S>
{
    /**
     * Registers a new character in the alphabet. This method is called while parsing
     * the input (specifying the alphabet for the L-system).
     *
     * @param sym character used in the input to denote this symbol
     * @return the corresponding {@link Symbol} in the alphabet
     */
    public abstract Symbol addSymbol(char sym);

    /**
     * Adds a new rule to the grammar. This method is called while parsing the input.
     * Symbols on the right-hand side are encoded by
     * <code>char</code>s in the same way as in {@link #addSymbol(char)}. It is allowed to
     * add the same rule more than once - each one is stored as an alternative.
     *
     * @param sym symbol on left-hand side that is rewritten by this rule
     * @param expansion sequence on right-hand side
     */
    public abstract void addRule(Symbol sym, String expansion);

    /**
     * Associates a turtle action with a symbol. This method is called while parsing the input.
     * The action must correspond to one of the methods in {@link Turtle}: {@link Turtle#draw()}, {@link Turtle#move()},
     * {@link Turtle#turnL() }, {@link Turtle#turnR}, {@link Turtle#stay}, {@link Turtle#pop()}, {@link Turtle#push()}.
     *
     * @param sym a symbol corresponding to a turtle action
     * @param action a turtle action
     */
    public abstract void setAction(Symbol sym, String action);

    /**
     * Defines the starting sequence for the L-system.
     * This method is called when parsing the input.
     *
     * Symbols are encoded by <code>char</code>s as in
     * {@link #addSymbol(char) }.
     *
     * @param str starting sequence
     */
    public abstract void setAxiom(String str);

    /**
     * Starting sequence.
     * @return starting sequence
     */
    public abstract Symbol.Seq<S> getAxiom();
    /**
     * Applies a symbol's rewriting rule.
     * If no rule was previously stored with {@link #addRule}, then it returns null. If a single rule
     * was given, it uses the rule's right-hand side. If multiple rules were given ({@link #addRule} called with the same
     * {@link Symbol} argument more than once), then one of them is chosen randomly.
     *
     * @param sym a symbol that would be rewritten.
     * @return null if no rule, or one of the applicable rules chosen randomly
     */
    public abstract Symbol.Seq<S> rewrite(Symbol sym);
    /**
     * Executes the action corresponding to a symbol (specified by {@link #setAction}) on a given turtle.
     *
     * @param turtle used for executing the action
     * @param sym symbol that needs to be executed
     */
    public abstract void tell(Turtle turtle, Symbol sym) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException;

    /**
     * Calculates the result of multiple rounds of rewriting. Symbols with no rewriting rules are simply copied
     * at each round.
     *
     * @param seq starting sequence
     * @param n number of rounds
     * @return sequence obtained after rewriting the entire sequence <var>n</var> times
     */
    public abstract Symbol.Seq<S> applyRules(Symbol.Seq<Symbol> seq, int n);

    /**
     * Draws the result after multiple rounds of rewriting, starting from a single symbol.
     * Symbols with no rewriting rules are simply copied
     * at each round.
     *
     * @param turtle turtle used for drawing
     * @param seq the starting sequence in round 0: a single symbol
     * @param rounds number of rounds
     * @return bounding box (minimal rectangle covering all visited turtle positions)
     */
    public abstract Rectangle2D tell(Turtle turtle, Symbol.Seq<Symbol> seq, int rounds) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException;
}
