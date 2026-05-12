# Moveryy Pilot - Splash Screen Design

## 🎨 Design Overview

The Moveryy Pilot splash screen follows the official Moveryy brand guidelines with a professional, clean design that creates a strong first impression.

### Visual Elements

1. **Background**: Solid Moveryy blue (#1E5BA8)
2. **Logo Container**: White rounded card with elevation
3. **Logo**: Official Moveryy Pilot logo (180dp × 120dp)
4. **App Title**: "Moveryy" in bold white text (36sp)
5. **Tagline**: "Fast, Reliable Delivery Management" in light text (15sp)
6. **Loading Indicator**: White circular progress bar at bottom

## 🎯 Brand Colors

### Primary Colors
- **Moveryy Blue**: `#1E5BA8` (Primary brand color)
- **Dark Blue**: `#164A8C` (Primary dark variant)
- **Light Blue**: `#2B6BC0` (Primary light variant)
- **Accent**: `#00BCD4` (Cyan accent)

### Text Colors
- **White**: `#FFFFFF` (Primary text on blue)
- **Light Text**: `#E3F2FD` (Secondary text on blue)
- **Dark Text**: `#212121` (Primary text on light backgrounds)
- **Secondary Text**: `#757575` (Secondary text on light backgrounds)

## ✨ Animations

The splash screen features smooth fade-in animations:

1. **Logo Card**: Fades in over 800ms (starts at 200ms)
2. **App Name**: Fades in over 600ms (starts at 600ms)
3. **Tagline**: Fades in over 600ms (starts at 900ms)
4. **Total Duration**: 2.5 seconds before transitioning to main activity

## 📱 Technical Implementation

### Files Structure
```
app/src/main/
├── java/com/example/moveryypilot/
│   └── SplashActivity.java          # Splash screen logic & animations
├── res/
│   ├── drawable/
│   │   ├── moveryypilot.png         # Official logo
│   │   ├── splash_background.xml    # Splash background drawable
│   │   └── splash_background_gradient.xml
│   ├── layout/
│   │   └── activity_splash.xml      # Splash screen layout
│   ├── values/
│   │   ├── colors.xml               # Moveryy brand colors
│   │   ├── strings.xml              # App strings
│   │   └── themes.xml               # Splash themes
│   └── values-night/
│       └── colors.xml               # Dark mode colors
```

### Dependencies
- `androidx.core:core-splashscreen:1.0.1` - Android 12+ splash screen API
- `androidx.cardview:cardview:1.0.0` - Logo container card

### Android Manifest
```xml
<activity
    android:name=".SplashActivity"
    android:exported="true"
    android:theme="@style/Theme.MoveryyPilot.SplashCompat">
    <intent-filter>
        <action android:name="android.intent.action.MAIN" />
        <category android:name="android.intent.category.LAUNCHER" />
    </intent-filter>
</activity>
```

## 🌙 Dark Mode Support

The app includes dark mode color variants for a consistent experience across system themes.

## 📐 Design Specifications

### Logo Card
- **Size**: wrap_content (180dp × 120dp with 16dp padding)
- **Corner Radius**: 20dp
- **Elevation**: 8dp
- **Background**: White (#FFFFFF)
- **Position**: Centered horizontally, 180dp from top

### Typography
- **App Name**: 36sp, Bold, sans-serif-medium, White
- **Tagline**: 15sp, Regular, sans-serif, Light blue (#E3F2FD)

### Loading Indicator
- **Size**: 48dp × 48dp
- **Color**: White
- **Position**: Centered horizontally, 80dp from bottom

## 🚀 Usage

The splash screen automatically displays when the app launches and transitions to MainActivity after 2.5 seconds with a smooth fade animation.

---

**Design Status**: ✅ Production Ready  
**Last Updated**: May 9, 2026  
**Version**: 1.0
