const express = require('express');
const http = require('http');
const socketIO = require('socket.io');
const { MongoClient } = require('mongodb');
const cors = require('cors');
// const { Kafka } = require('kafkajs');
const Kafka = require("node-rdkafka");
const config = require("./kafka-prop.json");

const app = express();
app.use(cors({ origin: ['http://127.0.0.1:5173','http://localhost:5173','https://whatsapp-harsha.onrender.com'] }));

const server = http.createServer(app);

const io = require('socket.io')(server, {
  cors: {
    origin: ["http://127.0.0.1:5173","http://localhost:5173","https://whatsapp-harsha.onrender.com"],
    methods: ["GET", "POST"],
    allowedHeaders: ['Content-Type', 'Authorization'],
  }
});

const connectedUsers = {};

io.on('connection', (socket) => {
  console.log('Client connected');

  socket.emit('messageFromServer', 'Hello, client,!'+socket._id);

  socket.on('initial', (user) => {
      console.log('Message from client:', user);
      user = JSON.parse(user);
      connectedUsers[user.email] = {user:user,socket:socket};
  });

  socket.on('notify', (data) => {
    const { recipientSocketId, message } = data;
    const recipientSocket = io.sockets.sockets[recipientSocketId];

    if (recipientSocket) {
        console.log('byr')
        recipientSocket.emit('messageFromServer', `Private message: ${message}`);
    } else {
        console.log('Recipient not found');
    }
  });
});

let users = {};

app.get('/test', (req, res) => {
  console.log('test');
  res.json({ message: "success" });
});

config["group.id"] = "node-group";
const consumer = new Kafka.KafkaConsumer(config, {"auto.offset.reset": "earliest" });
consumer.connect();
consumer.on("ready", () => {
    consumer.subscribe(["notification"]);
    consumer.consume();
}).on("data", (message) => {
    console.log("Consumed message", message);
    console.log(message.value.toString('utf-8'));
    let kafkaMessage = message.value.toString('utf-8');

    const keyValuePairs = kafkaMessage.match(/(\w+)=([\w@.\s]+|"[^"]*")/g);

    // Construct an object from the key-value pairs
    const messageObject = {};
    keyValuePairs.forEach((pair) => {
      const [key, value] = pair.split('=');
      messageObject[key] = value.replace(/^"(.*)"$/, '$1');
    });
    
    const { _id, from, to, msg, time, edited, type, fromName } = messageObject;
    console.log(to,from,msg)
    console.log(connectedUsers)
    if (connectedUsers[to]) {
      const userSocket = connectedUsers[to].socket;
      
      userSocket.emit('notify', { msg,to });
    } else {
      console.log('User not connected');
    }

});


















// io.on('connection', socket => {
//   console.log('User connected');

//   socket.on('join', displayName => {
//     users[socket.id] = displayName;
//     console.log(users);
//     io.emit('userList', users);

//     socket.on('disconnect', () => {
//       delete users[socket.id];
//       io.emit('userList', users);
//       console.log('User disconnected');
//     });
//   });

//    socket.on('send-message', (payload) => {
//     console.log(payload);
//     socket.broadcast.emit('message', payload);
//   });

//   socket.on('chatMessage', async data => {
//     console.log(data);
//     if (!data.recipient) {
//       io.emit('chatMessage', data);
//     } else {
//       if (recipientSocket) {
//         io.to(data.recipient).emit('chatMessage', data);
//         io.to(socket.id).emit('chatMessage', data);
//       }
//     }
//   });
// });

const PORT = process.env.PORT || 3000;
server.listen(PORT, () => {
  console.log(`Server listening on port ${PORT}`);
});



