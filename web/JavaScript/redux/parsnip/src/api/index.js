import axios from "axios";

const API_BASE_URL = "http://localhost:8080";

// 封装http client
const client = axios.create({
    baseURL: API_BASE_URL,
    headers: {
        'Content-Type': 'application/json',
    },
});

export function fetchTasks() {
    return client.get("/tasks");
}