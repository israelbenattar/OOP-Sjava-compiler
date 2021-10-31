package oop.ex6.checkfile.methods;

/**
 * this class represent HashMapFacade that inherit to the Methods and to the IfAndWhile
 */
public interface HashMapFacade {

    boolean containsKey(String key);

    Object[] get(String key);

    Object[] put(String key, Object[] value);

}
