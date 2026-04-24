import { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'
import './index.css'
import App from './App.jsx'
import { ReactKeycloakProvider } from '@react-keycloak/web';
import keycloak from "./services/keycloak";

createRoot(document.getElementById('root')).render(
  <StrictMode>
    <ReactKeycloakProvider authClient={keycloak}>
      <App />
    </ReactKeycloakProvider>
    
  </StrictMode>,
)
