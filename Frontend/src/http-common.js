import axios from "axios";
import keycloak from "./services/keycloak";

const travelAgencyBackendServer = import.meta.env.VITE_TRAVELAGENCY_BACKEND_SERVER;
const travelAgencyBackendPort = import.meta.env.VITE_TRAVELAGENCY_BACKEND_PORT;

console.log(travelAgencyBackendServer)
console.log(travelAgencyBackendPort)

const api = axios.create({
  baseURL: `http://${travelAgencyBackendServer}:${travelAgencyBackendPort}`,
  headers: {
    "Content-Type": "application/json"
  }
});

api.interceptors.request.use(async (config) => {
  if (keycloak.authenticated) {
    await keycloak.updateToken(30);
    config.headers.Authorization = `Bearer ${keycloak.token}`;
  }
  return config;
}, (error) => {
  return Promise.reject(error);
});

export default api;