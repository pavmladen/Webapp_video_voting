# Video Voting Web Application

Java JSP web application that allows users to vote for videos, displays top-ranked videos, and provides user behavior analytics. Developed as a project for the Web Application Development course.

---

## Features

- Voting between two random YouTube videos

- Top 5 mini ranking table (on the home page)

- Full ranking list with pagination (20 per page)

- Share option for sharing the current video pair

- Statistical ranking using the Wilson score algorithm

- Refresh and display of new videos

---

## Technologies

- **Java 21 / 23**
- **JSP, Servlets**
- **JPA (Hibernate) + MySQL**
- **Gradle 8.5 + Gretty (Jetty)**
- **Vanilla JS, HTML, CSS**

---

## Installation and Setup

### 1. Start MySQL server

```bash
sudo service mysql start
```

### 2. Create the database

In the MySQL terminal:

```sql
CREATE DATABASE videodb DEFAULT CHARACTER SET utf8mb4;
```

(Check the username and password in src/main/resources/META-INF/persistence.xml)

---

### 3. Run the application

In the project root folder (where gradlew is located):

```bash
./gradlew clean build
./gradlew appRun
```

The application runs at:

```
http://localhost:8080/home
```

---

## Generating Video Data

### Run the generator

Run the generator once to populate the database:

```bash
./gradlew run --args="generateVideos"
```

It uses 10 real YouTube IDs and the Faker library for random titles and descriptions.

---

## URLs

- Home page: /home

- Ranking list: /rankings.html

- API random video: /api/videos/random

- API vote: /api/vote

---

## Ranking – Wilson Score

For fair ranking, the Wilson score formula with 95% confidence is used:

```
score = (p + z²/2n - z√[(p(1-p) + z²/4n)/n]) / (1 + z²/n)
```

Where:

- p = positive votes / total votes
- n = total number of votes
- z = 1.96 (for 95% confidence)

Both the rank position and rankScore are displayed (either as a decimal or percentage).

---
