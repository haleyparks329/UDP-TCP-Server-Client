# UDP-TCP-Server-Client

## Purpose
This implements a client program (Client) and server program (Server) that communicate between themselves.

## Program Summary
This is a summary of the assignment given to me.

The client will transfer a file <filename> (specified in the command line) to the server over the network using internet sockets. This program uses a two-stage communication process. In the negotiation stage, the client and the server negotiate a random port <r_port> for later user through a fixed negotiation port <n_port> of the server. Each port is allowed to be between 1024 and 65535 (inclusive). Later, in the transaction stge, the client connects to the server through the selected random port for actual data transer.

## Client-Server Communication
The communication between client and server in this progam is done in two main stages.

1. Negotiation using TCP sockets - In this stage, the client creates a TCP connection with the server using <server_address> as the server address and <n_port> as the negotiation port on the server (where the server is known a priori to be listening). The client sends a request to get the random port on the server where the client will send the actual data. In this program, the client will send the characters 259 (as a single message) to initiate the negotiation with the server. Once the server recieves this request in the negotiation port, the server will reply back with a random port number <r_port> between 1024 and 65535 (inclusive) where it will be listening for the expected data. The server will then write to screen "Negotiation has been detected. Please select your special random port <r_port>." The client and server must close their sockets once the negotiation stage has completed.

2. Transaction using UDP sockets - The client creates a UDP socket to the server in <r_port> and sends the data. The file is sent over the channel in chunks of 4 characters of the file per UDP packet. After each packet is sent, the client waits for an acknowledgement from the server that comes in the form of the most recent transmitted payload in capital letters. These acknowledgements are output to the screen on the client side as one line per packet. Once the file has been sent and the last acknowledgement received, the client closes its ports and terminates automatically. On the server side, the sender recieves the data and writes it to a file using filename "output.txt". After each receieved packet, the server uses the UDP socket to send back an acknowledgement to the client. Once the last acknowledgement has been sent, the server closes all ports and terminates automatically.
