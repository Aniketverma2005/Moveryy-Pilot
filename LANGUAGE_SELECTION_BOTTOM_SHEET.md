# Language Selection - Bottom Sheet Design

## Overview
A modern, professional language selection screen using Material Design Bottom Sheet pattern. The design provides an elegant user experience with a main screen showing the app branding and a slide-up drawer for language selection.

## Design Pattern: Bottom Sheet

### Why Bottom Sheet?
- **Modern UX**: Follows Material Design guidelines
- **Professional Look**: Clean, organized interface
- **Better Focus**: Main content remains visible while selecting
- **Smooth Interaction**: Slide-up animation feels natural
- **Space Efficient**: Maximizes screen real estate

## Screen Layout

### Main Content (Always Visible)
```
┌─────────────────────────────┐
│   Blue Gradient Background  │
│                             │
│      ┌─────────────┐        │
│      │   Moveryy   │        │
│      │    Logo     │        │
│      └─────────────┘        │
│                             │
│  Welcome to Moveryy Pilot   │
│  Your delivery management   │
│       companion             │
│                             │
│  ┌───────────────────────┐  │
│  │  🌐 Language Icon     │  │
│  │  Choose Your Language │  │
│  │  English (US)         │  │
│  │  Tap to select        │  │
│  └───────────────────────┘  │
│                             │
└─────────────────────────────┘
```

### Bottom Sheet (Slides Up)
```
┌─────────────────────────────┐
│         ─────               │ ← Drag Handle
│                             │
│  Select Language            │
│  Choose your preferred...   │
│                             │
│  ┌───────────────────────┐  │
│  │ 🇬🇧 English      ✓   │  │
│  └───────────────────────┘  │
│  ┌───────────────────────┐  │
│  │ 🇪🇸 Español          │  │
│  └───────────────────────┘  │
│  ┌───────────────────────┐  │
│  │ 🇫🇷 Français         │  │
│  └───────────────────────┘  │
│  ┌───────────────────────┐  │
│  │ 🇩🇪 Deutsch          │  │
│  └───────────────────────┘  │
│  ┌───────────────────────┐  │
│  │ 🇮🇳 हिन्दी           │  │
│  └───────────────────────┘  │
│  ┌───────────────────────┐  │
│  │ 🇸🇦 العربية          │  │
│  └───────────────────────┘  │
│                             │
│  ┌───────────────────────┐  │
│  │      Continue         │  │
│  └───────────────────────┘  │
└─────────────────────────────┘
```

## User Interaction Flow

### Step 1: Initial View
- User sees main screen with logo and branding
- White card shows "Choose Your Language" prompt
- Default language displayed: "English (US)"
- "Tap to select" hint visible

### Step 2: Open Bottom Sheet
- User taps on the language prompt card
- Bottom sheet slides up from bottom with smooth animation
- Main content remains visible (dimmed/blurred)
- Drag handle visible at top of sheet

### Step 3: Select Language
- User sees 6 language options in individual cards
- Each card shows flag emoji, native name, and English name
- User taps desired language
- Blue checkmark (✓) appears next to selected language
- Bottom sheet automatically collapses
- Selected language updates in main screen prompt

### Step 4: Continue
- User can tap prompt again to change selection
- When satisfied, user taps "Continue" button
- Language preference is saved
- App navigates to Welcome screen

## Visual Design Elements

### Main Screen Components

#### 1. Logo Card
- **Size**: 200dp × 110dp
- **Padding**: 20dp
- **Corner Radius**: 24dp
- **Elevation**: 16dp
- **Background**: White
- **Content**: Moveryy Pilot logo

#### 2. Welcome Text
- **Title**: "Welcome to Moveryy Pilot"
  - Size: 32sp
  - Color: White
  - Style: Bold
  - Font: sans-serif-medium
- **Subtitle**: "Your delivery management companion"
  - Size: 16sp
  - Color: White (95% opacity)

