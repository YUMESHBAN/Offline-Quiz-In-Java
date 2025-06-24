# Offline-Quiz-In-Java – Java Swing Project


A simple desktop-based **Quiz Management System** built using **Java Swing** and **SQLite**. This project allows admins to add quiz questions and students to take quizzes by subject, view results, and filter past performance.

---

## 📌 Features

### 👨‍🏫 Admin Panel
- ➕ Add new multiple-choice questions
- 📋 View all student results in a table
- 🔍 Filter results by student name or subject

### 👨‍🎓 Student Panel
- 📝 Take quizzes by selecting a subject
- 📊 Get instant feedback on score
- 💾 Result is saved for future reference

---

## 🧱 Tech Stack

| Component | Technology |
|----------|-------------|
| Language | Java |
| GUI      | Java Swing |
| Database | SQLite (via JDBC) |
| IDE      | Eclipse / IntelliJ |

---

## 📂 Folder Structure

├── db
│ └── DBConnection.java # Database connection utility
├── gui
│ ├── MainMenu.java # Home screen
│ ├── AddQuestion.java # Add question UI
│ ├── StartQuiz.java # Select subject to start quiz
│ ├── StudentQuiz.java # Quiz interface
│ ├── ViewResults.java # View all quiz results
│ └── FilteredResults.java # Filtered results display
└── resources
└── quiz.db # SQLite database

yaml
Copy
Edit

---

## ⚙️ How to Run

1. **Clone the repo:**
   ```bash
   git clone https://github.com/YOUR_USERNAME/YOUR_REPO_NAME.git
Open in Eclipse or any IDE.

Make sure sqlite-jdbc.jar is added to the classpath.

Run MainMenu.java

🗃️ Database
File: resources/quiz.db

Tables:

questions(id, question_text, option_a, option_b, option_c, option_d, correct_ans, subject)

results(id, student_name, subject, score, total)



🧑‍💻 Author
Name: Yumesh Ban

GitHub: @YUMESHBAN
