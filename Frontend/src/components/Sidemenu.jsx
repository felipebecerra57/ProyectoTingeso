import * as React from "react";
import Box from "@mui/material/Box";
import Drawer from "@mui/material/Drawer";
import List from "@mui/material/List";
import Divider from "@mui/material/Divider";
import ListItemButton from "@mui/material/ListItemButton";
import ListItemIcon from "@mui/material/ListItemIcon";
import ListItemText from "@mui/material/ListItemText";
import PeopleAltIcon from "@mui/icons-material/PeopleAlt";
import PaidIcon from "@mui/icons-material/Paid";
import CalculateIcon from "@mui/icons-material/Calculate";
import AnalyticsIcon from "@mui/icons-material/Analytics";
import DiscountIcon from "@mui/icons-material/Discount";
import HailIcon from "@mui/icons-material/Hail";
import MedicationLiquidIcon from "@mui/icons-material/MedicationLiquid";
import MoreTimeIcon from "@mui/icons-material/MoreTime";
import CalendarMonthIcon from "@mui/icons-material/CalendarMonth"
import HomeIcon from "@mui/icons-material/Home";
import AirplaneTicketIcon from '@mui/icons-material/AirplaneTicket';
import { useNavigate } from "react-router-dom";
import {useKeycloak} from "@react-keycloak/web";

export default function Sidemenu({ open, toggleDrawer }) {
    const navigate = useNavigate();
    const { keycloak } = useKeycloak();

    const roles = keycloak.tokenParsed?.realm_access?.roles || [];
    const isAdmin = roles.includes('Admin');

    const listOptions = () => (
        <Box
            role="presentation"
            onClick={toggleDrawer(false)}
        >
            <List>
                <ListItemButton onClick={() => navigate("/")}>
                    <ListItemIcon>
                        <HomeIcon />
                    </ListItemIcon>
                    <ListItemText primary="Home" />
                </ListItemButton>

                <Divider />
                {isAdmin ? (
                    <>
                        <ListItemButton onClick={() =>
                            navigate("/paquetes")}>
                            <ListItemIcon>
                                <AirplaneTicketIcon />
                            </ListItemIcon>
                            <ListItemText primary="Paquetes Turísticos" />
                        </ListItemButton>
                        <Divider />
                    </>
                ):(
                    <>
                        <ListItemButton onClick={() =>
                            navigate("/paquetesTuristicos")}>
                            <ListItemIcon>
                                <AirplaneTicketIcon />
                            </ListItemIcon>
                            <ListItemText primary="Paquetes Turísticos" />
                        </ListItemButton>

                        <ListItemButton onClick={()=>
                            navigate(`/misReservas`)}>
                            <ListItemIcon>
                                <CalendarMonthIcon />
                            </ListItemIcon>
                            <ListItemText primary="Reservas Actuales" />
                        </ListItemButton>
                        <Divider />
                    </>
                )}

                <Divider />


            </List>
        </Box>
    );
    return (
        <div>
            <Drawer anchor={"left"} open={open} onClose={toggleDrawer(false)}>
                {listOptions()}
            </Drawer>
        </div>
    );
}