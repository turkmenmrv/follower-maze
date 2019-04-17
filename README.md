## EventProcessor - Design Overview

The project is written via Java 8 and builded via Maven. No external libraries is used.

For the diagram representing system design, please see **DesignDiagram.jpg** under project path.

There are two main controllers in the system, running seperate threads:

1. EventSourceController

2. UserClientController

####EventSourceController:

Responsible from handling "*EventSource*"s 
It starts **EventSocketServer**, and after accepting the *EventSource* connection, reads incoming messages from socket.
Read process is handled via **EventSourceHandler**. EventSourceHandler reads from socket via chunks (rather than reading line by line)
The size of the chunk is 3000 chars by default, but it can be changed via environment variable **"readBufferSize"**. 
Then the message is parsed and inserted into **MessageHeap**(*). 
Then the messages in MessageHeap are polled (in order by nature) and 2 operations are done for each massage:
1) Update **FollowerCache**(**) if the massege type is *Follow* or *Unfollow*
2) Insert message to **UserMessageQueue**(***) for proper *UserClients*

(*) **MessageHeap** is actually a *PriorityQueue* which enables polling next message ordered.
There is a control mechanism when messages are polled from MessageHeap - which ensures that number of messages in MessageHeap must be at least *"maxEventSourceBatchSize"*
otherwise no poll is done. This control guarantees to get and process messages in the correct order.

(**) **FollowerCache** is a cache (uses *ConcurrentMap*) storing Ids of *UserClients* and the list of its followers

(***) **UserMessageQueue** is cache storing *Messages* of each *UserClient* (which has connected) in a seperate queue. The messages are pushed in the correct order.

####UserClientController:

Responsible from handling "*UserClient*"s
It starts **UserSocketServer** and accepts *UserClient* connections.
Then listens for **UserMessageQueue** on a seperate thread for each *UserClient* and writes its messages via **UserClientHandler**

####Automated Tests
Unit tests are introduced which can be run via
```
$ mvn clean test
```


####How To Run
In order to create fat jar:
```
$ mvn clean package
```

Then, run the shell script **run.sh** via:

```
$ ./run.sh
```


