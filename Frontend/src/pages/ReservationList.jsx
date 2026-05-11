import { useEffect, useState } from 'react';
import {
    Table,
    TableBody,
    TableCell,
    TableContainer,
    TableHead,
    TableRow,
    Paper,
    Typography,
    Chip,
    Button
} from '@mui/material';
import reservationService from "../services/reservation.service.js";
import keycloak from "../services/keycloak.js";
import {useNavigate} from "react-router-dom";

const ReservationList = () => {
    const [reservas, setReservas] = useState([]);
    const navigate = useNavigate();

    useEffect(() => {
        const fetchReservas = async () => {
            try {
                const data = await reservationService.getReservations(keycloak.token);
                setReservas(data);
            } catch (error) {
                console.error("Error al cargar reservas", error);
            }
        };
        fetchReservas();
    }, []);

    return (
        <TableContainer component={Paper} sx={{ bgcolor: '#1e1e1e', color: 'white', mt: 4 }}>
            <Typography variant="h4" sx={{ p: 2 }}>Mis Reservas</Typography>
            <Table sx={{ minWidth: 650 }}>
                <TableHead>
                    <TableRow>
                        <TableCell sx={{ color: 'gray' }}>Paquete</TableCell>
                        <TableCell sx={{ color: 'gray' }}>Destino</TableCell>
                        <TableCell sx={{ color: 'gray' }}>Fecha</TableCell>
                        <TableCell sx={{ color: 'gray' }}>Pasajeros</TableCell>
                        <TableCell sx={{ color: 'gray' }}>Estado</TableCell>
                        <TableCell sx={{ color: 'gray' }}>Descuentos</TableCell>
                        <TableCell sx={{ color: 'gray' }}>Precio</TableCell>
                    </TableRow>
                </TableHead>
                <TableBody>
                    {reservas.map((res) => (
                        <TableRow key={res.idReservation}>
                            <TableCell sx={{ color: 'white' }}>{res.turisticPackage}</TableCell>
                            <TableCell sx={{ color: 'white' }}>{res.destiny}</TableCell>
                            <TableCell sx={{ color: 'white' }}>{new Date(res.date).toLocaleDateString()}</TableCell>
                            <TableCell sx={{ color: 'white' }}>{res.passengers}</TableCell>
                            <TableCell sx={{ color: 'white' }}>{res.status}</TableCell>
                            <TableCell>
                                {res.discounts?.map((d, i) => (
                                    <Chip key={i} label={d.name} size="small" color="success" sx={{ mr: 0.5 }} />
                                ))}
                            </TableCell>
                            <TableCell>${res.finalPrice?.toLocaleString('es-CL')}</TableCell>
                            {/* if no está pagada, mostrar boton  */}
                            <TableCell>
                                {res.status === 'PENDING' && (
                                    <Button
                                        variant="contained"
                                        color="primary"
                                        size="small"
                                        onClick={() => {
                                            navigate('/pagar', {
                                                state: {
                                                    reservaId: res.idReservation,
                                                    simulacion: {
                                                        finalPrice: res.finalPrice,
                                                        originalPrice: res.originalPrice,
                                                        turisticPackageName: res.turisticPackage,
                                                        passengers: res.passengers,
                                                        discountsName: res.discountsName
                                                    }
                                                }
                                            });
                                        }}
                                    >
                                        Pagar
                                    </Button>

                                )}
                                <Button
                                    variant="contained"
                                    color="error"
                                    size="small"
                                    onClick={() => {

                                        navigate('/pagar', {
                                            state: {
                                                reservaId: res.idReservation,
                                                simulacion: {
                                                    finalPrice: res.finalPrice,
                                                    originalPrice: res.originalPrice,
                                                    turisticPackageName: res.turisticPackage,
                                                    passengers: res.passengers,
                                                    discountsName: res.discountsName
                                                }
                                            }
                                        });
                                    }}
                                >
                                    Eliminar
                                </Button>
                                {res.status === 'PAID' && (
                                    <Typography variant="body2" color="success.main">Pagado</Typography>
                                )}
                            </TableCell>
                        </TableRow>
                    ))}
                </TableBody>
            </Table>
        </TableContainer>
    );
};
export default ReservationList