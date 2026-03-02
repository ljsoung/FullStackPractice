import { DialogContent, Stack, TextField } from "@mui/material";
import { Car } from "../types";

interface DialogFormProps {
    car: Car;
    handleChange: (event: React.ChangeEvent<HTMLInputElement>) => void;
}

function CarDialogContent({car, handleChange}: DialogFormProps){
    return(<DialogContent>
                <Stack spacing={2} mt={1}>
                    <TextField placeholder="Brand" name="brand" value={car.brand} onChange={handleChange} />
                    <TextField placeholder="Model" name="model" value={car.model} onChange={handleChange} />
                    <TextField placeholder="Color" name="color" value={car.color} onChange={handleChange} />
                    <TextField placeholder="RegistrationNumber" name="registrationNumber" value={car.registrationNumber} onChange={handleChange} />
                    <TextField placeholder="ModelYear" name="modelYear" value={car.modelYear} onChange={handleChange} />
                    <TextField placeholder="Price" name="price" value={car.price} onChange={handleChange} />
                </Stack>
            </DialogContent>);
}

export default CarDialogContent;