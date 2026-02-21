import { Button, Dialog, DialogActions, DialogContent, DialogTitle, TextField } from "@mui/material";
import { useState } from "react";
import type { Item } from './App';

interface AddItemProps{
    addItem: (item:Item) => void;
}

function AddItem(props: AddItemProps){
    const [open, setOpen] = useState(false);
    const [item, setItem] = useState<Item>({
        product: '',
        amount: '',
    });

    const handleOpen = () => {
        setOpen(true);
    }

    const handleClose = () => {
        setOpen(false);
    }

    const addItem = () => {
        props.addItem(item);
        setItem({product: '', amount: ''});
        handleClose();
    }

    return(<>
        <Button onClick={handleOpen}>
            Add Item
        </Button>
        <Dialog open={open} onClose={handleClose}>
            <DialogTitle>New Item</DialogTitle>
            <DialogContent>
                <TextField value={item.product} margin="dense" onChange={e => setItem({...item, product: e.target.value})} label="Product" fullWidth />
                <TextField value={item.amount} margin="dense" onChange={e => setItem({...item, amount: e.target.value})} label="Amount" fullWidth />
            </DialogContent>
            <DialogActions>
                <Button onClick={handleClose}>
                    Cancel
                </Button>
                <Button onClick={addItem}>
                    Add
                </Button>
            </DialogActions>
        </Dialog>
    </>);
}

export default AddItem;