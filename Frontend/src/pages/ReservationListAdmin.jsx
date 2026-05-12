import React, { useState, useEffect } from 'react';
import {
    Box, Typography, Table, TableBody, TableCell, TableContainer,
    TableHead, TableRow, Paper, TextField, MenuItem, Stack, Chip, Button, CircularProgress
} from '@mui/material';
import reservationService from '../services/reservation.service.js';
import { useKeycloak } from '@react-keycloak/web';

const ReservationListAdmin = () => {
    const { keycloak } = useKeycloak();
    const [reservations, setReservations] = useState([]);
    const [filteredReservations, setFilteredReservations] = useState([]);
    const [loading, setLoading] = useState(true);
    const [filters, setFilters] = useState({
        status: '',
        date: '',
        paymentMethod: ''
    });

    // 1. Cargar todas las reservas al montar el componente
    useEffect(() => {
        const fetchAllReservations = async () => {
            try {
                const data = await reservationService.getAllReservations(keycloak.token);

                // VALIDACIÓN CLAVE: Solo setear si es un Array
                if (Array.isArray(data)) {
                    setReservations(data);
                    setFilteredReservations(data);
                } else {
                    console.error("Los datos recibidos no son una lista:", data);
                    setReservations([]);
                }
            } catch (error) {
                console.error("Error cargando reservas:", error);
                setReservations([]); // En caso de error, dejamos la lista vacía
            } finally {
                setLoading(false);
            }
        };

        if (keycloak.token) fetchAllReservations();
    }, [keycloak.token]);

    // 2. Lógica de Filtrado (Se ejecuta cada vez que cambia un filtro o la lista original)
    useEffect(() => {
        let result = reservations;

        if (filters.status) {
            result = result.filter(res => res.status === filters.status);
        }

        if (filters.date) {
            // Asume que la fecha viene en formato YYYY-MM-DD
            result = result.filter(res => res.reservationDate.includes(filters.date));
        }

        if (filters.paymentMethod) {
            result = result.filter(res => res.paymentMethod === filters.paymentMethod);
        }

        setFilteredReservations(result);
    }, [filters, reservations]);

    const handleClearFilters = () => {
        setFilters({ status: '', date: '', paymentMethod: '' });
    };

    const formatDate = (dateString) => {
        if (!dateString) return "-";
        const date = new Date(dateString);
        // Usamos el formato chileno/español
        return date.toLocaleDateString('es-ES', {
            year: 'numeric',
            month: 'short', // 'short' pone el nombre del mes corto (may, jun)
            day: '2-digit'
        });
    };
    const handleDelete = async (id) => {
        if (window.confirm("¿Estás seguro de que deseas eliminar esta reserva? Se devolverá el cupo al paquete.")) {
            try {
                await reservationService.deleteReservation(id, keycloak.token);
                setReservations(prev => prev.filter(res => res.id !== id));
                alert("Reserva eliminada con éxito.");
            } catch (error) {
                console.error("Error al eliminar:", error);
                alert("No se pudo eliminar la reserva.");
            }
        }
    };

    if (loading) return <Box sx={{ display: 'flex', justifyContent: 'center', mt: 5 }}><CircularProgress /></Box>;

    return (
        <Box sx={{ p: 4, backgroundColor: '#121212', minHeight: '100vh', color: 'white' }}>
            <Typography variant="h4" sx={{ mb: 4, fontWeight: 'bold' }}>
                Panel Admin: Gestión de Reservas
            </Typography>

            {/* BARRA DE FILTROS */}
            <Stack direction={{ xs: 'column', md: 'row' }} spacing={2} sx={{ mb: 4 }}>
                <TextField
                    select
                    label="Estado de Pago"
                    value={filters.status}
                    onChange={(e) => setFilters({...filters, status: e.target.value})}
                    sx={{ minWidth: 200, "& .MuiOutlinedInput-root": { color: "white" }, "& .MuiInputLabel-root": { color: "gray" } }}
                >
                    <MenuItem value="">Todos los estados</MenuItem>
                    <MenuItem value="PENDING">Pendiente</MenuItem>
                    <MenuItem value="PAGADA">Pagada</MenuItem>
                </TextField>

                <TextField
                    select
                    label="Medio de Pago"
                    value={filters.paymentMethod}
                    onChange={(e) => setFilters({...filters, paymentMethod: e.target.value})}
                    sx={{ minWidth: 200, "& .MuiOutlinedInput-root": { color: "white" }, "& .MuiInputLabel-root": { color: "gray" } }}
                >
                    <MenuItem value="">Cualquier medio</MenuItem>
                    <MenuItem value="VISA">Visa</MenuItem>
                    <MenuItem value="MASTERCARD">MasterCard</MenuItem>
                    <MenuItem value="AMEX">Amex</MenuItem>
                </TextField>

                <TextField
                    type="date"
                    label="Fecha de Reserva"
                    InputLabelProps={{ shrink: true }}
                    value={filters.date}
                    onChange={(e) => setFilters({...filters, date: e.target.value})}
                    sx={{ minWidth: 200, "& .MuiOutlinedInput-root": { color: "white" }, "& .MuiInputLabel-root": { color: "gray" } }}
                />

                <Button variant="outlined" color="inherit" onClick={handleClearFilters}>
                    Limpiar Filtros
                </Button>
            </Stack>

            {/* TABLA DE RESERVAS */}
            <TableContainer component={Paper} sx={{ backgroundColor: '#1e1e1e', backgroundImage: 'none' }}>
                <Table>
                    <TableHead>
                        <TableRow>
                            <TableCell sx={{ color: '#bbb', fontWeight: 'bold' }}>ID</TableCell>
                            <TableCell sx={{ color: '#bbb', fontWeight: 'bold' }}>Cliente (User ID)</TableCell>
                            <TableCell sx={{ color: '#bbb', fontWeight: 'bold' }}>Paquete</TableCell>
                            <TableCell sx={{ color: '#bbb', fontWeight: 'bold' }}>Fecha</TableCell>
                            <TableCell sx={{ color: '#bbb', fontWeight: 'bold' }}>Monto</TableCell>
                            <TableCell sx={{ color: '#bbb', fontWeight: 'bold' }}>Medio Pago</TableCell>
                            <TableCell sx={{ color: '#bbb', fontWeight: 'bold' }}>Estado</TableCell>
                            <TableCell sx={{ color: '#bbb', fontWeight: 'bold' }}>Acciones</TableCell>
                        </TableRow>
                    </TableHead>
                    <TableBody>
                        {filteredReservations.length > 0 ? (
                            Array.isArray(filteredReservations) && filteredReservations.map((res) => (
                                <TableRow key={res.id} sx={{ '&:hover': { backgroundColor: '#2a2a2a' } }}>
                                    <TableCell sx={{ color: 'white' }}>{res.id}</TableCell>
                                    <TableCell sx={{ color: 'white' }}>{res.userId || 'N/A'}</TableCell>
                                    <TableCell sx={{ color: 'white' }}>{res.turisticPackage.name}</TableCell>
                                    <TableCell sx={{ color: 'white' }}>{formatDate(res.date)}</TableCell>
                                    <TableCell sx={{ color: 'white' }}>${res.finalAmount?.toLocaleString()}</TableCell>
                                    <TableCell sx={{ color: 'white' }}>{res.paymentMethod || '-'}</TableCell>
                                    <TableCell>
                                        <Chip
                                            label={res.status === 'PAGADA' ? 'PAGADO' : 'PENDIENTE'}
                                            color={res.status === 'PAID' ? 'success' : 'warning'}
                                            variant="outlined"
                                        />
                                    </TableCell>
                                    <TableCell>
                                        <Button
                                            variant="outlined"
                                            color="error"
                                            size="small"
                                            onClick={() => handleDelete(res.id)}
                                            sx={{ borderColor: '#f44336', color: '#f44336' }}
                                        >
                                            Eliminar
                                        </Button>
                                    </TableCell>
                                </TableRow>
                            ))
                        ) : (
                            <TableRow>
                                <TableCell colSpan={7} sx={{ color: 'white', textAlign: 'center', py: 3 }}>
                                    No se encontraron reservas con los filtros seleccionados.
                                </TableCell>
                            </TableRow>
                        )}
                    </TableBody>
                </Table>
            </TableContainer>
        </Box>
    );
};

export default ReservationListAdmin;