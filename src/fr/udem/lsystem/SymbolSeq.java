package fr.udem.lsystem;

import java.lang.reflect.Array;
import java.util.*;
import java.util.function.Consumer;

public class SymbolSeq<S> implements Symbol.Seq<Symbol> {
    private List<Symbol> list;

    public SymbolSeq() {
        list = new LinkedList<>();
    }

    public SymbolSeq(String str) {
        list = new LinkedList<>();
        for (int n = 0; n < str.length(); n++) {
            list.add(new Symbol(str.substring(n, n + 1)));
        }
    }

    public SymbolSeq(Symbol sym) {
        list = new LinkedList<>();
        list.add(sym);
    }

    public SymbolSeq(SymbolSeq<Symbol> list) {
        this.list = list.getList();
    }

    /**
     *
     * @param sym
     */
    public void add(Symbol sym) {
        list.add(sym);
    }

    /**
     *
     * @param obj
     * @return
     */
    public boolean equals(Object obj) {
        if(obj instanceof SymbolSeq)
            return list.equals(((SymbolSeq) obj).list);
        return false;
    }

    /**
     *
     * @return
     */
    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        list.forEach((Symbol sym) -> {
                    str.append(sym.toString());
                    str.append(" ");
        });
        return str.toString();
    }

    /**
     *
     * @param seq
     */
    public void append(Symbol.Seq seq) {
        for (Object o : seq) list.add((Symbol) o);
    }

    public List<Symbol> getList() {
        return list;
    }

    /**
     *
     * @param sym
     * @return
     */
    public boolean contains(Symbol sym) {
        return list.contains(sym);
    }

    public void clear() {
        list.clear();
    }

    /**
     *
     * @param index
     * @return
     */
    @Override
    public Symbol get(int index) {
        return list.get(index);
    }

    public void set(Symbol sym, int index) {
        list.set(index, sym);
    }

    public void addAll(List<Symbol> seq, int index) {
        list.remove(index);
        list.addAll(index, seq);
    }

    /**
     *
     * @return
     */
    @Override
    public int size() {
        return list.size();
    }

    /**
     *
     * @return
     */
    @Override
    public Iterator<Symbol> iterator() {
        return list.iterator();
    }
}