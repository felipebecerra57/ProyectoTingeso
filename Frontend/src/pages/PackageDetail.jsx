import {
    Grid,
    Tab,
    Box,
    Typography,
    Divider,
    List,
    ListItem,
    ListItemText,
    ListItemIcon,
    Tabs,
    Button, TextField, Paper
} from '@mui/material';
import CalendarMonthIcon from '@mui/icons-material/CalendarMonth';
import LocationOnIcon from '@mui/icons-material/LocationOn';
import SellIcon from '@mui/icons-material/Sell';
import GroupsIcon from '@mui/icons-material/Groups';
import {useEffect, useState} from "react";
import {useLocation} from "react-router-dom";
import { useNavigate } from 'react-router-dom';
import keycloak from "../services/keycloak.js";
import reservationService from "../services/reservation.service.js";

function CustomTabPanel(props) {
    const { children, value, index, ...other } = props;

    return (
        <div
            role="tabpanel"
            hidden={value !== index}
            id={`simple-tabpanel-${index}`}
            aria-labelledby={`simple-tab-${index}`}
            {...other}
        >
            {value === index && (
                <Box sx={{ p: 3 }}>
                    {children}
                </Box>
            )}
        </div>
    );
}
const formatDate = (dateString) => {
    if (!dateString) return "No definida";
    const date = new Date(dateString);
    return date.toLocaleDateString('es-CL');
};

function a11yProps(index) {
    return {
        id: `simple-tab-${index}`,
        'aria-controls': `simple-tabpanel-${index}`,
    };
}

