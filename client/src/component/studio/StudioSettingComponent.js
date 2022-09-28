import React,{useRef,useEffect,useState} from 'react';
import Box from '@mui/material/Box';
import Slider from '@mui/material/Slider';
import InputLabel from '@mui/material/InputLabel';
import MenuItem from '@mui/material/MenuItem';
import FormControl from '@mui/material/FormControl';
import Select from '@mui/material/Select';
import {colorInfo} from './colorData'
import { ThemeProvider } from '@mui/material';
import {theme} from './edittool/theme/MainTheme'



export default function StudioSettingComponent() {
 

    const lineColorHandleChange = (event) => {
        const convertLineColor = colorInfo.find((value) => value.name === event.target.value);
        localStorage.setItem("lineColor",convertLineColor.hex);
    };

   

    const lineThickHandleChange = (event, newValue) => {
        
        
        localStorage.setItem("lineThick",event.target.value);
    };


    return (
        <>
        <ThemeProvider theme={theme}>
            <div>
                <p>선 굵기</p>
                <Box width={300}>
                    <Slider
                        color="button"
                        size="small"
                        defaultValue={70}
                        aria-label="Small"
                        valueLabelDisplay="auto"
                        onChange={lineThickHandleChange}
                    />
                </Box>

                <p>선 색상</p>
                <Box sx={{ minWidth: 120 }}>
                    <FormControl fullWidth>
                        <InputLabel id="demo-simple-select-label">색상 </InputLabel>
                        <Select
                            
                            labelId="demo-simple-select-label"
                            id="demo-simple-select"
                            label="Color"
                            onChange={lineColorHandleChange}
                        >

                            {colorInfo.map((color,index)=> {
                                return (<MenuItem key={index} value={color.name}>{color.name}</MenuItem>)
                            })}
                            
                        </Select>
                    </FormControl>
                </Box>
            </div>
            </ThemeProvider>
        </>

    )
}