package com.carteresto.igr230.carteresto.MenuDetail;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.carteresto.igr230.carteresto.R;

public class NoteDialog extends DialogFragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        final View v = inflater.inflate(R.layout.activity_menu_detail_note_dialog, container, false) ;
        return super.onCreateView(inflater, container, savedInstanceState);
    }
}
