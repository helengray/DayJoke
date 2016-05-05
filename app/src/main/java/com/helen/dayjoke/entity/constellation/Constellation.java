package com.helen.dayjoke.entity.constellation;

import java.io.Serializable;

/**
 * Created by 李晓伟 on 2016/5/4.
 *
 */
public abstract class Constellation implements Serializable{
    public static final Constellation[] CONSTELLATIONS = new Constellation[]{
            new Aquarius(),
            new Pisces(),
            new Aries(),
            new Taurus(),
            new Gemini(),
            new Cancer(),
            new Leo(),
            new Virgo(),
            new Libra(),
            new Scorpio(),
            new Sagittarius(),
            new Capricorn()
    };

    abstract public String getName();
    abstract public String getDate();
    abstract public int getResId();

    @Override
    public boolean equals(Object o) {
        if(o != null && o instanceof Constellation){
            Constellation c = (Constellation) o;
            return getName().equals(c.getName());
        }
        return false;
    }
}
