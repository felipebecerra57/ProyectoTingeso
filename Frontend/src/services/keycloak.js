import Keycloak from "keycloak-js";

const keycloak = new Keycloak({
  url: "http://localhost:8080",
  realm: "travelAgency",
  clientId: "travelAgency-frontend",
});

export default keycloak;