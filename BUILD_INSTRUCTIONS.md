# Build Instructions - Enhanced Maternity & Baby Shopping Tracker

## 🚀 Quick Start

### Prerequisites
- Android Studio Arctic Fox (2020.3.1) or later
- JDK 8 or later
- Android SDK with API level 24+ (Android 7.0)
- Minimum 4GB RAM recommended

### Step-by-Step Build Process

1. **Open Android Studio**
   - Launch Android Studio
   - Select "Open an existing project"
   - Navigate to the project directory and select it

2. **Gradle Sync**
   - Android Studio will automatically start Gradle sync
   - Wait for sync to complete (may take 2-5 minutes)
   - If sync fails, check the troubleshooting section below

3. **Build the Project**
   ```bash
   # For debug build
   ./gradlew assembleDebug
   
   # For release build
   ./gradlew assembleRelease
   ```

4. **Run on Device/Emulator**
   - Connect an Android device or start an emulator
   - Click the "Run" button (green triangle) in Android Studio
   - Select your target device

## 📱 APK Installation

### Debug APK
The debug APK will be generated at:
```
app/build/outputs/apk/debug/app-debug.apk
```

### Release APK
The release APK will be generated at:
```
app/build/outputs/apk/release/app-release.apk
```

### Install via ADB
```bash
adb install app/build/outputs/apk/debug/app-debug.apk
```

## 🔧 Project Configuration

### Key Files
- `app/build.gradle` - App-level dependencies and configuration
- `build.gradle` - Project-level configuration
- `settings.gradle` - Project settings
- `gradle.properties` - Gradle properties

### Important Dependencies
```kotlin
// Core Android
implementation 'androidx.appcompat:appcompat:1.6.1'  // CRITICAL for theme support
implementation 'androidx.core:core-ktx:1.12.0'

// Jetpack Compose
implementation platform('androidx.compose:compose-bom:2023.10.01')
implementation 'androidx.compose.ui:ui'
implementation 'androidx.compose.material3:material3'
implementation 'androidx.activity:activity-compose:1.8.2'

// Navigation
implementation 'androidx.navigation:navigation-compose:2.7.5'

// Database
implementation 'androidx.room:room-runtime:2.6.1'
implementation 'androidx.room:room-ktx:2.6.1'
ksp 'androidx.room:room-compiler:2.6.1'

// ViewModel
implementation 'androidx.lifecycle:lifecycle-viewmodel-compose:2.7.0'
```

## 🛠️ Troubleshooting

### Common Build Issues

#### 1. Gradle Sync Failed
**Problem**: Gradle sync fails with dependency resolution errors
**Solution**:
```bash
# Clean and rebuild
./gradlew clean
./gradlew build
```

#### 2. Theme Resolution Errors
**Problem**: `Theme.AppCompat` not found
**Solution**: Ensure AppCompat dependency is included:
```kotlin
implementation 'androidx.appcompat:appcompat:1.6.1'
```

#### 3. Room Compilation Errors
**Problem**: Room database compilation fails
**Solution**: 
- Verify KSP plugin is applied
- Check entity annotations
- Ensure migration is properly implemented

#### 4. Compose Version Conflicts
**Problem**: Compose BOM version conflicts
**Solution**: Use the BOM to manage versions:
```kotlin
implementation platform('androidx.compose:compose-bom:2023.10.01')
```

### Build Environment Issues

#### Java Version
Ensure you're using JDK 8 or later:
```bash
java -version
```

#### Android SDK
Verify SDK installation:
- Open SDK Manager in Android Studio
- Ensure API 24+ is installed
- Install latest build tools

#### Memory Issues
If build fails due to memory:
```kotlin
// In gradle.properties
org.gradle.jvmargs=-Xmx2048m -Dfile.encoding=UTF-8
```

## 📊 Database Migration

### Migration from V1 to V2
The app includes automatic migration:
```kotlin
val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL("ALTER TABLE shopping_items ADD COLUMN actualPrice REAL")
        database.execSQL("ALTER TABLE shopping_items ADD COLUMN purchasedAt INTEGER")
    }
}
```

### Testing Migration
1. Install V1 of the app (if available)
2. Add some test data
3. Install V2 - migration should run automatically
4. Verify data integrity

## 🧪 Testing

### Manual Testing Checklist
- [ ] App launches successfully
- [ ] Add new shopping items
- [ ] Create and manage labels
- [ ] Filter items by labels (AND/OR)
- [ ] Mark items as purchased
- [ ] Use quick purchase feature
- [ ] View analytics chart
- [ ] Check budget calculations
- [ ] Test app restart (data persistence)

### Device Testing
Test on various devices:
- Different screen sizes (phone/tablet)
- Different Android versions (API 24+)
- Different orientations (portrait/landscape)

## 🎯 Feature Verification

### Core Features
1. **Shopping List Management**
   - ✅ Add/edit/delete items
   - ✅ Quantity and price tracking
   - ✅ Purchase status toggle

2. **Label System**
   - ✅ Create custom labels with colors
   - ✅ Assign multiple labels to items
   - ✅ Filter by labels (AND/OR logic)

3. **Enhanced Purchase Tracking**
   - ✅ Actual price input
   - ✅ Quick purchase with search
   - ✅ Budget comparison
   - ✅ Purchase timestamps

4. **Visual Analytics**
   - ✅ Animated pie chart
   - ✅ Progress percentage
   - ✅ Budget summary cards

## 📱 App Information

- **Package Name**: com.maternitytracker
- **Version Code**: 2
- **Version Name**: "2.0"
- **Min SDK**: 24 (Android 7.0)
- **Target SDK**: 34 (Android 14)
- **App Name**: Enhanced Maternity Tracker

## 🔐 Permissions

The app requires minimal permissions:
- Storage access for database operations (handled automatically by Room)
- No network permissions required (offline-first design)

## 📞 Support

If you encounter issues:
1. Check this troubleshooting guide
2. Verify all prerequisites are met
3. Clean and rebuild the project
4. Check Android Studio logs for specific errors

---

**Happy Building! 🎉**

The Enhanced Maternity & Baby Shopping Tracker is now ready to build and deploy. Follow these instructions carefully, and you'll have a fully functional app with all the advanced features specified in your requirements.