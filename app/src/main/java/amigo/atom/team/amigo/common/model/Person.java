package amigo.atom.team.amigo.common.model;

import com.stfalcon.chatkit.commons.models.IUser;

import java.io.Closeable;
import java.io.IOException;
import java.io.Serializable;

/*
 * Created by troy379 on 04.04.17.
 */
public class Person implements IUser, Serializable, Cloneable {

    private String id;
    private String name;
    private String avatar;
    private boolean online;

    public Person(){}

    public Person(String id, String name, String avatar, boolean online) {
        this.id = id;
        this.name = name;
        this.avatar = avatar;
        this.online = online;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getAvatar() {
        return avatar;
    }

    public boolean isOnline() {
        return online;
    }
}
