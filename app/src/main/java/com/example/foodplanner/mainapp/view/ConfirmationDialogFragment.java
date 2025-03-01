package com.example.foodplanner.mainapp.view;

import android.os.Bundle;

import androidx.fragment.app.DialogFragment;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.foodplanner.R;



public class ConfirmationDialogFragment extends DialogFragment {

    private static final String ARG_MESSAGE = "message";
    private static final String ARG_BTN_TEXT = "btnText";
    private OnConfirmedListener listener;
    private String message;
    private String btnText;

    public interface OnConfirmedListener {
        void onConfirmed();
    }

    public static ConfirmationDialogFragment newInstance(String message, String BtnText,OnConfirmedListener listener) {
        ConfirmationDialogFragment fragment = new ConfirmationDialogFragment();
        Bundle args = new Bundle();
        args.putString(ARG_MESSAGE, message);
        args.putString(ARG_BTN_TEXT, BtnText);
        fragment.setArguments(args);
        fragment.setListener(listener);
        return fragment;
    }

    public void setListener(OnConfirmedListener listener) {
        this.listener = listener;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_confirmation_dialog, container, false);

        if (getArguments() != null) {
            message = getArguments().getString(ARG_MESSAGE);
            btnText=getArguments().getString(ARG_BTN_TEXT);
        }
        TextView tvMessage = view.findViewById(R.id.tvConfirmationMessage);
        Button btnConfirm = view.findViewById(R.id.btnConfirm);
        ImageView btnClose = view.findViewById(R.id.btnClose);

        tvMessage.setText(message);
        btnConfirm.setText(btnText);

        btnConfirm.setOnClickListener(v -> {
            if (listener != null) {
                listener.onConfirmed();
            }
            dismiss();
        });

        btnClose.setOnClickListener(v -> dismiss());

        return view;
    }
}

