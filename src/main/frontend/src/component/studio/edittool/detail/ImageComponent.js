import Button from '@mui/material/Button';
import {styled} from '@mui/material/styles';
import {fabric} from 'fabric';
import {useEffect} from 'react';
import {ThemeProvider} from '@mui/material/styles';
import {theme} from '../theme/MainTheme'

export default function ImageComponent(props) {
    const canvas = props.canvas;

    useEffect(()=> {
        document.getElementById('contained-button-file').onchange = function(e) {
            const fileReader = new FileReader();
            fileReader.readAsDataURL(e.target.files[0]);
            fileReader.onload = function (e) {
                console.log(e.target.result);
                const imageInstance = new fabric.Image.fromURL(e.target.result,function(oImg) {
                    canvas.add(oImg)
                })
            }
        }
    });

    const Input = styled('input')({
        display: 'none',
      });

    return (
        <>
            <h3 style={{ margin : "0", paddingTop : "20px", paddingLeft : "30px"}}>이미지 모드</h3>
            <label htmlFor="contained-button-file">
                <Input accept="image/*" id="contained-button-file" multiple type="file"/>
                <ThemeProvider theme={theme}>
                <Button color = "button" style={{marginLeft : "30px" , marginBottom : "-90px"}} variant="contained" component="span">
                    이미지 업로드
                </Button>
                </ThemeProvider>
            </label>
        </>
    );
}