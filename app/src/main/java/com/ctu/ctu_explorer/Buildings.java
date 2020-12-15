package com.ctu.ctu_explorer;

import android.content.Context;
import android.content.res.TypedArray;

import org.osmdroid.util.GeoPoint;

import java.util.Arrays;
import java.util.List;

public final class Buildings {
    public static GeoPoint[] locations = {
            null,
            new GeoPoint(10.030452, 105.768692),
            new GeoPoint(10.032823, 105.770596),
            new GeoPoint(10.028428, 105.769443),
            new GeoPoint(10.030747, 105.768258),
            new GeoPoint(10.031334, 105.765887),
            new GeoPoint(10.031873, 105.766472),
            new GeoPoint(10.027092, 105.764778),
            new GeoPoint(10.032174, 105.770624),
            new GeoPoint(10.029522, 105.770268),
            new GeoPoint(10.030040, 105.771756),
            new GeoPoint(10.032316, 105.771295),
            new GeoPoint(10.030922, 105.772571),
            new GeoPoint(10.028064, 105.768478),
            new GeoPoint(10.032026, 105.768885),
            new GeoPoint(10.028011, 105.767786),
            new GeoPoint(10.033753, 105.769973),
            new GeoPoint(10.029728, 105.764493)
    };

    private List<String> names;
    private TypedArray pics;
    private List<String> codes;
    private List<String> descriptions;
    private List<String> descriptionsLong;

    public Buildings(Context context) {
        names = Arrays.asList(context.getResources().getStringArray(R.array.buildings_name));

        pics = context.getResources().obtainTypedArray(R.array.imgs);
        codes = Arrays.asList(context.getResources().getStringArray(R.array.buildings_code));
        descriptions = Arrays.asList(context.getResources().getStringArray(R.array.buildings_desc));
        descriptionsLong = Arrays.asList(context.getResources().getStringArray(R.array.buildings_desc_long));
    }

    public List<String> getNames() {
        return names;
    }

    public List<String> getCodes() {
        return codes;
    }

    public String getNameByCode(String code) {
        int index = codes.indexOf(code);
        if (index < 0) return "Unknown";
        return names.get(index);
    }

    public String getDescriptionByCode(String code) {
        int index = codes.indexOf(code);
        if (index < 0) return "Unknown";
        return descriptions.get(index);
    }
    public String getDescriptionLongByCode(String code) {
        int index = codes.indexOf(code);
        if (index < 0) return "Unknown";
        return descriptionsLong.get(index);
    }
    public int getPicsByCode(String code) {
        int index = codes.indexOf(code);
        if (index < 0) return 0;
        return pics.getResourceId(index,0);
    }
}
