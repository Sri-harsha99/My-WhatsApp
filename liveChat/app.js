const express = require('express');
const http = require('http');
const socketIO = require('socket.io');
const { MongoClient } = require('mongodb');
const cors = require('cors');


const app = express();
app.use(cors());

const server = http.createServer(app);

const io = require("socket.io")(server, {
  cors: {
    origin: "http://localhost:4200",
    methods: ["GET", "POST"]
  }
});

const url = 'mongodb://localhost:27017';
const dbName = 'chat';
let db;

let users = {};

app.get('/test', (req, res) => {
  console.log('test');
  res.json({ message: "success" });
});


// app.get('/currentProducts', (req, res) => {
//   console.log('test');
//   res.jsonp([{ id:1,name:'Shoes',type:['clothing'],color:['blue'] },
//            { id:2,name:'Shirt',type:['clothing'],color:['black'] },
//            { id:3,name:'Laptop',type:['electronics']},
//            { id:4,name:'TV',type:['electronics']},
//            { id:5,name:'Laptop',type:['electronics']}]);
// });

// app.get('/archivedProducts', (req, res) => {
//   console.log('test2');
//   res.json([{ id:1,name:'Chair',type:['home'],color:['blue'] },
//            { id:2,name:'table',type:['home'],color:['orange'] },
//            { id:3,name:'scarf',type:['clothing']},
//            { id:4,name:'cabinet',type:['home']},
//            { id:5,name:'books',type:['education']}]);
// });


io.on('connection', socket => {
  console.log('User connected');

  socket.on('join', displayName => {
    users[socket.id] = displayName;
    console.log(users);
    io.emit('userList', users);

    socket.on('disconnect', () => {
      delete users[socket.id];
      io.emit('userList', users);
      console.log('User disconnected');
    });
  });

   socket.on('send-message', (payload) => {
    console.log(payload);
    socket.broadcast.emit('message', payload);
  });

  socket.on('chatMessage', async data => {
    console.log(data);
    if (!data.recipient) {
      io.emit('chatMessage', data);
    } else {
      if (recipientSocket) {
        io.to(data.recipient).emit('chatMessage', data);
        io.to(socket.id).emit('chatMessage', data);
      }
    }
  });
});

const PORT = process.env.PORT || 3000;
server.listen(PORT, () => {
  console.log(`Server listening on port ${PORT}`);
});



