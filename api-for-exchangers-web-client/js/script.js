var stompClient = null;

function connect() {
    var socket = new SockJS('http://localhost:8080/websocket'); 
    stompClient = Stomp.over(socket);

    stompClient.connect({}, function (frame) {
        console.log('Connected: ' + frame);
        document.getElementById('response').innerHTML = 'Connected to server.';
    }, function (error) {
        console.error('Connection error: ', error);
        document.getElementById('response').innerHTML = 'Failed to connect to the server. Please try again later.';
    });
}


function subscribe() {
    var currencyPairs = document.getElementById('currencyPairInput').value.trim();

    if (!currencyPairs) {
        document.getElementById('response').innerHTML = 'Please enter at least one currency pair.';
        return;
    }

    var pairsArray = currencyPairs.split(',');

    if (stompClient !== null && stompClient.connected) {
        pairsArray.forEach(function(currencyPair) {
            var trimmedPair = currencyPair.trim();
            if (trimmedPair) {
                stompClient.subscribe('/topic/exchange-rate/' + trimmedPair, function (message) {
                    displayMessage(message.body, trimmedPair);
                });
                console.log('Subscribed to /topic/exchange-rate/' + trimmedPair);
            }
        });
        document.getElementById('response').innerHTML = 'Subscribed to ' + pairsArray.join(', ');
    } else {
        document.getElementById('response').innerHTML = 'Not connected to the server. Please connect before subscribing.';
    }
}


function displayMessage(messageBody, currencyPair) {
    var message = JSON.parse(messageBody);

    var responseDiv = document.getElementById('response');
    var messages = responseDiv.getElementsByClassName('message');
    
    if (messages.length >= 2) {
        responseDiv.removeChild(messages[0]); 
    }

    var messageHTML = `
        <div class="message">
            <p class="currency-pair">Currency Pair: <strong>${message.currencyPair}</strong></p>
            <p class="buy-rate">Buy Rate: <strong>${message.buyRate}</strong></p>
            <p class="sell-rate">Sell Rate: <strong>${message.sellRate}</strong></p>
            <p class="last-updated">Last Updated: <strong>${message.lastUpdated}</strong></p>
            <p class="action">Action: <strong>${message.action}</strong></p>
        </div>
    `;

    responseDiv.innerHTML += messageHTML;
}