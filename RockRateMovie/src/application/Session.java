package application;

import java.util.HashMap;

class Session{

    private HashMap<String,Object> store = new HashMap();

    public static final Session INSTANCE = new Session();

    public void put(String key, Object value){
        this.store.put(key,value);
    }

    public Object get(String key) {
        return this.store.get(key);
    }

    public String getUsername(String username) {
        username = (String) Session.INSTANCE.get("user");
        return username;
    }
}

