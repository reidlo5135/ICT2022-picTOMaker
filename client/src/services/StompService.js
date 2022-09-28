import SockJS from "sockjs-client";
import Stomp from 'stompjs';

const sock = new SockJS("http://localhost:8080/ws/");
const ws = Stomp.over(sock);

export const wsConnect = () => {
    ws.connect({});
};

export const wsCommunicationWithConnection = (requestUrl, requestHeaders, requestData, responseUrl, action) => {
    ws.connect({}, () => {
        ws.send(requestUrl, requestHeaders, JSON.stringify(requestData));
        ws.subscribe(responseUrl,
            (data) => {
                const res = JSON.parse(data.body);
                console.log("Stomp ws msg : ", res);
                action(res);
            }
        );
    })
};

export const wsCommunication = (requestUrl, requestHeaders, requestData, responseUrl, action) => {
    ws.send(requestUrl, requestHeaders, JSON.stringify(requestData));
    ws.subscribe(responseUrl,
        (data) => {
            const res = JSON.parse(data.body);
            console.log("Stomp ws msg : ", res);
            action(res);
        }
    );
}