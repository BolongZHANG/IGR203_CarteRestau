package com.carteresto.igr230.carteresto.MenuDetail;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.carteresto.igr230.carteresto.R;

public class NoteDialog extends DialogFragment {


    private MenuDetailActivity parent;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        final View v = inflater.inflate(R.layout.activity_menu_detail_note_dialog, container, false);
        parent = (MenuDetailActivity) getActivity();
        Button cancelBtn = (Button) v.findViewById(R.id.menu_detail_note_cancel_btn);
        Button saveBtn = (Button) v.findViewById(R.id.menu_detail_note_save_btn);
        final EditText noteEdit = (EditText) v.findViewById(R.id.menu_detail_note_edit);
        String curNote = parent.getCurNote();
        if (curNote != null) {
            noteEdit.setText(curNote);
        }

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String note = noteEdit.getText().toString();
                if (!note.equals("")) {
                    parent.setNote(note);
                    Toast.makeText(parent, R.string.menu_detail_note_dialog_note_saved, Toast.LENGTH_SHORT).show();
                    dismiss();
                } else {
                    Toast.makeText(parent, R.string.menu_detail_note_dialog_void, Toast.LENGTH_SHORT).show();
                }
            }
        });
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        return v;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        super.onViewCreated(view, savedInstanceState);
    }
}
