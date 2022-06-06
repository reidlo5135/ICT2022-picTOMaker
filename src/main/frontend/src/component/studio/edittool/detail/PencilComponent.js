import Box from '@mui/material/Box';
import Slider from '@mui/material/Slider';
import InputLabel from '@mui/material/InputLabel';
import MenuItem from '@mui/material/MenuItem';
import FormControl from '@mui/material/FormControl';
import Select from '@mui/material/Select';
import {useState} from 'react';
import {theme} from '../theme/MainTheme';
import { ThemeProvider } from '@mui/material/styles';

const colorList = ["빨간색","주황색","노란색","초록색","파란색","남색","보라색","검정색","하얀색"];

export default function PencilComponent(props) {
    const [color,setColor] = useState('빨간색');
    const canvas = props.canvas;

    const thickChange = (event,newValue) => {
        canvas.freeDrawingBrush.width = newValue;
    }

    const handleChange = (event) => {
        const colorName = event.target.value;
        setColor(event.target.value);
        console.log(event.target.value);
        switch(colorName) {
            case "빨간색":
                canvas.freeDrawingBrush.color = '#FF3030';  
                break;
            case "주황색":
                canvas.freeDrawingBrush.color = '#FC9D45';
                break;
            case "노란색":
                canvas.freeDrawingBrush.color = '#FCFF63';
                break;
            case "초록색":
                canvas.freeDrawingBrush.color = '#49FF45';
                break;
            case "파란색":
                canvas.freeDrawingBrush.color = '#2E81FF';
                break;
            case "남색":
                canvas.freeDrawingBrush.color = '#2312E8';
                break;
            case "보라색":
                canvas.freeDrawingBrush.color = '#E91EED';
                break;
            case "검정색":
                canvas.freeDrawingBrush.color = '#1B1B1B';
                break;
        }
      };

    return ( 
        <>
            <h3 style={{ margin : "0", paddingTop : "20px", paddingLeft : "30px"}}>그리기 모드</h3>
            <div style={{display : "flex",flexDirection : "row", justifyContent : "flex-start", marginTop : "10px", marginLeft : "20px"}}>
                <div style={{height : "150px", width:"150px", marginLeft : "10px"}}>
                    <p style={{textAlign : "left"}}>선 색상</p>
                    <FormControl size="small">
                            <Select
                                id="demo-simple-select"
                                value={color}
                                onChange={handleChange}
                                >
                                {colorList.map(item=> {
                                 return <MenuItem key={item} value={item}>{item}</MenuItem>
                                })}
                            </Select>
                    </FormControl>
                </div>
                <div style={{height : "150px", width:"150px",marginLeft : "10px"}}>
                    <p style={{textAlign : "left"}}>선 굵기</p>
                    <ThemeProvider theme={theme}>
                    <Box width={100} sx={{marginTop : "20px"}}>

                        <Slider
                            color = "button"
                            size="small"
                            defaultValue={15}
                            aria-label="Small"
                            valueLabelDisplay="auto"
                            onChange={thickChange}
                        />
                    </Box>
                    </ThemeProvider>
                </div>
            </div>
        </>
    );
}