var java = require("java");
var net = require('net');
var http = require("http");

var express = require('express');
var app = express();
var socketIO = require('socket.io');

java.classpath.push("./java/bin");
var itg = java.newInstanceSync("pssix.InteractiveTextGenerator", 2, "java/src/pssix/taytay.txt");

/*
itg.print("It feels ", 40, function(err, result){
//itg.print(40, function(err, result){
    console.log(result);
});
*/
 
var server = http.Server(app).listen(8080);

var io = socketIO.listen(server);

app.get('/', function (req, res) {
  res.sendFile(__dirname + '/index.html');
});


io.on("connection", function (socket) {
	socket.emit("message", "Hi, I'm TayTay.ai! ");
	socket.on("teach", function(message){
  	console.log(message);
  	itg.add(message,function(error, result){
    	socket.emit("message", result);
  	});
	});
	socket.on("prompt", function(message){
  	itg.print(message, 40, function(error, result){
    	socket.emit("message", result);
  	});
	});
});