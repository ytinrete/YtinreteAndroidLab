// IAIDLTestInterface.aidl
package com.ytinrete.android.lab;

import com.ytinrete.dto.AIDLTestClientObj;
import com.ytinrete.dto.AIDLTestServerObj;

// Declare any non-default types here with import statements

interface IAIDLTestInterface {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat,
            double aDouble, String aString);

    int clientToServer(String str);

    AIDLTestServerObj testObj(in AIDLTestClientObj obj);
}
