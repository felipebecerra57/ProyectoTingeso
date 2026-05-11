import React, { useState } from 'react';
import { Box, Typography, TextField, Button, Grid, Divider, Paper, Alert } from '@mui/material';
import { useNavigate, useLocation } from 'react-router-dom';
import reservationService from "../services/reservation.service.js";
import { MenuItem } from '@mui/material';

const PaymentPage = () => {
    const navigate = useNavigate();
    const location = useLocation();
    const { simulacion, reservaId } = location.state?.data || location.state ||{};

    if (!reservaId || !simulacion) {
        return (
            <Box sx={{ p: 4, textAlign: 'center', color: 'white' }}>
                <Typography variant="h5">No se seleccionó ninguna reserva para pagar.</Typography>
                <Button onClick={() => navigate('/misReservas')} sx={{ mt: 2 }}>
                    Volver a mis reservas
                </Button>
            </Box>
        );
    }

    const [paymentData, setPaymentData] = useState({
        cardNumber: '',
        cvv: '',
        expiry: '',
        paymentMethod: 'VISA'
    });

    const [error, setError] = useState("");

    const handlePay = async () => {
        // clean of characters to validate the length
        const cleanCard = paymentData.cardNumber.replace(/-/g, '');
        const cleanExpiry = paymentData.expiry.replace(/\//g, '');
        // length validation
        if (cleanCard.length !== 16) {
            setError("El número de tarjeta debe tener 16 dígitos.");
            return;
        }
        if (paymentData.cvv.length !== 3) {
            setError("El CVV debe tener 3 dígitos.");
            return;
        }
        if (cleanExpiry.length !== 4) { // MMYY son 4 números
            setError("La fecha de vencimiento debe tener 4 dígitos (MM/YY).");
            return;
        }

        try {
            // call to the service to set the new status
            await reservationService.confirmPayment(reservaId);
            alert("¡Pago procesado con éxito!");
            navigate('/misReservas');
        } catch (error) {
            setError("Hubo un problema al procesar el pago en el servidor.");
        }
    };
    // every 4 digits we put a "-"
    const handleCardChange = (e) => {
        let value = e.target.value.replace(/\D/g, '');
        let formattedValue = value.replace(/(\d{4})(?=\d)/g, '$1-');
        setPaymentData({
            ...paymentData,
            cardNumber: formattedValue.slice(0, 19)
        });
    };
    const handleExpiryChange = (e) => {
        let value = e.target.value.replace(/\D/g, '');
        if (value.length > 4) return;
        let formattedValue = "";
        if (value.length > 2) {
            formattedValue = `${value.slice(0, 2)}/${value.slice(2, 4)}`;
        } else {
            formattedValue = value;
        }
        setPaymentData({
            ...paymentData,
            expiry: formattedValue
        });
    };

    return (
        <Box sx={{ p: 4, color: 'white', maxWidth: '900px', margin: 'auto' }}>
            <Typography variant="h4" sx={{ mb: 4, fontWeight: 'bold' }}>Checkout</Typography>
            <Button onClick={() => navigate('/misReservas')} sx={{ mt: 2 }}>
                Volver a mis reservas
            </Button>
            <Grid container spacing={4}>
                {/* input of data */}
                <Grid item xs={12} md={7}>
                    <Paper sx={{ p: 3, bgcolor: '#1e293b', color: 'white', borderRadius: 2 }}>
                        <Typography variant="h6" sx={{ mb: 3 }}>Información de la Tarjeta</Typography>

                        {error && <Alert severity="error" sx={{ mb: 2 }}>{error}</Alert>}

                        <TextField
                            select
                            fullWidth
                            label="Medio de Pago"
                            value={paymentData.paymentMethod || ""} // Asegura que no sea undefined
                            onChange={(e) => setPaymentData({...paymentData, paymentMethod: e.target.value})}
                            sx={{
                                mb: 3,
                                "& .MuiOutlinedInput-root": { color: "white" },
                                "& .MuiInputLabel-root": { color: "gray" },
                                "& .MuiSvgIcon-root": { color: "white" }
                            }}
                        >
                            <MenuItem value="" disabled>Seleccione un medio de pago</MenuItem>
                            <MenuItem value="DEBITO">Débito</MenuItem>
                            <MenuItem value="CREDITO">Crédito</MenuItem>
                            <MenuItem value="AMEX">American Express</MenuItem>
                        </TextField>

                        <TextField
                            placeholder="0000-0000-0000-0000"
                            label="Número de Tarjeta"
                            value={paymentData.cardNumber}
                            onChange={handleCardChange}
                            inputProps={{ inputMode: 'numeric', pattern: '[0-9]*' }}
                        />

                        <Grid container spacing={2}>
                            <Grid item xs={6}>
                                <TextField
                                    label="Vencimiento"
                                    placeholder="MM/YY"
                                    value={paymentData.expiry}
                                    onChange={handleExpiryChange}
                                    inputProps={{
                                        maxLength: 5, inputMode: 'numeric'}}
                                    sx={{ input: { color: 'white' }, label: { color: 'gray' } }}
                                />
                            </Grid>
                            <Grid item xs={6}>
                                <TextField
                                    fullWidth
                                    type="password"
                                    label="CVV"
                                    placeholder="123"
                                    value={paymentData.cvv}
                                    onChange={(e) => setPaymentData({...paymentData, cvv: e.target.value.replace(/\D/g, '').slice(0, 3)})}
                                    sx={{ input: { color: 'white' }, label: { color: 'gray' } }}
                                />
                            </Grid>
                        </Grid>

                        <Button
                            fullWidth
                            variant="contained"
                            color="success"
                            size="large"
                            onClick={handlePay}
                            sx={{ mt: 4, fontWeight: 'bold' }}
                        >
                            CONFIRMAR Y PAGAR ${simulacion?.finalPrice?.toLocaleString('es-CL')}
                        </Button>
                    </Paper>
                </Grid>

                {/* Resume side */}
                <Grid item xs={12} md={5}>
                    <Box sx={{ p: 2, borderLeft: '1px solid #334155' }}>
                        <Typography variant="h6" sx={{ mb: 2 }}>Resumen del Paquete</Typography>
                        <Typography variant="body1" color="gray">{simulacion?.turisticPackageName}</Typography>
                        <Typography variant="body2" color="gray">Pasajeros: {simulacion?.passengers}</Typography>

                        <Divider sx={{ my: 2, borderColor: '#334155' }} />

                        <Box sx={{ display: 'flex', justifyContent: 'space-between', mb: 1 }}>
                            <Typography>Precio Base:</Typography>
                            <Typography>${simulacion?.originalPrice?.toLocaleString('es-CL')}</Typography>
                        </Box>

                        {simulacion?.discountsName?.map((d, i) => (
                            <Typography key={i} variant="caption" color="#4caf50" sx={{ display: 'block' }}>
                                ✓ {d}
                            </Typography>
                        ))}

                        <Divider sx={{ my: 2, borderColor: '#334155' }} />

                        <Box sx={{ display: 'flex', justifyContent: 'space-between', color: '#4caf50' }}>
                            <Typography variant="h6">Total:</Typography>
                            <Typography variant="h6">${simulacion?.finalPrice?.toLocaleString('es-CL')}</Typography>
                        </Box>
                    </Box>
                </Grid>
            </Grid>
        </Box>
    );
};

export default PaymentPage;