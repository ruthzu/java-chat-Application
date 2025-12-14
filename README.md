# Java Chat App

Simple TCP socket-based chat using Java, from Chapter 4: Network Programming. Server uses `ServerSocket`, clients use `Socket`; supports multiple users with threading and message broadcasting.

## Features
- TCP chat
- Multi-client
- Broadcast msgs
- Exit via "exit"

## Requirements
- JDK 8+

## Setup & Run
1. Create folder, add `ChatServer.java` & `ChatClient.java`.
2. Compile: `javac *.java`
3. Run server: `java ChatServer`
4. Run clients: `java ChatClient` (multiple terminals)
5. For remote: Edit `SERVER_ADDRESS` in client to server IP.

Server on port 8000; type msgs to chat.
