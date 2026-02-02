package com.example.virtualcompanion;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * DatabaseHelper
 *
 * This class:
 * - Creates the database
 * - Creates all tables
 * - Handles upgrades
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    // Database file name
    private static final String DB_NAME = "virtual_companion.db";

    // Change this if you modify tables later
    private static final int DB_VERSION = 6; // Incremented for mood TEXT migration

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    /**
     * Called automatically when DB is created first time
     */
    @Override
    public void onCreate(SQLiteDatabase db) {

        // ================= USER TABLE =================
        // Stores main player data
        db.execSQL(
                "CREATE TABLE IF NOT EXISTS user (" +

                        // Unique ID (auto-generated)
                        "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        // User / pet name (cannot be empty)
                        "name TEXT NOT NULL, " +
                        // User coins (default 0)
                        "coins INTEGER NOT NULL DEFAULT 0, " +
                        // Pet gender (only allowed values)
                        "pet_gender TEXT NOT NULL CHECK " +
                        "(pet_gender IN ('male','female'))" +
                        ");"
        );

        // ================= ACCESSORY TABLE =================
        // Stores shop & equipped items
        db.execSQL(
                "CREATE TABLE IF NOT EXISTS accessory (" +

                        // Unique ID
                        "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        // Drawable resource ID
                        "image INTEGER NOT NULL, " +
                        // Item price
                        "price INTEGER NOT NULL, " +
                        // Category
                        "type TEXT NOT NULL CHECK " +
                        "(type IN ('top','bottom','hat','glasses')), " +
                        // 0 = not owned, 1 = owned
                        "owned INTEGER NOT NULL DEFAULT 0 CHECK (owned IN (0,1)), " +
                        // 0 = not equipped, 1 = equipped
                        "equipped INTEGER NOT NULL DEFAULT 0 CHECK (equipped IN (0,1))" +
                        ");"
        );

        // ================= QUEST TABLE =================
        // Stores quest progress
        db.execSQL(
                "CREATE TABLE IF NOT EXISTS quest (" +

                        "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        // Quest title
                        "title TEXT NOT NULL, " +
                        // Quest description
                        "description TEXT, " +
                        // Coins reward
                        "reward INTEGER NOT NULL DEFAULT 0, " +
                        "progress INTEGER NOT NULL DEFAULT 0, " +
                        // 0 = not done, 1 = done
                        "rewarded INTEGER NOT NULL DEFAULT 0 CHECK (rewarded IN (0,1)), " +
                        // Mood category (neutral, happy, sad, angry, anxious)
                        "mood TEXT NOT NULL CHECK " +
                        "(mood IN ('neutral','happy','sad','angry','anxious'))" +
                        ");"
        );

        // ================= MOOD TABLE =================
        // Stores mood history
        db.execSQL(
                "CREATE TABLE IF NOT EXISTS mood (" +

                        "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        // Mood value (1 to 5 only)
                        "value INTEGER NOT NULL CHECK (value BETWEEN 1 AND 5), " +
                        // Date string
                        "date TEXT NOT NULL" +
                        ");"
        );

        // Insert default values
        insertDefaults(db);
    }

    /**
     * Insert starting data (runs once)
     */
    private void insertDefaults(SQLiteDatabase db) {

        db.execSQL(
                "INSERT OR IGNORE INTO user (id, name, coins, pet_gender) " +
                        "VALUES (1,'Iggy',150,'male');"
        );

        // 5 quests per mood
        db.execSQL(
                "INSERT OR IGNORE INTO quest (title, description, reward, mood) VALUES " +

                        // Neutral
                        "('Breathing Exercise','Guided deep breathing for stress relief',50,'neutral')," +
                        "('Hydration Reminder','Encouraging water intake for physical wellness',30,'neutral')," +
                        "('Positive Reflection','Write one thing you are grateful for or proud of',60,'neutral')," +
                        "('Journaling Check-In','Write a short journal entry about your day',40,'neutral')," +
                        "('Stretch Break','Do a short stretching routine',35,'neutral')," +

                        // Happy
                        "('Share Gratitude','Tell someone what you appreciate about them',50,'happy')," +
                        "('Compliment Someone','Give a genuine compliment today',40,'happy')," +
                        "('Dance for 5 Minutes','Move to your favorite song',45,'happy')," +
                        "('Smile at a Mirror','Smile at yourself for a minute',30,'happy')," +
                        "('Favorite Song Listening','Listen to your favorite song',35,'happy')," +

                        // Sad
                        "('Talk to a Friend','Reach out to someone you trust',50,'sad')," +
                        "('Guided Meditation','Use a meditation app to relax',40,'sad')," +
                        "('Write Down Feelings','Write your emotions down',45,'sad')," +
                        "('Uplifting Music','Play mood-lifting music',35,'sad')," +
                        "('Nature Walk','Take a short walk outside',40,'sad')," +

                        // Angry
                        "('Deep Breathing','Practice slow breathing',50,'angry')," +
                        "('Physical Exercise','Do light physical activity',45,'angry')," +
                        "('Count to 10','Pause before reacting',30,'angry')," +
                        "('Punching Bag Exercise','Release energy safely',40,'angry')," +
                        "('Cold Water Splash','Splash cold water on your face',35,'angry')," +

                        // Anxious
                        "('Muscle Relaxation','Tense and relax muscles',50,'anxious')," +
                        "('Short Meditation','5–10 minute meditation',45,'anxious')," +
                        "('Journaling Thoughts','Write worries down',40,'anxious')," +
                        "('Warm Drink','Drink something warm',35,'anxious')," +
                        "('Visualization','Visualize a peaceful place',45,'anxious');"
        );
    }

    /**
     * Called when DB version changes
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldV, int newV) {

        // Recreate quest table to migrate mood from INTEGER to TEXT
        if (oldV < 6) {
            db.execSQL("DROP TABLE IF EXISTS quest");
            onCreate(db);
        }
    }
}
