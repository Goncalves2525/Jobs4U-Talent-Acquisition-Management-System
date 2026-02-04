# Jobs4U - Talent Acquisition Management System

## 1. Description

Jobs4U is an application developed to help talent acquisition companies simplify and optimize processes related to the selection and recruitment of candidates. The system provides automated recruitment processes with interfaces for all users involved in Jobs4U (system administrators, customer managers, operators, and candidates).

The application streamlines the entire recruitment process from job posting to candidate selection, supporting features such as:
- Job opening management
- Candidate application processing
- Requirements verification
- Interview management
- Candidate ranking
- Communication with clients and candidates

## 2. Project Structure

The project is organized into several components:

- **BackOffice App**: Main application used by administrators, customer managers, and operators
- **Candidate App**: Console application for candidates to view applications and receive notifications
- **Customer App**: Console application for clients to monitor job openings
- **Applications File Bot**: Processes application files for system import
- **Follow Up Server**: Manages communication between clients and the database
- **Job Requirements and Interview Plugins**: ANTLR-based language processing for evaluating requirements and interviews

## 3. System Requirements

- Java JDK 11 or higher
- Maven 3.6 or higher
- ANTLR 4.10 or higher
- C/C++ compiler (GCC or equivalent)
- H2 Database Engine (included in the project)
- Git

## 4. Installation Guide

### 4.1 Common Steps for All Platforms

1. Clone the repository:
   ```bash
   git clone https://github.com/your-username/jobs4u.git
   ```

2. Make sure JAVA_HOME is set to your JDK installation folder and Maven is on your system PATH.

### 4.2 Platform-Specific Installation

#### 4.2.1 Windows

