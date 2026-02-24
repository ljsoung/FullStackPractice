import { useState } from "react";
import { Car } from "../types";
import { Dialog, DialogActions, DialogContent, DialogTitle } from "@mui/material";
import { useMutation, useQueryClient } from "@tanstack/react-query";
import { addCar } from "../api/carapi";
import CarDialogContent from "./CarDialogContent";

function AddCar() {
    const [open, setOpen] = useState(false);
    const [car, setCar] = useState<Car>({
        brand: '',
        model: '',
        color: '',
        registrationNumber: '',
        modelYear: 0,
        price: 0
    });

    // AddCar 컴포넌트 함수 안에 추가
    const queryClient = useQueryClient();

    const { mutate } = useMutation(addCar, {
        onSuccess: () => {
        queryClient.invalidateQueries(["cars"]);
        },
        onError: (err) => {
        console.error(err);
        },
    });  

    const handleChange = (event: React.ChangeEvent<HTMLInputElement>) => {
        setCar({...car, [event.target.value]:event.target.value});
    }

    // 모달 폼 열기
    const handleClickOpen = () => {
        setOpen(true);
    }

    // 모달 폼 닫기
    const handleClose = () => {
        setOpen(false);
    }

    const handleSave = () => {
    mutate(car);
    setCar({ brand: '', model: '', color: '', registrationNumber: '', modelYear: 0, price: 0 });
    handleClose();

  } 

    return(<>
        <button onClick={handleClickOpen}>New Car</button>
        <Dialog open={open} onClose={handleClose}>
            <DialogTitle>New Car</DialogTitle>
            <DialogContent>
                <CarDialogContent car={car} handleChange={handleChange} />
            </DialogContent>
            <DialogActions>
                <button onClick={handleClose}>Cancel</button>
                <button onClick={handleSave}>Save</button>
            </DialogActions>
        </Dialog>
    </>)
}

export default AddCar;