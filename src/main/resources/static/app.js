const isDevelopment = window.location.hostname === 'localhost' ||
        window.location.hostname === '127.0.0.1';

    const API_URL = isDevelopment
        ? 'http://localhost:8080'
        : window.location.origin;

    async function getWeather() {
        const city = document.getElementById('city').value;

        try {
            const response = await fetch(`${API_URL}/api/v1/get-weather/get`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({city})
            });

            const data = await response.json();
            document.getElementById('result').textContent = data.result || JSON.stringify(data, null, 2);
        } catch (error) {
            console.error('Error:', error);
            document.getElementById('result').textContent = 'Error: ' + error;
        }
    }