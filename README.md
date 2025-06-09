✨ Envitia Task Logger ✨
A simple Android app that lets you type log messages, view them in a scrollable list, save them to a file, and reload them when the app restarts. Includes:

✅ Real-time log display
✅ Persistent storage to internal file
✅ Auto-load logs on launch
✅ Clear log button
✅ Disabled "OK" button until input is provided
✅ ViewModel-based architecture
✅ Unit-tested with Robolectric
✅ Espresso-ready setup

🚀 Features
Log input
Type a message and press OK to log it with a timestamp.

ScrollView output
View all log entries in a scrollable text view.

File storage
Logs are saved to internal storage and automatically reloaded when the app restarts.

Button behaviour
"OK" is disabled when the input is empty. "Clear Logs" removes all saved entries.

🧪 Testing
Unit tests written for MainViewModel using Robolectric

Espresso test setup included (ready for your UI assertions!)

🛠️ Tech
Kotlin

ViewModel + LiveData

DataBinding

Robolectric + JUnit

Espresso (for UI testing)

Made with ☕ and 💻 by Bobby
