# Security Policy

## Supported Versions

We actively support the following versions of the Enhanced Maternity & Baby Shopping Tracker:

| Version | Supported          |
| ------- | ------------------ |
| 2.0.x   | :white_check_mark: |
| 1.0.x   | :x:                |

## Reporting a Vulnerability

We take the security of our application seriously. If you discover a security vulnerability, please follow these steps:

### 1. Do Not Create Public Issues
Please **do not** create public GitHub issues for security vulnerabilities. This helps protect users while we work on a fix.

### 2. Contact Information
Report security vulnerabilities by emailing: **security@example.com**

### 3. Include the Following Information
When reporting a vulnerability, please include:

- **Description**: Clear description of the vulnerability
- **Steps to Reproduce**: Detailed steps to reproduce the issue
- **Impact**: Potential impact and severity assessment
- **Environment**: Device information, Android version, app version
- **Proof of Concept**: If applicable, include screenshots or code snippets

### 4. Response Timeline
- **Initial Response**: Within 48 hours
- **Status Update**: Within 7 days
- **Resolution**: Varies based on complexity and severity

## Security Measures

### Data Protection
- **Local Storage Only**: All data is stored locally using Room database
- **No Network Communication**: App operates entirely offline
- **Encrypted Storage**: Sensitive data is protected using Android's built-in encryption
- **No Personal Data Collection**: App doesn't collect or transmit personal information

### Code Security
- **Input Validation**: All user inputs are validated and sanitized
- **SQL Injection Prevention**: Room database provides built-in protection
- **Memory Management**: Proper cleanup of sensitive data in memory
- **Secure Coding Practices**: Following Android security best practices

### Build Security
- **Dependency Scanning**: Regular updates and vulnerability scanning
- **Code Signing**: Release APKs are properly signed
- **ProGuard/R8**: Code obfuscation for release builds
- **Secure Build Pipeline**: GitHub Actions with security checks

## Security Best Practices for Users

### Installation
- **Official Sources Only**: Download APKs only from official GitHub releases
- **Verify Signatures**: Check APK signatures before installation
- **Enable Security Features**: Use Android's built-in security features

### Usage
- **Regular Updates**: Keep the app updated to the latest version
- **Device Security**: Use device lock screens and encryption
- **Backup Considerations**: Be aware that app data may be included in device backups

## Known Security Considerations

### Local Data Storage
- Shopping list data is stored locally on the device
- Data is accessible to users with device access
- No cloud synchronization means no remote data exposure

### Permissions
- **Minimal Permissions**: App requests only necessary permissions
- **Storage Access**: Required for database operations
- **No Network Permissions**: App doesn't access the internet

## Security Updates

### Update Process
1. Security vulnerabilities are assessed for severity
2. Fixes are developed and tested
3. Updates are released through GitHub releases
4. Users are notified through release notes

### Emergency Updates
For critical security issues:
- Immediate patch development
- Expedited testing process
- Emergency release within 24-48 hours
- Clear communication about the urgency

## Responsible Disclosure

We appreciate security researchers who:
- Report vulnerabilities responsibly
- Allow reasonable time for fixes
- Avoid accessing user data unnecessarily
- Follow coordinated disclosure practices

### Recognition
Security researchers who report valid vulnerabilities will be:
- Credited in release notes (if desired)
- Listed in our security acknowledgments
- Provided with early access to fixes for verification

## Security Contact

For security-related questions or concerns:
- **Email**: security@example.com
- **Response Time**: Within 48 hours
- **Encryption**: PGP key available upon request

## Compliance

### Android Security
- Follows Android security guidelines
- Implements Android security best practices
- Regular security testing and updates

### Privacy
- No data collection or transmission
- Local-only data storage
- User privacy protection

---

**Last Updated**: December 2024

Thank you for helping keep the Enhanced Maternity & Baby Shopping Tracker secure for all users.