# Moveryy Pilot - App Navigation Flow

## Complete Navigation Structure

```
┌─────────────────┐
│ SplashActivity  │ (2.5 seconds)
│  - Moveryy Logo │
│  - Blue Theme   │
└────────┬────────┘
         │
         ▼
┌──────────────────────┐
│ LanguageSelection    │
│  - English           │
│  - Spanish           │
│  - French            │
│  - German            │
│  - Hindi             │
│  - Arabic            │
└────────┬─────────────┘
         │
         ▼
┌─────────────────────┐
│ WelcomeActivity     │
│  - Features List    │
│  - Two Buttons:     │
│    1. Get Started   │
│    2. Sign in       │
└──────┬──────┬───────┘
       │      │
       │      └──────────────┐
       │                     │
       ▼                     ▼
┌──────────────┐      ┌──────────────┐
│ SignUpActivity│      │ LoginActivity│
│ (Registration)│      │  - Password  │
│               │      │  - Email OTP │
│               │      │  - Social    │
└──────┬───────┘      └──────┬───────┘
       │                     │
       │                     │
       └──────────┬──────────┘
                  │
                  ▼
         ┌────────────────┐
         │ MainActivity   │
         │ (Main App)     │
         └────────────────┘
```

## Detailed Flow Description

### 1. **Splash Screen** → Language Selection
- **Duration**: 2.5 seconds
- **Transition**: Automatic fade animation
- **Purpose**: Brand introduction, app loading

### 2. **Language Selection** → Welcome Screen
- **Languages Available**:
  - 🇬🇧 English (US)
  - 🇪🇸 Español (Spanish)
  - 🇫🇷 Français (French)
  - 🇩🇪 Deutsch (German)
  - 🇮🇳 हिन्दी (Hindi)
  - 🇸🇦 العربية (Arabic)
- **Features**:
  - Visual selection with checkmarks
  - Flag emojis for easy identification
  - Saves preference to SharedPreferences
  - Only shown once (first time)
- **Back Navigation**: Disabled (user must select language)
- **Continue Button**: Navigates to Welcome Screen

### 3. **Welcome Screen** → Two Options

#### Option A: "Get Started" Button
- **Destination**: SignUpActivity (Registration)
- **Use Case**: New users who want to create an account
- **Transition**: Fade animation
- **Back Navigation**: Allowed (can go back to Welcome)

#### Option B: "Sign in" Button
- **Destination**: LoginActivity
- **Use Case**: Existing users who want to log in
- **Transition**: Fade animation
- **Back Navigation**: Allowed (can go back to Welcome)

### 4. **Login Screen** → Main App
- **Authentication Methods**:
  - Password login
  - Email OTP
  - Google OAuth
  - GitHub OAuth
  - LinkedIn OAuth
- **Additional Options**:
  - "Forgot password?" → (TODO: Password recovery)
  - "Create one now" → SignUpActivity
- **On Success**: Navigate to MainActivity
- **Back Navigation**: Allowed (can go back to Welcome)

### 5. **Sign Up Screen** → Main App
- **Status**: Placeholder (Coming Soon)
- **Purpose**: New user registration
- **On Success**: Navigate to MainActivity
- **Back Navigation**: Allowed (can go back to Welcome)

## Navigation Rules

### Back Button Behavior
- **Splash Screen**: Cannot go back (entry point)
- **Language Selection**: Disabled (user must select language)
- **Welcome Screen**: Exit app
- **Login Screen**: Return to Welcome Screen
- **Sign Up Screen**: Return to Welcome Screen
- **Main Activity**: Exit app (or show exit confirmation)

### Session Management
- Language preference saved in SharedPreferences
- Language selection shown only once (first time)
- After language selection, always goes to Welcome screen
- Users can access Welcome → Login/SignUp flow
- After successful login/signup, user goes to MainActivity
- MainActivity should check authentication status

## File Structure

### Activities
```
app/src/main/java/com/example/moveryypilot/
├── SplashActivity.java              ✅ Complete
├── LanguageSelectionActivity.java   ✅ Complete
├── WelcomeActivity.java             ✅ Complete
├── LoginActivity.java               ✅ Complete
├── SignUpActivity.java              ✅ Created (Placeholder)
└── MainActivity.java                ✅ Existing
```

### Layouts
```
app/src/main/res/layout/
├── activity_splash.xml              ✅ Complete
├── activity_language_selection.xml  ✅ Complete
├── activity_welcome.xml             ✅ Complete
├── activity_login.xml               ✅ Complete
├── activity_signup.xml              ✅ Created (Placeholder)
└── activity_main.xml                ✅ Existing
```

### AndroidManifest.xml
```xml
✅ SplashActivity (LAUNCHER)
✅ LanguageSelectionActivity
✅ WelcomeActivity
✅ LoginActivity
✅ SignUpActivity
✅ MainActivity
```

## Implementation Status

| Screen | Status | Functionality |
|--------|--------|---------------|
| Splash | ✅ Complete | Auto-navigate to Language Selection |
| Language Selection | ✅ Complete | 6 languages, saves preference |
| Welcome | ✅ Complete | Two navigation options |
| Login | ✅ Complete | Full UI, tab switching, validation |
| Sign Up | 🟡 Placeholder | Basic layout, needs implementation |
| Main | ✅ Existing | App main screen |

## Next Steps

### Priority 1: Sign Up Screen
- [ ] Design sign up form layout
- [ ] Add input fields (name, email, password, confirm password)
- [ ] Implement form validation
- [ ] Add terms & conditions checkbox
- [ ] Connect to authentication backend

### Priority 2: Authentication Backend
- [ ] Set up authentication API
- [ ] Implement JWT token handling
- [ ] Add secure credential storage
- [ ] Session management

### Priority 3: OAuth Integration
- [ ] Google OAuth setup
- [ ] GitHub OAuth setup
- [ ] LinkedIn OAuth setup

### Priority 4: Password Recovery
- [ ] Create ForgotPasswordActivity
- [ ] Email verification flow
- [ ] Password reset functionality

### Priority 5: Email OTP
- [ ] OTP generation and sending
- [ ] OTP verification screen
- [ ] Resend OTP functionality

## Testing Checklist

- [x] Splash screen displays for 2.5 seconds
- [x] Splash navigates to Language Selection
- [x] Language Selection displays 6 languages
- [x] Language selection saves preference
- [x] Language Selection navigates to Welcome
- [x] Language Selection shown only once
- [x] Welcome "Get Started" button navigates to SignUp
- [x] Welcome "Sign in" button navigates to Login
- [x] Login screen displays correctly
- [x] Login tab switching works (Password ↔ Email OTP)
- [x] Login "Create one now" navigates to SignUp
- [ ] SignUp screen displays (placeholder)
- [ ] Back navigation works on all screens
- [ ] Login validation works
- [ ] Successful login navigates to Main
- [ ] Successful signup navigates to Main
- [ ] Language preference persists across app restarts

## User Experience Notes

1. **First Time Users**: Splash → Language Selection → Welcome → Get Started → SignUp → Main
2. **Returning Users**: Splash → Welcome (skips language) → Sign in → Login → Main
3. **Language Change**: Users can change language in app settings (TODO)
4. **Forgot Account**: Login → "Create one now" → SignUp
5. **No Account Yet**: SignUp → "Already have account?" → Login
6. **Back Navigation**: All screens allow going back except Splash and Language Selection
