# Free Palestine Bank — Queue Management Platform (SC-03)

An Aeal-time token and queue optimization platform built for the **SC-03 Public Service Queue and Wait-Time Optimizer** challenge. Designed specifically for **Free Palestine Bank**, the platform eliminates long, unpredictable physical lines by combining a frictionless citizen experience with an optimized counter-staff workflow.

---

## 🌐 Live Application Demo

You can test the live deployment directly in your browser:
🔗 **[https://free-palestine-openhack.loca.lt](https://free-palestine-openhack.loca.lt)**

> **⚠️ Important Notes for Live Access (LocalTunnel):**
> * **First-Time Screen:** When opening the link for the first time, LocalTunnel will display a security/warning page. Simply follow the instructions on that page (such as acknowledging or copying the verification token/IP) to proceed to the app.
> * **Tunnel Availability:** Because this is hosted via a local tunnel, it may occasionally close due to internet connection breaks (resulting in a `503` error). If the link is down or unavailable, please contact us on `0344-8143397` so we can restart the tunnel immediately!

---

## 🌟 Problem & Solution

* **The Problem:** Long and unpredictable wait times at bank and service counters waste citizens' time and create severe operational bottlenecks, as quick transactions get trapped behind lengthy service requests.
* **The Solution:** A digital token system where citizens can scan a QR code to view live wait times and request tokens instantly without logging in, while bankers utilize an optimized **Shortest Job First (SJF) + Aging** scheduling engine and a real-time operator command center.

---

## 🧭 Pages & Route Map

| Page | File | Access Level | Description |
| :--- | :--- | :--- | :--- |
| **Landing & QR** | `index.html` | Public | Welcome portal displaying a **downloadable QR code** that links straight to the public queue service portal. |
| **Service Request & Tracker** | `services.html` | Public | Frictionless citizen portal. Users enter their name, select a service from a dynamic dropdown, generate a token, and monitor live queue position, wait-time countdowns, and status updates (`WAITING`, `SERVING`, `COMPLETED`, `CANCELLED`). |
| **Staff Login** | `login.html` | Public / Role-Protected | Secure portal for bank counter personnel and administrators to authenticate into the system. *(Note: Forgot Password modal is currently a UI placeholder and not functional yet).* |
| **Staff Registration** | `register.html` | Admin Only | Allows existing administrators to securely register new counter operators. |
| **Service Catalog (Jobs)** | `jobs.html` | Admin / Staff | Management interface where bankers define available bank services (e.g., *Deposit Cash* taking 1.5 minutes, *Open Account* taking 3 minutes) which feed directly into the scheduling algorithm. |
| **Operator Dashboard** | `dashboard.html` | Admin / Staff | Real-time command center displaying live queue metrics (Waiting, Serving, Completed, Cancelled counts) and ledger tables with actions to advance token states (`Serve`, `Complete`). |
| **About & Docs** | `about.html` | Public | Team credentials, architectural breakdown, and project documentation. |

---

## 👥 Role-Based Workflows

### 1. Citizens / Customers (Frictionless)
* **No Login Required:** Customers scan the QR code on `index.html` or visit `services.html`.
* **Token Generation:** Enter full name, select a required banking service, and click "Continue".
* **Live Tracking:** The screen transforms into a live tracker showing people ahead, estimated wait time in minutes, and status badges. Users can cancel their token while waiting or being served.

### 2. Counter Operators & Administrators (Secure)
* **Authentication:** Staff must log in via `login.html`. New operators are onboarded through `register.html` by admins.
* **Service Configuration:** Through `jobs.html`, operators establish service types and baseline durations (e.g., quick 1-minute deposits vs. multi-minute account setups).
* **Queue Control:** The operator dashboard (`dashboard.html`) auto-refreshes every 5 seconds, displaying real-time operational analytics and allowing staff to advance queue tokens smoothly.

---

## 🧮 Queue Optimization: SJF + Aging Algorithm

To prevent starvation of long-running tasks while maximizing counter throughput for quick transactions, the backend (`BankQueueService`) implements **Shortest Job First (SJF) enhanced with dynamic aging**:

$$\text{Priority Score} = \text{ServiceTime} - (\text{WaitTime} \times 0.2)$$

* **SJF Principle:** Shorter tasks naturally float to the top of the queue to clear counter traffic fast.
* **Aging Factor:** As a customer waits longer in line, their score decreases based on their accumulated wait time multiplied by a `0.2` weight factor, ensuring absolute fairness.

---

## 🛠️ Technology Stack

* **Backend:** Java 17, Spring Boot, Spring Security, Spring Data JPA / Hibernate, REST APIs.
* **Database:** MySQL / H2 (Relational schema for tokens, jobs, and audit timestamps).
* **Frontend:** HTML5, Tailwind CSS, FontAwesome icons, Vanilla JavaScript (`Fetch API`, dynamic DOM manipulation, polling loops).
* **Security & Session:** Spring Security with role-based restrictions (`ADMIN`, `STAFF`) and permitted public static endpoints.

---

## ⚠️ Known Limitations & Future Scope

* **Password Reset:** The "Forgot Password" modal currently present on `login.html` is a frontend UI placeholder and is **not functional yet** (backend reset token services are planned for a future release).
* **Multi-Counter Support:** The current implementation uses a simulated single-counter model, with expansion for multi-counter allocation queued for subsequent iterations.

---

## 🚀 Getting Started & Local Setup

To run the application locally on your PC, follow these steps:

1. **Clone the repository:**
   ```bash
   git clone [https://github.com/Ahtisham-1214/OpenHack-FreePalestine.git](https://github.com/Ahtisham-1214/OpenHack-FreePalestine.git)
   ```

Configure Database:
Open src/main/resources/application.properties and update your database connection settings (spring.datasource.url, username, password) if required.

Run the Application:

## Windows

```bash
mvnw.cmd spring-boot:run
```
or

```bash
mvnw spring-boot:run
```

or 

```bash
./mvnw spring-boot:run
```

## Linux

```bash
mvnw spring-boot:run
```
or 

```bash
bash mvnw spring-boot:run
```

Access the Application Locally:
Open your browser and navigate to:
http://localhost:8080