const PackageDetail = () => {
    const [value, setValue] = useState(0);
    const location = useLocation();
    const navigate = useNavigate();
    const initialData = location.state?.initialData;
    const handleChange = (event, newValue) => {
        setValue(newValue);
    };

    const [reservationData, setReservationData] = useState({
        date: new Date().toISOString().split('T')[0], // today's date
        passengers: 1,
        turisticPackage: initialData.id,
        client: keycloak.tokenParsed.sub,
        status: 'PENDING',
        paid: false
    });

    const [simulacion, setSimulacion] = useState({ original: 0, final: 0, descuentos: [] });

    useEffect(() => {
        const fetchSimulacion = async () => {
            if (reservationData.passengers > 0) {
                try {
                    const response = await reservationService.simulate(reservationData, keycloak.token);
                    setSimulacion({
                        original: response.originalPrice,
                        final: response.finalPrice,
                        descuentos: response.discounts
                    });
                } catch (error) {
                    console.error("Error en simulación", error);
                }
            }
        };
        fetchSimulacion();
    }, [reservationData.passengers]);

    const handleReservaSubmit = async (e) => {
        e.preventDefault();

        try {
            await reservationService.create(reservationData, keycloak.token);
            alert("¡Reserva realizada con éxito!");
            // navigate("/mis-reservas");
        } catch (error) {
            console.error("Error al reservar:", error); // POR MIENTRAS
            alert("Hubo un error al procesar tu reserva.");
        }
    };

    const tabStyle = {
        color: 'white',
        '&.Mui-selected': {
            color: '#fce4ec',
        },
    };
    return (
        <Box sx={{ width: '100%' }}>
            <Box sx={{ borderBottom: 1, borderColor: '#fce4ec' }}>
                <Tabs value={value} onChange={handleChange} aria-label="detalles del paquete">
                    <Tab label="Información del paquete" {...a11yProps(0)} sx={tabStyle} />
                    <Tab label="Reservar" {...a11yProps(1)} sx={tabStyle} />
                </Tabs>
            </Box>

            <CustomTabPanel value={value} index={0}>
                {/* ------- DETAILS OF PACKAGE --------*/}
                <Grid container spacing={3} sx={{ color: 'white' }}>
                    {/* Tittle and destiny*/}
                    <Grid item xs={12}>
                        <Typography variant="h4" sx={{ fontWeight: 'bold', color: '#fce4ec' }}>
                            {initialData.name}
                        </Typography>
                        <Box sx={{ display: 'flex', alignItems: 'center', mt: 1 }}>
                            <LocationOnIcon sx={{ color: '#fce4ec', mr: 1 }} />
                            <Typography variant="h4">{initialData.destiny}</Typography>
                        </Box>
                    </Grid>

                    <Grid item xs={12}><Divider sx={{ bgcolor: 'rgba(255,255,255,0.2)' }} /></Grid>

                    {/* Attributes */}
                    <Grid item xs={12} sm={6} md={3}>
                        <Box sx={{ textAlign: 'center' }}>
                            <SellIcon color="primary" />
                            <Typography variant="subtitle2" sx={{ opacity: 0.7 }}>Precio</Typography>
                            <Typography variant="h6">${initialData.price.toLocaleString('es-CL')}</Typography>
                        </Box>
                    </Grid>

                    <Grid item xs={12} sm={6} md={3}>
                        <Box sx={{ textAlign: 'center' }}>
                            <CalendarMonthIcon color="primary" />
                            <Typography variant="subtitle2" sx={{ opacity: 0.7 }}>Fecha Inicio</Typography>
                            <Typography variant="h6">{formatDate(initialData.inicialDate)}</Typography>
                        </Box>
                    </Grid>

                    <Grid item xs={12} sm={6} md={3}>
                        <Box sx={{ textAlign: 'center' }}>
                            <CalendarMonthIcon color="primary" />
                            <Typography variant="subtitle2" sx={{ opacity: 0.7 }}>Fecha Término</Typography>
                            <Typography variant="h6">{formatDate(initialData.finalDate)}</Typography>
                        </Box>
                    </Grid>

                    <Grid item xs={12} sm={6} md={3}>
                        <Box sx={{ textAlign: 'center' }}>
                            <GroupsIcon color="primary" />
                            <Typography variant="subtitle2" sx={{ opacity: 0.7 }}>Cupos Disp.</Typography>
                            <Typography variant="h6">{initialData.capacity}</Typography>
                        </Box>
                    </Grid>

                    <Grid item xs={12}><Divider sx={{ bgcolor: 'rgba(255,255,255,0.2)' }} /></Grid>

                    {/* Description*/}
                    <Grid item xs={12}>
                        <Typography variant="h6" gutterBottom color="#fce4ec">Descripción del Viaje</Typography>
                        <Typography variant="body1" sx={{ lineHeight: 1.8 }}>
                            {initialData.description || "Sin descripción disponible para este paquete."}
                        </Typography>
                    </Grid>

                    {/* Listas de Servicios y Condiciones */}
                    <Grid item xs={12} md={6}>
                        <Typography variant="h6" gutterBottom color="#fce4ec">Servicios Incluidos</Typography>
                        <List>
                            {initialData.services?.map((service, i) => (
                                <ListItem key={i} disablePadding>
                                    <ListItemText primary={service} />
                                </ListItem>
                            )) || "No especificado"}
                        </List>
                    </Grid>
                </Grid>
            </CustomTabPanel>
            {/* ------- RESERVATION FORM --------*/}
            <CustomTabPanel value={value} index={1}>
                <CustomTabPanel value={value} index={1}>
                    <Paper elevation={3} sx={{ p: 4, maxWidth: 600, mx: 'auto', bgcolor: '#1e1e1e', color: 'white' }}>
                        <Typography variant="h5" gutterBottom sx={{ color: '#fce4ec' }}>
                            Confirmar Reserva para: {initialData.name}
                        </Typography>

                        <Typography variant="h6" gutterBottom sx={{ color: '#fce4ec' }}>
                            Con destino: {initialData.destiny}
                        </Typography>

                        <Box component="form" onSubmit={handleReservaSubmit} sx={{ mt: 2 }}>
                            <Grid container spacing={3}>
                                {/* Fecha de la Reserva */}
                                <Grid item xs={12}>
                                    <TextField
                                        label="Fecha de Viaje"
                                        type="date"
                                        fullWidth
                                        value={reservationData.date}
                                        InputLabelProps={{ shrink: true }}
                                        disabled
                                    />
                                </Grid>

                                {/* Passengers */}
                                <Grid item xs={12}>
                                    <TextField
                                        label="Cantidad de Pasajeros"
                                        type="number"
                                        fullWidth
                                        value={reservationData.passengers}
                                        onChange={(e) => setReservationData({...reservationData, passengers: e.target.value})}
                                        InputProps={{ inputProps: { min: 1, max: initialData.capacity } }}
                                    />
                                    <Typography variant="caption" sx={{ opacity: 0.6 }}>
                                        Cupos disponibles: {initialData.capacity}
                                    </Typography>
                                </Grid>

                                {/* Initial Price */}
                                <Grid item xs={12}>
                                    <Typography variant="h6">
                                        Total a pagar: ${(initialData.price * reservationData.passengers).toLocaleString('es-CL')}
                                    </Typography>
                                </Grid>

                                {/* -------Simulation box*/}
                                <Box sx={{ mt: 3, p: 2, bgcolor: 'rgba(255,255,255,0.05)', borderRadius: 2 }}>
                                    <Typography variant="body1" sx={{ display: 'flex', justifyContent: 'space-between' }}>
                                        Precio Original: <span>${simulacion.original.toLocaleString('es-CL')}</span>
                                    </Typography>

                                    {simulacion.descuentos.map((desc, index) => (
                                        <Typography key={index} variant="body2" color="success.main" sx={{ display: 'flex', justifyContent: 'space-between' }}>
                                            {desc}: <span>- ${(simulacion.original * 0.1).toLocaleString('es-CL')}</span>
                                        </Typography>
                                    ))}

                                    <Divider sx={{ my: 1, bgcolor: 'white' }} />

                                    <Typography variant="h5" sx={{ display: 'flex', justifyContent: 'space-between', color: '#4caf50' }}>
                                        Total Final: <span>${simulacion.final.toLocaleString('es-CL')}</span>
                                    </Typography>
                                </Box>

                                <Grid item xs={12}>
                                    <Button
                                        type="submit"
                                        variant="contained"
                                        fullWidth
                                        sx={{ bgcolor: '#4caf50', '&:hover': { bgcolor: '#45a049' } }}
                                    >
                                        CONFIRMAR RESERVA
                                    </Button>
                                </Grid>
                            </Grid>
                        </Box>
                    </Paper>
                </CustomTabPanel>
            </CustomTabPanel>

        </Box>
    );
};

export default PackageDetail;