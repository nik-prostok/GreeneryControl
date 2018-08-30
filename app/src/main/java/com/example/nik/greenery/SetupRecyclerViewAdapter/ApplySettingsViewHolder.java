package com.example.nik.greenery.SetupRecyclerViewAdapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import com.example.nik.greenery.R;

/**
 * Created by Nik on 01.06.2018.
 */

public class ApplySettingsViewHolder extends RecyclerView.ViewHolder {

    private Button mApplySettings;

    public ApplySettingsViewHolder(View itemView) {
        super(itemView);
        mApplySettings = itemView.findViewById(R.id.apply_settings);
    }

    public Button getmApplySettings() {
        return mApplySettings;
    }

    public void setmApplySettings(Button mApplySettings) {
        this.mApplySettings = mApplySettings;
    }
}
