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

  socket.emit('messageFromServer', 'Hello, client!');

  socket.on('initial', (user) => {
      console.log('Message from client:', user);
      connectedUsers[user.email] = {user:user,socket:socket};
  });

  socket.on('notify', (data) => {
    const { recipientSocketId, message } = data;
    const recipientSocket = io.sockets.sockets[recipientSocketId];

    if (recipientSocket) {
        recipientSocket.emit('messageFromServer', `Private message: ${message}`);
    } else {
        console.log('Recipient not found');
    }
  });
});

function notifyUser(socket, recipientSocketId, message) {
  socket.emit('notify', { message });
}



const url = 'mongodb://localhost:27017';
const dbName = 'chat';
let db;

let users = {};

app.get('/test', (req, res) => {
  console.log('test');
  res.json({ message: "success" });
});


// const { Kafka } = require('kafkajs');

// const kafka = new Kafka({
//     clientId: 'your-client-id',
//     brokers: ['localhost:9092'],
// });

// const consumer = kafka.consumer({ groupId: 'your-group-id' });

// const runConsumer = async () => {
//     await consumer.connect();
//     await consumer.subscribe({ topic: 'notification', fromBeginning: true });

//     await consumer.run({
//         eachMessage: async ({ topic, partition, message }) => {
//           const jsonMessage = message.value.toString();
          
//           const toMatch = jsonMessage.match(/to=([^,]+)/);
//           const toData = toMatch ? toMatch[1] : null;
          
//           console.log(toData);
//           console.log(connectedUsers);

//           notifyUser(connectedUsers[toData],'pull')
//         }
//     });
// };

// runConsumer().catch(console.error);




config["group.id"] = "node-group";
const consumer = new Kafka.KafkaConsumer(config, {"auto.offset.reset": "earliest" });
consumer.connect();
consumer.on("ready", () => {
    consumer.subscribe(["notification"]);
    consumer.consume();
}).on("data", (message) => {
    console.log("Consumed message", message);
    console.log(message.value.toString('utf-8'));
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



