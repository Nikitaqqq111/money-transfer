package org.mycompany.economics.storage;

import org.mycompany.economics.model.IAccount;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class RuntimeAccountStorage implements IStorage<String, IAccount> {

    private final ConcurrentHashMap<String, IAccount> store = new ConcurrentHashMap<>();

    @Override
    public IAccount findById(String id) {
        return store.get(id);
    }

    @Override
    public void store(List<IAccount> accounts) {
        accounts.forEach(account -> store.put(account.getId(), account));
    }

    @Override
    public List<IAccount> getAll() {
        return new ArrayList<>(store.values());
    }
}
