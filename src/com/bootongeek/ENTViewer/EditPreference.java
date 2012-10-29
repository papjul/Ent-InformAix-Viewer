package com.bootongeek.ENTViewer;

import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.PreferenceActivity;
import android.text.InputType;

public class EditPreference extends PreferenceActivity{

    @SuppressWarnings("deprecation")
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preference);
        
        EditTextPreference tmptxt = (EditTextPreference)findPreference("pref_sem");
        tmptxt.getEditText().setInputType(InputType.TYPE_CLASS_NUMBER);
    }
}
