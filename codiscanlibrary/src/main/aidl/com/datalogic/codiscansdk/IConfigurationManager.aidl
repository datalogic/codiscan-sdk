package com.datalogic.codiscansdk;

import android.os.Bundle;
import com.datalogic.codiscansdk.IGetConfigListener;
import com.datalogic.codiscansdk.ISetConfigListener;

interface IConfigurationManager {
    int set(in Bundle ints, in Bundle strings);

    int get(in int[] ids);

    int registerGetListener(IGetConfigListener listener);

    int removeGetListener(IGetConfigListener listener);

    int registerSetListener(ISetConfigListener listener);

    int removeSetListener(ISetConfigListener listener);
}