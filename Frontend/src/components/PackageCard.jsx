import { Card, CardContent, CardMedia, Typography, Button, Box, Chip } from '@mui/material';

const PackageCard = ({ pkg, onReserve }) => {
    return (
        <Card sx={{ maxWidth: 345, borderRadius: 4, boxShadow: 3 }}>
            {/* Picture of the destiny
            <CardMedia
                component="img"
                height="140"
                image="https://source.unsplash.com/random/?travel,landscape"
                alt={pkg.destiny}
            />
            */}

            <CardContent>
                <Typography variant="caption" color="text.secondary">Paquete {pkg.name}</Typography>
                <Typography variant="h6" component="div" sx={{ fontWeight: 'bold' }}>
                    {pkg.destiny}
                </Typography>

                <Chip label="Solo ida" size="small" sx={{ mt: 1, mb: 1, bgcolor: '#f3e5f5', color: '#7b1fa2' }} />

                <Box sx={{ mt: 2 }}>
                    <Typography variant="body2" color="text.secondary">Precio desde</Typography>
                    <Typography variant="h5" color="primary" sx={{ fontWeight: 'bold' }}>
                        CLP {pkg.price.toLocaleString('es-CL')}
                    </Typography>
                </Box>

                <Button
                    fullWidth
                    variant="contained"
                    sx={{ mt: 2, borderRadius: 2, bgcolor: '#1a237e' }}
                    onClick={() => onReserve(pkg.id)}
                >
                    Ver oferta
                </Button>
            </CardContent>
        </Card>
    );
};
export default PackageCard;