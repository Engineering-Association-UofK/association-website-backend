### Cloud Infrastructure

* **Provider:** Oracle Cloud Infrastructure (OCI) - Always Free Tier.
* **Instance Specs:** 1 OCPU, 1GB RAM, Ubuntu 24.04.4 LTS (Noble).
* **Storage/Swap:** 2GB Swapfile at `/swapfile` to address RAM limitations.
* **Database:** TiDB Cloud (Distributed MySQL).
* **Asset Management:** Cloudinary, signed certificates.
* **DNS:** Caddy Managed SSL.

### Services & Applications

1. Java Backend (Spring Boot)

* **Port:** `8080`
* **Process:** `systemd` service (`backend.service`).
* **Directory:** `/opt/backend/`.
* **JVM Options:** `-Xms128m` / `-Xmx512m`.
* **Key Features:** PDF Signing (Bouncy Castle), QR Code Generation (ZXing), Thymeleaf Templating, Database Interactions.

#### 2. T.O.B. (Go Microservice)

* **Port:** `8888`
* **Process:** `systemd` service (`top.service`).
* **Directory:** `/opt/tob/`.
* **Core Logic:**
* **Watchdog:** Monitors Java backend health independently to ensure feedback endpoints remain responsive even with high RAM usage.
* **PDF Engine:** Renders HTML to PDF using **Chromium**.
* **Alerts:** Manages Telegram notifications for system events.

#### 3. Web & Edge

* **Reverse Proxy:** Caddy.
* **Frontend:** React/Vite, `/var/www/`.

---

### CI/CD & Deployment

* **Automation:** GitHub Actions triggered on push/merge to `main` branch for all repositories.
* **Logs:** * `journalctl -u backend -f`
* `journalctl -u tob -f`

---

### Server Environment Setup

*The following commands are required to initialize the environment and support PDF rendering.*

#### 1. Repository & Package Updates

```bash
# Enable universe for extended package support
sudo apt-add-repository universe
sudo apt-get update
```

#### 2. Localization & Rendering (Arabic Support)

*Essential for correct PDF character rendering in the Chromium service.*

```bash
# Install Amiri font for high quality Arabic typography
sudo apt-get install fonts-hosny-amiri
```

#### 3. Maintenance Notes

* The `oracle-cloud-agent` is **disabled** to maximize available RAM.