import React, { useEffect, useState } from 'react';
import packageService from '../services/package.service';
import { useKeycloak } from "@react-keycloak/web";
import { Table, TableBody, TableCell, TableContainer, TableHead, TableRow, Paper, Button, Typography } from '@mui/material';

const PackageList = () => {
    const [packages, setPackages] = useState([]);
    const { keycloak } = useKeycloak();

    useEffect(() => {
        // Llamamos al servicio usando el token de la sesión actual
        packageService.getAll(keycloak.token)
            .then(response => {
                setPackages(response.data);
            })
            .catch(error => {
                console.error("Error al buscar paquetes:", error);
            });
    }, [keycloak.token]);

    return (
        <TableContainer component={Paper} sx={{ marginTop: 4, padding: 2 }}>
            <Typography variant="h4" gutterBottom>Paquetes Turísticos Disponibles</Typography>
            <Table>
                <TableHead>
                    <TableRow>
                        <TableCell>Nombre</TableCell>
                        <TableCell>Destino</TableCell>
                        <TableCell align="right">Precio</TableCell>
                        <TableCell align="right">Cupos</TableCell>
                        <TableCell align="center">Acción</TableCell>
                    </TableRow>
                </TableHead>
                <TableBody>
                    {packages.map((pkg) => (
                        <TableRow key={pkg.id}>
                            <TableCell>{pkg.name}</TableCell>
                            <TableCell>{pkg.destiny}</TableCell>
                            <TableCell align="right">${pkg.price}</TableCell>
                            <TableCell align="right">{pkg.capacity}</TableCell>
                            <TableCell align="center">
                                <Button variant="contained" color="primary">Reservar</Button>
                            </TableCell>
                        </TableRow>
                    ))}
                </TableBody>
            </Table>
        </TableContainer>
    );
};

export default PackageList;