/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wisenet.protocols.insens.utils;

import java.util.Hashtable;
import java.util.Map;

/**
 * Key Store for global access 
 * @author CIAdmin
 */
public class NetworkKeyStore extends Hashtable<Short, byte[]> {

    protected static NetworkKeyStore instance;

    public static NetworkKeyStore getInstance() {
        if (instance == null) {
            instance = new NetworkKeyStore();
        }
        return instance;
    }

    public NetworkKeyStore(Map<? extends Short, ? extends byte[]> t) {
        super(t);
    }

    public NetworkKeyStore() {
        super();
    }

    public NetworkKeyStore(int initialCapacity) {
        super(initialCapacity);
    }

    public NetworkKeyStore(int initialCapacity, float loadFactor) {
        super(initialCapacity, loadFactor);
    }

    /**
     * Get the registred key for node identified by id
     * @param id
     * @return
     * @throws NetworkKeyStoreException
     */
    public byte[] getNodeKey(short id) throws NetworkKeyStoreException {
        byte[] key = get(id);
        if (key == null) {
            throw new NetworkKeyStoreException("Cannot be found a key for this id: " + id);
        }
        return key;
    }

    /**
     *  Register a key for a node identified by id
     * @param id
     * @param key
     */
    public void registerKey(short id, byte[] key) {
        put(id, key);
    }
}
