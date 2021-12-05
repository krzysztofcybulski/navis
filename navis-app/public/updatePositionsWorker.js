const ws = new WebSocket('ws://localhost:8080/ships/live');

let queue = [];

ws.onmessage = evt => {
    const message = JSON.parse(evt.data);
    if (message.position) {
        queue.push({
            mmsi: message.mmsi.raw,
            longitude: message.position.longitude,
            latitude: message.position.latitude
        });
    }
};

setInterval(() => {
    if (queue.length > 200) {
        console.log(`Updating ${queue.length} positions`);
        postMessage(queue);
        queue = [];
    }
}, 1000);
