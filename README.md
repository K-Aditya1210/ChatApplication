Basic Chat Application in Java

This project is a simple chat application built using Java that allows two users to communicate over a network. The application follows a client-server architecture, where one instance acts as the server and the other as the client.

Key Features:
Client-Server Model: The application consists of two separate programs—a server and a client. The server listens for incoming connections, while the client connects to the server to initiate the chat.
Text-Based Communication: Users can send and receive text messages in real-time.
Network Configuration: The application can be run on the same computer or across different computers connected to the same network.
Port Customization: The default port is set to 7777, but it can be changed if needed to avoid conflicts.
Connection Termination: Typing "exit" in the chat will terminate the connection and close the chat session.
How It Works:
Server Setup: First, run the server application. It will wait for a client to connect.
Client Connection: Next, run the client application, which will connect to the server using the server's IP address and port number.
Chat Session: Once connected, both users can exchange text messages.
Ending the Chat: To end the chat, simply type "exit" and the connection will be closed.
This basic chat application is a great way to understand the fundamentals of socket programming in Java, including establishing connections, sending/receiving data, and handling network communication between different devices.
