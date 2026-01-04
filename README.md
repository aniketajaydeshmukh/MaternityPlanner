# Enhanced Maternity & Baby Shopping Tracker

A comprehensive Android application for managing maternity and baby shopping lists with advanced features including label management, budget tracking, and visual analytics.

## 🚀 Features

### Core Shopping List Management
- ✅ Add, edit, and delete shopping items
- ✅ Track quantity and estimated prices
- ✅ Mark items as purchased/unpurchased
- ✅ Persistent local storage with Room database
- ✅ Modern Material 3 design

### Advanced Label System
- ✅ Create custom labels with 8 predefined colors
- ✅ Assign multiple labels to items
- ✅ Filter items by labels with AND/OR logic
- ✅ Complete label management screen with CRUD operations

### Enhanced Purchase Tracking
- ✅ Track actual prices when items are purchased
- ✅ Quick Purchase Dialog with search functionality
- ✅ Budget comparison (estimated vs actual spending)
- ✅ Purchase timestamp tracking

### Visual Analytics
- ✅ Animated pie chart showing purchase progress
- ✅ Real-time progress percentage display
- ✅ Budget summary cards with color-coded statistics
- ✅ Integrated analytics on home screen

## 🏗️ Technical Architecture

### Technology Stack
- **Language**: Kotlin
- **UI Framework**: Jetpack Compose with Material 3
- **Architecture**: MVVM (Model-View-ViewModel)
- **Database**: Room Database with SQLite
- **Navigation**: Jetpack Navigation Compose
- **Dependency Injection**: Manual DI with Repository pattern
- **Reactive Programming**: Kotlin Coroutines + Flow

### Database Schema
```kotlin
// Shopping Item Entity (Version 2)
@Entity(tableName = "shopping_items")
data class ShoppingItem(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
    val quantity: Int,
    val estimatedPrice: Double,
    val actualPrice: Double? = null,  // NEW in v2
    val labels: String,  // Comma-separated
    val isPurchased: Boolean = false,
    val purchasedAt: Date? = null,    // NEW in v2
    val createdAt: Date = Date()
)

// Label Entity
@Entity(tableName = "labels")
data class Label(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
    val color: String  // Hex color code
)
```

### Key Components
1. **HomeScreen**: Main interface with shopping list and analytics
2. **LabelManagementScreen**: CRUD operations for labels
3. **AddEditItemDialog**: Modal for item creation/editing
4. **QuickPurchaseDialog**: Search and purchase items quickly
5. **PurchaseAnalyticsChart**: Animated progress visualization
6. **BudgetSummaryCard**: Financial overview component

## 📱 Screenshots & UI Flow

### Home Screen
- Shopping list with filter chips
- Budget summary card showing estimated vs actual spending
- Animated pie chart displaying purchase progress
- Quick action buttons for adding items and quick purchase

### Label Management
- Color-coded label creation and editing
- 8 predefined colors: Lavender, Light Pink, Pale Green, Sky Blue, Plum, Khaki, Light Salmon, Light Sea Green
- Delete confirmation dialogs
- Real-time preview of label appearance

### Purchase Tracking
- Search functionality to find unpurchased items
- Actual price input when marking items as purchased
- Purchase timestamp recording
- Budget variance tracking

## 🛠️ Installation & Setup

### Prerequisites
- Android Studio Arctic Fox or later
- Android SDK 24+ (Android 7.0)
- Kotlin 1.9.20+
- Gradle 8.1.4+

### Build Instructions
1. Clone or download the project files
2. Open Android Studio
3. Select "Open an existing project"
4. Navigate to the project directory
5. Wait for Gradle sync to complete
6. Build and run the app

### Dependencies
```kotlin
dependencies {
    implementation 'androidx.appcompat:appcompat:1.6.1'
    implementation 'androidx.core:core-ktx:1.12.0'
    implementation 'androidx.lifecycle:lifecycle-runtime-ktx:2.7.0'
    implementation 'androidx.activity:activity-compose:1.8.2'
    implementation platform('androidx.compose:compose-bom:2023.10.01')
    implementation 'androidx.compose.ui:ui'
    implementation 'androidx.compose.material3:material3'
    implementation 'androidx.navigation:navigation-compose:2.7.5'
    implementation 'androidx.lifecycle:lifecycle-viewmodel-compose:2.7.0'
    implementation 'androidx.room:room-runtime:2.6.1'
    implementation 'androidx.room:room-ktx:2.6.1'
    ksp 'androidx.room:room-compiler:2.6.1'
    implementation 'org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3'
    implementation 'androidx.compose.material:material-icons-extended:1.5.4'
}
```

## 🎨 Design System

### Color Palette
- **Primary**: Lavender (#9C27B0)
- **Secondary**: Light Lavender (#E1BEE7)
- **Tertiary**: Dark Purple (#4A148C)
- **Label Colors**: 8 predefined colors for categorization

### Typography
- Material 3 typography scale
- Consistent font weights and sizes
- Proper contrast ratios for accessibility

## 📊 Database Migration

The app includes a migration from version 1 to version 2:
```kotlin
val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL("ALTER TABLE shopping_items ADD COLUMN actualPrice REAL")
        database.execSQL("ALTER TABLE shopping_items ADD COLUMN purchasedAt INTEGER")
    }
}
```

## 🔧 Configuration

### App Configuration
- **Application ID**: com.maternitytracker
- **Version Code**: 2
- **Version Name**: "2.0"
- **Min SDK**: 24 (Android 7.0)
- **Target SDK**: 34 (Android 14)
- **Compile SDK**: 34

### Build Variants
- **Debug**: Development build with debugging enabled
- **Release**: Production build with ProGuard optimization

## 🧪 Testing

### Test Coverage
- Database operations (CRUD)
- Migration testing (v1 → v2)
- ViewModel state management
- UI component rendering
- Filter logic (AND/OR operations)
- Search functionality
- Budget calculations

### Manual Testing Checklist
- [ ] Add/edit/delete items
- [ ] Create/manage labels
- [ ] Filter by labels (AND/OR)
- [ ] Quick purchase workflow
- [ ] Budget tracking accuracy
- [ ] Analytics chart animation
- [ ] Data persistence across app restarts

## 🚀 Future Enhancements

### Potential Features
- Cloud synchronization
- Export to CSV/PDF
- Shopping list sharing
- Barcode scanning
- Price comparison
- Shopping reminders
- Category-based budgeting
- Multi-user support

### Technical Improvements
- Unit test coverage
- UI testing with Espresso
- Performance optimization
- Accessibility improvements
- Offline-first architecture
- Background sync

## 🐛 Troubleshooting

### Common Issues
1. **Build Errors**: Ensure all dependencies are properly synced
2. **Theme Issues**: Verify AppCompat dependency is included
3. **Database Errors**: Check migration implementation
4. **Navigation Issues**: Verify NavHost setup

### Debug Tips
- Enable verbose logging for database operations
- Use Layout Inspector for UI debugging
- Monitor memory usage during chart animations
- Test on different screen sizes and orientations

## 📄 License

This project is created for educational and demonstration purposes. Feel free to use and modify as needed.

## 👥 Contributing

Contributions are welcome! Please follow these guidelines:
1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Add tests if applicable
5. Submit a pull request

## 📞 Support

For issues or questions:
1. Check the troubleshooting section
2. Review the code documentation
3. Create an issue with detailed description
4. Include device information and logs

---

**Built with ❤️ using Jetpack Compose and Material 3**