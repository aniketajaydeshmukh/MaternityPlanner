# Contributing to Enhanced Maternity & Baby Shopping Tracker

Thank you for your interest in contributing to the Enhanced Maternity & Baby Shopping Tracker! This document provides guidelines and information for contributors.

## 🚀 Getting Started

### Prerequisites
- Android Studio Arctic Fox (2020.3.1) or later
- JDK 8 or later
- Git
- Basic knowledge of Kotlin and Android development

### Development Setup

1. **Fork the repository**
   ```bash
   # Click the "Fork" button on GitHub
   # Then clone your fork
   git clone https://github.com/YOUR_USERNAME/enhanced-maternity-tracker.git
   cd enhanced-maternity-tracker
   ```

2. **Set up the development environment**
   ```bash
   # Add the original repository as upstream
   git remote add upstream https://github.com/ORIGINAL_OWNER/enhanced-maternity-tracker.git
   
   # Create a new branch for your feature
   git checkout -b feature/your-feature-name
   ```

3. **Open in Android Studio**
   - Open Android Studio
   - Select "Open an existing project"
   - Navigate to the cloned directory
   - Wait for Gradle sync to complete

## 📋 Development Guidelines

### Code Style

#### Kotlin Conventions
- Follow [Kotlin coding conventions](https://kotlinlang.org/docs/coding-conventions.html)
- Use meaningful variable and function names
- Add KDoc comments for public APIs
- Keep functions small and focused

#### Android Specific
- Follow [Android coding standards](https://developer.android.com/kotlin/style-guide)
- Use Jetpack Compose best practices
- Follow Material 3 design guidelines
- Implement proper error handling

#### Example Code Style
```kotlin
/**
 * Represents a shopping item with purchase tracking capabilities.
 * 
 * @param name The display name of the item
 * @param quantity Number of items to purchase
 * @param estimatedPrice Expected price per item
 * @param actualPrice Actual price paid when purchased (nullable)
 */
@Entity(tableName = "shopping_items")
data class ShoppingItem(
    @PrimaryKey(autoGenerate = true) 
    val id: Long = 0,
    val name: String,
    val quantity: Int,
    val estimatedPrice: Double,
    val actualPrice: Double? = null,
    val labels: String,
    val isPurchased: Boolean = false,
    val purchasedAt: Date? = null,
    val createdAt: Date = Date()
)
```

### Architecture Guidelines

#### MVVM Pattern
- ViewModels should not hold references to Views or Contexts
- Use Repository pattern for data access
- Implement proper separation of concerns

#### Database Design
- Use Room for local storage
- Implement proper migrations for schema changes
- Use TypeConverters for complex data types

#### UI Development
- Use Jetpack Compose for all UI components
- Follow Material 3 design system
- Implement proper state management with StateFlow

## 🔧 Development Workflow

### Branch Naming
- `feature/feature-name` - New features
- `bugfix/bug-description` - Bug fixes
- `hotfix/critical-fix` - Critical production fixes
- `docs/documentation-update` - Documentation changes

### Commit Messages
Follow [Conventional Commits](https://www.conventionalcommits.org/):

```bash
# Format: type(scope): description
feat(shopping): add quick purchase dialog
fix(database): resolve migration issue from v1 to v2
docs(readme): update installation instructions
style(ui): improve label chip appearance
refactor(viewmodel): simplify state management
test(repository): add unit tests for CRUD operations
```

### Pull Request Process

1. **Before submitting**
   ```bash
   # Ensure your branch is up to date
   git fetch upstream
   git rebase upstream/main
   
   # Run tests
   ./gradlew test
   
   # Check code style
   ./gradlew lintDebug
   ```

2. **Create Pull Request**
   - Use a descriptive title
   - Fill out the PR template completely
   - Link related issues
   - Add screenshots for UI changes
   - Request review from maintainers

3. **PR Template**
   ```markdown
   ## Description
   Brief description of changes
   
   ## Type of Change
   - [ ] Bug fix
   - [ ] New feature
   - [ ] Breaking change
   - [ ] Documentation update
   
   ## Testing
   - [ ] Unit tests pass
   - [ ] Manual testing completed
   - [ ] No new lint warnings
   
   ## Screenshots (if applicable)
   [Add screenshots here]
   
   ## Checklist
   - [ ] Code follows style guidelines
   - [ ] Self-review completed
   - [ ] Documentation updated
   - [ ] Tests added/updated
   ```

## 🧪 Testing

### Unit Tests
```kotlin
@Test
fun `should calculate budget correctly`() {
    // Given
    val items = listOf(
        ShoppingItem(name = "Item 1", quantity = 2, estimatedPrice = 10.0),
        ShoppingItem(name = "Item 2", quantity = 1, estimatedPrice = 15.0, actualPrice = 12.0, isPurchased = true)
    )
    
    // When
    val budget = calculateBudget(items)
    
    // Then
    assertEquals(35.0, budget.totalEstimated)
    assertEquals(12.0, budget.totalActual)
    assertEquals(23.0, budget.remaining)
}
```

### UI Tests
```kotlin
@Test
fun `should display shopping items correctly`() {
    composeTestRule.setContent {
        HomeScreen(viewModel = mockViewModel)
    }
    
    composeTestRule
        .onNodeWithText("Test Item")
        .assertIsDisplayed()
}
```

### Manual Testing Checklist
- [ ] App launches without crashes
- [ ] All CRUD operations work
- [ ] Database migration functions correctly
- [ ] UI responds properly to user interactions
- [ ] Analytics charts display correctly
- [ ] Search functionality works
- [ ] Label filtering operates as expected

## 🐛 Bug Reports

### Before Reporting
1. Check existing issues
2. Reproduce the bug consistently
3. Test on latest version
4. Gather relevant information

### Bug Report Template
```markdown
**Describe the bug**
A clear description of what the bug is.

**To Reproduce**
Steps to reproduce the behavior:
1. Go to '...'
2. Click on '....'
3. Scroll down to '....'
4. See error

**Expected behavior**
What you expected to happen.

**Screenshots**
If applicable, add screenshots.

**Device Information:**
- Device: [e.g. Pixel 6]
- OS: [e.g. Android 13]
- App Version: [e.g. 2.0]

**Additional context**
Any other context about the problem.
```

## ✨ Feature Requests

### Before Requesting
1. Check if feature already exists
2. Search existing feature requests
3. Consider if it fits the app's purpose
4. Think about implementation complexity

### Feature Request Template
```markdown
**Is your feature request related to a problem?**
A clear description of what the problem is.

**Describe the solution you'd like**
A clear description of what you want to happen.

**Describe alternatives you've considered**
Alternative solutions or features you've considered.

**Additional context**
Any other context or screenshots about the feature request.
```

## 📚 Documentation

### Code Documentation
- Add KDoc comments for public APIs
- Document complex algorithms
- Explain business logic
- Include usage examples

### README Updates
- Keep installation instructions current
- Update feature lists
- Maintain screenshot gallery
- Document configuration options

## 🏆 Recognition

### Contributors
All contributors will be recognized in:
- README.md contributors section
- Release notes
- GitHub contributors page

### Types of Contributions
- Code contributions
- Bug reports
- Feature suggestions
- Documentation improvements
- Testing and QA
- Design and UX feedback

## 📞 Getting Help

### Communication Channels
- **GitHub Issues**: Bug reports and feature requests
- **GitHub Discussions**: General questions and ideas
- **Pull Request Comments**: Code-specific discussions

### Response Times
- Bug reports: 2-3 business days
- Feature requests: 1 week
- Pull requests: 3-5 business days

## 📄 License

By contributing to this project, you agree that your contributions will be licensed under the MIT License.

## 🙏 Thank You

Thank you for contributing to the Enhanced Maternity & Baby Shopping Tracker! Your contributions help make this app better for everyone in the community.

---

**Happy Contributing! 🎉**

For questions about contributing, please open an issue or start a discussion on GitHub.