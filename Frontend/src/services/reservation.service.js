import httpClient from "../http-common";

const create = (data, token) => {
    return httpClient.post('/api/reservations/create', data, {
        headers: {
            Authorization: `Bearer ${token}`
        }
    });
};
const simulate = async (reservationData, token) => {
    const response = await httpClient.post('/api/reservations/simulate', reservationData, {
        headers: {
            Authorization: `Bearer ${token}`
        }
    });
    return response.data;
};
const getReservations = async (token) => {
    const response = await httpClient.get('/api/reservations/myReservations', {
        headers: {
            Authorization: `Bearer ${token}`
        }
    });
    return response.data;
};
const getAllReservations = async (token) => {
    const response = await httpClient.get('/api/reservations/allReservations', {
        headers: {
            Authorization: `Bearer ${token}`
        }
    });
    return response.data;
};

const confirmPayment = async (reservationId, method, token) => {
    const response = await httpClient.put(`/api/reservations/${reservationId}/pay`, {paymentMethod: method}, {
        headers: {
            Authorization: `Bearer ${token}`,
            "Content-Type": "application/json"
        }
    });
    return response.data;
};
const deleteReservation = async (reservationId, token) => {
    const response = await httpClient.delete(`/api/reservations/${reservationId}`, {
        headers: {
            Authorization: `Bearer ${token}`,
        }
    });
    return response.data;
};

export default { create,simulate,getReservations,getAllReservations,confirmPayment,deleteReservation };