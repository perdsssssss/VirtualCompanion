# Virtual Companion - ECHO

A wellness-focused Android application featuring a customizable virtual companion that supports emotional well-being through mood tracking, personalized quests, and interactive features.

---

## 📱 About the App

Virtual Companion is a wellness app designed to help users track their mood, complete wellness quests, and customize their companion's appearance. The app uses a SQLite database to persist user data, mood logs, quest progress, and accessory purchases.

### Key Features
- **Mood Tracking**: Log daily moods using a 1-5 scale (Neutral, Happy, Sad, Angry, Anxious)
- **Emotional Companion**: Virtual pet that responds to your mood with visual expressions
- **Mood-Adaptive Quests**: Complete wellness challenges tailored to your current emotional state
- **Timed Quest Sessions**: Interactive quest timer with mood-specific background music
- **Customization Shop**: Purchase and equip accessories (tops, bottoms, hats, glasses)
- **Coin Economy**: Earn coins by completing quests to purchase accessories
- **Profile Management**: Customize companion name and gender
- **Background Music**: Seamless looping music with toggle control and mood-specific quest tracks

---

## 🗄️ Database Schema

### Table: `user`
Stores user profile and game progress.

| Column | Type | Description |
|--------|------|-------------|
| id | INTEGER PRIMARY KEY | User ID |
| name | TEXT | Companion name |
| pet_gender | TEXT | Companion gender (male/female) |
| coins | INTEGER | User's currency |

### Table: `mood`
Records daily mood entries.

| Column | Type | Description |
|--------|------|-------------|
| id | INTEGER PRIMARY KEY | Entry ID |
| value | INTEGER | Mood rating (1-5) |
| date | TEXT | Entry date |

**Mood Values:**
- 1 = Neutral
- 2 = Happy
- 3 = Sad
- 4 = Angry
- 5 = Anxious

### Table: `quest`
Stores wellness quests and completion status.

| Column | Type | Description |
|--------|------|-------------|
| id | INTEGER PRIMARY KEY | Quest ID |
| title | TEXT | Quest title |
| description | TEXT | Quest details |
| reward | INTEGER | Coin reward (30 or 50 coins) |
| timer_minutes | INTEGER | Timer duration in minutes (1 or 2) |
| progress | INTEGER | Completion progress (0-100) |
| rewarded | INTEGER | Completion status (0=incomplete, 1=complete) |
| mood | TEXT | Mood category (neutral/happy/sad/angry/anxious) |

### Table: `accessory`
Manages clothing and accessory items.

| Column | Type | Description |
|--------|------|-------------|
| id | INTEGER PRIMARY KEY | Accessory ID |
| image | INTEGER | Drawable resource ID |
| price | INTEGER | Cost in coins |
| type | TEXT | Category (top/bottom/hat/glasses) |
| owned | INTEGER | Ownership (0=not owned, 1=owned) |
| equipped | INTEGER | Equipped status (0=not equipped, 1=equipped) |

---

## 🎯 App Flow

### First Launch
1. **OpeningActivity** → Tap to start
2. **CustomizeActivity** → Enter companion name and gender → Save to `user` table
3. **MoodActivity** → Log mood (1-5) → Save to `mood` table
4. **MoodResultActivity** → See companion's emotional response

### Daily Use
1. **OpeningActivity** → Check if mood logged today
    - If not logged → **MoodActivity**
    - If already logged → **MoodResultActivity**
2. Navigate between:
    - **Home** (MoodResultActivity)
    - **Quests** (QuestsActivity)
    - **Customize** (CustomTopActivity, CustomBottomActivity, CustomHatActivity, CustomGlassesActivity)

### Quest Flow
1. User selects a quest from **QuestsActivity**
2. Confirmation dialog shows quest details and timer duration
3. User starts quest → **QuestSessionActivity** launches
4. Mood-specific background music plays during quest
5. Timer counts down (1-2 minutes depending on quest)
6. Quest completes → Progress updates to 100% → Coins awarded
7. **User is prompted to log their mood again** after completing the quest
8. User returns to **MoodActivity** → Logs updated mood
9. Flow continues to **MoodResultActivity** with refreshed quest list

### Customization Flow
1. Navigate to customization category (**CustomTopActivity**, **CustomBottomActivity**, **CustomHatActivity**, or **CustomGlassesActivity**)
2. Browse accessories in horizontal RecyclerView
3. Check if owned:
    - If owned → Equip/Unequip (update `accessory.equipped`)
    - If not owned → Purchase (deduct `user.coins`, set `accessory.owned = 1`)
4. View changes on pet display in real-time

---

## 🎵 Background Music System


**Music Tracks:**
- **Background Music** - Default ambient music for main app navigation
- **Mood-Specific Quest Music:**
    - Happy Quest Music (`quest_happy.mp3`)
    - Sad Quest Music (`quest_sad.mp3`)
    - Angry Quest Music (`quest_angry.mp3`)
    - Anxious Quest Music (`quest_anxious.mp3`)
    - Neutral Quest Music (`quest_neutral.mp3`)

**Features:**
- Seamless looping with no gaps
- Continues playing between activities
- Automatically switches to mood-specific music during quest sessions
- Restores previous track after quest completion
- Toggle on/off in Settings
- Volume set to 50%
- Smart memory management (pauses on low memory, stops on critical memory)

---

## 🚀 Setup Instructions

1. **Clone the repository**
2. **Open in Android Studio**
3. **Add music files:**
    - Place `background_music.mp3` in `app/src/main/res/raw/`
    - Place quest music files in `app/src/main/res/raw/`:
        - `quest_happy.mp3`
        - `quest_sad.mp3`
        - `quest_angry.mp3`
        - `quest_anxious.mp3`
        - `quest_neutral.mp3`
4. **Sync Gradle**
5. **Run on emulator or device**

---

## 📋 Requirements

- **Min SDK:** 24 (Android 7.0)
- **Target SDK:** 34
- **Language:** Java
- **Database:** SQLite
- **Architecture:** Activity-based with SQLite persistence

---

## 🎮 How to Use

1. **First Launch:**
    - Tap anywhere on opening screen
    - Enter your companion's name (up to 8 letters)
    - Select gender (Male/Female)

2. **Daily Mood Logging:**
    - Select your mood from 5 options (Neutral/Happy/Sad/Angry/Anxious)
    - Press Submit
    - View companion's emotional response

3. **Complete Quests:**
    - Navigate to Quests tab
    - View quests filtered by your current mood
    - Tap "Start Quest" to begin
    - Follow quest instructions during the timed session
    - Earn coins upon completion
    - **Log your mood again** after completing a quest to refresh your state

4. **Customize Companion:**
    - Navigate to Customize tab
    - Browse categories (Top/Bottom/Hat/Glasses)
    - Purchase with coins or equip owned items
    - See changes instantly on your companion

5. **Settings:**
    - Access via settings icon
    - Change companion name/gender
    - Toggle background music
    - Save changes

---

## 📝 Notes

- Mood can only be logged once per day (unless completing a quest)
- After completing any quest, you'll be prompted to log your mood again
- All navigation buttons are disabled until initial mood is logged
- Quest rewards range from 30-50 coins depending on difficulty
- Quest timers range from 1-2 minutes
- Background music continues seamlessly between screens
- Quest sessions play mood-specific music
- Progress is saved in SQLite database

---

## 📄 License

This project is for educational purposes.