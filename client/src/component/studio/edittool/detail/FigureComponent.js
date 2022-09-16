import MenuItem from '@mui/material/MenuItem';
import FormControl from '@mui/material/FormControl';
import Select from '@mui/material/Select';
import Button from '@mui/material/Button';
import {fabric} from 'fabric';
import { useState } from 'react';
import {colorInfo} from '../../colorData';
import { ThemeProvider } from '@mui/material/styles';
import {theme} from '../theme/MainTheme'

const figureList = ["삼각형","사각형","둥근 사각형","원","타원"];
const colorList = ["빨간색","주황색","노란색","초록색","파란색","남색","보라색","검정색","하얀색"];

export default function FigureComponent(props) {
    const [figure,setFigure] = useState('삼각형');
    const [color,setColor] = useState('빨간색')
    const canvas = props.canvas;

    function addEvent() {
        const colorValue = colorInfo.find(colors => colors.name === color)
        const hex = String('#'+colorValue.hex);
       
        if (figure === "삼각형") {
            canvas.add(new fabric.Triangle({
                left : 0,
                top : 0,
                fill : hex,
                width : 20,
                height : 20
            }));
        } else if (figure === "사각형") {
            canvas.add(new fabric.Rect({
                left : 0,
                top : 0,
                fill : hex,
                width : 20,
                height : 20
            }));
        } else if (figure ==="둥근 사각형") {
            canvas.add(new fabric.Rect({
                left : 0,
                top : 0,
                fill : hex,
                width : 20,
                height : 20,
                rx : 5,
                ry : 5
            }));
        } else if (figure === "원") {
            console.log("원 추가")
            canvas.add(new fabric.Circle({
                left : 0,
                top : 0,
                fill : hex,
                radius: 30
            }));
        } else if (figure === "타원") {
            console.log("타원 추가")
            canvas.add(new fabric.Ellipse({
                left : 0,
                top : 0,
                fill : hex,
                radius : 50,
                rx : 80,
                ry : 40
                
            }));
        }
    }
    
    const figureHandle = (event) => {
        setFigure(event.target.value)
    }

    const colorHandle = (event) => {
        setColor(event.target.value);
    }

    return (
        <>
            <h3 style={{ margin : "0", paddingTop : "20px", paddingLeft : "30px"}}>도형 모드</h3>
            <div style={{display : "flex",flexDirection : "row", justifyContent : "flex-start", marginTop : "10px", marginLeft : "20px"}}>
                 <div style={{height : "150px", width:"150px", marginLeft : "10px"}}>
                        <p style={{textAlign : "left"}}>도형 종류</p>
                        <FormControl size="small">
                            <Select
                                id="demo-simple-select"
                                value={figure}
                                onChange={figureHandle}
                                >
                                {figureList.map(item=> {
                                 return <MenuItem key={item} value={item}>{item}</MenuItem>
                                })}
                            </Select>
                        </FormControl>
                </div>
                <div style={{height : "150px", width:"150px", marginLeft : "10px"}}>
                        <p style={{textAlign : "left"}}>도형 색상</p>
                        <FormControl size="small">
                            <Select
                                id="demo-simple-select"
                                value={color}
                                onChange={colorHandle}
                                >
                                {colorList.map(item=> {
                                 return <MenuItem key={item} value={item}>{item}</MenuItem>
                                })}
                            </Select>
                        </FormControl>
                </div>
                <div style={{height : "150px", width:"150px", marginLeft : "10px"}}>
                <ThemeProvider theme={theme}>
                <Button color="button" sx={{marginTop : "20px"}} variant="contained" onClick={()=> {
                    addEvent();
                }}>추가</Button>
                </ThemeProvider>
                </div>
            </div>
        </>
    );
}