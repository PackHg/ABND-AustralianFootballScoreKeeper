package com.oz_heng.apps.android.australianfootballscorekeeper;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by Pack Heng on 9/08/17
 * pack@oz-heng.com
 */

public class ScoringRulesDialog extends DialogFragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.dialog_scoring_rules, container, false);

        Button ok = (Button) rootView.findViewById(R.id.button_ok);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Dismiss this DialogFragment
                dismiss();
            }
        });

        return rootView;
    }
}
