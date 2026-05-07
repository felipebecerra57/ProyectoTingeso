import './App.css'
import { useKeycloak } from '@react-keycloak/web';
import {BrowserRouter , Routes, Route} from 'react-router-dom';
import Navbar from './components/Navbar'
import PackageListAdmin from './pages/packageListAdmin.jsx';
import PackageList from './pages/PackageList.jsx';
import PackageDetail from './pages/PackageDetail.jsx';
import Home from './components/Home';
import { Container } from '@mui/material';
import CreatePackage from "./pages/CreatePackage.jsx";
import ReservationList from "./pages/ReservationList.jsx"

function App() {
  const { keycloak, initialized } = useKeycloak();

  if (!initialized) return <div>Cargando...</div>;

  const isLoggedIn = keycloak.authenticated;
  const roles = keycloak.tokenParsed?.realm_access?.roles || [];

  const PrivateRoute = ({ element, rolesAllowed }) => {
    if (!isLoggedIn) {
      keycloak.login();
      return null;
    }
    if (rolesAllowed && !rolesAllowed.some(r => roles.includes(r))) {
      return <h2>No tienes permiso para ver esta página</h2>;
    }
    return element;
  };

  if (!isLoggedIn) { 
    keycloak.login(); 
    return null; 
  }
  return (
    <BrowserRouter>
      <Navbar/>
      <Container maxWidth="lg" sx={{ mt: 4, mb: 4 }}>
        <Routes>
          <Route path="/" element={<Home />} />
          <Route path="/paquetes" element={<PackageListAdmin />} />
          <Route path="/paquetesTuristicos" element={<PackageList />} />
          <Route path="/nuevoPaquete" element={<CreatePackage />}/>
          <Route path="/detallePaquete" element={<PackageDetail/>}/>
          <Route path="/misReservas" element={<ReservationList/>}/>
        </Routes>
      </Container>
    </BrowserRouter>
    
  )
}
export default App
