import React, {useState} from 'react';
import AppBar from '@mui/material/AppBar';
import Toolbar from '@mui/material/Toolbar';
import Typography from '@mui/material/Typography';
import Box from '@mui/material/Box';
import MenuIcon from "@mui/icons-material/Menu";
import {Button, IconButton} from "@mui/material";
import {useKeycloak} from "@react-keycloak/web";
//import Sidemenu from "./Sidemenu.jsx";


function Navbar() {
    const [open, setOpen] = useState(false);
    const { keycloak, initialized } = useKeycloak();

    const toggleDrawer = (open) => (event) => {
        setOpen(open);
    };
    return (
        <Box sx={{ flexGrow: 1 }}>
            <AppBar position="static" sx={{ backgroundColor: '#1976d2' }}>
                <Toolbar>
                    <IconButton
                        size="large"
                        edge="start"
                        color="inherit"
                        aria-label="menu"
                        sx={{ mr: 2 }}
                        onClick={toggleDrawer(true)}
                    >
                        <MenuIcon />
                    </IconButton>
                    <Typography variant="h6" component="div" sx={{ flexGrow: 1 }}>
                        TravelAgency : Vuela feliz
                    </Typography>

                    {initialized && (
                        <>
                            {keycloak.authenticated ? (
                                <>
                                    <Typography sx={{ mr: 2 }}>
                                        {keycloak.tokenParsed?.preferred_username ||
                                            keycloak.tokenParsed?.email}
                                    </Typography>
                                    <Button color="inherit" onClick={() => keycloak.logout()}>
                                        Logout
                                    </Button>
                                </>
                            ) : (
                                <Button color="inherit" onClick={() => keycloak.login()}>
                                    Login
                                </Button>
                            )}
                        </>
                    )}
                </Toolbar>
            </AppBar>
            
            <SideMenu open = {open} toggleDrawer={toggleDrawer}></SideMenu>

        </Box>
    );
}

export default Navbar;
