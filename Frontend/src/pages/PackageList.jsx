import {Grid, Container, Typography, Chip} from '@mui/material';
import PackageCard from '../components/PackageCard';
import React, { useEffect, useState } from 'react';
import packageService from '../services/package.service';
import { useKeycloak } from "@react-keycloak/web";
import {useNavigate} from "react-router-dom";

const ClientPackages = () => {
    const navigate = useNavigate();
    const [packages, setPackages] = useState([]);
    const { keycloak } = useKeycloak();
// we take the roles from the realm
    const roles = keycloak.tokenParsed?.realm_access?.roles || [];
// boolean cons to read the roles
    const isAdmin = roles.includes('Admin');
    const isClient = roles.includes('Client');
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
        <Container sx={{ py: 5 }}>
            <Typography variant="h4" sx={{ fontWeight: 'bold', mb: 4, color: '#FFFFFF' }}>
                ¡Descubre las mejores ofertas!
            </Typography>

            <Grid container spacing={4}>
                {packages.map((pkg) => (
                    <Grid size={{ xs: 12, sm: 6, md: 4 }} key={pkg.id}>
                        <PackageCard pkg={pkg} onReserve={(id) => console.log("Reservando", id)} />
                    </Grid>
                ))}
            </Grid>
        </Container>
    );
};
export default ClientPackages;