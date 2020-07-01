gRPC simple demo
================
This example implements very simple demo of [gRPC](https://grpc.io/) communication
between simple server and client application. Once server is
started, client connects to the server and can run one of pre-defined
scenarios. Server always responds to particular client scenario.

Build and Install
-----------------
```gradle clean installDist distZip```  
```./build/install/grpc-demo/bin/grpc-demo --help```

Run Server
----------
```./build/install/grpc-demo/bin/grpc-demo --host <interfaceIp> --port 50051```


Run Client
----------
```./build/install/grpc-demo/bin/grpc-demo --host <serverHostName> --port 50051 <scenarioName> <scenarioParameters>```

Supported client scenarios
--------------------------

#### sayHello 
Client sends single hello message to server synchronously and ends. Server responds with hello reply message.  
```./build/install/grpc-demo/bin/grpc-demo --host localhost --port 50051 sayHello -m hi```

#### getDataSync
Client sends synchronously several warm-up messages and than number of data messages to server synchronously and ends. 
Server replies with sending back same message for every received message.
Example shows getDataSync with 500 warm-up messages and 1k data messages. Test is repeated 5 times, each time time and performance is printed 
on stdout when done.  
```./build/install/grpc-demo/bin/grpc-demo --host localhost --port 50051 getDataSync -w 500 -c 1000 -m hi -r 5```

#### getDataAsync
Client sends asynchronously several warm-up messages and than number of data messages to server asynchronously and ends.
Server replies with sending back same message for every received message.
Example shows getDataSync with 500 warm-up messages and 1k data messages. Test is repeated 5 times, each time time and performance is printed 
on stdout when done.  
```./build/install/grpc-demo/bin/grpc-demo --host localhost --port 50051 getDataAsync -w 500 -c 1000 -m hi -r 5```

Performance measurements
------------------------
Here are some performance measurements of for synchronous and asynchronous message sending.  
DataMessage exchanged between client and server in those tests looks like this: ```{ index: $i, message: "data" }```

| Scenario    | Parameters            | Server       | Client         | Network   | Result [msg/s] |
|-------------|-----------------------|--------------|----------------|:---------:|---------------:|
| getDataAsync|-c 100000 -r 20 -m data|i7-3632QM CPU | i7-3632QM CPU  | localhost | 39 000         |
| getDataSync |-c 100000 -r 20 -m data|i7-3632QM CPU | i7-3632QM CPU  | localhost |  6 400         |
| getDataAsync|-c 100000 -r 20 -m data|i7-3632QM CPU | Odroid C1 SBC  | 1 Gb/s    | 11 000         |
| getDataSync |-c 100000 -r 20 -m data|i7-3632QM CPU | Odroid C1 SBC  | 1 Gb/s    |    860         |
| getDataAsync|-c 100000 -r 20 -m data|i7-3632QM CPU | i7-4810MQ CPU  | 1 Gb/s    | 92 000         |
| getDataSync |-c 100000 -r 20 -m data|i7-3632QM CPU | i7-4810MQ CPU  | 1 Gb/s    |  1 600         |
| getDataAsync|-c 100000 -r 20 -m data|i7-3632QM CPU |     J1900 CPU  | 1 Gb/s    | 26 000         |
| getDataSync |-c 100000 -r 20 -m data|i7-3632QM CPU |     J1900 CPU  | 1 Gb/s    |  1 200         |
| getDataAsync|-c 100000 -r 20 -m data|i7-3632QM CPU | RaspberryPi B+ | 100 Mb/s  |  1 300         |
| getDataSync |-c 100000 -r 20 -m data|i7-3632QM CPU | RaspberryPi B+ | 100 Mb/s  |    200         |
| getDataAsync|-c 100000 -r 20 -m data|i7-3632QM CPU | RaspberryPi 0  | 100 Mb/s  |  2 000         |
| getDataSync |-c 100000 -r 20 -m data|i7-3632QM CPU | RaspberryPi 0  | 100 Mb/s  |    260         |
| getDataAsync|-c 100000 -r 20 -m data|i7-3632QM CPU | RaspberryPiW0  | wifi      |  1 900         |
| getDataSync |-c 100000 -r 20 -m data|i7-3632QM CPU | RaspberryPiW0  | wifi      |    150         |

