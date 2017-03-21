package org.nyjsl.cache;

import java.io.Serializable;

/**
 * Created by pc on 2017/3/20.
 */

public interface Cache<T> extends Serializable {

    void create();

    Cache<T> read() ;

    void update();

    void delete();
}
