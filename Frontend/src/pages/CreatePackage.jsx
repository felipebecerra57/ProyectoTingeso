import React, { useState } from 'react';
import { TextField, Button, Box, Typography, Paper } from '@mui/material';
import packageService from '../services/package.service';
import { useKeycloak } from "@react-keycloak/web";
import { useNavigate } from 'react-router-dom';
import { useLocation } from 'react-router-dom';


const CreatePackage = () => {
    const location = useLocation();
    console.log("Datos recibidos en location.state:", location.state);
    const isEdit = location.state?.editMode || false;
    const initialData = location.state?.packageData;
    const { keycloak } = useKeycloak();
    const navigate = useNavigate();

    const formatDate = (dateString) => {
        if (!dateString) return '';
        return dateString.split('T')[0];
    };
    const [formData, setFormData] = useState(isEdit ? {
        ...initialData,
        inicialDate: formatDate(initialData.inicialDate),
        finalDate: formatDate(initialData.finalDate)
    } : {
        name: '',
        destiny: '',
        price: 0,
        capacity: 0,
        inicialDate: '',
        finalDate: '',
        services: [],
        conditions: []
    });

    const handleSubmit = (e) => {
        e.preventDefault();
        const action = isEdit
            ? packageService.update(formData.id, formData, keycloak.token)
            : packageService.create(formData, keycloak.token);

        action.then(() => {
            alert(isEdit ? "¡Editado con éxito!" : "¡Creado con éxito!");
            navigate("/paquetes");
        });
    };

    const handleChange = (e) => {
        setFormData({ ...formData, [e.target.name]: e.target.value });
    };


    return (
        <Paper sx={{ p: 4, maxWidth: 500, mx: 'auto', mt: 5, justifyContent: 'flex-end' }}>
            <Typography variant="h5" mb={3}>
                {isEdit ? `Editando: ${initialData.name}` : "Crear Nuevo Paquete"}
                <Button
                    variant="outlined"
                    color="secondary"
                    onClick={() => navigate("/paquetes")} // O navigate(-1)
                >
                    Volver
                </Button>
            </Typography>

            <Box component="form" onSubmit={handleSubmit} sx={{ display: 'flex', flexDirection: 'column', gap: 2 }}>
                <TextField label="Nombre del Paquete"
                           name="name"
                           value={formData.name || ''}
                           onChange={handleChange}
                           required />
                <TextField label="Destino"
                           name="destiny"
                           value={formData.destiny || ''}
                           onChange={handleChange}
                           required />
                <TextField label="Precio"
                           name="price"
                           type="number"
                           value={formData.price|| ''}
                           onChange={handleChange} r
                           equired />
                <TextField
                    label="Fecha Inicio"
                    name="inicialDate"
                    type="date"
                    slotProps={{ inputLabel: { shrink: true } }}
                    value={formData.inicialDate || ''}
                    onChange={handleChange}
                    required
                />
                <TextField
                    label="Fecha Término"
                    name="finalDate"
                    type="date"
                    slotProps={{ inputLabel: { shrink: true } }}
                    value={formData.finalDate|| ''}
                    onChange={handleChange}
                    required
                />
                <TextField
                    label="Descripción"
                    name="description"
                    multiline
                    onChange={handleChange}
                    value={formData.description|| ''}
                />
                <TextField label="Capacidad"
                           name="capacity"
                           type="number"
                           onChange={handleChange}
                           value={formData.capacity || ''}
                           required />
                <Button
                    type="submit"
                    variant="contained"
                    color="success" >

                    Guardar Paquete</Button>
            </Box>
        </Paper>
    );
};

export default CreatePackage;