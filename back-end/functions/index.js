const functions = require('firebase-functions');

const json = [
    {
        "robotName": "Gary",
        "robotCode": "000001",
        "address":  "Somewhere",
        "time": "12:00",
        "trajectory": "Linear",
        "photoUrl": "http://chinarobotshop.com/wp-content/uploads/2017/12/Security-Patrol-Robot-3.png"
    },
    {
        "robotName": "Tony",
        "robotCode": "000002",
        "address":  "Somewhere",
        "time": "12:00",
        "trajectory": "Linear",
        "photoUrl": "http://chinarobotshop.com/wp-content/uploads/2017/12/Intelligence-Autonomy-patrol-Security-Patrol-Robot-c.png"
    },
    {
        "robotName": "Mary",
        "robotCode": "000003",
        "address":  "Somewhere",
        "time": "12:00",
        "trajectory": "Linear",
        "photoUrl": "http://chinarobotshop.com/wp-content/uploads/2017/12/Security-Patrol-Robot-3.png"
    },
    {
        "robotName": "Iona",
        "robotCode": "000004",
        "address":  "Somewhere",
        "time": "12:00",
        "trajectory": "Linear",
        "photoUrl": "http://chinarobotshop.com/wp-content/uploads/2017/12/Intelligence-Autonomy-patrol-Security-Patrol-Robot-c.png"
    },
    {
        "robotName": "Martin",
        "robotCode": "000005",
        "address":  "Somewhere",
        "time": "12:00",
        "trajectory": "Linear",
        "photoUrl": "http://chinarobotshop.com/wp-content/uploads/2017/12/Security-Patrol-Robot-3.png"
    },
    {
        "robotName": "Gary",
        "robotCode": "000006",
        "address":  "Somewhere",
        "time": "12:00",
        "trajectory": "Linear",
        "photoUrl": "http://chinarobotshop.com/wp-content/uploads/2017/12/Intelligence-Autonomy-patrol-Security-Patrol-Robot-c.png"
    },
    {
        "robotName": "Martin",
        "robotCode": "000007",
        "address":  "Somewhere",
        "time": "12:00",
        "trajectory": "Linear",
        "photoUrl": "http://chinarobotshop.com/wp-content/uploads/2017/12/Security-Patrol-Robot-3.png"
    },
    {
        "robotName": "Tony",
        "robotCode": "000008",
        "address":  "Somewhere",
        "time": "12:00",
        "trajectory": "Linear",
        "photoUrl": "http://chinarobotshop.com/wp-content/uploads/2017/12/Intelligence-Autonomy-patrol-Security-Patrol-Robot-c.png"
    },
    {
        "robotName": "Gary",
        "robotCode": "000009",
        "address":  "Somewhere",
        "time": "12:00",
        "trajectory": "Linear",
        "photoUrl": "http://chinarobotshop.com/wp-content/uploads/2017/12/Security-Patrol-Robot-3.png"
    },
    {
        "robotName": "Mary",
        "robotCode": "000010",
        "address":  "Somewhere",
        "time": "12:00",
        "trajectory": "Linear",
        "photoUrl": "http://chinarobotshop.com/wp-content/uploads/2017/12/Intelligence-Autonomy-patrol-Security-Patrol-Robot-c.png"
    },

];
exports.robot = functions.https.onRequest((request, response) => {response.send(json);
});