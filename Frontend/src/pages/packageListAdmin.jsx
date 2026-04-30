import React, { useEffect, useState } from 'react';
import packageService from '../services/package.service';
import { useKeycloak } from "@react-keycloak/web";
import { Table, TableBody, TableCell, TableContainer, TableHead, TableRow, Paper, Button, Typography } from '@mui/material';
import { useNavigate } from "react-router-dom";
import ListItemText from "@mui/material/ListItemText";
import ListItemIcon from "@mui/material/ListItemIcon";
import ListItemButton from "@mui/material/ListItemButton";
import AddCircleIcon from '@mui/icons-material/AddCircle';



const PackageListAdmin = () => {
    const navigate = useNavigate();
    const [packages, setPackages] = useState([]);
    const { keycloak } = useKeycloak();
// we take the roles from the realm
    const roles = keycloak.tokenParsed?.realm_access?.roles || [];
// boolean cons to read the roles
    const isAdmin = roles.includes('Admin');
    const isClient = roles.includes('Client');

    useEffect(() => {
        // call the service with the current token
        packageService.getAll(keycloak.token)
            .then(response => {
                setPackages(response.data);
            })
            .catch(error => {
                console.error("Error al buscar paquetes:", error);
            });
    }, [keycloak.token]);

    const handleDelete = (id) => {
        // Confirmation before delete
        if (window.confirm("¿Estás seguro de que deseas eliminar este paquete?")) {
            packageService.remove(id, keycloak.token)
                .then(() => {
                    setPackages(packages.filter(pkg => pkg.id !== id));
                    alert("Paquete eliminado correctamente");
                })
                .catch(error => {
                    console.error("Error al eliminar:", error);
                    alert("No se pudo eliminar el paquete. Revisa los permisos.");
                });
        }
    };
    const handleEdit = (pkg) => {
        navigate("/nuevoPaquete", {
            state: {
                editMode: true,
                packageData: pkg
            }
        });
    };

    return (
        <TableContainer component={Paper} sx={{ marginTop: 4, padding: 2 }}>
            <Typography variant="h4" gutterBottom>Paquetes Turísticos Disponibles</Typography>
            {isAdmin && (
                <ListItemButton onClick={() => navigate("/nuevoPaquete")}>
                    <ListItemIcon>
                        <AddCircleIcon />
                    </ListItemIcon>
                    <ListItemText primary="Crear Paquete" />
                </ListItemButton>
            )}
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
                                {isAdmin && (<>
                                        <Button
                                            variant="outlined"
                                            color="secondary"
                                            sx={{ mr: 1 }}
                                            onClick={() => handleEdit(pkg)}
                                        >
                                            Editar
                                        </Button>
                                        <Button
                                            variant="outlined"
                                            color="error"
                                            onClick={() => handleDelete(pkg.id)}
                                        >
                                            Eliminar
                                        </Button>
                                    </>
                                )}
                            </TableCell>
                        </TableRow>
                    ))}
                </TableBody>
            </Table>
        </TableContainer>
    );
};

export default PackageListAdmin;