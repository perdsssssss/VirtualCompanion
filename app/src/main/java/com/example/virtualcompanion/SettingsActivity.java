package com.example.virtualcompanion;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.widget.SwitchCompat;
import com.google.android.material.button.MaterialButton;

public class SettingsActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_settings);

        DatabaseManager db = DatabaseManager.get(this);

        // ===============================
        // LOAD PET NAME AND GENDER
        // ===============================

        EditText petNameInput = findViewById(R.id.petNameInput);
        View boyButton = findViewById(R.id.boyButton);
        View girlButton = findViewById(R.id.girlButton);
        MaterialButton saveButton = findViewById(R.id.saveButton);

        // Load saved name
        String savedName = db.getName();
        if (!savedName.isEmpty()) {
            petNameInput.setText(savedName);
            petNameInput.setSelection(savedName.length());
        }

        // Use array to allow modification in lambda
        String[] currentGender = {db.getGender()};

        // Set gender button states based on current gender
        updateGenderButtonStates(boyButton, girlButton, currentGender[0]);

        // Gender button listeners
        boyButton.setOnClickListener(v -> {
            currentGender[0] = "male";
            updateGenderButtonStates(boyButton, girlButton, currentGender[0]);
        });

        girlButton.setOnClickListener(v -> {
            currentGender[0] = "female";
            updateGenderButtonStates(boyButton, girlButton, currentGender[0]);
        });

        // Save button listener
        if (saveButton != null) {
            saveButton.setOnClickListener(v -> {
                String name = petNameInput.getText().toString().trim();
                if (!name.isEmpty()) {
                    db.setName(name);
                    db.setGender(currentGender[0]);
                    finish();
                }
            });
        }

        // ===============================
        // SOUND EFFECTS TOGGLE
        // ===============================

        SwitchCompat soundEffectsToggle = findViewById(R.id.soundEffectsToggle);

        if (soundEffectsToggle != null) {

            // Set initial state
            soundEffectsToggle.setChecked(MusicManager.isMusicEnabled());

            // Handle toggle changes
            soundEffectsToggle.setOnCheckedChangeListener((buttonView, isChecked) -> {

                MusicManager.setMusicEnabled(isChecked);

                if (isChecked) {
                    MusicManager.startMusic(this);
                } else {
                    MusicManager.pauseMusic();


            }
            });
        }

        // ===============================
        // BACK BUTTON
        // ===============================

        ImageView backButton = findViewById(R.id.backButton);

        if (backButton != null) {
            backButton.setOnClickListener(v -> finish());
        }

        // ===============================
        // BOTTOM NAVIGATION
        // ===============================

        ImageView navHome = findViewById(R.id.navHome);
        ImageView navQuests = findViewById(R.id.navQuests);
        ImageView navCustomize = findViewById(R.id.navCustomize);

        if (navHome != null) {
            navHome.setOnClickListener(v -> {
                startActivity(new Intent(
                        SettingsActivity.this,
                        MoodResultActivity.class
                ));
                finish();
            });
        }

        if (navQuests != null) {
            navQuests.setOnClickListener(v -> {
                startActivity(new Intent(
                        SettingsActivity.this,
                        QuestsActivity.class
                ));
                finish();
            });
        }

        if (navCustomize != null) {
            navCustomize.setOnClickListener(v -> {
                startActivity(new Intent(
                        SettingsActivity.this,
                        CustomTopActivity.class
                ));
                finish();
            });
        }
    }

    /**
     * Update gender button visual states based on current selection
     */
    private void updateGenderButtonStates(View boyButton, View girlButton, String gender) {
        if ("male".equalsIgnoreCase(gender)) {
            boyButton.setAlpha(1.0f);
            girlButton.setAlpha(0.5f);
        } else {
            boyButton.setAlpha(0.5f);
            girlButton.setAlpha(1.0f);
        }
    }
}
