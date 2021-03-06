package com.zk.qpm.manager;

import android.text.TextUtils;

import com.zk.qpm.utils.PrefsParser;

import java.util.List;


public class QPMModeManager {

    private static final String SP_NAME_MODE = "jmgt_mode";
    private static final String KEY_MODE = "mode";

    public static final int MODE_SIMPLE = 1;
    public static final int MODE_CUSTOM = 2;

    private static volatile QPMModeManager instance;

    private QPMModeManager() {
    }

    public static QPMModeManager getInstance() {
        if (instance == null) {
            synchronized (QPMModeManager.class) {
                if (instance == null) {
                    instance = new QPMModeManager();
                }
            }
        }
        return instance;
    }

    public void init() {
        List<PrefsParser.PrefItem> prefs = getPrefItem();
        initMode(prefs, KEY_MODE, MODE_CUSTOM);
    }

    private void initMode(List<PrefsParser.PrefItem> prefs, String key, int defaultValue) {
        for (PrefsParser.PrefItem item : prefs) {
            if (TextUtils.equals(item.key, key)) {
                return;
            }
        }
        PrefsParser.PrefItem item = new PrefsParser.PrefItem(PrefsParser.IPrefTypeDefine.TYPE_INT, key, String.valueOf(defaultValue));
        PrefsParser.writePrefs(QPMManager.getInstance().getContext(), SP_NAME_MODE, item);
        prefs.add(item);
    }

    public boolean isSimpleMode() {
        List<PrefsParser.PrefItem> prefs = getPrefItem();
        try {
            for (PrefsParser.PrefItem item : prefs) {
                if (TextUtils.equals(item.key, KEY_MODE)) {
                    return Integer.parseInt(item.value) == MODE_SIMPLE;
                }
            }
        } catch (Exception e) {
        }
        return false;
    }

    public void writeMode(boolean isSimpleMode) {
        PrefsParser.PrefItem item = new PrefsParser.PrefItem(PrefsParser.IPrefTypeDefine.TYPE_INT,
                KEY_MODE, String.valueOf(isSimpleMode ? MODE_SIMPLE : MODE_CUSTOM));
        PrefsParser.writePrefs(QPMManager.getInstance().getContext(), SP_NAME_MODE, item);
    }

    private List<PrefsParser.PrefItem> getPrefItem() {
        return PrefsParser.getPrefs(QPMManager.getInstance().getContext(), SP_NAME_MODE, PrefsParser.PARSER_SP);
    }
}
