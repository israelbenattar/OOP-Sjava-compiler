package oop.ex6.checkfile.methods;

import oop.ex6.checkfile.methods.ifandwhile.IfAndWhile;

import java.util.HashMap;

/**
 * this class represent VarHashMapWrapper and extends HashMap
 * he have to constructors because we may use it for methods or for block(if and while).
 */
public class VarHashMapWrapper extends HashMap<String, Object[]> {

    //the hashMap
    private HashMapFacade varLst;


    //-----------------------------     constructors     -----------------------------//


    /**
     * the method constructor
     *
     * @param varLst the variable list of the method
     */
    public VarHashMapWrapper(Methods varLst) {
        this.varLst = varLst;
    }

    /**
     * the ifAndWhile constructor
     *
     * @param blockVar the variable list of the block
     */
    public VarHashMapWrapper(IfAndWhile blockVar) {
        this.varLst = blockVar;
    }


    //-----------------------------     methods     -----------------------------//


    /**
     * this method return boolean value, true if the variable contain the key, false
     * otherwise
     *
     * @param key the key that we want to check
     * @return boolean value, true if the variable contain the key, false otherwise
     */
    @Override
    public boolean containsKey(Object key) {
        return varLst.containsKey((String) key);
    }


    /**
     * this method get key and return the Object[] that he is the value of the key
     *
     * @param key the key that we want is value
     * @return Object[] that he is the value of the key
     */
    @Override
    public Object[] get(Object key) {
        return varLst.get((String) key);
    }


    /**
     * this method get key and value and adding that to the list and return the value: Object[]
     *
     * @param key   the key
     * @param value the value
     * @return the value: Object[]
     */
    @Override
    public Object[] put(String key, Object[] value) {
        varLst.put(key, value);
        return value;
    }
}
