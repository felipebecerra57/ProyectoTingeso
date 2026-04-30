import httpClient from "../http-common";
import {data} from "react-router-dom";

const create = (data, token) => {
    return httpClient.post('/api/reservations/create', data, {
        headers: {
            Authorization: `Bearer ${token}`
        }
    });
};


export default { create };