#### 3. Language Prompt Card
- **Background**: White with light blue border
- **Corner Radius**: 24dp
- **Padding**: 24dp
- **Ripple Effect**: Light blue (#E3F2FD)
- **Icon**: 🌐 Language globe (48dp, blue)
- **Title**: "Choose Your Language" (20sp, bold, blue)
- **Selected**: Current language name (16sp, gray)
- **Hint**: "Tap to select" (14sp, light gray)

### Bottom Sheet Components

#### 1. Sheet Container
- **Background**: White
- **Top Corners**: Rounded (32dp)
- **Elevation**: 16dp
- **Behavior**: Hideable, expandable

#### 2. Drag Handle
- **Size**: 40dp × 4dp
- **Color**: #CCCCCC
- **Corner Radius**: 2dp
- **Position**: Top center
- **Margin**: 12dp top, 20dp bottom

#### 3. Sheet Header
- **Title**: "Select Language"
  - Size: 24sp
  - Color: #1A1A1A
  - Style: Bold
- **Subtitle**: "Choose your preferred language"
  - Size: 14sp
  - Color: #757575

#### 4. Language Cards
- **Layout**: Individual CardView for each language
- **Corner Radius**: 16dp
- **Elevation**: 2dp
- **Padding**: 20dp
- **Margin Bottom**: 12dp
- **Ripple Effect**: Material ripple
- **Components**:
  - Flag emoji (48dp, 32sp)
  - Native name (18sp, bold, black)
  - English name (14sp, gray)
  - Checkmark icon (28dp, blue, conditional)

#### 5. Continue Button
- **Width**: Match parent (with 24dp margins)
- **Height**: 58dp
- **Background**: Blue (#1E5BA8)
- **Text**: "Continue" (17sp, bold, white)
- **Corner Radius**: 16dp
- **Elevation**: 4dp
- **Margin Top**: 24dp

## Technical Implementation

### Layout Structure
```xml
CoordinatorLayout (root)
├── LinearLayout (main content)
│   ├── CardView (logo)
│   ├── TextView (welcome title)
│   ├── TextView (subtitle)
│   └── LinearLayout (language prompt)
│       ├── ImageView (language icon)
│       ├── TextView (title)
│       ├── TextView (selected language)
│       └── TextView (hint)
└── NestedScrollView (bottom sheet)
    └── LinearLayout
        ├── View (drag handle)
        ├── TextView (sheet title)
        ├── TextView (sheet subtitle)
        ├── LinearLayout (language cards container)
        │   ├── CardView (English)
        │   ├── CardView (Spanish)
        │   ├── CardView (French)
        │   ├── CardView (German)
        │   ├── CardView (Hindi)
        │   └── CardView (Arabic)
        └── MaterialButton (continue)
```

### Bottom Sheet Behavior
```java
BottomSheetBehavior<NestedScrollView> bottomSheetBehavior;

// Initialize
bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);
bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);

// Show on click
languagePrompt.setOnClickListener(v -> {
    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
});

// Auto-collapse after selection
bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
```

### States
1. **STATE_HIDDEN**: Bottom sheet completely hidden (initial state)
2. **STATE_COLLAPSED**: Partially visible (not used in this design)
3. **STATE_EXPANDED**: Fully visible (when user selects language)

## Animations

### Bottom Sheet Slide-Up
- **Duration**: ~300ms (Material default)
- **Easing**: Decelerate interpolator
- **Effect**: Smooth slide from bottom

### Language Selection
- **Checkmark**: Fade in/out
- **Card**: Ripple effect on touch
- **Sheet**: Auto-collapse after selection

### Screen Transition
- **To Welcome**: Fade in/out (300ms)

## Accessibility Features

### Touch Targets
- All interactive elements ≥ 48dp height
- Language cards: 88dp height (comfortable)
- Continue button: 58dp height

### Visual Feedback
- Ripple effects on all clickable items
- Checkmark for selected language
- Clear visual hierarchy

### Screen Reader Support
- Content descriptions for icons
- Proper focus order
- Semantic labels

## Responsive Design

### Portrait Mode (Primary)
- Full-screen main content
- Bottom sheet slides up from bottom
- Language cards stack vertically

### Landscape Mode
- Main content adjusts to available space
- Bottom sheet may show more items
- Scrollable if needed

### Different Screen Sizes
- **Small phones**: Bottom sheet takes most of screen
- **Large phones**: Bottom sheet ~70% of screen
- **Tablets**: Bottom sheet ~50% of screen

## Color Palette

### Main Screen
- Background Gradient: `#1E5BA8` → `#2B6BC0`
- Logo Card: `#FFFFFF`
- Text: `#FFFFFF`
- Prompt Card: `#FFFFFF`
- Prompt Border: `#E3F2FD`
- Icon: `#1E5BA8`

### Bottom Sheet
- Background: `#FFFFFF`
- Drag Handle: `#CCCCCC`
- Title: `#1A1A1A`
- Subtitle: `#757575`
- Card Background: `#FFFFFF`
- Card Border: None (elevation only)
- Checkmark: `#1E5BA8`
- Button: `#1E5BA8`

## Advantages Over Previous Design

### Previous Design (Full Screen List)
- ❌ Takes entire screen
- ❌ Hides branding during selection
- ❌ Less modern feel
- ❌ Abrupt transitions

### New Design (Bottom Sheet)
- ✅ Keeps branding visible
- ✅ Modern Material Design pattern
- ✅ Smooth, natural animations
- ✅ Better visual hierarchy
- ✅ More professional appearance
- ✅ Easier to understand flow
- ✅ Can dismiss by dragging down
- ✅ Better use of screen space

## User Experience Benefits

1. **Context Preservation**: Logo and branding remain visible
2. **Clear Action**: Obvious what to do (tap prompt)
3. **Smooth Interaction**: Natural slide-up animation
4. **Quick Selection**: Auto-collapse after choosing
5. **Easy Correction**: Can reopen to change selection
6. **Professional Feel**: Modern, polished interface
7. **Intuitive Gesture**: Can drag to dismiss
8. **Visual Feedback**: Clear indication of selection

## Testing Checklist

- [x] Bottom sheet slides up smoothly
- [x] Drag handle visible and functional
- [x] Language prompt opens bottom sheet
- [x] Language cards are clickable
- [x] Checkmark appears on selection
- [x] Only one language selected at a time
- [x] Selected language updates in prompt
- [x] Bottom sheet auto-collapses after selection
- [x] Continue button navigates to Welcome
- [x] Back button collapses sheet (not exit app)
- [ ] Drag down to dismiss works
- [ ] Animations are smooth (60fps)
- [ ] Works on different screen sizes
- [ ] Accessible with TalkBack

## Future Enhancements

1. **Dim Background**: Add scrim when sheet is open
2. **Search**: Add search bar for many languages
3. **Recent Languages**: Show recently used at top
4. **System Language**: Auto-detect and suggest
5. **Preview**: Show sample text in selected language
6. **Animations**: Add more micro-interactions
7. **Haptic Feedback**: Vibrate on selection
8. **Voice**: Voice command to select language