1. Install the required tools:
  - Download and install Java JDK 11+ from [Oracle](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html)
  - Download and install Maven from [Apache Maven](https://maven.apache.org/download.cgi)
  - Download and install Git from [Git for Windows](https://gitforwindows.org/)
  - Install ANTLR using Maven (included in project dependencies)
  - Install a C/C++ compiler:
    ```
    // Option 1: MinGW
    choco install mingw
    
    // Option 2: Visual Studio Build Tools
    // Download and install from https://visualstudio.microsoft.com/downloads/
    ```

2. Set environment variables:
   ```
   setx JAVA_HOME "C:\Program Files\Java\jdk-11"
   setx PATH "%PATH%;%JAVA_HOME%\bin;C:\Program Files\Maven\bin"
   ```

3. Build the project:
   ```
   .\build-all.bat
   ```

#### 4.2.2 macOS

1. Install the required tools using Homebrew:
   ```bash
   # Install Homebrew if not already installed
   /bin/bash -c "$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/HEAD/install.sh)"
   
   # Install Java, Maven, and ANTLR
   brew install openjdk@11
   brew install maven
   brew install antlr
   
   # Install C compiler (included with macOS, but ensure Xcode command line tools are installed)
   xcode-select --install
   ```

2. Set environment variables:
   ```bash
   echo 'export JAVA_HOME=$(/usr/libexec/java_home -v 17)' >> ~/.zshrc
   echo 'export PATH=$JAVA_HOME/bin:$PATH' >> ~/.zshrc
   source ~/.zshrc
   ```

3. Build the project:
   ```bash
   chmod +x build-all.sh
   ./build-all.sh
   ```

#### 4.2.3 Linux (Ubuntu/Debian)

1. Install the required tools:
   ```bash
   # Update package list
   sudo apt update
   
   # Install Java, Maven, and build tools
   sudo apt install -y openjdk-11-jdk maven build-essential
   
   # Install ANTLR
   sudo apt install -y antlr
   ```

2. Set environment variables:
   ```bash
   echo 'export JAVA_HOME=/usr/lib/jvm/java-11-openjdk-amd64' >> ~/.bashrc
   echo 'export PATH=$JAVA_HOME/bin:$PATH' >> ~/.bashrc
   source ~/.bashrc
   ```

3. Build the project:
   ```bash
   chmod +x build-all.sh
   ./build-all.sh
   ```

### 4.3 Special Considerations for ANTLR and C Components

#### ANTLR Setup

The project uses ANTLR for language processing in job requirements and interviews. The build process automatically handles ANTLR, but if you need to work with grammar files directly:

1. Ensure ANTLR is installed:
   ```bash
   # Check ANTLR installation
   antlr -version
   ```

2. Manually compile grammar files (if needed):
   ```bash
   # Navigate to grammar directory
   cd src/main/antlr4

   # Generate ANTLR files
   antlr -no-listener -visitor JobRequirements.g4
   antlr -no-listener -visitor InterviewModel.g4
   ```

#### C Components with Mutexes and Threads

The Applications File Bot uses C components with mutexes and threads for file processing. To compile these components manually:

**Windows:**
```batch
cd scomp
nmake -f makefile
```

**macOS/Linux:**
```bash
cd scomp
make
```

## 5. Running the System

Once the project is built using `build-all.sh`, you can run the different applications. Each command below should be executed from the project root directory.

### 5.1 BackOffice Application

The BackOffice application runs standalone and does not require the Follow-Up Server.

```bash
cd jobs4u.backofficeApp
mvn dependency:copy-dependencies
java -cp "target/jobs4u.backofficeApp-0.1.0.jar:target/dependency/*" BackOffice
cd ..  # Return to project root
```

### 5.2 Follow-Up Server

The Follow-Up Server must be running before starting Customer or Candidate applications. It listens on port 1027 and handles authentication and notifications.

```bash
cd jobs4u.followUpServer
mvn dependency:copy-dependencies
java -cp "target/jobs4u.followUpServer-0.1.0.jar:target/dependency/*:../jobs4u.core/target/jobs4u.core-0.1.0.jar:../jobs4u.persistence/target/jobs4u.persistence-0.1.0.jar:../jobs4u.infrastructure.application/target/jobs4u.infrastructure.application-0.1.0.jar:../jobs4u.common/target/jobs4u.common-0.1.0.jar" FollowUpServerApp
cd ..  # Return to project root
```

**Important:** Keep this server running in a separate terminal while using Customer or Candidate applications.

### 5.3 Customer Application

**Requires Follow-Up Server to be running first.**

```bash
cd jobs4u.customerApp
mvn dependency:copy-dependencies
java -cp "target/jobs4u.customerApp-0.1.0.jar:target/dependency/*:../jobs4u.core/target/jobs4u.core-0.1.0.jar:../jobs4u.persistence/target/jobs4u.persistence-0.1.0.jar:../jobs4u.infrastructure.application/target/jobs4u.infrastructure.application-0.1.0.jar:../jobs4u.common/target/jobs4u.common-0.1.0.jar" CustomerApp
cd ..  # Return to project root
```

### 5.4 Candidate Application

**Requires Follow-Up Server to be running first.**

```bash
cd jobs4u.candidateApp
mvn dependency:copy-dependencies
java -cp "target/jobs4u.candidateApp-0.1.0.jar:target/dependency/*:../jobs4u.core/target/jobs4u.core-0.1.0.jar:../jobs4u.persistence/target/jobs4u.persistence-0.1.0.jar:../jobs4u.infrastructure.application/target/jobs4u.infrastructure.application-0.1.0.jar:../jobs4u.common/target/jobs4u.common-0.1.0.jar" Candidate
cd ..  # Return to project root
```

### 5.4 Bootstrap Mode

The BackOffice application can be run in bootstrap mode, which initializes the database with sample data. When prompted, enter 'y' to run in bootstrap mode.

## 6. System Configuration

### 6.1 Database Configuration

The system uses an H2 database engine for data persistence. The database connection settings can be modified in the `application.properties` file:

```
# Database Connection
database.url=jdbc:h2:./db/jobs4u
database.user=jobs4u
database.password=password
```

### 6.2 Server Configuration

The Follow Up Server settings can be configured in `server.properties`:

```
# Server Configuration
server.port=1027
server.timeout=30000
server.hostname=localhost
```

## 7. Running Tests

To run the automated tests:

```bash
mvn test
```

## 8. Project Documentation

Detailed project documentation is available in the `docs` folder of the repository. This includes:
- Sequence diagrams for user stories
- Domain model
- Technical documentation

You can generate PlantUML diagrams for documentation by running:

```bash
./generate-plantuml-diagrams.sh
```

## 9. Troubleshooting

### Common Issues

1. **Database connection errors**
  - Verify that the H2 database is properly configured in `application.properties`
  - Check that the database files are not locked by another process

2. **ANTLR grammar issues**
  - Regenerate ANTLR files using the commands in Section 4.3
  - Check for syntax errors in grammar files

3. **C component compilation failures**
  - Verify that the appropriate C compiler is installed
  - Check for missing libraries or dependencies

### Platform-Specific Issues

#### Windows
- If experiencing path-related issues, ensure system PATH variables are correctly set
- For C component compilation, verify Visual Studio Build Tools or MinGW is properly installed

#### macOS
- If experiencing permissions issues with scripts, run `chmod +x *.sh` to make them executable
- For ANTLR issues, verify installation with `brew info antlr`

#### Linux
- If experiencing shared library issues, run `sudo ldconfig` to update the shared library cache
- For C component compilation issues, verify that build-essential package is installed
