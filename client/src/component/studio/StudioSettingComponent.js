import React,{useRef,useEffect,useState} from 'react';
import Box from '@mui/material/Box';
import Slider from '@mui/material/Slider';
import InputLabel from '@mui/material/InputLabel';
import MenuItem from '@mui/material/MenuItem';
import FormControl from '@mui/material/FormControl';
import Select from '@mui/material/Select';
import Button from '@mui/material/Button'
import {colorInfo} from './colorData'
import { ThemeProvider } from '@mui/material';
import {theme} from './edittool/theme/MainTheme'


export default function StudioSettingComponent() {
    const [lineThick, setLineThick] = useState(15);
    const [lineColor, setLineColor] = useState('빨간색');
    const [bgColor, setBgColor] = useState('빨간색');

    const lineColorHandleChange = (event) => {
        setLineColor(event.target.value)
    };

    const bgColorHandleChange = (event) => {
        setBgColor(event.target.value);
    };

    const lineThickHandleChange = (event, newValue) => {
        setLineThick(newValue);
    }

    useEffect(()=> {
        console.log("lineColor : " + lineColor);
    },[lineColor])

    useEffect(()=> {
        console.log("bgColor : " + bgColor);
    },[bgColor])

    const settingConfirm = () => {
        const convertLineColor = colorInfo.find((value)=> value.name === lineColor);
        console.log(convertLineColor.hex);
        window.localStorage.setItem("lineThick",lineThick);
        window.localStorage.setItem("lineColor",convertLineColor.hex);
        window.localStorage.setItem("backgroundColor",bgColor)
    }


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
                            value={lineColor}
                            label="Color"
                            onChange={lineColorHandleChange}
                        >

                            {colorInfo.map((color,index)=> {
                                return (<MenuItem key={index} value={color.name}>{color.name}</MenuItem>)
                            })}
                            
                        </Select>
                    </FormControl>
                </Box>

                <p>배경 색상</p>
                <Box sx={{ minWidth: 120 }}>
                    <FormControl fullWidth>
                        <InputLabel id="demo-simple-select-label">색상</InputLabel>
                        <Select
                            labelId="demo-simple-select-label"
                            id="demo-simple-select"
                            value={bgColor}
                            label="Color"
                            onChange={bgColorHandleChange}
                        >

                            {colorInfo.map((color,index)=> {
                                 return (<MenuItem key={index} value={color.name}>{color.name}</MenuItem>)
                            })}
                            
                        </Select>
                    </FormControl>
                </Box>

                <Button sx={{marginTop: "20px"}} color="button" variant="contained" onClick={settingConfirm}>확인</Button>
            </div>
            </ThemeProvider>
        </>

    )
}