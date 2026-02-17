const isDevelopment = window.location.hostname === 'localhost' ||
    window.location.hostname === '127.0.0.1';

const API_URL = isDevelopment
    ? 'http://localhost:8080'
    : 'https://api.myapp.com';

async function getWhether() {
    const city = document.getElementById('city-input').value;

    // try {
    //     const response = await fetch(`${API_URL}/api/math/add`, {
    //         method: 'POST',
    //         headers: {
    //             'Content-Type': 'application/json',
    //         },
    //         body: JSON.stringify({ num1, num2 })
    //     });

    //     const data = await response.json();
    //     document.getElementById('result').textContent =
    //         `Result: ${data.result}`;
    // } catch (error) {
    //     console.error('Error:', error);
    // }
}