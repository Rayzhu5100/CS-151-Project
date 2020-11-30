package application;

import com.mysql.jdbc.Blob;
import javafx.scene.image.Image;

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

    public String getUsername() {
        return (String) get("user");
    }

    public String getMovieName(){
        return (String) get("movieName");
    }

    public Blob getImageBlob(){
        return (Blob) get("blob");
    }

    public float getScore(){
        return (float) get("score");
    }

    public int getVoters(){
        return (int) get("voters");
    }

    public String getRank(){
        return (String)get("rank");
    }

    public String getName(){
        return (String)get("name");
    }

}

